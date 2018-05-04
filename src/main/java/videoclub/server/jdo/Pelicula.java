package videoclub.server.jdo;

import java.util.Arrays;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Pelicula implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private int duracion;
	private byte[] descripcion;
	private int anyo;
	private Categoria categoria;
	private float precio;
	private Imagen image;

	public Pelicula(String nombre, int duracion, byte[] descripcion, int anyo, float precio, Categoria categoria,
			Imagen image) {
		this.setAnyo(anyo);
		this.setCategoria(categoria);
		this.setDescripcion(descripcion);
		this.setDuracion(duracion);
		this.setImage(image);
		this.setNombre(nombre);
		this.setPrecio(precio);
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public byte[] getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(byte[] descripcion) {
		this.descripcion = descripcion;
	}

	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Imagen getImage() {
		return image;
	}

	public void setImage(Imagen image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Pelicula [getPrecio()=" + getPrecio() + ", getNombre()=" + getNombre() + ", getDuracion()="
				+ getDuracion() + ", getDescripcion()=" + Arrays.toString(getDescripcion()) + ", getAnyo()=" + getAnyo()
				+ ", getCategoria()=" + getCategoria().toString() + ", getImage()=" + getImage().toString() + "]";
	}

}
