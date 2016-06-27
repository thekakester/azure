import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class PauseScreen {
	private Sprite sprite;
	private Rectangle bounds;
	private Rectangle inventoryBounds;
	Font font;
	private int inventoryWidth;	//Used for up/downs
	private int inventoryHeight;
	int selectedItemID = 0;

	public PauseScreen(int x, int y, int width, int height, int fontSize) {
		bounds = new Rectangle(x,y,width,height);
		sprite = new Sprite(Sprites.ITEMS);
		font = new Font("SDS_6x6", Font.PLAIN, fontSize);

		inventoryWidth = width - 2;
		inventoryHeight = 3;
		inventoryBounds = new Rectangle(1,height-inventoryHeight-1,inventoryWidth,inventoryHeight);
	}

	public void draw(Graphics2D g, Entity player) {

		int itemId = 0;
		for (int h = 0; h < bounds.height; h++) {
			for (int w = 0; w < bounds.width; w++) {
				int x = w + bounds.x;
				int y = h + bounds.y;

				//Calculate the animation to use
				int animation = 4;	//Empty space
				if (h == 0) {
					if (w == 0) 					{ animation = 0; }
					else if (w < bounds.width - 1) 	{ animation = 1; }
					else							{ animation = 2; }
				} else if (h < bounds.height - 1) {
					if (w == 0) 					{ animation = 3; }
					else if (w < bounds.width - 1) 	{ animation = 4; }
					else 							{ animation = 5; }
				} else {
					if (w == 0) 					{ animation = 6; }
					else if (w < bounds.width - 1) 	{ animation = 7; }
					else 							{ animation = 8; }
				}

				if (inventoryBounds.contains(w, h)) {
					animation = 9;
					if (itemId == selectedItemID) {
						animation = 22;	//Selected item
					}
				}

				sprite.setAnimation(animation);
				sprite.draw(g, x*16, y*16);

				if (animation == 9 || animation == 22) {
					//Draw the item
					Item i = player.getItem(itemId++);
					if (i != null) {
						i.draw(g, x*16, y*16);

						if (animation == 22) {
							Font old = g.getFont();
							g.setFont(font);
							g.setColor(Color.white);
							g.drawString(i.info.name, (bounds.x + inventoryBounds.x) * 16, (bounds.y + inventoryBounds.y)*16);
							g.setFont(old);
						}
					}
				}

			}
		}

		Font old = g.getFont();
		g.setFont(font);
		g.setColor(Color.green);
		g.drawString("Favorites", 100, 28);
		g.setFont(old);
	}

	public void moveSelection(Direction direction) {
		if (direction == Direction.LEFT) {
			selectedItemID--;
		} else if (direction == Direction.RIGHT) {
			selectedItemID++;
		} else if (direction == Direction.UP) {
			selectedItemID -= inventoryWidth;
		} else if (direction == Direction.DOWN ){
			selectedItemID += inventoryWidth;
		}

		int inventorySize = inventoryWidth * inventoryHeight;
		selectedItemID = (selectedItemID + inventorySize) % inventorySize;

		if (direction != Direction.NONE) {
			System.out.println("Selected item: " + selectedItemID);
		}
	}
}
