import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ClientWindow {

	private JFrame frame;
	private JTextArea textWindow;
	private JTextField textField;
	private final JButton btnNewButton = new JButton("Connect");
	private JTextField textField_1;
	private JTextField textField_2;

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
		frame.setBounds(100, 100, 616, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textWindow = new JTextArea();
		textWindow.setBounds(6, 6, 438, 228);
		frame.getContentPane().add(textWindow);
		
		textField = new JTextField();
		textField.setBounds(6, 246, 308, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		btnNewButton.setBounds(315, 246, 129, 26);
		frame.getContentPane().add(btnNewButton);
		
		textField_1 = new JTextField();
		textField_1.setBounds(456, 6, 130, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(456, 35, 130, 26);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
	}

}
