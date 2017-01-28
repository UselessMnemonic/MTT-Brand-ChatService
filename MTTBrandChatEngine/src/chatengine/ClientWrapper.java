package chatengine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientWrapper extends Thread
{
	private Socket 					clientConnection;
	private BufferedReader 			in;
	private BufferedWriter 			out;
	private ServerResourceManager 	resources;
	private boolean 				alive;
	
	public ClientWrapper(Socket clientConnection, ServerResourceManager resources) throws IOException
	{
		this.clientConnection = clientConnection;
		this.resources = resources;
		
		in = new BufferedReader(new InputStreamReader(clientConnection.getInputStream(), StandardCharsets.UTF_8));
		resources.communicateDebug("CREATED WRAPPER READER");
		out = new BufferedWriter(new OutputStreamWriter(clientConnection.getOutputStream(), StandardCharsets.UTF_8));
		resources.communicateDebug("CREATED WRAPPER WRITER");
		alive = true;
	}
	
	public void sendMessage(String s)
	{
		
		try
		{
			out.write(s);
			out.append('\n');
			out.flush();
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
					recievedMessage = in.readLine();
					resources.communicateDebug("CLIENT HAS SENT MESSAGE");
					resources.addMessage(recievedMessage);
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
}