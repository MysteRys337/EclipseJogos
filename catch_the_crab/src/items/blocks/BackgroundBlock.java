package items.blocks;

import java.awt.Graphics;

import graphics.sprite.Sprite;
import world.TimeOfDay;

public class BackgroundBlock extends Block{
	
	private Sprite[] sprites;
	private byte index;

	public BackgroundBlock(int x, int y, Sprite[] sprites, TimeOfDay timeofday) {
		super(x, y, null);
		this.sprites = sprites;
		this.index   = timeofday.getTimeOfDay();
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
