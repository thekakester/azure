import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Sprite {
	private Image image;
	private int animation = 0;
	private int frame = 0;
	public boolean play = true;
	private final String url;
	
	private ArrayList<Animation> animations = new ArrayList<Animation>();
	
	public Sprite(Sprite clone) {
		this.image = clone.image;
		this.animation = clone.animation;
		this.frame = clone.frame;
		this.animations = clone.animations;
		this.url = clone.url;
	}
	
	public Sprite(String url) {
		url = "assets/" + url;
		this.url = url;
		try {
			image = ImageIO.read(Sprite.class.getResource(url));
		} catch (Exception e) {
			System.err.println("Could not load resource: " + url);
			e.printStackTrace();
		}
		
		System.out.println("Loaded " + url);
		url += ".map";
		
		try {
			Scanner scanner = new Scanner(Sprite.class.getResourceAsStream(url));
			
			while (scanner.hasNextInt()) {
				//First line is the size of animation
				int frames = scanner.nextInt();
				int rate = scanner.nextInt();
				int loops = scanner.nextInt();
				int width = scanner.nextInt();
				int height = scanner.nextInt();
				Animation animation = new Animation(frames, rate, loops, width, height);
				int x = 0;
				int y = 0;
				System.out.println("Animation: " + rate + "fps");
				for (int i = 0; i < frames; i++) {
					x += scanner.nextInt();
					y += scanner.nextInt();
					System.out.println("	(" + x + ", " + y + ")");
					animation.addFrame(x, y);
				}
				animations.add(animation);
			}
		} catch (Exception e) {
			System.err.println("Could not load resource: " + url);
			e.printStackTrace();
		}
		System.out.println(animations.size() + " animations loaded");
	}
	
	public void setAnimation(int id) {
		//System.out.println(this + " change animation to " + id);
		if (animation == id) { return; }
		animation = id;
		frame = 0;
	}
	
	public int getWidth() {
		return animations.get(0).width;
	}
	public int getHeight() {
		return animations.get(0).height;
	}

	//Draw and go to the next frame
	public void draw(Graphics g, int x, int y) {
		Animation a = animations.get(animation);
		int frameNum = frame++ / a.rate;
		if (frameNum >= a.frames) {
			if (a.loops) {
				frame = frameNum = 0;
			} else {
				frame--; frameNum--;
			}
		}
		
		Point pos = a.getFrame(frameNum);
		g.drawImage(image,x,y,x+a.width,y+a.height,pos.x,pos.y,pos.x+a.width,pos.y+a.height,null);
	}
	
	@Override
	public String toString() {
		return "Sprite [" + url + "]";
	}

	public int getAnimation() {
		return animation;
	}

	public void restartAnimation() {
		frame = 0;
	}
}
