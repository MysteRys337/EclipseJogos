package scenes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

import entities.Entity;
import entities.enemies.Enemy;
import entities.player.Player;
import graphics.UI;
import main.Game;
import world.Context;
import world.World;
import items.blocks.*;

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
		
		world = new World(width,height);
		
		this.context = new Context(width, height, World.getWIDTH(), World.getHEIGHT(), Gamestate.normal);
		
		//Criando o player, e adicionando ele na ArrayList
		player = new Player(100,100,16,16,2,"player",ui);
		
		entities.add(player);
	}

	public void tick() {
		
		world.tick();
		
		player.tick(context,action);
		
		//Renderizar todas as entidades na tela
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick(context);
		}
	}
	
	public static void spawnEntity(int x,int y, String entityName) {
		
		int size = World.BLOCK_SIZE;
		
		String[] split = entityName.split("_");
		
		if(split[0].equals("enemy")) {
			Enemy e = new Enemy(x, y, size, size,1,entityName);
			entities.add(e);
			enemies.add(e);
			
		}
	}
	
	public static void removeEntity(Entity e) {
		entities.remove(e);
		if(e instanceof Enemy)
			enemies.remove(e);
	}
	
	public static void placeBlock(int x,int y, Block block) {
		
		int tileSize = World.BLOCK_SIZE;
		
		boolean canPlaceABlock     = World.canPlaceBlock(x, y);
		boolean playerPositionFree = Math.abs(x - player.getX() - player.getMaskW()) > tileSize || Math.abs(y - player.getY() - player.getMaskH()) > tileSize;
		
		if(canPlaceABlock && playerPositionFree) 
			if(block != null)
				World.setBlock(x/tileSize, y/tileSize, new SolidBlock(x-(x%tileSize),y-(y%tileSize),block.getSprite()));
		
	}
	
	public static Block removeBlock(int x,int y) {
		
		Block block = World.canRemoveBlock(x,y);
		
		if(block != null)
			World.breakBlock(x, y);
		
		return block;
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
		
		ui.render(g);
	}

}
