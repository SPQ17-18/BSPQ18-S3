package videoclub.client.gui.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import videoclub.server.collector.ICollector;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.Recomendacion;
import videoclub.server.jdo.Usuario;

public class ClientRecomendacionFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JButton btnNewButton;
	private JScrollPane scrollPane;
	private JLabel lblNa;
	private JLabel lblElijaUnaPelcula;
	private Usuario usuario;
	private String amigo;
	private ICollector collector;
	private List<Recomendacion> peliculasYaRecomendadas = new ArrayList<Recomendacion>();

	/**
	 * Create the frame.
	 */
	public ClientRecomendacionFrame(ICollector collector, Usuario usuario, String amigo) {
		this.collector = collector;
		this.amigo = amigo;
		this.usuario = usuario;
		try {
			this.peliculasYaRecomendadas = collector.obtenerRecomendaciones(peliculasYaRecomendadas);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inicializar();
		añadir();
		componentes();
		eventos();
		mostrarPeliculas();
	}

	private void inicializar() {
		lblElijaUnaPelcula = new JLabel("  ELIJA UNA PELÍCULA A RECOMENDAR PARA SU AMIGO: ");
		lblNa = new JLabel(this.amigo);
		scrollPane = new JScrollPane();
		table = new JTable();
		btnNewButton = new JButton("RECOMENDAR PELÍCULAS SELECCIONADAS");
		contentPane = new JPanel();
	}

	private void añadir() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(850, 500);
		setTitle("VENTANA DE PELÍCULAS A RECOMENDAR DEL USUARIO: " + this.usuario.getNombreUsuario());
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		getContentPane().add(lblElijaUnaPelcula);
		getContentPane().add(lblNa);
		getContentPane().add(scrollPane);
		getContentPane().add(btnNewButton);
		getContentPane().setBackground(Color.DARK_GRAY);

		scrollPane.setViewportView(table);
	}

	private void componentes() {
		lblElijaUnaPelcula.setBorder(new LineBorder(SystemColor.textHighlight, 1, true));
		lblElijaUnaPelcula.setForeground(SystemColor.textHighlight);
		lblElijaUnaPelcula.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblElijaUnaPelcula.setBounds(12, 13, 619, 32);
		lblNa.setForeground(Color.CYAN);
		lblNa.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNa.setBounds(446, 13, 185, 32);
		scrollPane.setBounds(12, 53, 820, 343);
		btnNewButton.setForeground(Color.GREEN);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setBounds(233, 411, 372, 41);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
	}

	private void eventos() {
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < table.getRowCount(); i++) {
					String titulo = table.getValueAt(i, 1).toString();
					Boolean checked = Boolean.valueOf(table.getValueAt(i, 5).toString());
					if (checked == true) {
						// Comprobamos antes si la pelicula a recomendar ya ha
						// sido recomendada:
						if (isRecomendada(titulo)) {
							JOptionPane.showMessageDialog(null,
									"La película: " + titulo + " ya ha sido recomendada antes!");
						} else {
							// Guardamos recomendación de la película para el
							// amigo:
							try {
								collector.setRecomendacion(usuario.getNombreUsuario(), amigo, arrayPeliculas.get(i));
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}

				JOptionPane.showMessageDialog(null, "PELICULAS RECOMENDADAS CON ÉXITO.");
				ClientRecomendacionFrame.this.dispose();
			}
		});
	}

	private boolean isRecomendada(String titulo) {
		boolean dev = false;
		for (Recomendacion r : peliculasYaRecomendadas) {
			if (r.getAmigo().getNombreUsuario().equals(amigo)
					&& r.getUsuario().getNombreUsuario().equals(usuario.getNombreUsuario())
					&& r.getPelicula().getNombre().equals(titulo)) {
				dev = true;
			}
		}
		return dev;
	}

	private List<Pelicula> arrayPeliculas;

	private void mostrarPeliculas() {
		DefaultTableModel model = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return ImageIcon.class;
				case 1:
					return Object.class;
				case 2:
					return Object.class;
				case 3:
					return Object.class;
				case 4:
					return Object.class;
				case 5:
					return Boolean.class;
				default:
					return Object.class;
				}
			}
		};

		table.setModel(model);

		model.addColumn("IMÁGEN");
		model.addColumn("TÍTULO");
		model.addColumn("GÉNERO");
		model.addColumn("AÑO");
		model.addColumn("DURACIÓN");
		model.addColumn("SELECCIONAR");

		// Centramos las columnas de la tabla menos la de la imágen y la del
		// checkBox!:
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

		// Vamos introduciendo las películas:
		arrayPeliculas = new ArrayList<Pelicula>();
		try {
			arrayPeliculas = collector.obtenerPeliculas(arrayPeliculas);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < arrayPeliculas.size(); i++) {
			model.addRow(new Object[] { getImageIconPelicula(arrayPeliculas.get(i).getImage()),
					arrayPeliculas.get(i).getNombre(), arrayPeliculas.get(i).getCategoria().getNombre(),
					arrayPeliculas.get(i).getAnyo(), arrayPeliculas.get(i).getDuracion(), false });
		}
	}

	/**
	 * Método para devolver automaticamente ya la ImageIcon!:
	 * 
	 * @param imagen
	 * @return
	 */
	private ImageIcon getImageIconPelicula(Imagen imagen) {
		ImageIcon dev = null;
		byte[] bytes = imagen.getImage();
		BufferedImage image = null;
		InputStream in = new ByteArrayInputStream(bytes);
		try {
			image = ImageIO.read(in);
			dev = new ImageIcon(image.getScaledInstance(195, 270, 0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dev;
	}
}
