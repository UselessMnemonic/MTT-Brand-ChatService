import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MemberHandler extends Thread{
	
	private volatile ArrayList<Object> messages;
	private volatile ServerSocket server;
	private volatile ArrayList<MemberWrapper> members;
	private boolean shouldrun;
	
	public MemberHandler(ArrayList<Object> messages, ServerSocket server)
	{
		this.messages = messages;
		this.server = server;
		shouldrun = true;
		start();
	}
	
	public void run()
	{
		while(shouldrun)
		{
			try {
				Socket joiningClient = server.accept();
				members.add(new MemberWrapper(joiningClient, messages));
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
