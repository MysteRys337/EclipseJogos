package entities;

import java.awt.image.BufferedImage;

public class LifePack extends Entity{

	public LifePack(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.setMask(4,4,8,8);
	}

}
