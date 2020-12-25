package entities.enemies;

import java.awt.Color;
import java.awt.Graphics;

import entities.AnimatedEntity;
import scenes.Normal;
import world.Camera;
import world.Context;
import world.pathfinding.Pathfinding;

public class Enemy extends AnimatedEntity{
	
	private int life;
	private int maxLife;

	public Enemy(int x, int y, int width, int height, double speed, String name) {
		super(x, y, width, height, speed, name);
		
		this.maxIndex = 1;
		this.life     = 30;
		this.maxLife  = this.life;
		
		path = Pathfinding.findPath(Normal.world,x/16,y/16, Normal.player.getX()/16,Normal.player.getY()/16);
		
	}
	
	public void decreaseLife(int points) {
		this.life -= points;
	}
	
	public void tick(Context context) {
		
		if(life <= 0) {
			Normal.enemies.remove(this);
			Normal.entities.remove(this);
		}
		
		moved = true;
		followPath(path);
		
		animation();
	}
	

	public void render(Graphics g) {
		this.sprite = sprites[index];
		super.render(g);
		
		g.setColor(Color.red);
		g.fillRect(getX() - 6 - Camera.x, getY() - 10 - Camera.y,(int)(((double)life/maxLife)* 30), 6);
	}

}
