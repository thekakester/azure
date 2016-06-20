import java.awt.Graphics;

public class Tile {
	public final Sprite sprite;
	public boolean passable = true;
	public final int x,y;
	public final int tileID;
	
	public Tile(int tileID, int x, int y) {
		this.sprite = new Sprite(Sprites.TILES);
		this.sprite.setAnimation(tileID);
		
		this.passable = TileProperties.PASSABLE.get(tileID);
		this.x = x;
		this.y = y;
		this.tileID = tileID;
	}
	
	public void draw(Graphics g) {
		sprite.draw(g, x*sprite.getWidth(), y*sprite.getHeight());
	}
}
