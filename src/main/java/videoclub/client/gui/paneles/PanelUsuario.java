package videoclub.client.gui.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import videoclub.client.utiles.Temas;
import videoclub.client.utiles.UrlToImage;
import videoclub.server.gui.ICollector;
import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.PeliculaFavorita;
import videoclub.server.jdo.PeliculaPendiente;
import videoclub.server.jdo.PeliculaVista;
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
	private UrlToImage imageIconBotonAnterio;
	private UrlToImage imageIconBotonSiguiente;
	
	private ActionListener actionListenerPOP = new PopUpActionListener();
	private JPopupMenu Pmenu;
	private JMenuItem menuItem;
	public Pelicula peliculaAGuardar;


	/**
	 * Create the panel.
	 */
	public PanelUsuario(ICollector collector) {
		this.collector = collector;
		this.imageIconBotonAnterio = new UrlToImage("https://icon-icons.com/icons2/10/PNG/256/above_thearrow_1550.png");
		this.imageIconBotonSiguiente = new UrlToImage("https://icon-icons.com/icons2/10/PNG/256/Next_arrow_1559.png");
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
		Anterior = new JButton();
		Siguiente = new JButton();
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
		Anterior.setBorderPainted(false);
		Siguiente.setBorderPainted(false);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		Anterior.setContentAreaFilled(false);
		Siguiente.setContentAreaFilled(false);
		Anterior.setBounds(12, 254, 32, 25);
		Anterior.setIcon(new ImageIcon(imageIconBotonAnterio.getImageIcon().getImage()
				.getScaledInstance(Anterior.getWidth(), Anterior.getHeight(), Image.SCALE_SMOOTH)));
		Siguiente.setBounds(1231, 254, 32, 25);
		Siguiente.setIcon(new ImageIcon(imageIconBotonSiguiente.getImageIcon().getImage()
				.getScaledInstance(Siguiente.getWidth(), Siguiente.getHeight(), Image.SCALE_SMOOTH)));
		lblNewLabel.setBorder(new LineBorder(Color.GREEN));
		labelDinero.setBorder(null);
		labelDinero.setHorizontalAlignment(SwingConstants.CENTER);
		comboBoxTema.setBorder(new LineBorder(Color.MAGENTA));
		scrollPane.setBounds(56, 112, 1163, 302);
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
		add(Anterior);
		add(Siguiente);

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
					scrollPane.setBounds(298, 112, 921, 302);
					// Recogemos panel usuariosamigos
					scrollPane_1.setBounds(12, 501, 232, 205);
					// Recogemos botón anterior:
					Anterior.setBounds(252, 254, 32, 25);
					// Actualizamos componentes:
					updatePanel();
					panelOpcionesRecogido = false;

				} else {
					// Lo recogemos:
					panelOpciones.setSize(panelOpciones.getWidth(), alturaPanelOpcionesInicial);
					// Expandimos panel películas
					scrollPane.setBounds(56, 112, 1163, 302);
					// Expandimos panel usuariosamigos
					scrollPane_1.setBounds(12, 427, 232, 279);
					// Expandimos botón anterior:
					Anterior.setBounds(12, 254, 32, 25);
					// Actualizamos componentes:
					updatePanel();
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
		Anterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarBotonesPeliculasAnteriores();
			}
		});
		Siguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ultimoBotonAgregado + 1 < sizeArrayBotones) {
					agregarBotonesPeliculasSiguientes();
				}
			}
		});
		btnListaFavoritos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (obtenerPeliculasFavoritas() == false) {
					JOptionPane.showMessageDialog(null, "¡No tiene ninguna película su lista de favoritos!");
				} else {
					peliculaAlquiladaAVer = false;
				}
			}
		});
		btnPeliculasPendientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (obtenerPeliculasPendientes() == false) {
					JOptionPane.showMessageDialog(null, "No tiene ninguna pelÌcula en pendientes.");
				} else {
					peliculaAlquiladaAVer = false;
				}
			}
		});
		btnPeliculasVistas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (obtenerPeliculasVistas() == false) {
					JOptionPane.showMessageDialog(null, "No ha visto todavÌa ninguna pelÌcula.");
				}else {
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

	private int ultimoBotonAgregado = 0;
	private int sizeArrayBotones = 0;
	private int botonesMaximosPorPantalla = 0;

	/**
	 * Método que se va a encargar de agregar soloamente una cantidad de películas
	 * al panel automáticamente:
	 * 
	 */
	private void agregarBotonesPeliculasAlPanel(int maximosPorPantalla) {

		this.botonesMaximosPorPantalla = maximosPorPantalla;

		// Nuevo panel:
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(panel);
		panel.setLayout(gl_panel);

		sizeArrayBotones = arrayBotones.length;
		// Agregamos botones:
		for (int i = ultimoBotonAgregado; i < botonesMaximosPorPantalla; i++) {
			// Comprobamos antes si i es menor que cantidadBotonesMaximos:
			if (i < sizeArrayBotones) {
				// Entonces podemos agregar:
				// Añadimos botón de la película al panel asignado para ello:
				panel.add(arrayBotones[i]);
				ultimoBotonAgregado = i;
			}
		}
	}

	private void agregarBotonesPeliculasSiguientes() {
		// Nuevo panel:
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(panel);
		panel.setLayout(gl_panel);

		int devaluador = 0;
		sizeArrayBotones = arrayBotones.length;
		// Agregamos botones:
		for (int i = ultimoBotonAgregado + 1; devaluador < botonesMaximosPorPantalla; i++) {
			// Comprobamos antes si i es menor que cantidadBotonesMaximos:
			if (i < sizeArrayBotones) {
				// Entonces podemos agregar:
				// Añadimos botón de la película al panel asignado para ello:
				panel.add(arrayBotones[i]);
				ultimoBotonAgregado = i;
			}

			devaluador++;
		}
	}

	private void agregarBotonesPeliculasAnteriores() {
		// Nuevo panel:
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(panel);
		panel.setLayout(gl_panel);

		int devaluador = 0;
		sizeArrayBotones = arrayBotones.length;
		// Agregamos botones:
		for (int i = 0; devaluador < botonesMaximosPorPantalla; i++) {
			// Comprobamos antes si i es menor que cantidadBotonesMaximos:
			if (i < sizeArrayBotones) {
				// Entonces podemos agregar:
				// Añadimos botón de la película al panel asignado para ello:
				panel.add(arrayBotones[i]);
				ultimoBotonAgregado = i;
			}

			devaluador++;
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
			}

			ultimoBotonAgregado = 0;
			agregarBotonesPeliculasAlPanel(4);
			eventosBotonesPeplicula();
		}
	}

	private int indexBotonesPelicula = 0;
	private JButton Anterior;
	private JButton Siguiente;

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
				public void mouseClicked(MouseEvent e) {
					// MouseEvent.BUTTON3 es el boton derecho
					if (e.getButton() == MouseEvent.BUTTON3) {
						// Preguntamos si quiere guardar la pelÌcula
						// seleccionada en favoritos o en pendientes de ver:
						CrearPopMenu(e);
						peliculaAGuardar = arrayBotonesPelicula.get(myIndex).getPelicula();
					}
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					arrayBotones[myIndex].setBorder(new LineBorder(Color.ORANGE, 3));

					ImageIcon dev = null;
					byte[] bytes = arrayBotonesPelicula.get(myIndex).getPelicula().getImage().getImage();
					BufferedImage image = null;
					InputStream in = new ByteArrayInputStream(bytes);
					try {
						image = ImageIO.read(in);
						dev = new ImageIcon(image.getScaledInstance(210, 285, 0));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					arrayBotones[myIndex].setIcon(dev);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					arrayBotones[myIndex].setBorder(new LineBorder(SystemColor.textHighlight));

					ImageIcon dev = null;
					byte[] bytes = arrayBotonesPelicula.get(myIndex).getPelicula().getImage().getImage();
					BufferedImage image = null;
					InputStream in = new ByteArrayInputStream(bytes);
					try {
						image = ImageIO.read(in);
						dev = new ImageIcon(image.getScaledInstance(195, 270, 0));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					arrayBotones[myIndex].setIcon(dev);
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
					posArrayBotones++;
				}
			}
		}
		ultimoBotonAgregado = 0;
		agregarBotonesPeliculasAlPanel(4);
		eventosBotonesPeplicula();
	}

	private void buscarPeliculasPorGenero(String categoria) {
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
					posArrayBotones++;
				}
			}
			ultimoBotonAgregado = 0;
			agregarBotonesPeliculasAlPanel(4);
			eventosBotonesPeplicula();
		} else {
			JOptionPane.showMessageDialog(null, "No se ha encontrado ninguna película con el género " + categoria);
		}
	}

	private void buscarPeliculaPorNombre(List<String> nombres) {
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
						posArrayBotones++;
					}
				}
			}
			ultimoBotonAgregado = 0;
			agregarBotonesPeliculasAlPanel(4);
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
			}
		}
		ultimoBotonAgregado = 0;
		agregarBotonesPeliculasAlPanel(4);
		eventosBotonesPeplicula();
	}

	private boolean obtenerPeliculasRecomendadas() {
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
					posArrayBotones++;
					algunaRecomendacion = true;
				}
			}
		}
		ultimoBotonAgregado = 0;
		agregarBotonesPeliculasAlPanel(2);
		eventosBotonesPeplicula();

		return algunaRecomendacion;
	}

	private boolean obtenerPeliculasAlquiladas() {
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
					posArrayBotones++;
					algunaAlquilada = true;
				}
			}
		}
		ultimoBotonAgregado = 0;
		agregarBotonesPeliculasAlPanel(4);
		eventosBotonesPeplicula();

		return algunaAlquilada;
	}

	private boolean obtenerPeliculasFavoritas() {
		boolean correcto = false;
		boolean algunaFavorita = false;
		int arraySize = 0;
		List<PeliculaFavorita> arrayPeliculasFavoritas = new ArrayList<PeliculaFavorita>();
		try {
			arrayPeliculasFavoritas = collector.obtenerPeliculasFavoritas(arrayPeliculasFavoritas);
			correcto = true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Comprobamos cuantas PeliculasFavoritas tenemos:
		for (int i = 0; i < arrayPeliculasFavoritas.size(); i++) {
			if (arrayPeliculasFavoritas.get(i).getCliente().getNombre().equals(clienteActual.getNombre())
					&& arrayPeliculasFavoritas.get(i).getCliente().getApellidos()
							.equals(clienteActual.getApellidos())) {
				arraySize++;
			}
		}

		int posArrayBotones = 0;
		if (correcto == true) {
			arrayBotones = new JToggleButton[arraySize];
			arrayBotonesPelicula = new ArrayList<BotonPelicula>();
			for (int i = 0; i < arrayPeliculasFavoritas.size(); i++) {

				// Buscamos las películas que se hayan recomendado a este
				// usuario:
				if (arrayPeliculasFavoritas.get(i).getCliente().getNombre().equals(clienteActual.getNombre())
						&& arrayPeliculasFavoritas.get(i).getCliente().getApellidos()
								.equals(clienteActual.getApellidos())) {
					ImageIcon icon = null;
					icon = getImageIconPelicula(arrayPeliculasFavoritas.get(i).getPelicula().getImage());

					arrayBotones[posArrayBotones] = new JToggleButton(icon);
					arrayBotones[posArrayBotones].setForeground(Color.GREEN);
					arrayBotones[posArrayBotones].setContentAreaFilled(false);
					arrayBotones[posArrayBotones].setBorder(new LineBorder(SystemColor.textHighlight));
					arrayBotonesPelicula.add(new BotonPelicula(arrayPeliculasFavoritas.get(i).getPelicula()));
					posArrayBotones++;
					algunaFavorita = true;
				}
			}
		}
		ultimoBotonAgregado = 0;
		agregarBotonesPeliculasAlPanel(4);
		eventosBotonesPeplicula();

		return algunaFavorita;
	}
	private boolean obtenerPeliculasPendientes() {
		boolean correcto = false;
		boolean algunaPendiente = false;
		int arraySize = 0;
		List<PeliculaPendiente> arrayPeliculasPendientes = new ArrayList<PeliculaPendiente>();
		try {
			arrayPeliculasPendientes = collector.obtenerPeliculasPendientes(arrayPeliculasPendientes);
			correcto = true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Comprobamos cuantas PeliculasFavoritas tenemos:
		for (int i = 0; i < arrayPeliculasPendientes.size(); i++) {
			if (arrayPeliculasPendientes.get(i).getCliente().getNombre().equals(clienteActual.getNombre())
					&& arrayPeliculasPendientes.get(i).getCliente().getApellidos()
							.equals(clienteActual.getApellidos())) {
				arraySize++;
			}
		}

		int posArrayBotones = 0;
		if (correcto == true) {
			arrayBotones = new JToggleButton[arraySize];
			arrayBotonesPelicula = new ArrayList<BotonPelicula>();
			for (int i = 0; i < arrayPeliculasPendientes.size(); i++) {
				if (arrayPeliculasPendientes.get(i).getCliente().getNombre().equals(clienteActual.getNombre())
						&& arrayPeliculasPendientes.get(i).getCliente().getApellidos()
								.equals(clienteActual.getApellidos())) {
					ImageIcon icon = null;
					icon = getImageIconPelicula(arrayPeliculasPendientes.get(i).getPelicula().getImage());

					arrayBotones[posArrayBotones] = new JToggleButton(icon);
					arrayBotones[posArrayBotones].setForeground(Color.GREEN);
					arrayBotones[posArrayBotones].setContentAreaFilled(false);
					arrayBotones[posArrayBotones].setBorder(new LineBorder(SystemColor.textHighlight));
					arrayBotonesPelicula.add(new BotonPelicula(arrayPeliculasPendientes.get(i).getPelicula()));
					posArrayBotones++;
					algunaPendiente = true;
				}
			}
		}
		ultimoBotonAgregado = 0;
		agregarBotonesPeliculasAlPanel(4);
		eventosBotonesPeplicula();

		return algunaPendiente;
	}

	public void guardarPeliculaEnFavoritos() {
		int opcion = JOptionPane.showConfirmDialog(null,
				"øQuieres guardar la pelÌcula: " + peliculaAGuardar.getNombre() + " en favoritos?");
		if (opcion == 0) {
			// Se guardar·:
			// Comprobamos primero que la pelÌcula a guardar no
			// estÈ ya en favoritos:
			List<PeliculaFavorita> arrayPeliculasFavoritas = new ArrayList<PeliculaFavorita>();
			try {
				arrayPeliculasFavoritas = collector.obtenerPeliculasFavoritas(arrayPeliculasFavoritas);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			boolean peliculaExisteEnFavoritos = false;
			for (PeliculaFavorita peli : arrayPeliculasFavoritas) {
				if (peli.getPelicula().getNombre().equals(peliculaAGuardar.getNombre())
						&& peli.getCliente().getNombre().equals(clienteActual.getNombre())
						&& peli.getCliente().getApellidos().equals(clienteActual.getApellidos())) {
					peliculaExisteEnFavoritos = true;
				}
			}

			if (peliculaExisteEnFavoritos == false) {
				// Guardamos entonces en favoritos:
				try {
					if (collector.setPeliculaFavorita(peliculaAGuardar, clienteActual) == true) {
						JOptionPane.showMessageDialog(null, "°PelÌcula gaurdada correctamente en favoritos!");
					} else {
						JOptionPane.showMessageDialog(null, "°Error al guardar pelÌcula en favoritos!", "°ERROR!",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "°Esa pelÌcula ya existe en tu lista de favoritos!", "°ERROR!",
						JOptionPane.ERROR_MESSAGE);
			}

		} else {
			// No se guardar·:
			JOptionPane.showMessageDialog(null, "°OperaciÛn cancelada!");
		}
	}

	public void guardarPeliculaEnPendientes() {
		int opcion = JOptionPane.showConfirmDialog(null,
				"øQuieres guardar la pelÌcula: " + peliculaAGuardar.getNombre() + " en la lista de pendientes?");
		if (opcion == 0) {
			// Se guardar·:
			// Comprobamos primero que la pelÌcula a guardar no
			// estÈ ya en pendientes:
			List<PeliculaPendiente> arrayPeliculasPendientes = new ArrayList<PeliculaPendiente>();
			try {
				arrayPeliculasPendientes = collector.obtenerPeliculasPendientes(arrayPeliculasPendientes);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			boolean peliculaExisteEnPendiente = false;
			for (PeliculaPendiente peli : arrayPeliculasPendientes) {
				if (peli.getPelicula().getNombre().equals(peliculaAGuardar.getNombre())
						&& peli.getCliente().getNombre().equals(clienteActual.getNombre())
						&& peli.getCliente().getApellidos().equals(clienteActual.getApellidos())) {
					peliculaExisteEnPendiente = true;
				}
			}

			if (peliculaExisteEnPendiente == false) {
				// Guardamos entonces en pendientes:
				try {
					if (collector.setPeliculaPendiente(peliculaAGuardar, clienteActual) == true) {
						JOptionPane.showMessageDialog(null, "°PelÌcula gaurdada correctamente en pendientes!");
					} else {
						JOptionPane.showMessageDialog(null, "°Error al guardar pelÌcula en pendientes!", "°ERROR!",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "°Esa pelÌcula ya existe en tu lista de pendientes!", "°ERROR!",
						JOptionPane.ERROR_MESSAGE);
			}

		} else {
			// No se guardar·:
			JOptionPane.showMessageDialog(null, "°OperaciÛn cancelada!");
		}
	}
	private boolean obtenerPeliculasVistas() {
		boolean correcto = false;
		boolean algunaVista = false;
		int arraySize = 0;
		List<PeliculaVista> arrayPeliculasVistas = new ArrayList<PeliculaVista>();
		try {
			arrayPeliculasVistas = collector.obtenerPeliculasVistas(arrayPeliculasVistas);
			correcto = true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Comprobamos cuantas PeliculasFavoritas tenemos:
		for (int i = 0; i < arrayPeliculasVistas.size(); i++) {
			if (arrayPeliculasVistas.get(i).getCliente().getNombre().equals(clienteActual.getNombre())
					&& arrayPeliculasVistas.get(i).getCliente().getApellidos().equals(clienteActual.getApellidos())) {
				arraySize++;
			}
		}

		int posArrayBotones = 0;
		if (correcto == true) {
			arrayBotones = new JToggleButton[arraySize];
			arrayBotonesPelicula = new ArrayList<BotonPelicula>();
			for (int i = 0; i < arrayPeliculasVistas.size(); i++) {
				if (arrayPeliculasVistas.get(i).getCliente().getNombre().equals(clienteActual.getNombre())
						&& arrayPeliculasVistas.get(i).getCliente().getApellidos()
								.equals(clienteActual.getApellidos())) {
					ImageIcon icon = null;
					icon = getImageIconPelicula(arrayPeliculasVistas.get(i).getPelicula().getImage());

					arrayBotones[posArrayBotones] = new JToggleButton(icon);
					arrayBotones[posArrayBotones].setForeground(Color.GREEN);
					arrayBotones[posArrayBotones].setContentAreaFilled(false);
					arrayBotones[posArrayBotones].setBorder(new LineBorder(SystemColor.textHighlight));
					arrayBotonesPelicula.add(new BotonPelicula(arrayPeliculasVistas.get(i).getPelicula()));
					posArrayBotones++;
					algunaVista = true;
				}
			}
		}
		ultimoBotonAgregado = 0;
		agregarBotonesPeliculasAlPanel(4);
		eventosBotonesPeplicula();

		return algunaVista;
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
		private void updatePanel() {
		this.repaint();
		this.validate();
	}
		
		// Define ActionListener:
		// Clase para las escuchas del Popup:
		public class PopUpActionListener implements ActionListener {

			public void actionPerformed(ActionEvent actionEvent) {
				// Obtenemos lo que ha elegido el cliente:
				String opcion = actionEvent.getActionCommand();

				// Comprobamos opciones:
				if (opcion.equals("Guardar en favoritos.")) {
					guardarPeliculaEnFavoritos();
				} else if (opcion.equals("Guardar como pendiente de ver.")) {
					guardarPeliculaEnPendientes();
				}
			}
		}

		/*
		 * MÈtodo para crear un PopMen˙:
		 */
		private void CrearPopMenu(MouseEvent evt) {
			// AÒadimos items al PopMun˙:
			Pmenu = new JPopupMenu();
			// Creamos una nueva instancia
			menuItem = new JMenuItem("Guardar en favoritos.");
			// Creamos nuevo item
			Pmenu.addSeparator();
			// AÒadimos separador
			menuItem.addActionListener(actionListenerPOP);
			// AÒadimos escucha al item
			Pmenu.add(menuItem);
			// AÒadimos item al men˙
			menuItem = new JMenuItem("Guardar como pendiente de ver.");
			// Creamos nuevo item
			Pmenu.addSeparator();
			// AÒadimos separador
			menuItem.addActionListener(actionListenerPOP);
			// AÒadimos escucha al item
			Pmenu.add(menuItem);
			// AÒadimos item al men˙
			Pmenu.show(evt.getComponent(), evt.getX(), evt.getY());
			// Mostramos men˙ en la pos 0,0 del ratÛn
		}

}
