package chatengine;

import org.json.JSONObject;
/**
 * 
 * @author Christopher Madrigal
 * The Message class is an internal representation of the engine's message format.
 */
public class Message
{
	private final String content;
	private final String destination;
	
	/**
	 * Returns a message from a String containing only the content field of the message
	 * @param content The string that holds only the content of the message
	 * @return Message new Message object
	 */
	public static Message constructMessage(String content)
	{
		return new Message(new JSONObject().put("content", content).put("destination", "ALL").toString());
	}
	
	/**
	 * Creates a message from a JSON formatted string
	 * @param JSONSerialized the string containing the JSON data
	 */
	public Message(String JSONSerialized)
	{
		JSONObject jo = new JSONObject(JSONSerialized);
		content = jo.getString("content");
		destination = jo.getString("destination");
	}

	/**
	 * Returns the message's scope
	 * @return The string describing the messge's scope
	 */
	public String getDestination()
	{
		return destination;
	}

	/**
	 * Returns the content string of the message
	 * @return The string containing the message's content
	 */
	public String getContent()
	{
		return content;
	}
	
	/**
	 * Serializes the Message into a JSON format data
	 * @return the String containing the JSON data
	 */
	public String toString()
	{
		return new JSONObject().put("content", content).put("destination", destination).toString();
	}
	
	/**
	 * Returns a JSONObject described in the <a href="https://github.com/stleary/JSON-java">org.json</a> package
	 * @return JSONObject the JSONObject representation of the message
	 */
	public JSONObject toJSONObject()
	{
		return new JSONObject().put("content", content).put("destination", destination);
	}
}
