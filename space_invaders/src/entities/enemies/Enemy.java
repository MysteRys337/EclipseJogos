package entities.enemies;

import java.awt.Graphics;

import attributes.Timer;
import entities.Direcao;
import entities.Entity;
import entities.bullets.Bullet;
import entities.bullets.EnemyBullet;
import graphics.sprite.Sprite;
import scenes.Normal;
import world.Context;

public class Enemy extends Entity{
	
	int points_for_destruction = 15;
	protected Timer timerMovimento;
	
	public Enemy(int x, int y, int width, int height, double speed, Sprite sprite) {
		super(x, y, width, height, speed, sprite);
		this.timerMovimento = new Timer(10);
	}
	
	public void tick(Context context) {
		
		timerMovimento.tick();
		
		if(timerMovimento.is_stopped()) {
			
			timerMovimento.reset();
			
			if(EnemyController.hasMovementStart()) {
				if(EnemyController.getDirecao() == Direcao.DIREITA) 
					x+=EnemyController.enemySpeed;
				else if(EnemyController.getDirecao() == Direcao.ESQUERDA)
					x-=EnemyController.enemySpeed;
				else if(EnemyController.getDirecao() == Direcao.BAIXO)
					y+=EnemyController.enemySpeed;
			}
			
			if(gerador.nextInt(100) < 1) 
				Normal.shoot(getX(), getY() , -5,true);
			
		}
		this.detectCollisionWithBullets();
		
	}
	
	protected void detectCollisionWithBullets() {
		for(Bullet b : Normal.bullets) 
			if(!(b instanceof EnemyBullet) && Entity.isColidding(this, b)) {
				this.destroy(b);
				break;
			}
	}
	
	protected void destroy(Bullet b) {
		Normal.removeEntity(this);
		Normal.removeEntity(b);
		Normal.player.setPoints(Normal.player.getPoints() + points_for_destruction);
	}
	

	public void render(Graphics g) {
		
		g.drawImage(sprite.getBufferedImage(), getX(), getY(), null);
	}

}
