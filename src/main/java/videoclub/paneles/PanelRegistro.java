package videoclub.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;


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
	//private JDateChooser dateChooser;
	private JTextField textFieldNombre;
	private JTextField textFieldApellidos;
	private JTextField textFieldCiudad;

	/**
	 * Create the panel.
	 */
	public PanelRegistro() {
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
		//dateChooser = new JDateChooser();
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
		//dateChooser.setBorder(new LineBorder(SystemColor.textHighlight));
		//dateChooser.setBounds(258, 66, 233, 35);
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
		//add(dateChooser);
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

			}
		});

	}
}
