import java.awt.Graphics;
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

			
			Entity torch = new Entity(this,Sprites.OBJECTS,2,7);
			torch.sprite.setAnimation(3);
			torch.idle = true;
			
			//Entity upvote = new Entity(this,Sprites.UPVOTE,14,9);
			Entity upvote = new Entity(this,Sprites.UPVOTE,11,4);
			upvote.sprite.setAnimation(0);
			upvote.idle = true;
			
			entities.add(new Slime(this, 12, 2));
			entities.add(new Slime(this, 12, 7));
			entities.add(new Slime(this, 7, 4));
			entities.add(new Spooder(this, 2, 7));
			entities.add(new Female(this, 10,4));
			entities.add(torch);
			entities.add(upvote);
			
			entities.add(player);
			
		} catch (Exception e) {
			System.err.println("Failed to load map: " + mapPath);
			e.printStackTrace();
		}
	}

	public void update() {
		for (Entity e : entities) {
			e.update();
		}
	}
	
	public void draw(Graphics g) {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				map[row][col].draw(g);
			}
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
			
			pw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
