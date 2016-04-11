import java.io.IOException;
import java.util.ArrayList;

public class Broadcaster extends Thread{
	
	private volatile ArrayList<MemberWrapper> members;
	private volatile ArrayList<Object> messageBuffer;
	private boolean shouldrun;
	
	public Broadcaster(ArrayList<MemberWrapper> clients)
	{
		this.members = clients;
		messageBuffer = new ArrayList<Object>(1);
		shouldrun = true;
		start();
	}
	
	public void postMessage(Object o)
	{
		messageBuffer.add(o);
	}
	
	public void run()
	{
		while(shouldrun)
		{
			
			if(messageBuffer.size() > 0)
			{
				Object brstm = messageBuffer.remove(0);
				for(MemberWrapper m : members)
				{
					try {
						m.send(brstm);
					} catch (IOException e) {
						members.remove(members.indexOf(m));
						e.printStackTrace();
					}
				}
			}
		}
		
		//ending code
		for(MemberWrapper m : members)
		{
			try {
				m.close();
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
