package videoclub.client.gui.ventanas;

import java.awt.Color;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import videoclub.server.gui.ICollector;
import videoclub.server.jdo.Opinion;
import videoclub.server.jdo.Pelicula;

public class ClientOpinionesFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ICollector collector;
	private JTable table;
	private JScrollPane scrollPane;
	private List<Opinion> arrayOpiniones = new ArrayList<Opinion>();
	private TableModel tableModel;
	private Pelicula pelicula;

	/**
	 * Create the frame.
	 */
	public ClientOpinionesFrame(ICollector collector, Pelicula pelicula) {
		this.collector = collector;
		this.pelicula = pelicula;
		try {
			this.arrayOpiniones = this.collector.obtenerOpiniones(arrayOpiniones);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inicializar();
		aÒadir();
		componentes();
		mostrarOpiniones();
	}

	private void inicializar() {
		contentPane = new JPanel();
		scrollPane = new JScrollPane();
		table = new JTable();

	}

	private void aÒadir() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(850, 500);
		setTitle("VENTANA DE OPINIONES");
		setContentPane(contentPane);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
	}

	private void componentes() {
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setLayout(null);
		scrollPane.setBounds(12, 13, 820, 439);
	}

	private void mostrarOpiniones() {
		tableModel = new TableModel();
		for (Opinion op : arrayOpiniones) {
			if (op.getPelicula().getNombre().equals(pelicula.getNombre())) {
				tableModel.addRow(new Object[] { op.getUser().getNombreUsuario(), op.getDescripcionOpinion() });
			}
		}
		// Introducimos el modelo en la tabla:
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setCellRenderer(new CellRendererImagen());
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(750);
	}

	// Clase interna Para agregar columnas:
	class TableModel extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

		public TableModel() {
			addColumn("USER");
			addColumn("OPINION");
		}
	}

	// Clase interna para agregar las im·genes a las tablas:
	class CellRendererImagen extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		// ----------------------------//
		public CellRendererImagen() {
			super.setHorizontalAlignment(JLabel.CENTER);
		}
	}
}