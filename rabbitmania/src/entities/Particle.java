package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import world.Camera;
import zelda.Game;

public class Particle extends Entity {
	
	public byte life = 10,speed = 2;
	public double dx,dy;

	public Particle(int x, int y, int width, int height, BufferedImage sprite) {
		
		super(x, y, width, height, sprite);
		
		dx = Game.gerador.nextGaussian();
		dy = Game.gerador.nextGaussian();
		
	}
	
	public void tick() {
		
		x+=dx*speed;
		y+=dy*speed;
		life--;
		if(life <= 0) {
			
			Game.entities.remove(this);
		}
		
		
	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.ORANGE);
		g.fillRect((int)(x - Camera.x), (int)(y - Camera.y), width, height);
	}
}
