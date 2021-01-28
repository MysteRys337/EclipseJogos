package items;

import java.awt.Graphics;

import graphics.sprite.Sprite;
import world.Camera;

public class Item {

	protected Sprite sprite;
	protected int x,y;
	
	public Item(int x, int y, Sprite sprite) {
		
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void render(Graphics g) {
		g.drawImage(this.sprite.getBufferedImage(), x - Camera.x, y - Camera.y, null);
	}
}
