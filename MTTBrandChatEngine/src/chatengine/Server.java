package chatengine;

/**
 * 
 * @author Christopher Madrigal
 * 
 * <p> The Server class is the front end of the server implementation of this chat service.</p>
 *
 */

public class Server
{
	private ServerResourceManager resources;
	private ClientManager clientManager;
	private Broadcaster broadcaster;
	
	/**
	 * Creates a new Server
	 * @param port Port for the Server to listen to
	 * @param debugHandler Responds to Debug, Exception, and System message events
	 * @see ServerHandler
	 */
	public Server(int port, ServerHandler debugHandler)
	{
		debugHandler.onDebug("SERVER OBJECT CREATED, BEGINING SUBMODULE CONSTRUCTION");
		resources = new ServerResourceManager(debugHandler);
		debugHandler.onDebug("RESOURCE MANAGER INSTANTIATED");
		clientManager = new ClientManager(port, resources);
		debugHandler.onDebug("CLIENT MANAGER INSTANTIADED");
		broadcaster = new Broadcaster(resources);
		debugHandler.onDebug("BROADCASTER INSTANTIATED");
		
		clientManager.start();
		debugHandler.onDebug("CLIENT MANAGER STARTED");
		broadcaster.start();
		debugHandler.onDebug("BROADCASTER STARTED");
	}
	
	 /**
	  * Allows host to send a message to all Clients.
	  * @param messageToSend Message to broadcast
	  * @see Message
	  */
	public void sendToClients(Message messageToSend)
	{
		resources.sendToClients(messageToSend);
	}

	/**
	 * Allows host to send message to specific client.
	 * @param clientID ID of client to send message to
	 * @param messageToSend Message to send to client
	 * 
	 * @see Message
	 * @see ClientWrapper
	 */
	public void sendToClient(String clientID, Message messageToSend)
	{
		resources.sendToClient(clientID, messageToSend);
	}
	
	/**
	 * Shuts down server.
	 * <p>Closes the client manager, broadcaster, and purges all connections, in that order.</p>
	 * 
	 * @see ClientManager
	 * @see Broadcaster
	 * @see ServerResourceManager
	 */
	public void shutdown()
	{
		clientManager.shutdown();
		resources.communicateDebug("CLIENT MANAGER SHUT DOWN");
		broadcaster.shutdown();
		resources.communicateDebug("BROADCASTER SHUT DOWN");
		resources.purge();
		resources.communicateDebug("RESORUCES PURGED");
	}
}