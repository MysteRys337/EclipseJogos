package zelda;

import java.io.*;
import javax.sound.sampled.*;
public class Sound {

	public static String musicBackground = "res/music.wav";
	public static String pistolFire = "res/fire.wav";
	public static String pistolReload = "res/reload.wav";
	public static String playerDamage = "res/damage.wav";
	public static String enemyDamage = "res/damage2.wav";
	
	public void play(String path) {
		
		try {
			
			File musicPath = new File(path);
			
			if(musicPath.exists()) {
				
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
			}
			else {
				
				System.out.println("Can\'t find file");
			}
			
		}
		catch (Exception ex) {
			
			ex.printStackTrace();
		}
		
	}
	
	public void loopMusic(String path) {
		
		try {
			
			System.out.println(musicBackground);
			File musicPath = new File(path);
			
			if(musicPath.exists()) {
				
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			else {
				
				System.out.println("Can\'t find file");
			}
			
		}
		catch (Exception ex) {
			
			ex.printStackTrace();
		}
		
	}
	

}
