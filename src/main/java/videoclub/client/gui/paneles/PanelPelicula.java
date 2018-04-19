package videoclub.client.gui.paneles;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import videoclub.client.gui.ventanas.ClientAlquilerFrame;
import videoclub.server.gui.ICollector;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.Usuario;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextPane;

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
		scrollPane.setViewportView(textPaneDescripcion);
	}

	private void componentes() {
		btnverPelculaAhora.setForeground(Color.GREEN);
		btnverPelculaAhora.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnverPelculaAhora.setContentAreaFilled(false);
		btnverPelculaAhora.setBorder(new LineBorder(Color.GREEN));
		btnverPelculaAhora.setBounds(207, 0, 809, 34);
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
		btnalquilarYaMismo.setBounds(614, 0, 400, 34);
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
		scrollPane.setBounds(209, 91, 806, 186);
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

			}
		});
	}

	/**
	 * Método que muestra todos los detalles de la película seleccionada a
	 * alquilar:
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
	 * Método que comprueba si el panel tiene que mostrar una película para ver
	 * o simplimente para alquilar:
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

			// Ocultamos componentes que no queremos mostrar:
			btnverPelculaAhora.setVisible(false);
			
			mostrarPeliculaAAlquilar();
		}
	}
}
