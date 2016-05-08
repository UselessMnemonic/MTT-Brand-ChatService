import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MemberWrapper extends Thread{
	
	private volatile ObjectInputStream in;
	private volatile ObjectOutputStream out;
	private volatile ArrayList<Object> messages;
	private volatile Socket client;

	public MemberWrapper(Socket joiningClient, ArrayList<Object> messages) throws IOException {
		this.messages = messages;
		client = joiningClient;
		start();
	}
	
	public void run()
	{
		
		try {
			Thread.sleep(1000);
			in = new ObjectInputStream(client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());
		} catch (InterruptedException | IOException e1) {
			e1.printStackTrace();
		}
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
