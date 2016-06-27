import java.awt.Graphics;

/**This avoids storing redundant info.
 * References should be made to this.
 * 
 * @author Mitch
 *
 */
public class TileInfo {
	public int tileID;
	public boolean passable;
	public final Sprite sprite;
	public final String name;
	
	public TileInfo(int id, boolean passable, Sprite sprite, String name) {
		this.tileID = id;
		this.passable = passable;
		this.sprite = sprite;
		this.name = name;
	}
	
	public void draw(Graphics g, int x, int y) {
		//By default, tiles don't advance frames
		sprite.draw(g, x*sprite.getWidth(), y*sprite.getHeight(), false);
	}
}
