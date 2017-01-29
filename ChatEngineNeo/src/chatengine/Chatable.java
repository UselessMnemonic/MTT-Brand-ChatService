package chatengine;

import java.io.IOException;

public interface Chatable
{
	public void onMessage(String message);

	public void onError(IOException e);
}
