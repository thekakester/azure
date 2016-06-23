import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MessageBox {
	Rectangle bounds;
	Font font;
	Sprite sprite;
	int fontSize;
	
	public MessageBox(int x, int y, int width, int height, int fontSize) {
		bounds = new Rectangle(x,y,width,height);
		font = new Font("SDS_6x6", Font.PLAIN, fontSize);
		sprite = new Sprite(Sprites.ITEMS);
		this.fontSize = fontSize;
	}

	public void draw(Graphics2D g, String message) {
		if (message == null) { return; }
		
		for (int w = 0; w < bounds.width; w++) {
			for (int h = 0; h < bounds.height; h++) {
				int x = w + bounds.x;
				int y = h + bounds.y;
				
				//Calculate the animation to use
				int animation = -1;	//Empty space
				if (h == 0) {
					if (w == 0) 					{ animation = 11; }
					else if (w < bounds.width - 1) 	{ animation = 12; }
					else 							{ animation = 13; }
				} else if (h < bounds.height - 1) {
					if (w == 0) 					{ animation = 14; }
					else if (w < bounds.width - 1) 	{ animation = 15; }
					else 							{ animation = 16; }
				} else {
					if (w == 0) 					{ animation = 17; }
					else if (w < bounds.width - 1) 	{ animation = 18; }
					else 							{ animation = 19; }
				}
				
				sprite.setAnimation(animation);
				sprite.draw(g, x*16, y*16);
			}
		}
		
		//Draw the text
		Font old = g.getFont();
		g.setFont(font);
		
		float offset = 1.5f;	//1.5x the font size
		for (String s : message.split("\n")) {
			g.drawString(s, (bounds.x * 16) + fontSize, (bounds.y * 16) + (offset * fontSize));
			offset += 1.1f;
		}
		g.setFont(old);
	}
}
