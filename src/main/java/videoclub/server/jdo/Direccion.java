package videoclub.server.jdo;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Direccion implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String calle;
	private String ciudad;
	private String pais;

	public Direccion(String calle, String ciudad, String pais) {
		this.setCalle(calle);
		this.setCiudad(ciudad);
		this.setPais(pais);
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Override
	public String toString() {
		return "Direccion [getCalle()=" + getCalle() + ", getCiudad()=" + getCiudad() + ", getPais()=" + getPais()
				+ "]";
	}

}
