package videoclub.server.gui;

import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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

import videoclub.observer.RMI.IRemoteObserver;
import videoclub.observer.RMI.RemoteObservable;
import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Amigo;
import videoclub.server.jdo.Categoria;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Direccion;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Inventario;
import videoclub.server.jdo.Mensaje;
import videoclub.server.jdo.Novedad;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.PeliculaVista;
import videoclub.server.jdo.Recomendacion;
import videoclub.server.jdo.Usuario;

public class ServerCollector extends UnicastRemoteObject implements ICollector {

	private static final long serialVersionUID = 1L;
	private RemoteObservable remoteObservable;
	private PersistenceManager pm = null;
	private Transaction tx = null;
	private Cliente cliente;
	private Usuario usuario;
	private List<Usuario> usuariosConectados = new ArrayList<Usuario>();

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

	public synchronized void addRemoteObserver(IRemoteObserver observer) {
		this.remoteObservable.addRemoteObserver(observer);

	}

	public synchronized void deleteRemoteObserver(IRemoteObserver observer) {
		this.remoteObservable.deleteRemoteObserver(observer);
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
					this.cliente = usuario.getCliente();
					this.usuario = usuario;
					this.usuariosConectados.add(usuario);
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
	public boolean insertarPelicula(String nombre, int duracion, byte[] descripcion, int anyo, float precio,
			String categoria, int cantidad, Imagen imagen, boolean novedad) {
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
			Pelicula pelicula = new Pelicula(nombre, duracion, descripcion, anyo, precio, cat, imagen);
			ServerFrame.textArea.append("Creando imagen: " + imagen.getNombre() + " \n");
			pm.makePersistent(pelicula);
			ServerFrame.textArea.append("Pelicula: " + nombre + " creada exitosamente!\n");

			// Creamos el inventario con la cantidad de películas en stock!:
			ServerFrame.textArea.append("Creando inventario para la película: " + nombre + "\n");
			pm.makePersistent(new Inventario(cantidad, pelicula));
			ServerFrame.textArea.append("Inventario para le película " + nombre + " creado exitosamente..!\n");

			// Comprobamos si la casilla de novedad está activada:
			if (novedad == true) {
				// Enconten metemos la película a novedades:
				ServerFrame.textArea.append("Metiendo película a novedades...\n");
				pm.makePersistent(new Novedad(pelicula));
				ServerFrame.textArea.append("Película metida en novedades!\n");
			}

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
			Query<Pelicula> q = pm.newQuery("SELECT FROM " + Pelicula.class.getName() + " WHERE nombre != ' '");
			List<Pelicula> peliculas = (List<Pelicula>) q.executeList();
			for (Pelicula pelicula : peliculas) {
				// Vamos añadiendo las películas al array pasado:
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

	@Override
	public Cliente getCliente() throws RemoteException {
		// TODO Auto-generated method stub
		return this.cliente;
	}

	@Override
	public boolean alquilarPelicula(Alquiler alquiler) throws RemoteException {
		// TODO Auto-generated method stub
		boolean alquilerCorrecto = false;
		try {
			tx.begin();
			ServerFrame.textArea.append("Comprobando de cliente...\n");
			Cliente cliente = null;
			@SuppressWarnings("unchecked")
			Query<Cliente> q = pm.newQuery("SELECT FROM " + Cliente.class.getName());
			List<Cliente> clientes = q.executeList();
			for (Cliente client : clientes) {
				if (client.getNombre().equals(alquiler.getCliente().getNombre())
						&& client.getApellidos().equals(alquiler.getCliente().getApellidos())
						&& client.getFecha_nacimiento().equals(alquiler.getCliente().getFecha_nacimiento())) {
					cliente = client;
				}
			}

			ServerFrame.textArea.append("Comprobando de inventario...\n");
			Inventario inventario = null;
			@SuppressWarnings("unchecked")
			Query<Inventario> q2 = pm.newQuery("SELECT FROM " + Inventario.class.getName());
			List<Inventario> inventarios = q2.executeList();
			for (Inventario invent : inventarios) {
				if (invent.getPelicula().getNombre().equals(alquiler.getInventario().getPelicula().getNombre())) {
					inventario = invent;
				}
			}

			// Ahora ya podemos hacer persistente el alquiler:
			Alquiler nuevoAlquiler = new Alquiler(alquiler.getFecha_alquiler(), alquiler.getFecha_devolucion(), cliente,
					inventario);
			pm.makePersistent(nuevoAlquiler);
			ServerFrame.textArea.append("Alquiler de " + alquiler.getCliente().getNombre() + " creado exitosamente!\n");
			tx.commit();

			alquilerCorrecto = true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}

		return alquilerCorrecto;
	}

	@Override
	public List<Inventario> obtenerInventarios(List<Inventario> arrayInventarios) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();
			ServerFrame.textArea.append("Obteniendo inventarios de la BD...\n");

			@SuppressWarnings("unchecked")
			Query<Inventario> q = pm.newQuery("SELECT FROM " + Inventario.class.getName());
			List<Inventario> inventarios = (List<Inventario>) q.executeList();
			for (Inventario inventario : inventarios) {
				arrayInventarios.add(inventario);
			}
			ServerFrame.textArea.append("Inventarios obteneidos ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayInventarios;
	}

	@Override
	public List<Cliente> obtenerClientes(List<Cliente> arrayClientes) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();
			ServerFrame.textArea.append("Obteniendo clientes de la BD...\n");

			@SuppressWarnings("unchecked")
			Query<Cliente> q = pm.newQuery("SELECT FROM " + Cliente.class.getName());
			List<Cliente> clientes = (List<Cliente>) q.executeList();
			for (Cliente cliente : clientes) {
				arrayClientes.add(cliente);
			}
			ServerFrame.textArea.append("Clientes obteneidos ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayClientes;
	}

	@Override
	public boolean setMensaje(Mensaje mensaje) throws RemoteException {
		// TODO Auto-generated method stub
		boolean mensajeCorrecto = false;
		try {
			tx.begin();
			ServerFrame.textArea
					.append("Comprobación del usuario: '" + mensaje.getUsuario().getNombreUsuario() + "'\n");
			Usuario usuario = null;

			@SuppressWarnings("unchecked")
			Query<Usuario> q = pm.newQuery("SELECT FROM " + Usuario.class.getName() + " WHERE nombreUsuario == '"
					+ mensaje.getUsuario().getNombreUsuario() + "'");
			List<Usuario> usuarios = q.executeList();
			for (Usuario user : usuarios) {
				if (user.getNombreUsuario().equals(mensaje.getUsuario().getNombreUsuario())) {
					usuario = user;
				}
			}

			// Ahora toca crear la película:
			ServerFrame.textArea.append("Creando nuevo mensaje...\n");
			Mensaje nuevoMensaje = new Mensaje(mensaje.getMensaje(), mensaje.getFecha(), usuario);
			pm.makePersistent(nuevoMensaje);
			ServerFrame.textArea.append("Mensaje " + mensaje.getMensaje() + " enviado exitosamente!\n");

			tx.commit();

			mensajeCorrecto = true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}

		return mensajeCorrecto;
	}

	@Override
	public Usuario getUsuario() throws RemoteException {
		// TODO Auto-generated method stub
		return this.usuario;
	}

	@Override
	public List<Mensaje> obtenerMensajes(List<Mensaje> arrayMensajes) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();
			@SuppressWarnings("unchecked")
			Query<Mensaje> q = pm.newQuery("SELECT FROM " + Mensaje.class.getName());
			List<Mensaje> mensajes = (List<Mensaje>) q.executeList();
			// Solo obtendremos mensajes si los mensajes de ld bd son mayores
			// que arrayMensajes:
			if (mensajes.size() > arrayMensajes.size()) {
				for (Mensaje mensaje : mensajes) {
					arrayMensajes.add(mensaje);
				}
			}
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayMensajes;
	}

	@Override
	public List<Alquiler> obtenerAlquileres(List<Alquiler> arrayAlquileres) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();
			ServerFrame.textArea.append("Obteniendo alquileres...\n");
			@SuppressWarnings("unchecked")
			Query<Alquiler> q = pm.newQuery("SELECT FROM " + Alquiler.class.getName());
			List<Alquiler> alquileres = (List<Alquiler>) q.executeList();
			for (Alquiler alquiler : alquileres) {
				arrayAlquileres.add(alquiler);
			}
			ServerFrame.textArea.append("Alquileres obtenidos!\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayAlquileres;
	}

	@Override
	public List<Pelicula> obtenerPeliculasNuevas(List<Pelicula> arrayPeliculas) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();
			ServerFrame.textArea.append("Obteniendo películas nuevas de la BD...\n");

			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<Novedad> q = pm.newQuery("SELECT FROM " + Novedad.class.getName());
			List<Novedad> peliculas = (List<Novedad>) q.executeList();
			for (Novedad pelicula : peliculas) {
				// Vamos añadiendo las películas al array pasado:
				arrayPeliculas.add(pelicula.getPelicula());
			}
			ServerFrame.textArea.append("Películas nuevas obteneidas ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayPeliculas;
	}

	@Override
	public List<Usuario> obtenerUsuarios(List<Usuario> arrayUsuarios) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();
			ServerFrame.textArea.append("Obteniendo usuarios de la BD...\n");

			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<Usuario> q = pm.newQuery("SELECT FROM " + Usuario.class.getName());
			List<Usuario> usuarios = (List<Usuario>) q.executeList();
			for (Usuario usuario : usuarios) {
				// Vamos añadiendo las películas al array pasado:
				arrayUsuarios.add(usuario);
			}
			ServerFrame.textArea.append("Usuarios obtenenidos ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayUsuarios;
	}

	@Override
	public List<Amigo> obtenerAmigos(List<Amigo> arrayAmigos) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();

			ServerFrame.textArea.append("Obteniendo usuarios amigos de la BD...\n");
			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<Amigo> q = pm.newQuery("SELECT FROM " + Amigo.class.getName());
			List<Amigo> amigos = (List<Amigo>) q.executeList();
			for (Amigo amigo : amigos) {
				// Vamos añadiendo las películas al array pasado:
				arrayAmigos.add(amigo);
			}
			ServerFrame.textArea.append("Usuarios amigos obtenenidos ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayAmigos;
	}

	@Override
	public boolean setAmigo(String usuario, String amigo) throws RemoteException {
		// TODO Auto-generated method stub
		boolean amigoGuardado = false;
		try {
			tx.begin();

			Usuario _usuario = null;
			Usuario _amigo = null;

			@SuppressWarnings("unchecked")
			Query<Usuario> q = pm.newQuery("SELECT FROM " + Usuario.class.getName());
			List<Usuario> usuarios = (List<Usuario>) q.executeList();
			for (Usuario u : usuarios) {
				if (u.getNombreUsuario().equals(usuario)) {
					_usuario = u;
				} else if (u.getNombreUsuario().equals(amigo)) {
					_amigo = u;
				}
			}

			ServerFrame.textArea.append("Creando nuevo amigo...\n");
			Amigo a = new Amigo(_usuario, _amigo);
			pm.makePersistent(a);
			ServerFrame.textArea.append("Amigo " + a.getAmigo().getNombreUsuario() + " creado exitosamente!\n");
			tx.commit();
			amigoGuardado = true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}

		return amigoGuardado;
	}

	@Override
	public boolean setRecomendacion(String usuario, String amigo, Pelicula pelicula) throws RemoteException {
		// TODO Auto-generated method stub
		boolean recomendacionCorrecta = false;
		try {
			tx.begin();

			Usuario _usuario = null;
			Usuario _amigo = null;
			Pelicula _pelicula = null;

			@SuppressWarnings("unchecked")
			Query<Usuario> q = pm.newQuery("SELECT FROM " + Usuario.class.getName());
			List<Usuario> usuarios = (List<Usuario>) q.executeList();
			for (Usuario u : usuarios) {
				if (u.getNombreUsuario().equals(usuario)) {
					_usuario = u;
				} else if (u.getNombreUsuario().equals(amigo)) {
					_amigo = u;
				}
			}

			@SuppressWarnings("unchecked")
			Query<Pelicula> q1 = pm.newQuery("SELECT FROM " + Pelicula.class.getName());
			List<Pelicula> peliculas = (List<Pelicula>) q1.executeList();
			for (Pelicula pel : peliculas) {
				if (pel.getNombre().equals(pelicula.getNombre())) {
					_pelicula = pel;
					break;
				}
			}

			ServerFrame.textArea.append("Creando nueva recomendacion de amigo...\n");
			Recomendacion r = new Recomendacion(_usuario, _amigo, _pelicula);
			pm.makePersistent(r);
			ServerFrame.textArea.append("Recomendacion creado exitosamente!\n");
			tx.commit();
			recomendacionCorrecta = true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return recomendacionCorrecta;
	}

	@Override
	public List<Recomendacion> obtenerRecomendaciones(List<Recomendacion> arrayRecomendaciones) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();

			ServerFrame.textArea.append("Obteniendo recomendaciones de amigos de la BD...\n");
			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<Recomendacion> q = pm.newQuery("SELECT FROM " + Recomendacion.class.getName());
			List<Recomendacion> recomendaciones = (List<Recomendacion>) q.executeList();
			for (Recomendacion recomendacion : recomendaciones) {
				// Vamos añadiendo las películas al array pasado:
				arrayRecomendaciones.add(recomendacion);
			}
			ServerFrame.textArea.append("Recomendaciones de amigos obtenenidos ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayRecomendaciones;
	}

	@Override
	public boolean eliminarCliente(String nombre, String apellidos, String fechaNacimiento) throws RemoteException {
		// TODO Auto-generated method stub
		boolean clienteEliminado = false;
		try {
			tx.begin();
			ServerFrame.textArea.append("Obteniendo usuarios de la BD...\n");
			@SuppressWarnings("unchecked")
			Query<Usuario> q = pm.newQuery("SELECT FROM " + Usuario.class.getName());
			List<Usuario> usuarios = (List<Usuario>) q.executeList();
			Usuario usuarioActual = null;
			Cliente cliente = null;
			Direccion direccion = null;
			for (Usuario user : usuarios) {
				// Recorremos clientes para ver c˙al es el actual:
				if (user.getCliente().getNombre().equals(nombre) && user.getCliente().getApellidos().equals(apellidos)
						&& user.getCliente().getFecha_nacimiento().toString().equals(fechaNacimiento)) {
					// Lo tenemos:
					usuarioActual = user;
					cliente = user.getCliente();
					direccion = user.getCliente().getDireccion();
				}
			}

			// Eliminamos cliente de la base de datos:
			pm.deletePersistent(usuarioActual);
			pm.deletePersistent(cliente);
			pm.deletePersistent(direccion);

			clienteEliminado = true;
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return clienteEliminado;

	}

	@Override
	public boolean setPeliculaVista(Pelicula pelicula, Cliente cliente) throws RemoteException {
		// TODO Auto-generated method stub
		boolean peliculaVistaGuardada = false;
		try {
			tx.begin();

			Cliente _cliente = null;
			Pelicula _pelicula = null;

			@SuppressWarnings("unchecked")
			Query<Cliente> q = pm.newQuery("SELECT FROM " + Cliente.class.getName());
			List<Cliente> clientes = (List<Cliente>) q.executeList();
			for (Cliente c : clientes) {
				if (c.getNombre().equals(cliente.getNombre()) && c.getApellidos().equals(cliente.getApellidos())) {
					_cliente = c;
					break;
				}
			}

			@SuppressWarnings("unchecked")
			Query<Pelicula> q1 = pm.newQuery("SELECT FROM " + Pelicula.class.getName());
			List<Pelicula> peliculas = (List<Pelicula>) q1.executeList();
			for (Pelicula pel : peliculas) {
				if (pel.getNombre().equals(pelicula.getNombre())) {
					_pelicula = pel;
					break;
				}
			}

			ServerFrame.textArea.append("Creando nueva pelicula ya vista...\n");
			PeliculaVista peliculaVista = new PeliculaVista(_pelicula, _cliente);
			pm.makePersistent(peliculaVista);
			ServerFrame.textArea.append("Pelicula vista creada exitosamente!\n");

			tx.commit();
			peliculaVistaGuardada = true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return peliculaVistaGuardada;
	}

	/*
	 * Creado metodo para obtener las peliculas en la base de datos que serán
	 * pasadas al cliente. (non-Javadoc)
	 * 
	 * @see
	 * videoclub.server.gui.ICollector#obtenerPeliculasAlquiladas(java.util.
	 * List)
	 */
	@Override
	public List<Alquiler> obtenerPeliculasAlquiladas(List<Alquiler> arrayPeliculasAlquiladas) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();

			ServerFrame.textArea.append("Obteniendo peliculas alquiladas de la BD...\n");
			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<Alquiler> q = pm.newQuery("SELECT FROM " + Alquiler.class.getName());
			List<Alquiler> alquileres = (List<Alquiler>) q.executeList();
			for (Alquiler alquiler : alquileres) {
				// Vamos añadiendo las películas al array pasado:
				arrayPeliculasAlquiladas.add(alquiler);
			}
			ServerFrame.textArea.append("Peliculas alquiladas obtenenidas ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayPeliculasAlquiladas;
	}

	@Override
	public List<Usuario> obtenerUsuariosConectados() throws RemoteException {
		// TODO Auto-generated method stub
		return this.usuariosConectados;
	}

	@Override
	public synchronized void broadcastMessage(Object[] mensaje) throws RemoteException {
		// TODO Auto-generated method stub
		// Notificamos a todos los usuarios conectados al servidor:
		this.remoteObservable.notifyRemoteObserversChatMessages(mensaje);
	}

	@Override
	public synchronized void desconectarUsuario(String usuario) throws RemoteException {
		// TODO Auto-generated method stub
		// Buscamos usuario y lo quitamos de la lista:
		for (int i = 0; i < usuariosConectados.size(); i++) {
			if (usuariosConectados.get(i).getNombreUsuario().equals(usuario)) {
				usuariosConectados.remove(i);
			}
		}

		if (this.usuariosConectados.size() >= 1) {
			// Notificamos a todos los usuarios conectados al servidor:
			this.remoteObservable.notifyRemoteObserversUsuarioDesconectado();
		}
	}

	@Override
	public synchronized void conectarUsuario() throws RemoteException {
		// TODO Auto-generated method stub
		// Notificamos a todos los usuarios conectados al servidor:
		this.remoteObservable.notifyRemoteObserversUsuarioDesconectado();
	}
}