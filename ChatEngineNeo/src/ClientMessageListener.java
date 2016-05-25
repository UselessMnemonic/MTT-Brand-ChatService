import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ClientMessageListener {

	private volatile ObjectInputStream incomingMessages;
	private volatile boolean shouldRun;
	private Chatable parent;
	
	public ClientMessageListener(InputStream inputStream, Chatable parent) throws IOException {
		this.parent = parent;
		System.out.println("CLIENT: Making input stream...");
		incomingMessages = new ObjectInputStream(inputStream);
		shouldRun = true;
		start();
	}
	
	public void start()
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

}
