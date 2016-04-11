import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer extends Thread{
	
	private volatile ArrayList<MemberWrapper> CLIENTS;
	private volatile ArrayList<Object> pendingMessages;
	private volatile ServerSocket self;
	private int port;
	private String addressAndSocket;
	private MemberHandler clientManager;
	private Broadcaster broadcaster;
	
	public ChatServer(int port) throws IOException
	{
		CLIENTS = new ArrayList<MemberWrapper>(1);
		this.port = port;
		self = new ServerSocket(port);
		addressAndSocket = self.toString();
		clientManager = new MemberHandler(pendingMessages, self);
		broadcaster = new Broadcaster(CLIENTS);
	}
	
	public void sendMessage(Object o)
	{
		broadcaster.postMessage(o);
	}

	public void shutdown() throws InterruptedException
	{
		clientManager.shutdown();
		broadcaster.shutdown();
	}
	
	public int getPort()
	{
		return port;
	}
	
	public ArrayList<Object> getCurrentMessageBuffer()
	{
		return pendingMessages;
	}
}
