package videoclub.client.main;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;

import videoclub.client.gui.ventanas.ClientFrame;
import videoclub.client.observer.ClientRemoteObserver;
import videoclub.server.collector.ICollector;

public class Client {
	public ICollector collector;
	public ClientRemoteObserver remoteClient;
	public ClientFrame frame;

	public Client(boolean inicializarFrame) {
		if (inicializarFrame == true) {
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
	}

	public void start(String[] args) {
		this.connect2Collector(args);
		try {
			this.remoteClient = new ClientRemoteObserver(this.collector, this);
		} catch (RemoteException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING,
					" # Error creating Remote Donor: " + e.getMessage());
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
			Logger.getLogger(getClass().getName()).log(Level.WARNING,
					" *# Error connecting to Donation Collector: " + e.getMessage());
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
		try {
			if (this.frame.panelUsuario.pau.tablaAmigosCargada == true) {
				this.frame.panelUsuario.pau.mostrarAmigos();
			} else {
				this.frame.panelUsuario.pau.mostrarUsuarios();
			}
		} catch (Exception e) {
			Logger.getLogger(Client.class.getName()).log(Level.INFO,
					" # (notifyUsuarioDesconectado) recibida señal de un usuario que se acaba de desconectar");
			Logger.getLogger(Client.class.getName()).log(Level.WARNING,
					" # Error, frame.panelUsuario.pau (notifyUsuarioDesconectado) no INICIALIZADO!");
		}
	}

	public void notifyUsuarioConectado() {
		try {
			if (this.frame.panelUsuario.pau.tablaAmigosCargada == true) {
				this.frame.panelUsuario.pau.mostrarAmigos();
			} else {
				this.frame.panelUsuario.pau.mostrarUsuarios();
			}
		} catch (Exception e) {
			Logger.getLogger(Client.class.getName()).log(Level.INFO,
					" # (notifyUsuarioConectado) recibida señal de un usuario que se acaba de conectar");
			Logger.getLogger(Client.class.getName()).log(Level.WARNING,
					" # Error, frame.panelUsuario.pau (notifyUsuarioConectado) no INICIALIZADO!");
		}
	}

	public void notifyMessage(Object[] arg) {
		try {
			this.frame.panelUsuario.panelChat.addNewMessage(arg);
		} catch (Exception e) {
			Logger.getLogger(Client.class.getName()).log(Level.INFO,
					" # (notifyMessage) mensaje recibido desde el servidor: " + arg);
			Logger.getLogger(Client.class.getName()).log(Level.WARNING,
					" # Error, frame.panelUsuario.panelChat (notifyMessage) no INICIALIZADO!");
		}
	}

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			Client client = new Client(true);
			client.start(args);
		} catch (Exception e) {
			Logger.getLogger(Client.class.getName()).log(Level.WARNING, " # Error starting Client: " + e.getMessage());
			e.printStackTrace();
		}
	}
}