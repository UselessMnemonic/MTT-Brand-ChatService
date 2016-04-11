import java.io.IOException;
import java.net.UnknownHostException;

public class ChatService
{
	
	public static ChatServer getServer(int port) throws IOException
	{
		return new ChatServer(port);
	}
	
	public static ChatClient getChatClient(Chatable implementer, String ip, int port) throws UnknownHostException, IOException
	{
		return new ChatClient(implementer, ip, port);
	}

}
