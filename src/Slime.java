import java.util.Random;

public class Slime extends Entity{
	Random r = new Random();
	
	public Slime(Scene scene, int x, int y) {
		super(scene, Sprites.SLIME, x, y);
	}
	
	@Override
	public void update() {
		super.update();
		
		int direction = r.nextInt(1500);
		if (direction < 4) {
			this.move(Direction.values()[direction]);
		}
	}
}
