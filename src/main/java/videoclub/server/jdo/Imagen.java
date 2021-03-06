package videoclub.server.jdo;

import java.util.Arrays;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Imagen implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte[] image;
	private String nombre;

	public Imagen(String nombre, byte[] image) {
		this.setNombre(nombre);
		this.setImage(image);
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Imagen [getImage()=" + Arrays.toString(getImage()) + ", getNombre()=" + getNombre() + "]";
	}

}
