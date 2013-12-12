public class ShutdownHook extends Thread
{
	public void run()
	{
		evidence.db.close();
		System.out.println("zavřena db");
		
		//System.exit(0);
	}	
}