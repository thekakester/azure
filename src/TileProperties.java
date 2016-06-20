import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TileProperties {
	public static final ArrayList<Boolean> PASSABLE;

	static {
		PASSABLE = new ArrayList<Boolean>();

		String propertiesFile = "assets/tiles.property";
		try {
			Scanner scanner = new Scanner(TileProperties.class.getResourceAsStream(propertiesFile));
			while (scanner.hasNextInt()) {
				PASSABLE.add(scanner.nextInt() == 1);
			}
			scanner.close();
			
		} catch (Exception e) {
			System.err.println("Could not load tile properties");
			e.printStackTrace();
		}
	}
}
