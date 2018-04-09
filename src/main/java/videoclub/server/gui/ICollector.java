package videoclub.server.gui;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import videoclub.observer.RMI.IRemoteObservable;
import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Inventario;
import videoclub.server.jdo.Mensaje;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.Usuario;

public interface ICollector extends IRemoteObservable {
	List<Alquiler> obtenerAlquileres (List<Alquiler> arrayAlquileres) throws RemoteException;
	List<Mensaje> obtenerMensajes (List<Mensaje> arrayMensajes) throws RemoteException;
	boolean setMensaje(Mensaje mensaje) throws RemoteException;
	List<Cliente> obtenerClientes(List<Cliente> arrayClientes) throws RemoteException;
	List<Inventario> obtenerInventarios(List<Inventario> arrayInventarios) throws RemoteException;
	boolean alquilarPelicula(Alquiler alquiler) throws RemoteException;
	Cliente getCliente() throws RemoteException;
	Usuario getUsuario() throws RemoteException;
	List<Pelicula> obtenerPeliculas(List<Pelicula> arrayPeliculas) throws RemoteException;
	boolean insertarPelicula(String nombre, int duracion, byte[] descripcion, int anyo, float precio, String categoria,int cantidad, Imagen imagen) throws RemoteException;
	boolean login(String nombreUsuario, String contraseña) throws RemoteException;
	boolean registerUser(String nombreUsuario, String contraseña, String correo, String nombre, String apellidos, Date fechaNacimiento, String calle, String ciudad, String pais) throws RemoteException;
}