package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import entities.Entity;
import world.World;
import zelda.Game;

public class UI {
	
	private BufferedImage minimap;
	private int[] minimapPixels;
	
	
	public void setMinimap(int WIDTH,int HEIGHT) {
		
		minimap = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
		minimapPixels = ((DataBufferInt)minimap.getRaster().getDataBuffer()).getData();
	}
	
	public void renderPlayerStats(Graphics g) {
		
		//Renderizando barra de vida
		
		//Renderizando barra vermelha
		g.setColor(Color.red);		
		g.fillRect(8, 4, 50, 8);
		
		//Renderizando a barra verde(vida) com base na vida do player
		g.setColor(Color.green);
		g.fillRect(8, 4, (int)((Game.player.getLife()/Game.player.getMaxLife())*50), 8);
		
		//Renderizando o contador da vida do player
		g.setColor(Color.white);
		g.setFont(new Font("Century",Font.BOLD,7));
		g.drawString((int)Game.player.getLife()+"/"+(int)Game.player.getMaxLife(), 15, 11);
		
		g.setFont(new Font("Century",Font.BOLD,10));
		
		//Renderizando contador de municao
		if (Game.player.getWeapon().getType() != -1) {
			
			if (Game.player.getAmmo() >= 10 && Game.player.getReserveAmmo() >= 10 ) {
				
				g.drawString("Ammo: " + Game.player.getAmmo() + "/" + Game.player.getReserveAmmo(), 161, 11);
			}
			else if (Game.player.getReserveAmmo() >= 10 ) {
				
				g.drawString("Ammo: " + Game.player.getAmmo() + "/" + Game.player.getReserveAmmo(), 167, 11);
			}
			else if (Game.player.getAmmo() >= 10 ){
				
				g.drawString("Ammo: " + Game.player.getAmmo() + "/" + Game.player.getReserveAmmo(), 165, 11);
			}
			else {
				
				g.drawString("Ammo: " + Game.player.getAmmo() + "/" + Game.player.getReserveAmmo(), 175, 11);
			}
			
			g.drawRect(215, 15, 20, 20);
			if(Game.player.getWeapon().getType() == 0) 
				
				g.drawImage(Entity.GUN_RIGHT,217, 21, null);
			else if (Game.player.getWeapon().getType() == 1)
				
				g.drawImage(Entity.GUN2_RIGHT,218, 17, null);
		}	
		
	}
	
	public void renderMinimap(Graphics g) {
		
		World.renderMinimap(minimapPixels);
		g.drawImage(minimap,100,50,World.WIDTH*4,World.HEIGHT*4,null);
	}
	
	public void renderGameOver(Graphics g,boolean message) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100));
		g2.fillRect(0, 0, Game.getWIDTH()*Game.SCALE, Game.getHEIGHT()*Game.SCALE);
		
		g.setColor(Color.RED);
		g.setFont(Game.pixelfont64);
		g.drawString("Game Over!", 240,350);
		
		g.setColor(Color.WHITE);
		g.setFont(Game.pixelfont48);
		if (message) {
			g.drawString(">Pressione", 270,450);
			g.drawString(">\"Enter\"", 320,520);
			g.drawString(">para reiniciar", 200,590);
		}
	}
	
	public void renderReload(Graphics g) {
		
		if(Game.player.isReloading) {
			
			float reloadFrames = Game.player.getWeapon().getReloadFrames();
			float maxReloadFrames = Game.player.getWeapon().getMaxReloadFrames();
			
			g.setColor(Color.black);		
			g.fillRect(188, 37, 65, 8);
			g.setColor(Color.blue);
			g.fillRect(188,37, (int)((reloadFrames / maxReloadFrames)*50), 8);
			
			g.setColor(Color.white);
			g.setFont(new Font("Century",Font.BOLD,7));
			g.drawString("Reloading...", 190, 44);
			
		}
	}
}
