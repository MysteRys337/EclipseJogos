package entities.player;

import java.awt.Graphics;

import attributes.Timer;
import entities.AnimatedEntity;
import entities.Entity;
import entities.bullets.Bullet;
import entities.bullets.EnemyBullet;
import graphics.UI;
import scenes.Normal;
import world.Camera;
import world.Context;

public class Player extends AnimatedEntity{
	
	private boolean isAlive;

	private UI ui;
	private int points;
	
	Timer shootDelay = new Timer(0);

	public Player(int x, int y, int width, int height, double speed,String name,UI ui) {
		super(x, y, width, height, speed, name);
		this.depth = 1;
		
		shootDelay.setNumberOfTicks(15);
		
		this.points  = 0;
		this.isAlive = true;
		
		this.ui = ui;
		
		this.frames    = 0;
		this.maxFrames = 7;
		
		this.index    = 0;
		this.minIndex = 0;
		this.maxIndex = 2;
		
		this.moved = false;
		
		setMask(5,1,7,14);
	}
	
	public boolean getLifeStatus() {
		return this.isAlive;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}

	public void tick(Context ctx,PlayerAction action) {
		
		//System.out.println(getCoordenates());
		//System.out.println(action.print());
		
		for(Bullet b:Normal.bullets) 
				if(b instanceof EnemyBullet && Entity.isColidding(this, b)) {
					Normal.gameOver();
					break;
				}
		
		
		shootDelay.tick();
		
		movement(ctx,action);
		
		action(action);
		
		animation();
		
		updateCamera(ctx);
	}
	
	public void movement(Context ctx,PlayerAction action) {
		
		moved = true;
		
		if(action.getRight()) 
			x+=speed;
		else if(action.getLeft()) 
			x-=speed;
		
		if(x >= ctx.getScreenWIDTH()) 
			x = -width;
		else if(x+width < 0)
			x = ctx.getScreenWIDTH();
		
	}
	
	public void action(PlayerAction action) {
		
		if (action.getShoot() && shootDelay.is_stopped()) {
			action.setShoot(false);
			shootDelay.reset();
			Normal.shoot(getX(), getY(), 5,false);
			
		}
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
	
	
	public void updateCamera(Context ctx) {
		
		//Configurando a camera para seguir o jogador
		//System.out.println(Camera.x + "|" + Camera.y);
		Camera.x = Camera.clamp(this.getX() - (ctx.getScreenWIDTH()/2),0,(ctx.getWorldWIDTH()*16)   - ctx.getScreenWIDTH());
		Camera.y = Camera.clamp(this.getY() - (ctx.getScreenHEIGHT()/2),0,(ctx.getWorldHEIGHT()*16) - ctx.getScreenHEIGHT());	
		
	}
	
	public void render(Graphics g) {	
		
		super.renderMask(g);
		g.drawImage(sprites[index].getBufferedImage(), getX(), getY(), null);
		
		ui.render(g,this);
	}
	
}
