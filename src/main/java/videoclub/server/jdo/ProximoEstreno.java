package videoclub.server.jdo;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class ProximoEstreno implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombrePelicula;

	public ProximoEstreno(String nombrePelicula) {
		this.setNombrePelicula(nombrePelicula);
	}

	public String getNombrePelicula() {
		return nombrePelicula;
	}

	public void setNombrePelicula(String nombrePelicula) {
		this.nombrePelicula = nombrePelicula;
	}

	@Override
	public String toString() {
		return "ProximoEstreno [getNombrePelicula()=" + getNombrePelicula() + "]";
	}

}