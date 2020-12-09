package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entities.Player;
import flappybird.Game;
import world.tiles.*;

public class World {

	// Variaveis do mundo
	public static Tile[] tiles;
	public static int WIDTH;
	public static int HEIGHT;
	public static final int TILE_SIZE = 16;
	public Random gerador = new Random();

	// Construtor do mundo
	public World(String path) throws IOException {
		geracaoControlada(path);

	}

	public void geracaoControlada(String path) throws IOException {

		// Gerando o mapa a partir do map.png
		// Pegando o arquivo em "path"

		int[] pixels;

		BufferedImage map = ImageIO.read(getClass().getResource(path));
		WIDTH = map.getWidth();
		HEIGHT = map.getHeight();

		// Iniciando os tiles, e o array de pixels do mapa
		tiles  = new Tile[WIDTH * HEIGHT];
		pixels = new int[WIDTH * HEIGHT];

		// Inserindo os pontos do mapa no array
		map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);

		// Configurando os tiles
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {

				int pixelAtual = x + (y * WIDTH);

				// Iniciar todo sprite inicialmente como chao
				tiles[pixelAtual] = new FloorTile(x * 16, y * 16, Tile.TILE_FLOOR);

				switch (pixels[x + (y * WIDTH)]) {

					case 0xFFffffff: // Cor Branca | Colocar parede
						
						tiles[pixelAtual] = new WallTile(x*16,y*16,Tile.TILE_WALL);
						break;

					case 0xFF2770ff: // Cor Azul | Spawn player
						
						Game.player.setX(x*16);
						Game.player.setY(y*16);
						break;
						
					default: // Cor Preta | Spawn de grama

						tiles[pixelAtual] = new FloorTile(x * 16, y * 16, Tile.TILE_FLOOR);
						break;
				}
			}
		}
	}

	// Reiniciar o jogo
	public static void restartGame() {
		Game.player = new Player(Game.getWIDTH()/2 - 13,Game.getHEIGHT()/2 - 5,16,16,2,null);
		Game.entities.clear();
		Game.entities.add(Game.player);
		
	}
	
	
	//Verificando se o espaco ao redor do jogador está livre
	public static boolean isFree(int xnext,int ynext){
		
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
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

}
