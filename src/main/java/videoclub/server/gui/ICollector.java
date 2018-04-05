package videoclub.server.gui;

import java.rmi.RemoteException;

import videoclub.observer.RMI.IRemoteObservable;


public interface ICollector extends IRemoteObservable {
	void getDonation(int donation) throws RemoteException;
}