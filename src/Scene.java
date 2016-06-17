import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Scanner;

public class Scene {
	public Sprite[][] map;
	int tileWidth = 0;	//Set from map file
	int tileHeight = 0;	//Set from map file
	Entity player = new Entity(Sprites.PLAYER, 4, 4);
	ArrayList<Entity> entities = new ArrayList<Entity>();

	public Scene (String path) {
		String mapPath = "assets/" + path+"_map.scene";

		try {
			Scanner scanner = new Scanner(Scene.class.getResourceAsStream(mapPath));

			//Read the dimensions
			map = new Sprite[scanner.nextInt()][scanner.nextInt()];

			//Read em in!
			for (int row = 0; row < map.length; row++) {
				for (int col = 0; col < map[row].length; col++) {
					map[row][col] = new Sprite(Sprites.TILES);
					map[row][col].setAnimation(scanner.nextInt());
				}
			}

			tileWidth = map[0][0].getWidth();
			tileHeight = map[0][0].getHeight();

			entities.add(player);
			entities.add(new Slime(12, 2));
			entities.add(new Slime(12, 7));
			entities.add(new Slime(7, 4));
			entities.add(new Spooder(2, 7));
			
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
				map[row][col].draw(g,row*tileHeight,col*tileWidth);
			}
		}
		for (Entity e : entities) {
			e.draw(g);
		}
	}
}
