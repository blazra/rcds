import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
//import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
//import com.db4o.query.Query;

import java.util.Scanner;
import java.util.List;
import java.io.*;



public class evidence
{
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
        Resistor odpor = new Resistor(100, 0.33, 3);
        
        db.store(odpor);
    }

    static ObjectContainer db;

    public static void main(String[] args) 
    {
        System.out.println("--- Vítej v evidenci ---\nverze 0.2 revize Doma 2.12.2013\n");

        Scanner scan = new Scanner(System.in);
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
            if(!choose.equals("quit"))
                waitForEnter();
        }while(!choose.equals("quit"));

        db.close();
        scan.close();
    }
}

