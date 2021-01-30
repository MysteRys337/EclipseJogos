package entities.player;

import java.awt.Graphics;

import entities.Entity;
import graphics.UI;
import graphics.sprite.Sprite;
import scenes.Normal;
import world.Camera;
import world.Context;

public class Player extends Entity{

	private boolean isAlive;

	private UI ui;
	private int points;

	public Player(int x, int y, int width, int height,UI ui, Sprite sprite) {
		super(x, y, width, height, 0, sprite);
		this.ui = ui;
	}

	
	public boolean getLifeStatus() {
		return this.isAlive;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}

	public void tick(Context ctx,PlayerAction action) {
		
		//System.out.println(getCoordenates());
		//System.out.println(action.print());
		
		x = ctx.getScreenWIDTH()/2 - 30;
		y = ctx.getScreenHEIGHT()/2 - 28;
		
		movement(ctx,action);
		
		action(ctx,action);
		
		updateCamera(ctx);
	}
	
	public void movement(Context ctx,PlayerAction action) {
		
	}
	
	public void action(Context ctx,PlayerAction action) {
		
		if (action.getShoot()) {
			action.setShoot(false);
			Normal.killEnemy(action.getX(),action.getY() );
		}
	}
	
	
	public void updateCamera(Context ctx) {
		
		//Configurando a camera para seguir o jogador
		//System.out.println(Camera.x + "|" + Camera.y);
		Camera.x = Camera.clamp(this.getX() - (ctx.getScreenWIDTH()/2),0,(ctx.getWorldWIDTH()*16)   - ctx.getScreenWIDTH());
		Camera.y = Camera.clamp(this.getY() - (ctx.getScreenHEIGHT()/2),0,(ctx.getWorldHEIGHT()*16) - ctx.getScreenHEIGHT());	
		
	}
	
	public void render(Graphics g) {	
		g.drawImage(sprite.getBufferedImage(), getX(), getY(), null);
		
		ui.render(g,this);
	}
	
}
