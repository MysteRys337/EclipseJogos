package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import world.Camera;
import world.Pathfinding;
import world.Vector2i;
import world.World;
import zelda.Game;
import zelda.Sound;

public class Enemy extends Entity{

	//Velocidade do inimigo
	private double speed = 1.2;
	
	//Configurando variaveis de animacao
	private int frames = 0,maxFrames = 10,index = 0,maxIndex = 1,deathFrames = 10;
	private BufferedImage[] sprites;
	
	//Variaveis de estatísticas
	private int life = 9;
	private boolean isDamaged = false;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		
		super(x, y, width, height, sprite);
		
		depth = 0;
		
		//Criando o array com sprites de animacao
		sprites = new BufferedImage[2];
		sprites[0] = Entity.ENEMY_EN;
		sprites[1] = Entity.ENEMY2_EN;
		
		//Configurando o x e y da mascara do meu inimigo
		setMask(5,5,8,10);
		
		//Gerando uma velocidade aleatoria para cada inimigo
		speed -= Game.gerador.nextDouble();
	}
	
	public void tick() {
		
		Astarmovement();
		//movement();
			
		frames++;
		if (frames == maxFrames) {
			
			frames = 0;
			index++;
			if ( index > maxIndex) {
				
				index = 0;
			}
		}
		
		if(isDamaged) {
			
			deathFrames++;
			if( deathFrames >= 10) {
				
				isDamaged = false;
			}
		}
		
		isCollidingBullet();
		
		if ( life <= 0) {
			destroySelf();
		}
		
	}
	
	public void movement() {
		
		if (calculateDistance(x,y,Game.player.getX(),Game.player.getY()) < 150) {
			
			if (!isAttackingPlayer()) {
				
				if ((int)x < Game.player.getX() && World.isFree((int)(x+speed),(int)y,width,height,z)
					&& !(isColliding((int)(x+speed),(int)y))) {
					
					 x += speed;
				}
				
				else if ((int)x > Game.player.getX() && World.isFree((int)(x-speed),(int)y,width,height,z)
						&& !(isColliding((int)(x-speed),(int)y))) {
					
					 x -= speed;
				}
				
				if ((int)y < Game.player.getY() && World.isFree((int)x,(int)(y+speed),width,height,z)
						&& !(isColliding((int)x,(int)(y+speed)))) {
					
					 y += speed;
				}
				
				else if ((int)y > Game.player.getY() && World.isFree((int)x,(int)(y-speed),width,height,z)
						&& !(isColliding((int)x,(int)(y-speed)))) {
					
					 y -= speed;
				}
				
			}
			else {
				
				Game.player.decreaseLife();
			}
		}
	}
	
	public void Astarmovement() {
		
		if (calculateDistance(x,y,Game.player.getX(),Game.player.getY()) < 150) {

			if(!isAttackingPlayer()) {
				
				if(path == null || path.size() == 0) {
					
					Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
					Vector2i end = new Vector2i((int)(Game.player.getX()/16),(int)(Game.player.getY()/16));
					path = Pathfinding.findPath(Game.world,start,end);
				}
			}
			else {
				
				Game.player.decreaseLife();
			}
				
			followPath(path);
		}
	}
	
	public void destroySelf() {
		
		Game.enemies.remove(this);
		Game.entities.remove(this);
	}
	
	public void isCollidingBullet() {
		
		for (byte i = 0; i < Game.bullets.size(); i++) {
			
			Bullet b = Game.bullets.get(i);
			if (Entity.isColidding(b, this)) {
				
				Game.sound.play(Sound.enemyDamage);
				life -= b.getDamage();
				Game.bullets.remove(b);
				isDamaged = true;
				deathFrames = 0;
				return;
			}
		}
		
	}
	
	public boolean isAttackingPlayer() {
		
		Rectangle enemyColision = new Rectangle((int)(x + maskX) ,(int)(y + maskY) ,maskW, maskH);
		Rectangle playerColision = new Rectangle((int)(Game.player.getX()) ,(int)(Game.player.getY()) ,16, 16);
		
		return enemyColision.intersects(playerColision);
	}
	
	public void render(Graphics g) {
		if (!isDamaged) {
			g.drawImage(sprites[index],(int)x - Camera.x ,(int)y - Camera.y,null);
		}
		else {
			g.drawImage(ENEMY_FEEDBACK,(int)x - Camera.x ,(int)y - Camera.y,null);
		}
	}

}
