package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entities.player.Player;

public class UI extends Interface{

	public UI(int width, int height, int scale) {
		super(width, height, scale);
	}
	
	public void render(Graphics g,Player p) {
		

		g.setColor(Color.white);
		g.setFont(new Font("Century",Font.BOLD,10));
		g.drawString("POINTS: "+ p.getPoints() ,(int)(dwh), 20 );
	}
	
	

}
