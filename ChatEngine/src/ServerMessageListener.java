import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMessageListener<Format> extends Thread{
	
	private ArrayList<Socket> members;
	private Broadcaster<Format> broadcaster;
	private boolean shouldrun;
	
	public ServerMessageListener (ArrayList<Socket> members, Broadcaster<Format> broadcaster)
	{
		this.members = members;
		this.broadcaster = broadcaster;
		shouldrun = true;
		start();
	}
	
	public void run()
	{
		while(shouldrun)
		{
			for(int i = 0; i < members.toArray().length; i++)
			{
				try {
					
					if(members.get(i).isClosed())
					{
					}
					else
					{
					ObjectInputStream in = new ObjectInputStream(members.get(i).getInputStream());
					if(in.available() > 0)
					{
						@SuppressWarnings("unchecked")
						Format o = (Format) in.readObject();
						broadcaster.postMessage(o);
					}
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			for(Socket s : members)
			{
				if(s.isClosed())
					members.remove(members.indexOf(s));
			}
		}
	}

	public void shutdown() throws InterruptedException
	{
		shouldrun = false;
	}

}
