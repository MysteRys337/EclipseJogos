package entities.bullets;

import java.awt.Color;
import java.awt.Graphics;

import entities.Entity;
import scenes.Normal;
import world.Context;

public class Bullet extends Entity{
	
	int damage;

	public Bullet(int x, int y, int width, int height, double speed) {
		super(x, y, width, height, speed, null);
		damage = 10;
	}
	
	public void tick(Context ctx) {
		y-=speed;
		if(y-width <= 0) 
			Normal.removeEntity(this);
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(getX(), getY(), width, height);
	}

}
