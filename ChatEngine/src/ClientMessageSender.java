import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientMessageSender extends Thread{

	private volatile Object message;
	private ObjectOutputStream messageStream;
	private boolean pendingMessage;
	private volatile boolean shouldrun;
	
	public ClientMessageSender(Socket s) throws IOException
	{
		pendingMessage = false;
		OutputStream out = s.getOutputStream();
		messageStream = new ObjectOutputStream(out);
		System.out.println("OOS saved");
		shouldrun = true;
		start();
	}

	public void sendMessage(Object o) {
		this.message = o;
		pendingMessage = true;
	}
	
	public void run()
	{
		System.out.println("Sender started");
		while(shouldrun)
		{
			if(pendingMessage)
			{
				try {
					pendingMessage = false;
					messageStream.writeObject(message);
					messageStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		//ending code
		try {
			messageStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void shutdown()
	{
		shouldrun = false;
	}
}
