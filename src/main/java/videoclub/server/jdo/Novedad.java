package videoclub.server.jdo;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Novedad implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pelicula pelicula;

	public Novedad(Pelicula pelicula) {
		this.setPelicula(pelicula);
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	@Override
	public String toString() {
		return "Novedad [getPelicula()=" + getPelicula().toString() + "]";
	}

}
