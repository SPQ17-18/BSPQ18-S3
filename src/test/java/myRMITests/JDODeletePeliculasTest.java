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

import videoclub.server.jdo.Categoria;
import videoclub.server.jdo.Direccion;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Inventario;
import videoclub.server.jdo.Pelicula;

@Ignore
public class JDODeletePeliculasTest {
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
	public void testEliminarTodasLasDirecciones() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO DIRECCIONES...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Direccion.class);
			@SuppressWarnings("unchecked")
			Collection<Direccion> list = (Collection<Direccion>) q.execute();
			Iterator<Direccion> iterator = list.iterator();
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
	public void testEliminarTodosLosInventarios() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO INVENTARIOS...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Inventario.class);
			@SuppressWarnings("unchecked")
			Collection<Inventario> list = (Collection<Inventario>) q.execute();
			Iterator<Inventario> iterator = list.iterator();
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
	public void testEliminarTodasLasPeliculas() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO PELÍCULAS...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Pelicula.class);
			@SuppressWarnings("unchecked")
			Collection<Pelicula> list = (Collection<Pelicula>) q.execute();
			Iterator<Pelicula> iterator = list.iterator();
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
	public void testEliminarTodasLasImagenes() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO IMÁGENES...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Imagen.class);
			@SuppressWarnings("unchecked")
			Collection<Imagen> list = (Collection<Imagen>) q.execute();
			Iterator<Imagen> iterator = list.iterator();
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
	public void testEliminarTodasLasCategorias() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO CATEGORIAS...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Categoria.class);
			@SuppressWarnings("unchecked")
			Collection<Categoria> list = (Collection<Categoria>) q.execute();
			Iterator<Categoria> iterator = list.iterator();
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