package entities.enemies;

import java.awt.Graphics;

import entities.Entity;
import entities.MultipleSpriteEntity;
import scenes.Normal;
import world.Context;

public class Enemy extends MultipleSpriteEntity{

	int points_for_destruction = 15;
	
	public Enemy(int x, int y, int width, int height, double speed, String name) {
		super(x, y, width, height, speed, name);
		this.maxIndex = 1;
	}
	
	public void tick(Context context) {
		this.detectCollision();
		moved = true;
		animation();
		if(x<Normal.player.getX()) {
			x+=speed;
		}
		if(x>Normal.player.getX()) {
			x-=speed;
		}
		
		if(y<Normal.player.getY()) {
			y+=speed;
		}
		if(y>Normal.player.getY()) {
			y-=speed;
		}
		
	}
	
	protected void detectCollision() {
		if(Entity.isColidding(this, Normal.player))
			Normal.gameOver();
	}
	
	public void destroy() {
		Normal.spawnEntity(this.getX(),this.getY(),0, "smoke");
		Normal.removeEntity(this);
		Normal.player.setPoints(Normal.player.getPoints() + points_for_destruction);
	}
	

	public void render(Graphics g) {
		sprite = sprites[index];
		super.render(g);
	}

}
