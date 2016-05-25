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

	public boolean hasNextMessage() {
		return pendingMessages.size() > 0;
	}

	public int messageListSize() {
		return pendingMessages.size();
	}

	public Object removeNextMessage() {
		return pendingMessages.remove(0);
	}

	public ClientWrapper getClient(int x) {
		return clients.get(0);
	}

	public void sendToClients(Object nextMessage) {
		new Thread(){ public void run(){
		Object[] currClient = clients.toArray();
		System.out.println("SERVER: Got Array...");
		for(Object cw : currClient)
		{
			try {
				((ClientWrapper)cw).sendMessage(nextMessage);
				System.out.println("SERVER: Sent to Client...");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		}}.start();
	}

	public Socket acceptNextClient() throws IOException {
		return self.accept();
	}

	public void addClient(ClientWrapper clientWrapper) {
		new Thread(){ public void run(){
		clients.add(clientWrapper);
		}}.start();
	}

	public void addMessage(Object incomingMessage) {
		Thread ugh = new Thread(){ public void run(){
		pendingMessages.add(incomingMessage);
		}};
		ugh.start();
	}

	public void removeClient(ClientWrapper clientWrapper) {
		new Thread(){ public void run(){
		clients.remove(clients.indexOf(clientWrapper));
		}}.start();
	}

	public void shutdown() {
		for(ClientWrapper cw : clients)
			cw.shutDown();
		try {
			self.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}