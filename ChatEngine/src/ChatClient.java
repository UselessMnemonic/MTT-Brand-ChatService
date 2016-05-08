import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
	
	private volatile Socket server;
	private volatile ClientMessageListener listener;
	private volatile ClientMessageSender sender;
	
	public ChatClient(Chatable client, String IP, int port) throws UnknownHostException, IOException
	{
		server = new Socket(IP, port);
		System.out.println("Socket connected");
		listener = new ClientMessageListener(server, client);
		sender = new ClientMessageSender(server);
	}
	
	public void sendMeesage(Object o)
	{
		sender.sendMessage(o);
	}

	public void shutdown()
	{
		sender.shutdown();
		listener.shutdown();
	}

}
