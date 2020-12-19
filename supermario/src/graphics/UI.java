package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UI extends Interface{

	public UI(int width, int height, int scale) {
		super(width, height, scale);
	}
	
	public void render(Graphics g,int points) {
		
		g.setColor(Color.white);
		g.setFont(new Font("Century",Font.BOLD,10));
		g.drawString("Pontos:", dhs/scale-5, dws/scale-5);
		g.drawString(String.valueOf(points), dhs/scale + 11, dws/scale + 5);
	}
	
	

}
