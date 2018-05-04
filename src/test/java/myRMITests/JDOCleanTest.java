package myRMITests;

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

import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Amigo;
import videoclub.server.jdo.Categoria;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Direccion;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Inventario;
import videoclub.server.jdo.Mensaje;
import videoclub.server.jdo.Noticia;
import videoclub.server.jdo.Novedad;
import videoclub.server.jdo.Opinion;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.PeliculaFavorita;
import videoclub.server.jdo.PeliculaPendiente;
import videoclub.server.jdo.PeliculaVista;
import videoclub.server.jdo.ProximoEstreno;
import videoclub.server.jdo.Recomendacion;
import videoclub.server.jdo.Usuario;

public class JDOCleanTest {
	private PersistenceManagerFactory pmf = null;
	private PersistenceManager pm = null;
	private Transaction tx = null;

	@Before
	public void setUp() throws Exception {
		// Code executed before each test
		this.pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

		System.out.println("DataNucleus AccessPlatform with JDO");
		System.out.println("===================================");

		// Persistence of a Product and a Book.
		this.pm = this.pmf.getPersistenceManager();
		this.tx = this.pm.currentTransaction();
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodasLasImagenes() throws ParseException {
		try {
			tx.begin();
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
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodasLasCategorias() throws ParseException {
		try {
			tx.begin();
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
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodasLasPeliculas() throws ParseException {
		try {
			tx.begin();
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
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodasLasNoticias() throws ParseException {
		try {
			tx.begin();
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
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosUsuarios() throws ParseException {
		try {
			tx.begin();
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
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodasLasDirecciones() throws ParseException {
		try {
			tx.begin();
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
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosClientes() throws ParseException {
		try {
			tx.begin();
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
	}

	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testEliminarTodosLosInventarios() throws ParseException {
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Inventario.class);
			@SuppressWarnings("unchecked")
			Collection<Inventario> list = (Collection<Inventario>) q.execute();
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
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Alquiler.class);
			@SuppressWarnings("unchecked")
			Collection<Alquiler> list = (Collection<Alquiler>) q.execute();
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
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Amigo.class);
			@SuppressWarnings("unchecked")
			Collection<Amigo> list = (Collection<Amigo>) q.execute();
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
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Mensaje.class);
			@SuppressWarnings("unchecked")
			Collection<Mensaje> list = (Collection<Mensaje>) q.execute();
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
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Opinion.class);
			@SuppressWarnings("unchecked")
			Collection<Opinion> list = (Collection<Opinion>) q.execute();
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
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Novedad.class);
			@SuppressWarnings("unchecked")
			Collection<Novedad> list = (Collection<Novedad>) q.execute();
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
		try {
			tx.begin();
			Query<?> q = pm.newQuery(PeliculaFavorita.class);
			@SuppressWarnings("unchecked")
			Collection<PeliculaFavorita> list = (Collection<PeliculaFavorita>) q.execute();
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
		try {
			tx.begin();
			Query<?> q = pm.newQuery(ProximoEstreno.class);
			@SuppressWarnings("unchecked")
			Collection<ProximoEstreno> list = (Collection<ProximoEstreno>) q.execute();
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
		try {
			tx.begin();
			Query<?> q = pm.newQuery(PeliculaPendiente.class);
			@SuppressWarnings("unchecked")
			Collection<PeliculaPendiente> list = (Collection<PeliculaPendiente>) q.execute();
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
		try {
			tx.begin();
			Query<?> q = pm.newQuery(PeliculaVista.class);
			@SuppressWarnings("unchecked")
			Collection<PeliculaVista> list = (Collection<PeliculaVista>) q.execute();
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
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Recomendacion.class);
			@SuppressWarnings("unchecked")
			Collection<Recomendacion> list = (Collection<Recomendacion>) q.execute();
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