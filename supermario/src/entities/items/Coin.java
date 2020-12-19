package entities.items;

import java.awt.Graphics;

import entities.AnimatedEntity;
import scenes.Normal;
import world.Context;

public class Coin extends AnimatedEntity{

	public Coin(int x, int y, int width, int height, double speed, String name) {
		
		super(x, y, width, height, speed, name);
		this.maxIndex = 5;
		
	}
	
	public void tick(Context ctx) {
		
		moved = true;
		animation();
	}
	
	public void destroy() {
		Normal.entities.remove(this);
		Normal.coins.remove(this);
	}

	public void render(Graphics g) {
		int currentIndex = (index+(dir*8));
		
		this.sprite = sprites[currentIndex].getBufferedImage();
		
		super.render(g);
	}
	
}
