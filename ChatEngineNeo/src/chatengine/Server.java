package chatengine;

public class Server
{
	private ServerResourceManager resources;
	private ClientManager clientManager;
	private Broadcaster broadcaster;
	public Server(int port)
	{
		resources = new ServerResourceManager();
		clientManager = new ClientManager(port, resources);
		broadcaster = new Broadcaster(resources);
		
		clientManager.start();
		broadcaster.start();
	}
	
	public Server(int port, DebugHandler debugHandler)
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