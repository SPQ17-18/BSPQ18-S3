package videoclub.client.gui.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import videoclub.client.gui.ventanas.ClientEstadisticasFrame;
import videoclub.client.utiles.Resaltador;
import videoclub.server.collector.ICollector;
import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Inventario;
import videoclub.server.jdo.NotificarAlquiler;

import javax.swing.SwingConstants;

public class PanelAdministrador extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int anchuraPanel = 1080;
	private int alturaPanel = 720;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnMostrarInventarioDe;
	private JButton btnMostrarAlquileresDe;
	private JButton btnMostrarClientes;
	private JButton btnInsertarNuevaPelcula;
	private JLabel label;
	private JTextField textFieldNombrePelicula;
	private JComboBox<Integer> comboBoxAnyo;
	private JComboBox<Integer> comboBoxDuracion;
	private JComboBox<String> comboBoxCategoria;
	private JTextPane textPaneDescripcion;
	private JScrollPane scrollPane_2;
	private JComboBox<Float> comboBoxPrecio;
	private JTextPane textPaneMostrarDescripcion;
	private JScrollPane scrollPane_3;

	// Para obtener ruta im·genes:
	private JFileChooser jF1 = new JFileChooser();
	private JLabel LabelImage;
	private boolean imagenSubida = false;
	private Imagen imagen;
	private Path ruta;

	private DefaultTableModel tableModel = new DefaultTableModel();
	private JTable table_1;
	private JScrollPane scrollPane_1;

	private ICollector collector; // Collector implementado desde el "ClienfFrame"
	private JComboBox<Integer> comboBoxCantidad;

	private int anchuraInicialTabla = 1056;
	private int anchuraFinalTabla = 1056;
	private int alturaInicialTabla = 146;
	private int alturaFinalTabla = 640;
	private boolean isExpanded = false;
	private JButton btnAadirNuevaNoticia;
	private JButton btnAadirNuevoPrximo;

	/**
	 * Create the panel.
	 */
	public PanelAdministrador(ICollector collector) {
		this.collector = collector;
		inicializar();
		componentes();
		aÒadirComponentes();
		eventos();

		valoresComboBoxCategorias();
		valoresComboBoxAÒos();
		valoresComoboBoxDuraciones();
		valoresComboBoxPrecios();
		valoresComboBoxCantidad();
	}

	private void inicializar() {
		btnAadirNuevoPrximo = new JButton("CREAR NUEVO ESTRENO");
		btnAadirNuevaNoticia = new JButton("CREAR NUEVA NOTICIA");
		btnMostrarInventarioDe = new JButton("INVENTARIO");
		btnMostrarAlquileresDe = new JButton("ALQUILERES");
		btnMostrarClientes = new JButton("CLIENTES");
		btnInsertarNuevaPelcula = new JButton("Insertar nueva pel\u00EDcula");
		label = new JLabel();
		scrollPane_1 = new JScrollPane();
		table_1 = new JTable();
		textFieldNombrePelicula = new JTextField();
		comboBoxAnyo = new JComboBox<Integer>();
		comboBoxDuracion = new JComboBox<Integer>();
		comboBoxCategoria = new JComboBox<String>();
		scrollPane_2 = new JScrollPane();
		textPaneDescripcion = new JTextPane();
		comboBoxPrecio = new JComboBox<Float>();
		comboBoxCantidad = new JComboBox<Integer>();
		LabelImage = new JLabel();
		scrollPane = new JScrollPane();
		table = new JTable();
		scrollPane_3 = new JScrollPane();
		textPaneMostrarDescripcion = new JTextPane();
		LabelFondo = new JLabel();
		chckbxNovedad = new JCheckBox("Novedad");
		btnNotificaciones = new JButton("NOTIFICACIONES");
		btnEstadisticas = new JButton("ESTADISTICAS");
	}

	private void componentes() {
		btnAadirNuevoPrximo.setHorizontalAlignment(SwingConstants.CENTER);
		btnAadirNuevaNoticia.setHorizontalAlignment(SwingConstants.CENTER);
		btnMostrarInventarioDe.setHorizontalAlignment(SwingConstants.CENTER);
		btnMostrarAlquileresDe.setHorizontalAlignment(SwingConstants.CENTER);
		btnMostrarClientes.setHorizontalAlignment(SwingConstants.CENTER);
		btnAadirNuevaNoticia.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAadirNuevaNoticia.setBounds(692, 13, 179, 41);
		btnAadirNuevoPrximo.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAadirNuevoPrximo.setBounds(883, 13, 185, 41);
		btnMostrarInventarioDe.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMostrarInventarioDe.setBounds(12, 13, 120, 41);
		btnMostrarAlquileresDe.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMostrarAlquileresDe.setBounds(144, 13, 120, 41);
		btnMostrarClientes.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMostrarClientes.setBounds(276, 13, 100, 41);
		btnInsertarNuevaPelcula.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnInsertarNuevaPelcula.setBounds(22, 667, 267, 27);
		scrollPane.setBounds(12, 67, 1056, 146);
		label.setBorder(new LineBorder(SystemColor.textHighlight));
		label.setBounds(12, 454, 1056, 253);
		table.setCellSelectionEnabled(true);
		table.setRowHeight(20);
		table.setGridColor(new Color(0, 51, 102));
		table.setFont(new Font("Tahoma", Font.BOLD, 15));
		table.setSelectionBackground(new Color(0, 0, 153));
		table.setSelectionForeground(new Color(0, 204, 102));
		scrollPane_1.setBounds(12, 226, 1056, 76);
		table_1.setSelectionForeground(new Color(0, 204, 102));
		table_1.setSelectionBackground(new Color(0, 0, 153));
		table_1.setRowHeight(20);
		table_1.setGridColor(new Color(0, 51, 102));
		table_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		table_1.setCellSelectionEnabled(true);
		textFieldNombrePelicula.setBackground(Color.DARK_GRAY);
		textFieldNombrePelicula.setBorder(new TitledBorder(null, "NOMBRE DE LA PELICULA", TitledBorder.CENTER,
				TitledBorder.TOP, null, SystemColor.textHighlight));
		textFieldNombrePelicula.setBounds(22, 465, 267, 48);
		textFieldNombrePelicula.setColumns(10);
		comboBoxAnyo.setBackground(Color.DARK_GRAY);
		comboBoxAnyo.setBorder(new TitledBorder(null, "A\u00D1O", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 120, 215)));
		comboBoxAnyo.setBounds(427, 472, 115, 41);
		comboBoxDuracion.setBorder(new TitledBorder(null, "DURACION", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 120, 215)));
		comboBoxDuracion.setBackground(Color.DARK_GRAY);
		comboBoxDuracion.setBounds(748, 472, 127, 41);
		comboBoxCategoria.setBorder(new TitledBorder(null, "CATEGORIA", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 120, 215)));
		comboBoxCategoria.setBackground(Color.DARK_GRAY);
		comboBoxCategoria.setBounds(557, 472, 179, 41);
		scrollPane_2.setBounds(301, 526, 755, 168);
		scrollPane_2.setViewportView(textPaneDescripcion);
		textPaneDescripcion.setBackground(Color.DARK_GRAY);
		textPaneDescripcion.setBorder(new TitledBorder(null, "DESCRIPCION", TitledBorder.CENTER, TitledBorder.TOP, null,
				SystemColor.textHighlight));
		scrollPane_1.setBorder(new TitledBorder(null,
				"Pel\u00EDculas alquiladas por los clientes - Nombre y descripci\u00F3n del cliente en particular",
				TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.textHighlight));
		comboBoxPrecio.setBorder(
				new TitledBorder(null, "PRECIO", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 120, 215)));
		comboBoxPrecio.setBackground(Color.DARK_GRAY);
		comboBoxPrecio.setBounds(301, 472, 114, 41);
		comboBoxCantidad.setBorder(new TitledBorder(null, "CANTIDAD", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 120, 215)));
		comboBoxCantidad.setBackground(Color.DARK_GRAY);
		comboBoxCantidad.setBounds(887, 472, 169, 41);
		LabelImage.setBorder(new LineBorder(SystemColor.textHighlight));
		LabelImage.setBounds(32, 514, 100, 140);
		textPaneMostrarDescripcion.setForeground(Color.WHITE);
		textPaneMostrarDescripcion.setBackground(Color.DARK_GRAY);
		textPaneMostrarDescripcion.setBorder(new TitledBorder(null,
				"Descripci\u00F3n total de la pel\u00EDcula (primero click en descripci\u00F3n de pel\u00EDcula)",
				TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.textHighlight));
		scrollPane_3.setBounds(12, 315, 1056, 126);
		scrollPane.setBorder(new LineBorder(Color.ORANGE, 3));
		LabelFondo.setBackground(Color.DARK_GRAY);
		LabelFondo.setOpaque(true);
		LabelFondo.setBounds(12, 67, 1056, 146);
		chckbxNovedad.setBounds(154, 571, 113, 25);
		btnNotificaciones.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNotificaciones.setBounds(388, 13, 139, 41);
		btnEstadisticas.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEstadisticas.setBounds(541, 14, 139, 41);
	}

	private void aÒadirComponentes() {
		setSize(anchuraPanel, alturaPanel);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		setBorder(null);

		add(scrollPane);
		add(LabelFondo);
		add(btnMostrarInventarioDe);
		add(btnMostrarAlquileresDe);
		add(btnMostrarClientes);
		add(btnInsertarNuevaPelcula);
		add(textFieldNombrePelicula);
		add(comboBoxAnyo);
		add(comboBoxDuracion);
		add(comboBoxCategoria);
		add(comboBoxPrecio);
		add(scrollPane_2);
		add(LabelImage);
		add(label);
		add(scrollPane_1);
		add(comboBoxCantidad);
		add(scrollPane_3);
		add(chckbxNovedad);
		add(btnAadirNuevaNoticia);
		add(btnAadirNuevoPrximo);
		add(btnNotificaciones);
		add(btnEstadisticas);
		
		scrollPane_3.setViewportView(textPaneMostrarDescripcion);
		scrollPane.setViewportView(table);
		scrollPane_1.setViewportView(table_1);
	}

	private void eventos() {
		btnMostrarInventarioDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPeliculas();
				peliculasAlquiladasEnTabla = false;
				peliculasDescripcionEnTabla = true;
				clientesDescripcionEnTabla = false;
			}
		});
		btnMostrarAlquileresDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarAlquileres();
				peliculasEnTabla = false;
				peliculasAlquiladasEnTabla = true;
				peliculasDescripcionEnTabla = false;
				clientesDescripcionEnTabla = false;
			}
		});
		btnMostrarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarClientes();
				peliculasEnTabla = false;
				peliculasAlquiladasEnTabla = false;
				peliculasDescripcionEnTabla = false;
				clientesDescripcionEnTabla = true;
			}
		});
		btnInsertarNuevaPelcula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Antes de insertar hay que comprobar si los campos est·n
				// correctos:
				boolean camposCorrectos = true;
				if (textFieldNombrePelicula.getText().equals("")) {
					camposCorrectos = false;
				}
				if (textPaneDescripcion.getText().equals("")) {
					camposCorrectos = false;
				}

				if (camposCorrectos == true) {
					insertarNuevaPeliculaEnLaBD();
				} else {
					JOptionPane.showMessageDialog(null, "°LOS CAMPOS EST¡N INCOMPLETOS!", "°ERROR!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (peliculasAlquiladasEnTabla == true) {
					if (table.isCellSelected(table.getSelectedRow(), 2)) {
						mostrarClienteDelAlquiler();
					}
					if (table.isCellSelected(table.getSelectedRow(), 3)) {
						mostrarPeliculaAlquiladaDelAlquiler();
					}
				} else if (peliculasDescripcionEnTabla == true) {
					if (table.isCellSelected(table.getSelectedRow(), 2)) {
						mostrarDescripcionDePeliculas();
					}
				} else if (clientesDescripcionEnTabla == true) {
					if (table.isCellSelected(table.getSelectedRow(), 0)) {
						DefaultTableModel tm = (DefaultTableModel) table.getModel();
						String cliente = (String) tm.getValueAt(table.getSelectedRow(), 0);
						String apellidos = (String) tm.getValueAt(table.getSelectedRow(), 1);
						String fechaNacimiento = tm.getValueAt(table.getSelectedRow(), 2).toString();
						int opcion = JOptionPane.showConfirmDialog(null,
								"¯Desea eliminar al cliente: " + cliente + " ?");
						// La opci€n 0 es un SI:
						if (opcion == 0) {
							eliminarCliente(cliente, apellidos, fechaNacimiento);
							// La opci€n 1 es un NO:
						} else if (opcion == 1) {
							JOptionPane.showMessageDialog(null, "El cliente: " + cliente + " no ha sido eliminado.");
							// Sino es igual a CANCELAR:
						} else {
							JOptionPane.showMessageDialog(null, "Operaci€n cancelada.");
						}
					}
				}
				rowSelected = table.getSelectedRow();
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				// Si se clicka dos veces en la tabla se expendir· ocupando todo
				// el panel! :D
				if (mouseEvent.getClickCount() >= 2) {
					if (isExpanded == false) {
						// Expandimos
						scrollPane.setSize(anchuraFinalTabla, alturaFinalTabla);
						LabelFondo.setSize(anchuraFinalTabla, alturaFinalTabla);
						isExpanded = true;
					} else {
						// Conmprimimos:
						scrollPane.setSize(anchuraInicialTabla, alturaInicialTabla);
						LabelFondo.setSize(anchuraInicialTabla, alturaInicialTabla);
						isExpanded = false;
					}
				}
			}
		});
		LabelImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				imagenSubida = false;
				jF1.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// Ahora tenemos que conseguir una imane a subir a la bd:
				if (jF1.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

					// Obtenemos ruta de la im·gen:
					ruta = jF1.getSelectedFile().toPath();

					// Guardamos imagen para introeducir en bd:
					try {
						byte[] data = Files.readAllBytes(ruta);
						imagen = new Imagen(jF1.getSelectedFile().getName(), data);

						// Mostramos imagen el el label:
						byte[] bytes = imagen.getImage();
						BufferedImage image = null;
						InputStream in = new ByteArrayInputStream(bytes);
						image = ImageIO.read(in);
						LabelImage.setIcon(new ImageIcon(image.getScaledInstance(100, 140, 0)));

						// Imagen subida correcto!
						imagenSubida = true;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});
		btnAadirNuevaNoticia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String noticia = JOptionPane.showInputDialog("Inserte una nueva noticia: ");
				if (noticia != null) {
					try {
						if (collector.setNoticia(noticia) != false) {
							JOptionPane.showMessageDialog(null, "Noticia guardada con Èxito.");
						} else {
							JOptionPane.showMessageDialog(null, "Error al guardar noticia.", "ERRRO!",
									JOptionPane.ERROR_MESSAGE);
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnAadirNuevoPrximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String estreno = JOptionPane.showInputDialog("Inserte un nuevo estreno: ");
				if (estreno != null) {
					try {
						if (collector.setProximoEstreno(estreno) != false) {
							JOptionPane.showMessageDialog(null, "Estreno guardado con Èxito.");
						} else {
							JOptionPane.showMessageDialog(null, "Error al guardar estreno.", "ERRRO!",
									JOptionPane.ERROR_MESSAGE);
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnNotificaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarNotificacionesAlquileres();
				peliculasEnTabla = false;
				peliculasAlquiladasEnTabla = false;
				peliculasDescripcionEnTabla = false;
				clientesDescripcionEnTabla = false;
			}
		});
		btnEstadisticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientEstadisticasFrame clientEstadisticasFrame = new ClientEstadisticasFrame(collector);
				clientEstadisticasFrame.setVisible(true);
			}
		});
	}

	private void mostrarNotificacionesAlquileres() {
		tableModel = new DefaultTableModel();
		columnasTabla(3);
		List<NotificarAlquiler> arrayNotificacionesAlquileres = new ArrayList<NotificarAlquiler>();
		try {
			arrayNotificacionesAlquileres = collector.obtenerNotificacionesAlquileres(arrayNotificacionesAlquileres);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < arrayNotificacionesAlquileres.size(); i++) {
			tableModel.addRow(new Object[] { "[" + (i + 1) + "]",
					arrayNotificacionesAlquileres.get(i).getFechaNotificacion().toString(), "ALQUILER", "SI", "SI",
					"NO" });
		}

		// Introducimos el modelo en la tabla:
		table.setModel(tableModel);
		resaltarColumnas(table, 6, new int[] { 0, 1, 2, 3, 4 }, true, new int[] { 3, 4 });
	}

	private void valoresComboBoxCategorias() {
		String[] arrayCategorias = new String[] { "Infantil", "Comedia", "Thriller", "Miedo", "Clasica", "Musical" };
		for (int i = 0; i < arrayCategorias.length; i++) {
			comboBoxCategoria.addItem(arrayCategorias[i]);
		}
	}

	private void valoresComboBoxAÒos() {
		// Introducimos aÒos desde 2017-1900:
		for (int i = 2017; i > 1900; i--) {
			comboBoxAnyo.addItem(i);
		}
	}

	private void valoresComoboBoxDuraciones() {
		// Introducimos duraciones desde 45 minutos hasta 345 minutos:
		for (int i = 345; i > 45; i--) {
			comboBoxDuracion.addItem(i);
		}
	}

	private void valoresComboBoxPrecios() {
		// Introducimos 100 precios diferentes:
		float precio = 0.25F;
		for (int i = 0; i < 100; i++) {
			comboBoxPrecio.addItem(Redondear(precio, 2));
			precio += 0.25F;
		}
	}

	private void valoresComboBoxCantidad() {
		for (int i = 1; i < 100; i++) {
			comboBoxCantidad.addItem(i);
		}
	}

	public float Redondear(float pNumero, int pCantidadDecimales) {
		// the function is call with the values Redondear(625.3f, 2)
		BigDecimal value = new BigDecimal(pNumero);
		value = value.setScale(pCantidadDecimales, RoundingMode.HALF_EVEN);
		return value.floatValue(); // but here the values is 625.3
	}

	/**
	 * MÈtodo para insertar nueva pelÌcula:
	 */
	private void insertarNuevaPeliculaEnLaBD() {
		if (imagenSubida == true) {
			// Primero debemos obtener todos los valores de los campos:
			String nombre = textFieldNombrePelicula.getText();
			String descripcion = textPaneDescripcion.getText();
			float precio = (float) comboBoxPrecio.getSelectedItem();
			int anyo = (int) comboBoxAnyo.getSelectedItem();
			int duracion = (int) comboBoxDuracion.getSelectedItem();
			String categoria = (String) comboBoxCategoria.getSelectedItem();
			int cantidad = (int) comboBoxCantidad.getSelectedItem();

			// Finalmente guardamos la pelÌcula en la base de datos:
			try {
				if (collector.insertarPelicula(nombre, duracion, descripcion.getBytes(), anyo, precio, categoria,
						cantidad, imagen, chckbxNovedad.isSelected()) == true) {
					JOptionPane.showMessageDialog(null,
							"La pelÌcula: " + nombre + " ha sido insertada correctamente en la base de datos.");
				}
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "NO HAS ELEGIDO NINGUNA IM¡GEN PARA LA PELÕCULA...");
		}
	}

	@SuppressWarnings("unused")
	private int rowSelected = 0;

	private void columnasTabla(int i) {
		// Creacion de las columnas de la tabla:
		if (i == 0) {
			tableModel.addColumn("NOMBRE");
			tableModel.addColumn("DURACION");
			tableModel.addColumn("DESCRIPCION");
			tableModel.addColumn("A—O");
			tableModel.addColumn("CATEGORIA");
			tableModel.addColumn("DISPONIBLES");
			tableModel.addColumn("PRECIO");
		} else if (i == 1) {
			tableModel.addColumn("NOMBRE");
			tableModel.addColumn("APELLIDOS");
			tableModel.addColumn("FECHA NACIMIENTO");
			tableModel.addColumn("CALLE");
			tableModel.addColumn("CIUDAD");
			tableModel.addColumn("PAIS");
		} else if (i == 2) {
			tableModel.addColumn("FECHA DE ALQUILER");
			tableModel.addColumn("FECHA DE DEVOLUCION");
			tableModel.addColumn("N∫ CLIENTE");
			tableModel.addColumn("N∫ INVENTARIO");
		} else if (i == 3) {
			tableModel.addColumn("NOTIFICACI”N");
			tableModel.addColumn("FECHA");
			tableModel.addColumn("TIPO");
			tableModel.addColumn("CORRECTO");
			tableModel.addColumn("COMPRADO");
			tableModel.addColumn("DEVUELTO");
		}
	}

	@SuppressWarnings("unused")
	private boolean peliculasEnTabla = false;
	private boolean peliculasAlquiladasEnTabla = false;
	private boolean peliculasDescripcionEnTabla = false;
	private boolean clientesDescripcionEnTabla = false;
	private JLabel LabelFondo;
	private JCheckBox chckbxNovedad;
	private JButton btnNotificaciones;
	private JButton btnEstadisticas;

	private void mostrarPeliculas() {
		tableModel = new DefaultTableModel();
		columnasTabla(0);
		List<Inventario> arrayInventarios = new ArrayList<Inventario>();
		try {
			arrayInventarios = collector.obtenerInventarios(arrayInventarios);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < arrayInventarios.size(); i++) {
			tableModel.addRow(new Object[] { arrayInventarios.get(i).getPelicula().getNombre(),
					arrayInventarios.get(i).getPelicula().getDuracion(),
					openFileToString(arrayInventarios.get(i).getPelicula().getDescripcion()),
					arrayInventarios.get(i).getPelicula().getAnyo(),
					arrayInventarios.get(i).getPelicula().getCategoria().getNombre(),
					arrayInventarios.get(i).getDisponibles(), arrayInventarios.get(i).getPelicula().getPrecio() });
		}

		// Introducimos el modelo en la tabla:
		table.setModel(tableModel);
		resaltarColumnas(table, 7, new int[] { 2, 5, 6 }, true, new int[] { 5, 6 });
		peliculasEnTabla = true;
	}

	private void mostrarAlquileres() {
		tableModel = new DefaultTableModel();
		columnasTabla(2);
		List<Alquiler> arrayAlquileres = new ArrayList<Alquiler>();
		try {
			arrayAlquileres = collector.obtenerAlquileres(arrayAlquileres);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < arrayAlquileres.size(); i++) {
			tableModel.addRow(new Object[] { arrayAlquileres.get(i).getFecha_alquiler(),
					arrayAlquileres.get(i).getFecha_devolucion(), arrayAlquileres.get(i).getCliente(),
					arrayAlquileres.get(i).getInventario() });
		}

		// Introducimos el modelo en la tabla:
		table.setModel(tableModel);
		resaltarColumnas(table, 4, new int[] { 2, 3 }, false, new int[] { -1 });
	}

	private void mostrarClientes() {
		tableModel = new DefaultTableModel();
		columnasTabla(1);
		List<Cliente> arrayClientes = new ArrayList<Cliente>();
		try {
			arrayClientes = collector.obtenerClientes(arrayClientes);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < arrayClientes.size(); i++) {
			tableModel.addRow(new Object[] { arrayClientes.get(i).getNombre(), arrayClientes.get(i).getApellidos(),
					arrayClientes.get(i).getFecha_nacimiento(), arrayClientes.get(i).getDireccion().getCalle(),
					arrayClientes.get(i).getDireccion().getCiudad(), arrayClientes.get(i).getDireccion().getPais() });
		}

		// Introducimos el modelo en la tabla:
		table.setModel(tableModel);
		resaltarColumnas(table, 6, new int[] { -1 }, false, new int[] { -1 });
	}

	private void mostrarClienteDelAlquiler() {
		// Primero obtener el valor de la celda seleccionada:
		DefaultTableModel tm = (DefaultTableModel) table.getModel();
		Cliente cliente = (Cliente) tm.getValueAt(table.getSelectedRow(), 2);

		// Ahora tenemos que buscar el valor en todos los clientes y si
		// corresponde mostrar el cliente:
		tableModel = new DefaultTableModel();
		columnasTabla(1);
		tableModel.addRow(new Object[] { cliente.getNombre(), cliente.getApellidos(), cliente.getFecha_nacimiento(),
				cliente.getDireccion().getCalle(), cliente.getDireccion().getCiudad(),
				cliente.getDireccion().getPais() });

		// Introducimos el modelo en la tabla:
		table_1.setModel(tableModel);
		resaltarColumnas(table_1, 6, new int[] { 0, 1, 2, 3, 4, 5 }, false, new int[] { -1 });
	}

	private void mostrarPeliculaAlquiladaDelAlquiler() {
		// Primero obtener el valor de la celda seleccionada:
		DefaultTableModel tm = (DefaultTableModel) table.getModel();
		Inventario inventario = (Inventario) tm.getValueAt(table.getSelectedRow(), 3);

		tableModel = new DefaultTableModel();
		columnasTabla(0);

		tableModel.addRow(new Object[] { inventario.getPelicula().getNombre(), inventario.getPelicula().getDuracion(),
				openFileToString(inventario.getPelicula().getDescripcion()), inventario.getPelicula().getAnyo(),
				inventario.getPelicula().getCategoria().getNombre(), inventario.getDisponibles(),
				inventario.getPelicula().getPrecio() });

		// Introducimos el modelo en la tabla:
		table_1.setModel(tableModel);
		resaltarColumnas(table_1, 7, new int[] { 0, 1, 2, 3, 4, 5, 6 }, true, new int[] { 5, 6 });
	}

	private void mostrarDescripcionDePeliculas() {
		// Primero obtener el valor de la celda seleccionada:
		DefaultTableModel tm = (DefaultTableModel) table.getModel();
		textPaneMostrarDescripcion.setText((String) tm.getValueAt(table.getSelectedRow(), 2));
	}

	/*
	 * M»todo que eliminar un cliente y sus correspondientes relacinones de la base
	 * de datos del programa:
	 */
	private void eliminarCliente(String nombre, String apellidos, String fechaNacimiento) {
		// Eliminamos cliente:
		try {
			if (collector.eliminarCliente(nombre, apellidos, fechaNacimiento)) {
				JOptionPane.showMessageDialog(null, "Cliente: " + nombre + " eliminado correctamente.");
			} else {
				JOptionPane.showMessageDialog(null, "Error al intentar eliminar el cliente: " + nombre, "ERROR!",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void resaltarColumnas(JTable tabla, int numeroColumnas, int[] columnasAResaltar, boolean coumnaModificable,
			int[] columnaAModificar) {
		for (int i = 0; i < numeroColumnas; i++) {
			// Indicamos como sera el resaltado de la tabla
			for (int j = 0; j < columnasAResaltar.length; j++) {
				if (i == columnasAResaltar[j]) {
					tabla.getColumnModel().getColumn(i)
							.setCellRenderer(new Resaltador(true, coumnaModificable, columnaAModificar));
					break;
				} else {
					tabla.getColumnModel().getColumn(i)
							.setCellRenderer(new Resaltador(false, coumnaModificable, columnaAModificar));
				}
			}
		}
	}

	/**
	 * MÈtodo para pasar bytes a string:
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
}
