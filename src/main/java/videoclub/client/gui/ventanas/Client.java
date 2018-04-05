package videoclub.client.gui.ventanas;

import java.awt.EventQueue;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;

import videoclub.server.gui.ICollector;

public class Client{
	private ICollector collector;
	private ClientRemoteObserver remoteDonor;

	public Client() {
		// EDT para ajustar el tema propio al JFrame creado para el cliente:
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel((LookAndFeel) new SubstanceRavenLookAndFeel());
					ClientFrame frame = new ClientFrame(525, 325);
					frame.setVisible(true);
					frame.cargarPanelIniciarSesion();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void start(String[] args) {
		this.connect2Collector(args);
		try {
			this.remoteDonor = new ClientRemoteObserver(this.collector, this);
		} catch (RemoteException e) {
			System.err.println(" # Error creating Remote Donor: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void connect2Collector(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			String URL = "//" + args[0] + ":" + args[1] + "/" + args[2];
			this.collector = (ICollector) Naming.lookup(URL);
		} catch (Exception e) {
			System.err.println(" *# Error connecting to Donation Collector: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			Client donor = new Client();
			donor.start(args);
		} catch (Exception e) {
			System.err.println(" # Error starting Donor: " + e.getMessage());
			e.printStackTrace();
		}
	}
}