package graphics;

import java.awt.Color;
import java.awt.Graphics;

import entities.player.Player;

public class UI extends Interface{

	public UI(int width, int height, int scale) {
		super(width, height, scale);
	}
	
	public void render(Graphics g,Player p) {
		
		g.setColor(Color.red);
		for (int i = 0 ; i < p.getMaxLife(); i++) 
			g.fillRect(dws/scale - 12 + (i*15), 20,7,7 );
		
		g.setColor(Color.green);
		for (int i = 0 ; i < p.getLife(); i++) 
			g.fillRect(dws/scale - 12 + (i*15), 20,7,7 );
		
	}
	
	

}
