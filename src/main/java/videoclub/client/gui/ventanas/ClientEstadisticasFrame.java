package videoclub.client.gui.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import videoclub.client.utiles.Estadisticas;
import videoclub.server.collector.ICollector;
import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Pelicula;

public class ClientEstadisticasFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnClientesQueMs;
	private JButton btnPelculasMsAlquiladas;
	private JButton btnGnerosDePelculas;

	private Estadisticas estadisticas;
	private ICollector collector;
	private BarChart_AWT chart;
	private List<Stat> arrayPeliculasStats = new ArrayList<Stat>();
	private List<Stat> arrayGenerosStats = new ArrayList<Stat>();
	private List<Stat> arrayClientesStats = new ArrayList<Stat>();

	/**
	 * Create the frame.
	 */
	public ClientEstadisticasFrame(ICollector collector) {
		this.estadisticas = new Estadisticas();
		this.collector = collector;
		inicializar();
		añadir();
		componentes();
		eventos();
		obtenerPeliculasStats();
		obtenerClientesStats();
		obtenerGenerosStats();
	}

	private void inicializar() {
		contentPane = new JPanel();
		btnGnerosDePelculas = new JButton("Generos de peliculas mas usados");
		btnPelculasMsAlquiladas = new JButton("Peliculas mas alquiladas");
		btnClientesQueMs = new JButton("Clientes que mas han alquilado");

	}

	private void añadir() {
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(1080, 720);
		setLocationRelativeTo(null);
		setContentPane(contentPane);

		contentPane.add(btnGnerosDePelculas);
		contentPane.add(btnPelculasMsAlquiladas);
		contentPane.add(btnClientesQueMs);
	}

	private void componentes() {
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setLayout(null);
		btnGnerosDePelculas.setForeground(Color.GREEN);
		btnGnerosDePelculas.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnGnerosDePelculas.setBounds(12, 13, 301, 36);
		btnPelculasMsAlquiladas.setForeground(Color.GREEN);
		btnPelculasMsAlquiladas.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnPelculasMsAlquiladas.setBounds(325, 13, 230, 36);
		btnClientesQueMs.setForeground(Color.GREEN);
		btnClientesQueMs.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnClientesQueMs.setBounds(567, 13, 271, 36);

	}

	private void eventos() {
		btnGnerosDePelculas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chart = new BarChart_AWT("Estadisticas de genero de pelÌculas", "Generos mas alquilados", "Genero",
						arrayGenerosStats);
				chart.pack();
				chart.setVisible(true);
				contentPane.add(chart);
			}
		});
		btnPelculasMsAlquiladas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chart = new BarChart_AWT("Estadisticas de peliculas", "Peliculas mas alquiladas", "Pelicula",
						arrayPeliculasStats);
				chart.pack();
				chart.setVisible(true);
				contentPane.add(chart);
			}
		});
		btnClientesQueMs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chart = new BarChart_AWT("Estadisticas de clientes", "Clientes que mas han alquilado", "Cliente",
						arrayClientesStats);
				chart.pack();
				chart.setVisible(true);
				contentPane.add(chart);
			}
		});
	}

	private void obtenerPeliculasStats() {
		// Obtenemos los alquileres de la base de datos:
		List<Alquiler> arrayAlquileres = new ArrayList<Alquiler>();
		try {
			arrayAlquileres = collector.obtenerAlquileres(arrayAlquileres);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
		}

		// Obtenemos las peliculas de la base de datos:
		List<Pelicula> arrayPeliculas = new ArrayList<Pelicula>();
		try {
			arrayPeliculas = collector.obtenerPeliculas(arrayPeliculas);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
		}

		// Buscamos las veces que se ha alquilado cada pelicula:
		for (Pelicula pelicula : arrayPeliculas) {
			int value = estadisticas.getCountPeliculaAlquilada(arrayAlquileres, pelicula);
			// Minimo 5:
			if (value > 5) {
				arrayPeliculasStats.add(new Stat(pelicula.getNombre(), value));
			}
		}
	}

	private void obtenerGenerosStats() {
		// Obtenemos los alquileres de la base de datos:
		List<Alquiler> arrayAlquileres = new ArrayList<Alquiler>();
		try {
			arrayAlquileres = collector.obtenerAlquileres(arrayAlquileres);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
		}

		// Obtenemos las peliculas de la base de datos:
		List<Pelicula> arrayPeliculas = new ArrayList<Pelicula>();
		try {
			arrayPeliculas = collector.obtenerPeliculas(arrayPeliculas);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
		}

		// Buscamos las veces que se ha alquilado cada pelicula:
		for (Pelicula pelicula : arrayPeliculas) {
			int value = estadisticas.getCountGeneroPelicula(arrayAlquileres, pelicula);
			// Minimo 10:
			if (value > 10) {
				arrayGenerosStats.add(new Stat(pelicula.getCategoria().getNombre(), value));
			}
		}
	}

	private void obtenerClientesStats() {
		// Obtenemos los alquileres de la base de datos:
		List<Alquiler> arrayAlquileres = new ArrayList<Alquiler>();
		try {
			arrayAlquileres = collector.obtenerAlquileres(arrayAlquileres);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
		}

		// Obtenemos los clientes de la base de datos:
		List<Cliente> arrayClientes = new ArrayList<Cliente>();
		try {
			arrayClientes = collector.obtenerClientes(arrayClientes);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
		}

		// Buscamos las veces que el cliente a alquilado:
		for (Cliente cliente : arrayClientes) {
			int value = estadisticas.getCountClienteAlquiler(arrayAlquileres, cliente);
			// Minimo 10:
			if (value > 10) {
				arrayClientesStats.add(new Stat(cliente.getNombre() + " " + cliente.getApellidos(), value));
			}
		}
	}

	public class Stat {
		private String nombre;
		private double valor;

		public Stat(String nombre, double valor) {
			this.nombre = nombre;
			this.valor = valor;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public double getValor() {
			return valor;
		}

		public void setValor(double valor) {
			this.valor = valor;
		}
	}

	public class BarChart_AWT extends JInternalFrame {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public BarChart_AWT(String applicationTitle, String chartTitle, String tipo, List<Stat> lista) {
			super(applicationTitle);
			JFreeChart barChart = ChartFactory.createBarChart(chartTitle, tipo, "Cantidad", createDataset(lista, tipo),
					PlotOrientation.VERTICAL, true, true, false);

			ChartPanel chartPanel = new ChartPanel(barChart);
			chartPanel.setPreferredSize(new java.awt.Dimension(990, 550));
			setContentPane(chartPanel);
			setBounds(12, 62, 990, 550);
		}

		private CategoryDataset createDataset(List<Stat> lista, String tipo) {
			final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			// Recorremos toda la lista y vamos aÒadiendo valores al dataset:
			for (Stat stat : lista) {
				dataset.addValue(stat.getValor(), tipo, stat.getNombre());
			}
			return dataset;
		}
	}
}

