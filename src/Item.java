import java.awt.Graphics;

public class Item {
	ItemInfo info;	//A reference to the details of this item
	
	public Item(ItemInfo info) {
		if (info == null) { throw new RuntimeException("Can't create null item"); }
		this.info = info;	
	}
	
	public void draw(Graphics g, int x, int y) {
		info.draw(g, x, y);
	}
}
