import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private Socket connection;
	private ClientMessageListener listener;
	private ObjectOutputStream sendMessages;
	
	public Client(String ip, int port, Chatable parent) throws UnknownHostException, IOException
	{
		System.out.println("CLIENT: Creating Client socket...");
		connection = new Socket(ip, port);
		System.out.println("CLIENT: Creating output link...");
		sendMessages = new ObjectOutputStream(connection.getOutputStream());
		System.out.println("CLIENT: Creating input link...");
		ObjectInputStream out = new ObjectInputStream(connection.getInputStream());
		System.out.println("CLIENT: Creating listener...");
		listener = new ClientMessageListener(out, parent);
		System.out.println("CLIENT IS READY");
	}
	
	public Client(String ip, String port, Chatable parent) throws NumberFormatException, UnknownHostException, IOException {
		this(ip, new Integer(port), parent);
	}

	public void sendMessage(Object message) throws IOException
	{
		sendMessages.writeObject(message);
	}

	public void shutdown() throws IOException {
		listener.shutdown();
		connection.close();
	}
	
}
