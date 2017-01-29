package chatengine;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread
{
	private Chatable				parent;
	private Socket					connection;
	private BufferedUTF8Reader 		in;
	private BufferedUTF8Writer 		out;
	private boolean 				shouldRun;
	
	public Client(String ip, int port, Chatable parent) throws UnknownHostException, IOException
	{
		shouldRun = true;
		connection = new Socket(ip, port);
		this.parent = parent;
		in = new BufferedUTF8Reader(connection.getInputStream());
		out = new BufferedUTF8Writer(connection.getOutputStream());
		start();
	}
	
	public void run()
	{
		String nextMessage;
		while(shouldRun)
		{
			try
			{
				if(in.ready())
				{
					nextMessage = in.read();
					parent.onMessage(new Message(nextMessage));
				}
			}
			catch (IOException e)
			{
				parent.onError(e);
			}
		}
	}
	
	public void sendMessage(Message message)
	{
		try
		{
			out.write(message.toString());
		}
		catch (IOException e)
		{
			parent.onError(e);
		}
	}
	
	public void sendMessage(String message)
	{
		sendMessage(Message.constructMessage(message));
	}
	
	public void shutdown()
	{
		shouldRun = false;
		try
		{
			connection.close();
		} catch (IOException e)
		{
			parent.onError(e);
		}
	}
}
