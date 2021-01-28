package scenes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

import entities.Entity;
import entities.bullets.Bullet;
import entities.bullets.EnemyBullet;
import entities.enemies.Enemy;
import entities.enemies.EnemyController;
import entities.enemies.SpecialEnemy;
import entities.player.Player;
import graphics.UI;
import graphics.parallax.ParallaxY;
import main.Game;
import world.Context;
import world.World;

public class Normal extends Scene{

	public static Player player;				// Objeto player
	
	public static ArrayList<Entity> entities;	// array dinamico contendo todos as entidades
	public static ArrayList<Enemy> enemies;     // array dinamico contendo todos os inimigos
	public static ArrayList<Bullet> bullets;     // array dinamico contendo todos os inimigos
	
	public static EnemyController enemyController;
	
	private ParallaxY p;
	private ParallaxY p2;
	
	private UI ui;
	
	private Context context;
	
	public Normal(int width, int height, int scale) {
		
		super(width, height, scale);
		
		p = new ParallaxY("/bg1.png",1,height,5);
		p2 = new ParallaxY("/bg2.png",2,height,5);
		ui = new UI(width,height,scale);
		
		//Arrays flexiveis
		entities = new ArrayList<Entity>();
		enemies  = new ArrayList<Enemy>();
		bullets  = new ArrayList<Bullet>();
		
		this.context = new Context(width, height, World.getWIDTH(), World.getHEIGHT(), Gamestate.normal);
		
		//Criando o player, e adicionando ele na ArrayList
		player = new Player(width/2,height-22,16,16,2,"player",ui);
		
		entities.add(player);
		
		enemyController = new EnemyController(height,width);
	}

	public void tick() {
		
		if(EnemyController.hasMovementStart() && enemyController.numberOfenemies == 0) 
			System.exit(1);
		
		
		p.tick(context);
		p2.tick(context);
		
		player.tick(context,action);
		enemyController.tick(context);
		
		//Renderizar todas as entidades na tela
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick(context);
		}
	}
	
	public static void spawnEntity(int x,int y,int speed, String entityName) {
		
		int size = World.BLOCK_SIZE;
		
		if(entityName.contains("enemy")) {
			Enemy e = null;
			if(entityName.equals("enemy_1")) {
				e = new Enemy(x, y, size, size,speed,Game.spritesheet.getSprite(entityName));
				
			}
			else {
				e = new SpecialEnemy(x, y, size, size,speed,Game.spritesheet.getSprite(entityName));
			}
			entities.add(e);
			enemies.add(e);
				
		}
	}
	
	public static void shoot(int x,int y,int speed,boolean isEnemy) {
		
		int size = World.BLOCK_SIZE;
		
		Bullet b = null;
		if(isEnemy)
			b = new EnemyBullet(x+size/2  ,y+5,2,5,speed);
		else 
			b = new Bullet(x+size/2  ,y+5,2,5,speed);
		
		entities.add(b);
		bullets.add(b);
	}
	
	public static void removeEntity(Entity e) {
		entities.remove(e);
		if(e instanceof Enemy) {
			enemies.remove(e);
			if(!(e instanceof SpecialEnemy)) {
				enemyController.numberOfenemies--;
			}
		}
		else if(e instanceof Bullet) {
			bullets.remove(e);
		}
			
	}
	
	public static void gameOver() {
		
		Game.gamestate = Gamestate.gameover;
	}
	
	
	public void render(Graphics g) {
		
		Collections.sort(entities, Entity.entitySorter);
		
		p.render(g);
		p2.render(g);
		
		//Renderizar todas as entidades na tela
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		ui.render(g);
	}

}
