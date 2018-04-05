package videoclub.server.gui;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ServerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextArea textArea;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public ServerFrame() {

		inicializar();
		añadirComponentes();
		componentes();
		eventos();
	}
	
	private void inicializar()
	{
		contentPane = new JPanel();
		scrollPane = new JScrollPane();
		textArea = new JTextArea();		
	}
	
	private void añadirComponentes()
	{
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
		setTitle("SERVIDOR VIDEOCLUB");
		contentPane.setLayout(null);
		setContentPane(contentPane);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(textArea);
	}
	
	private void componentes()
	{
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		scrollPane.setBorder(new LineBorder(SystemColor.textHighlight, 3));
		scrollPane.setBounds(0, 0, 494, 265);
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.DARK_GRAY);
	}
	
	private void eventos()
	{
		
	}
}
