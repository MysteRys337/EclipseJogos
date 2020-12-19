package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
//import java.util.Random;

import javax.imageio.ImageIO;

import graphics.sprite.Sprite;
import main.Game;
import scenes.Normal;
import world.tiles.*;

public class World {
	
	public static int TILE_SIZE = 16;

	// Variaveis do mundo
	public static Tile[] tiles;
	private int[]        pixels;
	private static int   WIDTH;
	private static int   HEIGHT;
	private Sprite[]     tileSprites;
	
	// Variaveis a serem usadas pelo player
	public static int spawnX;
	public static int spawnY;
	
	//private Random gerador = new Random();
	
	// Resolução da tela
	private final int screenWIDTH;
	private final int screenHEIGHT;
	
	public static int getWIDTH() {
		return WIDTH;
	}
	
	public static int getHEIGHT() {
		return HEIGHT;
	}

	public World(String path,int ScreenWIDTH, int ScreenHEIGHT) {
		
		this.screenWIDTH  = ScreenWIDTH;
		this.screenHEIGHT = ScreenHEIGHT;
		
		tileSprites = Game.spritesheet.getSprites("tile");
		
		imageToMap(path);
		
		if(this.pixels != null) 
			setTiles();
		 else 
			System.err.println("ERRO! Nenhum mapa encontrado em " + path);
	}
	
	/**
	 * Resetar o mapa atual
	 */
	public void restartMap() {
		
		tiles  = new Tile[WIDTH * HEIGHT];
		setTiles();
	}
	
	/**
	 * Atualizar o mapa e configura-lo
	 * @param path
	 */
	public void updateMap(String path) {
		
		imageToMap(path);
		setTiles();
		
		if(this.pixels != null) {
			setTiles();
			
		} else { 
			System.err.println("ERRO! Nenhum mapa encontrado em " + path);
			System.exit(0);
		}
	}

	/**
	 * Resgatar uma imagem do diretório do jogo
	 * a partir de uma String com o endereço
	 * @param path - endereço da imagem onde contém informações do mapa
	 */
	private void imageToMap(String path) {

		BufferedImage map = null;
		try {
			
			map = ImageIO.read(getClass().getResource(path));
		} catch (Exception e ) { e.printStackTrace();}
		
		if (map == null)
			return;
		
		WIDTH  = map.getWidth();
		HEIGHT = map.getHeight();

		// Iniciando os tiles, e o array de pixels do mapa
		tiles       = new Tile[WIDTH * HEIGHT];
		this.pixels = new int[WIDTH * HEIGHT];

		// Inserindo os pontos do mapa no array
		map.getRGB(0, 0, WIDTH, HEIGHT, this.pixels, 0, WIDTH);
		
	}
	
	/**
	 * Configurar o mapa do mundo
	 */
	private void setTiles() {
		
		// Configurando os tiles
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {

				int pixelAtual = x + (y * WIDTH);

				// Iniciar todo sprite inicialmente como fundo
				tiles[pixelAtual] = new WallTile(x*TILE_SIZE, y*TILE_SIZE, tileSprites[0].getBufferedImage());

				switch (this.pixels[pixelAtual]) {

					case 0xFFffffff: // Cor Branca | Spawn do chao
						
						tiles[pixelAtual] = new FloorTile(x*TILE_SIZE,y*TILE_SIZE,tileSprites[1].getBufferedImage());
						break;

					case 0xFF0067ff: // Cor Azul | Spawn player
						
						spawnX = x * TILE_SIZE;
						spawnY = y * TILE_SIZE;
						break;
						
					case 0xFFff0000: // Spawn do inimigo
						
						Normal.spawnEntity(x, y, "enemy_goomba");
						break;
						
					case 0xFFffe400:
						
						Normal.spawnEntity(x, y, "item_coin");
						break;
					
					default: // Cor Preta | Spawn do fundo

						tiles[pixelAtual] = new WallTile(x*TILE_SIZE, y*TILE_SIZE, tileSprites[0].getBufferedImage());
						break;
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
		
		int x1 = xnext / TILE_SIZE;
		
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext + width-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + height-1) / TILE_SIZE;
		
		int x4 = (xnext + width-1) / TILE_SIZE;
		int y4 = (ynext + height-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof FloorTile) ||
				 (tiles[x2 + (y2*World.WIDTH)] instanceof FloorTile) ||
				 (tiles[x3 + (y3*World.WIDTH)] instanceof FloorTile) ||
				 (tiles[x4 + (y4*World.WIDTH)] instanceof FloorTile));
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
				
				Tile tile = tiles[x + (y * WIDTH)];
				tile.render(g);
			}
		}
		
	}

}
