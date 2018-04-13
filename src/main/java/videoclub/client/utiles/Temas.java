package videoclub.client.utiles;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

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
		try {
			if (tema.equals("Tema Autum")) {
				try {
					UIManager.setLookAndFeel((LookAndFeel) new SubstanceAutumnLookAndFeel());
				} catch (Exception ex) {

				}

			} else if (tema.equals("Tema Raven")) {
				try {
					UIManager.setLookAndFeel((LookAndFeel) new SubstanceRavenLookAndFeel());
				} catch (Exception ex) {

				}

			}
		} catch (Exception e) {

		}
	}
}