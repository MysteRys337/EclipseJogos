package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import entities.Animation;
import entities.Entity;
import entities.enemies.Enemy;
import entities.enemies.EnemyController;
import entities.player.Player;
import graphics.UI;
import main.Game;
import world.Context;
import world.World;

public class Normal extends Scene{

	public static Player player;				// Objeto player
	
	public static ArrayList<Entity> entities;	// array dinamico contendo todos as entidades
	public static ArrayList<Enemy> enemies;     // array dinamico contendo todos os inimigos
	
	public static EnemyController enemyController;
	
	private Context context;
	
	public BufferedImage background;
	
	public Normal(int width, int height, int scale) {
		
		super(width, height, scale);
		
		UI ui = new UI(width,height,scale);
		
		try {
			background = ImageIO.read(new File("res/fundo.png"));
		} catch(Exception e) {e.printStackTrace();}
		
		//Arrays flexiveis
		entities = new ArrayList<Entity>();
		enemies  = new ArrayList<Enemy>();
		
		this.context = new Context(width, height, World.getWIDTH(), World.getHEIGHT(),scale, Gamestate.normal);
		
		//Criando o player, e adicionando ele na ArrayList
		player = new Player(width/2,height/2,48,48,ui,Game.spritesheet.getSprite("player"));
		
		entities.add(player);
		
		enemyController = new EnemyController(120);
	}
	
	public void reset() {
		player.setPoints(0);
		entities.clear();
		enemies.clear();
		entities.add(player);
		enemyController = new EnemyController(120);
	}

	public void tick() {
		
		player.tick(context,action);
		enemyController.tick(context);
		
		//Renderizar todas as entidades na tela
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick(context);
		}
	}
	
	public static void spawnEntity(int x,int y,int speed, String entityName) {
		
		if(entityName.contains("crab")) {
			Enemy e = null;

			e = new Enemy(x, y, 48, 48,speed,entityName);
			entities.add(e);
			enemies.add(e);
				
		}
		else if(entityName.contains("smoke")) {
			Animation smoke = new Animation(x,y,entityName);
			entities.add(smoke);
		}
	}
	 
	public static void killEnemy(int x,int y) {
		for(Enemy e: enemies) {
			if(Entity.isColidding(x, y,e)) {
				e.destroy();
				break;
			}
			
		}
	}
	
	public static void removeEntity(Entity e) {
		entities.remove(e);
		if(e instanceof Enemy) {
			enemies.remove(e);
		}
			
	}
	
	public static void gameOver() {
		Game.gamestate = Gamestate.gameover;
	}
	
	
	public void render(Graphics g) {
		
		g.drawImage(background, 0, 0, null);
		
		Collections.sort(entities, Entity.entitySorter);
		
		//Renderizar todas as entidades na tela
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
	}

}
