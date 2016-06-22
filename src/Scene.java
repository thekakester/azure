import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Scene {
	public Tile[][] map;
	int tileWidth = 0;	//Set from map file
	int tileHeight = 0;	//Set from map file
	Entity player;
	ArrayList<Entity> entities = new ArrayList<Entity>();
	ArrayList<Entity> objects = new ArrayList<Entity>();	//non moving entities (objects.png)
	private final String mapPath;

	public Scene (Game game, String path) {
		mapPath = "assets/" + path+"_map.scene";
		
		player = new Entity(this, Sprites.PLAYER, 4, 4);
			
		try {
			//See if there's a user file
			File userFile = new File(mapPath);
			
			Scanner scanner;
			if (userFile.exists()) {
				scanner = new Scanner(userFile);
			} else {
				//Use the preset
				scanner = new Scanner(Scene.class.getResourceAsStream(mapPath));
			}
			
			/**************
			 * MAP        *
			 **************/
			
			//Read the dimensions
			map = new Tile[scanner.nextInt()][scanner.nextInt()];

			//Read em in!
			for (int row = 0; row < map.length; row++) {
				for (int col = 0; col < map[row].length; col++) {
					int tileID = scanner.nextInt();
					map[row][col] = new Tile(tileID, col, row);
				}
			}

			tileWidth = map[0][0].sprite.getWidth();
			tileHeight = map[0][0].sprite.getHeight();

			/************
			 * Objects *
			 ************/
			
			int count = scanner.nextInt();
			
			//Read in this many entities
			for (int i = 0; i < count; i++) {
				//ID, x, y
				int id 				= scanner.nextInt();
				int x  				= scanner.nextInt();
				int y  				= scanner.nextInt();
				
				Entity e = new Object(this,id, x,y);
				e.sprite.setAnimation(id);
				e.idle = true;	//TODO replace this with moving entity
				
				//Set the attributes
				
				objects.add(e);
			}
			
			//Entity upvote = new Entity(this,Sprites.UPVOTE,14,9);
			Entity upvote = new Entity(this,Sprites.UPVOTE,11,4);
			upvote.sprite.setAnimation(0);
			upvote.idle = true;
			
			Entity downvote = new Entity(this,Sprites.UPVOTE,20,4);
			downvote.sprite.setAnimation(1);
			downvote.idle = true;
			
			entities.add(new Slime(this, 12, 2));
			entities.add(new Slime(this, 12, 7));
			entities.add(new Slime(this, 7, 4));
			entities.add(new Spooder(this, 2, 7));
			entities.add(new Female(this, 10,4));
			entities.add(upvote);
			entities.add(downvote);
			
			entities.add(player);
			
		} catch (Exception e) {
			System.err.println("Failed to load map: " + mapPath);
			e.printStackTrace();
		}
	}

	public void update() {
		for (Entity e : objects) {
			e.update();
		}
		
		for (Entity e : entities) {
			e.update();
		}
		
	}
	
	public void draw(Graphics g, Rectangle viewport) {
		//Calculate where to draw based on viewport
		//Draw a 5 tile buffer around it all
		int startRow = viewport.y;
		int startCol = viewport.x;
		int endRow   = startRow + viewport.height;
		int endCol   = startCol + viewport.width;
		
		//Snap in bounds
		startRow = startRow < 0 ? 0 : startRow;
		startCol = startCol < 0 ? 0 : startCol;
		endRow = endRow >= map.length ? map.length    - 1 : endRow;
		endCol = endCol >= map.length ? map[0].length - 1 : endCol;
		
		for (int row = startRow; row <= endRow; row++) {
			for (int col = startCol; col < endCol; col++) {
				map[row][col].draw(g);
			}
		}
		for (Entity e : objects) {
			e.draw(g);
		}
		for (Entity e : entities) {
			e.draw(g);
		}
	}

	public boolean isTilePassable(int x, int y) {
		if (y < 0 || y >= map.length) { return false; }
		if (x < 0 || x >= map[y].length) { return false; }
		return map[y][x].passable;
	}
	
	public void save() {
		try {
			File f = new File("assets");
			f.mkdirs();
			
			PrintWriter pw = new PrintWriter(mapPath);
			
			pw.println(map.length + " " + map[0].length);
			for (int row = 0; row < map.length; row++) {
				for (int col = 0; col < map[row].length; col++) {
					pw.print(map[row][col].tileID + " ");
				}
				pw.println();
			}
			
			pw.println();
			pw.println();
			
			//Save objects
			pw.println(objects.size());
			
			for (Entity e : objects) {
				pw.print(e.sprite.getAnimation() + " ");
				pw.print(e.getX() + " ");
				pw.println(e.getY() + " ");
			}
			
			pw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
