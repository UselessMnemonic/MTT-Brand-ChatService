import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientMessageListener extends Thread{
	
	private ObjectInputStream messageStream;
	private volatile Object message;
	private Chatable client;
	private volatile boolean shouldrun;
	
	public ClientMessageListener(Socket server, Chatable c) throws IOException {
		super();
		messageStream = new ObjectInputStream(server.getInputStream());
		client = c;
		shouldrun = true;
		start();
	}

	public void run()
	{
		while(shouldrun)
		{
			try {
				message = messageStream.readObject();
				client.onMessage(message);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void shutdown()
	{
		shouldrun = false;
	}

}
