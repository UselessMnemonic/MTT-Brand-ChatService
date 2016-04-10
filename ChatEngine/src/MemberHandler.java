import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MemberHandler extends Thread{
	
	private volatile ArrayList<Socket> members;
	private volatile ServerSocket server;
	private boolean shouldrun;
	
	public MemberHandler(ArrayList<Socket> members, ServerSocket server)
	{
		this.members = members;
		this.server = server;
		shouldrun = true;
		start();
	}
	
	public void run()
	{
		Socket joiningClient;
		while(shouldrun)
		{
			try {
				joiningClient = server.accept();
				members.add(joiningClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void shutdown() throws InterruptedException
	{
		shouldrun = false;
	}

}
