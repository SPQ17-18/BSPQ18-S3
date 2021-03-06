package videoclub.server.jdo;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Cliente implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String apellidos;
	private Date fecha_nacimiento;
	private Direccion direccion;

	public Cliente(String nombre, String apellidos, Date fecha_nacimiento, Direccion direccion) {
		this.setApellidos(apellidos);
		this.setDireccion(direccion);
		this.setFecha_nacimiento(fecha_nacimiento);
		this.setNombre(nombre);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Cliente [getNombre()=" + getNombre() + ", getApellidos()=" + getApellidos() + ", getFecha_nacimiento()="
				+ getFecha_nacimiento() + ", getDireccion()=" + getDireccion().toString() + "]";
	}

}
