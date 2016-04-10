import java.io.IOException;
import java.net.UnknownHostException;

public class ChatService
{
	
	public static <Format> ChatServer<Format> getServer(int port) throws IOException
	{
		return new ChatServer<Format>(port);
	}
	
	public static <Format> ChatClient<Format> getChatClient(Chatable<Format> implementer, String ip, int port) throws UnknownHostException, IOException
	{
		return new ChatClient<Format>(implementer, ip, port);
	}

}
