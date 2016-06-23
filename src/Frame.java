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
	public static float SCALE = 3;
	public Game game;
	private boolean keyMap[] = new boolean[11];
	private HashMap<Integer,Keys> keyMapping = new HashMap<Integer,Keys>();
	private int developerMode = 0;	//0=none, 1=tiles, 2=objects 
	private boolean paused = false;
	private PauseScreen pauseScreen;
	private MessageBox messageBox;


	//DEVELOPER VARIABLES
	private int devX,devY;
	private int devTileID = 0;

	private Point viewportPivot = new Point (16*4,16*4);	//There is a 4 tile thick border around the screen
	private Point viewportSize;

	int fps, framesCounted = 0;
	long lastFPSUpdate = 0;

	public Frame(Game game) {
		this.setPreferredSize(new Dimension(game.WIDTH * (int)SCALE,game.HEIGHT * (int)SCALE));
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

		pauseScreen = new PauseScreen(1, 1, 13, 8, 7);
		messageBox  = new MessageBox(0,8,15,2,7);
	}

	@Override
	public void paint(Graphics gs) {
		long time = System.currentTimeMillis();
		Graphics2D g = (Graphics2D)gs;

		//SCALE = SCALE * 0.995f;

		//Scale and clear the screen
		g.scale(SCALE, SCALE);
		g.setColor(Color.black);
		g.fillRect(0, 0, game.WIDTH, game.HEIGHT);
		AffineTransform original = g.getTransform();

		if (isKeyPressed(Keys.START)) {
			paused = !paused;
			Sprites.LOGO.restartAnimation();
			unPressKey(Keys.START);
		}

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
		if (developerMode > 0) {
			viewport = new Rectangle(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		}

		game.scene.draw(g, viewport);

		if (isKeyPressed(Keys.DEVELOPER)) {
			developerMode = (developerMode + 1 ) % 3;
			unPressKey(Keys.DEVELOPER);

			if (developerMode == 1) {
				devX = game.scene.player.getX();
				devY = game.scene.player.getY();
			}

			//if they turned off dev mode, save the map
			if (developerMode == 0) {
				game.scene.save();
			}

			devTileID = 0;	//Reset tile
		}

		if (developerMode > 0) {			
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
					unPressKey(Keys.L);
				}

				//CHANE TILE (Next)
				if (isKeyPressed(Keys.R)) {
					devTileID++;
					unPressKey(Keys.R);
				}

				//Assure we wrap properly (stay in bounds)
				if (developerMode == 1) {
					devTileID = (devTileID + TileProperties.PASSABLE.size()) % TileProperties.PASSABLE.size();
					if (isKeyPressed(Keys.A)) {
						game.scene.map[devY][devX] = new Tile(devTileID,devX,devY);
					}

					//Create the tile and draw it up and left from our changer area
					//Draw 5 dev tiles
					for (int xi = -2; xi <= 2; xi++) {
						int id = devTileID+xi;
						id %= TileProperties.PASSABLE.size();
						if (id < 0) { id += TileProperties.PASSABLE.size(); }

						Tile t = new Tile(id,devX+xi, devY-1);
						t.draw(g);
					}
				} else {
					//Do the same thing as above, but with objects

					devTileID = (devTileID + ObjectProperties.PASSABLE.size()) % ObjectProperties.PASSABLE.size();
					if (isKeyPressed(Keys.A)) {
						game.scene.objects.add(new Object(game.scene, devTileID, devX, devY));
					}
					if (isKeyPressed(Keys.B)) {
						//Delete all tiles on this spot
						boolean removed = true;
						while (removed) {
							removed = false;
							for (Entity e : game.scene.objects) {
								if (e.getX() == devX && e.getY() == devY) {
									game.scene.objects.remove(e);
									removed = true;
									break;
								}
							}
						}
					}

					//Create the tile and draw it up and left from our changer area
					//Draw 5 dev tiles
					for (int xi = -2; xi <= 2; xi++) {
						int id = devTileID+xi;
						id %= ObjectProperties.PASSABLE.size();
						if (id < 0) { id += ObjectProperties.PASSABLE.size(); }

						Object o = new Object(game.scene, id,devX+xi, devY-1);
						o.draw(g);
					}
				}

				//Draw boxes around tiles
				g.setColor(Color.gray);
				g.drawRect((devX-2) * 16, (devY-1) * 16, 5*16, 16);
				g.setColor(Color.white);
				g.drawRect((devX) * 16, (devY-1) * 16, 16,16);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			if (!paused) {
				if (!game.scene.disableMovement) {
					if (isKeyPressed(Keys.UP)) 		{game.scene.player.move(Direction.UP);}
					if (isKeyPressed(Keys.DOWN)) 	{game.scene.player.move(Direction.DOWN);}
					if (isKeyPressed(Keys.LEFT)) 	{game.scene.player.move(Direction.LEFT);}
					if (isKeyPressed(Keys.RIGHT)) 	{game.scene.player.move(Direction.RIGHT);}
				}
				if (isKeyPressed(Keys.A))		{game.scene.use(game.scene.player); unPressKey(Keys.A);}
			}
		}

		//Restore transform to draw overlay stuff
		g.setTransform(original);

		//Draw the message if there is one
		messageBox.draw(g,game.scene.getMessage());

		if (paused) {
			//Draw the pause screen
			pauseScreen.draw(g, game.scene.player);
			Sprites.LOGO.draw(g, 8, 0);
		}

		if (developerMode > 0) {
			//Draw fps
			g.drawString(fps + "fps",10,20);
			//Draw position
			g.drawString(devX + " " + devY, 10, 35);
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
			System.err.println("Can't keep up!  Framerate dropped!");
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
