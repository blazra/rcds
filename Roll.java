public class Roll extends Thread
{
	public void run()
	{
		System.out.print("/ ");
        int sleepTime = 100;
        
	        while(!Thread.currentThread().isInterrupted())
	        {
	        	try
	        	{
		            System.out.print("\b\b| ");
		            Thread.currentThread().sleep(sleepTime);
		            System.out.print("\b\b\\ ");
		            Thread.currentThread().sleep(sleepTime);
		            System.out.print("\b\b- ");
		            Thread.currentThread().sleep(sleepTime);
		            System.out.print("\b\b/ ");
		            Thread.currentThread().sleep(sleepTime);
	            }
	            catch(InterruptedException e)
	            {
	            	Thread.currentThread().interrupt();
	            }
	        }
	}
}