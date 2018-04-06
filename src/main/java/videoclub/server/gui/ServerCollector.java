package videoclub.server.gui;

import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;

import android.view.LayoutInflater.Filter;
import videoclub.client.gui.paneles.PanelIniciarSesion;
import videoclub.client.gui.paneles.PanelUsuario;
import videoclub.observer.RMI.IRemoteObserver;
import videoclub.observer.RMI.RemoteObservable;
import videoclub.server.jdo.Categoria;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Direccion;
import videoclub.server.jdo.Inventario;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.Usuario;

public class ServerCollector extends UnicastRemoteObject implements ICollector {

	private static final long serialVersionUID = 1L;
	private RemoteObservable remoteObservable;
	private PersistenceManager pm = null;
	private Transaction tx = null;

	public ServerCollector() throws RemoteException {
		super();
		this.remoteObservable = new RemoteObservable();
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();

		// EDT para ajustar el tema propio al JFrame creado para el servidor:
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel((LookAndFeel) new SubstanceRavenLookAndFeel());
					ServerFrame serverFrame = new ServerFrame();
					serverFrame.setVisible(true);
					// Mensaje del servidor saludo:
					ServerFrame.textArea.append("Servidor activo y a la espera...");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	protected void finalize() throws Throwable {
		if (tx.isActive()) {
			tx.rollback();
		}
		pm.close();
	}

	public void addRemoteObserver(IRemoteObserver observer) {
		this.remoteObservable.addRemoteObserver(observer);

	}

	public void deleteRemoteObserver(IRemoteObserver observer) {
		this.remoteObservable.deleteRemoteObserver(observer);
	}

	public synchronized void getDonation(int donation) throws RemoteException {

	}

	@SuppressWarnings("unused")
	private void notifyTotal(int total) {

	}

	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;

	public static void main(String[] args) {
		// Launch the RMI registry
		class RMIRegistryRunnable implements Runnable {

			public void run() {
				try {
					java.rmi.registry.LocateRegistry.createRegistry(1099);
					System.out.println("RMI registry ready.");
				} catch (Exception e) {
					System.out.println("Exception starting RMI registry:");
					e.printStackTrace();
				}
			}
		}

		rmiRegistryThread = new Thread(new RMIRegistryRunnable());
		rmiRegistryThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

		class RMIServerRunnable implements Runnable {

			public void run() {
				System.setProperty("java.security.policy", "target\\classes\\security\\java.policy");

				if (System.getSecurityManager() == null) {
					System.setSecurityManager(new SecurityManager());
				}

				String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
				System.out.println(" * Server name: " + name);

				try {
					ICollector iCollector = new ServerCollector();
					Naming.rebind(name, iCollector);

				} catch (RemoteException re) {
					System.err.println(" # Collector RemoteException: " + re.getMessage());
					re.printStackTrace();
					System.exit(-1);
				} catch (MalformedURLException murle) {
					System.err.println(" # Collector MalformedURLException: " + murle.getMessage());
					murle.printStackTrace();
					System.exit(-1);
				}
			}
		}

		rmiServerThread = new Thread(new RMIServerRunnable());
		rmiServerThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	@Override
	public boolean registerUser(String nombreUsuario, String contraseña, String correo, String nombre, String apellidos,
			Date fechaNacimiento, String calle, String ciudad, String pais) throws RemoteException {
		// TODO Auto-generated method stub
		boolean denegarRegistro = false;
		try {
			tx.begin();
			ServerFrame.textArea.append("Comprobación de si el usuario: '" + nombreUsuario + "' ya existe\n");
			Usuario user = null;

			// Ejecutamos consulta para comprobar todos los usuarios de la base
			// de datos:
			@SuppressWarnings("unchecked")
			Query<Usuario> q = pm.newQuery(
					"SELECT FROM " + Usuario.class.getName() + " WHERE nombreUsuario == '" + nombreUsuario + "'");
			List<Usuario> usuarios = q.executeList();
			for (Usuario usuario : usuarios) {
				// Comprobamos si el nombre coincice con el que el usuario quere
				// registrarse:
				if (usuario.getNombreUsuario().equals(nombreUsuario)) {
					// SI es así entonces se le denegará el registro:
					denegarRegistro = true;
				}
			}

			// Comprobamos si ha saltado el booleano de denegar regisro:
			if (denegarRegistro == true) {
				ServerFrame.textArea.append("El usuario: " + nombreUsuario + " ya está registrado!, ERROR!\n");
			} else {
				ServerFrame.textArea.append("Creando nuevo cliente...");
				Cliente cliente = new Cliente(nombre, apellidos, fechaNacimiento, new Direccion(calle, ciudad, pais));
				ServerFrame.textArea.append("Cliente: " + nombre + " - " + apellidos + " creado exitosamente! :D\n");
				ServerFrame.textArea.append("Creando nuevo usuario: " + nombreUsuario + "\n");
				user = new Usuario(nombreUsuario, contraseña, correo, cliente);
				ServerFrame.textArea.append("Usuario creado: " + nombreUsuario + "\n");
				pm.makePersistent(user);
				ServerFrame.textArea.append("Sincronizado con las bases de datos... ! Correcto\n");
			}
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}

		return denegarRegistro;

	}

	@Override
	public boolean login(String nombreUsuario, String contraseña) throws RemoteException {
		// TODO Auto-generated method stub
		boolean inicioCorrecto = false;
		try {
			tx.begin();
			ServerFrame.textArea.append("Comprobación de si el usuario '" + nombreUsuario + "' es correcto...\n");

			// Ejecutamos consulta para comprobar si el usuario es correcto:
			@SuppressWarnings("unchecked")
			Query<Usuario> q = pm.newQuery("SELECT FROM " + Usuario.class.getName() + " WHERE nombreUsuario == '"
					+ nombreUsuario + "' && contraseña == '" + contraseña + "'");
			List<Usuario> usuarios = q.executeList();
			for (Usuario usuario : usuarios) {
				// Comprobamos que el usuario sea correcto:
				if (usuario.getNombreUsuario().equals(nombreUsuario) && usuario.getContraseña().equals(contraseña)) {
					inicioCorrecto = true;
					ServerFrame.textArea.append("Usuario y contraseña correctas! :D\n");
					ServerFrame.textArea.append("Obteniendo datos del cliente...\n");					
					PanelUsuario.clienteActual = usuario.getCliente();
					ServerFrame.textArea.append("Datos del cliente obtenidos!\n");
					ServerFrame.textArea.append("Bienvenido " + usuario.getCliente().getNombre() + " :D\n");
				}
			}
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return inicioCorrecto;
	}

	@Override
	public boolean insertarPelicula(String nombre, int duracion, String descripcion, int anyo, float precio,
			String categoria, int cantidad) {
		// TODO Auto-generated method stub
		boolean correcto = false;
		boolean categoriaExiste = false;
		try {
			tx.begin();
			ServerFrame.textArea.append("Comprobación de la categoria: '" + categoria + "'\n");
			Categoria cat = null;

			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<Categoria> q = pm
					.newQuery("SELECT FROM " + Categoria.class.getName() + " WHERE nombre == '" + categoria + "'");
			List<Categoria> categorias = q.executeList();
			// Comprobamos si dicha categoria existe:
			for (Categoria cate : categorias) {
				if (cate.getNombre().equals(categoria)) {
					// Existe dicha categoria por tanto no hay que crearla!
					categoriaExiste = true;
					// Guardamos la categoria para usarla al crear la película:
					cat = cate;
				}
			}

			// Comprobamos si la categoria existe:
			if (categoriaExiste == false) {
				// Hacer persistente:
				ServerFrame.textArea.append("Creando nueva categoria...\n");
				cat = new Categoria(categoria);
				pm.makePersistent(cat);
				ServerFrame.textArea.append("Categoria creada con éxito!\n");
			}

			// Ahora toca crear la película:
			ServerFrame.textArea.append("Creando nueva película...\n");
			Pelicula pelicula = new Pelicula(nombre, duracion, descripcion, anyo, precio, cat);
			pm.makePersistent(pelicula);
			ServerFrame.textArea.append("Pelicula: " + nombre + " creada exitosamente!\n");

			// Creamos el inventario con la cantidad de películas en stock!:
			ServerFrame.textArea.append("Creando inventario para la película: " + nombre + "\n");
			pm.makePersistent(new Inventario(cantidad, pelicula));
			ServerFrame.textArea.append("Inventario para le película " + nombre + " creado exitosamente..!\n");

			tx.commit();

			correcto = true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}

		return correcto;
	}

	@Override
	public List<Pelicula> obtenerPeliculas(List<Pelicula> arrayPeliculas) throws RemoteException {
		// TODO Auto-generated method stub

		try {
			tx.begin();
			ServerFrame.textArea.append("Obteniendo películas de la BD...\n");

			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<Pelicula> q = pm
			.newQuery("SELECT FROM " + Pelicula.class.getName() + " WHERE nombre != ' '");
			List<Pelicula> peliculas = (List<Pelicula>) q.executeList();
			for (Pelicula pelicula : peliculas) {
				//Vamos añadiendo las películas al array pasado:
				arrayPeliculas.add(pelicula);
			}
			ServerFrame.textArea.append("Películas obteneidas ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		
		return arrayPeliculas;
	}
}