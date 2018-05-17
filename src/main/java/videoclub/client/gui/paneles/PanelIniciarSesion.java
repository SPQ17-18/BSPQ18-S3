package videoclub.client.gui.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import videoclub.client.gui.ventanas.ClientFrame;
import videoclub.client.main.Client;
import videoclub.client.utiles.SwingWorkerProgress;
import videoclub.server.collector.ICollector;

public class PanelIniciarSesion extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int anchuraPanel = 500;
	int alturaPanel = 300;
	private JPasswordField passwordField;
	private JTextField textField;
	private JButton BotonAcceder;
	public JButton BotonRegistrarse;
	private JButton BotonContraseñaOlvidada;
	private ICollector collector; // Pasamos collector desde el "ClientFrame"

	/**
	 * Create the panel.
	 */
	public PanelIniciarSesion(ClientFrame frame, ICollector collector, Client cliente) {

		this.collector = collector;
		inicializar();
		componentes();
		añadirComponentes();
		eventos(frame, cliente);
	}

	private void inicializar() {
		textField = new JTextField();
		passwordField = new JPasswordField();
		BotonAcceder = new JButton("ACCEDER");
		BotonRegistrarse = new JButton("REGISTRARSE ");
		BotonContraseñaOlvidada = new JButton("CONTRASE\u00D1A OLVIDADA");

	}

	private void componentes() {

		textField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textField.setColumns(10);
		textField.setBounds(71, 29, 361, 60);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		passwordField.setBounds(71, 102, 361, 60);
		BotonAcceder.setForeground(SystemColor.textHighlight);
		BotonAcceder.setFont(new Font("Tahoma", Font.BOLD, 20));
		BotonAcceder.setBackground(Color.DARK_GRAY);
		BotonAcceder.setBounds(150, 175, 193, 35);
		BotonRegistrarse.setFont(new Font("Tahoma", Font.BOLD, 16));
		BotonRegistrarse.setBackground(Color.DARK_GRAY);
		BotonRegistrarse.setBounds(12, 223, 231, 40);
		BotonContraseñaOlvidada.setFont(new Font("Tahoma", Font.BOLD, 16));
		BotonContraseñaOlvidada.setBackground(Color.DARK_GRAY);
		BotonContraseñaOlvidada.setBounds(255, 223, 233, 40);
		textField.setBackground(Color.DARK_GRAY);
		textField.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Introduzca usuario",
				TitledBorder.CENTER, TitledBorder.TOP, null, SystemColor.textHighlight));
		passwordField.setBackground(Color.DARK_GRAY);
		passwordField.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Introduzca contrase\u00F1a", TitledBorder.CENTER, TitledBorder.TOP, null, SystemColor.textHighlight));
		BotonAcceder.setBorder(new LineBorder(SystemColor.textHighlight, 2));
		BotonRegistrarse.setBorder(new LineBorder(SystemColor.textHighlight, 2));
		BotonContraseñaOlvidada.setBorder(new LineBorder(SystemColor.textHighlight, 2));
		textField.setForeground(SystemColor.textHighlight);
		passwordField.setForeground(SystemColor.textHighlight);
		BotonRegistrarse.setForeground(SystemColor.textHighlight);
		BotonContraseñaOlvidada.setForeground(SystemColor.textHighlight);

	}

	private void añadirComponentes() {
		setSize(anchuraPanel, alturaPanel);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		setBorder(null);
		add(textField);
		add(passwordField);
		add(BotonAcceder);
		add(BotonRegistrarse);
		add(BotonContraseñaOlvidada);
	}

	private void eventos(ClientFrame frame, Client cliente) {

		BotonAcceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Carga de la base de datos:
				// Cargamos loader:
				new SwingWorkerProgress(e, comprobarCredenciales(), frame, cliente, collector).setVisible(true);
			}
		});
		BotonContraseñaOlvidada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
	}

	private String comprobarCredenciales() {
		String usuario = textField.getText();
		String contrasenya = String.valueOf(passwordField.getPassword());
		String dev = "TIPO_USUARIO";

		if (usuario.equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUN USUARIO.", "¡ERROR!",
					JOptionPane.ERROR_MESSAGE);
		} else if (contrasenya.equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUNA CONTRASEÑA.", "¡ERROR!",
					JOptionPane.ERROR_MESSAGE);
		} else {
			// ADMIN LOCAL SIN CONECTAR A BD:
			if (textField.getText().equals("ADMIN") && String.valueOf(passwordField.getPassword()).equals("12345")) {
				dev = "ADMIN";
				// USER LOCAL SIN CONECTAR A BD:
			} else
				try {
					if (collector.login(textField.getText(), String.valueOf(passwordField.getPassword())) == true) {
						dev = "USER";
					} else {
						JOptionPane.showMessageDialog(null, "USUARIO & CONTRASEÑA INCORRECTOS!.", "¡ERROR!",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
				}
		}

		return dev;
	}

}
