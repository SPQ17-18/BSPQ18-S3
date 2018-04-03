package videoclub.ventanas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;

import videoclub.datos.Cliente;
import videoclub.paneles.PanelAdministrador;
import videoclub.paneles.PanelIniciarSesion;
import videoclub.paneles.PanelRegistro;
import videoclub.paneles.PanelUsuario;
import java.awt.SystemColor;

public class VentanaPrincipal {

	public JFrame frame;
	private JScrollPane contenedorDePaneles;
	private PanelIniciarSesion panelIniciarSesion;
	private PanelUsuario panelUsuario;
	private PanelAdministrador panelAdministrador;
	private PanelRegistro panelRegistro;
	private int anchura;
	private int altura;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//UIManager.setLookAndFeel((LookAndFeel) new SubstanceRavenLookAndFeel());
					VentanaPrincipal window = new VentanaPrincipal(525,325);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPrincipal(int anchura, int altura) {
		
		//Antes de llamar a los métodos debemos asignar la anchura y altura al JFrame:
		this.anchura = anchura;
		this.altura = altura;
		
		initialize();
		componentes();
		añadirComponentes();
		eventos();	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		panelIniciarSesion = new PanelIniciarSesion(frame);
		panelRegistro = new PanelRegistro();		
		contenedorDePaneles = new JScrollPane();
	}
	
	private void componentes()
	{
		frame.getContentPane().setBackground(Color.BLACK);		
		//El contenedor se ajustará automáticamente a la anchura y altura pasadas por parámetro:
		contenedorDePaneles.setBounds(0, 0, anchura - 6, altura - 35);
		contenedorDePaneles.setBorder(new LineBorder(SystemColor.textHighlight, 3));
	}
	
	private void añadirComponentes()
	{
		frame.setBackground(Color.DARK_GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(anchura, altura);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);	
		frame.getContentPane().add(contenedorDePaneles);
		cargarPanelIniciarSesion();
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
	public void cargarPanelUsuario(Cliente cliente)
	{
		//Inicializamos el panel:
		panelUsuario = new PanelUsuario(cliente);
		//Cargamos el panel en el scrollPane: contenedorDePaneles
		contenedorDePaneles.setViewportView(panelUsuario);
	}
	
	//Método para cargar el panel del administrador:
	public void cargarPanelAdministrador()
	{
		//Inicializamos el panel:
		panelAdministrador = new PanelAdministrador();
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
