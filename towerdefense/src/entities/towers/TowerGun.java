package entities.towers;

import java.awt.Color;
import java.awt.Graphics;

import entities.Entity;
import entities.enemies.Enemy;
import graphics.sprite.Sprite;
import scenes.Normal;
import world.Camera;
import world.Context;

public class TowerGun extends Entity{
	
	private int xTarget;
	private int yTarget;
	
	private boolean atacking;

	public TowerGun(int x, int y, int width, int height, double speed, Sprite sprite) {
		super(x, y, width, height, speed, sprite);
		atacking = false;
	}
	
	public void tick(Context ctx) {
		Enemy tmp = null;
		for(Enemy e : Normal.enemies) 
			if(Entity.calculateDistance(e.getX(), e.getY(), this.getX(), this.getY()) < 40) 
				tmp = e;
		
			
		if (tmp != null) {
			atacking = true;
			xTarget = tmp.getX();
			yTarget = tmp.getY();
			tmp.decreaseLife(1);
			
		} else {
			atacking = false;
			
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
		
		if(atacking) {
			
			g.setColor(Color.red);
			g.drawLine(getX() + 6 - Camera.x, getY() + 6 - Camera.y, xTarget + 6 - Camera.x, yTarget + 6 - Camera.y);
		}
	}
 
}
