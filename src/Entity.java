import java.awt.Graphics;

public class Entity {
	private int x,y,oldX,oldY;
	private float tween = 0;
	public Sprite sprite;
	
	public Entity(Sprite sprite, int x, int y) {
		this.x = this.oldX = x;
		this.y = this.oldY = y;
		
		this.sprite = new Sprite(sprite);
	}
	
	public void draw(Graphics g) {
		sprite.draw(g, x * 16, y * 16);
	}
}
