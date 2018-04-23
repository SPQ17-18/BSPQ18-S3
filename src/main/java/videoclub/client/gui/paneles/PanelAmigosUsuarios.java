package videoclub.client.gui.paneles;

import java.awt.Color;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import videoclub.client.gui.ventanas.ClientRecomendacionFrame;
import videoclub.client.utiles.UrlToImage;
import videoclub.server.gui.ICollector;
import videoclub.server.jdo.Amigo;
import videoclub.server.jdo.Usuario;

public class PanelAmigosUsuarios extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane scrollPane;
	private TableModel tableModel;
	private ICollector collector;
	private UrlToImage imgConectado;
	private UrlToImage imgDesconectado;
	private UrlToImage imgRecomendacion;
	private Usuario usuarioActual;
	public boolean tablaAmigosCargada = false;

	/**
	 * Create the panel.
	 */
	public PanelAmigosUsuarios(ICollector collector, String mostrar, Usuario usuarioActual) {
		this.collector = collector;
		this.imgConectado = new UrlToImage("http://icdn.pro/images/es/b/o/bola-verde-icono-8113-128.png");
		this.imgDesconectado = new UrlToImage(
				"http://www.clker.com/cliparts/j/N/m/m/d/2/glossy-red-icon-button-md.png");
		this.imgRecomendacion = new UrlToImage(
				"http://icons.iconarchive.com/icons/graphicloads/rounded-social-media/512/share-icon.png");
		this.usuarioActual = usuarioActual;
		inicializar();
		añadir();
		componentes();
		eventos();

		if (mostrar.equals("USUARIOS")) {
			mostrarUsuarios();
			tablaAmigosCargada = false;
		} else if (mostrar.equals("AMIGOS")) {
			mostrarAmigos();
			tablaAmigosCargada = true;
		}
	}

	private void inicializar() {
		table = new JTable();
		scrollPane = new JScrollPane();
	}

	private void añadir() {
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		add(scrollPane);
		scrollPane.setViewportView(table);
	}

	private void componentes() {
		scrollPane.setBounds(0, 0, 232, 279);
		table.setForeground(SystemColor.textHighlight);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	private void eventos() {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tablaUsuario == true) {
					// Obtenemos amigo.
					String u = (String) table.getValueAt(table.getSelectedRow(), 1);
					// Guardamos amigo:
					try {
						if (collector.setAmigo(usuarioActual.getNombreUsuario(), u) == true) {
							JOptionPane.showMessageDialog(null, "El usuario: " + u + " es ahora tu amigo.");
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					// Obtenemos amigo.
					String u = (String) table.getValueAt(table.getSelectedRow(), 1);
					ClientRecomendacionFrame frame = new ClientRecomendacionFrame(collector, usuarioActual, u);
					frame.setVisible(true);
				}
			}
		});
	}

	private void columnasTabla(int i) {
		// Creacion de las columnas de la tabla:
		tableModel = new TableModel(i);
	}

	private boolean tablaUsuario = true;

	public void mostrarUsuarios() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				columnasTabla(0);
				List<Usuario> arrayUsuarios = new ArrayList<Usuario>();
				List<Usuario> usuariosConectados = new ArrayList<Usuario>();
				try {
					arrayUsuarios = collector.obtenerUsuarios(arrayUsuarios);
					usuariosConectados = collector.obtenerUsuariosConectados();
					PanelChat.usuariosEnLinea.setText(Integer.toString(usuariosConectados.size()));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for (int i = 0; i < arrayUsuarios.size(); i++) {
					tableModel.addRow(new Object[] { isUsuarioConectado(arrayUsuarios.get(i), usuariosConectados),
							arrayUsuarios.get(i).getNombreUsuario() });
				}
				// Introducimos el modelo en la tabla:
				table.setModel(tableModel);
				table.getColumnModel().getColumn(0).setCellRenderer(new CellRendererImagen());
				table.getColumnModel().getColumn(0).setPreferredWidth(10);
				table.getColumnModel().getColumn(1).setPreferredWidth(210);
				tablaUsuario = true;
			}
		});
	}

	public void mostrarAmigos() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				columnasTabla(1);
				List<Amigo> arrayAmigos = new ArrayList<Amigo>();
				List<Usuario> usuariosConectados = new ArrayList<Usuario>();

				try {
					arrayAmigos = collector.obtenerAmigos(arrayAmigos);
					usuariosConectados = collector.obtenerUsuariosConectados();
					PanelChat.usuariosEnLinea.setText(Integer.toString(usuariosConectados.size()));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for (int i = 0; i < arrayAmigos.size(); i++) {
					// Recorremos todo el array en busca de los amigos del
					// usuario
					// actual:
					if (usuarioActual.getNombreUsuario().equals(arrayAmigos.get(i).getUsuario().getNombreUsuario())) {
						tableModel.addRow(
								new Object[] { isUsuarioConectado(arrayAmigos.get(i).getAmigo(), usuariosConectados),
										arrayAmigos.get(i).getAmigo().getNombreUsuario(), "RECOMENDAR" });
					}
				}
				// Introducimos el modelo en la tabla:
				table.setModel(tableModel);
				table.getColumnModel().getColumn(0).setCellRenderer(new CellRendererImagen());
				table.getColumnModel().getColumn(2).setCellRenderer(new CellRendererImagen());
				table.getColumnModel().getColumn(0).setPreferredWidth(10);
				table.getColumnModel().getColumn(1).setPreferredWidth(190);
				table.getColumnModel().getColumn(2).setPreferredWidth(20);
				tablaUsuario = false;
			}
		});
	}

	/**
	 * Método para comprobar qué usuarios están conectados y mostrar un indicativo
	 * de ello:
	 * 
	 * @param usuario
	 * @param usuariosConectados
	 * @return
	 */
	String isUsuarioConectado(Usuario usuario, List<Usuario> usuariosConectados) {
		String conectado = "N";
		for (int i = 0; i < usuariosConectados.size(); i++) {
			if (usuario.getNombreUsuario().equals(usuariosConectados.get(i).getNombreUsuario())) {
				conectado = "Y";
				break;
			}
		}
		return conectado;
	}

	// Clase interna Para agregar columnas:
	class TableModel extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

		public TableModel(int i) {
			if (i == 0) {
				addColumn("");
				addColumn("USUARIO");
			} else if (i == 1) {
				addColumn("");
				addColumn("AMIGO");
				addColumn("");
			}
		}
	}

	// Clase interna para agregar las imágenes a las tablas:
	class CellRendererImagen extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		// ----------------------------//
		public CellRendererImagen() {
			super.setHorizontalAlignment(JLabel.CENTER);
		}

		public void setValue(Object value) {
			if (value == null)
				setText("");
			else {
				if (value.toString() != null) {
					if (value.toString().equals("Y")) {
						setIcon(new ImageIcon(
								imgConectado.getImageIcon().getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH)));
					} else if (value.toString().equals("N")) {
						setIcon(new ImageIcon(imgDesconectado.getImageIcon().getImage().getScaledInstance(10, 10,
								Image.SCALE_SMOOTH)));
					} else if (value.toString().equals("RECOMENDAR")) {
						setIcon(new ImageIcon(imgRecomendacion.getImageIcon().getImage().getScaledInstance(20, 20,
								Image.SCALE_SMOOTH)));
					}
				}
			}
		}

	}
}
