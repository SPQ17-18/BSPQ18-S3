package videoclub.client.utiles;

import java.util.List;

import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Pelicula;

public class Estadisticas {

	/**
	 * Método para obtener las veces que se ha alquilado una película:
	 * 
	 * @param alquileres:
	 *            array con los alquileres de la base de datos
	 * @param pelicula:
	 *            pelicula a comprobar
	 * @return devuelve las veces que se ha alquilado la película pasada como
	 *         parámetro
	 */
	public int getCountPeliculaAlquilada(List<Alquiler> alquileres, Pelicula pelicula) {
		int dev = 0;
		// Buscamos dicha película
		for (Alquiler alquiler : alquileres) {
			// Localizamos la película:
			if (alquiler.getInventario().getPelicula().getNombre().equals(pelicula.getNombre())) {
				// Localizada:
				dev++;
			}
		}

		// Devolvemos valor de dicha película:
		return dev;
	}

	/**
	 * Método para obtener las veces que se ha alquilado un género en concreto:
	 * 
	 * @param alquileres:
	 *            array con los alquileres de la base de datos
	 * @param pelicula:
	 *            película para comprobar el género más usado
	 * @return devuelve las veces que se ha usado dicho género
	 */
	public int getCountGeneroPelicula(List<Alquiler> alquileres, Pelicula pelicula) {
		int dev = 0;
		// Buscamos dicha película
		for (Alquiler alquiler : alquileres) {
			// Localizamos la película:
			if (alquiler.getInventario().getPelicula().getCategoria().getNombre()
					.equals(pelicula.getCategoria().getNombre())) {
				// Localizada:
				dev++;
			}
		}

		// Devolvemos valor del género:
		return dev;
	}

	/**
	 * Método para obtener el número de veces que un cliente a alquilado
	 * películas:
	 * 
	 * @param alquileres:
	 *            array con los alquileres de la base de datos
	 * @param cliente:
	 *            cliente actual a comprobar con los alquileres de la base de
	 *            datos
	 * @return devuelve el número de veces que el cliente a alquilado películas
	 */
	public int getCountClienteAlquiler(List<Alquiler> alquileres, Cliente cliente) {
		int dev = 0;
		// Buscamos dicha película
		for (Alquiler alquiler : alquileres) {
			// Localizamos la película:
			if (alquiler.getCliente().getNombre().equals(cliente.getNombre())
					&& alquiler.getCliente().getApellidos().equals(cliente.getApellidos())
					&& alquiler.getCliente().getFecha_nacimiento().equals(cliente.getFecha_nacimiento())) {
				// Localizada:
				dev++;
			}
		}

		// Devolvemos valor del cliente:
		return dev;
	}

}
