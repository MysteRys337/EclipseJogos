package world;

import java.awt.Graphics;
import java.util.Random;

import attributes.Timer;
import graphics.sprite.Sprite;
import items.blocks.*;
import main.Game;

public class World {
	
	public static int BLOCK_SIZE = 16;

	// Variaveis do mundo
	private static Block[] blocks;
	private static int   WIDTH;
	private static int   HEIGHT;
	public static Sprite[]     tileSprites;
	
	// Variaveis a serem usadas pelo player
	public static int spawnX = 0;
	public static int spawnY = 0;
	
	private Random gerador = new Random();
	
	private Timer dayCicle;
	private static TimeOfDay timeofday;
	
	// Resolução da tela
	private final int screenWIDTH;
	private final int screenHEIGHT;
	
	public static int getWIDTH() {
		return WIDTH;
	}
	
	public static int getHEIGHT() {
		return HEIGHT;
	}
	
	public static Block getBlock(int x, int y) {
		return blocks[x+y*WIDTH];
	}
	
	public static void setBlock(int x, int y, Block block) {
		blocks[x+y*WIDTH] = block; 
	}

	public World(int ScreenWIDTH, int ScreenHEIGHT) {
		
		this.dayCicle = new Timer(2000);
		this.timeofday = TimeOfDay.Morning;
		
		this.screenWIDTH  = ScreenWIDTH;
		this.screenHEIGHT = ScreenHEIGHT;
		
		tileSprites = Game.spritesheet.getSprites("tile");
		
		generateRandomWorld();
	}
	
	public void generateRandomWorld() {
		WIDTH  = 1000;
		HEIGHT = 80;
		
		blocks = new Block[WIDTH*HEIGHT];
		
		Sprite[] skySprites = {tileSprites[3],tileSprites[4],tileSprites[5]};
		
		for(int x = 0; x < WIDTH; x++) {
			int initialHeight = gerador.nextInt(12-8)+8;
			int initialStoneHeight = initialHeight + gerador.nextInt(12-8)+8;
			
			for(int y = 0; y < HEIGHT; y++) {
				if(y == (HEIGHT) - 1 || x == (WIDTH) - 1 || x == 0 || y == 0) {
					blocks[x+y*WIDTH] = new Bedrock(x*BLOCK_SIZE,y*BLOCK_SIZE,tileSprites[6]);
					
				} else {
					
					if ( y > initialStoneHeight) {
						blocks[x+y*WIDTH] = new StoneBlock(x*BLOCK_SIZE,y*BLOCK_SIZE,tileSprites[2]);
						
					} else if(y >= initialHeight) {
						if (blocks[x+(y-1)*WIDTH] instanceof SolidBlock) {
							blocks[x+y*WIDTH] = new SolidBlock(x*BLOCK_SIZE,y*BLOCK_SIZE,tileSprites[1]);
							
						} else {
							blocks[x+y*WIDTH] = new GrassBlock(x*BLOCK_SIZE,y*BLOCK_SIZE,tileSprites[0]);
						}
						
					} else {
						blocks[x+y*WIDTH] = new BackgroundBlock(x*BLOCK_SIZE,y*BLOCK_SIZE,skySprites,timeofday);
					}
				}
			}
		}
	}

	/**
	 * Verificar se um espaço no mapa está disponível para locomoção
	 * @param xnext - X aonde a locomoção acontecera
	 * @param ynext - Y aonde a locomoção acontecera
	 * @return true ou false se é possível ir para o lugar
	 */
	public static boolean isFree(int xnext,int ynext,int width,int height){
		
		int x1 = xnext / BLOCK_SIZE;
		int y1 = ynext / BLOCK_SIZE;
		
		int x2 = (xnext + width+1 ) / BLOCK_SIZE;
		int y2 = ynext / BLOCK_SIZE;
		
		int x3 = xnext / BLOCK_SIZE;
		int y3 = (ynext + height + 1) / BLOCK_SIZE;

		int x4 = (xnext + width+1) / BLOCK_SIZE;
		int y4 = (ynext + height+1) / BLOCK_SIZE;
		
		return !((blocks[x1 + (y1*World.WIDTH)] instanceof SolidBlock) ||
				 (blocks[x2 + (y2*World.WIDTH)] instanceof SolidBlock) ||
				 (blocks[x3 + (y3*World.WIDTH)] instanceof SolidBlock) ||
				 (blocks[x4 + (y4*World.WIDTH)] instanceof SolidBlock));
	}
	
