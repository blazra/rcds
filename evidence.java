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
    public static final String ANSI_RED = "\u001B[31m";         //escape console code for red text
    public static final String ANSI_RESET = "\u001B[0m";        //and color reset..

    static ObjectContainer db;                                  //database instance
    static Scanner scan;                                        

    //prints red error msg
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
        System.out.println("list   - zobrazí všechny evidované předměty");
        System.out.println("search - vyhledat evidované predměty");
        System.out.println("delete - smazat předmět z evidence");
        System.out.println("store  - přidat předmět do evidence");
        System.out.println("quit   - ukončení");
        System.out.println();
        System.out.print(":) ");
    }

    static void waitForEnter() throws InterruptedException
    {
        Roll rollThread = new Roll();

        System.out.print("Stiskni enter pro pokracovani ");
        rollThread.start();         //start rolling

        scan.nextLine();            //wait for enter
   
        rollThread.interrupt();     //stop rolling
        rollThread.join();          //wait for roll thread to die

        System.out.println();
    }

    static int listResult(List<ElPart> result){
        for (ElPart r : result)                         //for all results from result list
        {
            System.out.print(db.ext().getID(r)+"\t");   //print db4o internal object ID
            for(Object o : r.getAllParams())            //print rest of the parameters
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
        System.out.println("ID\tTyp\t\tR\tPmax\tTol\tMateriál");        //print resistor header
        List<ElPart> resistors = db.query(new Predicate<ElPart>(){      //get all resistors from db4o
            public boolean match(ElPart part){
                return part.getClass()==Resistor.class;
            }});
        listResult(resistors);
        System.out.println();

        System.out.println("ID\tTyp\t\tC\tUmax\tDielektrikum\tTol");    //print capacitor header
        List<ElPart> capacitors = db.query(new Predicate<ElPart>(){     //get all capacitors from db4o
            public boolean match(ElPart part){
                return part.getClass()==Capacitor.class;
            }});
        listResult(capacitors);
        System.out.println();

        System.out.println("ID\tTyp\t\tL\tImax\tTol");                  //print inductor header
        List<ElPart> inductors = db.query(new Predicate<ElPart>(){      //get all inductors from db4o
            public boolean match(ElPart part){
                return part.getClass()==Inductor.class;
            }});
        listResult(inductors);
        System.out.println();
    }

    static void searchObjects()
    {
        System.out.println("Zadej typ");
        final char type = scan.nextLine().toLowerCase().charAt(0);
        System.out.print("Zadej rozsah hodnoty - min: ");
        final double min = ElPart.scanValue();
        System.out.print("                       max: ");
        final double max = ElPart.scanValue();
        List<ElPart> parts = db.query(new Predicate<ElPart>(){      
            public boolean match(ElPart part){
                return part.getName().toLowerCase().charAt(0)==type && part.getValue()>=min && part.getValue()<=max;
            }});
        listResult(parts);
        System.out.println();
    }

    static void deleteObject()
    {
        List<ElPart> parts = new ArrayList<>();

        System.out.print("Zadej ID objektu k vymazání: ");
        int wantedId = scan.nextInt();
        try
        {
            Object wantedObject = db.ext().getByID(wantedId);           //get object from db4o
            if(wantedObject==null)
                throw new com.db4o.ext.InvalidIDException(wantedId);    //throw exception if you enter ID of deleted object
            parts.add((ElPart)wantedObject);                            //add object to list for print
            listResult(parts);                                          //print object which will be deleted
            db.delete(parts.get(0));                                    //delete object from db4o
            System.out.println("deleted");
        }
        catch(com.db4o.ext.InvalidIDException e)
        {
            System.out.println(ANSI_RED);
            System.out.println("Objekt nenalezen");                     //print err msg in red
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

        ElPart part = ElPart.factory(scan.nextLine());           //create new part of desired type
        if(part!=null)                                          //if err don't store empty part
        {
            db.store(part);
            scan.nextLine();                                    //clears buffer for waitforenter to work
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

                    case "search":  searchObjects();
                        waitForEnter();
                        break;

                    case "delete":  deleteObject();
                        break;

                    case "store":   storeObject();
                        break;
                }
            }
            catch(InputMismatchException e)
            {
                printErr("Chybně zadaná hodnota");                //print err msg in red
                //scan.nextLine();
                waitForEnter();
            }

        }while(!(choose.equals("quit")||choose.equals("q")));
    }
}
