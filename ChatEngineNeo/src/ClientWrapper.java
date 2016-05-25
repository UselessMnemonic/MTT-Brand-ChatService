import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientWrapper extends Thread{

	private volatile Socket wrappedClient;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ServerResourceManager res;
	private volatile boolean shouldRun;
	private volatile boolean alive;
	
	public ClientWrapper(Socket incomingClient, ServerResourceManager res) throws IOException {
		this.res = res;
		output = new ObjectOutputStream(incomingClient.getOutputStream());
		input = new ObjectInputStream(incomingClient.getInputStream());
		System.out.println("SERVER: Wrapping new client...");
		alive = true;
		wrappedClient = incomingClient;
		shouldRun = true;
		res.addClient(this);
		start();
	}

	public void run()
	{
		while(shouldRun)
		{
			try {
				Object incomingMessage = input.readObject();
				System.out.println("SERVER: Got client's message! Adding to list...");
				res.addMessage(incomingMessage);
				System.out.println("SERVER: Added message to list!");
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		try {
			alive = false;
			wrappedClient.close();
			res.removeClient(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void shutDown()
	{
		shouldRun = false;
	}
	
	public boolean isRunning()
	{
		return alive;
	}

	public void sendMessage(Object nextMessage) throws IOException {
		output.writeObject(nextMessage);
		System.out.println("SERVER WROTE TO OUTPUT");
		output.flush();
		System.out.println("SERVER FLUSHED");
	}
	
}
