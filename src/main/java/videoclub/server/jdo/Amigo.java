package videoclub.server.jdo;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Amigo  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Usuario amigo;
	
	public Amigo(Usuario usuario, Usuario amigo) {
		this.setAmigo(amigo);
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
	@Override
	public String toString() {
		return "Amigo [getUsuario()=" + getUsuario() + ", getAmigo()=" + getAmigo() + "]";
	}
	
	
	

}
