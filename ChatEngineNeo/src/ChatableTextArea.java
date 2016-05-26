import java.awt.TextArea;

public class ChatableTextArea extends TextArea implements Chatable{

	private boolean isFirstMessage;
	
	@Override
	public void onMessage(Object message) {
		if(isFirstMessage){
			isFirstMessage = false;
			setText(getText() + (String)message);
		}
		else
			setText(getText() + "\n" + (String)message);
	}

	public ChatableTextArea()
	{
		super();
		isFirstMessage = true;
		setEditable(false);
	}
	
}
