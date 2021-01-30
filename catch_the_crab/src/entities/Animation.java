package entities;

import java.awt.Graphics;

import attributes.Timer;
import scenes.Normal;
import world.Context;

public class Animation extends MultipleSpriteEntity{

	private Timer animationLife;
	public Animation(int x, int y, String name) {
		super(x, y, 48, 48, 0, name);
		this.animationLife = new Timer(maxIndex*maxFrames);
		
	}
	
	public void tick(Context ctx) {
		moved = true;
		animationLife.tick();
		if(animationLife.is_stopped()) 
			Normal.removeEntity(this);
		
		animation();
	}
	
	public void animation() {
		
		if(moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if ( index > maxIndex) 
					index = minIndex;
				
			}
		}
		else {
			index = minIndex;
		}
	}
	
	public void render(Graphics g) {
		sprite = sprites[index];
		super.render(g);
	}

}
