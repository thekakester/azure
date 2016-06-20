import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Scanner;

public class Scene {
	public Tile[][] map;
	int tileWidth = 0;	//Set from map file
	int tileHeight = 0;	//Set from map file
	Entity player;
	ArrayList<Entity> entities = new ArrayList<Entity>();

	public Scene (Game game, String path) {
		String mapPath = "assets/" + path+"_map.scene";
		
		player = new Entity(this, Sprites.PLAYER, 4, 4);
			
		try {
			Scanner scanner = new Scanner(Scene.class.getResourceAsStream(mapPath));

			//Read the dimensions
			map = new Tile[scanner.nextInt()][scanner.nextInt()];

			//Read em in!
			for (int row = 0; row < map.length; row++) {
				for (int col = 0; col < map[row].length; col++) {
					int tileID = scanner.nextInt();
					boolean passable = TileProperties.PASSABLE.get(tileID);
					Sprite tileSprite = new Sprite(Sprites.TILES);
					tileSprite.setAnimation(tileID);
					map[row][col] = new Tile(tileSprite, col, row, passable);
				}
			}

			tileWidth = map[0][0].sprite.getWidth();
			tileHeight = map[0][0].sprite.getHeight();

			entities.add(player);
			entities.add(new Slime(this, 12, 2));
			entities.add(new Slime(this, 12, 7));
			entities.add(new Slime(this, 7, 4));
			entities.add(new Spooder(this, 2, 7));
			
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
}
