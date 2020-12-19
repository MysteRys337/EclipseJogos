package entities.player;

import java.awt.Graphics;

import entities.AnimatedEntity;
import entities.Entity;
import entities.enemies.Enemy;
import graphics.UI;
import scenes.Normal;
import world.Camera;
import world.Context;
import world.World;

public class Player extends AnimatedEntity{

	private boolean transformed;
	private boolean isJumping;
	private boolean isFalling;
	
	private byte points;
	
	private byte jumpHeight;
	private byte jumpFrames;
	private byte jumpSpeed;
	
	private UI ui;

	public Player(int x, int y, int width, int height, double speed,String name,UI ui) {
		super(x, y, width, height, speed, name);
		this.depth = 1;
		
		this.ui = ui;
		
		this.frames    = 0;
		this.maxFrames = 7;
		this.index     = 0;
		this.minIndex  = 0;
		this.maxIndex  = 2;
		this.dir       = 0;
		
		this.jumpHeight = 50;
		this.jumpSpeed  = 2;
		
		this.moved = this.transformed = this.isFalling = false;
		
		setMask(4,1,7,14);
		
	}
	
	public void addPoints(int points) {
		this.points += points;
	}

	public void tick(Context ctx,PlayerAction action) {
		
		movement(action);
		
		Normal.playerCollision();
		
		animation();
		
		updateCamera(ctx);
	}
	
	public void movement(PlayerAction action) {
		
		moved = false;
		
		if(action.getRight() && World.isFree(this.getX() + this.getSpeed(),this.getY(),maskW,maskH)) {
			
			moved = true;
			x+=speed;
			dir = 0;
		}
		else if(action.getLeft() && World.isFree(this.getX() - this.getSpeed(),this.getY(),maskW,maskH)) {
			
			moved = true;
			x-=speed;
			dir = 1;
		}
		if(action.getAction()) {
			
			if(!World.isFree(this.getX(), this.getY() + 1, maskW, maskH)) {
				
				isJumping = true;
			} else {
				
				action.setAction(false);
			}
			
		} else if (World.isFree(this.getX(), this.getY() + this.getSpeed(),maskW,maskH) && !isJumping) {
			
			y += speed;
			isFalling = true;
			for (Enemy e : Normal.enemies) {
				if(Entity.isColidding(this, e)) {
					e.kill();
					isJumping = true;
					points += 10;
				}
			}
		}
		else {
			isFalling = false;
		}
		
		jumping(action);
		
	}
	
	public void jumping(PlayerAction action) {
		
		if(isJumping) {
			
			if(World.isFree(this.getX(), this.getY() - jumpSpeed, maskW, maskH)) {
				
				y-=jumpSpeed;
				jumpFrames+=jumpSpeed;
				if(jumpFrames == jumpHeight) {
					isJumping = false;
					action.setAction(false);
					jumpFrames = 0;
				}
				
			} else {
				
				isJumping  = false;
				action.setAction(false);
				jumpFrames = 0;
			}
		}
	}
	
	public void animation() {
		
		if(transformed) {
			minIndex = 4;
			maxIndex = 6;
			
		} else {
			minIndex = 0;
			maxIndex = 2;
			
		}
		
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
		Camera.x = Camera.clamp(this.getX() - (ctx.getScreenWIDTH()/2),0,(ctx.getWorldWIDTH()*16)   - ctx.getScreenWIDTH());
		Camera.y = Camera.clamp(this.getY() - (ctx.getScreenHEIGHT()/2),0,(ctx.getWorldHEIGHT()*16) - ctx.getScreenHEIGHT());	
		
	}
	
	public void render(Graphics g) {
		int currentIndex = ((isFalling || isJumping) ? ((transformed) ? 7+(dir*8) : 3+(dir*8)) : index+(dir*8));
		
		this.sprite = sprites[currentIndex].getBufferedImage();
		
		ui.render(g,points);
		
		super.render(g);
	}
	
}
