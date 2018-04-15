package videoclub.server.jdo;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Amigo  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Usuario amigo;
	
	public Amigo(Usuario usuario, Usuario amigo) {
		this.usuario = usuario;
		this.amigo = amigo;
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
	
	

}
