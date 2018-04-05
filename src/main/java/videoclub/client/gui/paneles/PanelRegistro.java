package videoclub.client.gui.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import videoclub.server.gui.ICollector;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Direccion;
import videoclub.server.jdo.Usuario;

public class PanelRegistro extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int anchuraPanel = 500;
	int alturaPanel = 300;
	private JTextField textFieldUser;
	private JPasswordField passwordFieldContra;
	private JButton btnRegistrase;
	public JButton btnVolver;
	private JTextField textFieldCorreo;
	private JTextField textFieldCalle;
	private JComboBox<String> comboBox;
	private JDateChooser dateChooser;
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JTextField textFieldCiudad;
	private ICollector collector; // Pasamos collector desde el "ClientFrame"

	/**
	 * Create the panel.
	 */
	public PanelRegistro(ICollector collector) {
		this.collector = collector;
		inicializar();
		componentes();
		añadirComponentes();
		eventos();
	}

	private void inicializar() {
		textFieldUser = new JTextField();
		passwordFieldContra = new JPasswordField();
		btnRegistrase = new JButton("REGISTRASE");
		btnVolver = new JButton("VOLVER");
		textFieldCorreo = new JTextField();
		textFieldCalle = new JTextField();
		comboBox = new JComboBox<String>();
		dateChooser = new JDateChooser();
		textFieldNombre = new JTextField();
		textFieldApellidos = new JTextField();
	}

	private void componentes() {
		textFieldUser.setForeground(SystemColor.textHighlight);
		textFieldUser.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textFieldUser.setColumns(10);
		textFieldUser.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Introduzca usuario",
				TitledBorder.CENTER, TitledBorder.TOP, null, SystemColor.textHighlight));
		textFieldUser.setBackground(Color.DARK_GRAY);
		textFieldUser.setBounds(12, 13, 234, 50);
		passwordFieldContra.setForeground(SystemColor.textHighlight);
		passwordFieldContra.setFont(new Font("Tahoma", Font.PLAIN, 25));
		passwordFieldContra.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Introduzca contrase\u00F1a", TitledBorder.CENTER, TitledBorder.TOP, null, SystemColor.textHighlight));
		passwordFieldContra.setBackground(Color.DARK_GRAY);
		passwordFieldContra.setBounds(12, 62, 234, 50);
		btnRegistrase.setForeground(SystemColor.textHighlight);
		btnRegistrase.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnRegistrase.setBorder(new LineBorder(SystemColor.textHighlight, 2));
		btnRegistrase.setBackground(Color.DARK_GRAY);
		btnRegistrase.setBounds(370, 223, 121, 35);
		btnVolver.setForeground(SystemColor.textHighlight);
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnVolver.setBorder(new LineBorder(SystemColor.textHighlight, 2));
		btnVolver.setBackground(Color.DARK_GRAY);
		btnVolver.setBounds(258, 223, 108, 35);
		textFieldCorreo.setForeground(SystemColor.textHighlight);
		textFieldCorreo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textFieldCorreo.setColumns(10);
		textFieldCorreo.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Introduzca correo electr\u00F3nico",
						TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 120, 215)));
		textFieldCorreo.setBackground(Color.DARK_GRAY);
		textFieldCorreo.setBounds(12, 111, 234, 50);
		textFieldCalle.setForeground(SystemColor.textHighlight);
		textFieldCalle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textFieldCalle.setColumns(10);
		textFieldCalle.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Introduzca calle",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 120, 215)));
		textFieldCalle.setBackground(Color.DARK_GRAY);
		textFieldCalle.setBounds(12, 160, 234, 50);
		dateChooser.setBorder(new LineBorder(SystemColor.textHighlight));
		dateChooser.setBounds(258, 66, 233, 35);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Espa\u00F1a", "Francia", "Alemania",
				"Holanda", "B\u00E9lgica", "Portugal", "Italia" }));
		comboBox.setBorder(new LineBorder(SystemColor.textHighlight));
		comboBox.setBounds(258, 19, 233, 35);
		textFieldNombre.setForeground(SystemColor.textHighlight);
		textFieldNombre.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textFieldNombre.setColumns(10);
		textFieldNombre.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Introduzca nombre",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 120, 215)));
		textFieldNombre.setBackground(Color.DARK_GRAY);
		textFieldNombre.setBounds(258, 111, 234, 50);
		textFieldApellidos.setForeground(SystemColor.textHighlight);
		textFieldApellidos.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textFieldApellidos.setColumns(10);
		textFieldApellidos.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Introduzca apellidos", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 120, 215)));
		textFieldApellidos.setBackground(Color.DARK_GRAY);
		textFieldApellidos.setBounds(257, 160, 234, 50);
	}

	private void añadirComponentes() {
		setSize(anchuraPanel, alturaPanel);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		setBorder(null);

		add(textFieldUser);
		add(passwordFieldContra);
		add(btnRegistrase);
		add(btnVolver);
		add(textFieldCorreo);
		add(textFieldCalle);
		add(dateChooser);
		add(comboBox);
		add(textFieldNombre);
		add(textFieldApellidos);

		textFieldCiudad = new JTextField();
		textFieldCiudad.setForeground(SystemColor.textHighlight);
		textFieldCiudad.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textFieldCiudad.setColumns(10);
		textFieldCiudad.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Introduzca ciudad",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 120, 215)));
		textFieldCiudad.setBackground(Color.DARK_GRAY);
		textFieldCiudad.setBounds(12, 208, 234, 50);
		add(textFieldCiudad);
	}

	private void eventos() {

		btnRegistrase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrarUsuario();
			}
		});
	}

	private void registrarUsuario() {
		String nombreUser = textFieldUser.getText();
		String contra = String.valueOf(passwordFieldContra.getPassword());
		String correo = textFieldCorreo.getText();

		if (nombreUser.equals("") && contra.equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUN NOMBRE DE USUARIO NI NINGUNA CONTRASEÑA.",
					"¡ERROR!", JOptionPane.ERROR_MESSAGE);
		} else if (nombreUser.equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUN NOMBRE DE USUARIO", "¡ERROR!",
					JOptionPane.ERROR_MESSAGE);
		} else if (nombreUser.equals("") && correo.equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUN NOMBRE DE USUARIO NI CORREO", "¡ERROR!",
					JOptionPane.ERROR_MESSAGE);
		} else if (contra.equals("") && correo.equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUNA CONTRASEÑA NI CORREO", "¡ERROR!",
					JOptionPane.ERROR_MESSAGE);
		} else if (contra.equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUNA CONTRASEÑA", "¡ERROR!",
					JOptionPane.ERROR_MESSAGE);
		} else if (correo.equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUN CORREO", "¡ERROR!",
					JOptionPane.ERROR_MESSAGE);
		} else if (nombreUser.equals("") && correo.equals("") && contra.equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUN NOMBRE DE USUARIO NI CORREO NI CONTRASEÑA",
					"¡ERROR!", JOptionPane.ERROR_MESSAGE);
		} else if (dateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUNA FECHA DE NACIMIENTO", "¡ERROR!",
					JOptionPane.ERROR_MESSAGE);
		} else if (textFieldCalle.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUNA CALLE", "¡ERROR!",
					JOptionPane.ERROR_MESSAGE);
		} else if (textFieldApellidos.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUN APELLIDO", "¡ERROR!",
					JOptionPane.ERROR_MESSAGE);
		} else if (textFieldCiudad.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUNA CIUDAD", "¡ERROR!",
					JOptionPane.ERROR_MESSAGE);
		} else if (textFieldNombre.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUN NOMBRE", "¡ERROR!",
					JOptionPane.ERROR_MESSAGE);
		} else {
			try {

				String nombre = textFieldNombre.getText();
				String apellidos = textFieldApellidos.getText();
				Date fechaNacimiento = dateChooser.getDate();
				String calle = textFieldCalle.getText();
				String ciudad = textFieldCiudad.getText();
				String pais = (String) comboBox.getSelectedItem();
				
				if (collector.registerUser(nombreUser, contra, correo, nombre, apellidos, fechaNacimiento, calle, ciudad, pais) == true) {
					JOptionPane.showMessageDialog(null, "ESE USUARIO YA ESTÁ REGISTRADO!", "¡ERROR!",
							JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "NUEVO USUARIO REGISTRADO EN LA PLATAFORMA VIDEOTHEK");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "ERROR AL CREAR USUARIO!", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
