import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientMessageListener extends Thread{
	
	private volatile ObjectInputStream messageStream;
	private volatile Object message;
	private volatile Chatable client;
	private volatile boolean shouldrun;
	
	public ClientMessageListener(Socket server, Chatable c) throws IOException {
		messageStream = new ObjectInputStream(server.getInputStream());
		System.out.println("IN established!");
		client = c;
		shouldrun = true;
		start();
	}

	public void run()
	{
		System.out.println("Listener started!");
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
