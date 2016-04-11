import java.io.IOException;
import java.util.Scanner;

public class ServerTest {

	private ChatServer server;
	
	public ServerTest(int port) throws IOException
	{
		server = ChatService.getServer(port);
	}
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		
		System.out.println("Type the port: ");
		int port = in.nextInt();
		
		try {
			ServerTest t = new ServerTest(port);
			
			while(true)
			{
				System.out.println("Type a message: ");
				
				String serverMsg = in.next();
				
				t.send(serverMsg);
			}
		} catch (IOException e) {
			e.printStackTrace();
			in.close();
		}
	}

	private void send(String serverMsg) {
		server.sendMessage(serverMsg);
	}
}
