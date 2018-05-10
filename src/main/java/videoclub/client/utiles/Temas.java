package videoclub.client.utiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;

public class Temas {

	/**
	 * 
	 * @param tema
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Temas(String tema) throws FileNotFoundException, IOException {
		ComprobarTemaElegido(tema);
	}

	/**
	 * 
	 * @param tema
	 */
	private void ComprobarTemaElegido(String tema) {
		if (tema.equals("Tema Autum")) {
			try {
				UIManager.setLookAndFeel((LookAndFeel) new SubstanceAutumnLookAndFeel());
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				Logger.getLogger(getClass().getName()).log(Level.WARNING,
						" # Error (UnsupportedLookAndFeelException) ComprobarTemaElegido: " + e.getMessage());
			} catch (NullPointerException e2) {
				Logger.getLogger(getClass().getName()).log(Level.WARNING,
						" # Error (NullPointerException) ComprobarTemaElegido: " + e2.getMessage());
			}
		} else if (tema.equals("Tema Raven")) {
			try {
				UIManager.setLookAndFeel((LookAndFeel) new SubstanceRavenLookAndFeel());
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				Logger.getLogger(getClass().getName()).log(Level.WARNING,
						" # Error (UnsupportedLookAndFeelException) ComprobarTemaElegido: " + e.getMessage());
			} catch (NullPointerException e2) {
				Logger.getLogger(getClass().getName()).log(Level.WARNING,
						" # Error (NullPointerException) ComprobarTemaElegido: " + e2.getMessage());
			}
		}
	}
}