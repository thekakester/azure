import java.awt.Graphics;

public class Tile {
	public final Sprite sprite;
	public boolean passable = true;
	public final int x,y;
	
	public Tile(Sprite sprite, int x, int y, boolean passable) {
		this.sprite = sprite;
		this.passable = passable;
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g) {
		sprite.draw(g, x*sprite.getWidth(), y*sprite.getHeight());
	}
}
