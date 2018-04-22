package videoclub.client.gui.ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.io.FileUtils;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import videoclub.server.jdo.Pelicula;

public class ClientPeliculaFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JToggleButton btnMute;
	private JButton btnPause;
	private JButton btnPlay;
	private JButton btnStop;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel4;
	private JToolBar.Separator jSeparator1;
	private JToolBar jToolBar1;
	private JSlider sldProgress;
	private JSlider sldVolumen;
	private GridBagConstraints gridBagConstraints;

	private EmbeddedMediaPlayerComponent player;
	private File file;
	private Pelicula pelicula;
	static {
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC\\");
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
	}
	// bandera para controlar la reproduccion de video y el cambio en el avance
	// de video
	private boolean band = true;

	public ClientPeliculaFrame(Pelicula pelicula) {
		this.pelicula = pelicula;

		inicializar();
		componentes();
		añadir();
		eventos();

		file = new File("C:\\SoftLabs\\SPQ_GitHub\\Peliculas\\" + pelicula.getNombre() + ".mp4");
		btnPlay.doClick();
	}

	private void inicializar() {
		jPanel1 = new JPanel();
		jPanel3 = new JPanel();
		sldProgress = new JSlider();
		jPanel4 = new JPanel();
		btnPlay = new JButton();
		btnPause = new JButton();
		btnStop = new JButton();
		btnMute = new JToggleButton();
		sldVolumen = new JSlider();
		jPanel2 = new JPanel();
		jToolBar1 = new JToolBar();
		jSeparator1 = new JToolBar.Separator();
		player = new EmbeddedMediaPlayerComponent();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
	}

	private void añadir() {
		setTitle("ESTA VIENDO LA PELÕCULA: " + pelicula.getNombre());
		setLocationRelativeTo(null);

		jPanel3.add(sldProgress);
		jPanel2.add(player);
		jPanel1.add(jPanel3, BorderLayout.NORTH);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel4.add(btnPlay, gridBagConstraints);

		btnPause.setText("Pause");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel4.add(btnPause, gridBagConstraints);

		btnStop.setText("Stop");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel4.add(btnStop, gridBagConstraints);

		btnMute.setText("Mute");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel4.add(btnMute, gridBagConstraints);

		sldVolumen.setPreferredSize(new java.awt.Dimension(100, 23));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
		jPanel4.add(sldVolumen, gridBagConstraints);

		jPanel1.add(jPanel4, BorderLayout.CENTER);
		jToolBar1.add(jSeparator1);

		getContentPane().add(jPanel1, BorderLayout.SOUTH);
		getContentPane().add(jPanel2, BorderLayout.CENTER);
		getContentPane().add(jToolBar1, BorderLayout.NORTH);

		pack();
	}

	private void componentes() {
		player.setSize(jPanel2.getSize());
		player.setVisible(true);
		sldVolumen.setMinimum(0);
		sldVolumen.setMaximum(100);
		sldProgress.setMinimum(0);
		sldProgress.setMaximum(100);
		sldProgress.setValue(0);
		sldProgress.setEnabled(false);
		btnPlay.setText("Play");
		btnPause.setText("Pause");
		btnStop.setText("Stop");
		btnMute.setText("Mute");
		sldVolumen.setPreferredSize(new Dimension(100, 23));
		jPanel2.setBackground(new Color(204, 204, 204));
		jPanel2.setMinimumSize(new Dimension(100, 100));
		jPanel2.setPreferredSize(new Dimension(400, 300));
		jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.LINE_AXIS));
		jToolBar1.setFloatable(false);
		jToolBar1.setRollover(true);
		jPanel1.setLayout(new BorderLayout());
		jPanel3.setLayout(new BoxLayout(jPanel3, BoxLayout.LINE_AXIS));
		jPanel4.setLayout(new GridBagLayout());
	}

	private void eventos() {

		// Control de reproduccion
		btnPlay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (file != null) {
					player.getMediaPlayer().playMedia(file.getAbsolutePath());
					sldVolumen.setValue(100);
					sldProgress.setEnabled(true);
				}
			}
		});

		// Control de pausa
		btnPause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				player.getMediaPlayer().setPause(player.getMediaPlayer().isPlaying() ? true : false);
			}
		});

		// Control detener reproduccion
		btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				player.getMediaPlayer().stop();
				sldProgress.setValue(0);
				sldProgress.setEnabled(false);
			}
		});

		// Control silenciar
		btnMute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				player.getMediaPlayer().mute(abstractButton.getModel().isSelected());
			}
		});

		// Control slider cambiar volumen
		sldVolumen.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Object source = e.getSource();
				player.getMediaPlayer().setVolume(((JSlider) source).getValue());
			}
		});

		// Listener de reproductor para mostrar el progreso en la reproduccion
		// del video
		player.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {

			@Override
			public void positionChanged(MediaPlayer mp, float pos) {
				if (band) {
					int value = Math.min(100, Math.round(pos * 100.0f));
					sldProgress.setValue(value);
				}
			}

			@Override
			public void finished(MediaPlayer mediaPlayer) {

			}

		});

		// Listener para el slider progress
		sldProgress.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				band = false;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				band = true;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});

		// Control para cambiar a posicion de reproduccion
		sldProgress.addChangeListener(new ChangeListener() {

			@Override
			public synchronized void stateChanged(ChangeEvent e) {
				if (!band) {
					Object source = e.getSource();
					float np = ((JSlider) source).getValue() / 100f;
					player.getMediaPlayer().setPosition(np);
				}

			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				player.getMediaPlayer().stop();
				player.getMediaPlayer().mute(true);
				ClientPeliculaFrame.this.dispose();
			}
		});

	}
}
