import java.util.ArrayList;
import java.util.Scanner;

public class Items {
	
	public static ItemInfo[] item = new ItemInfo[0];
	
	/**Find an item by its name.  This is slow!  Avoid using!
	 * Item name must be exact!  (Case insensitive)
	 * @param name The name of the item
	 */
	public static ItemInfo findByName(String name) {
		for (int i = 1; i < item.length; i++) {
			if (item[i].name.equalsIgnoreCase(name)) {
				return item[i];
			}
		}
		return null;
	}
	
	static {
		ArrayList<ItemInfo> itemsList = new ArrayList<ItemInfo>();
		
		//The order of these matter.  Item 1 is the first in the list
		//Item 0 means null
		int id = 1;
		itemsList.add(null);
		
		//Add the items from items.property
		Scanner scanner = new Scanner(Items.class.getResourceAsStream("assets/items.property"));
		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().trim().split(",");
			
			//trim everything to avoid chaos
			for (int i = 0; i < line.length; i++) {
				line[i] = line[i].trim();
			}
			
			if (line[0].startsWith("#")) { continue; }	//Skip comments
			
			//Read the data in!
			Sprite sprite = new Sprite(Sprites.ITEMS);
			sprite.setAnimation(Integer.parseInt(line[0]));
			
			String name = line[1];
			String description = line[2];
			
			itemsList.add(new ItemInfo(id++,sprite,name,description));
		}
		
		//Turn into an array
		item = itemsList.toArray(item);
		System.out.println(item.length-1 + " items loaded");
	}
}
