package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import graphics.sprite.Sprite;
import world.Camera;
import world.Context;
import world.pathfinding.*;

public class Entity {
	
	protected double x,y;
	protected int    maskX,maskY;
	protected int    width,height;
	protected int    maskW,maskH;
	
	protected double speed;

	public Random gerador;					
	
	public byte depth;
	
	protected ArrayList<Node> path;
	
	protected Sprite sprite;
	
	public Entity(int x, int y, int width, int height, double speed, Sprite sprite) {
		this.x      = x;
		this.y      = y;
		this.width  = width;
		this.height = height;
		this.sprite = sprite;
		this.speed  = speed;
		
		gerador = new Random();
		
		setMask(0,0,width,height);
		
	}
	
	public int getSpeed() {
		return (int)this.speed;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	/**
	 * Pegue as coordenadas de um entidade separadas por |
	 * @return o X e o Y de uma entidade, separado por |
	 */
	public String getCoordenates() {
		return getX() + "|" + getY();
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getMaskW() {
		return this.maskW;
	}
	
	public int getMaskH() {
		return this.maskH;
	}
	
	public Sprite getSprite() {
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
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setCoordenates(double x, double y) {
		setX(x);
		setY(y);
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setMask(int maskX,int maskY, int maskW, int maskH) {
		this.maskX = maskX;
		this.maskY = maskY;
		this.maskW = maskW;
		this.maskH = maskH;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void tick(Context context) {

	}
	
	public static double calculateDistance(double x1, double y1, double x2, double y2) {
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
	
	public static boolean isColidding(Entity e1,Entity e2) {
		
		Rectangle e1Mask = new Rectangle((int)(e1.x + e1.maskX),(int)(e1.y + e1.maskY),e1.maskW,e1.maskH);
		Rectangle e2Mask = new Rectangle((int)(e2.x + e2.maskX),(int)(e2.y + e2.maskY),e2.maskW,e2.maskH);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		
		g.drawImage(sprite.getBufferedImage(),getX() - Camera.x, getY() - Camera.y, null);
	}
	
	protected void renderMask(Graphics g) {
		
		//Funcao para renderizar mascara das entidades(Funcao de debug)
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100));
		g2.fillRect(getX() + maskX - Camera.x, getY() + maskY - Camera.y, maskW, maskH);
	}
}
