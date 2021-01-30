package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import main.Game;

public class Gameover extends Scene {
	
	private short       animationFrames;
	private final short animationFramesMax;
	private boolean     showMessageGameOver;

	public Gameover(int width, int height, int scale) {
		super(width, height, scale);
		animationFramesMax = 90;
	}
	
	public void tick() {
		
		if(action.getShoot()) {
			Game.resetScene(Gamestate.normal);
			Game.gamestate = Gamestate.normal;
		}
		
		animationFrames++;
		if( animationFrames == animationFramesMax) {
			
			animationFrames = 0;
			if (showMessageGameOver) {
				
				showMessageGameOver = false;
			}
			else {
				
				showMessageGameOver =  true;
			}
		}

	}
	
	public void render(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100));
		g2.fillRect(0, 0, width*scale, height*scale);
		
		int fontScale = 25;
		g.setColor(Color.red);
		g.setFont(new Font("arial", Font.BOLD, fontScale));
		g.drawString("Você Perdeu!", width/2 - (fontScale*scale)*2, height/2);
		
		if (showMessageGameOver) 
			pressioneEnterParaContinuar(g);
		
	}
	
	public void pressioneEnterParaContinuar(Graphics g) {
		g.setColor(Color.green);
		
		int fontScale = 20;
		g.setFont(new Font("arial", Font.BOLD, fontScale));
		
		int x = width*scale/(scale*3);
		
		g.drawString("Pressione", x + fontScale, height/2 + fontScale);
		g.drawString("\'Enter\'", x + fontScale*2, height/2 + (scale * fontScale));
		g.drawString("para continuar", x, height/2 + (scale * fontScale) + fontScale);
	}

}
