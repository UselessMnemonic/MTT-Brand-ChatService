package chatengine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Christopher Madrigal
 * The ClientManager class provides clients with a connection to the server.
 */
public class ClientManager extends Thread
{

	private ServerResourceManager resources;
	private ServerSocket incomingClientSocket;
	private boolean shouldRun;
	
	/**
	 * Creates a ClientManager object
	 * @param port The port to listen to for clients
	 * @param resources The ServerResourceManager object that provides access to the Client pool
	 */
	public ClientManager(int port, ServerResourceManager resources)
	{
		this.resources = resources;
		try
		{
			resources.communicateDebug("CREATING SERVER SOCKET");
			incomingClientSocket = new ServerSocket(port);
			resources.communicateDebug("SERVER SOCKET INSTANTIATED");
			shouldRun = true;
		} 
		catch (IOException e)
		{
			shouldRun = false;
			resources.communicateDebug(e);
		}
	}
	
	public void run()
	{
		Socket nextSocket;
		ClientWrapper nextClient;
		while(shouldRun)
		{
			try
			{
				resources.communicateDebug("WAITING FOR NEXT CLIENT");
				nextSocket = incomingClientSocket.accept();
				resources.communicateDebug("NEW CLIENT ACCEPTED");
				nextClient = new ClientWrapper(nextSocket, resources);
				resources.addClient(nextClient);
				resources.communicateDebug("ADDED CLIENT");
				nextClient.start();
				resources.communicateDebug("STARTED CLIENT");
			} catch (IOException e)
			{
				resources.communicateDebug(e);
			}
		}
	}

	/**
	 * Closes the server's client listening socket
	 */
	public void shutdown()
	{
		shouldRun = false;
		try
		{
			resources.communicateDebug("SHUTTING DOWN CLIENT MANAGER");
			incomingClientSocket.close();
		}
		catch (IOException e)
		{
			resources.communicateDebug(e);
		}
	}
}
