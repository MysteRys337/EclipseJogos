package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entities.player.Player;
import world.Context;

public class UI extends Interface{

	public UI(int width, int height, int scale) {
		super(width, height, scale);
	}
	
	public void render(Graphics g,Player p, Context ctx) {
		
		g.setColor(Color.black);
		g.fillRect(dws/scale - 15, mhs/scale - 20,ctx.getScreenWIDTH(),20 );
		
		g.setColor(Color.white);
		g.setFont(new Font("Century",Font.BOLD,10));
		g.drawString("LIFE:", dhs/scale-5, mhs/scale - 7);
		g.drawString("$" + p.getMoney(), mws/scale-35, mhs/scale - 7);
		
		g.setColor(Color.red);
		for (int i = 0 ; i < p.getMaxLife(); i++) 
			g.fillRect(dws/scale + 18 + (i*15), mhs/scale - 15,10,10 );
		
		g.setColor(Color.green);
		for (int i = 0 ; i < p.getLife(); i++) 
			g.fillRect(dws/scale + 18 + (i*15), mhs/scale - 15,10,10 );
		
	}
	
	

}
