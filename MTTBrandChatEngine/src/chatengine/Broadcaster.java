package chatengine;
/**
 * 
 * @author Christopher Madrigal
 * The Broadcaster class enables messages to be routed across all clients connected to the server.
 */
public class Broadcaster extends Thread
{
	private ServerResourceManager resources;
	private boolean shouldRun;
	
	/**
	 * Creates a new Broadcaster object
	 * @param resources The ServerResourceManager that provides resource access
	 */
	public Broadcaster(ServerResourceManager resources)
	{
		this.resources = resources;
		shouldRun = true;
	}
	
	public void run()
	{
		Message messageToSend;
		
		while(shouldRun)
		{
			if(resources.hasPendingMessage())
			{
				resources.communicateDebug("NEED TO SEND MESSAGE");
				messageToSend = resources.getNextPendingMessage();
				
				if(messageToSend.getDestination().equals("ALL"))
				{
					resources.sendToClients(messageToSend);
				}
				else if(messageToSend.getDestination().equals("HOST"))
				{
					resources.communicateSystemMessage(messageToSend);
				}
				else
				{
					resources.sendToClient(messageToSend.getDestination(), messageToSend);
				}
				
				resources.communicateDebug("COMPLETED BROADCASTING CYCLE");
			}
		}
	}

	/**
	 * Stops sending messages
	 */
	public void shutdown()
	{
		shouldRun = false;
	}
}
