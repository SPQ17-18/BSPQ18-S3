package myTests;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.databene.contiperf.timer.RandomTimer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import videoclub.client.gui.ventanas.Client;
import videoclub.server.gui.ICollector;
import videoclub.server.gui.ServerCollector;
import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Pelicula;

public class RMITest {
	@Rule
	public ContiPerfRule contiPerfRule = new ContiPerfRule();
	private String[] arg = { "127.0.0.1", "1099", "TestVideoclub" };
	private static String cwd = RMITest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;
	public static ICollector collector;

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(RMITest.class);
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
					collector = new ServerCollector(true);
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

	// Registro de usuarios en paralelo:
	private int invocacion = 0;

	// @PerfTest(invocations = 20000, threads = 10, timer = RandomTimer.class,
	// timerParams = { 3, 8 })//RÁPIDO PERO NO SEGURO
	@PerfTest(invocations = 200, threads = 1, timer = RandomTimer.class, timerParams = { 3, 8 }) // LENTO PERO
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
	@PerfTest(invocations = 200, threads = 1, timer = RandomTimer.class, timerParams = { 30, 80 })
	public void inicioSesionIncorrectoTest() {
		boolean dev = false;
		try {
			dev = collector.login("DGP", "11111"); // ERROR DE CONTRASEÑA!
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # INICIO SESION TEST CORRECTO: " + dev);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	// Consultas paralelas de inicio de sesión correctas:
	@Test
	// @PerfTest(invocations = 20000, threads = 1000, timer = RandomTimer.class,
	// timerParams = { 300, 800 })//RÁPIDO PERO NO SEGURO
	@PerfTest(invocations = 200, threads = 1, timer = RandomTimer.class, timerParams = { 30, 80 })
	public void inicioSesionCorrectoTest() {
		boolean dev = false;
		try {
			dev = collector.login("DGP", "12345"); // CONTRASEÑA CORRECTA!
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # INICIO SESION TEST CORRECTO: " + dev);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	// Consultas paralelas para la obtención de películas:
	@Test
	// @PerfTest(invocations = 20000, threads = 1000, timer = RandomTimer.class,
	// timerParams = { 300, 800 })//RÁPIDO PERO NO SEGURO
	@PerfTest(invocations = 200, threads = 1, timer = RandomTimer.class, timerParams = { 30, 80 })
	public void obtencionPeliculasTest() {
		try {
			List<Pelicula> arrayPeliculas = new ArrayList<Pelicula>();
			arrayPeliculas = collector.obtenerPeliculas(arrayPeliculas);
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # OBTENCIÓN PELICULAS TEST: " + arrayPeliculas);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	// Consultas paralelas para la obtención de alquileres:
	@Test
	// @PerfTest(invocations = 20000, threads = 1000, timer = RandomTimer.class,
	// timerParams = { 300, 800 })//RÁPIDO PERO NO SEGURO
	@PerfTest(invocations = 200, threads = 1, timer = RandomTimer.class, timerParams = { 30, 80 })
	public void obtencionAlquileresTest() {
		try {
			List<Alquiler> arrayAlquileres = new ArrayList<Alquiler>();
			arrayAlquileres = collector.obtenerAlquileres(arrayAlquileres);
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # OBTENCIÓN ALQUILERES TEST: " + arrayAlquileres);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	// Lanzamiento del proceso Client:
	@Test
	@PerfTest(invocations = 10, threads = 1, timer = RandomTimer.class, timerParams = { 30, 80 })
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
			Logger.getLogger(RMITest.class.getName()).log(Level.WARNING, ie.getMessage());
		}
	}
}
