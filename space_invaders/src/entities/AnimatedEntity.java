package entities;

import graphics.sprite.Sprite;
import main.Game;

public class AnimatedEntity extends Entity{
	
	//Variaveis de animacao do movimento
	protected byte frames;
	protected byte maxFrames;
	
	protected byte index;
	protected byte minIndex;
	protected byte maxIndex;
	
	protected Direcao dir;
	
	protected boolean moved;
	
	protected Sprite[] sprites;

	public AnimatedEntity(int x, int y, int width, int height, double speed, String name) {
		super(x, y, width, height, speed, null);
		
		this.frames    = 0;
		this.maxFrames = 7;
		
		this.index    = 0;
		this.minIndex = 0;
		this.maxIndex = 2;
		
		dir = Direcao.DIREITA;
		
		sprites = Game.spritesheet.getSprites(name);
	}
	
	public void animation() {
		
		if(moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if ( index > maxIndex) 
					index = minIndex;
				
			}
		}
		else {
			index = minIndex;
		}
	}

}
