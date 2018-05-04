package myTests;

import java.text.ParseException;
import java.util.Collection;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import videoclub.server.jdo.Categoria;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Direccion;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Noticia;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.Usuario;

public class JDOCleanTest {
	private PersistenceManagerFactory pmf = null;
	private PersistenceManager pm = null;
	private Transaction tx = null;
//
//	public static junit.framework.Test suite() {
//		return new JUnit4TestAdapter(RMITest.class);
//	}

	@Before
	public void setUp() throws Exception {
		// Code executed before each test
		this.pmf = JDOHelper.getPersistenceManagerFactory("datanucleusTEST.properties");

		System.out.println("DataNucleus AccessPlatform with JDO");
		System.out.println("===================================");

		// Persistence of a Product and a Book.
		this.pm = this.pmf.getPersistenceManager();
		this.tx = this.pm.currentTransaction();
	}

	private String convert2Upcase(String str) {
		return str.toUpperCase();
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodasLasImagenes() throws ParseException {
		try {
			tx.begin();

			System.out.println(convert2Upcase("Eliminando todas las imágenes"));

			Query<?> q = pm.newQuery(Imagen.class);
			@SuppressWarnings("unchecked")
			Collection<Imagen> list = (Collection<Imagen>) q.execute();
			pm.deletePersistentAll(list);

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		System.out.println("");
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodasLasCategorias() throws ParseException {
		try {
			tx.begin();

			System.out.println(convert2Upcase("Eliminando todas las categorias"));

			Query<?> q = pm.newQuery(Categoria.class);
			@SuppressWarnings("unchecked")
			Collection<Categoria> list = (Collection<Categoria>) q.execute();
			pm.deletePersistentAll(list);

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		System.out.println("");
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodasLasPeliculas() throws ParseException {
		try {
			tx.begin();

			System.out.println(convert2Upcase("Eliminando todas las películas"));

			Query<?> q = pm.newQuery(Pelicula.class);
			@SuppressWarnings("unchecked")
			Collection<Pelicula> list = (Collection<Pelicula>) q.execute();
			pm.deletePersistentAll(list);

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		System.out.println("");
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodasLasNoticias() throws ParseException {
		try {
			tx.begin();

			System.out.println(convert2Upcase("Eliminando todas las noticias"));

			Query<?> q = pm.newQuery(Noticia.class);
			@SuppressWarnings("unchecked")
			Collection<Noticia> list = (Collection<Noticia>) q.execute();
			pm.deletePersistentAll(list);

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		System.out.println("");
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosUsuarios() throws ParseException {
		try {
			tx.begin();

			System.out.println(convert2Upcase("Eliminando todos los usuarios"));

			Query<?> q = pm.newQuery(Usuario.class);
			@SuppressWarnings("unchecked")
			Collection<Usuario> list = (Collection<Usuario>) q.execute();
			pm.deletePersistentAll(list);

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		System.out.println("");
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodasLasDirecciones() throws ParseException {
		try {
			tx.begin();

			System.out.println(convert2Upcase("Eliminando todas las direcciones"));

			Query<?> q = pm.newQuery(Direccion.class);
			@SuppressWarnings("unchecked")
			Collection<Direccion> list = (Collection<Direccion>) q.execute();
			pm.deletePersistentAll(list);

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		System.out.println("");
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosClientes() throws ParseException {
		try {
			tx.begin();

			System.out.println(convert2Upcase("Eliminando todos los clientes"));

			Query<?> q = pm.newQuery(Cliente.class);
			@SuppressWarnings("unchecked")
			Collection<Cliente> list = (Collection<Cliente>) q.execute();
			pm.deletePersistentAll(list);

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		System.out.println("");
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