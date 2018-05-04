package videoclub.server.jdo;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Recomendacion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Usuario amigo;
	private Pelicula pelicula;

	public Recomendacion(Usuario usuario, Usuario amigo, Pelicula pelicula) {
		this.setAmigo(amigo);
		this.setPelicula(pelicula);
		this.setUsuario(usuario);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getAmigo() {
		return amigo;
	}

	public void setAmigo(Usuario amigo) {
		this.amigo = amigo;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	@Override
	public String toString() {
		return "Recomendacion [getUsuario()=" + getUsuario().toString() + ", getAmigo()=" + getAmigo().toString()
				+ ", getPelicula()=" + getPelicula().toString() + "]";
	}

}
