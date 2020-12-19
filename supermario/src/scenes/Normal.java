package scenes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

import entities.Entity;
import entities.enemies.Enemy;
import entities.items.Coin;
import entities.player.Player;
import graphics.UI;
import main.Game;
import world.Context;
import world.World;

public class Normal extends Scene{

	public static World  world;					// Objeto contendo o mapa do mundo
	public static Player player;				// Objeto player
	
	public static ArrayList<Entity> entities;	// array dinamico contendo todos as entidades
	public static ArrayList<Enemy> enemies;     // array dinamico contendo todos os inimigos
	public static ArrayList<Coin> coins;        // array dinamico contendo todos os coins
	
	private Context context;
	
	public Normal(int width, int height, int scale) {
		
		super(width, height, scale);
		
		UI ui = new UI(width,height,scale);
		
		//Arrays flexiveis
		entities = new ArrayList<Entity>();
		enemies  = new ArrayList<Enemy>();
		coins  = new ArrayList<Coin>();
		
		world = new World("/level1.png",width,height);
		
		this.context = new Context(width, height, World.getWIDTH(), World.getHEIGHT(), Gamestate.normal);
		
		//Criando o player, e adicionando ele na ArrayList
		player = new Player(World.spawnX,World.spawnY,16,16,2,"player",ui);
		
		entities.add(player);
	}

	public void tick() {
		
		//System.out.println(context.getContext());
		
		player.tick(context,action);
		
		//Renderizar todas as entidades na tela
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick(context);
		}
	}
	
	public static void playerCollision() {
		
		for (Enemy e : Normal.enemies) {
			if(Entity.isColidding(player, e)) {
				gameOver();
			}
		}
		
		for (Coin c : Normal.coins) {
			if(Entity.isColidding(player,c)) {
				c.destroy();
				player.addPoints(10); 
				break;
			}
		}

	}
	
	public static void spawnEntity(int x,int y, String entityName) {
		
		int size = World.TILE_SIZE;
		
		String[] split = entityName.split("_");
		
		if(split[0].equals("enemy")) {
			Enemy e = new Enemy(x*size, y*size, size, size,1,entityName);
			entities.add(e);
			enemies.add(e);
			
		} else if ( split[0].equals("item")) {
			
			Coin c = new Coin(x*size,y*size,size,size,1,entityName);
			coins.add(c);
			entities.add(c);
		}
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
	}

}
