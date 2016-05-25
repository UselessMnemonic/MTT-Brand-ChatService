import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerResourceManager {
	
	private volatile ArrayList<ClientWrapper> clients;
	private volatile ArrayList<Object> pendingMessages;
	private volatile ServerSocket self;
	
	public ServerResourceManager(int port) throws IOException
	{
		 System.out.println("SERVER: Initializing client list...");
		 clients = new ArrayList<ClientWrapper>();
		 System.out.println("SERVER: Initializing message list...");
		 pendingMessages = new ArrayList<Object>();
		 System.out.println("SERVER: Initializing server socket...");
		 self = new ServerSocket(port);
		 System.out.println("SERVER: Initializing Client Manager...");
	}

	public synchronized boolean hasNextMessage() {
		return pendingMessages.size() > 0;
	}

	public synchronized int messageListSize() {
		return pendingMessages.size();
	}

	public synchronized Object removeNextMessage() {
		return pendingMessages.remove(0);
	}

	public synchronized ClientWrapper getClient(int x) {
		return clients.get(0);
	}

	public synchronized void sendToClients(Object nextMessage) {
		ArrayList<ClientWrapper> currClient = new ArrayList<ClientWrapper>();
		for(ClientWrapper cw : clients)
		{
			currClient.add(cw);
		}
		for(ClientWrapper cw : currClient)
		{
			try {
				cw.sendMessage(nextMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized Socket acceptNextClient() throws IOException {
		return self.accept();
	}

	public synchronized void addClient(ClientWrapper clientWrapper) {
		clients.add(clientWrapper);
	}

	public synchronized void addMessage(Object incomingMessage) {
		pendingMessages.add(incomingMessage);
	}

	public synchronized void removeClient(ClientWrapper clientWrapper) {
		clients.remove(clients.indexOf(clientWrapper));
	}

	public synchronized void shutdown() {
		for(ClientWrapper cw : clients)
			cw.shutDown();
		try {
			self.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
