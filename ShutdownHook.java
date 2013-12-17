public class ShutdownHook extends Thread
{
	public void run()
	{
		evidence.db.close();
		System.out.println("DB zavřena");
		
		//System.exit(0);
	}	
}