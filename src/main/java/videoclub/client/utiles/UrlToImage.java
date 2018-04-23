package videoclub.client.utiles;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class UrlToImage {

	private ImageIcon imageIcon;

	public UrlToImage(String url) {
		this.imageIcon = convertirUrlToImage(url);
	}

	private ImageIcon convertirUrlToImage(String urlPatch) {
		Image imagen = null;
		URL url;
		try {
			url = new URL(urlPatch);
			imagen = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon img = new ImageIcon(imagen);
		return img;
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

}
