package videoclub.server.gui;

import java.rmi.RemoteException;

import java.util.Date;
import java.util.List;

import videoclub.observer.RMI.IRemoteObservable;
import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Amigo;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Inventario;
import videoclub.server.jdo.Mensaje;
import videoclub.server.jdo.Noticia;
import videoclub.server.jdo.Opinion;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.PeliculaFavorita;
import videoclub.server.jdo.PeliculaPendiente;
import videoclub.server.jdo.PeliculaVista;
import videoclub.server.jdo.ProximoEstreno;
import videoclub.server.jdo.Recomendacion;
import videoclub.server.jdo.Usuario;

public interface ICollector extends IRemoteObservable {
	List<ProximoEstreno> obtenerProximosEstrenos(List<ProximoEstreno> arrayProximosEstrenos) throws RemoteException;
	boolean setProximoEstreno(String pelicula) throws RemoteException;
	List<Noticia> obtenerNoticias(List<Noticia> arrayNoticias) throws RemoteException;
	boolean setNoticia(String noticia) throws RemoteException;
	List<Opinion> obtenerOpiniones(List<Opinion> arrayOpiniones) throws RemoteException;
	boolean setOpinion(Pelicula pelicula, Usuario user, String opinion) throws RemoteException;
	List<PeliculaVista> obtenerPeliculasVistas(List<PeliculaVista> arrayPeliculasVistas) throws RemoteException;
	List<PeliculaPendiente> obtenerPeliculasPendientes(List<PeliculaPendiente> arrayPeliculasPendientes) throws RemoteException;
	boolean setPeliculaPendiente(Pelicula pelicula, Cliente cliente) throws RemoteException;
	List<PeliculaFavorita> obtenerPeliculasFavoritas(List<PeliculaFavorita> arrayPeliculasFavoritas) throws RemoteException;
	boolean setPeliculaFavorita(Pelicula pelicula, Cliente cliente) throws RemoteException;
	void conectarUsuario() throws RemoteException;
	void desconectarUsuario(String usuario) throws RemoteException;
	List<Alquiler> obtenerPeliculasAlquiladas(List<Alquiler> arrayPeliculasAlquiladas) throws RemoteException;
	boolean setPeliculaVista(Pelicula pelicula, Cliente cliente) throws RemoteException;
	boolean eliminarCliente(String nombre, String apellidos, String fechaNacimiento) throws RemoteException;
	void broadcastMessage(Object[] mensaje)  throws RemoteException;
	boolean setRecomendacion(String usuario, String amigo, Pelicula pelicula)  throws RemoteException;
	List<Recomendacion> obtenerRecomendaciones(List<Recomendacion> arrayRecomendaciones)  throws RemoteException;
	List<Amigo> obtenerAmigos(List<Amigo> arrayAmigos) throws RemoteException;
	boolean setAmigo(String usuario, String amigo) throws RemoteException;
	List<Usuario> obtenerUsuariosConectados () throws RemoteException;
	List<Usuario> obtenerUsuarios (List<Usuario> arrayUsuarios) throws RemoteException;
	List<Alquiler> obtenerAlquileres (List<Alquiler> arrayAlquileres) throws RemoteException;
	List<Mensaje> obtenerMensajes (List<Mensaje> arrayMensajes) throws RemoteException;
	boolean setMensaje(Mensaje mensaje) throws RemoteException;
	List<Cliente> obtenerClientes(List<Cliente> arrayClientes) throws RemoteException;
	List<Inventario> obtenerInventarios(List<Inventario> arrayInventarios) throws RemoteException;
	boolean alquilarPelicula(Alquiler alquiler) throws RemoteException;
	Cliente getCliente() throws RemoteException;
	Usuario getUsuario() throws RemoteException;
	List<Pelicula> obtenerPeliculas(List<Pelicula> arrayPeliculas) throws RemoteException;
	List<Pelicula> obtenerPeliculasNuevas(List<Pelicula> arrayPeliculas) throws RemoteException;
	boolean insertarPelicula(String nombre, int duracion, byte[] descripcion, int anyo, float precio, String categoria,int cantidad, Imagen imagen, boolean novedad) throws RemoteException;
	boolean login(String nombreUsuario, String contraseña) throws RemoteException;
	boolean registerUser(String nombreUsuario, String contraseña, String correo, String nombre, String apellidos, Date fechaNacimiento, String calle, String ciudad, String pais) throws RemoteException;
}