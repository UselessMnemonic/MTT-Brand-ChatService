import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer<Format> extends Thread{
	
	private ArrayList<Socket> CLIENTS;
	private ArrayList<Format> pendingMessages;
	private ServerSocket self;
	private int port;
	private String addressAndSocket;
	private MemberHandler clientManager;
	private Broadcaster<Format> broadcaster;
	private ServerMessageListener<Format> listener;
	
	public ChatServer(int port) throws IOException
	{
		CLIENTS = new ArrayList<Socket>(1);
		this.port = port;
		self = new ServerSocket(port);
		addressAndSocket = self.toString();
		clientManager = new MemberHandler(CLIENTS, self);
		broadcaster = new Broadcaster<Format>(CLIENTS);
		listener = new ServerMessageListener<Format>(CLIENTS, broadcaster);
	}
	
	public void sendMessage(Format o)
	{
		broadcaster.postMessage(o);
	}

	public void shutdown() throws InterruptedException
	{
		clientManager.shutdown();
		broadcaster.shutdown();
		listener.shutdown();
	}
}
