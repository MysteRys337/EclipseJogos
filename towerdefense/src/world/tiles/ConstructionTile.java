package world.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class ConstructionTile extends Tile{
	
	BufferedImage[] sprites;

	public ConstructionTile(int newX, int newY, BufferedImage sprite1,BufferedImage sprite2) {
		super(newX, newY, null);
		
		sprites    = new BufferedImage[2];
		sprites[0] = sprite1;
		sprites[1] = sprite2;
		
		this.isFree = true;
	}
	
	public void render(Graphics g) {
		
		if(isFree) 
			sprite = sprites[0];
		else 
			sprite = sprites[1];
		
		super.render(g);
	}

}
