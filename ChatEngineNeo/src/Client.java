import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private volatile Socket connection;
	private volatile ClientMessageListener listener;
	private volatile ObjectOutputStream sendMessages;
	
	public Client(String ip, int port, Chatable parent) throws UnknownHostException, IOException
	{
		System.out.println("CLIENT: Creating Client socket...");
		connection = new Socket(ip, port);
		System.out.println("CLIENT: Creating listener...");
		listener = new ClientMessageListener(connection.getInputStream(), parent);
		System.out.println("CLIENT: Creating output link...");
		sendMessages = new ObjectOutputStream(connection.getOutputStream());
	}
	
	public Client(String ip, String port, Chatable parent) throws NumberFormatException, UnknownHostException, IOException {
		this(ip, new Integer(port), parent);
	}

	public synchronized void sendMessage(Object message) throws IOException
	{
		sendMessages.writeObject(message);
	}
	
}
