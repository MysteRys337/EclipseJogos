package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import world.Camera;
import world.World;
import zelda.Game;

public class Bullet extends Entity{

	private double dx,dy;
	private double speed;
	
	private int life;
	private int damage;
	
	public int getDamage() {
		
		return damage;
	}
	
	public Bullet(int x, int y, int width, int height, BufferedImage sprite,double dx,double dy,int damage,int spread) {
		
		super(x, y, width, height, null);
		this.dx = dx;
		this.dy = dy;
		this.speed = 4.0;
		this.life = 60;
		this.damage = damage;
		setMask(1,4,2,2);
	}

	public void tick() {
		
		if(World.isFree((int)(x+(dx*speed)) ,(int)(y+(dy*speed)), 2, 2, z)) {
				
				x+=dx*speed;
				y+=dy*speed;
		}
		else {
			
			Game.bullets.remove(this);
			World.generateParticle(25, (int)x, (int)y);
			return;
		}
		life--;
		
		if ( life < 0 ) {
			
			Game.bullets.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval((int)(this.getX() - Camera.x),(int)(this.getY() - Camera.y + 3), 3,3);
	}
}
