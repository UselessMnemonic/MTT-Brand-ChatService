package chatengine;

public class Broadcaster extends Thread
{
	private ServerResourceManager resources;
	private boolean shouldRun;
	
	public Broadcaster(ServerResourceManager resources)
	{
		this.resources = resources;
		shouldRun = true;
	}
	
	public void run()
	{
		while(shouldRun)
		{
			if(resources.hasPendingMessage())
			{
				resources.communicateDebug("NEED TO SEND MESSAGE");
				String messageToSend = resources.getNextPendingMessage();
				resources.sendToClients(messageToSend);
				resources.communicateDebug("COMPLETED BROADCASTING CYCLE");
			}
		}
	}

	public void shutdown()
	{
		shouldRun = false;
	}
}
