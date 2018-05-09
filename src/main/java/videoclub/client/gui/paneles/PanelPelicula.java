package videoclub.client.gui.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import videoclub.client.gui.ventanas.ClientAlquilerFrame;
import videoclub.client.gui.ventanas.ClientOpinionesFrame;
import videoclub.client.gui.ventanas.ClientPeliculaFrame;
import videoclub.server.collector.ICollector;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.PeliculaVista;
import videoclub.server.jdo.Usuario;

/**
 * Panel para ver los detalles de las películas:
 */
public class PanelPelicula extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblImagenPelicula;
	private JLabel labelTitulo;
	private JLabel labelDuracion;
	private JLabel labelAño;
	private JLabel labelCategoria;
	private JLabel labelDisponibles;
	private JLabel labelPrecio;
	private JButton btnalquilarYaMismo;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JScrollPane scrollPane;
	private JTextPane textPaneDescripcion;

	private ICollector collector; // Collector implementado desde "ClienfFrame"
	private Cliente clienteActual;
	private Pelicula peliculaAAlquilar;
	private Pelicula peliculaAVer;
	private boolean peliculaAlquiladaAVer;
	private JButton btnverPelculaAhora;
	private JButton btnTrailer;
	private JButton btnVerLasOpiniones;
	private JButton btnOpinarPelcula;

	/**
	 * Create the panel.
	 */
	public PanelPelicula(ICollector collector, Cliente cliente, Pelicula pelicula, boolean peliculaAlquiladaAVer) {
		this.peliculaAlquiladaAVer = peliculaAlquiladaAVer;
		this.collector = collector;
		this.clienteActual = cliente;
		this.peliculaAAlquilar = pelicula;
		this.peliculaAVer = pelicula;
		inicializar();
		añadir();
		componentes();
		eventos();

		comprobarPeliculaAlquilada();
	}

	private void inicializar() {
		lblImagenPelicula = new JLabel();
		labelDuracion = new JLabel();
		labelAño = new JLabel();
		labelTitulo = new JLabel();
		labelCategoria = new JLabel();
		labelDisponibles = new JLabel();
		labelPrecio = new JLabel();
		btnalquilarYaMismo = new JButton("¡ALQUILAR YA MISMO!");
		label = new JLabel();
		label_1 = new JLabel();
		label_2 = new JLabel();
		label_3 = new JLabel();
		label_4 = new JLabel();
		label_5 = new JLabel();
		scrollPane = new JScrollPane();
		textPaneDescripcion = new JTextPane();
		btnverPelculaAhora = new JButton("¡VER PELÍCULA AHORA¡");
		btnTrailer = new JButton("TRAILER");
		btnOpinarPelcula = new JButton("Opinar pelÌcula");
		btnVerLasOpiniones = new JButton("Ver las opiniones de la pelÌcula");
	}

	private void añadir() {
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		add(lblImagenPelicula);
		add(labelTitulo);
		add(labelDuracion);
		add(labelAño);
		add(labelCategoria);
		add(labelDisponibles);
		add(labelPrecio);
		add(btnalquilarYaMismo);
		add(label);
		add(label_1);
		add(label_2);
		add(label_3);
		add(label_4);
		add(label_5);
		add(scrollPane);
		add(btnverPelculaAhora);
		add(btnTrailer);
		add(btnOpinarPelcula);
		add(btnVerLasOpiniones);
		scrollPane.setViewportView(textPaneDescripcion);
	}

	private void componentes() {
		btnOpinarPelcula.setForeground(Color.GREEN);
		btnOpinarPelcula.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnOpinarPelcula.setBounds(207, 91, 265, 25);
		btnVerLasOpiniones.setForeground(Color.GREEN);
		btnVerLasOpiniones.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnVerLasOpiniones.setBounds(484, 91, 532, 25);
		btnTrailer.setForeground(Color.CYAN);
		btnTrailer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTrailer.setContentAreaFilled(false);
		btnTrailer.setBorder(new LineBorder(Color.CYAN));
		btnTrailer.setBounds(850, 0, 163, 34);
		btnverPelculaAhora.setForeground(Color.GREEN);
		btnverPelculaAhora.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnverPelculaAhora.setContentAreaFilled(false);
		btnverPelculaAhora.setBorder(new LineBorder(Color.GREEN));
		btnverPelculaAhora.setBounds(207, 0, 806, 34);
		lblImagenPelicula.setBorder(new LineBorder(SystemColor.textHighlight));
		lblImagenPelicula.setBounds(0, 0, 195, 277);
		labelTitulo.setForeground(Color.WHITE);
		labelTitulo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelTitulo.setBorder(null);
		labelTitulo.setBounds(283, 47, 189, 31);
		labelDuracion.setForeground(Color.WHITE);
		labelDuracion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelDuracion.setBorder(null);
		labelDuracion.setBounds(735, 47, 81, 31);
		labelAño.setForeground(Color.WHITE);
		labelAño.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelAño.setBorder(null);
		labelAño.setBounds(536, 47, 81, 31);
		labelCategoria.setForeground(Color.WHITE);
		labelCategoria.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelCategoria.setBorder(null);
		labelCategoria.setBounds(905, 47, 111, 31);
		labelDisponibles.setText("100");
		labelDisponibles.setHorizontalAlignment(SwingConstants.CENTER);
		labelDisponibles.setForeground(Color.CYAN);
		labelDisponibles.setFont(new Font("Tahoma", Font.BOLD, 15));
		labelDisponibles.setBorder(null);
		labelDisponibles.setBounds(312, 0, 139, 34);
		labelPrecio.setText("0 €");
		labelPrecio.setHorizontalAlignment(SwingConstants.CENTER);
		labelPrecio.setForeground(Color.GREEN);
		labelPrecio.setFont(new Font("Tahoma", Font.BOLD, 15));
		labelPrecio.setBorder(null);
		labelPrecio.setBounds(536, 0, 67, 34);
		btnalquilarYaMismo.setForeground(Color.GREEN);
		btnalquilarYaMismo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnalquilarYaMismo.setContentAreaFilled(false);
		btnalquilarYaMismo.setBorder(new LineBorder(Color.GREEN));
		btnalquilarYaMismo.setBounds(614, 0, 224, 34);
		label.setText("  TÍTULO");
		label.setForeground(SystemColor.textHighlight);
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		label.setBorder(new LineBorder(SystemColor.textHighlight));
		label.setBounds(207, 47, 265, 31);
		label_1.setText("  AÑO");
		label_1.setForeground(SystemColor.textHighlight);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_1.setBorder(new LineBorder(SystemColor.textHighlight));
		label_1.setBounds(484, 47, 133, 31);
		label_2.setText("  DURACIÓN");
		label_2.setForeground(SystemColor.textHighlight);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_2.setBorder(new LineBorder(SystemColor.textHighlight));
		label_2.setBounds(627, 47, 189, 31);
		label_3.setText("  GÉNERO");
		label_3.setForeground(SystemColor.textHighlight);
		label_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_3.setBorder(new LineBorder(SystemColor.textHighlight));
		label_3.setBounds(827, 47, 187, 31);
		label_4.setText("  DISPONIBLES");
		label_4.setForeground(new Color(199, 21, 133));
		label_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_4.setBorder(new LineBorder(new Color(199, 21, 133)));
		label_4.setBounds(207, 0, 244, 34);
		label_5.setText("  PRECIO");
		label_5.setForeground(Color.RED);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_5.setBorder(new LineBorder(new Color(255, 0, 0)));
		label_5.setBounds(463, 0, 140, 34);
		scrollPane.setBounds(209, 126, 806, 151);
		textPaneDescripcion.setForeground(Color.WHITE);
		textPaneDescripcion.setEditable(false);
		textPaneDescripcion.setBorder(new LineBorder(SystemColor.textHighlight));
		textPaneDescripcion.setBackground(Color.DARK_GRAY);
	}

	private void eventos() {
		btnalquilarYaMismo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientAlquilerFrame frame = new ClientAlquilerFrame(collector, peliculaAAlquilar, clienteActual);
				frame.setVisible(true);
			}
		});
		btnverPelculaAhora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* Create and display the form */
				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
						File file = new File(
								"C:\\SoftLabs\\SPQ_GitHub\\Peliculas\\Completas\\" + peliculaAVer.getNombre() + ".mp4");
						if (file.exists()) {
							new ClientPeliculaFrame(peliculaAVer, false).setVisible(true);
							guardarPeliculaComoVista();                                                                           
						} else {
							JOptionPane.showMessageDialog(null,
									"Lo sentimos, ahora mismo no tenemos actualizada la pelÃcula.");
						}
					}
				});
			}
		});
		btnTrailer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = new File(
						"C:\\SoftLabs\\SPQ_GitHub\\Peliculas\\Trailers\\" + peliculaAVer.getNombre() + ".mp4");
				if (file.exists()) {
					new ClientPeliculaFrame(peliculaAVer, true).setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Lo sentimos, ahora mismo no tenemos actualizada la pelÌcula.");
				}
			}
		});
		btnOpinarPelcula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Preguntamos al usuario que escriba su opiniÛn:
				// Solo se guardar· la opiniÛn se no es nula:
				String mensaje = JOptionPane.showInputDialog(null, "Escriba su opiniÛn: ",
						"OpiniÛn del cliente: " + clienteActual.getNombre(), JOptionPane.QUESTION_MESSAGE);
				if (mensaje != null) {
					guardarOpinionDePelicula(mensaje);
				}
			}
		});
		btnVerLasOpiniones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientOpinionesFrame clientOF = new ClientOpinionesFrame(collector, peliculaAVer);
				clientOF.setVisible(true);
			}
		});
	}

	/**
	 * Método que muestra todos los detalles de la película seleccionada a alquilar:
	 */
	private void mostrarPeliculaAAlquilar() {
		// Primero el el cartel de la película:
		ImageIcon icon = null;
		icon = getImageIconPelicula(peliculaAAlquilar.getImage());

		// Colocamos datos:
		lblImagenPelicula.setIcon(icon);
		labelAño.setText(Integer.toString(peliculaAAlquilar.getAnyo()));
		labelCategoria.setText(peliculaAAlquilar.getCategoria().getNombre());
		labelDuracion.setText(Integer.toString(peliculaAAlquilar.getDuracion()) + " m");
		labelTitulo.setText(peliculaAAlquilar.getNombre());
		textPaneDescripcion.setText(openFileToString(peliculaAAlquilar.getDescripcion()));
		labelPrecio.setText(Float.toString(peliculaAAlquilar.getPrecio()) + " €");
	}

	private void mostrarPeliculaAVer() {
		// Primero el el cartel de la película:
		ImageIcon icon = null;
		icon = getImageIconPelicula(peliculaAVer.getImage());

		// Colocamos datos:
		lblImagenPelicula.setIcon(icon);
		labelAño.setText(Integer.toString(peliculaAVer.getAnyo()));
		labelCategoria.setText(peliculaAVer.getCategoria().getNombre());
		labelDuracion.setText(Integer.toString(peliculaAVer.getDuracion()) + " m");
		labelTitulo.setText(peliculaAVer.getNombre());
		textPaneDescripcion.setText(openFileToString(peliculaAVer.getDescripcion()));
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
			dev = new ImageIcon(image.getScaledInstance(195, 279, 0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dev;
	}
	/**
	 * MÈtodo para guardar la pelÌcula que se est· viendo en la base de datos como
	 * pelÌcula ya vista:
	 */
	private void guardarPeliculaComoVista() {
		// Se guardar·:
		// Comprobamos primero que la pelÌcula a guardar no
		// estÈ ya en la lista:
		List<PeliculaVista> arrayPeliculasVistas = new ArrayList<PeliculaVista>();
		try {
			arrayPeliculasVistas = collector.obtenerPeliculasVistas(arrayPeliculasVistas);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		boolean peliculaExisteEnFavoritos = false;
		for (PeliculaVista peli : arrayPeliculasVistas) {
			if (peli.getPelicula().getNombre().equals(peliculaAVer.getNombre())
					&& peli.getCliente().getNombre().equals(clienteActual.getNombre())
					&& peli.getCliente().getApellidos().equals(clienteActual.getApellidos())) {
				peliculaExisteEnFavoritos = true;
			}
		}

		if (peliculaExisteEnFavoritos == false) {
			try {
				if (collector.setPeliculaVista(peliculaAVer, clienteActual) == false) {
					JOptionPane.showMessageDialog(null, "°No se ha podido guardar la pelÌcula en vistas!", "°ERROR!",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Método para pasar bytes a string:
	 * 
	 * @param _bytes
	 * @return
	 */
	public String openFileToString(byte[] _bytes) {
		String file_string = "";

		for (int i = 0; i < _bytes.length; i++) {
			file_string += (char) _bytes[i];
		}

		return file_string;
	}

	/*
	 * Método que comprueba si el panel tiene que mostrar una película para ver o
	 * simplimente para alquilar:
	 * 
	 */
	private void comprobarPeliculaAlquilada() {
		if (peliculaAlquiladaAVer == true) {
			// Olcultamos componentes que no queremos mostrar:
			btnalquilarYaMismo.setVisible(false);
			label_5.setVisible(false);
			label_4.setVisible(false);
			labelPrecio.setVisible(false);
			labelDisponibles.setVisible(false);
			btnTrailer.setVisible(false);

			// Mostramos componenets solo para películas a ver:
			btnverPelculaAhora.setVisible(true);

			mostrarPeliculaAVer();

		} else {
			// MOstramos componentes sólo para peliculas a alquilar:
			btnalquilarYaMismo.setVisible(true);
			label_5.setVisible(true);
			label_4.setVisible(true);
			labelPrecio.setVisible(true);
			labelDisponibles.setVisible(true);
			btnTrailer.setVisible(true);

			// Ocultamos componentes que no queremos mostrar:
			btnverPelculaAhora.setVisible(false);

			mostrarPeliculaAAlquilar();
		}
	}
	
	/**
	 * MÈtodo para guardar la opiniÛn de la pelÌcula ya vista:
	 */
	private void guardarOpinionDePelicula(String opinion) {

		// Primero sacamos los usuarios de la BD:
		List<Usuario> arrayUsuarios = new ArrayList<Usuario>();
		try {
			arrayUsuarios = collector.obtenerUsuarios(arrayUsuarios);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Sacamos el usuario que tenga el mismo cliente que ela ctual:
		Usuario user = null;

		for (Usuario us : arrayUsuarios) {
			if (us.getCliente().getNombre().equals(clienteActual.getNombre())
					&& us.getCliente().getApellidos().equals(clienteActual.getApellidos())) {
				user = us; // Ya tenemos a nuestro usuario.
			}
		}

		// Ahora ya podemos guardar la opiniÛn:
		try {
			if (collector.setOpinion(peliculaAVer, user, opinion) == false) {
				JOptionPane.showMessageDialog(null, "°No se ha podido guardar su opiniÛn!", "°ERROR!",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
