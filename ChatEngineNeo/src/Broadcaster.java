public class Broadcaster extends Thread{

	private ServerResourceManager res;
	private volatile boolean shouldRun;

	public Broadcaster(ServerResourceManager res) {
		shouldRun = true;
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
				System.out.println("SERVER: Got next message...");
				res.sendToClients(nextMessage);
				System.out.println("SERVER: Sent last message...");
			}
		}
	}
	
}
