package items.blocks;

import graphics.sprite.Sprite;

public class Bedrock extends SolidBlock{
	
	public Bedrock(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		this.canBreak = false;
	}

}
