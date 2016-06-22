import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;


public class Game{
	Scene scene = null;
	public final int WIDTH = 240;
	public final int HEIGHT = 160;


	public Game(String[] args) {
		//Load up the default scene
		scene = new Scene(this, "main");

		JFrame frame = new JFrame("Azure");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Frame frameObject = new Frame(this);
		frame.add(frameObject);
		frame.addKeyListener(frameObject);
		frame.setVisible(true);
		frame.pack();

		//This song gets really old really quick
		File noSound = new File("nosound.txt");
		if (!noSound.exists()) {
			Audio.INTRO_MUSIC.start();
		}
	}


	public static void main(String[] args) {
		new Game(args);
	}
}
