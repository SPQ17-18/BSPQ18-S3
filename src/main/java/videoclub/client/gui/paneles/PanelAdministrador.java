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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import videoclub.server.gui.ICollector;
import videoclub.server.jdo.Imagen;

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

	// Para obtener ruta imágenes:
	private JFileChooser jF1 = new JFileChooser();
	private JLabel LabelImage;
	private boolean imagenSubida = false;
	private Imagen imagen;
	private Path ruta;

	@SuppressWarnings("unused")
	private DefaultTableModel tableModel = new DefaultTableModel();
	private JTable table_1;
	private JScrollPane scrollPane_1;

	private ICollector collector; // Collector implementado desde "ClienfFrame"
	private JComboBox<Integer> comboBoxCantidad;

	/**
	 * Create the panel.
	 */
	public PanelAdministrador(ICollector collector) {
		this.collector = collector;
		inicializar();
		componentes();
		añadirComponentes();
		eventos();

		valoresComboBoxCategorias();
		valoresComboBoxAños();
		valoresComoboBoxDuraciones();
		valoresComboBoxPrecios();
		valoresComboBoxCantidad();
	}

	private void inicializar() {
		btnMostrarInventarioDe = new JButton("Mostrar inventario de pel\u00EDculas");
		btnMostrarAlquileresDe = new JButton("Mostrar alquileres de pel\u00EDculas");
		btnMostrarClientes = new JButton("Mostrar clientes");
		btnInsertarNuevaPelcula = new JButton("Insertar nueva pel\u00EDcula");
		scrollPane = new JScrollPane();
		table = new JTable();
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
	}

	private void componentes() {
		btnMostrarInventarioDe.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnMostrarInventarioDe.setBounds(12, 13, 232, 41);
		btnMostrarAlquileresDe.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnMostrarAlquileresDe.setBounds(255, 13, 225, 41);
		btnMostrarClientes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnMostrarClientes.setBounds(492, 13, 135, 41);
		btnInsertarNuevaPelcula.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnInsertarNuevaPelcula.setBounds(22, 667, 267, 27);
		scrollPane.setBounds(12, 67, 1056, 286);
		label.setBorder(new LineBorder(SystemColor.textHighlight));
		label.setBounds(12, 454, 1056, 253);
		table.setCellSelectionEnabled(true);
		table.setRowHeight(20);
		table.setGridColor(new Color(0, 51, 102));
		table.setFont(new Font("Tahoma", Font.BOLD, 15));
		table.setSelectionBackground(new Color(0, 0, 153));
		table.setSelectionForeground(new Color(0, 204, 102));
		scrollPane_1.setBounds(12, 356, 1056, 85);
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
		comboBoxAnyo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "A\u00D1O",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 120, 215)));
		comboBoxAnyo.setBounds(427, 472, 115, 41);
		comboBoxDuracion.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "DURACION",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 120, 215)));
		comboBoxDuracion.setBackground(Color.DARK_GRAY);
		comboBoxDuracion.setBounds(748, 472, 127, 41);
		comboBoxCategoria.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "CATEGORIA",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 120, 215)));
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
		comboBoxPrecio.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "PRECIO",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 120, 215)));
		comboBoxPrecio.setBackground(Color.DARK_GRAY);
		comboBoxPrecio.setBounds(301, 472, 114, 41);
		comboBoxCantidad.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "CANTIDAD",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 120, 215)));
		comboBoxCantidad.setBackground(Color.DARK_GRAY);
		comboBoxCantidad.setBounds(887, 472, 169, 41);
		LabelImage.setBorder(new LineBorder(SystemColor.textHighlight));
		LabelImage.setBounds(105, 517, 100, 140);
	}

	private void añadirComponentes() {
		setSize(anchuraPanel, alturaPanel);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		setBorder(null);

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
		add(scrollPane);
		add(LabelImage);
		add(label);
		add(scrollPane_1);
		add(comboBoxCantidad);
		scrollPane.setViewportView(table);
		scrollPane_1.setViewportView(table_1);

	}

	private void eventos() {
		btnMostrarInventarioDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnMostrarAlquileresDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnMostrarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnInsertarNuevaPelcula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Antes de insertar hay que comprobar si los campos están
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
					JOptionPane.showMessageDialog(null, "¡LOS CAMPOS ESTÁN INCOMPLETOS!", "¡ERROR!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		LabelImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				imagenSubida = false;
				jF1.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// Ahora tenemos que conseguir una imane a subir a la bd:
				if (jF1.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

					// Obtenemos ruta de la imágen:
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
	}

	private void valoresComboBoxCategorias() {
		String[] arrayCategorias = new String[] { "Infantil", "Comedia", "Thriller", "Miedo", "Clasica", "Musical" };
		for (int i = 0; i < arrayCategorias.length; i++) {
			comboBoxCategoria.addItem(arrayCategorias[i]);
		}
	}

	private void valoresComboBoxAños() {
		// Introducimos años desde 2017-1900:
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
	 * Método para insertar nueva película:
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

			// Finalmente guardamos la película en la base de datos:
			try {
				if (collector.insertarPelicula(nombre, duracion, descripcion, anyo, precio, categoria, cantidad,
						imagen) == true) {
					JOptionPane.showMessageDialog(null,
							"La película: " + nombre + " ha sido insertada correctamente en la base de datos.");
				}
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(null, "NO HAS ELEGIDO NINGUNA IMÁGEN PARA LA PELÍCULA...");
		}
	}
}
