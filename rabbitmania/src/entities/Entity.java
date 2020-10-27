package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import world.Camera;
import world.Node;
import world.Vector2i;
import zelda.Game;

public class Entity {
	
	//Sprite staticos(nao mudam durante a execucao do jogo)
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(32, 128, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(0, 112, 16, 16);
	public static BufferedImage WEAPON2_EN = Game.spritesheet.getSprite(64, 112, 16, 16);
	public static BufferedImage AMMO_EN = Game.spritesheet.getSprite(0, 128, 16, 16);
	public static BufferedImage AMMO2_EN = Game.spritesheet.getSprite(16, 128, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(0, 16, 16, 16);
	public static BufferedImage ENEMY2_EN = Game.spritesheet.getSprite(16, 16, 16, 16);
	public static BufferedImage ENEMY_FEEDBACK = Game.spritesheet.getSprite(32, 16, 16, 16);
	public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(16, 112, 16, 16);
	public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite(32, 112, 16, 16);
	public static BufferedImage GUN_DOWN = Game.spritesheet.getSprite(48, 112, 16, 16);
	public static BufferedImage GUN2_RIGHT = Game.spritesheet.getSprite(80, 112, 16, 16);
	public static BufferedImage GUN2_LEFT = Game.spritesheet.getSprite(96, 112, 16, 16);
	public static BufferedImage GUN2_DOWN = Game.spritesheet.getSprite(112, 112, 16, 16);
	public static BufferedImage BOX = Game.spritesheet.getSprite(0, 144, 16, 16);
	public static BufferedImage BOX_FEEDBACK = Game.spritesheet.getSprite(32, 144, 16, 16);
	
	protected double x,y;
	protected int maskX ,maskY ,maskW ,maskH ,z;
	protected int width,height;
	
	public byte depth;
	
	protected ArrayList<Node> path;
	
	private BufferedImage sprite;
	
	public Entity(int x,int y,int width, int height,BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		setMask(0,0,width,height);
		
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getZ() {
		return this.z;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public BufferedImage getSprite() {
		return this.sprite;
	}
	
	public static Comparator<Entity> entitySorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity t0, Entity t1) {
			if (t1.depth < t0.depth) {
				return + 1;
			}
			if (t1.depth > t0.depth) {
				return - 1;
			}
			return 0;
		}
	};
	
	public void setMask(int maskX,int maskY, int maskW, int maskH) {
		this.maskX = maskX;
		this.maskY = maskY;
		this.maskW = maskW;
		this.maskH = maskH;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	public int getType() {
		
		return 0;
	}
	
	public void tick() throws IOException{}
	
	public double calculateDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
	}
	
	public void followPath(ArrayList<Node> path) {
		
		if ( path != null) {
			
			if ( path.size() > 0) {
				
				Vector2i target = path.get(path.size() - 1).tile;
				if ( x < target.x * 16 ) {
					
					x++;
				}
				else if ( x > target.x * 16) {
					
					x--;
				}
				if ( y < target.y * 16 ) {
					
					y++;
				}
				else if ( y > target.y * 16) {
					
					y--;
				}
				if ( x == target.x * 16 && y == target.y * 16) {
					
					path.remove(path.size()-1);
				}
			}
		}
	}
	
	//Conferir colisao entre duas entidades
	public static boolean isColidding(Entity e1,Entity e2) {
		
		Rectangle e1Mask = new Rectangle((int)(e1.x + e1.maskX),(int)(e1.y + e1.maskY),e1.maskW,e1.maskH);
		Rectangle e2Mask = new Rectangle((int)(e2.x + e2.maskX),(int)(e2.y + e2.maskY),e2.maskW,e2.maskH);
		
		return e1Mask.intersects(e2Mask) && e1.z == e2.z;
	}
	
	//Conferir colisao com uma entidade e todos os inimigos
	public boolean isColliding(int xnext,int ynext) {
		
		Rectangle enemyCurrent = new Rectangle(xnext + maskX ,ynext + maskY ,maskW, maskH);
		
		for (byte i = 0; i < Game.enemies.size(); i++) {
			Enemy tmp = Game.enemies.get(i);
			
			//Ignorar colisao consigo mesmo
			if ( tmp == this) {
				continue;
			}
			
			Rectangle targetEnemy = new Rectangle((int)tmp.getX() + maskX ,(int)tmp.getY() + maskY, maskW, maskH);
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public void render(Graphics g) {
		
		g.drawImage(sprite,(int)x - Camera.x, (int)y - Camera.y, null);
	}
	
	protected void renderMask(Graphics g) {
		
		//Funcao para renderizar mascara das entidades(Funcao de debug)
		g.setColor(Color.blue);
		g.fillRect((int)(x + maskX - Camera.x), (int)(y + maskY - Camera.y), maskW, maskH);
	}
}
