package chatengine;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * @author Christopher Madrigal
 * The Client class is the front-end of the Client implementation of this chat engine.
 */
public class Client extends Thread
{
	private Chatable				parent;
	private Socket					connection;
	private BufferedUTF8Reader 		in;
	private BufferedUTF8Writer 		out;
	private boolean 				shouldRun;
	
	/**
	 * Constructs a new Client object.
	 * @param ip The IP address of the server.
	 * @param port The port to connect to.
	 * @param parent The Chatable that responds to messages.
	 * @throws UnknownHostException thrown by internal Socket
	 * @throws IOException thrown by internal IO streams
	 */
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
	
	/**
	 * Sends a message to the server.
	 * @param message The Message object whose JSON Serialized form will be transmitted to the server.
	 * 
	 * @see Chatable (Exceptions are rerouted to the parent)
	 */
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
	
	/**
	 * Closes the underlying Socket.
	 * @see Chatable (Exceptions are rerouted to the parent)
	 */
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
