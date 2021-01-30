package entities.enemies;

import java.awt.Graphics;

import attributes.Timer;
import entities.Entity;
import scenes.Normal;
import world.Context;

public class EnemyController extends Entity{
	
	private Timer spawnTimer;
	private Timer upgradeSpeedTimer;
	private int enemySpeed;

	public EnemyController(int spawnTimer) {
		super(0,0, 1, 1, 0, null);
		this.spawnTimer = new Timer(spawnTimer);
		this.upgradeSpeedTimer = new Timer(spawnTimer * 10);
		this.enemySpeed = 1;
	}
	
	public void tick(Context ctx) {
		
		
		spawnTimer.tick();
		upgradeSpeedTimer.tick();
		
		if(upgradeSpeedTimer.is_stopped()) {
			
			if(gerador.nextInt(100) > 50) 
				this.enemySpeed++;
			else 
				spawnTimer.decrease(30);

			upgradeSpeedTimer.decrease(30);
			upgradeSpeedTimer.reset();
		}
		
		if(spawnTimer.is_stopped()) {
			spawnTimer.reset();
			
			int rng = gerador.nextInt(100);
			if(rng < 25 )
				Normal.spawnEntity(-20, gerador.nextInt(ctx.getScreenHEIGHT()), this.enemySpeed, "crab");
			else if(rng >= 25 && rng < 50 ) 
				Normal.spawnEntity(gerador.nextInt(ctx.getScreenWIDTH()), -20, this.enemySpeed, "crab");
			else if(rng >= 50 && rng < 75)
				Normal.spawnEntity(ctx.getScreenWIDTH(), gerador.nextInt(ctx.getScreenHEIGHT()), this.enemySpeed, "crab");
			else 
				Normal.spawnEntity(gerador.nextInt(ctx.getScreenWIDTH()), ctx.getScreenHEIGHT(), this.enemySpeed, "crab");
			
		}
		
	}
	
	public void render(Graphics g) {
		
	}

}