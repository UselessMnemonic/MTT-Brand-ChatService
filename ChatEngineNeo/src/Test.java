import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.TextArea;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Test implements Chatable{

	private JFrame frmTestWindow;
	private JTextField textField;
	private JTextField ipField;
	private JTextField portField;
	private Server server;
	private Client client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test window = new Test();
					window.frmTestWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Test thisInstance()
	{
		return this;
	}

	/**
	 * Create the application.
	 */
	public Test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTestWindow = new JFrame();
		frmTestWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					server.shutdown();
					client.shutdown();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		frmTestWindow.setTitle("Test Window");
		frmTestWindow.setBounds(100, 100, 725, 476);
		frmTestWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTestWindow.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(265, 381, 345, 46);
		frmTestWindow.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton dualMode = new JButton("Start");
		dualMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
				if (dualMode.getText().equals("Start"))
				{
					startService();
					dualMode.setText("Send");
					ipField.setEditable(false);
					portField.setEditable(false);
				}
				else
				{
					try {
						client.sendMessage(textField.getText());
					} catch (IOException e) {
						e.printStackTrace();
					}
					textField.setText("");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}});
		dualMode.setBounds(620, 381, 79, 46);
		frmTestWindow.getContentPane().add(dualMode);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(10, 10, 689, 365);
		frmTestWindow.getContentPane().add(textArea);
		
		ipField = new JTextField();
		ipField.setHorizontalAlignment(SwingConstants.CENTER);
		ipField.setText("localhost");
		ipField.setName("ipField");
		ipField.setToolTipText("ip");
		ipField.setBounds(10, 381, 164, 46);
		frmTestWindow.getContentPane().add(ipField);
		ipField.setColumns(10);
		
		portField = new JTextField();
		portField.setHorizontalAlignment(SwingConstants.CENTER);
		portField.setText("9799");
		portField.setToolTipText("port");
		portField.setBounds(182, 381, 73, 46);
		frmTestWindow.getContentPane().add(portField);
		portField.setColumns(10);
	}

	protected void startService() throws NumberFormatException, UnknownHostException, IOException {
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					server = new Server(portField.getText());
					client = new Client(ipField.getText() , portField.getText(), thisInstance());
				} catch (NumberFormatException | IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void onMessage(Object message) {
		textField.setText(textField.getText() + "\n@" + new java.util.Date() + ": " + (String)message);
	}
}
