import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;


public class Game{
	Scene scene = null;

	public Game(String[] args) {
		//Load up the default scene
		scene = new Scene(this, "main");

		JFrame frame = new JFrame("Azure");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(240 * Frame.SCALE, 160 * Frame.SCALE);	//Gameboy advance resolution
		
		Frame frameObject = new Frame(this);
		frame.add(frameObject);
		frame.addKeyListener(frameObject);
		frame.setVisible(true);
		
		//Audio.INTRO_MUSIC.start();
	}


	public static void main(String[] args) {
		new Game(args);
	}
}
