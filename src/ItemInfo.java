import java.awt.Graphics;

/**This class exists to make sure that we don't have duplicate redundant data.
 * An Item is simply a reference to this class.
 * @author Mitch
 *
 */
public class ItemInfo {
	int id;
	String name;
	Sprite sprite;
	
	/**THis should only be called by Items.class
	 * All others should use the copy constructor
	 * @param itemID
	 */
	public ItemInfo(int itemID, Sprite sprite, String name, String description) {
		id = itemID;
		this.sprite = sprite;
		this.name = name;
	}
	
	public ItemInfo(ItemInfo clone) {
		this.id = clone.id;
	}
	
	public void draw(Graphics g, int x, int y) {
		sprite.draw(g, x, y);
	}
}
