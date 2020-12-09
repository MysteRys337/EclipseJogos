package world;

import java.util.Random;

import entities.Entity;
import entities.Pipe;
import flappybird.Game;

public class PipeGenerator {

	public int       time              = 0;
	public final int targetTime        = 120;
	public final int spaceBetweenPipes = 200;
	
	private Random   rng               = new Random();
	
	public void tick() {
		time++;
		if(time >= targetTime) {
			
			int randY  = rng.nextInt(50) + 55;
			
			Pipe floor = new Pipe(Game.getWIDTH(),randY,26,160,1,Entity.PIPE);
			Pipe ceil  = new Pipe(Game.getWIDTH(),randY - spaceBetweenPipes,26,160,1,Entity.PIPE_REVERSED);
			
			Game.entities.add(floor);
			Game.entities.add(ceil);
			
			time = 0;
			
		}
	}
}
