import java.io.IOException;
import java.util.ArrayList;

public class Broadcaster extends Thread{

	private volatile ArrayList<ClientWrapper> clients;
	private volatile ArrayList<Object> pendingMessages;
	private volatile boolean shouldRun;
	
	public Broadcaster(ArrayList<ClientWrapper> clients, ArrayList<Object> pendingMessages) {
		this.clients = clients;
		this.pendingMessages = pendingMessages;
		shouldRun = true;
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
			if(pendingMessages.size() > 0)
			{
				System.out.println("SERVER: Broadcasting next message...");
				Object nextMessage = pendingMessages.remove(0);
				for(int x = 0; x< clients.size(); x++)
				{
					ClientWrapper nextClient = clients.get(x);
					try {
						nextClient.sendMessage(nextMessage);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
