package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.*;
import graficos.Spritesheet;
import zelda.Game;

public class World {
	
	//Variaveis do mundo
	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public static void toFloorTile(int x,int y) {
		
		tiles[(x/16) + (y/16) * WIDTH] = new FloorTile(x,y,Tile.TILE_FLOOR);
	}
	
	//Construtor do mundo
	public World (String path) throws IOException {
		
		//geracaoAleatoria();
		
		geracaoControlada(path);
	}
	
	public void geracaoAleatoria() {
		
		Game.player.setX(0);
		Game.player.setY(0);
		
		WIDTH = HEIGHT = 100;
		tiles = new Tile[WIDTH * HEIGHT];
		
		for (int i = 0; i < WIDTH; i++) {
			
			for (int j = 0; j < HEIGHT; j++) {
				
				tiles[i + j * WIDTH] = new WallTile(i*16,j*16,Tile.TILE_WALL);
			}
		}
		
		int dir = 0, x = 0, y = 0;
		
		for (int i = 0; i < WIDTH + HEIGHT; i++) {
			
			tiles[x + y * WIDTH] = new FloorTile(x*16,y*16,Tile.TILE_FLOOR);
			
			if (dir == 0 ) {
				
				if ( x < WIDTH) {
					
					x++;
				}
			}
			else if ( dir == 1) {
				
				if ( x > WIDTH) {
					
					x--;
				}
			}
			else if ( dir == 2) {
				
				if ( y < HEIGHT) {
					
					y++;
				}
				
			}
			else if ( dir == 3 ) {
				
				if ( y > 0) {
					
					y--;
				}
			}
			if ( Game.gerador.nextInt(100) < 30 ) {
				
				dir = Game.gerador.nextInt(4);
			}
		}
	}
	
