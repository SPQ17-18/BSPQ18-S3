package videoclub.client.observer;

import java.rmi.RemoteException;

import videoclub.client.main.Client;
import videoclub.observer.RMI.RemoteObserver;
import videoclub.server.collector.ICollector;

public class ClientRemoteObserver extends RemoteObserver {

	private static final long serialVersionUID = 1L;
	private ICollector collector;
	private Client client;

	public ClientRemoteObserver(ICollector collector, Client client) throws RemoteException {
		super();
		this.collector = collector;
		this.client = client;
		this.collector.addRemoteObserver(this);
	}

	public void end() throws RemoteException {
		this.collector.deleteRemoteObserver(this);
	}

	@Override
	public void updateChatMessages(Object arg) throws RemoteException {
		// TODO Auto-generated method stub
		// Recibimos mensaje del servidor y mostramos el mensaje (de otro
		// usuario) mediante el Client:
		client.notifyMessage((Object[]) arg);
	}

	@Override
	public void updateUsuarioDesconecatdo() throws RemoteException {
		// TODO Auto-generated method stub
		client.notifyUsuarioDesconectado();
	}

	@Override
	public void updateUsuarioConectado() throws RemoteException {
		// TODO Auto-generated method stub
		client.notifyUsuarioConectado();
	}
}