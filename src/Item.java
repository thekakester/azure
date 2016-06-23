import java.awt.Graphics;

public class Item {
	public void draw(Graphics g, int x, int y) {
		Sprites.ITEMS.setAnimation(20);
		Sprites.ITEMS.draw(g, x, y);
	}
}
