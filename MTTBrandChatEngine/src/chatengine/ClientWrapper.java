package chatengine;

import java.io.IOException;
import java.net.Socket;

/**
 * 
 * @author Christopher Madrigal
 * The ClientWrapper class encapsulates all client communication.
 */
public class ClientWrapper extends Thread
{
	private Socket 					clientConnection;
	private BufferedUTF8Reader 				in;
	private BufferedUTF8Writer 				out;
	private ServerResourceManager 	resources;
	private boolean 				alive;
	private String					clientID;
	
	/**
	 * Creates a new ClientWrapper object
	 * @param clientConnection The socket that represents the connection to the client
	 * @param resources The ServerResourceManager object that provides access to the Message pool
	 * @throws IOException Thrown by internal BufferedUTF8Reader/Writer
	 * @see BufferedUTF8Reader
	 * @see BufferedUTF8Writer
	 */
	public ClientWrapper(Socket clientConnection, ServerResourceManager resources) throws IOException
	{
		this.clientConnection = clientConnection;
		this.resources = resources;
		clientID = clientConnection.getInetAddress().toString();
		in = new BufferedUTF8Reader(clientConnection.getInputStream());
		resources.communicateDebug("CREATED WRAPPER READER");
		out = new BufferedUTF8Writer(clientConnection.getOutputStream());
		resources.communicateDebug("CREATED WRAPPER WRITER");
		alive = true;
	}
	
	/**
	 * Sends a message
	 * @param message The message to send to the client.
	 */
	public void sendMessage(Message message)
	{
		
		try
		{
			out.write(message.toString());
			resources.communicateDebug("WROTE MESSAGE TO STREAM");
		} 
		catch (IOException e)
		{
			resources.communicateDebug(e);
			alive = false;
			resources.cleanClientPool();
		}
	}
	
	
	public void run()
	{
		String recievedMessage;
		while(alive)
		{
			try
			{
				if(in.ready())
				{
					
					recievedMessage = in.read();
					resources.communicateDebug("CLIENT HAS SENT MESSAGE");
					resources.addMessage(new Message(recievedMessage));
				}
			}
			catch (IOException e)
			{
				resources.communicateDebug(e);
				alive = false;
			}
		}
	}

	/**
	 * Closes the underlying socket
	 */
	public void close()
	{
		alive = false;
		try
		{
			resources.communicateDebug("CLOSING CLIENT WRAPPER");
			clientConnection.close();
			resources.communicateDebug("CLSOED CLIENT WRAPPER");
		} catch (IOException e)
		{
			resources.communicateDebug(e);
		}
	}
	
	/**
	 * Returns client status
	 * @return Whether or not the client has disconnected
	 */
	public boolean isClientAlive()
	{
		return alive;
	}

	/**
	 * Returns the Client's ID
	 * @return The String that contains the client's ID
	 */
	public String getClientID()
	{
		return clientID;
	}
}