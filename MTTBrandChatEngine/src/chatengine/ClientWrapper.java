package chatengine;

import java.io.IOException;
import java.net.Socket;

public class ClientWrapper extends Thread
{
	private Socket 					clientConnection;
	private BufferedUTF8Reader 				in;
	private BufferedUTF8Writer 				out;
	private ServerResourceManager 	resources;
	private boolean 				alive;
	private String					clientID;
	
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
	
	public void sendMessage(String messageContent)
	{
		
		try
		{
			out.write(messageContent);
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
	
	public boolean isClientAlive()
	{
		return alive;
	}

	public String getClientID()
	{
		return clientID;
	}
}