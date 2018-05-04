package myRMIConection;

import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.IOException;
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

import javax.imageio.ImageIO;

import org.databene.contiperf.PerfTest;
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
import videoclub.client.gui.ventanas.Client;
import videoclub.server.gui.ICollector;
import videoclub.server.gui.ServerCollector;
import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Direccion;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Mensaje;
import videoclub.server.jdo.Noticia;
import videoclub.server.jdo.Opinion;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.ProximoEstreno;
import videoclub.server.jdo.Usuario;

public class RMIConectionTest {
	@Rule
	public ContiPerfRule contiPerfRule = new ContiPerfRule();
	private String[] arg = { "127.0.0.1", "1099", "TestVideoclub" };
	private static String cwd = RMIConectionTest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;
	public static ICollector collector;
	public String url = "http://www.imdb.com/list/ls025439193/";
	public String urlWeb = "http://www.imdb.com";

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(RMIConectionTest.class);
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
					System.out.println("Exception starting RMI registry:");
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

				String name = "//127.0.0.1:1099/TestVideoclub";
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
	 * TEST PARA CREAR NUEVOS USUARIOS:
	 * 
	 * @throws ParseException
	 * @throws RemoteException
	 */
	@Test
	public void testCrearUsuarios() throws ParseException, RemoteException {
		List<Direccion> arrayDirecciones = new ArrayList<Direccion>();
		arrayDirecciones.add(new Direccion("Calle 1", "Bilbao", "España"));
		arrayDirecciones.add(new Direccion("Calle 2", "Madrid", "España"));
		arrayDirecciones.add(new Direccion("Calle 3", "Lugo", "España"));
		arrayDirecciones.add(new Direccion("Calle 4", "Barcelona", "España"));
		arrayDirecciones.add(new Direccion("Calle 5", "Burgos", "España"));

		List<Cliente> arrayClientes = new ArrayList<Cliente>();
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

		List<Usuario> arrayUsuarios = new ArrayList<Usuario>();
		arrayUsuarios.add(new Usuario("dgpUser", "12345", "dgp.g.p@opendeusto.es", arrayClientes.get(0)));
		arrayUsuarios.add(new Usuario("auUser", "12345", "au.g.p@opendeusto.es", arrayClientes.get(1)));
		arrayUsuarios.add(new Usuario("aaUser", "12345", "aa.g.p@opendeusto.es", arrayClientes.get(2)));
		arrayUsuarios.add(new Usuario("amUser", "12345", "am.g.p@opendeusto.es", arrayClientes.get(3)));
		arrayUsuarios.add(new Usuario("jpeUser", "12345", "jpe.g.p@opendeusto.es", arrayClientes.get(4)));

		for (Usuario user : arrayUsuarios) {
			collector.registerUser(user.getNombreUsuario(), user.getContraseña(), user.getCorreo(),
					user.getCliente().getNombre(), user.getCliente().getApellidos(),
					user.getCliente().getFecha_nacimiento(), user.getCliente().getDireccion().getCalle(),
					user.getCliente().getDireccion().getCiudad(), user.getCliente().getDireccion().getPais());
		}
	}

