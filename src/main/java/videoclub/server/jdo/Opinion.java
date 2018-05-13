package videoclub.server.jdo;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Opinion implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pelicula pelicula;
	private Usuario user;
	private String descripcionOpinion;

	public Opinion(Pelicula pelicula, Usuario user, String descripcionOpinion) {
		this.setDescripcionOpinion(descripcionOpinion);
		this.setPelicula(pelicula);
		this.setUser(user);
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

	@Override
	public String toString() {
		return "Opinion [getPelicula()=" + getPelicula().toString() + ", getUser()=" + getUser().toString()
				+ ", getDescripcionOpinion()=" + getDescripcionOpinion() + "]";
	}

}