package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Gameover extends Scene {
	
	private short       animationFrames;
	private final short animationFramesMax;
	private boolean     showMessageGameOver;

	public Gameover(int width, int height, int scale) {
		super(width, height, scale);
		animationFramesMax = 90;
	}
	
	public void tick() {
		
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

		if (action.getMouse1()) {
			//TODO
		}
	}
	
	public void render(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100));
		g2.fillRect(0, 0, mws, mhs);
		
		g.setColor(Color.red);
		g.setFont(new Font("arial", Font.BOLD, 25));
		g.drawString("Você Perdeu!", dws%dhs*scale/3, height/3);
		
		if (showMessageGameOver) 
			pressioneEnterParaContinuar(g);
		
	}
	
	public void pressioneEnterParaContinuar(Graphics g) {
		g.setColor(Color.green);
		g.setFont(new Font("arial", Font.BOLD, 20));
		
		int soma = dws + dhs;
		
		g.drawString("Pressione", 60, soma);
		g.drawString("\'Enter\'", 80, soma + (scale * 4));
		g.drawString("para continuar", 35, soma + (scale * 4) * 2);
	}

}
