import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager extends Thread{

	private ServerResourceManager res;
	private volatile boolean shouldRun;
	
	public ClientManager(ServerResourceManager res) {
		this.res = res;
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
			try {
				System.out.println("SERVER: Waiting for a new client...");
				Socket incomingClient = res.acceptNextClient();
				System.out.println("SERVER: Client connected!");
				new ClientWrapper(incomingClient, res);
			} catch (IOException e) {
				shouldRun = false;
			}
		}
	}
	
}
