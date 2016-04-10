import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Broadcaster<Format> extends Thread{
	
	private volatile ArrayList<Socket> members;
	private volatile ArrayList<Format> messageBuffer;
	private boolean shouldrun;
	
	public Broadcaster(ArrayList<Socket> members)
	{
		this.members = members;
		messageBuffer = new ArrayList<Format>(1);
		shouldrun = true;
		start();
	}
	
	public void postMessage(Format o)
	{
		messageBuffer.add(o);
	}
	
	public void run()
	{
		Format brstm;
		while(shouldrun)
		{
			
			if(messageBuffer.size() > 0)
			{
				brstm = messageBuffer.remove(0);
				
				for(Socket s : members)
				{
					try {
						
						if(s.isClosed()){
							members.remove(members.indexOf(s));
						}
						else
						{
						ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
						out.writeObject(brstm);
						out.flush();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		//ending code
		for(Socket s : members)
		{
			try {
				s.close();
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
