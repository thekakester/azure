import java.awt.Graphics;

public class Tile {
	public final int x,y;
	final TileInfo info;
	
	public Tile(TileInfo info, int x, int y) {
		this.info = info;
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g) {
		info.draw(g,x,y);
	}

	public boolean isPassable() {
		return info.passable;
	}
}
