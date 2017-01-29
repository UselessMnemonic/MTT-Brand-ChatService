package chatengine;

import java.util.ArrayList;

public class ServerResourceManager
{
	private volatile ArrayList<ClientWrapper> 	clientPool;
	private volatile ArrayList<String> 			messagePool;
	private 		 DebugHandler				debug;
	
	public ServerResourceManager()
	{
		clientPool = new ArrayList<ClientWrapper>();
		messagePool = new ArrayList<String>();
		debug = null;
	}
	
	public ServerResourceManager(DebugHandler debug)
	{
		this();
		this.debug = debug;
	}
	
	public synchronized void addMessage(String messageToAdd)
	{
		communicateDebug("ADDING MESSAGE TO MESSAGE POOL");
		messagePool.add(messageToAdd);
	}
	
	public boolean hasPendingMessage()
	{
		return !messagePool.isEmpty();
	}
	
	public synchronized void sendToClients(String message)
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
	
	public synchronized String getNextPendingMessage()
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
		if(debug != null)
			debug.onDebug(e);
		else
			e.printStackTrace();
	}
	
	public void communicateDebug(String debugMessage)
	{
		if(debug != null)
			debug.onDebug(debugMessage);
		else
			System.out.println(debugMessage);
	}
}