package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import world.Camera;
import world.World;
import zelda.Game;

public class Box extends Entity{

	private byte life,damageFrames = 90;
	private boolean isDamaged = false;
	private BufferedImage box;
	private BufferedImage box2;
	
	public Box(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		setMask(-2,0,20,18);
		life = 4;
		box = Entity.BOX;
		box2 = Entity.BOX_FEEDBACK;
	}
	
	public void tick() {
		
		if ( life <= 0 ) {
			
			if(Game.gerador.nextInt(100) > 95 ) {
				
				//System.out.println("inimigo");
				Enemy tmp = new Enemy((int)(x),(int)(y),16,16,Entity.ENEMY_EN);
				Game.entities.add(tmp);
				Game.enemies.add(tmp);
			}
			else if ( Game.gerador.nextInt(100) > 80) {
				
				//System.out.println("Municao de shotgun");
				Game.entities.add(new Ammo((int)x,(int)y,16,16,Entity.AMMO2_EN,1));
			}
			else {
				
				//System.out.println("Municao de Pistola");
				Game.entities.add(new Ammo((int)x,(int)y,16,16,Entity.AMMO_EN,0));
			}
			
			World.toFloorTile((int)x,(int)y);
			Game.entities.remove(this);
		}
		
		if (isDamaged) {
			
			damageFrames++;
			if (damageFrames >= 30) {
				
				isDamaged = false;
				damageFrames = 0;
			}
		}
		
		for (byte i = 0; i < Game.bullets.size(); i++) {
			
			Bullet b = Game.bullets.get(i);
			if (isColidding(b, this)) {
				
				World.generateParticle(25, (int)(x + 8), (int)(y + 8));
				life -= b.getDamage();
				Game.bullets.remove(b);
				isDamaged = true;
				return;
			}
		}
		
	}
	
	public void render(Graphics g) {
		
		if(!isDamaged)
			g.drawImage(box,(int)( x - Camera.x),(int)(y - Camera.y), null);
		else 
			g.drawImage(box2,(int)( x - Camera.x),(int)(y - Camera.y), null);
	}


}
