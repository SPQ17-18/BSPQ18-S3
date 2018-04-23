package videoclub.client.gui.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import videoclub.client.utiles.Temas;
import videoclub.server.gui.ICollector;
import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.Recomendacion;
import videoclub.server.jdo.Usuario;

public class PanelUsuario extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JScrollPane scrollPane;
	private GridLayout gl_panel;

	private JToggleButton[] arrayBotones;
	private List<Pelicula> arrayPeliculas = new ArrayList<Pelicula>();;
	private List<BotonPelicula> arrayBotonesPelicula;

	private JLabel NombreUsuario;
	private JTextField textFieldBuscarPelicula;
	private JLabel lblNewLabel;
	private JLabel labelDinero;
	private JComboBox<Integer> comboBoxAño;
	private JComboBox<String> comboBoxGenero;

	@SuppressWarnings("unused")
	private Pelicula peliculaAAlquilar;
	private JPanel panelOpciones;
	private JButton btnListaSeries;
	private JButton btnListaAmigos;
	private JButton btnListaFavoritos;
	private JButton btnListaRecomendadas;
	private JButton btnPeliculasVistas;
	private JButton btnPeliculasPendientes;
	private JButton btnChat;
	private JButton btnListaUsuarios;
	private JButton btnListaAlquiladas;
	private JButton btnPeliculasNuevas;
	private JButton btnListaPeliculas;
	private JLabel lblOpciones;
	private JScrollPane scrollContenedorPaneles;
	private JLabel lblBuscarPelculasPor;
	private JLabel lblBuscarPelculasPor_1;
	private JLabel lblBuscarPelculasPor_2;

	private ICollector collector; // Collector implementado desde "ClienfFrame"
	private Cliente clienteActual;
	public Usuario usuarioActual;

	private int alturaPanelOpcionesInicial = 41;
	private int alturaPanelOpcionesFinal = 428;
	private boolean panelOpcionesRecogido = true;
	private boolean peliculaAlquiladaAVer = false;
	public PanelChat panelChat;

	private List<JButton> arrayBotonesOpciones = new ArrayList<JButton>();
	public PanelAmigosUsuarios pau;

	/**
	 * Create the panel.
	 */
	public PanelUsuario(ICollector collector) {
		this.collector = collector;
		try {
			this.clienteActual = this.collector.getCliente();
			this.usuarioActual = this.collector.getUsuario();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inicializar();
		componentes();
		añadirComponentes();
		eventos();

		valoresComboBoxCategorias();
		valoresComboBoxAños();
		actualizarPanelUsuariosAmigos();
		panelChat = new PanelChat(collector, usuarioActual);
		scrollContenedorPaneles.setViewportView(panelChat);
	}

	private void inicializar() {
		// Numero de filas, Numero de columnas, Separaciones h y v:
		gl_panel = new GridLayout(1, 5, 5, 5);
		scrollPane = new JScrollPane();
		panel = new JPanel();
		NombreUsuario = new JLabel(" " + clienteActual.getNombre() + "- " + clienteActual.getApellidos() + " ["
				+ clienteActual.getDireccion().getPais() + "]");
		textFieldBuscarPelicula = new JTextField();
		comboBoxGenero = new JComboBox<String>();
		lblNewLabel = new JLabel("  SALDO ");
		labelDinero = new JLabel("0 \u20AC");
		comboBoxAño = new JComboBox<Integer>();
		comboBoxTema = new JComboBox<String>();
		panelOpciones = new JPanel();
		lblOpciones = new JLabel("OPCIONES");
		btnListaPeliculas = new JButton("PELICULAS");
		btnListaSeries = new JButton("SERIES");
		btnListaAmigos = new JButton("AMIGOS");
		btnListaFavoritos = new JButton("PELICULAS FAVORTIAS");
		btnListaRecomendadas = new JButton("PELICULAS RECOMENDADAS");
		btnPeliculasVistas = new JButton("PELICULAS VISTAS");
		btnPeliculasPendientes = new JButton("PELICULAS PENDIENTES");
		btnChat = new JButton("CHAT");
		btnListaUsuarios = new JButton("USUARIOS");
		btnListaAlquiladas = new JButton("PELICULAS ALQUILADAS");
		btnPeliculasNuevas = new JButton("PELICULAS NUEVAS");
		scrollContenedorPaneles = new JScrollPane();
		lblBuscarPelculasPor = new JLabel("Buscar películas por año");
		lblBuscarPelculasPor_1 = new JLabel("Buscar películas por género");
		lblBuscarPelculasPor_2 = new JLabel("Buscar películas por nombre (se mostrarán todos los parecidos)");
		scrollPane_1 = new JScrollPane();
		panelAmigosUsuarios = new JPanel();
	}

	private void componentes() {
		lblNewLabel.setBorder(new LineBorder(Color.GREEN));
		labelDinero.setBorder(null);
		labelDinero.setHorizontalAlignment(SwingConstants.CENTER);
		comboBoxTema.setBorder(new LineBorder(Color.MAGENTA));
		scrollPane.setBounds(12, 112, 1256, 302);
		NombreUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
		NombreUsuario.setForeground(Color.ORANGE);
		NombreUsuario.setBorder(new LineBorder(Color.ORANGE));
		NombreUsuario.setBounds(12, 13, 331, 29);
		textFieldBuscarPelicula.setBackground(Color.DARK_GRAY);
		textFieldBuscarPelicula.setBorder(new LineBorder(SystemColor.textHighlight));
		textFieldBuscarPelicula.setBounds(756, 76, 512, 29);
		textFieldBuscarPelicula.setColumns(10);
		comboBoxGenero.setBackground(Color.BLACK);
		comboBoxGenero.setBorder(new LineBorder(SystemColor.textHighlight));
		comboBoxGenero.setBounds(504, 76, 240, 29);
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(355, 13, 137, 29);
		labelDinero.setForeground(new Color(30, 144, 255));
		labelDinero.setFont(new Font("Tahoma", Font.BOLD, 15));
		labelDinero.setBounds(425, 13, 67, 29);
		comboBoxAño.setBorder(new LineBorder(SystemColor.textHighlight));
		comboBoxAño.setBackground(Color.BLACK);
		comboBoxAño.setBounds(252, 76, 240, 29);
		panel.setBackground(Color.DARK_GRAY);
		comboBoxTema.setModel(new DefaultComboBoxModel<String>(new String[] { "Tema Raven", "Tema Autum" }));
		comboBoxTema.setBounds(1118, 14, 150, 29);
		panel.setLayout(gl_panel);
		panelOpciones.setLayout(null);
		panelOpciones.setBorder(new LineBorder(Color.ORANGE));
		panelOpciones.setBackground(Color.GRAY);
		panelOpciones.setBounds(11, 64, 232, alturaPanelOpcionesInicial);
		lblOpciones.setBackground(Color.GRAY);
		lblOpciones.setOpaque(true);
		lblOpciones.setBorder(new LineBorder(Color.ORANGE));
		lblOpciones.setForeground(Color.ORANGE);
		lblOpciones.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblOpciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblOpciones.setBounds(0, 0, 232, 40);
		btnListaPeliculas.setBackground(Color.BLACK);
		btnListaPeliculas.setBorder(null);
		btnListaPeliculas.setForeground(Color.WHITE);
		btnListaPeliculas.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnListaPeliculas.setBounds(1, 41, 228, 34);
		btnListaSeries.setBackground(Color.BLACK);
		btnListaSeries.setForeground(Color.WHITE);
		btnListaSeries.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnListaSeries.setBorder(null);
		btnListaSeries.setBounds(1, 76, 228, 34);
		btnListaAmigos.setBackground(Color.BLACK);
		btnListaAmigos.setForeground(Color.WHITE);
		btnListaAmigos.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnListaAmigos.setBorder(null);
		btnListaAmigos.setBounds(1, 111, 228, 34);
		btnListaFavoritos.setBackground(Color.BLACK);
		btnListaFavoritos.setForeground(Color.WHITE);
		btnListaFavoritos.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnListaFavoritos.setBorder(null);
		btnListaFavoritos.setBounds(1, 146, 228, 34);
		btnListaRecomendadas.setBackground(Color.BLACK);
		btnListaRecomendadas.setForeground(Color.WHITE);
		btnListaRecomendadas.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnListaRecomendadas.setBorder(null);
		btnListaRecomendadas.setBounds(1, 181, 228, 34);
		btnPeliculasVistas.setBackground(Color.BLACK);
		btnPeliculasVistas.setForeground(Color.WHITE);
		btnPeliculasVistas.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnPeliculasVistas.setBorder(null);
		btnPeliculasVistas.setBounds(1, 356, 228, 34);
		btnPeliculasPendientes.setBackground(Color.BLACK);
		btnPeliculasPendientes.setForeground(Color.WHITE);
		btnPeliculasPendientes.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnPeliculasPendientes.setBorder(null);
		btnPeliculasPendientes.setBounds(1, 321, 228, 34);
		btnChat.setBackground(Color.BLACK);
		btnChat.setForeground(Color.WHITE);
		btnChat.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnChat.setBorder(null);
		btnChat.setBounds(1, 286, 228, 34);
		btnListaUsuarios.setBackground(Color.BLACK);
		btnListaUsuarios.setForeground(Color.WHITE);
		btnListaUsuarios.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnListaUsuarios.setBorder(null);
		btnListaUsuarios.setBounds(1, 251, 228, 34);
		btnListaAlquiladas.setBackground(Color.BLACK);
		btnListaAlquiladas.setForeground(Color.WHITE);
		btnListaAlquiladas.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnListaAlquiladas.setBorder(null);
		btnListaAlquiladas.setBounds(1, 216, 228, 34);
		btnPeliculasNuevas.setBackground(Color.BLACK);
		btnPeliculasNuevas.setForeground(Color.WHITE);
		btnPeliculasNuevas.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnPeliculasNuevas.setBorder(null);
		btnPeliculasNuevas.setBounds(1, 391, 228, 34);
		scrollContenedorPaneles.setBounds(252, 427, 1016, 279);
		lblBuscarPelculasPor.setForeground(SystemColor.textHighlight);
		lblBuscarPelculasPor.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBuscarPelculasPor.setBounds(252, 55, 240, 16);
		lblBuscarPelculasPor_1.setForeground(SystemColor.textHighlight);
		lblBuscarPelculasPor_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBuscarPelculasPor_1.setBounds(504, 55, 240, 16);
		lblBuscarPelculasPor_2.setForeground(SystemColor.textHighlight);
		lblBuscarPelculasPor_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBuscarPelculasPor_2.setBounds(756, 55, 512, 16);
		scrollPane_1.setBounds(12, 427, 232, 279);
		panelAmigosUsuarios.setBorder(new LineBorder(SystemColor.textHighlight));

	}

	private void añadirComponentes() {
		setSize(1280, 720);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		setBorder(null);

		add(scrollPane);
		add(NombreUsuario);
		add(textFieldBuscarPelicula);
		add(comboBoxGenero);
		add(lblNewLabel);
		add(labelDinero);
		add(comboBoxAño);
		add(comboBoxTema);
		add(panelOpciones);
		add(scrollContenedorPaneles);
		add(lblBuscarPelculasPor);
		add(lblBuscarPelculasPor_1);
		add(lblBuscarPelculasPor_2);
		add(scrollPane_1);

		panelOpciones.add(lblOpciones);
		panelOpciones.add(btnListaPeliculas);
		panelOpciones.add(btnListaSeries);
		panelOpciones.add(btnListaAmigos);
		panelOpciones.add(btnListaFavoritos);
		panelOpciones.add(btnListaRecomendadas);
		panelOpciones.add(btnPeliculasVistas);
		panelOpciones.add(btnPeliculasPendientes);
		panelOpciones.add(btnChat);
		panelOpciones.add(btnListaUsuarios);
		panelOpciones.add(btnListaAlquiladas);
		panelOpciones.add(btnPeliculasNuevas);

		arrayBotonesOpciones.add(btnListaPeliculas);
		arrayBotonesOpciones.add(btnListaSeries);
		arrayBotonesOpciones.add(btnListaAmigos);
		arrayBotonesOpciones.add(btnListaFavoritos);
		arrayBotonesOpciones.add(btnListaRecomendadas);
		arrayBotonesOpciones.add(btnPeliculasVistas);
		arrayBotonesOpciones.add(btnPeliculasPendientes);
		arrayBotonesOpciones.add(btnChat);
		arrayBotonesOpciones.add(btnListaUsuarios);
		arrayBotonesOpciones.add(btnListaAlquiladas);
		arrayBotonesOpciones.add(btnPeliculasNuevas);

		scrollPane.setViewportView(panel);
		scrollPane_1.setViewportView(panelAmigosUsuarios);

		agregarPeliculasAlPanel();
	}

	private boolean comboBoxAñoPresionado = false;
	private boolean comboBoxGeneroPresionado = false;
	private List<String> arrayNombresPeliculasEncontradas = new ArrayList<String>();
	private JComboBox<String> comboBoxTema;
	@SuppressWarnings("unused")
	private Temas tema;
	private JPanel panelAmigosUsuarios;
	private JScrollPane scrollPane_1;

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
		btnChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelChat = new PanelChat(collector, usuarioActual);
				scrollContenedorPaneles.setViewportView(panelChat);
			}
		});
		lblOpciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (panelOpcionesRecogido == true) {
					// Lo expandimos:
					panelOpciones.setSize(panelOpciones.getWidth(), alturaPanelOpcionesFinal);
					// Recogemos panel películas
					scrollPane.setBounds(252, 112, 1016, 302);
					// Recogemos panel usuariosamigos
					scrollPane_1.setBounds(12, 501, 232, 205);
					panelOpcionesRecogido = false;

				} else {
					// Lo recogemos:
					panelOpciones.setSize(panelOpciones.getWidth(), alturaPanelOpcionesInicial);
					// Expandimos panel películas
					scrollPane.setBounds(12, 112, 1256, 302);
					// Expandimos panel usuariosamigos
					scrollPane_1.setBounds(12, 427, 232, 279);
					panelOpcionesRecogido = true;
				}
			}
		});
		btnPeliculasNuevas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtenerPeliculasNuevas();
				peliculaAlquiladaAVer = false;
			}
		});
		btnListaPeliculas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Nuevo panel:
				panel = new JPanel();
				panel.setBackground(Color.DARK_GRAY);
				scrollPane.setViewportView(panel);
				panel.setLayout(gl_panel);
				agregarPeliculasAlPanel();
				peliculaAlquiladaAVer = false;
			}
		});
		btnListaUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pau = new PanelAmigosUsuarios(collector, "USUARIOS", usuarioActual);
				scrollPane_1.setViewportView(pau);
			}
		});
		btnListaAmigos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pau = new PanelAmigosUsuarios(collector, "AMIGOS", usuarioActual);
				scrollPane_1.setViewportView(pau);
			}
		});
		btnListaRecomendadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (obtenerPeliculasRecomendadas() == false) {
					JOptionPane.showMessageDialog(null, "No tiene ninguna recomendación de ningún amigo suyo.");
				} else {
					peliculaAlquiladaAVer = false;
				}
			}
		});

		btnListaAlquiladas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (obtenerPeliculasAlquiladas() == false) {
					JOptionPane.showMessageDialog(null, "No tiene ninguna pelicula alquilada.");
				} else {
					peliculaAlquiladaAVer = true;
				}
			}
		});

		eventosBotonesOpciones();
	}

	private int indexBotonesOpciones = 0;

	/**
	 * Método para agrupar todos los botones de las opciones con un addMouseListener
	 * único:
	 */
	private void eventosBotonesOpciones() {
		for (indexBotonesOpciones = 0; indexBotonesOpciones < arrayBotonesOpciones.size(); indexBotonesOpciones++) {
			arrayBotonesOpciones.get(indexBotonesOpciones).addMouseListener(new MouseAdapter() {
				private int myIndex;

				{
					this.myIndex = indexBotonesOpciones;
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					arrayBotonesOpciones.get(myIndex).setBorder(new LineBorder(Color.ORANGE, 2));
					arrayBotonesOpciones.get(myIndex).setForeground(Color.ORANGE);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					arrayBotonesOpciones.get(myIndex).setBorder(null);
					arrayBotonesOpciones.get(myIndex).setForeground(Color.WHITE);
				}
			});
		}
	}

	/**
	 * Método para cargar todas las películas en el gridLayout creando botones para
	 * cada una:
	 */
	private void agregarPeliculasAlPanel() {
		arrayPeliculas = new ArrayList<Pelicula>();
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
			eventosBotonesPeplicula();
		}
	}

	private int indexBotonesPelicula = 0;

	/*
	 * Método de eventos para los botones de las películas:
	 */
	private void eventosBotonesPeplicula() {
		for (int i = 0; i < arrayBotonesPelicula.size(); i++) {
			arrayBotones[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < arrayBotones.length; i++) {
						if (arrayBotones[i].isSelected() == true) {
							// Al accionar una de las películas creamos de nuevo
							// el panel con la película selecccionada:
							PanelPelicula panelPelicula = new PanelPelicula(collector, clienteActual,
									arrayBotonesPelicula.get(i).getPelicula(), peliculaAlquiladaAVer);
							scrollContenedorPaneles.setViewportView(panelPelicula);
							// Toca deseleccionar todos:
							for (int j = 0; j < arrayBotones.length; j++) {
								arrayBotones[j].setSelected(false);
								arrayBotones[j].setBorder(new LineBorder(SystemColor.textHighlight));
							}
							arrayBotones[i].setBorder(new LineBorder(Color.GREEN, 3));
							break;
						}
					}
				}
			});
		}

		for (indexBotonesPelicula = 0; indexBotonesPelicula < arrayBotones.length; indexBotonesPelicula++) {
			arrayBotones[indexBotonesPelicula].addMouseListener(new MouseAdapter() {
				private int myIndex;

				{
					this.myIndex = indexBotonesPelicula;
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					arrayBotones[myIndex].setBorder(new LineBorder(Color.ORANGE, 3));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					arrayBotones[myIndex].setBorder(new LineBorder(SystemColor.textHighlight));
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

	private void buscarPeliculasPorGenero(String categoria) {
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
	 * pareceidos a la búsqueda que hayas puesto, eso si no lo has escrito == a una
	 * de las peliculas que exista!
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

	private void obtenerPeliculasNuevas() {
		// Nuevo panel:
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(panel);
		panel.setLayout(gl_panel);

		boolean correcto = false;
		List<Pelicula> arrayPeliculasNuevas = new ArrayList<Pelicula>();
		// Primero obtenemos las películas de la base de datos:
		try {
			arrayPeliculasNuevas = collector.obtenerPeliculasNuevas(arrayPeliculasNuevas);
			correcto = true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (correcto == true) {
			arrayBotones = new JToggleButton[arrayPeliculasNuevas.size()];
			arrayBotonesPelicula = new ArrayList<BotonPelicula>();
			// Segundo buscamos el id asociado a cada película y lo cotejamos
			// con el
			// id de las imágenes:
			for (int i = 0; i < arrayPeliculasNuevas.size(); i++) {

				// Inicializamos cada BOTON!:

				ImageIcon icon = null;
				icon = getImageIconPelicula(arrayPeliculasNuevas.get(i).getImage());

				arrayBotones[i] = new JToggleButton(icon);
				arrayBotones[i].setContentAreaFilled(false);
				arrayBotones[i].setBorder(new LineBorder(SystemColor.textHighlight));
				arrayBotonesPelicula.add(new BotonPelicula(arrayPeliculasNuevas.get(i)));

				// Añadimos botón de la película al panel asignado para ello:
				panel.add(arrayBotones[i]);
			}
		}

		eventosBotonesPeplicula();
	}

	private boolean obtenerPeliculasRecomendadas() {
		// Nuevo panel:
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(panel);
		panel.setLayout(gl_panel);

		boolean correcto = false;
		boolean algunaRecomendacion = false;
		int arraySize = 0;
		List<Recomendacion> arrayRecomendaciones = new ArrayList<Recomendacion>();
		try {
			arrayRecomendaciones = collector.obtenerRecomendaciones(arrayRecomendaciones);
			correcto = true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Comprobamos cuantas recomendaciones tenemos:
		for (int i = 0; i < arrayRecomendaciones.size(); i++) {
			if (arrayRecomendaciones.get(i).getAmigo().getNombreUsuario().equals(usuarioActual.getNombreUsuario())) {
				arraySize++;
			}
		}

		int posArrayBotones = 0;
		if (correcto == true) {
			arrayBotones = new JToggleButton[arraySize];
			arrayBotonesPelicula = new ArrayList<BotonPelicula>();
			for (int i = 0; i < arrayRecomendaciones.size(); i++) {

				// Buscamos las películas que se hayan recomendado a este
				// usuario:
				if (arrayRecomendaciones.get(i).getAmigo().getNombreUsuario()
						.equals(usuarioActual.getNombreUsuario())) {
					ImageIcon icon = null;
					icon = getImageIconPelicula(arrayRecomendaciones.get(i).getPelicula().getImage());

					arrayBotones[posArrayBotones] = new JToggleButton(icon);
					// Mostramos también el nombre del amigo que se lo ha
					// recomendado:
					arrayBotones[posArrayBotones]
							.setText("Recomendada por: " + arrayRecomendaciones.get(i).getUsuario().getNombreUsuario());
					arrayBotones[posArrayBotones].setForeground(Color.GREEN);
					arrayBotones[posArrayBotones].setContentAreaFilled(false);
					arrayBotones[posArrayBotones].setBorder(new LineBorder(SystemColor.textHighlight));
					arrayBotonesPelicula.add(new BotonPelicula(arrayRecomendaciones.get(i).getPelicula()));

					// Añadimos botón de la película al panel asignado para
					// ello:
					panel.add(arrayBotones[posArrayBotones]);
					posArrayBotones++;
					algunaRecomendacion = true;
				}
			}
		}

		eventosBotonesPeplicula();

		return algunaRecomendacion;
	}

	private boolean obtenerPeliculasAlquiladas() {
		// Nuevo panel:
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(panel);
		panel.setLayout(gl_panel);

		boolean correcto = false;
		boolean algunaAlquilada = false;
		int arraySize = 0;
		List<Alquiler> arrayPeliculasAlquiladas = new ArrayList<Alquiler>();
		try {
			arrayPeliculasAlquiladas = collector.obtenerPeliculasAlquiladas(arrayPeliculasAlquiladas);
			correcto = true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Comprobamos cuantas arrayPeliculasAlquiladas tenemos:
		for (int i = 0; i < arrayPeliculasAlquiladas.size(); i++) {
			if (arrayPeliculasAlquiladas.get(i).getCliente().getNombre().equals(clienteActual.getNombre())
					&& arrayPeliculasAlquiladas.get(i).getCliente().getApellidos()
							.equals(clienteActual.getApellidos())) {
				arraySize++;
			}
		}

		int posArrayBotones = 0;
		if (correcto == true) {
			arrayBotones = new JToggleButton[arraySize];
			arrayBotonesPelicula = new ArrayList<BotonPelicula>();
			for (int i = 0; i < arrayPeliculasAlquiladas.size(); i++) {
				if (arrayPeliculasAlquiladas.get(i).getCliente().getNombre().equals(clienteActual.getNombre())
						&& arrayPeliculasAlquiladas.get(i).getCliente().getApellidos()
								.equals(clienteActual.getApellidos())) {
					ImageIcon icon = null;
					icon = getImageIconPelicula(
							arrayPeliculasAlquiladas.get(i).getInventario().getPelicula().getImage());

					arrayBotones[posArrayBotones] = new JToggleButton(icon);
					arrayBotones[posArrayBotones].setForeground(Color.GREEN);
					arrayBotones[posArrayBotones].setContentAreaFilled(false);
					arrayBotones[posArrayBotones].setBorder(new LineBorder(SystemColor.textHighlight));
					arrayBotonesPelicula
							.add(new BotonPelicula(arrayPeliculasAlquiladas.get(i).getInventario().getPelicula()));

					// Añadimos botón de la película al panel asignado para
					// ello:
					panel.add(arrayBotones[posArrayBotones]);
					posArrayBotones++;
					algunaAlquilada = true;
				}
			}
		}

		eventosBotonesPeplicula();

		return algunaAlquilada;
	}

	public void actualizarPanelUsuariosAmigos() {
		pau = new PanelAmigosUsuarios(collector, "USUARIOS", usuarioActual);
		scrollPane_1.setViewportView(pau);
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
