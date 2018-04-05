package	myTests;

import org.junit.*;

import videoclub.server.jdo.Alquiler;
import videoclub.server.jdo.Categoria;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Direccion;
import videoclub.server.jdo.Inventario;
import videoclub.server.jdo.Pelicula;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Extent;
import javax.jdo.Query;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;


/**
 * @author      Diego L�pez-de-Ipi�a <dipina@deusto.es>
 * @version     1.0                                    
 * @since       2012-02-01          
 * <p><This program shows how to use a set of classes prepared to be made serializable through <b>JDO</b></p> 
 *
 */

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
		this.pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");

        System.out.println("DataNucleus AccessPlatform with JDO");
        System.out.println("===================================");

        // Persistence of a Product and a Book.
        this.pm = this.pmf.getPersistenceManager();
        this.tx = this.pm.currentTransaction();
    } 
	
	/**
	 * Converts to upper cases. Simple (bogus) method only created to show how to document the input parameters and output of a method.
	 * @param str String to convert to capital letters
	 * @return The string converted into capital letters 
	*/
	private String convert2Upcase(String str) {
		return str.toUpperCase();
	}
	
	
	/**
	 * Tests Product creation
	 * @throws ParseException 
	*/
	@Test
    public void testCreacionObjetos() throws ParseException {
//		 try
//	        {
//	            tx.begin();
//	            System.out.println(this.convert2Upcase("Persisting products"));
//	            
//	            //Creación de categorias:
//	            Categoria[] arrayCategorias = new Categoria[5];
//	            arrayCategorias[0] = new Categoria("Comedia");
//	            arrayCategorias[1] = new Categoria("Accion");
//	            arrayCategorias[2] = new Categoria("Fantasia");
//	            arrayCategorias[3] = new Categoria("Terror");
//	            arrayCategorias[4] = new Categoria("Animacion");
//	            
//	            
//	            //Creación de películas:
//	            Pelicula[] arrayPeliculas = new Pelicula[5];
//	            arrayPeliculas[0] = new Pelicula("Pelicula Nombre 1", 100, "Descripcion Pelicula 1", 1995, arrayCategorias[0], 5.5F);
//	            arrayPeliculas[1] = new Pelicula("Pelicula Nombre 2", 101, "Descripcion Pelicula 2", 1996, arrayCategorias[1], 5.6F);
//	            arrayPeliculas[2] = new Pelicula("Pelicula Nombre 3", 102, "Descripcion Pelicula 3", 1997, arrayCategorias[2], 5.7F);
//	            arrayPeliculas[3] = new Pelicula("Pelicula Nombre 4", 103, "Descripcion Pelicula 4", 1998, arrayCategorias[3], 5.8F);
//	            arrayPeliculas[4] = new Pelicula("Pelicula Nombre 5", 104, "Descripcion Pelicula 5", 1999, arrayCategorias[4], 5.9F);
//	            
//	            //Creación de inventarios:
//	            Inventario[] arrayInventarios = new Inventario[5];
//	            arrayInventarios[0] = new Inventario(10,arrayPeliculas[0]);
//	            arrayInventarios[1] = new Inventario(11,arrayPeliculas[1]);
//	            arrayInventarios[2] = new Inventario(12,arrayPeliculas[2]);
//	            arrayInventarios[3] = new Inventario(13,arrayPeliculas[3]);
//	            arrayInventarios[4] = new Inventario(14,arrayPeliculas[4]);
//	            
//	            //Creación de direcciones:
//	            Direccion[] arrayDirecciones = new Direccion[5];
//	            arrayDirecciones[0] = new Direccion("Calle 1", "Ciudad 1", "Pais 1");
//	            arrayDirecciones[1] = new Direccion("Calle 2", "Ciudad 2", "Pais 2");
//	            arrayDirecciones[2] = new Direccion("Calle 3", "Ciudad 3", "Pais 3");
//	            arrayDirecciones[3] = new Direccion("Calle 4", "Ciudad 4", "Pais 4");
//	            arrayDirecciones[4] = new Direccion("Calle 5", "Ciudad 5", "Pais 5");
//	            
//	            //Creación de clientes:
//	            Cliente[] arrayClientes = new Cliente[5];
//	            arrayClientes[0] = new Cliente("Nombre 1", "Apellido 1", new SimpleDateFormat("yyyy-MM-dd").parse("1995-07-15"), arrayDirecciones[0]);
//	            arrayClientes[1] = new Cliente("Nombre 2", "Apellido 2", new SimpleDateFormat("yyyy-MM-dd").parse("1996-07-15"), arrayDirecciones[1]);
//	            arrayClientes[2] = new Cliente("Nombre 3", "Apellido 3", new SimpleDateFormat("yyyy-MM-dd").parse("1997-07-15"), arrayDirecciones[2]);
//	            arrayClientes[3] = new Cliente("Nombre 4", "Apellido 4", new SimpleDateFormat("yyyy-MM-dd").parse("1998-07-15"), arrayDirecciones[3]);
//	            arrayClientes[4] = new Cliente("Nombre 5", "Apellido 5", new SimpleDateFormat("yyyy-MM-dd").parse("1999-07-15"), arrayDirecciones[4]);
//	            
//	            //Creación de alquileres:
//	            Alquiler[] arrayAlquileres = new Alquiler[5];	            
//	            arrayAlquileres[0] = new Alquiler(new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-1"),
//	                                              new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-10"), arrayClientes[0], arrayInventarios[0]);
//	            arrayAlquileres[1] = new Alquiler(new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-2"),
//                                                  new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-12"), arrayClientes[1], arrayInventarios[1]);
//	            arrayAlquileres[2] = new Alquiler(new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-3"),
//                                                  new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-13"), arrayClientes[2], arrayInventarios[2]);
//	            arrayAlquileres[3] = new Alquiler(new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-4"),
//                                                  new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-14"), arrayClientes[3], arrayInventarios[3]);
//	            arrayAlquileres[4] = new Alquiler(new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-5"),
//                                                  new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-15"), arrayClientes[4], arrayInventarios[4]);
//	            
//	            //MakePersistent a todos los arrays:
//	            for(int i = 0; i<arrayCategorias.length; i++)
//	            {
//	                pm.makePersistent(arrayCategorias[i]);
//	            }
//	            
//	            //MakePersistent a todos los arrays:
//	            for(int i = 0; i<arrayPeliculas.length; i++)
//	            {
//	                pm.makePersistent(arrayPeliculas[i]);
//	            }
//	            
//	            //MakePersistent a todos los arrays:
//	            for(int i = 0; i<arrayInventarios.length; i++)
//	            {
//	                pm.makePersistent(arrayInventarios[i]);
//	            }
//	            
//	            //MakePersistent a todos los arrays:
//	            for(int i = 0; i<arrayDirecciones.length; i++)
//	            {
//	                pm.makePersistent(arrayDirecciones[i]);
//	            }
//	            
//	            //MakePersistent a todos los arrays:
//	            for(int i = 0; i<arrayClientes.length; i++)
//	            {
//	                pm.makePersistent(arrayClientes[i]);
//	            }
//	            
//	            //MakePersistent a todos los arrays:
//	            for(int i = 0; i<arrayAlquileres.length; i++)
//	            {
//	                pm.makePersistent(arrayAlquileres[i]);
//	            }
//	 
//	            tx.commit();
//	            System.out.println("Objetos creados en la BD! :D");
//	        }
//	        finally
//	        {
//	            if (tx.isActive())
//	            {
//	                tx.rollback();
//	            }
//	            pm.close();
//	        }
//	        System.out.println("");
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
