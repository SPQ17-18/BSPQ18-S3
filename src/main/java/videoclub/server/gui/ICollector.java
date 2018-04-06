package videoclub.server.gui;

import java.rmi.RemoteException;
import java.util.Date;

import videoclub.observer.RMI.IRemoteObservable;

public interface ICollector extends IRemoteObservable {
	void getDonation(int donation) throws RemoteException;
	boolean insertarPelicula(String nombre, int duracion, String descripcion, int anyo, float precio, String categoria,int cantidad) throws RemoteException;
	boolean login(String nombreUsuario, String contraseña) throws RemoteException;
	boolean registerUser(String nombreUsuario, String contraseña, String correo, String nombre, String apellidos, Date fechaNacimiento, String calle, String ciudad, String pais) throws RemoteException;
}