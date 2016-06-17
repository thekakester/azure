import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JFrame;

public class Frame extends Component implements KeyListener{
	public static int SCALE = 4;
	public Game game;
	private boolean keyMap[] = new boolean[10];
	private HashMap<Integer,Keys> keyMapping = new HashMap<Integer,Keys>();
	
	public Frame(Game game) {
		this.game = game;
		
		keyMapping.put(KeyEvent.VK_UP, 		Keys.UP);
		keyMapping.put(KeyEvent.VK_DOWN, 	Keys.DOWN);
		keyMapping.put(KeyEvent.VK_LEFT, 	Keys.LEFT);
		keyMapping.put(KeyEvent.VK_RIGHT, 	Keys.RIGHT);
		keyMapping.put(KeyEvent.VK_ENTER, 	Keys.START);
		keyMapping.put(KeyEvent.VK_SHIFT, 	Keys.SELECT);
		keyMapping.put(KeyEvent.VK_Z, 		Keys.A);
		keyMapping.put(KeyEvent.VK_X, 		Keys.B);
		keyMapping.put(KeyEvent.VK_A, 		Keys.L);
		keyMapping.put(KeyEvent.VK_S,	 	Keys.R);
		
	}
	
	@Override
	public void paint(Graphics gs) {
		Graphics2D g = (Graphics2D)gs;
		g.scale(SCALE, SCALE);
		
		game.scene.update();
		game.scene.draw(g);
		
		if (isKeyPressed(Keys.UP)) 		{game.scene.player.move(Direction.UP);}
		if (isKeyPressed(Keys.DOWN)) 	{game.scene.player.move(Direction.DOWN);}
		if (isKeyPressed(Keys.LEFT)) 	{game.scene.player.move(Direction.LEFT);}
		if (isKeyPressed(Keys.RIGHT)) 	{game.scene.player.move(Direction.RIGHT);}
		
		Sprites.LOGO.draw(g, 10, 10);
		
		try {
			Thread.sleep(17);
		} catch (Exception e) {
			System.err.println("Can't sleep");
		}
		repaint();
	}

	public boolean isKeyPressed(Keys k) {
		return keyMap[k.ordinal()];
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		keyMap[keyMapping.get(arg0.getKeyCode()).ordinal()] = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		keyMap[keyMapping.get(arg0.getKeyCode()).ordinal()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
