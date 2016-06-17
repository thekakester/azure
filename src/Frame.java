import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

public class Frame extends Component {
	public static int SCALE = 4;
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Azure");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(240 * Frame.SCALE, 160 * Frame.SCALE);	//Gameboy advance resolution
		frame.add(new Frame());
		frame.setVisible(true);
	}
	
	@Override
	public void paint(Graphics gs) {
		Graphics2D g = (Graphics2D)gs;
		g.scale(SCALE, SCALE);
		
		Sprites.LOGO.draw(g, 10, 10);
		
		try {
			Thread.sleep(17);
		} catch (Exception e) {
			System.err.println("Can't sleep");
		}
		repaint();
	}
}
