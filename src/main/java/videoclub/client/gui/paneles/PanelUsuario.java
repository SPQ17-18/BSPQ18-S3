package videoclub.client.gui.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import videoclub.client.utiles.Temas;
import videoclub.server.gui.ICollector;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Pelicula;

public class PanelUsuario extends JPanel {

	private static final long serialVersionUID = 1L;
	private int anchuraPanel = 1080;
	private int alturaPanel = 720;
	private JPanel panel;
	private JScrollPane scrollPane;
	private GridLayout gl_panel;

	private JToggleButton[] arrayBotones;
	private List<Pelicula> arrayPeliculas = new ArrayList<Pelicula>();;
	private List<BotonPelicula> arrayBotonesPelicula;

	private JLabel NombreUsuario;
	private JTextField textFieldBuscarPelicula;
	private JLabel lblImagenPelicula;
	private JLabel labelTitulo;
	private JLabel labelDuracion;
	private JLabel labelAño;
	private JLabel labelCategoria;
	private JTextPane textPaneDescripcion;
	private JLabel labelDisponibles;
	private JLabel labelPrecio;
	private JButton btnalquilarYaMismo;
	private JLabel lblNewLabel;
	private JLabel labelDinero;
	private JComboBox<Integer> comboBoxAño;
	private JComboBox<String> comboBoxGenero;

	private ICollector collector; // Collector implementado desde "ClienfFrame"
	public static Cliente clienteActual = null;

	/**
	 * Create the panel.
	 */
	public PanelUsuario(ICollector collector) {
		this.collector = collector;
		inicializar();
		componentes();
		añadirComponentes();
		eventos();

		valoresComboBoxCategorias();
		valoresComboBoxAños();
	}

	private void inicializar() {
		// Numero de filas, Numero de columnas, Separaciones h y v:
		gl_panel = new GridLayout(2, 2, 5, 5);
		scrollPane = new JScrollPane();
		panel = new JPanel();
		// ERROR! NombreUsuario = new JLabel(" " + clienteActual.getNombre() + "
		// - " + clienteActual.getApellidos() + " ["
		// ERROR! + clienteActual.getDireccion().getPais() + "]");
		NombreUsuario = new JLabel("Cliente...");
		textFieldBuscarPelicula = new JTextField();
		comboBoxGenero = new JComboBox<String>();
		lblImagenPelicula = new JLabel();
		labelTitulo = new JLabel();
		labelDuracion = new JLabel();
		labelAño = new JLabel();
		labelCategoria = new JLabel();
		labelDisponibles = new JLabel();
		labelDisponibles.setText(">0");
		labelPrecio = new JLabel();
		btnalquilarYaMismo = new JButton("\u00A1ALQUILAR YA MISMO!");
		lblNewLabel = new JLabel("SALDO ");
		labelDinero = new JLabel("0 \u20AC");
		comboBoxAño = new JComboBox<Integer>();
		scrollPane_1 = new JScrollPane();
		textPaneDescripcion = new JTextPane();
		comboBoxTema = new JComboBox<String>();
	}

