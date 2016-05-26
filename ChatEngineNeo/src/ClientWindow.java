import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientWindow {

	private JFrame frame;
	private ChatableTextArea textWindow;
	private JTextField input;
	private final JButton actionBttn = new JButton("Connect");
	private JTextField port;
	private JTextField ip;
	private JTextField username;
	private Client client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow window = new ClientWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if(client != null)
					try {
						client.sendMessage(username.getText() + " has disconnected.");
						client.shutdown();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		});
		frame.setBounds(100, 100, 600, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textWindow = new ChatableTextArea();
		textWindow.setBounds(6, 6, 438, 228);
		frame.getContentPane().add(textWindow);
		
		input = new JTextField();
		input.setBounds(6, 246, 308, 26);
		frame.getContentPane().add(input);
		input.setColumns(10);
		actionBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(actionBttn.getText().equals("Connect"))
				{
					actionBttn.setText("Send");
					try {
						client = new Client(ip.getText(), port.getText(), textWindow);
					} catch (NumberFormatException e1) {
						textWindow.onMessage("@Error: Check port setting!");
						actionBttn.setText("Connect");
					} catch (UnknownHostException e1) {
						textWindow.onMessage("@Error: Check that the IP is correct!");
						actionBttn.setText("Connect");
					} catch (IOException e1) {
						textWindow.onMessage("@Error: Couldn't connect!");
						actionBttn.setText("Connect");
					}
				}
				else
				{
					try {
						client.sendMessage(username.getText() + ": " + input.getText());
						input.setText("");
					} catch (IOException e1) {
						textWindow.onMessage("@Error: Coudln't send!");
					}
				}
			}
		});
		actionBttn.setBounds(315, 246, 129, 26);
		frame.getContentPane().add(actionBttn);
		
		port = new JTextField();
		port.setText("9799");
		port.setToolTipText("port");
		port.setBounds(456, 6, 130, 26);
		frame.getContentPane().add(port);
		port.setColumns(10);
		
		ip = new JTextField();
		ip.setText("ip");
		ip.setHorizontalAlignment(SwingConstants.CENTER);
		ip.setToolTipText("ip");
		ip.setBounds(456, 35, 130, 26);
		frame.getContentPane().add(ip);
		ip.setColumns(10);
		
		username = new JTextField();
		username.setText("username");
		username.setBounds(456, 64, 130, 26);
		frame.getContentPane().add(username);
		username.setColumns(10);
	}
}
