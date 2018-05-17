package videoclub.client.utiles;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import videoclub.client.gui.ventanas.ClientFrame;
import videoclub.client.main.Client;
import videoclub.server.collector.ICollector;

public class SwingWorkerProgress extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblBorde;
	private JLabel lblCargandoSwingComponents;
	private JProgressBar barraComponentes;
	private JProgressBar barraDatos;
	private JLabel lblCargandoDatos;
	private Timer hiloC;
	private Timer hiloD;

	public SwingWorkerProgress(ActionEvent evt, String tipoUsuario, ClientFrame frame, Client cliente,
			ICollector collector) {
		inicializar();
		componentes();
		añadir();
		startTimer(evt);
		iniciarlizarVentana(tipoUsuario, frame, cliente, collector);
	}

	private void inicializar() {
		lblBorde = new JLabel();
		barraComponentes = new JProgressBar();
		lblCargandoSwingComponents = new JLabel("Cargando componentes ...");
		barraDatos = new JProgressBar();
		lblCargandoDatos = new JLabel("Cargando datos ...");
	}

	private void componentes() {
		lblCargandoDatos.setHorizontalAlignment(SwingConstants.CENTER);
		lblCargandoDatos.setForeground(Color.ORANGE);
		lblCargandoDatos.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCargandoDatos.setBounds(12, 127, 476, 42);
		barraDatos.setBackground(Color.ORANGE);
		barraDatos.setForeground(Color.WHITE);
		barraDatos.setFont(new Font("Tahoma", Font.BOLD, 15));
		barraDatos.setBounds(12, 182, 476, 25);
		lblCargandoSwingComponents.setForeground(Color.ORANGE);
		lblCargandoSwingComponents.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCargandoSwingComponents.setHorizontalAlignment(SwingConstants.CENTER);
		lblCargandoSwingComponents.setBounds(12, 13, 476, 42);
		barraComponentes.setBackground(Color.ORANGE);
		barraComponentes.setForeground(Color.WHITE);
		barraComponentes.setFont(new Font("Tahoma", Font.BOLD, 15));
		barraComponentes.setBounds(12, 68, 476, 25);
		lblBorde.setBorder(new LineBorder(Color.ORANGE, 3));
		lblBorde.setBounds(0, 0, 500, 250);
	}

	private void añadir() {
		setSize(500, 250);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setResizable(false);

		getContentPane().setBackground(Color.DARK_GRAY);
		getContentPane().setLayout(null);
		getContentPane().add(lblCargandoDatos);
		getContentPane().add(barraDatos);
		getContentPane().add(lblCargandoSwingComponents);
		getContentPane().add(barraComponentes);
		getContentPane().add(lblBorde);

	}

	private void startTimer(ActionEvent evt) {
		ActionListener taskBarraComponentes = new ActionListener() {
			/**
			 * Método que accionador del Timer:
			 */
			public void actionPerformed(ActionEvent evt) {
				// Incrementamos valor de JProgressBar "Componentes" en 1:
				barraComponentes.setValue(barraComponentes.getValue() + 10);
				if (barraComponentes.getValue() >= 100 && barraDatos.getValue() >= 100) {
					SwingWorkerProgress.this.dispose();
				}
			}
		}; // Fin de la declaración del ActionListener.

		ActionListener taskBarraDatos = new ActionListener() {
			/**
			 * Método que accionador del Timer:
			 */
			public void actionPerformed(ActionEvent evt) {
				// Incrementamos valor de JProgressBar "Datos" en 10:
				barraDatos.setValue(barraDatos.getValue() + 10);
				if (barraComponentes.getValue() >= 100 && barraDatos.getValue() >= 100) {
					SwingWorkerProgress.this.dispose();
				}
			}
		}; // Fin de la declaración del ActionListener.

		// Creamos un nuevo Timer, indicando el retraso (100 ms),
		// y la acción a ejecutar: actionPerformed():
		hiloC = new Timer(100, taskBarraComponentes);
		hiloC.start(); // Empieza la ejecución del timer.

		// Creamos un nuevo Timer, indicando el retraso (10 ms),
		// y la acción a ejecutar: actionPerformed():
		hiloD = new Timer(10, taskBarraDatos);
		hiloD.start(); // Empieza la ejecución del timer.
	}

	private void iniciarlizarVentana(String tipoUsuario, ClientFrame frame, Client cliente, ICollector collector) {
		if (tipoUsuario.equals(("USER"))) {
			// Primero cerramos la ventana actual de las credenciales:
			frame.dispose();
			// Inicializamos nueva JFrame VentanaPrincipal pero con distintos parámetros de
			// entrada:
			frame = new ClientFrame(1280 + 6, 720 + 35, collector, cliente);
			frame.cargarPanelUsuario();
			cliente.setFrame(frame);
			try {
				collector.conectarUsuario();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
			}
		} else if (tipoUsuario.equals(("ADMIN"))) {
			// Primero cerramos la ventana actual de las credenciales:
			frame.dispose();
			// Inicializamos nueva JFrame VentanaPrincipal pero con distintos parámetros de
			// entrada:
			frame = new ClientFrame(1080 + 6, 720 + 35, collector, cliente);
			frame.setVisible(true);
			frame.cargarPanelAdministrador();
			cliente.setFrame(frame);
		}
	}
}