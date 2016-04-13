import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientMessageListener extends Thread{
	
	private ObjectInputStream messageStream;
	private volatile Object message;
	private Chatable client;
	private volatile boolean shouldrun;
	
	public ClientMessageListener(Socket server, Chatable c) throws IOException {
		InputStream in = server.getInputStream();
		messageStream = new ObjectInputStream(in);
		System.out.println("OIS saved");
		client = c;
		shouldrun = true;
		start();
	}

	public void run()
	{
		System.out.println("Listener started");
		while(shouldrun)
		{
			try {
				message = messageStream.readObject();
				client.onMessage(message);
			} catch (IOException | ClassNotFoundException e) {
			}
		}
	}

	public void shutdown()
	{
		shouldrun = false;
	}

}
