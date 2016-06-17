import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {

	public static Clip INTRO_MUSIC;
	
	static {
		INTRO_MUSIC = load("intro.wav");
	}

	private static Clip load(String path) {
		path = "assets/" + path;
		
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(Audio.class.getResource(path));
			Clip c = AudioSystem.getClip();
			c.open(stream);
			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
