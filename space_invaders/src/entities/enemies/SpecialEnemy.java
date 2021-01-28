package entities.enemies;

import graphics.sprite.Sprite;
import scenes.Normal;
import world.Context;

public class SpecialEnemy extends Enemy {

	public SpecialEnemy(int x, int y, int width, int height, double speed, Sprite sprite) {
		super(x, y, width, height, speed, sprite);
		this.points_for_destruction = 50;
	}
	
	public void tick(Context context) {
		
		x+=speed;
		
		if(x+speed > context.getScreenWIDTH() || x+speed < 0) 
			Normal.removeEntity(this);
		
		this.detectCollisionWithBullets();
	}

}
