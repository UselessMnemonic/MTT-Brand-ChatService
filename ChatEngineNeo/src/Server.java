import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server extends Thread{

	//Mission Report, December 16, 1991
	private volatile ArrayList<ClientWrapper> clients;
	private volatile ArrayList<Object> pendingMessages;
	private volatile ServerSocket self;
	private volatile ClientManager manager;
	private volatile Broadcaster broadcaster;
	
	public Server(int port) throws IOException
	{	
		 System.out.println("SERVER: Initializing client list...");
		 clients = new ArrayList<ClientWrapper>();
		 System.out.println("SERVER: Initializing message list...");
		 pendingMessages = new ArrayList<Object>();
		 System.out.println("SERVER: Initializing server socket...");
		 self = new ServerSocket(port);
		 System.out.println("SERVER: Initializing Client Manager...");
		 manager = new ClientManager(self, clients, pendingMessages);
		 System.out.println("SERVER: Initializing Broadcaster...");
		 broadcaster = new Broadcaster(clients, pendingMessages);
	}
	
	public Server(String text) throws NumberFormatException, IOException {
		this(new Integer(text));
	}

	public synchronized void shutdown() throws IOException
	{

		System.out.println("SERVER: Shuting down broadcaster...");
		broadcaster.shutDown();
		System.out.println("SERVER: Shuting down manager...");
		manager.shutDown();
		System.out.println("SERVER: Shuting down server socket...");
		self.close();
	}
	
}
