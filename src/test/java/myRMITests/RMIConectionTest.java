package myRMITests;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import videoclub.client.gui.ventanas.Client;
import videoclub.server.gui.ICollector;
import videoclub.server.gui.ServerCollector;

public class RMIConectionTest {
	@Rule
	public ContiPerfRule contiPerfRule = new ContiPerfRule();
	private String[] arg = { "127.0.0.1", "1099", "TestVideoclub" };
	private static String cwd = RMITest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;
	public static ICollector collector;

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
			Logger.getLogger(RMITest.class.getName()).log(Level.WARNING, ie.getMessage());
		}
	}
}
