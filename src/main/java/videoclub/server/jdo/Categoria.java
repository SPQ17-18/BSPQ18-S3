package videoclub.server.jdo;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Categoria  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	
	public Categoria(String nombre) {
		this.setNombre(nombre);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Categoria [getNombre()=" + getNombre() + "]";
	}
	
	
}
