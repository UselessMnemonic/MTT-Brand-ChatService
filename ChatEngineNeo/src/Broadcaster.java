public class Broadcaster extends Thread{

	private ServerResourceManager res;
	private volatile boolean shouldRun;

	public Broadcaster(ServerResourceManager res) {
		this.res = res;
		start();
	}

	public void shutDown()
	{
		shouldRun = false;
	}
	
	public void run()
	{
		while(shouldRun)
		{
			if(res.hasNextMessage())
			{
				System.out.println("SERVER: Broadcasting next message...");
				Object nextMessage = res.removeNextMessage();
				res.sendToClients(nextMessage);
			}
		}
	}
	
}
