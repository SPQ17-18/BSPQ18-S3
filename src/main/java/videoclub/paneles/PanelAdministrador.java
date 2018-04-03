package videoclub.paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

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
	private JButton btnEliminarPelcula;
	private JLabel label;
	private JTextField textFieldNombrePelicula;
	private JComboBox<Integer> comboBoxAnyo;
	private JComboBox<Integer> comboBoxDuracion;
	private JComboBox<String> comboBoxCategoria;
	private JTextPane textPaneDescripcion;
	private JScrollPane scrollPane_2;
	private JButton btnInsertarNuevoInventario;
	private JTextPane textPaneMostrarDescripcion;
	private JScrollPane scrollPane_3;
	private JComboBox<Float> comboBoxPrecio;

	@SuppressWarnings("unused")
	private DefaultTableModel tableModel = new DefaultTableModel();
	private JTable table_1;
	private JScrollPane scrollPane_1;

	/**
	 * Create the panel.
	 */
	public PanelAdministrador() {
		inicializar();
		componentes();
		añadirComponentes();
		eventos();
	}

	private void inicializar() {
		btnMostrarInventarioDe = new JButton("Mostrar inventario de pel\u00EDculas");
		btnMostrarAlquileresDe = new JButton("Mostrar alquileres de pel\u00EDculas");
		btnMostrarClientes = new JButton("Mostrar clientes");
		btnInsertarNuevaPelcula = new JButton("Insertar nueva pel\u00EDcula");
		btnEliminarPelcula = new JButton("Eliminar pel\u00EDcula");
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
		btnInsertarNuevoInventario = new JButton("Insertar nuevo inventario");
		scrollPane_3 = new JScrollPane();
		textPaneMostrarDescripcion = new JTextPane();
		comboBoxPrecio = new JComboBox<Float>();
	}

	private void componentes() {
		btnMostrarInventarioDe.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnMostrarInventarioDe.setBounds(12, 13, 232, 41);
		btnMostrarAlquileresDe.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnMostrarAlquileresDe.setBounds(255, 13, 225, 41);
		btnMostrarClientes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnMostrarClientes.setBounds(492, 13, 135, 41);
		btnInsertarNuevaPelcula.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnInsertarNuevaPelcula.setBounds(12, 535, 185, 41);
		btnEliminarPelcula.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnEliminarPelcula.setBounds(418, 535, 232, 41);
		scrollPane.setBounds(12, 67, 1056, 286);
		label.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Insertar pel\u00EDcula",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 120, 215)));
		label.setBounds(12, 582, 1056, 125);
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
		textFieldNombrePelicula.setBounds(22, 598, 205, 94);
		textFieldNombrePelicula.setColumns(10);
		comboBoxAnyo.setBackground(Color.DARK_GRAY);
		comboBoxAnyo.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "A\u00D1O",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 120, 215)));
		comboBoxAnyo.setBounds(359, 601, 93, 91);
		comboBoxDuracion.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "DURACION",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 120, 215)));
		comboBoxDuracion.setBackground(Color.DARK_GRAY);
		comboBoxDuracion.setBounds(464, 601, 127, 91);
		comboBoxCategoria.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "CATEGORIA",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 120, 215)));
		comboBoxCategoria.setBackground(Color.DARK_GRAY);
		comboBoxCategoria.setBounds(603, 601, 135, 91);
		scrollPane_2.setBounds(744, 600, 312, 92);
		scrollPane_2.setViewportView(textPaneDescripcion);
		textPaneDescripcion.setBackground(Color.DARK_GRAY);
		textPaneDescripcion.setBorder(new TitledBorder(null, "DESCRIPCION", TitledBorder.CENTER, TitledBorder.TOP, null,
				SystemColor.textHighlight));
		btnInsertarNuevoInventario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnInsertarNuevoInventario.setBounds(209, 535, 197, 41);
		scrollPane_1.setBorder(new TitledBorder(null,
				"Pel\u00EDculas alquiladas por los clientes - Nombre y descripci\u00F3n del cliente en particular",
				TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.textHighlight));
		textPaneMostrarDescripcion.setForeground(Color.WHITE);
		textPaneMostrarDescripcion.setBackground(Color.DARK_GRAY);
		textPaneMostrarDescripcion.setBorder(new TitledBorder(null,
				"Descripci\u00F3n total de la pel\u00EDcula (primero click en descripci\u00F3n de pel\u00EDcula)",
				TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.textHighlight));
		scrollPane_3.setBounds(12, 454, 1056, 70);
		comboBoxPrecio.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "PRECIO",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 120, 215)));
		comboBoxPrecio.setBackground(Color.DARK_GRAY);
		comboBoxPrecio.setBounds(233, 601, 114, 91);
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
		add(btnEliminarPelcula);
		add(textFieldNombrePelicula);
		add(comboBoxAnyo);
		add(comboBoxDuracion);
		add(comboBoxCategoria);
		add(comboBoxPrecio);
		add(scrollPane_2);
		add(scrollPane);
		add(label);
		add(scrollPane_1);
		add(btnInsertarNuevoInventario);
		add(scrollPane_3);

		scrollPane_3.setViewportView(textPaneMostrarDescripcion);
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
				
			}
		});
		btnEliminarPelcula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
						
			}
		});
		btnInsertarNuevoInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	
}
