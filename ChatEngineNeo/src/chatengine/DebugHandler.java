package chatengine;

public interface DebugHandler
{
	void onDebug(String debugString);
	void onDebug(Exception debugException);
}