	private void componentes() {
		btnalquilarYaMismo.setBorder(new LineBorder(Color.GREEN));
		scrollPane.setBounds(12, 64, 1056, 350);
		NombreUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
		NombreUsuario.setForeground(Color.ORANGE);
		NombreUsuario.setBorder(new TitledBorder(null, "Bienvenido de nuevo:", TitledBorder.LEADING, TitledBorder.TOP,
				null, SystemColor.textHighlight));
		NombreUsuario.setBounds(12, 0, 241, 65);
		textFieldBuscarPelicula.setBackground(Color.DARK_GRAY);
		textFieldBuscarPelicula.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Buscar pel\u00EDcula por nombre",
						TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.textHighlight));
		textFieldBuscarPelicula.setBounds(783, 0, 285, 65);
		textFieldBuscarPelicula.setColumns(10);
		comboBoxGenero.setBackground(Color.BLACK);
		comboBoxGenero.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Buscar pel\u00EDcula por g\u00E9nero",
						TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.textHighlight));
		comboBoxGenero.setBounds(524, 6, 247, 55);
		lblImagenPelicula.setBorder(new LineBorder(SystemColor.textHighlight));
		lblImagenPelicula.setBounds(12, 427, 100, 140);
		labelTitulo.setForeground(Color.WHITE);
		labelTitulo.setBorder(new LineBorder(SystemColor.textHighlight));
		labelTitulo.setBounds(124, 427, 195, 29);
		labelDuracion.setForeground(Color.WHITE);
		labelDuracion.setBorder(new LineBorder(SystemColor.textHighlight));
		labelDuracion.setBounds(124, 469, 195, 29);
		labelAño.setForeground(Color.WHITE);
		labelAño.setBorder(new LineBorder(SystemColor.textHighlight));
		labelAño.setBounds(331, 427, 195, 29);
		labelCategoria.setForeground(Color.WHITE);
		labelCategoria.setBorder(new LineBorder(SystemColor.textHighlight));
		labelCategoria.setBounds(331, 469, 195, 29);
		textPaneDescripcion.setForeground(Color.WHITE);
		textPaneDescripcion.setBorder(new LineBorder(SystemColor.textHighlight));
		textPaneDescripcion.setBackground(Color.DARK_GRAY);
		labelDisponibles.setForeground(Color.RED);
		labelDisponibles.setBorder(new LineBorder(SystemColor.textHighlight));
		labelDisponibles.setBounds(12, 580, 100, 29);
		labelPrecio.setForeground(Color.GREEN);
		labelPrecio.setBorder(new LineBorder(SystemColor.textHighlight));
		labelPrecio.setBounds(12, 622, 100, 29);
		btnalquilarYaMismo.setForeground(Color.GREEN);
		btnalquilarYaMismo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnalquilarYaMismo.setBounds(124, 661, 402, 46);
		lblNewLabel.setForeground(SystemColor.textHighlight);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(12, 661, 73, 46);
		labelDinero.setForeground(Color.GREEN);
		labelDinero.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelDinero.setBounds(81, 663, 157, 43);
		comboBoxAño.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Buscar pel\u00EDcula por a\u00F1o",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 120, 215)));
		comboBoxAño.setBackground(Color.BLACK);
		comboBoxAño.setBounds(265, 6, 247, 55);
		btnalquilarYaMismo.setContentAreaFilled(false);
		panel.setBackground(Color.DARK_GRAY);
		scrollPane_1.setBounds(124, 511, 402, 140);
		scrollPane_1.setBorder(new LineBorder(SystemColor.textHighlight));
		comboBoxTema.setModel(new DefaultComboBoxModel<String>(new String[] { "Tema Raven", "Tema Autum" }));
		comboBoxTema.setBounds(865, 685, 203, 22);

	}

	private void añadirComponentes() {
		setSize(anchuraPanel, alturaPanel);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		setBorder(null);

		add(scrollPane);
		add(NombreUsuario);
		add(textFieldBuscarPelicula);
		add(comboBoxGenero);
		add(lblImagenPelicula);
		add(labelTitulo);
		add(labelDuracion);
		add(labelAño);
		add(labelCategoria);
		add(scrollPane_1);

		add(labelDisponibles);
		add(labelPrecio);
		add(btnalquilarYaMismo);
		add(lblNewLabel);
		add(labelDinero);
		add(comboBoxAño);
		add(comboBoxTema);

		scrollPane_1.setViewportView(textPaneDescripcion);
		agregarPeliculasAlPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(gl_panel);
	}

	private boolean comboBoxAñoPresionado = false;
	private boolean comboBoxGeneroPresionado = false;
	private List<String> arrayNombresPeliculasEncontradas = new ArrayList<String>();
	private JScrollPane scrollPane_1;
	private JComboBox<String> comboBoxTema;
	@SuppressWarnings("unused")
	private Temas tema;

	private void eventos() {
		comboBoxAño.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBoxAñoPresionado == true) {
					buscarPeliculasPorAño((int) comboBoxAño.getSelectedItem());
				}
				comboBoxAñoPresionado = true;
			}
		});
		comboBoxGenero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBoxGeneroPresionado == true) {
					buscarPeliculasPorGenero((String) comboBoxGenero.getSelectedItem());
				}
				comboBoxGeneroPresionado = true;
			}
		});
		textFieldBuscarPelicula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarNombresPeliculasAproximadamente();
				buscarPeliculaPorNombre(arrayNombresPeliculasEncontradas);
			}
		});

		btnalquilarYaMismo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		comboBoxTema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tema = new Temas((String) comboBoxTema.getSelectedItem());
					// Actualizamos componentes:
					SwingUtilities.updateComponentTreeUI(getRootPane());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * Método para cargar todas las películas en el gridLayout creando botones
	 * para cada una:
	 */
	private void agregarPeliculasAlPanel() {
		boolean correcto = false;
		// Primero obtenemos las películas de la base de datos:
		try {
			arrayPeliculas = collector.obtenerPeliculas(arrayPeliculas);
			correcto = true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (correcto == true) {
			arrayBotones = new JToggleButton[arrayPeliculas.size()];
			arrayBotonesPelicula = new ArrayList<BotonPelicula>();
			// Segundo buscamos el id asociado a cada película y lo cotejamos
			// con el
			// id de las imágenes:
			for (int i = 0; i < arrayPeliculas.size(); i++) {

				// Inicializamos cada BOTON!:

				ImageIcon icon = null;
				icon = getImageIconPelicula(arrayPeliculas.get(i).getImage());

				arrayBotones[i] = new JToggleButton(icon);
				arrayBotones[i].setContentAreaFilled(false);
				arrayBotones[i].setBorder(new LineBorder(SystemColor.textHighlight));
				arrayBotonesPelicula.add(new BotonPelicula(arrayPeliculas.get(i)));

				// Añadimos botón de la película al panel asignado para ello:
				panel.add(arrayBotones[i]);
			}

		}

		eventosBotonesPeplicula();
	}

	/*
	 * Método de eventos para los botones de las películas:
	 */
	private void eventosBotonesPeplicula() {

		for (int i = 0; i < arrayBotonesPelicula.size(); i++) {
			arrayBotones[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < arrayBotones.length; i++) {
						if (arrayBotones[i].isSelected() == true) {
							mostrarPeliculaAAlquilar(arrayBotonesPelicula.get(i).getPelicula());
							// Toca deseleccionar todos:
							for (int j = 0; j < arrayBotones.length; j++) {
								arrayBotones[j].setSelected(false);
							}
							break;
						}
					}
				}
			});
		}
	}

	private void valoresComboBoxCategorias() {
		String[] arrayCategorias = new String[] { "Infantil", "Comedia", "Thriller", "Miedo", "Clasica", "Musical" };
		for (int i = 0; i < arrayCategorias.length; i++) {
			comboBoxGenero.addItem(arrayCategorias[i]);
		}
	}

	private void valoresComboBoxAños() {
		int[] arrayAños = new int[100];
		// Primero obtenemos todos los años del array de peliculas:
		for (int i = 0; i < arrayPeliculas.size(); i++) {
			boolean nuevoAño = true;
			for (int j = 0; j < arrayAños.length; j++) {
				if (arrayPeliculas.get(i).getAnyo() == arrayAños[j]) {
					nuevoAño = false; // YA EXISTE!
				}
			}

			// Ahora comprobamos si ha habido repes:
			if (nuevoAño == true) {
				// Entonces guardamos ese año:
				arrayAños[i] = arrayPeliculas.get(i).getAnyo();
			}
		}

		// Ahora los metemos al combo:
		for (int i = 0; i < arrayAños.length; i++) {
			// Solo aquellos que sean != 0
			if (arrayAños[i] != 0) {
				comboBoxAño.addItem(arrayAños[i]);
			}
		}

	}

	private void buscarPeliculasPorAño(int anyo) {
		desactivarComponentes();
		// Nuevo panel:
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(panel);
		panel.setLayout(gl_panel);

		// Primero obtenemos cantidad de peliculas por ese año:
		int cantAnyo = 0;
		for (int i = 0; i < arrayPeliculas.size(); i++) {
			if (arrayPeliculas.get(i).getAnyo() == anyo) {
				cantAnyo++;
			}
		}

		if (cantAnyo > 0) {
			// Creamos cantidad de botones a partir de la cantAnyo:
			arrayBotones = new JToggleButton[cantAnyo];
			int posArrayBotones = 0;
			// Nueva lista:
			arrayBotonesPelicula = new ArrayList<BotonPelicula>();

			// Segundo buscamos el id asociado a cada película y lo cotejamos
			// con el
			// id de las imágenes:
			for (int i = 0; i < arrayPeliculas.size(); i++) {

				// Inicializamos cada BOTON!:
				if (arrayPeliculas.get(i).getAnyo() == anyo) {
					ImageIcon icon = null;
					icon = getImageIconPelicula(arrayPeliculas.get(i).getImage());
					arrayBotones[posArrayBotones] = new JToggleButton(icon);
					arrayBotones[posArrayBotones].setContentAreaFilled(false);
					arrayBotones[posArrayBotones].setBorder(new LineBorder(SystemColor.textHighlight));
					arrayBotonesPelicula.add(new BotonPelicula(arrayPeliculas.get(i)));

					// Añadimos botón de la película al panel asignado para
					// ello:
					panel.add(arrayBotones[posArrayBotones]);

					posArrayBotones++;
				}
			}
		}
		eventosBotonesPeplicula();
	}

	/**
	 * Método que muestra todos los detalles de la película seleccionada a
	 * alquilar:
	 */
	private void mostrarPeliculaAAlquilar(Pelicula pelicula) {
		// Primero el el cartel de la película:
		ImageIcon icon = null;
		icon = getImageIconPelicula(pelicula.getImage());

		activarComponentes();

		// Colocamos datos:
		lblImagenPelicula.setIcon(icon);
		labelAño.setText(" AÑO: " + Integer.toString(pelicula.getAnyo()));
		labelCategoria.setText(" CATEGORIA: " + pelicula.getCategoria().getNombre());
		labelDuracion.setText(" DURACIÓN: " + Integer.toString(pelicula.getDuracion()) + " minutos");
		labelTitulo.setText(" TITULO: " + pelicula.getNombre());
		textPaneDescripcion.setText(pelicula.getDescripcion());
		labelPrecio.setText(" PRECIO: " + Float.toString(pelicula.getPrecio()) + "€");

		// Guardamos datos de la película por si el usuario quiere alquilarla:
		peliculaAAlquilar = pelicula;
	}

	@SuppressWarnings("unused")
	private Pelicula peliculaAAlquilar;

	private void desactivarComponentes() {
		// No visibles:
		lblImagenPelicula.setVisible(false);
		labelAño.setVisible(false);
		labelCategoria.setVisible(false);
		labelDisponibles.setVisible(false);
		labelDuracion.setVisible(false);
		labelTitulo.setVisible(false);
		textPaneDescripcion.setVisible(false);
		btnalquilarYaMismo.setVisible(false);
		labelPrecio.setVisible(false);
		lblNewLabel.setVisible(false);
		labelDinero.setVisible(false);
		scrollPane_1.setVisible(false);
	}

	private void activarComponentes() {
		// Visibles:
		lblImagenPelicula.setVisible(true);
		labelAño.setVisible(true);
		labelCategoria.setVisible(true);
		labelDisponibles.setVisible(true);
		labelDuracion.setVisible(true);
		labelTitulo.setVisible(true);
		textPaneDescripcion.setVisible(true);
		btnalquilarYaMismo.setVisible(true);
		labelPrecio.setVisible(true);
		lblNewLabel.setVisible(true);
		labelDinero.setVisible(true);
		scrollPane_1.setVisible(true);
	}

	private void buscarPeliculasPorGenero(String categoria) {
		desactivarComponentes();
		// Nuevo panel:
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(panel);
		panel.setLayout(gl_panel);

		// Primero obtenemos cantidad de peliculas por ese año:
		int cantAnyo = 0;
		for (int i = 0; i < arrayPeliculas.size(); i++) {
			if (arrayPeliculas.get(i).getCategoria().getNombre().equals(categoria)) {
				cantAnyo++;
			}
		}

		if (cantAnyo != 0) {
			// Creamos cantidad de botones a partir de la cantAnyo:
			arrayBotones = new JToggleButton[cantAnyo];
			int posArrayBotones = 0;
			// Nueva lista:
			arrayBotonesPelicula = new ArrayList<BotonPelicula>();

			// Segundo buscamos el id asociado a cada película y lo cotejamos
			// con el
			// id de las imágenes:
			for (int i = 0; i < arrayPeliculas.size(); i++) {

				// Inicializamos cada BOTON!:

				ImageIcon icon = null;

				if (arrayPeliculas.get(i).getCategoria().getNombre().equals(categoria)) {

					icon = getImageIconPelicula(arrayPeliculas.get(i).getImage());

					arrayBotones[posArrayBotones] = new JToggleButton(icon);
					arrayBotones[posArrayBotones].setContentAreaFilled(false);
					arrayBotones[posArrayBotones].setBorder(new LineBorder(SystemColor.textHighlight));
					arrayBotonesPelicula.add(new BotonPelicula(arrayPeliculas.get(i)));

					// Añadimos botón de la película al panel asignado para
					// ello:
					panel.add(arrayBotones[posArrayBotones]);

					posArrayBotones++;
				}
			}

			eventosBotonesPeplicula();
		} else {
			JOptionPane.showMessageDialog(null, "No se ha encontrado ninguna película con el género " + categoria);
		}
	}

	private void buscarPeliculaPorNombre(List<String> nombres) {
		desactivarComponentes();
		// Nuevo panel:
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(panel);
		panel.setLayout(gl_panel);

		// Primero obtenemos cantidad de peliculas por ese año:
		int cantAnyo = 0;
		for (int i = 0; i < arrayPeliculas.size(); i++) {
			for (int j = 0; j < nombres.size(); j++) {
				if (arrayPeliculas.get(i).getNombre().equals(nombres.get(j))) {
					cantAnyo++;
				}
			}
		}

		if (cantAnyo != 0) {
			// Creamos cantidad de botones a partir de la cantAnyo:
			arrayBotones = new JToggleButton[cantAnyo];
			int posArrayBotones = 0;
			// Nueva lista:
			arrayBotonesPelicula = new ArrayList<BotonPelicula>();

			// Segundo buscamos el id asociado a cada película y lo cotejamos
			// con el
			// id de las imágenes:
			for (int i = 0; i < arrayPeliculas.size(); i++) {

				// Inicializamos cada BOTON!:

				ImageIcon icon = null;

				for (int j = 0; j < nombres.size(); j++) {
					if (arrayPeliculas.get(i).getNombre().equals(nombres.get(j))) {
						icon = getImageIconPelicula(arrayPeliculas.get(i).getImage());

						arrayBotones[posArrayBotones] = new JToggleButton(icon);
						arrayBotones[posArrayBotones].setContentAreaFilled(false);
						arrayBotones[posArrayBotones].setBorder(new LineBorder(SystemColor.textHighlight));
						arrayBotonesPelicula.add(new BotonPelicula(arrayPeliculas.get(i)));

						// Añadimos botón de la película al panel asignado para
						// ello:
						panel.add(arrayBotones[posArrayBotones]);

						posArrayBotones++;
					}
				}
			}

			eventosBotonesPeplicula();
		} else {
			JOptionPane.showMessageDialog(null,
					"No se ha encontrado ninguna película con el nombre " + textFieldBuscarPelicula.getText());
		}
	}

	/**
	 * Método que busca los nombres de las peliculas a partir de una serie de
	 * caracteres, aunque el nombre no esté del todo puesto el buscador la
	 * encontrará, o las encontrará, se va a buscar todos los string que sean
	 * pareceidos a la búsqueda que hayas puesto, eso si no lo has escrito == a
	 * una de las peliculas que exista!
	 */
	private void buscarNombresPeliculasAproximadamente() {
		arrayNombresPeliculasEncontradas = new ArrayList<String>();
		String cancion = textFieldBuscarPelicula.getText();
		String sub;
		int pos = 0;
		for (int i = 0; i < arrayPeliculas.size(); i++) {
			try {
				pos = arrayPeliculas.get(i).getNombre().indexOf(cancion);
			} catch (Exception ex) {
				// Saltaría error de -1 en pos = Canción no encontrada
			}
			if (pos >= 0) {
				sub = arrayPeliculas.get(i).getNombre().substring(0, arrayPeliculas.get(i).getNombre().length());
				arrayNombresPeliculasEncontradas.add(sub);
			}
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
			dev = new ImageIcon(image.getScaledInstance(100, 140, 0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dev;
	}

	// Clase para guardar los objetos tipo "BotonPelicula" que contendrán el id
	// del botón:
	public class BotonPelicula {
		private Pelicula pelicula;

		public BotonPelicula(Pelicula pelicula) {
			this.pelicula = pelicula;
		}

		public Pelicula getPelicula() {
			return pelicula;
		}

		public void setPelicula(Pelicula pelicula) {
			this.pelicula = pelicula;
		}
	}
}
