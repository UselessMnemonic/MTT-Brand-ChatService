import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MemberWrapper extends Thread{
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ArrayList<Object> messages;
	private Socket client;

	public MemberWrapper(Socket joiningClient, ArrayList<Object> messages) throws IOException {
		
		in = new ObjectInputStream(joiningClient.getInputStream());
		out = new ObjectOutputStream(joiningClient.getOutputStream());
		this.messages = messages;
		client = joiningClient;
		start();
	}
	
	public void run()
	{
		while(true)
		{
			try {
				Object get = (Object) in.readObject();
				messages.add(get);
			} catch (IOException e) {
				break;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		try {
			client.close();
		} catch (IOException e) {
		}
	}

	public boolean isClosed() {
		return client.isClosed();
	}

	public void send(Object brstm) throws IOException {
		
		out.writeObject(brstm);
		
	}

	public void close() throws IOException {
		client.close();
	}
}
