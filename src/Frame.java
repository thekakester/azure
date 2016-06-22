import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.HashMap;

import javax.swing.JFrame;

public class Frame extends Component implements KeyListener{
	public static int SCALE = 2;
	public Game game;
	private boolean keyMap[] = new boolean[11];
	private HashMap<Integer,Keys> keyMapping = new HashMap<Integer,Keys>();
	private boolean developerMode = false;
	
	//DEVELOPER VARIABLES
	private int devX,devY;
	private int devTileID = 0;
	
	private Point viewportPivot = new Point (16*4,16*4);	//There is a 4 tile thick border around the screen
	private Point viewportSize;
	
	int fps, framesCounted = 0;
	long lastFPSUpdate = 0;
	
	public Frame(Game game) {
		this.setPreferredSize(new Dimension(game.WIDTH * SCALE,game.HEIGHT * SCALE));
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
		keyMapping.put(KeyEvent.VK_ESCAPE, 	Keys.DEVELOPER);

		viewportSize = new Point (game.WIDTH - (8*16), game.HEIGHT - (8*16));
		System.out.println("Movable bounds: (" + viewportSize.getX() + ", " + viewportSize.getY() + ")");
		
	}

	@Override
	public void paint(Graphics gs) {
		long time = System.currentTimeMillis();
		Graphics2D g = (Graphics2D)gs;

		//Scale and clear the screen
		g.scale(SCALE, SCALE);
		g.setColor(Color.black);
		g.fillRect(0, 0, game.WIDTH, game.HEIGHT);
		AffineTransform original = g.getTransform();
		
		
		//Call update before rendering
		game.scene.update();
		
		//Adjust the viewport if necessary
		//This is some touchy math, so I'd advise not changing this
		if (game.scene.player.getLastPixelX() >= viewportPivot.x + viewportSize.x ) { viewportPivot.x = game.scene.player.getLastPixelX() - viewportSize.x;}
		if (game.scene.player.getLastPixelX() < viewportPivot.x ) { viewportPivot.x = game.scene.player.getLastPixelX(); }
		if (game.scene.player.getLastPixelY() >= viewportPivot.y + viewportSize.y ) { viewportPivot.y = game.scene.player.getLastPixelY() - viewportSize.y;}
		if (game.scene.player.getLastPixelY() < viewportPivot.y ) { viewportPivot.y = game.scene.player.getLastPixelY(); }
		
		g.translate(-viewportPivot.x + (4*16), -viewportPivot.y + (4*16));
		
		//Calculate how much of the world to draw
		Rectangle viewport = new Rectangle((viewportPivot.x/16)-5,(viewportPivot.y/16)-5,-1,-1);
		viewport.width = (viewportSize.x/16) + 10;
		viewport.height = (viewportSize.y/16) + 10;
		
		//Draw everything if in developer mode
		if (developerMode) {
			viewport = new Rectangle(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		}
		
		game.scene.draw(g, viewport);

		if (isKeyPressed(Keys.DEVELOPER)) {
			developerMode = !developerMode;	//Toggle developer mode
			unPressKey(Keys.DEVELOPER);
			devX = game.scene.player.getX();
			devY = game.scene.player.getY();
			
			//if they turned off dev mode, save the map
			if (!developerMode) {
				game.scene.save();
			}
		}

		if (developerMode) {
			g.setColor(new Color(255,100,100,100));
			g.fillRect(devX * 16, devY * 16, 16, 16);
			g.setColor(Color.red);
			g.drawRect(devX * 16, devY * 16, 16, 16);

			if (isKeyPressed(Keys.UP)) 		{ devY--; unPressKey(Keys.UP);}
			if (isKeyPressed(Keys.DOWN)) 	{ devY++; unPressKey(Keys.DOWN);}
			if (isKeyPressed(Keys.LEFT)) 	{ devX--; unPressKey(Keys.LEFT);}
			if (isKeyPressed(Keys.RIGHT)) 	{ devX++; unPressKey(Keys.RIGHT);}

			try {
				
				//CHANGE TILE (prev)
				if (isKeyPressed(Keys.L)) {
					devTileID--;
					if (devTileID < 0) { devTileID++; }
					unPressKey(Keys.L);
				}
				
				//CHANE TILE (Next)
				if (isKeyPressed(Keys.R)) {
					devTileID++;
					if (devTileID >= TileProperties.PASSABLE.size()) { devTileID--; }
					unPressKey(Keys.R);
				}
				
				if (isKeyPressed(Keys.A)) {
					game.scene.map[devY][devX] = new Tile(devTileID,devX,devY);
				}
				
				//Create the tile and draw it up and left from our changer area
				Tile t = new Tile(devTileID,devX-1, devY-1);
				t.draw(g);
				g.setColor(Color.white);
				g.drawRect((devX-1) * 16, (devY-1) * 16, 16,16);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			if (isKeyPressed(Keys.UP)) 		{game.scene.player.move(Direction.UP);}
			if (isKeyPressed(Keys.DOWN)) 	{game.scene.player.move(Direction.DOWN);}
			if (isKeyPressed(Keys.LEFT)) 	{game.scene.player.move(Direction.LEFT);}
			if (isKeyPressed(Keys.RIGHT)) 	{game.scene.player.move(Direction.RIGHT);}
		}
		
		//Restore transform to draw overlay stuff
		g.setTransform(original);
		if (developerMode) {
			//Draw fps
			g.drawString(fps + "fps",10,20);
		} else {
			Sprites.LOGO.draw(g, 8, 0);
		}

		framesCounted++;
		if (lastFPSUpdate < System.currentTimeMillis() - 1000) {
			fps = framesCounted;
			framesCounted = 0;
			lastFPSUpdate = System.currentTimeMillis();
		}
		
		try {
			long endTime = System.currentTimeMillis();
			Thread.sleep(20-(endTime-time));
		} catch (Exception e) {
			System.err.println("Can't sleep");
		}
		repaint();
	}

	public boolean isKeyPressed(Keys k) {
		return keyMap[k.ordinal()];
	}

	public void unPressKey(Keys k) {
		keyMap[k.ordinal()] = false;
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
