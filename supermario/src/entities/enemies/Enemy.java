package entities.enemies;

import java.awt.Graphics;

import entities.AnimatedEntity;
import scenes.Normal;
import world.Context;
import world.World;

public class Enemy extends AnimatedEntity{
	
	private char walkingCycle;
	private boolean alive;
	
	public Enemy(int x, int y, int width, int height, double speed, String name) {
		super(x, y, width, height, speed, name);
		alive = true;
		
		int rng = gerador.nextInt(1);
		if(rng == 1) 
			walkingCycle = 'R';
		else 
			walkingCycle = 'L';
		
		
	}
	
	public void kill() {
		this.alive = false;
	}
	
	public void tick(Context context) {
		
		if(!alive) {
			Normal.entities.remove(this);
			Normal.enemies.remove(this);
		}
		
		movement();	
		
		animation();
	}
	
	public void movement() {
		
		moved = true;
		if (World.isFree(this.getX(), this.getY() + this.getSpeed(),maskW,maskH)) {
			
			y += speed;
		}
		
		//if(calculateDistance(x,y,Normal.player.getX(),Normal.player.getY()) < 100) {
			
			if(walkingCycle == 'R') {
				if(World.isFree(this.getX() + 16, this.getY() + this.getSpeed(),maskW,maskH)) {
					
					walkingCycle = 'L';
				}
				else if(World.isFree(this.getX() + this.getSpeed(),this.getY(),maskW,maskH)) {
					
					x+=speed;
				}
			}
			else if(walkingCycle == 'L') {
				if(World.isFree(this.getX() - 16, this.getY() + this.getSpeed(),maskW,maskH)) {
					
					walkingCycle = 'R';
				}
				else if(World.isFree(this.getX() - this.getSpeed(),this.getY(),maskW,maskH)) {
					
					x-=speed;
				}
			}
			
	  /*/} else {
			
			if (getX() < Normal.player.getX() && World.isFree((int)(x+speed),(int)y,width,height)) {
					
				x += speed;
			}
			else if ((int)x > Normal.player.getX() && World.isFree((int)(x-speed),(int)y,width,height)) {
				
				x -= speed;
			}
		}*/
	}
	
	public void render(Graphics g) {
		this.sprite = sprites[index+(dir*8)].getBufferedImage();
		
		super.render(g);
	}

}
