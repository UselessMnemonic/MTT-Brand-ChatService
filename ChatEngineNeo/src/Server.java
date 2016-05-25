import java.io.IOException;

public class Server extends Thread{

	//Mission Report, December 16, 1991
	private ServerResourceManager res;
	private ClientManager manager;
	private Broadcaster broadcaster;
	
	public Server(int port) throws IOException
	{	
		 res = new ServerResourceManager(port);
		 manager = new ClientManager(res);
		 System.out.println("SERVER: Initializing Broadcaster...");
		 broadcaster = new Broadcaster(res);
		 System.out.println("SERVER IS READY");
	}
	
	public Server(String text) throws NumberFormatException, IOException {
		this(new Integer(text));
	}

	public synchronized void shutdown() throws IOException
	{

		res.shutdown();
	}
	
}