	public void geracaoControlada(String path) throws IOException {
		
		// Gerando o mapa a partir do map.png
		//Pegando o arquivo em "path"
		BufferedImage map = ImageIO.read(getClass().getResource(path));
		WIDTH = map.getWidth();
		HEIGHT = map.getHeight();
		
		//Iniciando os tiles, e o array de pixels do mapa
		tiles = new Tile[WIDTH * HEIGHT];
		int[] pixels = new int[WIDTH * HEIGHT];
		
		//Inserindo os pontos do mapa no array
		map.getRGB(0, 0,WIDTH,HEIGHT,pixels,0,WIDTH);
		
		//Configurando os tiles
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				
				int pixelAtual = x + (y * WIDTH);
				
				//Iniciar todo sprite inicialmente como chao
				tiles[pixelAtual] = new FloorTile(x*16,y*16,Tile.TILE_FLOOR);
				
				switch(pixels[x + (y * WIDTH)]) {
				
					case 0xFFff0000: // Cor Vermelha | Spawn inimigo
						
						Enemy tmp = new Enemy(x*16,y*16,16,16,Entity.ENEMY_EN);
						Game.entities.add(tmp);
						Game.enemies.add(tmp);
						break;
					case 0xFFffffff: // Cor Branca | Colocar parede
						
						tiles[pixelAtual] = new WallTile(x*16,y*16,Tile.TILE_WALL);
						break;
					case 0xFFfffc00:
						
						tiles[pixelAtual] = new WallTile(x*16,y*16,Tile.TILE_WALL);
						Box box = new Box(x*16,y*16,16,16,null);
						Game.entities.add(box);
						break;
					case 0xFFff9800: // Cor Amarela | Spawn arma
						
						Game.entities.add(new Weapon(x*16,y*16,16,16,Entity.WEAPON_EN,0));
						break;
					case 0xFF2770ff: // Cor Azul | Spawn player
						
						Game.player.setX(x*16);
						Game.player.setY(y*16);
						break;
					case 0xFFc573ff: // Cor Lilas | Spawn municao
						
						Game.entities.add(new Ammo(x*16,y*16,16,16,Entity.AMMO_EN,0));
						break;
					case 0xFFc400ff: // Cor Lilas | Spawn municao de uzi
						
						Game.entities.add(new Ammo(x*16,y*16,16,16,Entity.AMMO2_EN,1));
						break;
					case 0xFF0dff00: // Cor Verde | Spawn pack de vida
						
						LifePack pack = new LifePack(x*16,y*16,16,16,Entity.LIFEPACK_EN);
						Game.entities.add(pack);
						break;
					case 0xFFff5400: // Spawn da Uzi
						
						Game.entities.add(new Weapon(x*16,y*16,16,16,Entity.WEAPON2_EN,1));
						break;
					default: // Cor Preta | Spawn de grama
						
						if ( Game.gerador.nextInt(100) < 30) { // Spawn da grama variacao 2
							
							tiles[pixelAtual] = new FloorTile(x*16,y*16,Tile.TILE_FLOOR2);
						}
						else if ( Game.gerador.nextInt(100) < 5) { // Spawn da grama com floor
							
							tiles[pixelAtual] = new FloorTile(x*16,y*16,Tile.TILE_FLOOR3);
						}
						else { // Spawn da grama variacao 1
							
							tiles[pixelAtual] = new FloorTile(x*16,y*16,Tile.TILE_FLOOR);
						}
						break;
				}
			}
		}
	}
	
	//Reiniciar o jogo
	public static void restartGame(String level) throws IOException {
		
		//Criando um array flexivel para todas as entidades e inimigos do jogo
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.bullets = new ArrayList<Bullet>();
		
		//Criando o objeto que contem o spritesheet do jogo
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		
		//Criando o player, e adicionando ele na ArrayList
		Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.add(Game.player);
		
		//Iniciando o objeto mundo;
		Game.gameState = "NORMAL";
		Game.world = new World("/"+level);
	}
	
	public static void generateParticle(int amount, int x, int y) {
		
		for(int i = 0 ; i < amount; i++) {
			Game.entities.add(new Particle(x,y,1,1,null));
		}
	}
	
	//Verificando se o espaco ao redor do jogador está livre
	public static boolean isFree(int xnext, int ynext,int width,int height,int z) {
		
		int x1 = xnext / TILE_SIZE,y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + width-1) / TILE_SIZE,y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE,y3 = (ynext + height-1) / TILE_SIZE;
		
		int x4 = (xnext + width-1) / TILE_SIZE,y4 = (ynext + height-1) / TILE_SIZE;
		
		if ( ! ( (tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				   (tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) || 
				   (tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				   (tiles[x4 + (y4*World.WIDTH)] instanceof WallTile) )) {
			
			return true;
		}
		if (z > 0) {
			
			return true;
		}
		return false;
	}
	
	//Renderizar o mapa
	public void render(Graphics g) {
		
		//Salvar a posicao da camera dividido pelo tamanho dos sprite
		int xstart = Camera.x >> 4,ystart = Camera.y >> 4;
		
		//Calculando quantos tiles cabem na tela do jogador
		int xfinal = xstart + (Game.getWIDTH() >> 4), yfinal = ystart + (Game.getHEIGHT() >> 4);
		
		//Renderizando na tela os sprites
		for (int x = xstart; x <= xfinal; x++) {
			
			for (int y = ystart ; y <= yfinal; y++ ) {
				
				//Caso o valor de x e y ultrapasse o limite do array
				if ( x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT)
					continue;
				
				Tile tile = tiles[x + (y * WIDTH)];
				tile.render(g);
			}
		}
		
	}
	
	public static void renderMinimap(int[] pixels) {
		
		int xstart = Camera.x >> 4,ystart = Camera.y >> 4;
		
		//Calculando quantos tiles cabem na tela do jogador
		int xfinal = xstart + (Game.getWIDTH() >> 4), yfinal = ystart + (Game.getHEIGHT() >> 4);
		
		for (int x = xstart; x <= xfinal; x++) {
			
			for (int y = ystart; y <= yfinal; y++) {
				
				if ( x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT )
					continue;
				
				else if ( tiles[x + (y * WIDTH)] instanceof WallTile) {
					
					pixels[x + (y * WIDTH)] = 0xFF636363;
				}
				else if (tiles[x + (y * WIDTH)] instanceof FloorTile) {
					
					pixels[x + (y * WIDTH)] = 0xFF00b521;
				}
				
			}
		}
		
		int xPlayer = (int)Game.player.getX()/16,yPlayer = (int)Game.player.getY()/16;
		pixels[xPlayer + (yPlayer * WIDTH)] = 0xFF0075ff;
		
	}
	
	public static boolean isVisible(int x,int xstart,int xfinal, int y, int ystart,int yfinal) {
		
		return ( x < xstart || x > xfinal || y < ystart || y > yfinal);
	}
}
