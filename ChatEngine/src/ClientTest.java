import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTest implements Chatable{

	String name;
	private volatile ChatClient client;
	
	public ClientTest(String IP, int port, String name) throws UnknownHostException, IOException
	{
		this.name = name;
		client = new ChatClient(this, IP, port);
	}
	
	@Override
	public void onMessage(Object message) {
		System.out.println((String)message);
	}
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		
		System.out.println("Type the IP address: ");
		String IP = in.next();
		
		System.out.println("Type the port: ");
		int port = in.nextInt();
		
		System.out.println("Type a name: ");
		String name = in.next();
		
		try {
			
			ClientTest t = new ClientTest(IP, port, name);
			
			while(true)
			{
				System.out.println("Type a Message: ");
				String message = in.next();
				t.send(message);
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
			in.close();
			
		}
	}

	private void send(String message) {

		client.sendMeesage(name + " sent " + message);
		
	}

}
