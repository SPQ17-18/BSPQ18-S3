package videoclub.server.jdo;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class NotificarAlquiler implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date fechaNotificacion;

	public NotificarAlquiler(Date fechaNotificacion) {
		this.setFechaNotificacion(fechaNotificacion);
	}

	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	@Override
	public String toString() {
		return "NotificarAlquiler [getFechaNotificacion()=" + getFechaNotificacion() + "]";
	}

}
