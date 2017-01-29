package chatengine;

/**
 * 
 * @author Christopher Madrigal
 * <p>Interface designed for server hosts to communicate with server's involved steps 
 * in routing messages and debug information, including exception events.</p>
 */
public interface ServerHandler
{
	/**
	 * An action to take when a debug message is received.
	 * @param debugString The string containing a debug message
	 * @see ServerResourceManager
	 */
	void onDebug(String debugString);
	
	/**
	 * An action to take when an Exception is received.
	 * @param debugException The exception sent by an internal process
	 * @see ServerResourceManager
	 */
	void onDebug(Exception debugException);
	
	 /**
	  * An action to take when a client has sent a message addressed to the host.
	  * @param message Message received by host.
	  * @see ServerResourceManager
	  */
	void onSystemMessage(Message message);
}
