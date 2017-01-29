package chatengine;

import org.json.JSONObject;

public class Message
{
	private final String content;
	private final String destination;
	
	public static Message constructMessage(String content)
	{
		return new Message(new JSONObject().put("content", content).put("destination", "ALL").toString());
	}
	
	public Message(String JSONSerialized)
	{
		JSONObject jo = new JSONObject(JSONSerialized);
		content = jo.getString("content");
		destination = jo.getString("destination");
	}

	public String getDestination()
	{
		return destination;
	}

	public String getContent()
	{
		return content;
	}
	
	public String toString()
	{
		return new JSONObject().put("content", content).put("destination", destination).toString();
	}
	
	public JSONObject toJSONObject()
	{
		return new JSONObject().put("content", content).put("destination", destination);
	}
}
