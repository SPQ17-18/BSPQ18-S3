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

import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Usuario;

//@Ignore
public class JDODeleteClientesTest {
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
	public void test10EliminarTodosLosUsuarios() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO USUARIOS...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Usuario.class);
			@SuppressWarnings("unchecked")
			Collection<Usuario> list = (Collection<Usuario>) q.execute();
			Iterator<Usuario> iterator = list.iterator();
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
	public void test20EliminarTodosLosClientes() throws ParseException {
		Logger.getLogger(getClass().getName()).log(Level.INFO, " # ELIMINANDO CLIENTES...");
		try {
			tx.begin();
			Query<?> q = pm.newQuery(Cliente.class);
			@SuppressWarnings("unchecked")
			Collection<Cliente> list = (Collection<Cliente>) q.execute();
			Iterator<Cliente> iterator = list.iterator();
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