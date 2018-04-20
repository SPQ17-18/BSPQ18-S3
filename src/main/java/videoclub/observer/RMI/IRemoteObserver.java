package videoclub.observer.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteObserver extends Remote {
	public void updateChatMessages(Object arg) throws RemoteException;
	public void updateUsuarioDesconecatdo() throws RemoteException;
	public void updateUsuarioConectado() throws RemoteException;
}