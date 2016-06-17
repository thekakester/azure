import java.util.Random;

public class Spooder extends Entity{
	Random r = new Random();
	
	public Spooder(int x, int y) {
		super(Sprites.SPOODER, x, y);
	}
	
	@Override
	public void update() {
		super.update();
		
		int direction = r.nextInt(4);
		if (direction < 4) {
			this.move(Direction.values()[direction]);
		}
	}
}
