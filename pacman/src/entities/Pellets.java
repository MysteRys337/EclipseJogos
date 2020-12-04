package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import world.Camera;

public class Pellets extends Entity{

	public Pellets(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.orange);
		g.fillRect((int)(x - Camera.x) + 7, (int)(y - Camera.y) + 7, width, height);
	}

}
