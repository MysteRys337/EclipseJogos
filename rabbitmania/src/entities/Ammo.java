package entities;

import java.awt.image.BufferedImage;

public class Ammo extends Entity{

		private int type;
		
		public Ammo(int x,int y, int width, int height,BufferedImage sprite,int type) {
			super(x,y,width,height,sprite);
			this.type = type;
		}
		
		public int getType() {
			
			return type;
		}
}
