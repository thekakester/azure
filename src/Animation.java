import java.awt.Point;
import java.util.ArrayList;

public class Animation {
	public final int frames;
	public final int rate;
	public final boolean loops;
	public final int width;
	public final int height;
	public final ArrayList<Point> positions;
	
	public Animation(int frames, int rate, int loops, int width, int height) {
		positions = new ArrayList<Point>(frames);
		this.frames = frames;
		this.rate = rate;
		this.loops = (loops != 0);
		this.width = width;
		this.height = height;
	}
	
	public void addFrame(int x, int y) {
		positions.add(new Point(x,y));
	}
	
	public Point getFrame(int i) {
		return positions.get(i);
	}
}
