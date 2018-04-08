package videoclub.client.gui.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;

import videoclub.server.gui.ICollector;
import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Inventario;
import videoclub.server.jdo.Pelicula;

public class ClientAlquilerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JDateChooser dateChooser;
	private JButton btnAlquilarAhora;
	private JLabel lblFechaAlquiler;
	private JLabel lblFechaDeAlquiler;
	private JLabel lblNewLabel;
	private ICollector collector;
	private Pelicula pelicula;
	private Cliente cliente;

	/**
	 * Create the frame.
	 */
	public ClientAlquilerFrame(ICollector collector, Pelicula pelicula, Cliente cliente) {
		this.collector = collector;
		this.pelicula = pelicula;
		this.cliente = cliente;
		inicializar();
		añadirComponentes();
		componentes();
		eventos();
	}

	private void inicializar() {
		contentPane = new JPanel();
		dateChooser = new JDateChooser();
		lblNewLabel = new JLabel("Elija fecha de devolución");
		lblFechaDeAlquiler = new JLabel("Fecha de alquiler");
		lblFechaAlquiler = new JLabel(new Date().toString());
		btnAlquilarAhora = new JButton("ALQUILAR AHORA");
	}

	private void añadirComponentes() {
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setSize(483, 207);
		setTitle("VENTANA DE ALQUILER");
		setContentPane(contentPane);
		contentPane.add(dateChooser);
		contentPane.add(lblNewLabel);
		contentPane.add(lblFechaDeAlquiler);
		contentPane.add(lblFechaAlquiler);
		contentPane.add(btnAlquilarAhora);
		contentPane.setLayout(null);

	}

	private void componentes() {
		contentPane.setBackground(Color.DARK_GRAY);
		dateChooser.setBorder(new LineBorder(SystemColor.textHighlight));
		dateChooser.setBounds(12, 54, 242, 36);
		lblNewLabel.setForeground(SystemColor.textHighlight);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(12, 13, 242, 29);
		lblFechaDeAlquiler.setHorizontalAlignment(SwingConstants.CENTER);
		lblFechaDeAlquiler.setForeground(SystemColor.textHighlight);
		lblFechaDeAlquiler.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFechaDeAlquiler.setBounds(266, 13, 199, 29);
		lblFechaAlquiler.setHorizontalAlignment(SwingConstants.CENTER);
		lblFechaAlquiler.setForeground(SystemColor.textHighlight);
		lblFechaAlquiler.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFechaAlquiler.setBounds(266, 54, 199, 36);
		btnAlquilarAhora.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnAlquilarAhora.setForeground(SystemColor.textHighlight);
		btnAlquilarAhora.setBounds(12, 103, 455, 49);
	}

	private void eventos() {
		btnAlquilarAhora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Primero obtenemos todo el inventario de la base de datos:
				List<Inventario> arrayInventarios = new ArrayList<Inventario>();
				try {
					arrayInventarios = collector.obtenerInventarios(arrayInventarios);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// Después obtenemos el id del cliente de la base de datos:
				List<Cliente> arrayClientes = new ArrayList<Cliente>();
				try {
					arrayClientes = collector.obtenerClientes(arrayClientes);
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				// Cotejamos los clientes con el actual y:
				// Buscamos el inventario que contenga la película a alquilar y
				// creamos un nuevo objeto alquiler:
				for (int i = 0; i < arrayInventarios.size(); i++) {
					if (arrayInventarios.get(i).getPelicula().getNombre().equals(pelicula.getNombre())) {
						for (int j = 0; j < arrayClientes.size(); j++) {
							if (arrayClientes.get(j).getNombre().equals(cliente.getNombre())
							 && arrayClientes.get(j).getApellidos().equals(cliente.getApellidos())
							 && arrayClientes.get(j).getDireccion().getCalle().equals(cliente.getDireccion().getCalle())) {
								// Entonces guardamos alquiler en base de datos
								// y
								// break en for:
								Alquiler alquiler = new Alquiler(new Date(), dateChooser.getDate(), cliente,
										arrayInventarios.get(i));
								// MakePersistent:
								try {
									if (collector.alquilarPelicula(alquiler) == true) {
										JOptionPane.showMessageDialog(null, "Película: " + pelicula.getNombre()
												+ ", alquilada correctamente, gracias.");
										ClientAlquilerFrame.this.dispose();
									} else {
										JOptionPane.showMessageDialog(null, "ERROR! VUELVA A INTENTARLO!");
										ClientAlquilerFrame.this.dispose();
									}
								} catch (RemoteException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}
				}
			}
		});
	}
}
