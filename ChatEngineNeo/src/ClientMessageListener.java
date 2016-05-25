import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientMessageListener extends Thread{

	private volatile ObjectInputStream incomingMessages;
	private volatile boolean shouldRun;
	private Chatable parent;
	
	public ClientMessageListener(ObjectInputStream out, Chatable parent) throws IOException {
		this.parent = parent;
		incomingMessages = out;
		shouldRun = true;
		start();
	}
	
	public void run()
	{
		while(shouldRun)
		{
			try {
				System.out.println("CLIENT: Client is wating for message...");
				Object nextMessage = incomingMessages.readObject();
				System.out.println("CLIENT: Client got message!");
				parent.onMessage(nextMessage);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void shutdown() throws IOException {
		shouldRun = false;
		incomingMessages.close();
	}

}
