package chatengine;

import java.util.ArrayList;

/**
 * 
 * @author Christopher Madrigal
 * <p>The ServerResourceManager object encapsulates all resource IO into a safe concurrency framework 
 * to facilitate data consistency between both producers and consumers of Clients and Messages</p>
 *
 */
public class ServerResourceManager
{
	private volatile ArrayList<ClientWrapper> 	clientPool; ///The ArrayList that contains all clients in service
	private volatile ArrayList<Message> 		messagePool; ///The ArrayList that contains messages in LIFO order
	private 		 ServerHandler				host; ///The ServerHandeler that responds to debug and exception events
	
	/**
	 * Constructs a new ServerResourceManager.
	 * @param host the ServerHandler that responds to debug info.
	 * @see ServerHandler
	 */
	public ServerResourceManager(ServerHandler host)
	{
		clientPool = new ArrayList<ClientWrapper>();
		messagePool = new ArrayList<Message>();
		this.host = host;
	}
	
	/**
	 * Adds a message to the message pool.
	 * @param messageToAdd The message to add to the message pool
	 * @see Message
	 */
	public synchronized void addMessage(Message messageToAdd)
	{
		communicateDebug("ADDING MESSAGE TO MESSAGE POOL");
		messagePool.add(messageToAdd);
	}
	
	/**
	 * Returns the state of the message pool.
	 * @return True if the messagePool has messages waiting.
	 */
	public boolean hasPendingMessage()
	{
		return !messagePool.isEmpty();
	}
	
	/**
	 * Broadcasts a message to all clients in the client pool.
	 * @param message The message to be sent to all clients.
	 * @see Message
	 */
	public synchronized void sendToClients(Message message)
	{
		for(ClientWrapper cw : clientPool)
		{
			new Thread(){
				public void run()
				{
					cw.sendMessage(message);
				}
			}.start();
			communicateDebug("MESSAGE BROADCAST THREAD SPAWNED");
		}
	}
	
	/**
	 * Sends a message to a specific client.
	 * @param clientID A client's internal client ID.
	 * @param messageToSend The message to send.
	 * @see Message
	 */
	public synchronized void sendToClient(String clientID, Message messageToSend)
	{
		for(ClientWrapper cw : clientPool)
		{
			if(cw.getClientID().equals(clientID))
			{
				new Thread(){
					public void run()
					{
						cw.sendMessage(messageToSend);
					}
				}.start();
				break;
			}
		}
	}
	
	/**
	 * @return The next message in the message pool.
	 * @throws ArrayIndexOutOfBoundsException if the underlying message pool is empty.
	 * @see Message
	 */
	public synchronized Message getNextPendingMessage() throws ArrayIndexOutOfBoundsException
	{
		communicateDebug("GOT NEXT PENDING MESSAGE");
		return messagePool.remove(0);
	}
	
	/**
	 * Adds a client to the client pool
	 * @param clientToAdd the ClientWrapper to add to the client.
	 * @see ClientWrapper
	 */
	public synchronized void addClient(ClientWrapper clientToAdd)
	{
		clientPool.add(clientToAdd);
		communicateDebug("CLIENT ADDED");
	}
	
	/**
	 * Purges the client pool of dead clients
	 * @see ClientWrapper
	 */
	public synchronized void cleanClientPool()
	{
		for(int i = clientPool.size()-1; i >= 0; i--)
		{
			if(!clientPool.get(i).isClientAlive())
			{
				clientPool.remove(i).close();
				communicateDebug("DEAD CLIENT FOUND AND REMOVED");
			}
		}
		communicateDebug("CLIENT POOL CLEANED");
	}

	/**
	 * Clears the client pool
	 * @see ClientWrapper
	 */
	public void purge()
	{
		for(ClientWrapper cw : clientPool)
		{
			cw.close();
			communicateDebug("CLOSED CLIENT");
		}
	}

	/**
	 * Sends an Exception to the Host
	 * @param e The Exception thrown by an inner mechanism
	 * @see Exception
	 * @see Exception
	 * @see ServerHandler
	 */
	public void communicateDebug(Exception e)
	{
		if(host != null)
			host.onDebug(e);
		else
			e.printStackTrace();
	}
	
	/**
	 * Sends a debug message to the host.
	 * @param debugMessage The string containing the message.
	 * @see ServerHandler
	 */
	public void communicateDebug(String debugMessage)
	{
		if(host != null)
			host.onDebug(debugMessage);
		else
			System.out.println(debugMessage);
	}

	/**
	 * Sends a message to the ServerHandler
	 * @see ServerHandler
	 * @param systemMessage The message that is to be sent to the host
	 */
	public void communicateSystemMessage(Message systemMessage)
	{
		if(host != null)
			host.onSystemMessage(systemMessage);
		else
			System.out.println("++START SYSTEM MESSAGE++\n" + systemMessage.getContent() + "++END SYSTEM MESSAGE++");
	}
}