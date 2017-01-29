package chatengine;

/**
 * 
 * @author Christopher Madrigal
 * <p>Interface designed for clients to accept messages and exception events.</p>
 */
public interface Chatable
{
	/**
	 * Action to take when a message has been received
	 * @param nextMessage The message received by the server
	 */
	public void onMessage(Message nextMessage);

	/**
	 * Action to take when an internal error is thrown
	 * @param e Exception thrown from inner mechanism
	 */
	public void onError(Exception e);
}
