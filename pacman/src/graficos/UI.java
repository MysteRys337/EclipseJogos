package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import pacman.Game;

public class UI {
	
	public void render(Graphics g) {
		
		g.setColor(Color.yellow);
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.drawString("Pontos: " + Game.player.getPontos(), 5, 8);
	}
	
	public void renderPause(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100));
		g2.fillRect(0, 0, Game.getWIDTH()*Game.SCALE, Game.getHEIGHT()*Game.SCALE);
		
		g.setColor(Color.green);
		g.setFont(new Font("arial", Font.BOLD, 18));
		g.drawString("Pausado", 80, 80);
	}
	
	public void renderWin(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100));
		g2.fillRect(0, 0, Game.getWIDTH()*Game.SCALE, Game.getHEIGHT()*Game.SCALE);
		
		g.setColor(Color.yellow);
		g.setFont(new Font("arial", Font.BOLD, 18));
		g.drawString("Você Ganhou!", 52, 85);
	}
}
