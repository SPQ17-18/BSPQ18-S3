package videoclub.server.main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import videoclub.server.collector.ICollector;
import videoclub.server.collector.ServerCollector;

public class Server {

	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;

	public static void main(String[] args) {
		// Launch the RMI registry
		class RMIRegistryRunnable implements Runnable {

			public void run() {
				try {
					java.rmi.registry.LocateRegistry.createRegistry(1099);
					Logger.getLogger(getClass().getName()).log(Level.INFO, "RMI registry ready.");
				} catch (Exception e) {
					Logger.getLogger(getClass().getName()).log(Level.INFO, "Exception starting RMI registry:");
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

				try {
					ICollector iCollector = new ServerCollector(false);
					Naming.rebind(name, iCollector);

				} catch (RemoteException re) {
					Logger.getLogger(getClass().getName()).log(Level.INFO,
							" # Server RemoteException: " + re.getMessage());
					System.exit(-1);
				} catch (MalformedURLException murle) {
					Logger.getLogger(getClass().getName()).log(Level.INFO,
							" # Server MalformedURLException: " + murle.getMessage());
					System.exit(-1);
				}
			}
		}

		rmiServerThread = new Thread(new RMIServerRunnable());
		rmiServerThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			Logger.getLogger(Server.class.getClass().getName()).log(Level.INFO,
					" # InterruptedException: " + ie.getMessage());
		}
	}
}
