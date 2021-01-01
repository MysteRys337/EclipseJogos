package items.blocks;

import graphics.sprite.Sprite;
import items.Item;

public class Block extends Item{

	protected boolean canBreak;
	
	public Block(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		this.canBreak = true;
	}
	
	public boolean canBreak() {
		return this.canBreak;
	}
	
		
}
