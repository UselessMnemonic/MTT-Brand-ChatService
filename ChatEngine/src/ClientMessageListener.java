import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientMessageListener<Format> extends Thread{
	
	private ObjectInputStream messageStream;
	private volatile Format message;
	private Chatable<Format> client;
	private volatile boolean shouldrun;
	
	public ClientMessageListener(Socket server, Chatable<Format> c) throws IOException {
		super();
		messageStream = new ObjectInputStream(server.getInputStream());
		client = c;
		shouldrun = true;
		start();
	}
	
	@SuppressWarnings("unchecked")
	public void run()
	{
		while(shouldrun)
		{
			try {
				while(messageStream.available() == 0){}
				message = (Format)messageStream.readObject();
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
