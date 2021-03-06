package videoclub.server.jdo;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Inventario implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int disponibles;
	private Pelicula pelicula;

	public Inventario(int disponibles, Pelicula pelicula) {
		this.setDisponibles(disponibles);
		this.setPelicula(pelicula);
	}

	public int getDisponibles() {
		return disponibles;
	}

	public void setDisponibles(int disponibles) {
		this.disponibles = disponibles;
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	@Override
	public String toString() {
		return "Inventario [getDisponibles()=" + getDisponibles() + ", getPelicula()=" + getPelicula().toString() + "]";
	}

}
