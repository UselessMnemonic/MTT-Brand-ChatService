package chatengine;

import java.io.IOException;

public interface Chatable
{
	public void onMessage(Message nextMessage);

	public void onError(IOException e);
}
