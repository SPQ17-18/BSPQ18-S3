package videoclub.client.gui.paneles;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.Timer;

import videoclub.server.gui.ICollector;
import videoclub.server.jdo.Mensaje;
import videoclub.server.jdo.Usuario;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelChat extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel time;
	private JTextArea textAreaMensajes;
	private JButton btnEnviarMensaje;
	private JScrollPane scrollPane;
	private JLabel lblNmeroDeUsuarios;
	private JLabel UsuariosEnLinea;
	private JLabel lblChatGlobal;

	private ICollector collector;
	private Usuario usuarioActual;
	List<Mensaje> arrayMensajes = new ArrayList<Mensaje>();
	private JTextField txtEscribirAquPara;

	/**
	 * Create the panel.
	 */
	public PanelChat(ICollector collector, Usuario usuario) {
		
		this.collector = collector;
		this.usuarioActual = usuario;
		
		inicializar();
		añadir();
		componentes();
		eventos();

		// Ejecutamos comprobación de mensajes cada 1 segundo:
		Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarMensajesNuevos();
			}
		});

		timer.start();
	}

	private void inicializar() {
		scrollPane = new JScrollPane();
		textAreaMensajes = new JTextArea();
		btnEnviarMensaje = new JButton("Enviar mensaje");
		UsuariosEnLinea = new JLabel("0");
		time = new JLabel();
		lblChatGlobal = new JLabel("  CHAT GLOBAL");
		lblNmeroDeUsuarios = new JLabel("NÚMERO DE USUARIOS EN LÍNEA:");
		txtEscribirAquPara = new JTextField();
	}

	private void añadir() {
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		add(scrollPane);
		add(btnEnviarMensaje);
		add(time);
		add(lblChatGlobal);
		add(lblNmeroDeUsuarios);
		add(UsuariosEnLinea);
		add(txtEscribirAquPara);
		scrollPane.setViewportView(textAreaMensajes);
	}

	private void componentes() {
		time.setForeground(SystemColor.textHighlight);
		time.setFont(new Font("Tahoma", Font.BOLD, 15));
		time.setText("00:00:00");
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		txtEscribirAquPara.setForeground(Color.GREEN);
		txtEscribirAquPara.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtEscribirAquPara.setText("Escribir aquí para mandar un mensaje...");
		txtEscribirAquPara.setBounds(92, 194, 912, 35);
		txtEscribirAquPara.setColumns(10);
		scrollPane.setBounds(1, 28, 1015, 161);
		textAreaMensajes.setForeground(Color.ORANGE);
		textAreaMensajes.setFont(new Font("Monospaced", Font.BOLD, 13));
		textAreaMensajes.setBackground(Color.DARK_GRAY);
		btnEnviarMensaje.setForeground(SystemColor.textHighlight);
		btnEnviarMensaje.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnEnviarMensaje.setBounds(0, 235, 1016, 44);
		time.setBounds(0, 194, 80, 35);
		lblChatGlobal.setForeground(SystemColor.textHighlight);
		lblChatGlobal.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblChatGlobal.setBounds(0, 3, 117, 21);
		lblNmeroDeUsuarios.setForeground(Color.ORANGE);
		lblNmeroDeUsuarios.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNmeroDeUsuarios.setBounds(129, 3, 270, 21);
		UsuariosEnLinea.setForeground(Color.GREEN);
		UsuariosEnLinea.setFont(new Font("Tahoma", Font.BOLD, 15));
		UsuariosEnLinea.setBounds(396, 3, 270, 21);
	}

	private void eventos() {
		btnEnviarMensaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (collector.setMensaje(
							new Mensaje(txtEscribirAquPara.getText(), new Date(), usuarioActual)) == true) {
						JOptionPane.showMessageDialog(null, "Mensaje enviado correctamente :D");
					} else {
						JOptionPane.showMessageDialog(null, "ERROR! MENSAJE NO ENVIADO!");
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		txtEscribirAquPara.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtEscribirAquPara.setText("");
			}
		});
	}

	@SuppressWarnings("deprecation")
	private void mostrarMensajesNuevos() {
		try {
			arrayMensajes = new ArrayList<Mensaje>();
			arrayMensajes = collector.obtenerMensajes(arrayMensajes);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Inicializamos de nuevo todo el componente:
		textAreaMensajes = new JTextArea();
		textAreaMensajes.setFont(new Font("Monospaced", Font.BOLD, 13));
		textAreaMensajes.setForeground(Color.ORANGE);
		textAreaMensajes.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(textAreaMensajes);

		// MOstramos mensajes:
		if (arrayMensajes.size() >= 0) {
			for (int i = 0; i < arrayMensajes.size(); i++) {
				textAreaMensajes.append("[" + arrayMensajes.get(i).getFecha().getHours() + ":"
						+ arrayMensajes.get(i).getFecha().getMinutes() + ":"
						+ arrayMensajes.get(i).getFecha().getSeconds() + "] "
						+ arrayMensajes.get(i).getUsuario().getNombreUsuario() + ": "
						+ arrayMensajes.get(i).getMensaje() + "\n");
			}
		}
		
		Date d = new Date();
		time.setText(d.getHours()+":"+d.getMinutes()+":"+d.getSeconds());
	}
}
