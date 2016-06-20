import java.awt.Graphics;

public class Entity {
	private int x,y,oldX,oldY;
	private float tween = 0;
	public Sprite sprite;
	private final Scene scene;

	public Entity(Scene scene, Sprite sprite, int x, int y) {
		this.x = this.oldX = x;
		this.y = this.oldY = y;
		this.scene = scene;

		this.sprite = new Sprite(sprite);
	}

	public void update() {
		tween += 1/16f;
		if (tween >= 1) { tween = 1; }
	}

	public void draw(Graphics g) {

		//Decide which way to go
		if (tween >= 1) {
			//Idle
			if (oldX > x) { sprite.setAnimation(5); }
			else if (oldX < x) { sprite.setAnimation(6); }
			else if (oldY > y) { sprite.setAnimation(7); }
			else { sprite.setAnimation(4); };
		} else {
			if (oldX > x) { sprite.setAnimation(1); }
			else if (oldX < x) { sprite.setAnimation(2); }
			else if (oldY > y) { sprite.setAnimation(3); }
			else { sprite.setAnimation(0); };
		}

		int xf = Math.round((x*16*tween) + (oldX * 16 *(1-tween)));
		int yf = Math.round((y*16*tween) + (oldY * 16 *(1-tween)));

		sprite.draw(g, xf, yf);
	}

	public void move(Direction d) {

		if (d == Direction.UP) {
			move(0,-1);
		} else if (d == Direction.DOWN) {
			move(0,1);
		} else if (d == Direction.LEFT) {
			move(-1,0);
		} else if (d == Direction.RIGHT) {
			move(1,0);
		}
	}

	private void move(int deltaX, int deltaY) {
		if (tween >= 1) {
			//Can we move to this location, or is there something there?
			if (scene.isTilePassable(x+deltaX,y+deltaY)) {
				oldX = x;
				oldY = y;
				x += deltaX;
				y += deltaY;
				tween = 0;
			}
		}
	}
}
