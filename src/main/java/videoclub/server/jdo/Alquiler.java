package videoclub.server.jdo;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Alquiler implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date fecha_alquiler;
	private Date fecha_devolucion;
	private Cliente cliente;
	private Inventario inventario;

	public Alquiler(Date fecha_alquiler, Date fecha_devolucion, Cliente cliente, Inventario inventario) {
		this.setCliente(cliente);
		this.setFecha_alquiler(fecha_alquiler);
		this.setFecha_devolucion(fecha_devolucion);
		this.setInventario(inventario);
	}

	public Date getFecha_alquiler() {
		return fecha_alquiler;
	}

	public void setFecha_alquiler(Date fecha_alquiler) {
		this.fecha_alquiler = fecha_alquiler;
	}

	public Date getFecha_devolucion() {
		return fecha_devolucion;
	}

	public void setFecha_devolucion(Date fecha_devolucion) {
		this.fecha_devolucion = fecha_devolucion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	@Override
	public String toString() {
		return "Alquiler [getFecha_alquiler()=" + getFecha_alquiler() + ", getFecha_devolucion()="
				+ getFecha_devolucion() + ", getCliente()=" + getCliente() + ", getInventario()=" + getInventario()
				+ "]";
	}

}
