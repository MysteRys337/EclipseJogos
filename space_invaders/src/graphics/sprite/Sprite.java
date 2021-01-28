package graphics.sprite;

import java.awt.image.BufferedImage;

public class Sprite {

	private BufferedImage sprite;
	private String        name;
	
	public Sprite(BufferedImage sprite, String name) {
		this.sprite = sprite;
		this.name   = name;
	}
	
	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BufferedImage getBufferedImage(){
		return this.sprite;
	}
	
	public String getName() {
		return this.name;
	}
}
