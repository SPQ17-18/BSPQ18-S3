package videoclub.client.gui.ventanas;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;

import videoclub.server.gui.ICollector;

public class Client {
	private ICollector collector;
	@SuppressWarnings("unused")
	private ClientRemoteObserver remoteClient;
	public ClientFrame frame;

	public Client() {
		// EDT para ajustar el tema propio al JFrame creado para el cliente:
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel((LookAndFeel) new SubstanceRavenLookAndFeel());
					frame = new ClientFrame(525, 325, collector, Client.this);
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
			this.remoteClient = new ClientRemoteObserver(this.collector, this);
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

	public void setFrame(ClientFrame frame) {
		this.frame = frame;
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					collector.desconectarUsuario(frame.panelUsuario.usuarioActual.getNombreUsuario());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public void notifyUsuarioDesconectado() {
		if (this.frame.panelUsuario.pau.tablaAmigosCargada == true) {
			this.frame.panelUsuario.pau.mostrarAmigos();
		} else {
			this.frame.panelUsuario.pau.mostrarUsuarios();
		}
	}

	public void notifyUsuarioConectado() {
		if (this.frame.panelUsuario.pau.tablaAmigosCargada == true) {
			this.frame.panelUsuario.pau.mostrarAmigos();
		} else {
			this.frame.panelUsuario.pau.mostrarUsuarios();
		}
	}

	public void notifyMessage(Object[] arg) {
		this.frame.panelUsuario.panelChat.addNewMessage(arg);
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