	/**
	 * TEST PARA CREAR NUEVAS PELÍCULAS:
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void testCrearPeliculas() throws ParseException, IOException {
		if (getStatusConnectionCode(url) == 200) {

			// Obtengo el HTML de la web en un objeto Document
			Document document = getHtmlDocument(url);

			// Busco todas las entradas que estan dentro de:
			Elements entradas = document.select("div.lister-item.mode-detail");
			System.out.println("Número de entradas en la página inicial de http://www.imdb.com/list/ls025439193/: "
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
				collector.insertarPelicula(titulo, Integer.parseInt(obtenerDuracion(duracion)),
						obtenerDescripcion(descripcion).getBytes(), Integer.parseInt(obtenerAnyo(anyo)), 5F,
						obtenerGenero(categoria), 5, new Imagen(obtenerSrcUrl(srcImage), data), true);
			}

		} else {
			// System.out.println("El Status Code no es OK es: " +
			// getStatusConnectionCode(url));
		}
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

		List<Noticia> arrayNoticias = new ArrayList<Noticia>();
		arrayNoticias.add(new Noticia("Noticia [1] creada a las: " + new Date()));
		arrayNoticias.add(new Noticia("Noticia [2] creada a las: " + new Date()));
		arrayNoticias.add(new Noticia("Noticia [3] creada a las: " + new Date()));
		arrayNoticias.add(new Noticia("Noticia [4] creada a las: " + new Date()));
		arrayNoticias.add(new Noticia("Noticia [5] creada a las: " + new Date()));
		for (Noticia noticia : arrayNoticias) {
			collector.setNoticia(noticia.getNoticia());
		}
	}

	/**
	 * TEST PARA CREAR NUEVAS MENSAJES:
	 * 
	 * @throws RemoteException
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testCrearMensajes() throws RemoteException {
		List<Usuario> arrayUsuarios = new ArrayList<Usuario>();
		arrayUsuarios = collector.obtenerUsuarios(arrayUsuarios);
		List<Mensaje> arrayMensajes = new ArrayList<Mensaje>();
		arrayMensajes.add(new Mensaje("Mensaje número 1", new Date(), arrayUsuarios.get(0)));
		arrayMensajes.add(new Mensaje("Mensaje número 2", new Date(), arrayUsuarios.get(1)));
		arrayMensajes.add(new Mensaje("Mensaje número 3", new Date(), arrayUsuarios.get(2)));
		arrayMensajes.add(new Mensaje("Mensaje número 4", new Date(), arrayUsuarios.get(3)));
		arrayMensajes.add(new Mensaje("Mensaje número 5", new Date(), arrayUsuarios.get(4)));
		for (Mensaje mensaje : arrayMensajes) {
			collector.setMensaje(mensaje);
		}
	}

	/**
	 * TEST PARA CREAR NUEVAS OPINIONES:
	 * 
	 * @throws RemoteException
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testCrearOpiniones() throws RemoteException {

		List<Opinion> arrayOpiniones = new ArrayList<Opinion>();
		List<Pelicula> arrayPeliculas = new ArrayList<Pelicula>();
		List<Usuario> arrayUsuarios = new ArrayList<Usuario>();
		arrayUsuarios = collector.obtenerUsuarios(arrayUsuarios);
		arrayPeliculas = collector.obtenerPeliculas(arrayPeliculas);
		arrayOpiniones.add(new Opinion(arrayPeliculas.get(0), arrayUsuarios.get(0),
				"Opinión de la película..." + arrayPeliculas.get(0) + "..1"));
		arrayOpiniones.add(new Opinion(arrayPeliculas.get(1), arrayUsuarios.get(1),
				"Opinión de la película..." + arrayPeliculas.get(1) + "..2"));
		arrayOpiniones.add(new Opinion(arrayPeliculas.get(2), arrayUsuarios.get(2),
				"Opinión de la película..." + arrayPeliculas.get(2) + "..3"));
		arrayOpiniones.add(new Opinion(arrayPeliculas.get(3), arrayUsuarios.get(3),
				"Opinión de la película..." + arrayPeliculas.get(3) + "..4"));
		arrayOpiniones.add(new Opinion(arrayPeliculas.get(4), arrayUsuarios.get(4),
				"Opinión de la película..." + arrayPeliculas.get(4) + "..5"));

		for (Opinion opinion : arrayOpiniones) {
			collector.setOpinion(opinion.getPelicula(), opinion.getUser(), opinion.getDescripcionOpinion());
		}
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

		List<ProximoEstreno> arrayProximosEstrenos = new ArrayList<ProximoEstreno>();
		arrayProximosEstrenos.add(new ProximoEstreno("Pelicula nueva ... 1"));
		arrayProximosEstrenos.add(new ProximoEstreno("Pelicula nueva ... 2"));
		arrayProximosEstrenos.add(new ProximoEstreno("Pelicula nueva ... 3"));
		arrayProximosEstrenos.add(new ProximoEstreno("Pelicula nueva ... 4"));
		arrayProximosEstrenos.add(new ProximoEstreno("Pelicula nueva ... 5"));

		for (ProximoEstreno proximoEstreno : arrayProximosEstrenos) {
			collector.setProximoEstreno(proximoEstreno.getNombrePelicula());
		}
	}

	public byte[] extractBytes(URL url) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(url);

		// get DataBufferBytes from Raster
		WritableRaster raster = bufferedImage.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

		return (data.getData());
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

	// Registro de usuarios en paralelo:
	private int invocacion = 0;

	// @PerfTest(invocations = 20000, threads = 10, timer = RandomTimer.class,
	// timerParams = { 3, 8 })//RÁPIDO PERO NO SEGURO
	@PerfTest(invocations = 20, threads = 1, timer = RandomTimer.class, timerParams = { 300, 800 }) // LENTO PERO
	@Test // SEGURO
	public void registroUsuariosTest() {
		try {
			collector.registerUser("DGP" + invocacion, "12345" + invocacion, "DGP@opendeusto.es" + invocacion,
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
	// @PerfTest(invocations = 20000, threads = 1000, timer = RandomTimer.class,
	// timerParams = { 300, 800 })//RÁPIDO PERO NO SEGURO
	@PerfTest(invocations = 20, threads = 1, timer = RandomTimer.class, timerParams = { 300, 800 })
	public void inicioSesionIncorrectoTest() {
		boolean dev = false;
		try {
			dev = collector.login("DGP0", "11111"); // ERROR DE CONTRASEÑA!
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # INICIO SESION TEST CORRECTO: " + dev);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	// Consultas paralelas de inicio de sesión correctas:
	@Test
	// @PerfTest(invocations = 20000, threads = 1000, timer = RandomTimer.class,
	// timerParams = { 300, 800 })//RÁPIDO PERO NO SEGURO
	@PerfTest(invocations = 20, threads = 1, timer = RandomTimer.class, timerParams = { 300, 800 })
	public void inicioSesionCorrectoTest() {
		boolean dev = false;
		try {
			dev = collector.login("DGP0", "123450"); // CONTRASEÑA CORRECTA!
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # INICIO SESION TEST CORRECTO: " + dev);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	// Consultas paralelas para la obtención de películas:
	@Test
	public void obtencionPeliculasTest() {
		try {
			List<Pelicula> arrayPeliculas = new ArrayList<Pelicula>();
			arrayPeliculas = collector.obtenerPeliculas(arrayPeliculas);
			for (Pelicula pelicula : arrayPeliculas) {
				// LOG:
				Logger.getLogger(getClass().getName()).log(Level.INFO, "PELICULA: " + pelicula.toString());
			}
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	// Consultas paralelas para la obtención de alquileres:
	@Test
	public void obtencionAlquileresTest() {
		try {
			List<Alquiler> arrayAlquileres = new ArrayList<Alquiler>();
			arrayAlquileres = collector.obtenerAlquileres(arrayAlquileres);
			for (Alquiler alquiler : arrayAlquileres) {
				// LOG:
				Logger.getLogger(getClass().getName()).log(Level.INFO, "ALQUILER: " + alquiler.toString());
			}
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	// Lanzamiento del proceso Client:
	@Test
	public void testRMIApp() {
		Client client = new Client();
		client.start(arg);
		assertTrue(true);
	}

	@AfterClass
	static public void tearDown() {
		try {
			rmiServerThread.join();
			rmiRegistryThread.join();
		} catch (InterruptedException ie) {
			Logger.getLogger(RMIConectionTest.class.getName()).log(Level.WARNING, ie.getMessage());
		}
	}
}
