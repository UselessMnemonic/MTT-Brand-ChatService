import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;

public class ServerWindow {

	private JFrame frame;
	private JTextField ip;
	private JTextField port;
	private JButton startBttn;
	private Server server;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow window = new ServerWindow();
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
	public ServerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 228, 105);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		ip = new JTextField();
		ip.setEnabled(false);
		ip.setEditable(false);
		ip.setBounds(6, 6, 158, 26);
		frame.getContentPane().add(ip);
		ip.setColumns(10);
		
		port = new JTextField();
		port.setText("9799");
		port.setBounds(166, 6, 54, 26);
		frame.getContentPane().add(port);
		port.setColumns(10);
		
		startBttn = new JButton("START");
		startBttn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(startBttn.getText().equals("START"))
				{
					try {
						startServer();
						startBttn.setText("STOP");
						port.setEditable(false);
					} catch (NumberFormatException e2) {
						ip.setText("Check port setting!");
					} catch (IOException e1) {
						ip.setText("IO ERROR!");
					}
				}
				else
				{
					startBttn.setText("START");
					port.setEditable(true);
					ip.setText("");
					try {
						stopServer();
					} catch (IOException e1) {
						ip.setText("Something happened...");
					}
				}
			}
		});
		startBttn.setBounds(6, 35, 214, 42);
		frame.getContentPane().add(startBttn);
	}

	protected void stopServer() throws IOException {
		server.shutdown();
	}

	protected void startServer() throws NumberFormatException, IOException {
		server = new Server(port.getText());
		ip.setText(InetAddress.getLocalHost().getHostAddress());
	}
}
