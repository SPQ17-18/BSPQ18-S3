package videoclub.client.gui.paneles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import videoclub.server.gui.ICollector;
import videoclub.server.jdo.Mensaje;
import videoclub.server.jdo.Usuario;

public class PanelChat extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel usuario;
	private JButton btnEnviarMensaje;
	private JLabel lblNmeroDeUsuarios;
	public static JLabel usuariosEnLinea;
	private JLabel lblChatGlobal;

	private ICollector collector;
	private Usuario usuarioActual;
	List<Mensaje> arrayMensajes = new ArrayList<Mensaje>();
	private JTextField txtEscribirAquPara;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel tableModel = new DefaultTableModel();

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

		mostrarMensajesNuevos();
	}

	private void inicializar() {
		btnEnviarMensaje = new JButton("Enviar mensaje");
		usuariosEnLinea = new JLabel();
		usuario = new JLabel();
		lblChatGlobal = new JLabel("  CHAT GLOBAL");
		lblNmeroDeUsuarios = new JLabel("NÚMERO DE USUARIOS EN LÍNEA:");
		txtEscribirAquPara = new JTextField();
		table = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
				Component componenet = super.prepareRenderer(renderer, rowIndex, columnIndex);
				Object value = getModel().getValueAt(rowIndex, columnIndex);
				int posInicial = String.valueOf(value).indexOf(" ") + 1;
				int posFinal = String.valueOf(value).lastIndexOf(":");
				String user = String.valueOf(value).substring(posInicial, posFinal);

				if (user.equals(usuarioActual.getNombreUsuario())) {
					componenet.setBackground(Color.BLACK);
					componenet.setForeground(Color.GREEN);
				} else {
					componenet.setBackground(Color.BLACK);
					componenet.setForeground(SystemColor.textHighlight);
				}
				return componenet;
			}
		};
		scrollPane = new JScrollPane();
	}

	private void añadir() {
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		add(btnEnviarMensaje);
		add(usuario);
		add(lblChatGlobal);
		add(lblNmeroDeUsuarios);
		add(usuariosEnLinea);
		add(txtEscribirAquPara);
		add(scrollPane);

		scrollPane.setViewportView(table);
	}

	private void componentes() {
		table.setVerifyInputWhenFocusTarget(false);
		table.setRequestFocusEnabled(false);
		scrollPane.setBounds(10, 37, 994, 151);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setShowGrid(false);
		table.setRowSelectionAllowed(false);
		table.setForeground(SystemColor.textHighlight);
		table.setFont(new Font("Tahoma", Font.BOLD, 15));
		table.setFocusable(false);
		usuario.setForeground(SystemColor.textHighlight);
		usuario.setFont(new Font("Tahoma", Font.BOLD, 15));
		usuario.setText(usuarioActual.getNombreUsuario());
		usuario.setHorizontalAlignment(SwingConstants.RIGHT);
		txtEscribirAquPara.setForeground(Color.GREEN);
		txtEscribirAquPara.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtEscribirAquPara.setText("Escribir aquí para mandar un mensaje...");
		txtEscribirAquPara.setBounds(92, 194, 912, 35);
		txtEscribirAquPara.setColumns(10);
		btnEnviarMensaje.setForeground(SystemColor.textHighlight);
		btnEnviarMensaje.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnEnviarMensaje.setBounds(0, 235, 1016, 44);
		usuario.setBounds(0, 194, 80, 35);
		lblChatGlobal.setForeground(SystemColor.textHighlight);
		lblChatGlobal.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblChatGlobal.setBounds(0, 3, 117, 21);
		lblNmeroDeUsuarios.setForeground(Color.ORANGE);
		lblNmeroDeUsuarios.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNmeroDeUsuarios.setBounds(129, 3, 270, 21);
		usuariosEnLinea.setForeground(Color.GREEN);
		usuariosEnLinea.setFont(new Font("Tahoma", Font.BOLD, 15));
		usuariosEnLinea.setBounds(396, 3, 270, 21);
	}

	private void eventos() {
		btnEnviarMensaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Date hora = new Date();
					if (collector.setMensaje(new Mensaje(txtEscribirAquPara.getText(), hora, usuarioActual)) == true) {
						@SuppressWarnings("deprecation")
						Object[] mensaje = new Object[] {
								"[" + hora.getHours() + ":" + hora.getMinutes() + ":" + hora.getSeconds() + "] "
										+ usuarioActual.getNombreUsuario() + ": " + txtEscribirAquPara.getText() };

						collector.broadcastMessage(mensaje);
						txtEscribirAquPara.setText("");
						table.updateUI();
					} else {
						JOptionPane.showMessageDialog(null, "ERROR! MENSAJE NO ENVIADO!", "ERROR",
								JOptionPane.ERROR_MESSAGE);
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
		tableModel = new DefaultTableModel();
		columnasTabla();

		try {
			arrayMensajes = new ArrayList<Mensaje>();
			arrayMensajes = collector.obtenerMensajes(arrayMensajes);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// MOstramos mensajes:
		if (arrayMensajes.size() >= 0) {
			for (int i = 0; i < arrayMensajes.size(); i++) {
				tableModel.addRow(new Object[] { "[" + arrayMensajes.get(i).getFecha().getHours() + ":"
						+ arrayMensajes.get(i).getFecha().getMinutes() + ":"
						+ arrayMensajes.get(i).getFecha().getSeconds() + "] "
						+ arrayMensajes.get(i).getUsuario().getNombreUsuario() + ": "
						+ arrayMensajes.get(i).getMensaje() });
			}
		}
		// Introducimos el modelo en la tabla:
		table.setModel(tableModel);
	}

	private void columnasTabla() {
		// Creacion de las columnas de la tabla:
		tableModel.addColumn("CHAT - MENSAJES - DIRECTOS");
	}

	public void addNewMessage(Object[] mensaje) {
		tableModel.addRow(mensaje);
	}

}
