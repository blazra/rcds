import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
//import com.db4o.query.Query;
//import com.db4o.ObjectSet;

import java.util.Scanner;
import java.util.List;
import java.io.*;



public class evidence
{
    static ObjectContainer db;
    static Scanner scan;

    static void printChooseInfo()
    {
        System.out.println("Vyber akci pane!");
        System.out.println("list   - zobrazi vsechny evidovane predmety");
        System.out.println("search - vyhledat evidovane predmety");
        System.out.println("store  - pridat predmet do evidence");
        System.out.println("quit   - ukonceni");
        System.out.println();
    }

    static void waitForEnter()
    {
        Roll rollThread = new Roll();

        System.out.print("Stiskni enter pro pokracovani ");
        rollThread.start();
        try
        {
            System.in.read();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        rollThread.interrupt();
        System.out.print("\b");
    }

    static void listResult(List<ElPart> result){
        System.out.println("Pocet objektu: "+result.size());
        for (ElPart r : result)
        {
            for(Object o : r.getAllParams())
            {
                System.out.print(o+"\t");   
            }
            System.out.println();
        }
    }

    static void listAllObjects()
    {
        List<ElPart> parts = db.query(new Predicate<ElPart>()
        {
            public boolean match(ElPart part)
            {
                return true;
            }
        });

        listResult(parts);
        System.out.println();
    }

    static void storeObject()
    {
        System.out.println("Zadejte typ:");
        System.out.println("R pro rezistor");
        System.out.println("C pro kondenzator");

        ElPart part = ElPart.factory(scan.nextLine());
        
        db.store(part);
    }

    public static void main(String[] args) 
    {
        System.out.println("--- Vítej v evidenci ---\n");

        scan = new Scanner(System.in);
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "database.db4o"); //open db4o

        String choose;
        do
        {
            printChooseInfo();
            choose=scan.nextLine();
            System.out.println();
            switch(choose)
            {
                case "list": listAllObjects();
                    break;

                case "search":
                    break;

                case "store": storeObject();
                    break;
            }
            if(!(choose.equals("quit")||choose.equals("q")))
                waitForEnter();
        }while(!(choose.equals("quit")||choose.equals("q")));

        db.close();
        scan.close();
    }
}

