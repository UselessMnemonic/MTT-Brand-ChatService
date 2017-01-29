package chatengine;

import java.io.IOException;
import java.net.UnknownHostException;

public class Tester implements ServerHandler, Chatable
{

	@Override
	public void onMessage(Message nextMessage)
	{
		System.out.println(nextMessage.getContent());
	}

	@Override
	public void onError(Exception e)
	{
		e.printStackTrace();
	}

	@Override
	public void onDebug(String debugString)
	{
		System.out.println(debugString);
	}

	@Override
	public void onDebug(Exception debugException)
	{
		debugException.printStackTrace();
	}

	@Override
	public void onSystemMessage(Message message)
	{
		System.out.println("++SYSTEM MESSAGE START++\n" + message.getContent() + "\n++SYSTEM MESSAGE END++");
	}
	
	public static void main(String[] args)
	{
		try {
			Tester test = new Tester();
			Server server = new Server(5545, test);
			Client c = new Client("localhost", 5545, test);
			c.sendMessage(Message.constructMessage("This is a test message."));
			while(true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
