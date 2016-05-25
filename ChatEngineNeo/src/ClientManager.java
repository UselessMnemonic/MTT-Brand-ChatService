import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager extends Thread{

	private volatile ServerSocket incomingClients;
	private volatile boolean shouldRun;
	private volatile ArrayList<ClientWrapper> clients;
	private volatile ArrayList<Object> pendingMessages;
	
	public ClientManager(ServerSocket self, ArrayList<ClientWrapper> clients, ArrayList<Object> pendingMessages) {
		this.pendingMessages = pendingMessages;
		this.clients = clients;
		incomingClients = self;
		shouldRun = true;
		start();
	}

	public synchronized void shutDown()
	{
		shouldRun = false;
	}
	
	public void run()
	{
		while(shouldRun)
		{
			try {
				System.out.println("SERVER: Waiting for client...");
				Socket incomingClient = incomingClients.accept();
				System.out.println("SERVER: Client connected!");
				new ClientWrapper(incomingClient, pendingMessages, clients);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
