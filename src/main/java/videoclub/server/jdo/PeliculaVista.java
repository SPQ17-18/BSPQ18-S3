package videoclub.server.jdo;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class PeliculaVista implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pelicula pelicula;
	private Cliente cliente;

	public PeliculaVista(Pelicula pelicula, Cliente cliente) {
		this.setCliente(cliente);
		this.setPelicula(pelicula);
	}

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "PeliculaVista [getPelicula()=" + getPelicula().toString() + ", getCliente()=" + getCliente().toString()
				+ "]";
	}

}