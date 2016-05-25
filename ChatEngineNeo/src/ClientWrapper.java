import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientWrapper extends Thread{

	private volatile Socket wrappedClient;
	private volatile boolean shouldRun;
	private volatile ObjectInputStream incomingMessages;
	private volatile ObjectOutputStream outgoingMessages;
	private volatile ArrayList<Object> pendingMessages;
	private volatile ArrayList<ClientWrapper> parent;
	private volatile boolean alive;
	
	public ClientWrapper(Socket incomingClient, ArrayList<Object> pendingMessages, ArrayList<ClientWrapper> clients) throws IOException {
		System.out.println("SERVER: Wrapping new client...");
		alive = true;
		parent = clients;
		wrappedClient = incomingClient;
		shouldRun = true;
		System.out.println("SERVER: Grabbing client's input...");
		incomingMessages = new ObjectInputStream(wrappedClient.getInputStream());
		System.out.println("SERVER: Grabbing client's output...");
		outgoingMessages = new ObjectOutputStream(wrappedClient.getOutputStream());
		System.out.println("SERVER: Adding new client...");
		clients.add(this);
		start();
	}

	public void run()
	{
		while(shouldRun)
		{
			try {
				System.out.println("SERVER: Waiting for client's message...");
				Object incomingMessage = incomingMessages.readObject();
				System.out.println("SERVER: Got client's message! Adding to list...");
				pendingMessages.add(incomingMessage);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		try {
			alive = false;
			wrappedClient.close();
			parent.remove(parent.indexOf(this));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void shutDown()
	{
		shouldRun = false;
	}
	
	public synchronized boolean isRunning()
	{
		return alive;
	}

	public synchronized void sendMessage(Object nextMessage) throws IOException {
		outgoingMessages.writeObject(nextMessage);
		outgoingMessages.flush();
	}
	
}
