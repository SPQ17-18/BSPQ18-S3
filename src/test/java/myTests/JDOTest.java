package myTests;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Direccion;
import videoclub.server.jdo.Usuario;

public class JDOTest {
	/**
	 * This variable represents the persistence manager factory instance
	 */
	private PersistenceManagerFactory pmf = null;
	/**
	 * This variable represents the persistence manager instance
	 */
	private PersistenceManager pm = null;
	/**
	 * This variable represents the transaction instance
	 */
	private Transaction tx = null;

	/**
	 * It initializes the variables used by the other methods
	 */
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

	/**
	 * Converts to upper cases. Simple (bogus) method only created to show how to
	 * document the input parameters and output of a method.
	 * 
	 * @param str
	 *            String to convert to capital letters
	 * @return The string converted into capital letters
	 */
	private String convert2Upcase(String str) {
		return str.toUpperCase();
	}

	/**
	 * Tests Product creation
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testCreacionObjetos() throws ParseException {
		try {
			tx.begin();

			System.out.println(convert2Upcase("Probando la insercción de un usuario en la base de datos:"));

			Direccion direccion = new Direccion("Alameda Urquijo", "Bilbao", "España");
			Cliente cliente = new Cliente("David", "García Pérez",
					new SimpleDateFormat("yyyy-MM-dd").parse("1995-07-15"), direccion);
			Usuario user = new Usuario("dgpUser", "12345", "dgp.g.p@opendeusto.es", cliente);
			pm.makePersistent(user);

			System.out.println(convert2Upcase("Usuario creado!"));

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
	 * Tests Product Extents
	 */
	@Test
	public void testProductExtent() {

	}

	/**
	 * Tests Product queries
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testProductQuery() {

	}

	/**
	 * Tests Product deletion
	 */
	@Test
	public void testProductDeletion() {

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
