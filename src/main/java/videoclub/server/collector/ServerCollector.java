package videoclub.server.collector;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import videoclub.server.gui.ServerFrame;
import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Amigo;
import videoclub.server.jdo.Categoria;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Direccion;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Inventario;
import videoclub.server.jdo.Mensaje;
import videoclub.server.jdo.Noticia;
import videoclub.server.jdo.NotificarAlquiler;
import videoclub.server.jdo.Novedad;
import videoclub.server.jdo.Opinion;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.PeliculaFavorita;
import videoclub.server.jdo.PeliculaPendiente;
import videoclub.server.jdo.PeliculaVista;
import videoclub.server.jdo.ProximoEstreno;
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
	private String datanucleusProperties = "datanucleus.properties";

	public ServerCollector(boolean isTest) throws RemoteException {
		super();
		this.remoteObservable = new RemoteObservable();
		this.datanucleusProperties = "datanucleus.properties";

		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(datanucleusProperties);
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
					Logger.getLogger(getClass().getName()).log(Level.INFO, "Servidor activo y a la espera...");
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

	public synchronized void deleteRemoteObservers() {
		this.remoteObservable.deleteRemoteObservers();
	}

	public synchronized int countRemoteObservers() {
		return this.remoteObservable.countRemoteObservers();
	}

	@Override
	public boolean registerUser(String nombreUsuario, String contraseña, String correo, String nombre, String apellidos,
			Date fechaNacimiento, String calle, String ciudad, String pais) throws RemoteException {
		// TODO Auto-generated method stub
		boolean denegarRegistro = false;
		try {
			tx.begin();
			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Comprobación de si el usuario: '" + nombreUsuario + "' ya existe\n");
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
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						"El usuario: " + nombreUsuario + " ya está registrado!, ERROR!\n");
			} else {
				Logger.getLogger(getClass().getName()).log(Level.INFO, "Creando nuevo cliente...");
				Cliente cliente = new Cliente(nombre, apellidos, fechaNacimiento, new Direccion(calle, ciudad, pais));
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						"Cliente: " + nombre + " - " + apellidos + " creado exitosamente! :D\n");
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						"Creando nuevo usuario: " + nombreUsuario + "\n");
				user = new Usuario(nombreUsuario, contraseña, correo, cliente);
				Logger.getLogger(getClass().getName()).log(Level.INFO, "Usuario creado: " + nombreUsuario + "\n");
				pm.makePersistent(user);
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						"Sincronizado con las bases de datos... ! Correcto\n");
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
			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Comprobación de si el usuario '" + nombreUsuario + "' es correcto...\n");

			// Ejecutamos consulta para comprobar si el usuario es correcto:
			@SuppressWarnings("unchecked")
			Query<Usuario> q = pm.newQuery("SELECT FROM " + Usuario.class.getName() + " WHERE nombreUsuario == '"
					+ nombreUsuario + "' && contraseña == '" + contraseña + "'");
			List<Usuario> usuarios = q.executeList();
			for (Usuario usuario : usuarios) {
				// Comprobamos que el usuario sea correcto:
				if (usuario.getNombreUsuario().equals(nombreUsuario) && usuario.getContraseña().equals(contraseña)) {
					inicioCorrecto = true;
					Logger.getLogger(getClass().getName()).log(Level.INFO, "Usuario y contraseña correctas! :D\n");
					Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo datos del cliente...\n");
					this.cliente = usuario.getCliente();
					this.usuario = usuario;
					this.usuariosConectados.add(usuario);
					Logger.getLogger(getClass().getName()).log(Level.INFO, "Datos del cliente obtenidos!\n");
					Logger.getLogger(getClass().getName()).log(Level.INFO,
							"Bienvenido " + usuario.getCliente().getNombre() + " :D\n");
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
			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Comprobación de la categoria: '" + categoria + "'\n");
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
				Logger.getLogger(getClass().getName()).log(Level.INFO, "Creando nueva categoria...\n");
				cat = new Categoria(categoria);
				pm.makePersistent(cat);
				Logger.getLogger(getClass().getName()).log(Level.INFO, "Categoria creada con éxito!\n");
			}

			// Ahora toca crear la película:
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Creando nueva película...\n");
			Pelicula pelicula = new Pelicula(nombre, duracion, descripcion, anyo, precio, cat, imagen);
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Creando imagen: " + imagen.getNombre() + " \n");
			pm.makePersistent(pelicula);
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Pelicula: " + nombre + " creada exitosamente!\n");

			// Creamos el inventario con la cantidad de películas en stock!:
			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Creando inventario para la película: " + nombre + "\n");
			pm.makePersistent(new Inventario(cantidad, pelicula));
			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Inventario para le película " + nombre + " creado exitosamente..!\n");

			// Comprobamos si la casilla de novedad está activada:
			if (novedad == true) {
				// Enconten metemos la película a novedades:
				Logger.getLogger(getClass().getName()).log(Level.INFO, "Metiendo película a novedades...\n");
				pm.makePersistent(new Novedad(pelicula));
				Logger.getLogger(getClass().getName()).log(Level.INFO, "Película metida en novedades!\n");
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
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo películas de la BD...\n");

			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<Pelicula> q = pm.newQuery("SELECT FROM " + Pelicula.class.getName() + " WHERE nombre != ' '");
			List<Pelicula> peliculas = (List<Pelicula>) q.executeList();
			for (Pelicula pelicula : peliculas) {
				// Vamos añadiendo las películas al array pasado:
				arrayPeliculas.add(pelicula);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Películas obteneidas ! :D\n");
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
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Comprobando de cliente...\n");
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

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Comprobando de inventario...\n");
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
			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Alquiler de " + alquiler.getCliente().getNombre() + " creado exitosamente!\n");
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
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo inventarios de la BD...\n");

			@SuppressWarnings("unchecked")
			Query<Inventario> q = pm.newQuery("SELECT FROM " + Inventario.class.getName());
			List<Inventario> inventarios = (List<Inventario>) q.executeList();
			for (Inventario inventario : inventarios) {
				arrayInventarios.add(inventario);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Inventarios obteneidos ! :D\n");
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
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo clientes de la BD...\n");

			@SuppressWarnings("unchecked")
			Query<Cliente> q = pm.newQuery("SELECT FROM " + Cliente.class.getName());
			List<Cliente> clientes = (List<Cliente>) q.executeList();
			for (Cliente cliente : clientes) {
				arrayClientes.add(cliente);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Clientes obteneidos ! :D\n");
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
			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Comprobación del usuario: '" + mensaje.getUsuario().getNombreUsuario() + "'\n");
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
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Creando nuevo mensaje...\n");
			Mensaje nuevoMensaje = new Mensaje(mensaje.getMensaje(), mensaje.getFecha(), usuario);
			pm.makePersistent(nuevoMensaje);
			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Mensaje " + mensaje.getMensaje() + " enviado exitosamente!\n");

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
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo alquileres...\n");
			@SuppressWarnings("unchecked")
			Query<Alquiler> q = pm.newQuery("SELECT FROM " + Alquiler.class.getName());
			List<Alquiler> alquileres = (List<Alquiler>) q.executeList();
			for (Alquiler alquiler : alquileres) {
				arrayAlquileres.add(alquiler);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Alquileres obtenidos!\n");
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
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo películas nuevas de la BD...\n");

			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<Novedad> q = pm.newQuery("SELECT FROM " + Novedad.class.getName());
			List<Novedad> peliculas = (List<Novedad>) q.executeList();
			for (Novedad pelicula : peliculas) {
				// Vamos añadiendo las películas al array pasado:
				arrayPeliculas.add(pelicula.getPelicula());
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Películas nuevas obteneidas ! :D\n");
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
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo usuarios de la BD...\n");

			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<Usuario> q = pm.newQuery("SELECT FROM " + Usuario.class.getName());
			List<Usuario> usuarios = (List<Usuario>) q.executeList();
			for (Usuario usuario : usuarios) {
				// Vamos añadiendo las películas al array pasado:
				arrayUsuarios.add(usuario);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Usuarios obtenenidos ! :D\n");
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

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo usuarios amigos de la BD...\n");
			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<Amigo> q = pm.newQuery("SELECT FROM " + Amigo.class.getName());
			List<Amigo> amigos = (List<Amigo>) q.executeList();
			for (Amigo amigo : amigos) {
				// Vamos añadiendo las películas al array pasado:
				arrayAmigos.add(amigo);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Usuarios amigos obtenenidos ! :D\n");
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

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Creando nuevo amigo...\n");
			Amigo a = new Amigo(_usuario, _amigo);
			pm.makePersistent(a);
			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Amigo " + a.getAmigo().getNombreUsuario() + " creado exitosamente!\n");
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

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Creando nueva recomendacion de amigo...\n");
			Recomendacion r = new Recomendacion(_usuario, _amigo, _pelicula);
			pm.makePersistent(r);
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Recomendacion creado exitosamente!\n");
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

			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Obteniendo recomendaciones de amigos de la BD...\n");
			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<Recomendacion> q = pm.newQuery("SELECT FROM " + Recomendacion.class.getName());
			List<Recomendacion> recomendaciones = (List<Recomendacion>) q.executeList();
			for (Recomendacion recomendacion : recomendaciones) {
				// Vamos añadiendo las películas al array pasado:
				arrayRecomendaciones.add(recomendacion);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Recomendaciones de amigos obtenenidos ! :D\n");
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
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo usuarios de la BD...\n");
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

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Creando nueva pelicula ya vista...\n");
			PeliculaVista peliculaVista = new PeliculaVista(_pelicula, _cliente);
			pm.makePersistent(peliculaVista);
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Pelicula vista creada exitosamente!\n");

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
	 * @see videoclub.server.gui.ICollector#obtenerPeliculasAlquiladas(java.util.
	 * List)
	 */
	@Override
	public List<Alquiler> obtenerPeliculasAlquiladas(List<Alquiler> arrayPeliculasAlquiladas) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo peliculas alquiladas de la BD...\n");
			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<Alquiler> q = pm.newQuery("SELECT FROM " + Alquiler.class.getName());
			List<Alquiler> alquileres = (List<Alquiler>) q.executeList();
			for (Alquiler alquiler : alquileres) {
				// Vamos añadiendo las películas al array pasado:
				arrayPeliculasAlquiladas.add(alquiler);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Peliculas alquiladas obtenenidas ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayPeliculasAlquiladas;
	}

	@Override
	public boolean setPeliculaFavorita(Pelicula pelicula, Cliente cliente) throws RemoteException {
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

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Creando nueva pelicula favorita...\n");
			PeliculaFavorita peliculaFavorita = new PeliculaFavorita(_pelicula, _cliente);
			pm.makePersistent(peliculaFavorita);
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Pelicula favorita creada exitosamente!\n");

			tx.commit();
			peliculaVistaGuardada = true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return peliculaVistaGuardada;

	}

	@Override
	public List<PeliculaFavorita> obtenerPeliculasFavoritas(List<PeliculaFavorita> arrayPeliculasFavoritas)
			throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo peliculas favoritas de la BD...\n");
			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<PeliculaFavorita> q = pm.newQuery("SELECT FROM " + PeliculaFavorita.class.getName());
			List<PeliculaFavorita> peliculasFavoritas = (List<PeliculaFavorita>) q.executeList();
			for (PeliculaFavorita peliFavorita : peliculasFavoritas) {
				// Vamos añadiendo las películas al array pasado:
				arrayPeliculasFavoritas.add(peliFavorita);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Peliculas favoritas obtenenidas ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayPeliculasFavoritas;
	}

	@Override
	public List<PeliculaPendiente> obtenerPeliculasPendientes(List<PeliculaPendiente> arrayPeliculasPendientes)
			throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo peliculas pendientes de la BD...\n");
			@SuppressWarnings("unchecked")
			Query<PeliculaPendiente> q = pm.newQuery("SELECT FROM " + PeliculaPendiente.class.getName());
			List<PeliculaPendiente> peliculasPendientes = (List<PeliculaPendiente>) q.executeList();
			for (PeliculaPendiente peliculaPendiente : peliculasPendientes) {
				// Vamos aÒadiendo las pelÌculas al array pasado:
				arrayPeliculasPendientes.add(peliculaPendiente);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Peliculas pendientes obtenenidas ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayPeliculasPendientes;
	}

	@Override
	public boolean setPeliculaPendiente(Pelicula pelicula, Cliente cliente) throws RemoteException {
		// TODO Auto-generated method stub
		boolean peliculaPendienteGuardada = false;
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

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Creando nueva pelicula pendiente...\n");
			PeliculaPendiente peliculaFavorita = new PeliculaPendiente(_pelicula, _cliente);
			pm.makePersistent(peliculaFavorita);
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Pelicula pendiente creada exitosamente!\n");

			tx.commit();
			peliculaPendienteGuardada = true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return peliculaPendienteGuardada;
	}

	@Override
	public List<PeliculaVista> obtenerPeliculasVistas(List<PeliculaVista> arrayPeliculasVistas) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo peliculas vistas de la BD...\n");
			// Sacamos todas las categorias de la BD:
			@SuppressWarnings("unchecked")
			Query<PeliculaVista> q = pm.newQuery("SELECT FROM " + PeliculaVista.class.getName());
			List<PeliculaVista> peliculasVistas = (List<PeliculaVista>) q.executeList();
			for (PeliculaVista peliculaVista : peliculasVistas) {
				// Vamos aÒadiendo las pelÌculas al array pasado:
				arrayPeliculasVistas.add(peliculaVista);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Peliculas vistas obtenenidas ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayPeliculasVistas;
	}

	@Override
	public List<Opinion> obtenerOpiniones(List<Opinion> arrayOpiniones) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo opiniones de la BD...\n");
			@SuppressWarnings("unchecked")
			Query<Opinion> q = pm.newQuery("SELECT FROM " + Opinion.class.getName());
			List<Opinion> opiniones = (List<Opinion>) q.executeList();
			for (Opinion opi : opiniones) {
				arrayOpiniones.add(opi);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Opiniones obtenenidas ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayOpiniones;
	}

	@Override
	public boolean setOpinion(Pelicula pelicula, Usuario user, String opinion) throws RemoteException {
		// TODO Auto-generated method stub
		boolean opinionGuardada = false;
		try {
			tx.begin();

			Usuario _usuario = null;
			Pelicula _pelicula = null;

			@SuppressWarnings("unchecked")
			Query<Usuario> q = pm.newQuery("SELECT FROM " + Usuario.class.getName());
			List<Usuario> usuarios = (List<Usuario>) q.executeList();
			for (Usuario u : usuarios) {
				if (u.getNombreUsuario().equals(user.getNombreUsuario())) {
					_usuario = u;
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

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Creando nueva opinion...\n");
			Opinion opinar = new Opinion(_pelicula, _usuario, opinion);
			pm.makePersistent(opinar);
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Opinion creada exitosamente!\n");

			tx.commit();
			opinionGuardada = true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return opinionGuardada;
	}

	@Override
	public List<Noticia> obtenerNoticias(List<Noticia> arrayNoticias) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo noticias de la BD...\n");
			@SuppressWarnings("unchecked")
			Query<Noticia> q = pm.newQuery("SELECT FROM " + Noticia.class.getName());
			List<Noticia> noticias = (List<Noticia>) q.executeList();
			for (Noticia noticia : noticias) {
				arrayNoticias.add(noticia);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Noticias obtenenidas ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayNoticias;
	}

	@Override
	public boolean setNoticia(String noticia) throws RemoteException {
		// TODO Auto-generated method stub
		boolean noticiaGuardada = false;
		try {
			tx.begin();

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Creando nueva noticia...\n");
			Noticia not = new Noticia(noticia);
			pm.makePersistent(not);
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Noticia creada exitosamente!\n");

			tx.commit();
			noticiaGuardada = true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return noticiaGuardada;
	}

	@Override
	public List<ProximoEstreno> obtenerProximosEstrenos(List<ProximoEstreno> arrayProximosEstrenos)
			throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Obteniendo proximos estrenos de la BD...\n");
			@SuppressWarnings("unchecked")
			Query<ProximoEstreno> q = pm.newQuery("SELECT FROM " + ProximoEstreno.class.getName());
			List<ProximoEstreno> estrenos = (List<ProximoEstreno>) q.executeList();
			for (ProximoEstreno estreno : estrenos) {
				arrayProximosEstrenos.add(estreno);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Proximos estrenos obtenenidas ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayProximosEstrenos;
	}

	@Override
	public boolean setProximoEstreno(String pelicula) throws RemoteException {
		// TODO Auto-generated method stub
		boolean estrenoGuardado = false;
		try {
			tx.begin();

			Logger.getLogger(getClass().getName()).log(Level.INFO, "Creando nuevo estreno...\n");
			ProximoEstreno estreno = new ProximoEstreno(pelicula);
			pm.makePersistent(estreno);
			Logger.getLogger(getClass().getName()).log(Level.INFO, "Proximo estreno creado exitosamente!\n");

			tx.commit();
			estrenoGuardado = true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return estrenoGuardado;
	}

	@Override
	public boolean setNotificacionAlquiler(Date fechaNotificacion) throws RemoteException {
		// TODO Auto-generated method stub
		boolean notificacionAlquilerCorrecto = false;
		try {
			tx.begin();

			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Creando nueva notificación de alquiler para el administrador...\n");
			NotificarAlquiler notificacion = new NotificarAlquiler(fechaNotificacion);
			pm.makePersistent(notificacion);
			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Notificación de alquiler para el administrador creada exitosamente!\n");

			tx.commit();
			notificacionAlquilerCorrecto = true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return notificacionAlquilerCorrecto;
	}

	@Override
	public List<NotificarAlquiler> obtenerNotificacionesAlquileres(
			List<NotificarAlquiler> arrayNotificacionesAlquileres) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();

			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Obteniendo notificaciones de alquileres para el administrador de la BD...\n");
			@SuppressWarnings("unchecked")
			Query<NotificarAlquiler> q = pm.newQuery("SELECT FROM " + NotificarAlquiler.class.getName());
			List<NotificarAlquiler> notificaciones = (List<NotificarAlquiler>) q.executeList();
			for (NotificarAlquiler notificacion : notificaciones) {
				arrayNotificacionesAlquileres.add(notificacion);
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Notificaciones de alquileres para el administrador obtenenidas ! :D\n");
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayNotificacionesAlquileres;
	}

	@Override
	public List<Categoria> obtenerCategorias(List<Categoria> arrayCategorias) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			tx.begin();
			@SuppressWarnings("unchecked")
			Query<Categoria> q = pm.newQuery("SELECT FROM " + Categoria.class.getName());
			List<Categoria> categorias = (List<Categoria>) q.executeList();
			for (Categoria categoria : categorias) {
				arrayCategorias.add(categoria);
			}
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		return arrayCategorias;
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