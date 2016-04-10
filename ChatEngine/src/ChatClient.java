import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient<Format> {
	
	private Socket server;
	private ClientMessageListener<Format> listener;
	private ClientMessageSender<Format> sender;
	
	public ChatClient(Chatable<Format> client, String IP, int port) throws UnknownHostException, IOException
	{
		server = new Socket(IP, port);
		listener = new ClientMessageListener<Format>(server, client);
		sender = new ClientMessageSender<Format>(server);
	}
	
	public void sendMeesage(Format o)
	{
		sender.sendMessage(o);
	}

	public void shutdown()
	{
		sender.shutdown();
		listener.shutdown();
	}

}
