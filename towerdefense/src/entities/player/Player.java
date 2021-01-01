package entities.player;

import entities.Entity;
import entities.enemies.Enemy;
import graphics.sprite.Sprite;
import scenes.Normal;
import world.Camera;
import world.Context;

public class Player extends Entity{
	
	private int life;
	private int maxLife;
	private int money;
	
	private int xCamera;
	private int yCamera;
	
	public Player(int x, int y, int width, int height, double speed, Sprite sprite) {
		super(x, y, width, height, speed, sprite);
		
		this.xCamera = this.yCamera = 0;
		
		this.life    = 5;
		this.maxLife = this.life;
		this.money   = 100;
	}
	
	public int getLife() {
		return this.life;
	}
	
	public int getMaxLife() {
		return this.maxLife;
	}
	
	public void decreaseLife(int points) {
		this.life -= points;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public boolean isAlive() {
		return (this.getLife() > 0);
	}
	
	public void decreaseMoney(int points) {
		this.money -= points;
	}
	
	public void tick(Context ctx, PlayerAction action) {
		
		//System.out.println(action.print());
		
		movement(ctx,action);
		
		spawnTowers(action);
		
		collision();
		
		updateCamera(ctx,action);
	}
	
	private void movement(Context ctx, PlayerAction action) {
		
		if(action.getLeft() && xCamera > ctx.getScreenWIDTH()/2)
			xCamera -= speed;
		
		else if(action.getRight() && xCamera < ctx.getScreenWIDTH() - ctx.getWorldWIDTH())
			xCamera += speed;
		
		if(action.getDown() && yCamera < ctx.getScreenHEIGHT()) 
			yCamera += speed;
		
		else if(action.getUp() && yCamera > ctx.getScreenHEIGHT()/2) 
			yCamera -=speed;
	}
	
	private void spawnTowers(PlayerAction action) {
		if(action.getAction() && money > 20) 
			Normal.spawnEntity(action.getX() + Camera.x, action.getY() + Camera.y, "tower_gun");
		
	}
	
	private void collision() {
		
		for(Enemy e : Normal.enemies) {
			if(Entity.isColidding(e,this)) {
				decreaseLife(1);		
				Normal.entities.remove(e);
				Normal.enemies.remove(e);
				break;
			}
		}
	}
	
	private void updateCamera(Context ctx,PlayerAction action) {
		
		//Configurando a camera para seguir o jogador
		Camera.x = Camera.clamp(xCamera - (ctx.getScreenWIDTH()/2),0,(ctx.getWorldWIDTH()*16)   - ctx.getScreenWIDTH());
		Camera.y = Camera.clamp(yCamera - (ctx.getScreenHEIGHT()/2),0,(ctx.getWorldHEIGHT()*16) - ctx.getScreenHEIGHT());	
		
	}
}
