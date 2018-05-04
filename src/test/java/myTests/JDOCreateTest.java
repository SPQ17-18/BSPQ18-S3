package myTests;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import videoclub.server.jdo.Categoria;
import videoclub.server.jdo.Cliente;
import videoclub.server.jdo.Direccion;
import videoclub.server.jdo.Imagen;
import videoclub.server.jdo.Noticia;
import videoclub.server.jdo.Pelicula;
import videoclub.server.jdo.Usuario;

public class JDOCreateTest {
	private PersistenceManagerFactory pmf = null;
	private PersistenceManager pm = null;
	private Transaction tx = null;
	public String url = "http://www.imdb.com/list/ls025439193/";
	public String urlWeb = "http://www.imdb.com";

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
	 * TEST PARA CREAR NUEVOS USUARIOS:
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testCrearUsuarios() throws ParseException {
		try {
			tx.begin();

			System.out.println(convert2Upcase("Creando direcciones"));

			List<Direccion> arrayDirecciones = new ArrayList<Direccion>();
			arrayDirecciones.add(new Direccion("Calle 1", "Bilbao", "España"));
			arrayDirecciones.add(new Direccion("Calle 2", "Madrid", "España"));
			arrayDirecciones.add(new Direccion("Calle 3", "Lugo", "España"));
			arrayDirecciones.add(new Direccion("Calle 4", "Barcelona", "España"));
			arrayDirecciones.add(new Direccion("Calle 5", "Burgos", "España"));

			System.out.println(convert2Upcase("Creando clientes"));

			List<Cliente> arrayClientes = new ArrayList<Cliente>();
			arrayClientes.add(new Cliente("David", "García Pérez",
					new SimpleDateFormat("yyyy-MM-dd").parse("1995-07-15"), arrayDirecciones.get(0)));
			arrayClientes.add(new Cliente("Asier", "Urquijo", new SimpleDateFormat("yyyy-MM-dd").parse("1996-12-25"),
					arrayDirecciones.get(1)));
			arrayClientes.add(new Cliente("Asier", "Aldekoa", new SimpleDateFormat("yyyy-MM-dd").parse("1997-04-4"),
					arrayDirecciones.get(2)));
			arrayClientes.add(new Cliente("Aitor", "Martínez", new SimpleDateFormat("yyyy-MM-dd").parse("1996-06-5"),
					arrayDirecciones.get(3)));
			arrayClientes.add(new Cliente("Javier", "Pérez Esnaola",
					new SimpleDateFormat("yyyy-MM-dd").parse("1998-01-1"), arrayDirecciones.get(4)));

			System.out.println(convert2Upcase("Creando usuarios"));

			List<Usuario> arrayUsuarios = new ArrayList<Usuario>();
			arrayUsuarios.add(new Usuario("dgpUser", "12345", "dgp.g.p@opendeusto.es", arrayClientes.get(0)));
			arrayUsuarios.add(new Usuario("auUser", "12345", "au.g.p@opendeusto.es", arrayClientes.get(1)));
			arrayUsuarios.add(new Usuario("aaUser", "12345", "aa.g.p@opendeusto.es", arrayClientes.get(2)));
			arrayUsuarios.add(new Usuario("amUser", "12345", "am.g.p@opendeusto.es", arrayClientes.get(3)));
			arrayUsuarios.add(new Usuario("jpeUser", "12345", "jpe.g.p@opendeusto.es", arrayClientes.get(4)));

			for (Usuario user : arrayUsuarios) {
				pm.makePersistent(user);
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						"User: " + user.getNombreUsuario() + "," + user.getContraseña() + "," + user.getCorreo() + ","
								+ user.getCliente().getNombre() + " persistente...");
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						"Client: " + user.getCliente().getNombre() + "," + user.getCliente().getApellidos() + ","
								+ user.getCliente().getFecha_nacimiento() + " persistente...");
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						"Client Dir: " + user.getCliente().getDireccion().getCalle() + ","
								+ user.getCliente().getDireccion().getCiudad() + ","
								+ user.getCliente().getDireccion().getPais() + " persistente...");
			}

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
	 * TEST PARA CREAR NUEVAS PELÍCULAS:
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testCrearPeliculas() throws ParseException {
		try {
			tx.begin();

			if (getStatusConnectionCode(url) == 200) {

				// Obtengo el HTML de la web en un objeto Document
				Document document = getHtmlDocument(url);

				// Busco todas las entradas que estan dentro de:
				Elements entradas = document.select("div.lister-item.mode-detail");
				System.out.println("Número de entradas en la página inicial de http://www.imdb.com/list/ls025439193/: "
						+ entradas.size() + "\n");

				// Paseo cada una de las entradas
				for (Element elem : entradas) {
					String titulo = elem.getElementsByClass("lister-item-header").get(0).getElementsByAttribute("href")
							.text();
					String anyo = elem.getElementsByClass("lister-item-header").get(0)
							.getElementsByClass("lister-item-year text-muted unbold").text();
					String descripcion = elem.getElementsByClass("lister-item-content").text();
					String duracion = elem.select("div.lister-item-content").select("p.text-muted.text-small")
							.select("span.runtime").text();
					String categoria = elem.select("div.lister-item-content").select("p.text-muted.text-small")
							.select("span.genre").text();
					String srcImage = elem.select("div.lister-item-image.ribbonize").select("img.loadlate").toString();

					System.out.println("TITULO: " + titulo);
					System.out.println("AÑO: " + obtenerAnyo(anyo));
					System.out.println("DURACION: " + obtenerDuracion(duracion));
					System.out.println("CATEGORIA: " + obtenerGenero(categoria));
					System.out.println("DESCRIPCION: " + obtenerDescripcion(descripcion));
					System.out.println("IMAGE URL: " + obtenerSrcUrl(srcImage));

					URL url = new URL(obtenerSrcUrl(srcImage));
					byte[] data = extractBytes(url);

					// String nombre, int duracion, byte[] descripcion, int anyo, float precio,
					// Categoria categoria, Imagen image
					// Creamos película:
					Categoria cat = new Categoria(obtenerGenero(categoria));

					// Comprobación de si exite ya la categoria a crear:
					boolean existe = false;
					Categoria _catExiste = null;
					Query<?> q = pm.newQuery(Categoria.class);
					@SuppressWarnings("unchecked")
					Collection<Categoria> list = (Collection<Categoria>) q.execute();
					for (Categoria cate : list) {
						if (cate.getNombre().equals(cat.getNombre())) {
							existe = true;
							_catExiste = cate;
						}
					}

					if (existe == true) {
						cat = _catExiste;
					}

					Pelicula pelicula = new Pelicula(titulo, Integer.parseInt(obtenerDuracion(duracion)),
							obtenerDescripcion(descripcion).getBytes(), Integer.parseInt(obtenerAnyo(anyo)), 4.5F, cat,
							new Imagen(obtenerSrcUrl(srcImage), data));

					// Añadimos película:
					pm.makePersistent(pelicula);

					// LOG:
					Logger.getLogger(getClass().getName()).log(Level.INFO,
							"MAKE PERSISTENT PELICULA: " + pelicula.getNombre() + "," + pelicula.getAnyo() + ","
									+ pelicula.getDuracion() + "," + pelicula.getPrecio());
				}

			} else {
				System.out.println("El Status Code no es OK es: " + getStatusConnectionCode(url));
			}

			tx.commit();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		System.out.println("");
	}

	/**
	 * TEST PARA CREAR NUEVAS NOTICIAS:
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testCrearNoticias() throws ParseException {
		try {
			tx.begin();

			System.out.println(convert2Upcase("Creando noticias"));

			List<Noticia> arrayNoticias = new ArrayList<Noticia>();
			arrayNoticias.add(new Noticia("Noticia [1] creada a las: " + new Date()));
			arrayNoticias.add(new Noticia("Noticia [2] creada a las: " + new Date()));
			arrayNoticias.add(new Noticia("Noticia [3] creada a las: " + new Date()));
			arrayNoticias.add(new Noticia("Noticia [4] creada a las: " + new Date()));
			arrayNoticias.add(new Noticia("Noticia [5] creada a las: " + new Date()));
			for (Noticia noticia : arrayNoticias) {
				pm.makePersistent(noticia);
				// LOG:
				Logger.getLogger(getClass().getName()).log(Level.INFO,
						"Noticia: " + noticia.getNoticia() + " persistente...");
			}

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		System.out.println("");
	}

	public byte[] extractBytes(URL url) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(url);

		// get DataBufferBytes from Raster
		WritableRaster raster = bufferedImage.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

		return (data.getData());
	}

	private String obtenerDescripcion(String all) {
		String descripcion = null;
		int posinicial = all.indexOf("Error: please try again.") + 25;
		int posFinal = all.indexOf("Director") - 1;
		descripcion = all.substring(posinicial, posFinal);
		return descripcion;
	}

	private String obtenerGenero(String all) {
		String genero = "Action";
		try {
			int posInicial = all.indexOf("");
			int posFinal = all.indexOf(",");
			genero = all.substring(posInicial, posFinal);
		} catch (Exception e) {

		}
		return genero;
	}

	private String obtenerDuracion(String all) {
		String duracion = "122";
		try {
			int posFinal = all.indexOf(" ");
			duracion = all.substring(0, posFinal);
		} catch (

		Exception e) {

		}
		return duracion;
	}

	private String obtenerAnyo(String all) {
		String anyo = "2005";
		try {
			int posInicial = all.indexOf("(") + 1;
			int posFinal = all.lastIndexOf(")");
			anyo = all.substring(posInicial, posFinal);
		} catch (

		Exception e) {

		}
		return anyo;
	}

	private String obtenerSrcUrl(String all) {
		String url = null;
		int posInicial = all.indexOf("class=\"loadlate\"") + 27;
		int posFinal = all.indexOf("data-tconst=") - 2;
		url = all.substring(posInicial, posFinal);
		return url;
	}

	/**
	 * Con esta método compruebo el Status code de la respuesta que recibo al hacer
	 * la petición EJM: 200 OK 300 Multiple Choices 301 Moved Permanently 305 Use
	 * Proxy 400 Bad Request 403 Forbidden 404 Not Found 500 Internal Server Error
	 * 502 Bad Gateway 503 Service Unavailable
	 * 
	 * @param url
	 * @return Status Code
	 */
	public int getStatusConnectionCode(String url) {

		Response response = null;

		try {
			response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
		} catch (IOException ex) {
			System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
		}
		return response.statusCode();
	}

	/**
	 * Con este método devuelvo un objeto de la clase Document con el contenido del
	 * HTML de la web que me permitirá parsearlo con los métodos de la librelia
	 * JSoup
	 * 
	 * @param url
	 * @return Documento con el HTML
	 */
	public Document getHtmlDocument(String url) {

		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
		} catch (IOException ex) {
			System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
		}
		return doc;
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