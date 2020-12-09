package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import flappybird.Game;
import world.Gamestate;

public class Pipe extends Entity{
	
	private boolean hasPassed = false;

	public Pipe(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		x--;
		if(x+width < 0) {
			Game.entities.remove(this);
			return;
		}
		
		if(isColidding(Game.player,this)) {
			Game.gamestate = Gamestate.gameover;
		}
		
		if(!hasPassed) {
			if(Game.player.getX() == this.x) {
				hasPassed = true;
				Game.player.aumentarPontos();
				
			}
			
		}
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,(int)x,(int)y, null);
	}


}
