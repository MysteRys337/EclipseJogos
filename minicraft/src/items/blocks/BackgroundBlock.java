package items.blocks;

import java.awt.Graphics;

import graphics.sprite.Sprite;

public class BackgroundBlock extends Block{
	
	private Sprite[] sprites;
	private byte index;

	public BackgroundBlock(int x, int y, Sprite[] sprites) {
		super(x, y, null);
		this.sprites = sprites;
		this.index   = 0;
		this.canBreak = false;
		
	}
	
	public void uppIndex() {
		if(index >= sprites.length -1)
			index = 0;
		else
			index++;
	}
	
	public void render(Graphics g) {
		this.sprite = sprites[index];
		super.render(g);
	}
	

}
