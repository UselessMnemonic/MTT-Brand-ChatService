import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
	
	private Socket server;
	private ClientMessageListener listener;
	private ClientMessageSender sender;
	
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
