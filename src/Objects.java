import java.util.ArrayList;
import java.util.Scanner;

public class Objects {
	public static ObjectInfo[] object = new ObjectInfo[0];

	static {

		String propertiesFile = "assets/objects.property";
		try {
			ArrayList<ObjectInfo> objectList = new ArrayList<ObjectInfo>();
			int id = 0;
			
			Scanner scanner = new Scanner(Tiles.class.getResourceAsStream(propertiesFile));
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(",");
				
				//Trim everything
				for (int i = 0; i < line.length; i++) {
					line[i] = line[i].trim();
				}
				
				if (line[0].startsWith("#")) { continue; }
				
				//Create an objectinfo object
				int animation		= Integer.parseInt(line[0]);
				boolean passable 	= !line[1].equals("0");
				boolean shift 		= !line[2].equals("0");
				int zIndex			= Integer.parseInt(line[3]);
				String name			= line[4];
				
				Sprite sprite = new Sprite(Sprites.OBJECTS);
				sprite.setAnimation(animation);
				
				ObjectInfo info = new ObjectInfo(id++,sprite,passable,shift,zIndex,name);
				objectList.add(info);
			}
			scanner.close();
			
			object = objectList.toArray(object);
			System.out.println("Loaded " + object.length + " objects");
		} catch (Exception e) {
			System.err.println("Could not load object properties");
			e.printStackTrace();
		}
	}
}
