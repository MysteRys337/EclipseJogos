package entities.enemies;

import java.awt.Color;
import java.awt.Graphics;

import attributes.Timer;
import entities.Entity;
import graphics.sprite.Sprite;
import scenes.Normal;
import world.Camera;
import world.Context;

public class EnemySpawner extends Entity{
	
	private Timer timer;

	public EnemySpawner(int x, int y, int width, int height, double speed, Sprite sprite) {
		super(x, y, width, height, speed, sprite);
		this.timer = new Timer();
	}
	
	public void tick(Context ctx) {
		timer.tick();
		if(timer.is_stopped()) {
			
			timer.reset();
			Normal.spawnEntity(getX() + 32, getY(), "enemy_dynamite");
		}
	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.red);
		g.fillRect(getX() - Camera.x, getY() - Camera.y, width, height);
	}

}