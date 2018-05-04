package myRMITests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.databene.contiperf.timer.RandomTimer;
import org.junit.Rule;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import videoclub.server.gui.ICollector;
import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Pelicula;

public class RMITest {
	@Rule
	public ContiPerfRule contiPerfRule = new ContiPerfRule();
	public static ICollector collector = RMIConectionTest.collector;

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(RMITest.class);
	}

	// Registro de usuarios en paralelo:
	private int invocacion = 0;

	// @PerfTest(invocations = 20000, threads = 10, timer = RandomTimer.class,
	// timerParams = { 3, 8 })//RÁPIDO PERO NO SEGURO
	@PerfTest(invocations = 20, threads = 1, timer = RandomTimer.class, timerParams = { 300, 800 }) // LENTO PERO
	@Test // SEGURO
	public void registroUsuariosTest() {
		try {
			collector.registerUser("DGP" + invocacion, "12345" + invocacion, "DGP@opendeusto.es" + invocacion,
					"David" + invocacion, "García Pérez" + invocacion, new Date(), "La Paz" + invocacion,
					"Bilbao" + invocacion, "España" + invocacion);
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # REGISTRO USUARIO: DGP" + invocacion);
			invocacion++;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	// Consultas paralelas de inicio de sesión incorrectas:
	@Test
	// @PerfTest(invocations = 20000, threads = 1000, timer = RandomTimer.class,
	// timerParams = { 300, 800 })//RÁPIDO PERO NO SEGURO
	@PerfTest(invocations = 20, threads = 1, timer = RandomTimer.class, timerParams = { 300, 800 })
	public void inicioSesionIncorrectoTest() {
		boolean dev = false;
		try {
			dev = collector.login("DGP", "11111"); // ERROR DE CONTRASEÑA!
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # INICIO SESION TEST CORRECTO: " + dev);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	// Consultas paralelas de inicio de sesión correctas:
	@Test
	// @PerfTest(invocations = 20000, threads = 1000, timer = RandomTimer.class,
	// timerParams = { 300, 800 })//RÁPIDO PERO NO SEGURO
	@PerfTest(invocations = 20, threads = 1, timer = RandomTimer.class, timerParams = { 300, 800 })
	public void inicioSesionCorrectoTest() {
		boolean dev = false;
		try {
			dev = collector.login("DGP", "12345"); // CONTRASEÑA CORRECTA!
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # INICIO SESION TEST CORRECTO: " + dev);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	// Consultas paralelas para la obtención de películas:
	@Test
	// @PerfTest(invocations = 20000, threads = 1000, timer = RandomTimer.class,
	// timerParams = { 300, 800 })//RÁPIDO PERO NO SEGURO
	@PerfTest(invocations = 20, threads = 1, timer = RandomTimer.class, timerParams = { 300, 800 })
	public void obtencionPeliculasTest() {
		try {
			List<Pelicula> arrayPeliculas = new ArrayList<Pelicula>();
			arrayPeliculas = collector.obtenerPeliculas(arrayPeliculas);
			for (Pelicula pelicula : arrayPeliculas) {
				// LOG:
				Logger.getLogger(getClass().getName()).log(Level.INFO, "PELICULA: " + pelicula.getNombre() + ","
						+ pelicula.getAnyo() + "," + pelicula.getDuracion() + "," + pelicula.getPrecio());
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # OBTENCIÓN PELICULAS TEST: " + arrayPeliculas);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}

	// Consultas paralelas para la obtención de alquileres:
	@Test
	// @PerfTest(invocations = 20000, threads = 1000, timer = RandomTimer.class,
	// timerParams = { 300, 800 })//RÁPIDO PERO NO SEGURO
	@PerfTest(invocations = 20, threads = 1, timer = RandomTimer.class, timerParams = { 300, 800 })
	public void obtencionAlquileresTest() {
		try {
			List<Alquiler> arrayAlquileres = new ArrayList<Alquiler>();
			arrayAlquileres = collector.obtenerAlquileres(arrayAlquileres);
			for (Alquiler alquiler : arrayAlquileres) {
				// LOG:
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						"ALQUILER: " + alquiler.getFecha_alquiler() + "," + alquiler.getFecha_devolucion());
				// LOG:
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						"CLIENTE: " + alquiler.getCliente().getNombre() + "," + alquiler.getCliente().getApellidos());
				// LOG:
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						"INVENTARIO PELICULA: " + alquiler.getInventario().getPelicula().getNombre() + ","
								+ alquiler.getInventario().getDisponibles());
			}
			Logger.getLogger(getClass().getName()).log(Level.INFO, " # OBTENCIÓN ALQUILERES TEST: " + arrayAlquileres);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
		}
	}
}
