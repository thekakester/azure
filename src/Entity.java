import java.awt.Graphics;

public class Entity {
	private int x,y,oldX,oldY;
	private float tween = 0;
	public final Sprite sprite;
	private final Scene scene;
	public boolean idle;
	private int tweenX,tweenY;
	protected int shift = 5;	//5 by default, gives 3D feel.  Can be changed
	protected boolean passable = false;	//False by default, can be overridden

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

		if (!idle) {
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
		}

		tweenX = Math.round((x*16*tween) + (oldX * 16 *(1-tween)));
		tweenY = Math.round((y*16*tween) + (oldY * 16 *(1-tween)));
		
		sprite.draw(g, tweenX, tweenY - shift);	//-5 to give it that lifted feel
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
				if (!scene.hasBlockingEntity(x+deltaX,y+deltaY)) {
					oldX = x;
					oldY = y;
					x += deltaX;
					y += deltaY;
					tween = 0;
				}
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**This is based on the last position it was drawn
	 * This will return the pixel coordinates of the screen, not grid coords
	 * @return
	 */
	public int getLastPixelX() {
		return tweenX;
	}
	
	/**This is based on the last position it was drawn
	 * This will return the pixel coordinates of the screen, not grid coords
	 * @return
	 */
	public int getLastPixelY() {
		return tweenY;
	}
}
