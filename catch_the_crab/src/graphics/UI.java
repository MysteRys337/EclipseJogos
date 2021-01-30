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
		

		g.setColor(Color.black);
		g.setFont(new Font("Century",Font.BOLD,12));
		g.drawString("POINTS: "+ p.getPoints() ,0, 10 );
	}
	
	

}
