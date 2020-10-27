package entities;

import java.awt.image.BufferedImage;

import zelda.Game;

public class Weapon extends Entity{
	
	private int ammo,damage,clipSize,reloadFrames,maxReloadFrames,type;
	public int delay,maxDelay;
	
	public Weapon(int x, int y, int width, int height, BufferedImage sprite,int type) {
		
		super(x, y, width, height, sprite);
		setMask(4,4,10,8);
		
		this.type = type;
		
		if(type == 0) { // 0 = Pistol
			
			clipSize = 13;
			maxReloadFrames = 120;
			damage = 3;
			maxDelay = 15;
		}
		else { // 1 = Uzi
			
			clipSize = 32;
			maxReloadFrames = 130;
			damage = 2;
			maxDelay = 3;
		}
		
	}
	
	public Weapon() {
		
		super(0,0,0,0,null);
		this.type = -1;
	}
	
	public int getType() {
		
		return type;
	}
	
	public int getAmmo() {
		
		return ammo;
	}
	
	public int getClipSize() {
		
		return clipSize;
	}
	
	public int getDamage() {
		
		return damage;
	}
	
	public void fire() {
		
		ammo--;
		delay = 0;
	}
	
	public int getReloadFrames() {
		
		return reloadFrames;
	}
	
	public int getMaxReloadFrames() {
		
		return maxReloadFrames;
	}
	
	public void resetReloadFrames() {
		
		reloadFrames = 0;
	}
	
	public int reload(int reserveAmmo) {
		
			reloadFrames++;
			if (reloadFrames == maxReloadFrames) {
				
				reloadFrames = 0;
				Game.player.isReloading = false;
				int aux = ammo - clipSize;
				ammo = (reserveAmmo > clipSize) ? clipSize : reserveAmmo;
				reserveAmmo += aux;
				if ( reserveAmmo < 0 ) {
					
					reserveAmmo = 0;
				}
			}
		
		return reserveAmmo;
	}

}
