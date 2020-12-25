package world.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import world.Camera;

public class Tile {
		
		protected BufferedImage sprite;
		protected int x,y;
		public boolean isFree;
		
		public Tile(int newX, int newY, BufferedImage newSprite) {
			
			this.x = newX;
			this.y = newY;
			this.sprite = newSprite;
			this.isFree = false;
		}
		
		public void render(Graphics g) {
			
			g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
		}
}
