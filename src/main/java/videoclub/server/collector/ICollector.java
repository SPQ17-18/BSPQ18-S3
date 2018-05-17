package videoclub.server.collector;

import java.rmi.RemoteException;

import java.util.Date;
import java.util.List;

import videoclub.observer.RMI.IRemoteObservable;
import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Amigo;
import videoclub.server.jdo.Categoria;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Inventario;
import videoclub.server.jdo.Mensaje;
import videoclub.server.jdo.Noticia;
import videoclub.server.jdo.NotificarAlquiler;
import videoclub.server.jdo.Opinion;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.PeliculaFavorita;
import videoclub.server.jdo.PeliculaPendiente;
import videoclub.server.jdo.PeliculaVista;
import videoclub.server.jdo.ProximoEstreno;
import videoclub.server.jdo.Recomendacion;
import videoclub.server.jdo.Usuario;

public interface ICollector extends IRemoteObservable {
	/**
	 * Método para obtener las categorias de la base de datos:
	 * @param arrayCategorias: parámetro tipo "List" asociado a la clase "Categoria" que pasamos al método.
	 * @return: el método devuelve una lista con todas las categorias.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<Categoria> obtenerCategorias(List<Categoria> arrayCategorias) throws RemoteException;
	/**
	 * Método para insertar una nueva notificación de alquiler en la base de datos:
	 * @param fechaNotificacion: parámetro necesario para la creación de una nueva notificación de alquiler.
	 * @return: el método devuelve true si todo ha ido bien.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean setNotificacionAlquiler(Date fechaNotificacion) throws RemoteException;
	/**
	 * Método para obtener las noficaciones de alquileres de la base de datos:
	 * @param arrayNotificacionesAlquileres: parámetro tipo "List" asociado a la clase "NofiticarAlquiler" que pasamos al método.
	 * @return: el método devuelve una lista con todas las notificaciones de alquileres.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<NotificarAlquiler> obtenerNotificacionesAlquileres(List<NotificarAlquiler> arrayNotificacionesAlquileres) throws RemoteException;
	/**
	 * Método para obtener los próximos estrenos de la base de datos:
	 * @param arrayProximosEstrenos: parámetro tipo "List" asociado a la clase "ProximoEstreno" que pasamos al método.
	 * @return: el método devuelve una lista con todas los próximos estrenos.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<ProximoEstreno> obtenerProximosEstrenos(List<ProximoEstreno> arrayProximosEstrenos) throws RemoteException;
	/**
	 * Método para insertar un nuevo proximo estreno en la base de datos:
	 * @param pelicula: parámetro necesario para la creación de un nuevo proximo estreno.
	 * @return: el método devuelve true si todo ha ido bien.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean setProximoEstreno(String pelicula) throws RemoteException;
	/**
	 * Método para obtener las noticias de la base de datos:
	 * @param arrayNoticias: parámetro tipo "List" asociado a la clase "Noticia" que pasamos al método.
	 * @return: el método devuelve una lista con todas las noticias.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<Noticia> obtenerNoticias(List<Noticia> arrayNoticias) throws RemoteException;
	/**
	 * Método para insertar una nueva noticia en la base de datos:
	 * @param noticia: parámetro necesario para la creación de una nueva noticia.
	 * @return: el método devuelve true si todo ha ido bien.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean setNoticia(String noticia) throws RemoteException;
	/**
	 * Método para obtener las opiniones de las películas de la base de datos:
	 * @param arrayOpiniones: parámetro tipo "List" asociado a la clase "Opinion" que pasamos al método.
	 * @return: el método devuelve una lista con todas las opiniones de las películas.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<Opinion> obtenerOpiniones(List<Opinion> arrayOpiniones) throws RemoteException;
	/**
	 * Método para insertar una nueva opinión en la base de datos:
	 * @param pelicula: parámetro necesario para la creación de una nueva opinión.
	 * @param user: parámetro necesario para la creación de una nueva opinión.
	 * @param opinion: parámetro necesario para la creación de una nueva opinión.
	 * @return: el método devuelve true si todo ha ido bien.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean setOpinion(Pelicula pelicula, Usuario user, String opinion) throws RemoteException;
	/**
	 * Método para obtener las películas vistas de la base de datos:
	 * @param arrayPeliculasVistas: parámetro tipo "List" asociado a la clase "PeliculaVista" que pasamos al método.
	 * @return: el método devuelve una lista con todas las películas vistas.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<PeliculaVista> obtenerPeliculasVistas(List<PeliculaVista> arrayPeliculasVistas) throws RemoteException;
	/**
	 * Método para obtener las películas pendientes de la base de datos:
	 * @param arrayPeliculasPendientes: parámetro tipo "List" asociado a la clase "PeliculaPendiente" que pasamos al método.
	 * @return: el método devuelve una lista con todas las películas pendientes.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<PeliculaPendiente> obtenerPeliculasPendientes(List<PeliculaPendiente> arrayPeliculasPendientes) throws RemoteException;
	/**
	 * Método para insertar una nueva película pendiente en la base de datos:
	 * @param pelicula: parámetro necesario para la creación de una nueva película pendiente.
	 * @param cliente: parámetro necesario para la creación de una nueva película pendiente.
	 * @return: el método devuelve true si todo ha ido bien.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean setPeliculaPendiente(Pelicula pelicula, Cliente cliente) throws RemoteException;
	/**
	 * Método para obtener las películas favoritas de la base de datos:
	 * @param arrayPeliculasFavoritas: parámetro tipo "List" asociado a la clase "PeliculaFavorita" que pasamos al método.
	 * @return: el método devuelve una lista con todas las películas favoritas.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<PeliculaFavorita> obtenerPeliculasFavoritas(List<PeliculaFavorita> arrayPeliculasFavoritas) throws RemoteException;
	/**
	 * Método para insertar una nueva película favorita en la base de datos:
	 * @param pelicula: parámetro necesario para la creación de una nueva película favorita.
	 * @param cliente: parámetro necesario para la creación de una nueva película favorita.
	 * @return: el método devuelve true si todo ha ido bien.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean setPeliculaFavorita(Pelicula pelicula, Cliente cliente) throws RemoteException;
	/**
	 * Método sincronizar que un usuario está conectado:
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	void conectarUsuario() throws RemoteException;
	/**
	 * Método para sincronizar la desconexión del usuario con los demás:
	 * @param usuario: parámetro necesario para la sincronización del usuario desconectado con todos los clientes en línea.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	void desconectarUsuario(String usuario) throws RemoteException;
	/**
	 * Método para obtener las películas alquiladas de la base de datos:
	 * @param arrayPeliculasAlquiladas: parámetro tipo "List" asociado a la clase "Alquiler" que pasamos al método.
	 * @return: el método devuelve una lista con todas las películas alquiladas .
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<Alquiler> obtenerPeliculasAlquiladas(List<Alquiler> arrayPeliculasAlquiladas) throws RemoteException;
	/**
	 * Método para insertar una nueva película vista en la base de datos:
	 * @param pelicula: parámetro necesario para la creación de una nueva película vista.
	 * @param cliente: parámetro necesario para la creación de una nueva película vista.
	 * @return: el método devuelve true si todo ha ido bien.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean setPeliculaVista(Pelicula pelicula, Cliente cliente) throws RemoteException;
	/**
	 * Método que elimina un cliente de la base de datos (su usuario y también)
	 * @param nombre: parámetro necesario para la eliminación del cliente.
	 * @param apellidos: parámetro necesario para la eliminación del cliente.
	 * @param fechaNacimiento: parámetro necesario para la eliminación del cliente.
	 * @return: el método devuelve true si todo ha ido bien.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean eliminarCliente(String nombre, String apellidos, String fechaNacimiento) throws RemoteException;
	/**
	 * Método que permite mandar un mensaje y ser sincronizado con todos los demás usuarios en línea,
	 * también almacena el mensaje en la base de datos para los usuarios que no estén conectados:
	 * @param mensaje: parámetro necesario para la sincronización con los demás usuarios y el almacenamiento del mensaje el la BD.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	void broadcastMessage(Object[] mensaje)  throws RemoteException;
	/**
	 * Método para insertar una nueva recomendación de película por parte de un usuario en la base de datos:
	 * @param usuario: parámetro necesario para la creación de una nueva recomendación de película.
	 * @param amigo: parámetro necesario para la creación de una nueva recomendación de película.
	 * @param pelicula: parámetro necesario para la creación de una nueva recomendación de película.
	 * @return: el método devuelve true si todo ha ido bien.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean setRecomendacion(String usuario, String amigo, Pelicula pelicula)  throws RemoteException;
	/**
	 * Método para obtener las recomendaciones de la base de datos:
	 * @param arrayRecomendaciones: parámetro tipo "List" asociado a la clase "Recomendacion" que pasamos al método.
	 * @return: el método devuelve una lista con todas las recomendaciones.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<Recomendacion> obtenerRecomendaciones(List<Recomendacion> arrayRecomendaciones)  throws RemoteException;
	/**
	 * Método para obtener los usuarios "amigos"  de la base de datos:
	 * @param arrayAmigos: parámetro tipo "List" asociado a la clase "Amigo" que pasamos al método.
	 * @return: el método devuelve una lista con todos los usuarios "amigos".
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<Amigo> obtenerAmigos(List<Amigo> arrayAmigos) throws RemoteException;
	/**
	 * Método para insertar un nuevo usuario "amigo" en la base de datos:
	 * @param usuario: parámetro necesario para la creación de un nuevo amigo.
	 * @param amigo: parámetro necesario para la creación de un nuevo amigo.
	 * @return: el método devuelve true si todo ha ido bien.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean setAmigo(String usuario, String amigo) throws RemoteException;
	/**
	 * Método para obtener los usuarios conectados de la aplicación:
	 * @return: el método devuelve una lista con todos los usuarios conectados actualmente en la aplicación.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<Usuario> obtenerUsuariosConectados () throws RemoteException;
	/**
	 * Método para obtener los usuarios de la base de datos:
	 * @param arrayUsuarios: parámetro tipo "List" asociado a la clase "Usuario" que pasamos al método.
	 * @return: el método devuelve una lista con todos los usuarios.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<Usuario> obtenerUsuarios (List<Usuario> arrayUsuarios) throws RemoteException;
	/**
	 * Método para obtener los alquileres de la base de datos:
	 * @param arrayAlquileres: parámetro tipo "List" asociado a la clase "Alquiler" que pasamos al método.
	 * @return: el método devuelve una lista con todos los alquileres.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<Alquiler> obtenerAlquileres (List<Alquiler> arrayAlquileres) throws RemoteException;
	/**
	 * Método para obtener los mensajes de la base de datos:
	 * @param arrayMensajes: parámetro tipo "List" asociado a la clase "Mensaje" que pasamos al método.
	 * @return: el método devuelve una lista con todos los mensajes.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<Mensaje> obtenerMensajes (List<Mensaje> arrayMensajes) throws RemoteException;
	/**
	 * Método para insertar un nuevo mensaje en la base de datos:
	 * @param mensaje: parámetro necesario para la creación de un nuevo mensaje.
	 * @return: el método devuelve true si todo ha ido bien.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean setMensaje(Mensaje mensaje) throws RemoteException;
	/**
	 * Método para obtener los clientes de la base de datos:
	 * @param arrayClientes: parámetro tipo "List" asociado a la clase "Cliente" que pasamos al método.
	 * @return: el método devuelve una lista con todos los clientes
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<Cliente> obtenerClientes(List<Cliente> arrayClientes) throws RemoteException;
	/**
	 * Método para obtener los inventarios de la base de datos:
	 * @param arrayInventarios: parámetro tipo "List" asociado a la clase "Inventario" que pasamos al método.
	 * @return: el método devuelve una lista con todos los inventarios.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<Inventario> obtenerInventarios(List<Inventario> arrayInventarios) throws RemoteException;
	/**
	 * Método para insertar un nuevo alquiler en la base de datos:
	 * @param alquiler: parámetro necesario para la creación de un nuevo alquiler.
	 * @return: el método devuelve true si todo ha ido bien.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean alquilarPelicula(Alquiler alquiler) throws RemoteException;
	/**
	 * Método para obtener el cliente actual que ha hecho la conexión con la aplicación:
	 * @return: devuelve el cliente
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	Cliente getCliente() throws RemoteException;
	/**
	 * Método para obtener el usuario actual que ha hecho la conexión con la aplicación:
	 * @return: devuelve el usuario
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	Usuario getUsuario() throws RemoteException;
	/**
	 * Método para obtener las peliculas de la base de datos:
	 * @param arrayPeliculas: parámetro tipo "List" asociado a la clase "Pelicula" que pasamos al método.
	 * @return: el método devuelve una lista con todas las películas.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<Pelicula> obtenerPeliculas(List<Pelicula> arrayPeliculas) throws RemoteException;
	/**
	 * Método para obtener las películas nuevas de la base de datos:
	 * @param arrayPeliculas: parámetro tipo "List" asociado a la clase "Pelicula" que pasamos al método.
	 * @return: el método devuelve una lista con todas las películas nuevas.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	List<Pelicula> obtenerPeliculasNuevas(List<Pelicula> arrayPeliculas) throws RemoteException;
	/**
	 * Método para insertar una nueva película en la base de datos:
	 * @param nombre: parámetro necesario para la creación de una nueva película.
	 * @param duracion: parámetro necesario para la creación de una nueva película.
	 * @param descripcion: parámetro necesario para la creación de una nueva película.
	 * @param anyo: parámetro necesario para la creación de una nueva película.
	 * @param precio: parámetro necesario para la creación de una nueva película.
	 * @param categoria: parámetro necesario para la creación de una nueva película.
	 * @param cantidad: parámetro necesario para la creación de una nueva película.
	 * @param imagen: parámetro necesario para la creación de una nueva película.
	 * @param novedad: parámetro necesario para la creación de una nueva película.
	 * @return: devuelve true si todo ha ido bien.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean insertarPelicula(String nombre, int duracion, byte[] descripcion, int anyo, float precio, String categoria,int cantidad, Imagen imagen, boolean novedad) throws RemoteException;
	/**
	 * Método para comprobar las credenciales de inicio de sesión:
	 * @param nombreUsuario: parámetro necesario para comprobar las credenciales.
	 * @param contraseña parámetro necesario para comprobar las credenciales.
	 * @return: devolverá true si las credenciales son correctas
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean login(String nombreUsuario, String contraseña) throws RemoteException;
	/**
	 * Método para la creación de un nuevo usuario en la base de datos:
	 * @param nombreUsuario: parámetro necesario para la creación del usuario.
	 * @param contraseña: parámetro necesario para la creación del usuario.
	 * @param correo: parámetro necesario para la creación del usuario.
	 * @param nombre: parámetro necesario para la creación del usuario.
	 * @param apellidos: parámetro necesario para la creación del usuario.
	 * @param fechaNacimiento: parámetro necesario para la creación del usuario.
	 * @param calle: parámetro necesario para la creación del usuario.
	 * @param ciudad: parámetro necesario para la creación del usuario.
	 * @param pais: parámetro necesario para la creación del usuario.
	 * @return: devolverá true si todo está correctamente.
	 * @throws RemoteException: excepción por si el servidor no está activo.
	 */
	boolean registerUser(String nombreUsuario, String contraseña, String correo, String nombre, String apellidos, Date fechaNacimiento, String calle, String ciudad, String pais) throws RemoteException;
}