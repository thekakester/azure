import java.util.ArrayList;
import java.util.Scanner;

public class ObjectProperties {
	public static final ArrayList<Boolean> PASSABLE;
	public static final ArrayList<Boolean> SHIFT;	//Slight upward shift for 3D effect

	static {
		PASSABLE 	= new ArrayList<Boolean>();
		SHIFT 		= new ArrayList<Boolean>();

		String propertiesFile = "assets/objects.property";
		try {
			Scanner scanner = new Scanner(TileProperties.class.getResourceAsStream(propertiesFile));
			while (scanner.hasNextInt()) {
				PASSABLE.add(scanner.nextInt() == 1);
				SHIFT.add(scanner.nextInt() == 1);
			}
			scanner.close();
			
		} catch (Exception e) {
			System.err.println("Could not load object properties");
			e.printStackTrace();
		}
	}
}
