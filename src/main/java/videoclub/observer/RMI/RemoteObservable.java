package videoclub.observer.RMI;

import java.util.ArrayList;
import java.util.List;

public class RemoteObservable {

	/**
	 * List for storing remote observers
	 */
	private List<IRemoteObserver> remoteObservers;

	public RemoteObservable() {
		this.remoteObservers = new ArrayList<IRemoteObserver>();
	}

	public synchronized void addRemoteObserver(IRemoteObserver observer) {
		if (observer != null) {
			this.remoteObservers.add(observer);
			System.out.println(observer.toString());
		}
	}

	public synchronized void deleteRemoteObserver(IRemoteObserver observer) {
		if (observer != null) {
			this.remoteObservers.remove(observer);
		}
	}

	public synchronized void deleteRemoteObservers() {
		this.remoteObservers.clear();
	}

	public synchronized int countRemoteObservers() {
		return this.remoteObservers.size();
	}

	public synchronized void notifyRemoteObservers(Object arg) {
		for (IRemoteObserver observer : remoteObservers) {
			try {
				observer.update(arg);
			} catch (Exception ex) {
				System.err.println(this.getClass().getName() + ".notifyRemoteObservers(): " + ex);
				ex.printStackTrace();
			}
		}
	}

	/*
	 * INSTRUCTIONS: - The remote server will keep a reference to a
	 * RemoteObservable object (this class) to which will delegate every
	 * subscription and call when updates are needed. The RemoteObservable
	 * object will not inherit any remote interface because it's not a remote
	 * object.
	 */

}