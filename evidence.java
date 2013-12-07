import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;


public class evidence
{
    static ObjectContainer db;
    static Scanner scan;

    static void printStartupInfo()
    {
        System.out.println("--- Vítej v evidenci ---");
        System.out.println("Copyright 2013 Radovan Blažek");
        System.out.println("This is free software with ABSOLUTELY NO WARRANTY.\n");
    }

    static void printChooseInfo()
    {
        System.out.println("list   - zobrazi vsechny evidovane predmety");
        System.out.println("search - vyhledat evidovane predmety");
        System.out.println("delete - smazat předmět z databáze");
        System.out.println("store  - pridat predmet do evidence");
        System.out.println("quit   - ukonceni");
        System.out.println();
    }

    static void waitForEnter() throws InterruptedException
    {
        Roll rollThread = new Roll();

        System.out.print("Stiskni enter pro pokracovani ");
        rollThread.start();

        scan.nextLine();
   
        rollThread.interrupt();
        rollThread.join();          //wait for roll thread to die
    }

    static int listResult(List<ElPart> result){
        for (ElPart r : result)
        {
            System.out.print(db.ext().getID(r)+"\t");   //print db4o internal object ID
            for(Object o : r.getAllParams())
            {
                System.out.print(o+"\t");  
            }
            System.out.println();
        }
        System.out.println("Pocet objektu: "+result.size());
        return result.size();
    }

    static void listAllObjects()
    {
        System.out.println("ID\tTyp\t\tOdpor\tPmax\tTol\tMateriál");
        List<ElPart> resistors = db.query(new Predicate<ElPart>(){    //get all resistors
            public boolean match(ElPart part){
                return part.getClass()==Resistor.class;
            }});
        listResult(resistors);
        System.out.println();
        System.out.println("ID\tTyp\t\tKap\tUmax\tDielektrikum\tTol");
        List<ElPart> capacitors = db.query(new Predicate<ElPart>(){    //get all capacitors
            public boolean match(ElPart part){
                return part.getClass()==Capacitor.class;
            }});
        listResult(capacitors);
        System.out.println();
    }

    static void deleteObject()
    {
        List<ElPart> parts = new ArrayList<>();

        System.out.print("Zadej ID objektu k vymazání: ");
        Object wantedObject = db.ext().getByID(scan.nextLong());
        if(wantedObject!=null)
        {
            parts.add((ElPart)wantedObject);
            listResult(parts);
            db.delete(parts.get(0));
            System.out.println("deleted");
        }
        else
        {
            System.out.println("Objekt nenalezen");
        }

        System.out.println();
        scan.nextLine();    //clears buffer for waitforenter to work
    }

    static void storeObject()
    {
        System.out.println("Zadejte typ:");
        System.out.println("R pro rezistor");
        System.out.println("C pro kondenzátor");

        ElPart part = ElPart.factory(scan.nextLine(), db.query(ElPart.class).size());
        if(part!=null)
            db.store(part);

        scan.nextLine();    //clears buffer for waitforenter to work
    }

    public static void main(String[] args) throws InterruptedException
    {
        printStartupInfo();

        scan = new Scanner(System.in);
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "database.db4o"); //open db4o

        String choose;
        do
        {
            printChooseInfo();
            choose=scan.nextLine();
            System.out.println();
            try
            {
                switch(choose)
                {
                    case "list":    listAllObjects();
                        waitForEnter();
                        break;

                    case "searcwaitForEnter();h":
                        break;

                    case "delete":  deleteObject();
                        break;

                    case "store":   storeObject();
                        break;
                }
            }
            catch(InputMismatchException e)
            {
                System.out.println("Chybně zadaná hodnota");
                scan.nextLine();
            }

        }while(!(choose.equals("quit")||choose.equals("q")));

        db.close();
        scan.close();
    }
}

