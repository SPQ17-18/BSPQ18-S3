package videoclub.server.jdo;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Noticia implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String noticia;

	public Noticia(String noticia) {
		this.setNoticia(noticia);
	}

	public String getNoticia() {
		return noticia;
	}

	public void setNoticia(String noticia) {
		this.noticia = noticia;
	}

	@Override
	public String toString() {
		return "Noticia [getNoticia()=" + getNoticia() + "]";
	}
	
	
}