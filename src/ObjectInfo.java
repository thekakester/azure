import java.awt.Graphics;

public class ObjectInfo {
	int id;
	boolean passable;
	boolean shift;
	String name;
	Sprite sprite;
	int zIndex;
	
	public ObjectInfo(int id, Sprite sprite, boolean passable, boolean shift, int zIndex, String name) {
		this.id = id;
		this.sprite = sprite;
		this.passable = passable;
		this.shift = shift;
		this.name = name;
		this.zIndex = zIndex;
	}
	
	public void draw(Graphics g, int x, int y) {
		sprite.draw(g, x, y, false);	//Don't update the animation
	}
}
