import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.db4o.config.*;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;


public class evidence
{
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    static ObjectContainer db;
    static Scanner scan;

    static void printErr(String s)
    {
        System.out.print(ANSI_RED);
        System.out.println(s);
        System.out.print(ANSI_RESET);
    }

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
        System.out.print(":) ");
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

        System.out.println("ID\tTyp\t\tIndukčnost\tImax\tTol");
        List<ElPart> inductors = db.query(new Predicate<ElPart>(){    //get all inductors
            public boolean match(ElPart part){
                return part.getClass()==Inductor.class;
            }});
        listResult(inductors);
        System.out.println();
    }

    static void deleteObject()
    {
        List<ElPart> parts = new ArrayList<>();

        System.out.print("Zadej ID objektu k vymazání: ");
        int wantedId = scan.nextInt();
        try
        {
            Object wantedObject = db.ext().getByID(wantedId);
            if(wantedObject==null)
                throw new com.db4o.ext.InvalidIDException(wantedId);
            parts.add((ElPart)wantedObject);
            listResult(parts);
            db.delete(parts.get(0));
            System.out.println("deleted");
        }
        catch(com.db4o.ext.InvalidIDException e)
        {
            System.out.println(ANSI_RED);
            System.out.println("Objekt nenalezen");
            System.out.print(ANSI_RESET);
        }

        System.out.println();
        scan.nextLine();    //clears buffer for waitforenter to work
    }

    static void storeObject()
    {
        System.out.println("Zadejte typ:");
        System.out.println("R pro rezistor");
        System.out.println("C pro kondenzátor");
        System.out.println("L pro cívku");

        ElPart part = ElPart.factory(scan.nextLine(), db.query(ElPart.class).size());
        if(part!=null)
        {
            db.store(part);
            scan.nextLine();    //clears buffer for waitforenter to work
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();         //create new config for db4o
        config.common().automaticShutDown(false);                               //disable db4o shutdownhook
        java.lang.Runtime.getRuntime().addShutdownHook(new ShutdownHook());     //enable our less verbose shutdownhook

        scan = new Scanner(System.in);
        db = Db4oEmbedded.openFile(config, "database.db4o");                    //open db4o

        printStartupInfo();

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

                    case "search":
                        break;

                    case "delete":  deleteObject();
                        break;

                    case "store":   storeObject();
                        break;
                }
            }
            catch(InputMismatchException e)
            {
                System.out.print(ANSI_RED);
                System.out.println("Chybně zadaná hodnota");
                System.out.print(ANSI_RESET);
                scan.nextLine();
            }

        }while(!(choose.equals("quit")||choose.equals("q")));

        //scan.close();
    }
}