package videoclub.observer.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteObserver extends Remote {
	public void update(Object arg) throws RemoteException;
}