package entities.enemies;

import java.awt.Graphics;

import attributes.Timer;
import entities.Direcao;
import entities.Entity;
import scenes.Normal;
import world.Context;

public class EnemyController extends Entity{
	
	private Timer timer;
	private int width;
	public int numberOfenemies;
	public static int enemySpeed;
	
	private static boolean movementStart = false;
	private static Direcao direcao;
	private Direcao movimentoAnterior;
	

	public EnemyController(int height,int width) {
		super(0,0, 1, 1, 0, null);
		this.timer           = new Timer(120);
		this.width = width;
		
		enemySpeed = 1;
	
		direcao           = Direcao.DIREITA;
		movimentoAnterior = Direcao.CIMA;
		
		movementStart = false;
		
		spawnEnemies(height,width);
		
		movementStart = true;
		
	}
	
	public static boolean hasMovementStart() {
		return movementStart;
	}
	
	public static Direcao getDirecao() {
		return direcao;
	}
	
	private void spawnEnemies(int height,int width) {
		
		numberOfenemies = 0;
		int space_between_enemies = gerador.nextInt(16)+ 32;
		
		int initialX = width/4 - space_between_enemies + 15;
		int finalX = width - initialX;
		System.out.println(initialX + "|" + finalX);
		
		int initialY = space_between_enemies;
		int finalY = (height/(finalX/initialX))*2;
		System.out.println(initialY + "|" + finalY);
		
		for(int y = initialY; y < finalY; y+= space_between_enemies) {
			for(int x = initialX; x < finalX; x+= space_between_enemies ) {
				Normal.spawnEntity(x, y,1, "enemy_1");
				numberOfenemies++;
			}
		}
			
		
	}
	
	public void tick(Context ctx) {
		timer.tick();
		if(timer.is_stopped()) {
			
			if(gerador.nextInt(100) < 25) {
				if(gerador.nextInt(100) < 50)
					Normal.spawnEntity(0, 16,1, "enemy_2");
				else 
					Normal.spawnEntity(width-16, 16,-1, "enemy_2");
			}
			
			if(gerador.nextInt(100) < 5) {
				enemySpeed++;
			}
			
			if(direcao == Direcao.DIREITA || direcao == Direcao.ESQUERDA) {
				movimentoAnterior = direcao;
				direcao = Direcao.BAIXO;
				timer.setNumberOfTicks(15);
			}
			else  {
				if(movimentoAnterior == Direcao.DIREITA) 
					direcao = Direcao.ESQUERDA;
				else 
					direcao = Direcao.DIREITA;
				
				timer.setNumberOfTicks(120);
			}
			timer.reset();
		}

	}
	
	public void render(Graphics g) {
		
	}

}