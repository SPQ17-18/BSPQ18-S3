package videoclub.client.gui.ventanas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import videoclub.client.gui.paneles.PanelAdministrador;
import videoclub.client.gui.paneles.PanelIniciarSesion;
import videoclub.client.gui.paneles.PanelRegistro;
import videoclub.client.gui.paneles.PanelUsuario;
import videoclub.server.gui.ICollector;
import videoclub.server.jdo.Cliente;

public class ClientFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private ICollector collector; //Pasamos collector desde el "Client"
    
	private JPanel contentPane;
	private JScrollPane contenedorDePaneles;
	private int anchura;
	private int altura;
	private PanelIniciarSesion panelIniciarSesion;
	public PanelUsuario panelUsuario;
	private PanelAdministrador panelAdministrador;
	private PanelRegistro panelRegistro;
	private Client client;

	/**
	 * Create the frame.
	 */
	public ClientFrame(int anchura, int altura, ICollector collector, Client cliente) {
		
		//Antes de llamar a los métodos debemos asignar la anchura y altura al JFrame:
		this.anchura = anchura;
		this.altura = altura;
		this.collector = collector;
		this.client = cliente;
		
		inicializar();
		componentes();
		añadirComponentes();
		eventos();				
	}
	
	private void inicializar()
	{
		panelIniciarSesion = new PanelIniciarSesion(this, this.collector,this.client);
		panelRegistro = new PanelRegistro(this.collector);
		contentPane = new JPanel();
		contenedorDePaneles = new JScrollPane();
	}
	
	private void componentes()
	{
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new LineBorder(Color.ORANGE, 2));
		
		//El contenedor se ajustará automáticamente a la anchura y altura pasadas por parámetro:
		contenedorDePaneles.setBounds(0, 0, anchura - 6, altura - 35);
	}
	
	private void añadirComponentes()
	{
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(anchura, altura);
		setLocationRelativeTo(null);
		setContentPane(contentPane);	
		
		contentPane.setLayout(null);
		contentPane.add(contenedorDePaneles);

	}
	
	private void eventos()
	{
		//Accedemos al evento del botón de volver del panel registro desde el JFrame VentanaPrincipal:
		panelRegistro.btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarPanelIniciarSesion();
			}
		});
		//Accedemos al evento del botón de registrarse del panel iniciar sesión desde el JFrame VentanaPrincipal:
		panelIniciarSesion.BotonRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarPanelRegistro();
			}
		});
	}
	
	//Método para cargar el panel de iniciar sesión en el scrollPane del JFrame VentanaPrincipal:
	public void cargarPanelIniciarSesion()
	{
		//Cargamos el panel en el scrollPane: contenedorDePaneles
		contenedorDePaneles.setViewportView(panelIniciarSesion);
	}
	
	//Método para cargar el panel del usuario:
	public void cargarPanelUsuario()
	{
		//Inicializamos el panel:
		panelUsuario = new PanelUsuario(this.collector);
		//Cargamos el panel en el scrollPane: contenedorDePaneles
		contenedorDePaneles.setViewportView(panelUsuario);
	}
	
	//Método para cargar el panel del administrador:
	public void cargarPanelAdministrador()
	{
		//Inicializamos el panel:
		panelAdministrador = new PanelAdministrador(this.collector);
		//Cargamos el panel en el scrollPane: contenedorDePaneles
		contenedorDePaneles.setViewportView(panelAdministrador);
	}
	
	//Método para cargar el panel registro:
	public void cargarPanelRegistro()
	{
		//Cargamos el panel en el scrollPane: contenedorDePaneles
		contenedorDePaneles.setViewportView(panelRegistro);
	}

}
