package scenes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

import entities.Entity;
import entities.enemies.Enemy;
import entities.enemies.EnemySpawner;
import entities.player.Player;
import entities.towers.TowerGun;
import graphics.UI;
import main.Game;
import world.Context;
import world.World;

public class Normal extends Scene{

	public static World  world;					// Objeto contendo o mapa do mundo
	public static Player player;				// Objeto player
	
	public static ArrayList<Entity> entities;	// array dinamico contendo todos as entidades
	public static ArrayList<Enemy> enemies;     // array dinamico contendo todos os inimigos
	
	private UI ui = new UI(width,height,scale);
	
	private Context context;
	
	public Normal(int width, int height, int scale) {
		
		super(width, height, scale);
		
		//Arrays flexiveis
		entities = new ArrayList<Entity>();
		enemies  = new ArrayList<Enemy>();
		
		world = new World("/level1.png",width,height);
		
		this.context = new Context(width, height, World.getWIDTH(), World.getHEIGHT(), Gamestate.normal);
		
		//Criando o player, e adicionando ele na ArrayList
		player = new Player(World.spawnX,World.spawnY,16,16,2,Game.spritesheet.getSprite("player_rock"));
		
		entities.add(player);
	}

	public void tick() {
		
		if (!player.isAlive())
			Game.gamestate = Gamestate.gameover;
			
		player.tick(context,action);
		
		//Renderizar todas as entidades na tela
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick(context);
		}
	}
	
	public static void spawnEntity(int x,int y, String entityName) {
		
		int size = World.TILE_SIZE;
		
		String[] split = entityName.split("_");
		
		if(split[0].equals("enemy")) {
			Enemy e = new Enemy(x, y, size, size,1,entityName);
			entities.add(e);
			enemies.add(e);
			
		}
		else if(split[0].equals("tower")) {
			if(World.isConstructableTile(x, y)) {
				TowerGun tg = new TowerGun(x-5,y-6,size,size,1,Game.spritesheet.getSprite(entityName));
				entities.add(tg);
				player.decreaseMoney(20);
			}
		}
	}
	
	public static void spawnEnemySpawner(int x,int y, int tile_size) {
		
		EnemySpawner es = new EnemySpawner(x,y,tile_size,tile_size,0,null);
		entities.add(es);
	}
	
	public static void gameOver() {
		
		Game.gamestate = Gamestate.gameover;
	}
	
	
	public void render(Graphics g) {

		world.render(g);
		
		Collections.sort(entities, Entity.entitySorter);
		
		//Renderizar todas as entidades na tela
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		ui.render(g,player,context);
	}

}
