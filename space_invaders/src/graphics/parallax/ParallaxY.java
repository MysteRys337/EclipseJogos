package graphics.parallax;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import attributes.Timer;
import world.Context;

public class ParallaxY {

	private BufferedImage background;
	private BufferedImage backgroundCopy;
	
	private double speed;
	
	private int y2;
	private int y;
	
	private int height;
	private Timer timer;

	public ParallaxY(String background,double speed,int height) {
		this.y  = 0;
		this.y2 = height;
		
		this.height = height;
		
		this.speed = speed;
		timer = new Timer(0);
		
		try {
			this.background = ImageIO.read(getClass().getResource(background));
			this.backgroundCopy =  ImageIO.read(getClass().getResource(background));
			
		} catch(Exception e ) {e.printStackTrace();}
	}
	
	public ParallaxY(String background,double speed,int height,int timer) {
		this(background, speed, height);
		this.timer = new Timer(timer);
	}
	
	public void tick(Context ctx) {
		
		timer.tick();
		if(timer.is_stopped()) {
			y-=speed;
			if(y+height <= 0)
				y = height;
			y2-=speed;
			if(y2+height<= 0) {
				y2 = height;
			}
			timer.reset();
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(background,0,y,null);
		g.drawImage(backgroundCopy,0,y2,null);
	}
}
