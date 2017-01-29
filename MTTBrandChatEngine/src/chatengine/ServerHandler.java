package chatengine;

public interface ServerHandler
{
	void onDebug(String debugString);
	void onDebug(Exception debugException);
	void onSystemMessage(Message message);
}
