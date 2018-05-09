package videoclub.observer.RMI;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoteObservable {

	/**
	 * List for storing remote observers
	 */
	private List<IRemoteObserver> remoteObservers;

	public RemoteObservable() {
		this.remoteObservers = new ArrayList<IRemoteObserver>();
	}

	public synchronized void addRemoteObserver(IRemoteObserver observer) {
		this.remoteObservers.add(observer);
		Logger.getLogger(getClass().getName()).log(Level.INFO, observer.toString());
	}

	public synchronized void deleteRemoteObserver(IRemoteObserver observer) {
		this.remoteObservers.remove(observer);
	}

	public synchronized void deleteRemoteObservers() {
		this.remoteObservers.clear();
	}

	public synchronized int countRemoteObservers() {
		return this.remoteObservers.size();
	}

	public synchronized void notifyRemoteObserversChatMessages(Object arg) throws RemoteException {
		for (IRemoteObserver observer : remoteObservers) {
			observer.updateChatMessages(arg);
		}
	}

	public synchronized void notifyRemoteObserversUsuarioDesconectado() throws RemoteException {
		for (IRemoteObserver observer : remoteObservers) {
			observer.updateUsuarioDesconecatdo();
		}
	}

	public synchronized void notifyRemoteObserversUsuarioConectado() throws RemoteException {
		for (IRemoteObserver observer : remoteObservers) {
			observer.updateUsuarioDesconecatdo();
		}
	}

	/*
	 * INSTRUCTIONS: - The remote server will keep a reference to a RemoteObservable
	 * object (this class) to which will delegate every subscription and call when
	 * updates are needed. The RemoteObservable object will not inherit any remote
	 * interface because it's not a remote object.
	 */

}