	//Verificando se o espaco ao redor do jogador está livre
	public static boolean isFree(int xnext,int ynext){
		
		int x1 = xnext / BLOCK_SIZE;
		int y1 = ynext / BLOCK_SIZE;
		
		int x2 = (xnext+BLOCK_SIZE-1) / BLOCK_SIZE;
		int y2 = ynext / BLOCK_SIZE;
		
		int x3 = xnext / BLOCK_SIZE;
		int y3 = (ynext+BLOCK_SIZE-1) / BLOCK_SIZE;
		
		int x4 = (xnext+BLOCK_SIZE-1) / BLOCK_SIZE;
		int y4 = (ynext+BLOCK_SIZE-1) / BLOCK_SIZE;
		
		return !((blocks[x1 + (y1*World.WIDTH)] instanceof SolidBlock) ||
				 (blocks[x2 + (y2*World.WIDTH)] instanceof SolidBlock) ||
				 (blocks[x3 + (y3*World.WIDTH)] instanceof SolidBlock) ||
				 (blocks[x4 + (y4*World.WIDTH)] instanceof SolidBlock));
	}
	
	public static boolean canPlaceBlock(int x, int y) {
		return !isFree(x,y+1) && getBlock(x/BLOCK_SIZE,y/BLOCK_SIZE) instanceof BackgroundBlock;
	}
	
	public static Block canRemoveBlock(int x, int y) {
		Block resp = getBlock(x/BLOCK_SIZE,y/BLOCK_SIZE);
		if (resp instanceof BackgroundBlock || resp instanceof Bedrock)
			resp = null;
		
		return resp;
	}
	
	public static void breakBlock(int x,int y) {
		Sprite[] skySprites = {tileSprites[3],tileSprites[4],tileSprites[5]};
		setBlock(x/BLOCK_SIZE, y/BLOCK_SIZE, new BackgroundBlock(x-(x%BLOCK_SIZE),y-(y%BLOCK_SIZE),skySprites,timeofday));
	}
	
	public void tick() {
		
		//System.out.println(timeofday);
		dayCicle.tick();
		if(dayCicle.is_stopped()) {
			dayCicle.reset();
			
			timeofday = TimeOfDay.nextPhase(timeofday);
			
			//Atualizando o skybox
			for (int x = 0; x < WIDTH; x++) {
				for (int y = 0 ; y < HEIGHT; y++ ) {
					Block tile = blocks[x + (y * WIDTH)];
					if(tile instanceof BackgroundBlock) 
						((BackgroundBlock) tile).uppIndex();
				}
			}
		}
	}
	
	public void render(Graphics g) {
		
		//Salvar a posicao da camera dividido pelo tamanho dos sprite
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		//Calculando quantos tiles cabem na tela do jogador
		int xfinal = xstart + (screenWIDTH >> 4);
		int yfinal = ystart + (screenHEIGHT >> 4);
		
		//Renderizando na tela os sprites
		for (int x = xstart; x <= xfinal; x++) {
			
			for (int y = ystart ; y <= yfinal; y++ ) {
				
				//Caso o valor de x e y ultrapasse o limite do array
				if ( x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT)
					continue;
				
				Block tile = blocks[x + (y * WIDTH)];
				tile.render(g);
			}
		}
		
	}

}
