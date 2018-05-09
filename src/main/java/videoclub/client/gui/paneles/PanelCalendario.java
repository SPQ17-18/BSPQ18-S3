package videoclub.client.gui.paneles;

import java.awt.Color;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import videoclub.server.collector.ICollector;
import videoclub.server.jdo.Noticia;
import videoclub.server.jdo.ProximoEstreno;

public class PanelCalendario extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCalendar calendar;
	private JTable tableEstrenos;
	private JScrollPane scrollPane;
	private JTable tableNoticias;
	private JScrollPane scrollPane_1;
	private JLabel lblNuevasNoticias;
	private JLabel lblPrximosExtrenos;
	private DefaultTableModel tableModel = new DefaultTableModel();
	private ICollector collector;

	/**
	 * Create the panel.
	 */
	public PanelCalendario(ICollector collector) {
		this.collector = collector;
		inicializar();
		añadir();
		componentes();
		eventos();

		mostrarNoticias();
		mostrarProximosEstrenos();
	}

	private void inicializar() {
		calendar = new JCalendar();
		lblPrximosExtrenos = new JLabel("  PRÓXIMOS EXTRENOS");
		scrollPane = new JScrollPane();
		tableEstrenos = new JTable();
		lblNuevasNoticias = new JLabel("  NUEVAS NOTICIAS");
		scrollPane_1 = new JScrollPane();
		tableNoticias = new JTable();
	}

	private void añadir() {
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		scrollPane.setViewportView(tableEstrenos);
		scrollPane_1.setViewportView(tableNoticias);
		add(calendar);
		add(lblPrximosExtrenos);
		add(scrollPane);
		add(lblNuevasNoticias);
		add(scrollPane_1);
	}

	private void componentes() {
		calendar.setBounds(12, 13, 305, 253);
		lblPrximosExtrenos.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrximosExtrenos.setBorder(new LineBorder(Color.CYAN));
		lblPrximosExtrenos.setForeground(Color.CYAN);
		lblPrximosExtrenos.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPrximosExtrenos.setBounds(670, 13, 334, 26);
		scrollPane.setBounds(674, 50, 330, 216);
		lblNuevasNoticias.setHorizontalAlignment(SwingConstants.CENTER);
		lblNuevasNoticias.setForeground(Color.CYAN);
		lblNuevasNoticias.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNuevasNoticias.setBorder(new LineBorder(Color.CYAN));
		lblNuevasNoticias.setBounds(329, 13, 329, 26);
		scrollPane_1.setBounds(329, 52, 328, 214);
	}

	private void eventos() {

	}

	private void mostrarNoticias() {
		tableModel = new DefaultTableModel();

		// Añadimos nombre columnas:
		tableModel.addColumn("NOTICIA - NOVEDAD");

		// Añadimos las noticias a la tabla:
		List<Noticia> arrayNoticias = new ArrayList<Noticia>();
		try {
			arrayNoticias = collector.obtenerNoticias(arrayNoticias);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Noticia not : arrayNoticias) {
			tableModel.addRow(new Object[] { not.getNoticia() });
		}

		// Introducimos el modelo en la tabla:
		tableNoticias.setModel(tableModel);
	}

	private void mostrarProximosEstrenos() {
		tableModel = new DefaultTableModel();

		// Añadimos nombre columnas:
		tableModel.addColumn("PROXIMO ESTRENO - NOVEDAD");

		// Añadimos los estrnos a la tabla:
		List<ProximoEstreno> arrayEstrenos = new ArrayList<ProximoEstreno>();
		try {
			arrayEstrenos = collector.obtenerProximosEstrenos(arrayEstrenos);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ProximoEstreno estreno : arrayEstrenos) {
			tableModel.addRow(new Object[] { estreno.getNombrePelicula() });
		}

		// Introducimos el modelo en la tabla:
		tableEstrenos.setModel(tableModel);
	}
}
