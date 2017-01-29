package chatengine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Client extends Thread
{
	private Chatable		parent;
	private Socket			connection;
	private BufferedReader 	in;
	private BufferedWriter 	out;
	private boolean 		shouldRun;
	
	public Client(String ip, int port, Chatable parent) throws UnknownHostException, IOException
	{
		shouldRun = true;
		connection = new Socket(ip, port);
		this.parent = parent;
		in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
		out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
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
					nextMessage = in.readLine();
					parent.onMessage(nextMessage);
				}
			}
			catch (IOException e)
			{
				parent.onError(e);
			}
		}
	}
	
	public void sendMessage(String message)
	{
		try
		{
			out.write(message);
			out.append('\n');
			out.flush();
		}
		catch (IOException e)
		{
			parent.onError(e);
		}
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
