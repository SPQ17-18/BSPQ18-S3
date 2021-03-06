package videoclub.server.jdo;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Usuario implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombreUsuario;
	private String contraseña;
	private String correo;
	private Cliente cliente;

	public Usuario(String nombreUsuario, String contraseña, String correo, Cliente cliente) {
		this.setCliente(cliente);
		this.setContraseña(contraseña);
		this.setCorreo(correo);
		this.setNombreUsuario(nombreUsuario);
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Usuario [getNombreUsuario()=" + getNombreUsuario() + ", getContraseña()=" + getContraseña()
				+ ", getCorreo()=" + getCorreo() + ", getCliente()=" + getCliente().toString() + "]";
	}

}
