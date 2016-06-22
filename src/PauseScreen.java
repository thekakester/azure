import java.awt.Graphics2D;
import java.awt.Rectangle;

public class PauseScreen {
	private Sprite sprite;
	private Rectangle bounds;
	
	public PauseScreen(int x, int y, int width, int height) {
		bounds = new Rectangle(x,y,width,height);
		sprite = new Sprite(Sprites.ITEMS);
	}
	
	public void draw(Graphics2D g) {
		for (int w = 0; w < bounds.width; w++) {
			for (int h = 0; h < bounds.height; h++) {
				int x = w + bounds.x;
				int y = h + bounds.y;
				
				//Calculate the animation to use
				int animation = 4;	//Empty space
				if (h == 0) {
					if (w == 0) { animation = 0; }
					else if (w < bounds.width - 1) { animation = 1; }
					else { animation = 2; }
				} else if (h < bounds.height - 1) {
					if (w == 0) { animation = 3; }
					else if (w < bounds.width - 1) { animation = 9; }
					else { animation = 5; }
				} else {
					if (w == 0) { animation = 6; }
					else if (w < bounds.width - 1) { animation = 7; }
					else { animation = 8; }
				}
				
				sprite.setAnimation(animation);
				sprite.draw(g, x*16, y*16);
			}
		}
	}
}
