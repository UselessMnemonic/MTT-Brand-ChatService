package chatengine;

import java.util.ArrayList;

public class ServerResourceManager
{
	private volatile ArrayList<ClientWrapper> 	clientPool;
	private volatile ArrayList<Message> 		messagePool;
	private 		 ServerHandler				host;
	
	public ServerResourceManager()
	{
		clientPool = new ArrayList<ClientWrapper>();
		messagePool = new ArrayList<Message>();
		host = null;
	}
	
	public ServerResourceManager(ServerHandler host)
	{
		this();
		this.host = host;
	}
	
	public synchronized void addMessage(Message messageToAdd)
	{
		communicateDebug("ADDING MESSAGE TO MESSAGE POOL");
		messagePool.add(messageToAdd);
	}
	
	public boolean hasPendingMessage()
	{
		return !messagePool.isEmpty();
	}
	
	public synchronized void sendToClients(Message message)
	{
		for(ClientWrapper cw : clientPool)
		{
			new Thread(){
				public void run()
				{
					cw.sendMessage(message.toString());
				}
			}.start();
			communicateDebug("MESSAGE BROADCAST THREAD SPAWNED");
		}
	}
	
	public synchronized void sendToClient(String clientID, Message messageToSend)
	{
		for(ClientWrapper cw : clientPool)
		{
			if(cw.getClientID().equals(clientID))
			{
				new Thread(){
					public void run()
					{
						cw.sendMessage(messageToSend.toString());
					}
				}.start();
				break;
			}
		}
	}
	
	public synchronized Message getNextPendingMessage()
	{
		communicateDebug("GOT NEXT PENDING MESSAGE");
		return messagePool.remove(0);
	}
	
	public synchronized void addClient(ClientWrapper clientToAdd)
	{
		clientPool.add(clientToAdd);
		communicateDebug("CLIENT ADDED");
	}
	
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

	public void purge()
	{
		for(ClientWrapper cw : clientPool)
		{
			cw.close();
			communicateDebug("CLOSED CLIENT");
		}
	}

	public void communicateDebug(Exception e)
	{
		if(host != null)
			host.onDebug(e);
		else
			e.printStackTrace();
	}
	
	public void communicateDebug(String debugMessage)
	{
		if(host != null)
			host.onDebug(debugMessage);
		else
			System.out.println(debugMessage);
	}

	public void communicateSystemMessage(Message systemMessage)
	{
		if(host != null)
			host.onSystemMessage(systemMessage);
		else
			System.out.println("++START SYSTEM MESSAGE++\n" + systemMessage.getContent() + "++END SYSTEM MESSAGE++");
	}
}