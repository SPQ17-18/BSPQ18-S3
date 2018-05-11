package myRMIConection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.databene.contiperf.timer.RandomTimer;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import videoclub.client.main.Client;
import videoclub.server.collector.ICollector;
import videoclub.server.collector.ServerCollector;
import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Amigo;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Direccion;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Inventario;
import videoclub.server.jdo.Mensaje;
import videoclub.server.jdo.Noticia;
import videoclub.server.jdo.NotificarAlquiler;
import videoclub.server.jdo.Opinion;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.PeliculaFavorita;
import videoclub.server.jdo.PeliculaPendiente;
import videoclub.server.jdo.PeliculaVista;
import videoclub.server.jdo.ProximoEstreno;
import videoclub.server.jdo.Recomendacion;
import videoclub.server.jdo.Usuario;

public class RMICollectorTest {
	@Rule
	public ContiPerfRule contiPerfRule = new ContiPerfRule();
	private String[] arg = { "127.0.0.1", "1099", "RMICollectorTest" };
	private static String cwd = RMICollectorTest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;
	public static ICollector collector;
	public String url = "http://www.imdb.com/list/ls025439193/";
	public String urlWeb = "http://www.imdb.com";

	private List<Direccion> arrayDirecciones = null;
	private List<Cliente> arrayClientes = null;
	private List<Usuario> arrayUsuarios = null;
	private List<Mensaje> arrayMensajes = null;
	private List<Opinion> arrayOpiniones = null;
	private List<Pelicula> arrayPeliculas = null;
	private List<Noticia> arrayNoticias = null;
	private List<ProximoEstreno> arrayProximosEstrenos = null;
	private List<Inventario> arrayInventarios = null;
	private List<Alquiler> arrayAlquileres = null;
	private List<Amigo> arrayAmigos = null;
	private List<Alquiler> arrayPeliculasAlquiladas = null;
	private List<PeliculaFavorita> arrayPeliculasFavoritas = null;
	private List<Pelicula> arrayPeliculasNuevas = null;
	private List<PeliculaPendiente> arrayPeliculasPendientes = null;
	private List<PeliculaVista> arrayPeliculasVistas = null;
	private List<Recomendacion> arrayRecomendaciones = null;
	private List<NotificarAlquiler> arrayNotificacionesAlquileres = null;

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(RMICollectorTest.class);
	}

	@BeforeClass
	static public void setUp() {
		// Lanzamiento del Proceso RMIRegistry:
		class RMIRegistryRunnable implements Runnable {

			public void run() {
				try {
					java.rmi.registry.LocateRegistry.createRegistry(1099);
					Logger.getLogger(getClass().getName()).log(Level.INFO, "RMI registry ready.");
				} catch (Exception e) {
					Logger.getLogger(getClass().getName()).log(Level.WARNING, "Exception starting RMI registry: ");
					Logger.getLogger(getClass().getName()).log(Level.WARNING, e.getMessage());
				}
			}
		}

		rmiRegistryThread = new Thread(new RMIRegistryRunnable());
		rmiRegistryThread.start();
		try {
			Thread.sleep(2000);
			Logger.getLogger(RMIRegistryRunnable.class.getName()).log(Level.INFO, "RMI Registry sleeping...");
		} catch (InterruptedException ie) {
			Logger.getLogger(RMIRegistryRunnable.class.getName()).log(Level.WARNING, ie.getMessage());
		}

		// Lanzamiento del Proceso RMIServer:
		class RMIServerRunnable implements Runnable {

			public void run() {
				Logger.getLogger(RMIServerRunnable.class.getName()).log(Level.INFO,
						"This is a test to check how mvn test executes this test without external interaction; JVM properties by program");
				Logger.getLogger(RMIServerRunnable.class.getName()).log(Level.INFO, "**************: " + cwd);
				System.setProperty("java.rmi.server.codebase", "file:" + cwd);
				System.setProperty("java.security.policy", "target\\classes\\security\\java.policy");

				if (System.getSecurityManager() == null) {
					System.setSecurityManager(new SecurityManager());
				}

				String name = "//127.0.0.1:1099/RMICollectorTest";
				Logger.getLogger(RMIServerRunnable.class.getName()).log(Level.INFO, " * TestServer name: " + name);
				try {
					// Insercción de datos "videoclubTEST database, datanuclesTEST.properties":
					collector = new ServerCollector(false);
					Naming.rebind(name, collector);
				} catch (RemoteException re) {
					Logger.getLogger(RMIServerRunnable.class.getName()).log(Level.WARNING,
							" # Collector RemoteException: " + re.getMessage());
					System.exit(-1);
				} catch (MalformedURLException murle) {
					Logger.getLogger(RMIServerRunnable.class.getName()).log(Level.INFO,
							" # Collector MalformedURLException: " + murle.getMessage());
					System.exit(-1);
				}
			}
		}
		rmiServerThread = new Thread(new RMIServerRunnable());
		rmiServerThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			Logger.getLogger(RMIServerRunnable.class.getName()).log(Level.WARNING, ie.getMessage());
		}

	}

	/**
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void testCreateALL() throws ParseException, IOException {
		arrayDirecciones = new ArrayList<Direccion>();
		arrayDirecciones.add(new Direccion("Calle 1", "Bilbao", "España"));
		arrayDirecciones.add(new Direccion("Calle 2", "Madrid", "España"));
		arrayDirecciones.add(new Direccion("Calle 3", "Lugo", "España"));
		arrayDirecciones.add(new Direccion("Calle 4", "Barcelona", "España"));
		arrayDirecciones.add(new Direccion("Calle 5", "Burgos", "España"));

		Logger.getLogger(getClass().getName()).log(Level.INFO, " # DIRECCIONES CREADAS!");

		arrayClientes = new ArrayList<Cliente>();
		arrayClientes.add(new Cliente("David", "García Pérez", new SimpleDateFormat("yyyy-MM-dd").parse("1995-07-15"),
				arrayDirecciones.get(0)));
		arrayClientes.add(new Cliente("Asier", "Urquijo", new SimpleDateFormat("yyyy-MM-dd").parse("1996-12-25"),
				arrayDirecciones.get(1)));
		arrayClientes.add(new Cliente("Asier", "Aldekoa", new SimpleDateFormat("yyyy-MM-dd").parse("1997-04-4"),
				arrayDirecciones.get(2)));
		arrayClientes.add(new Cliente("Aitor", "Martínez", new SimpleDateFormat("yyyy-MM-dd").parse("1996-06-5"),
				arrayDirecciones.get(3)));
		arrayClientes.add(new Cliente("Javier", "Pérez Esnaola", new SimpleDateFormat("yyyy-MM-dd").parse("1998-01-1"),
				arrayDirecciones.get(4)));

		Logger.getLogger(getClass().getName()).log(Level.INFO, " # CLIENTES CREADOS!");

		arrayUsuarios = new ArrayList<Usuario>();
		arrayUsuarios.add(new Usuario("dgpUser1", "12345", "dgp.g.p@opendeusto.es", arrayClientes.get(0)));
		arrayUsuarios.add(new Usuario("auUser2", "12345", "au.g.p@opendeusto.es", arrayClientes.get(1)));
		arrayUsuarios.add(new Usuario("aaUser3", "12345", "aa.g.p@opendeusto.es", arrayClientes.get(2)));
		arrayUsuarios.add(new Usuario("amUser4", "12345", "am.g.p@opendeusto.es", arrayClientes.get(3)));
		arrayUsuarios.add(new Usuario("jpeUser5", "12345", "jpe.g.p@opendeusto.es", arrayClientes.get(4)));

		Logger.getLogger(getClass().getName()).log(Level.INFO, " # USUARIOS CREADOS!");

		for (Usuario user : arrayUsuarios) {
			collector.registerUser(user.getNombreUsuario(), user.getContraseña(), user.getCorreo(),
					user.getCliente().getNombre(), user.getCliente().getApellidos(),
					user.getCliente().getFecha_nacimiento(), user.getCliente().getDireccion().getCalle(),
					user.getCliente().getDireccion().getCiudad(), user.getCliente().getDireccion().getPais());
		}

		Logger.getLogger(getClass().getName()).log(Level.INFO, " # USUARIOS REGISTRADOS!");

		// CREANDO MENSAJES:
		arrayMensajes = new ArrayList<Mensaje>();
		arrayMensajes.add(new Mensaje("Hola, estoy enviando un mensaje", new Date(), arrayUsuarios.get(0)));
		arrayMensajes.add(new Mensaje("Hola, yo también y que tal", new Date(), arrayUsuarios.get(1)));
		arrayMensajes.add(new Mensaje("Probando a enviar mensaje....", new Date(), arrayUsuarios.get(2)));
		arrayMensajes.add(new Mensaje("Hay alguien?", new Date(), arrayUsuarios.get(3)));
		arrayMensajes.add(new Mensaje("Yo estoy conectado!", new Date(), arrayUsuarios.get(4)));
		for (Mensaje mensaje : arrayMensajes) {
			assertTrue(collector.setMensaje(mensaje));
		}

		Logger.getLogger(getClass().getName()).log(Level.INFO, " # MENSAJES CREADOS!");

		// CREANDO PELÍCULAS:
		if (getStatusConnectionCode(url) == 200) {

			// Obtengo el HTML de la web en un objeto Document
			Document document = getHtmlDocument(url);

			// Busco todas las entradas que estan dentro de:
			Elements entradas = document.select("div.lister-item.mode-detail");
			Logger.getLogger(getClass().getName()).log(Level.INFO,
					"Número de entradas en la página inicial de http://www.imdb.com/list/ls025439193/: "
							+ entradas.size() + "\n");

			// Paseo cada una de las entradas
			for (Element elem : entradas) {
				String titulo = elem.getElementsByClass("lister-item-header").get(0).getElementsByAttribute("href")
						.text();
				String anyo = elem.getElementsByClass("lister-item-header").get(0)
						.getElementsByClass("lister-item-year text-muted unbold").text();
				String descripcion = elem.getElementsByClass("lister-item-content").text();
				String duracion = elem.select("div.lister-item-content").select("p.text-muted.text-small")
						.select("span.runtime").text();
				String categoria = elem.select("div.lister-item-content").select("p.text-muted.text-small")
						.select("span.genre").text();
				String srcImage = elem.select("div.lister-item-image.ribbonize").select("img.loadlate").toString();

				URL url = new URL(obtenerSrcUrl(srcImage));
				byte[] data = extractBytes(url);

				// Añadimos película:
				assertTrue(collector.insertarPelicula(titulo, Integer.parseInt(obtenerDuracion(duracion)),
						obtenerDescripcion(descripcion).getBytes(), Integer.parseInt(obtenerAnyo(anyo)), 5F,
						obtenerGenero(categoria), 5, new Imagen(obtenerSrcUrl(srcImage), data), true));
				Logger.getLogger(getClass().getName()).log(Level.INFO, " # INSERTADA PELICULA: " + titulo);
			}

			Logger.getLogger(getClass().getName()).log(Level.INFO, " # PELICULAS CREADAS!");

		} else {
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # ERROR AL CREAR PELÍCULAS!");
		}

		// CREANDO OPINIONES:
		arrayOpiniones = new ArrayList<Opinion>();
		arrayPeliculas = new ArrayList<Pelicula>();
		arrayPeliculas = collector.obtenerPeliculas(arrayPeliculas);
		arrayOpiniones.add(new Opinion(arrayPeliculas.get(0), arrayUsuarios.get(0),
				"Me ha gustado mucho la película..." + arrayPeliculas.get(0).getNombre() + "..1"));
		arrayOpiniones.add(new Opinion(arrayPeliculas.get(1), arrayUsuarios.get(1),
				"Muy mala.." + arrayPeliculas.get(1).getNombre() + "..2"));
		arrayOpiniones.add(new Opinion(arrayPeliculas.get(2), arrayUsuarios.get(2),
				"No me ha gustado nada!" + arrayPeliculas.get(2).getNombre() + "..3"));
		arrayOpiniones.add(new Opinion(arrayPeliculas.get(3), arrayUsuarios.get(3),
				"No la recomiendo!" + arrayPeliculas.get(3).getNombre() + "..4"));
		arrayOpiniones.add(new Opinion(arrayPeliculas.get(4), arrayUsuarios.get(4),
				"Está bien, 7/10" + arrayPeliculas.get(4).getNombre() + "..5"));

		for (Opinion opinion : arrayOpiniones) {
			assertTrue(collector.setOpinion(opinion.getPelicula(), opinion.getUser(), opinion.getDescripcionOpinion()));
		}

		// CREANDO PELICULAS FAVORITAS:
		for (Pelicula pelicula : arrayPeliculas) {
			assertTrue(collector.setPeliculaFavorita(pelicula, arrayClientes.get(0)));
		}

		// CREANDO PELICULAS PENDIENTES:
		for (Pelicula pelicula : arrayPeliculas) {
			assertTrue(collector.setPeliculaPendiente(pelicula, arrayClientes.get(0)));
		}

		// CREANDO PELICULAS VISTAS:
		for (Pelicula pelicula : arrayPeliculas) {
			assertTrue(collector.setPeliculaVista(pelicula, arrayClientes.get(0)));
		}

		// CREANDO RECOMENDACIONES:
		for (Pelicula pelicula : arrayPeliculas) {
			assertTrue(collector.setRecomendacion("dgpUser", "aaUser", pelicula));
		}

		// CREANDO AMIGOS:
		collector.setAmigo(arrayUsuarios.get(0).getNombreUsuario(), arrayUsuarios.get(1).getNombreUsuario());
		collector.setAmigo(arrayUsuarios.get(0).getNombreUsuario(), arrayUsuarios.get(2).getNombreUsuario());
		collector.setAmigo(arrayUsuarios.get(0).getNombreUsuario(), arrayUsuarios.get(3).getNombreUsuario());
		collector.setAmigo(arrayUsuarios.get(0).getNombreUsuario(), arrayUsuarios.get(4).getNombreUsuario());

		// CREANDO ALQUILERES:
		arrayInventarios = new ArrayList<Inventario>();
		arrayInventarios = collector.obtenerInventarios(arrayInventarios);

		for (Inventario inventario : arrayInventarios) {
			assertTrue(collector.alquilarPelicula(new Alquiler(new SimpleDateFormat("yyyy-MM-dd").parse("2018-5-15"),
					new SimpleDateFormat("yyyy-MM-dd").parse("2018-6-15"), arrayClientes.get(0), inventario)));
		}
		// CREANDO NOTIFICACIONES DE ALQUILERES:
		arrayNotificacionesAlquileres = new ArrayList<NotificarAlquiler>();
		arrayNotificacionesAlquileres.add(new NotificarAlquiler(new Date()));
		arrayNotificacionesAlquileres.add(new NotificarAlquiler(new Date()));
		arrayNotificacionesAlquileres.add(new NotificarAlquiler(new Date()));
		arrayNotificacionesAlquileres.add(new NotificarAlquiler(new Date()));
		arrayNotificacionesAlquileres.add(new NotificarAlquiler(new Date()));

		for (NotificarAlquiler notificar : arrayNotificacionesAlquileres) {
			assertTrue(collector.setNotificacionAlquiler(notificar.getFechaNotificacion()));
		}

		Logger.getLogger(getClass().getName()).log(Level.INFO, " # NOTIFICACIONES DE ALQUILERES CREADOS!");

	}

	/**
	 * TEST PARA CREAR NUEVAS NOTICIAS:
	 * 
	 * @throws RemoteException
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testCrearNoticias() throws RemoteException {

		arrayNoticias = new ArrayList<Noticia>();
		arrayNoticias.add(new Noticia("¡Vamos a actualizar la plataforma la próxima semana!"));
		arrayNoticias.add(new Noticia("¡Nuevas películas añadidas!"));
		arrayNoticias.add(new Noticia("¡Hemos actualizado la base de datos de nuevas películas!"));
		arrayNoticias.add(new Noticia("¡Nuevos descuentos durante esta semana!"));
		arrayNoticias.add(new Noticia("¡Habrá próximos estrenos pronto!"));
		for (Noticia noticia : arrayNoticias) {
			assertTrue(collector.setNoticia(noticia.getNoticia()));
		}

		Logger.getLogger(getClass().getName()).log(Level.INFO, " # NOTICIAS CREADAS!");
	}

	/**
	 * TEST PARA CREAR NUEVAS PROXIMOS ESTRENOS:
	 * 
	 * @throws RemoteException
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testCrearProximosEstrenos() throws RemoteException {

		arrayProximosEstrenos = new ArrayList<ProximoEstreno>();
		arrayProximosEstrenos.add(new ProximoEstreno("Los vengadores: Infinity WAR"));
		arrayProximosEstrenos.add(new ProximoEstreno("Los vengadores: Infitiny WAR II"));
		arrayProximosEstrenos.add(new ProximoEstreno("El Padrino I"));
		arrayProximosEstrenos.add(new ProximoEstreno("El Padrino II"));
		arrayProximosEstrenos.add(new ProximoEstreno("El Padrino III"));

		for (ProximoEstreno proximoEstreno : arrayProximosEstrenos) {
			assertTrue(collector.setProximoEstreno(proximoEstreno.getNombrePelicula()));
		}

		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ESTRENOS CREADOS!");
	}

	// Registro de usuarios en paralelo:
	private int invocacion = 0;

	@PerfTest(invocations = 20, threads = 1, timer = RandomTimer.class, timerParams = { 300, 800 }) // LENTO PERO
	@Test // SEGURO
	public void registroUsuariosTest() {
		try {
			collector.registerUser("DGP1" + invocacion, "123451" + invocacion, "DGP@opendeusto.es" + invocacion,
					"David" + invocacion, "García Pérez" + invocacion, new Date(), "La Paz" + invocacion,
					"Bilbao" + invocacion, "España" + invocacion);
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # REGISTRO USUARIO: DGP" + invocacion);
			invocacion++;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	// Consultas paralelas de inicio de sesión incorrectas:
	@Test
	@PerfTest(invocations = 20, threads = 1, timer = RandomTimer.class, timerParams = { 300, 800 })
	public void inicioSesionIncorrectoTest() throws RemoteException {
		try {
			assertFalse(collector.login("DGP04", "1234566"));
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # INICIO SESION TEST INCORRECTO");
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}
	
	@PerfTest(threads = 100, duration = 60000, rampUp = 1000, warmUp = 9000)
	public void inicioSesionIncorrecto2Test() throws RemoteException {
		try {
			assertFalse(collector.login("amUser4", "1235231"));
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # INICIO SESION TEST INCORRECTO (duration = 60000)");
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	@PerfTest(duration = 400, invocations = 100, threads = 10)
	@Required(max = 50, average = 20)
	public void inicioSesionIncorrecto3Test() throws RemoteException {
		try {
			assertFalse(collector.login("amUser4", "13245234"));
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # INICIO SESION TEST INCORRECTO (max = 50, average = 20)");
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	@Required(throughput = 20, average = 50, median = 45, max = 2000, totalTime = 5000, percentile90 = 3000, percentile95 = 5000, percentile99 = 10000)
	public void inicioSesionIncorrecto4Test() throws RemoteException {
		try {
			assertFalse(collector.login("amUser4", "52345234"));
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # INICIO SESION TEST INCORRECTO (throughput = 20...)");
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	private int clientInvocados = 0;

	// Lanzamiento del proceso Client:
	@Test
	@PerfTest(invocations = 100, threads = 1, timer = RandomTimer.class, timerParams = { 30, 80 })
	public void testRMIApp() throws RemoteException, ParseException {
		collector.registerUser("DGP23" + invocacion, "12345" + invocacion, "DGP@opendeusto.es" + invocacion,
				"David" + invocacion, "García Pérez" + invocacion, new Date(), "La Paz" + invocacion,
				"Bilbao" + invocacion, "España" + invocacion);
		Client client = new Client(false);
		client.start(arg);
		client.collector
				.broadcastMessage(new Object[] { "FECHA: " + new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-9")
						+ "[CLIENT " + clientInvocados + "]: Hola soy el cliente nº " + clientInvocados });
		client.collector.conectarUsuario();
		client.collector.desconectarUsuario("DGP");
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # testRMIApp CLIENT [" + clientInvocados + "]");
		assertTrue(true);
		clientInvocados++;
		if (clientInvocados >= 100) {
			assertEquals(String.valueOf(collector.countRemoteObservers()), "100");
			client.remoteClient.end();
			obtenerTodo();
		}
	}

	public void obtenerTodo() throws RemoteException {

		arrayClientes = new ArrayList<Cliente>();
		arrayUsuarios = new ArrayList<Usuario>();
		arrayMensajes = new ArrayList<Mensaje>();
		arrayOpiniones = new ArrayList<Opinion>();
		arrayPeliculas = new ArrayList<Pelicula>();
		arrayNoticias = new ArrayList<Noticia>();
		arrayProximosEstrenos = new ArrayList<ProximoEstreno>();
		arrayInventarios = new ArrayList<Inventario>();
		arrayAlquileres = new ArrayList<Alquiler>();
		arrayAmigos = new ArrayList<Amigo>();
		arrayPeliculasAlquiladas = new ArrayList<Alquiler>();
		arrayPeliculasFavoritas = new ArrayList<PeliculaFavorita>();
		arrayPeliculasNuevas = new ArrayList<Pelicula>();
		arrayPeliculasPendientes = new ArrayList<PeliculaPendiente>();
		arrayPeliculasVistas = new ArrayList<PeliculaVista>();
		arrayRecomendaciones = new ArrayList<Recomendacion>();

		arrayAlquileres = collector.obtenerAlquileres(arrayAlquileres);
		arrayAmigos = collector.obtenerAmigos(arrayAmigos);
		arrayClientes = collector.obtenerClientes(arrayClientes);
		arrayInventarios = collector.obtenerInventarios(arrayInventarios);
		arrayMensajes = collector.obtenerMensajes(arrayMensajes);
		arrayNoticias = collector.obtenerNoticias(arrayNoticias);
		arrayOpiniones = collector.obtenerOpiniones(arrayOpiniones);
		arrayPeliculas = collector.obtenerPeliculas(arrayPeliculas);
		arrayPeliculasAlquiladas = collector.obtenerPeliculasAlquiladas(arrayPeliculasAlquiladas);
		arrayPeliculasFavoritas = collector.obtenerPeliculasFavoritas(arrayPeliculasFavoritas);
		arrayPeliculasNuevas = collector.obtenerPeliculasNuevas(arrayPeliculasNuevas);
		arrayPeliculasPendientes = collector.obtenerPeliculasPendientes(arrayPeliculasPendientes);
		arrayPeliculasVistas = collector.obtenerPeliculasVistas(arrayPeliculasVistas);
		arrayRecomendaciones = collector.obtenerRecomendaciones(arrayRecomendaciones);
		arrayUsuarios = collector.obtenerUsuarios(arrayUsuarios);
		arrayProximosEstrenos = collector.obtenerProximosEstrenos(arrayProximosEstrenos);
	}

	public byte[] extractBytes(URL url) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try (InputStream inputStream = url.openStream()) {
			int n = 0;
			byte[] buffer = new byte[1024];
			while (-1 != (n = inputStream.read(buffer))) {
				output.write(buffer, 0, n);
			}
		}

		return output.toByteArray();
	}

	private String obtenerDescripcion(String all) {
		String descripcion = null;
		int posinicial = all.indexOf("Error: please try again.") + 25;
		int posFinal = all.indexOf("Director") - 1;
		descripcion = all.substring(posinicial, posFinal);
		return descripcion;
	}

	private String obtenerGenero(String all) {
		String genero = "Action";
		try {
			int posInicial = all.indexOf("");
			int posFinal = all.indexOf(",");
			genero = all.substring(posInicial, posFinal);
		} catch (Exception e) {

		}
		return genero;
	}

	private String obtenerDuracion(String all) {
		String duracion = "122";
		try {
			int posFinal = all.indexOf(" ");
			duracion = all.substring(0, posFinal);
		} catch (

		Exception e) {

		}
		return duracion;
	}

	private String obtenerAnyo(String all) {
		String anyo = "2005";
		try {
			int posInicial = all.indexOf("(") + 1;
			int posFinal = all.lastIndexOf(")");
			anyo = all.substring(posInicial, posFinal);
		} catch (

		Exception e) {

		}
		return anyo;
	}

	private String obtenerSrcUrl(String all) {
		String url = null;
		int posInicial = all.indexOf("class=\"loadlate\"") + 27;
		int posFinal = all.indexOf("data-tconst=") - 2;
		url = all.substring(posInicial, posFinal);
		return url;
	}

	/**
	 * Con esta método compruebo el Status code de la respuesta que recibo al hacer
	 * la petición EJM: 200 OK 300 Multiple Choices 301 Moved Permanently 305 Use
	 * Proxy 400 Bad Request 403 Forbidden 404 Not Found 500 Internal Server Error
	 * 502 Bad Gateway 503 Service Unavailable
	 * 
	 * @param url
	 * @return Status Code
	 */
	public int getStatusConnectionCode(String url) {

		Response response = null;

		try {
			response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
		} catch (IOException ex) {
			System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
		}
		return response.statusCode();
	}

	/**
	 * Con este método devuelvo un objeto de la clase Document con el contenido del
	 * HTML de la web que me permitirá parsearlo con los métodos de la librelia
	 * JSoup
	 * 
	 * @param url
	 * @return Documento con el HTML
	 */
	public Document getHtmlDocument(String url) {

		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
		} catch (IOException ex) {
			System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
		}
		return doc;
	}

	@AfterClass
	static public void tearDown() {

		try {
			assertEquals(String.valueOf(collector.countRemoteObservers()), "99");
			collector.deleteRemoteObservers();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			rmiServerThread.join();
			rmiRegistryThread.join();
		} catch (InterruptedException ie) {
			Logger.getLogger(RMICollectorTest.class.getName()).log(Level.WARNING, ie.getMessage());
		}
	}
}
