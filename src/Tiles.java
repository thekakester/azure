import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Tiles {
	
	public static TileInfo[] tile = new TileInfo[0];
	
	static {

		String propertiesFile = "assets/tiles.property";
		
		ArrayList<TileInfo> tileList = new ArrayList<TileInfo>();
		try {
			Scanner scanner = new Scanner(Tiles.class.getResourceAsStream(propertiesFile));
			int id = 0;
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(",");
				
				//Trim everything
				for (int i = 0; i < line.length; i++) {
					line[i] = line[i].trim();
				}
				
				if (line[0].startsWith("#")) { continue; }
				
				int animation = Integer.parseInt(line[0]);
				int passable = Integer.parseInt(line[1]);
				String name = line[2];
				
				Sprite sprite = new Sprite(Sprites.TILES);
				sprite.setAnimation(animation);
				
				TileInfo info = new TileInfo(id++, passable==1, sprite, name);
				tileList.add(info);
			}
			scanner.close();
			
			//Convert to array
			tile = tileList.toArray(tile);
			System.out.println(tile.length + " tiles loaded");
		} catch (Exception e) {
			System.err.println("Could not load tile properties");
			e.printStackTrace();
		}
	}

	public static void nextFrame() {
		for (TileInfo ti : tile) {
			ti.sprite.advanceFrame();
		}
	}
}
