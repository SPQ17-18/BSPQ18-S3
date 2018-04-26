package videoclub.server.jdo;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Opinion implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pelicula pelicula;
	private Usuario user;
	private String descripcionOpinion;

	public Opinion(Pelicula pelicula, Usuario user, String descripcionOpinion) {
		this.pelicula = pelicula;
		this.user = user;
		this.descripcionOpinion = descripcionOpinion;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public String getDescripcionOpinion() {
		return descripcionOpinion;
	}

	public void setDescripcionOpinion(String descripcionOpinion) {
		this.descripcionOpinion = descripcionOpinion;
	}

}