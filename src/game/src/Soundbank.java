package game.src;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Soundbank {

	private static HashMap<String, Audio> soundbank = new HashMap<String, Audio>();
	
	public static void loadAllSounds() {
		loadSound("shootbullet", "res/sound/shootbullet.wav");
		loadSound("bomb", "res/sound/bomb.wav");
		loadSound("bounce", "res/sound/bounce.wav");
		loadSound("bombget", "res/sound/bombget.wav");
		loadSound("gamestart", "res/sound/gamestart.wav");
		loadSound("music", "res/sound/music.wav");
	}
	
	public static void loadSound(String name, String file) {
		try {
			String ext = file.substring(file.length() - 3, file.length()).toUpperCase();
			soundbank.put(name, AudioLoader.getAudio(ext, ResourceLoader.getResourceAsStream(file)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int playSound(String name) {
		return soundbank.get(name).playAsSoundEffect(1.0f, 0.5f, false);
	}
	
	public static int playMusic(String name) {
		return soundbank.get(name).playAsMusic(1.0f, 0.7f, true);
	}
	
	public static void pauseSound(String name) {
		if (soundbank.get(name).isPlaying()) {
			soundbank.get(name).stop();
		}
	}
	
	public static Audio getSound(String name) {
		return soundbank.get(name);
	}
	
}