import java.awt.TextArea;

public class ChatableTextArea extends TextArea implements Chatable{

	@Override
	public void onMessage(Object message) {
		setText(getText() + "\n@" + new java.util.Date() + ": " + (String)message);
	}

	public ChatableTextArea()
	{
		super();
		setEditable(false);
	}
	
}
