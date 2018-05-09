package myRMITests;

import java.text.ParseException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Amigo;
import videoclub.server.jdo.Mensaje;
import videoclub.server.jdo.Noticia;
import videoclub.server.jdo.Novedad;
import videoclub.server.jdo.Opinion;
import videoclub.server.jdo.PeliculaFavorita;
import videoclub.server.jdo.PeliculaPendiente;
import videoclub.server.jdo.PeliculaVista;
import videoclub.server.jdo.ProximoEstreno;
import videoclub.server.jdo.Recomendacion;

//@Ignore
public class JDODeleteAllLessPeliculasTest {
	private PersistenceManagerFactory pmf = null;
	private PersistenceManager pm = null;
	private Transaction tx = null;

	@Before
	public void setUp() throws Exception {
		// Code executed before each test
		this.pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		// Persistence of a Product and a Book.
		this.pm = this.pmf.getPersistenceManager();
		this.tx = this.pm.currentTransaction();
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodasLasNoticias() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO NOTICIAS...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Noticia.class);
			@SuppressWarnings("unchecked")
			Collection<Noticia> list = (Collection<Noticia>) q.execute();
			Iterator<Noticia> iterator = list.iterator();
			while (iterator.hasNext()) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, iterator.next().toString());
			}
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosAlquileres() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO ALQUILERES...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Alquiler.class);
			@SuppressWarnings("unchecked")
			Collection<Alquiler> list = (Collection<Alquiler>) q.execute();
			Iterator<Alquiler> iterator = list.iterator();
			while (iterator.hasNext()) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, iterator.next().toString());
			}
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosAmigos() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO AMIGOS...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Amigo.class);
			@SuppressWarnings("unchecked")
			Collection<Amigo> list = (Collection<Amigo>) q.execute();
			Iterator<Amigo> iterator = list.iterator();
			while (iterator.hasNext()) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, iterator.next().toString());
			}
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosMensajes() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO MENSAJES...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Mensaje.class);
			@SuppressWarnings("unchecked")
			Collection<Mensaje> list = (Collection<Mensaje>) q.execute();
			Iterator<Mensaje> iterator = list.iterator();
			while (iterator.hasNext()) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, iterator.next().toString());
			}
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosOpiniones() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO OPINIONES...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Opinion.class);
			@SuppressWarnings("unchecked")
			Collection<Opinion> list = (Collection<Opinion>) q.execute();
			Iterator<Opinion> iterator = list.iterator();
			while (iterator.hasNext()) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, iterator.next().toString());
			}
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosNovedades() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO NOVEDADES...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Novedad.class);
			@SuppressWarnings("unchecked")
			Collection<Novedad> list = (Collection<Novedad>) q.execute();
			Iterator<Novedad> iterator = list.iterator();
			while (iterator.hasNext()) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, iterator.next().toString());
			}
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosPeliculasFavoritas() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO PELÍCULAS FAVORITAS...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(PeliculaFavorita.class);
			@SuppressWarnings("unchecked")
			Collection<PeliculaFavorita> list = (Collection<PeliculaFavorita>) q.execute();
			Iterator<PeliculaFavorita> iterator = list.iterator();
			while (iterator.hasNext()) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, iterator.next().toString());
			}
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosEstrenos() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO ESTRENOS...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(ProximoEstreno.class);
			@SuppressWarnings("unchecked")
			Collection<ProximoEstreno> list = (Collection<ProximoEstreno>) q.execute();
			Iterator<ProximoEstreno> iterator = list.iterator();
			while (iterator.hasNext()) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, iterator.next().toString());
			}
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosPeliculasPendientes() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO PELÍCULAS PENDIENTES...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(PeliculaPendiente.class);
			@SuppressWarnings("unchecked")
			Collection<PeliculaPendiente> list = (Collection<PeliculaPendiente>) q.execute();
			Iterator<PeliculaPendiente> iterator = list.iterator();
			while (iterator.hasNext()) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, iterator.next().toString());
			}
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosPeliculasVistas() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO PELÍCULAS VISTAS...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(PeliculaVista.class);
			@SuppressWarnings("unchecked")
			Collection<PeliculaVista> list = (Collection<PeliculaVista>) q.execute();
			Iterator<PeliculaVista> iterator = list.iterator();
			while (iterator.hasNext()) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, iterator.next().toString());
			}
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosRecomendaciones() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO RECOMENDACIONES...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Recomendacion.class);
			@SuppressWarnings("unchecked")
			Collection<Recomendacion> list = (Collection<Recomendacion>) q.execute();
			Iterator<Recomendacion> iterator = list.iterator();
			while (iterator.hasNext()) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, iterator.next().toString());
			}
			pm.deletePersistentAll(list);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Temoves everything not needed after executing a test
	 */
	@After
	public void tearDown() throws Exception {

		if (this.pm != null) {
			this.pm.close();
		}
	}
}