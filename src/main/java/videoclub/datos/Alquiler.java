package videoclub.datos;

import java.util.Date;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.InheritanceStrategy;

@PersistenceCapable
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Alquiler {
	private Date fecha_alquiler;
	private Date fecha_devolucion;
	private Cliente cliente;
	private Inventario inventario;
	
	public Alquiler(Date fecha_alquiler, Date fecha_devolucion, Cliente cliente, Inventario inventario) {
		this.fecha_alquiler = fecha_alquiler;
		this.fecha_devolucion = fecha_devolucion;
		this.cliente = cliente;
		this.inventario = inventario;
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
}
