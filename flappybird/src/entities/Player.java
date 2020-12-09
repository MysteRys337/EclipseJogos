package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import flappybird.Game;
import world.Camera;
import world.Gamestate;
import world.World;

public class Player extends Entity{
	
	//Variaveis de movimento
	public boolean isPressed = false;
	
	//Variaveis de estatísticas
	private double pontos;
	
	//Variaveis de animacao do movimento
	private byte frames    = 0;
	private byte maxFrames = 4;
	private byte index     = 0;
	private byte maxIndex  = 2;
	public BufferedImage[] sprites;
	
	public int getPontos() {
		return (int)this.pontos;
	}
	
	public void aumentarPontos() {
		this.pontos += 0.5;
	}

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		
		//Chamando construtor da classe Entity
		super(x, y, width, height, speed, sprite);
		depth   = 1;
		pontos  = 0;
		
		byte numOfSprites = (byte)(maxIndex +1);
		
		//Iniciando todos arrays de sprites 
		sprites = new BufferedImage[numOfSprites];
		
		for ( int i = 0 ; i < numOfSprites ; i++ ) {
			sprites[i] = Game.spritesheet.getSprite((i)*16, 0, 16, 16);
		}
		
	}

	public void tick() {
		
		movement();	
		animation();
		checkCollision();
		updateCamera();
	}
	
	public void checkCollision() {
		

	}
	
	public void animation() {
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if ( index > maxIndex) 
				index = 0;
			
		}
		
	}
	
	public void movement() {
		
		if (!isPressed) {
			y+=2;
		
		}else { 
			if ( y > 0)
				y-=2;
			
		}
		
		if (y > Game.getHEIGHT()  )
			Game.gamestate = Gamestate.gameover;
			
	}
	
	public void updateCamera() {
		
		//Configurando a camera para seguir o jogador
		Camera.x = Camera.clamp((int)x - (Game.getWIDTH()/2),0,(World.WIDTH*16) - Game.getWIDTH());
		Camera.y = Camera.clamp((int)y - (Game.getHEIGHT()/2),0,(World.HEIGHT*16) - Game.getHEIGHT());	
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		if(!isPressed) {
			g2.rotate(Math.toRadians(2), this.getX() + width/2, this.getY() + height/2);
			g.drawImage(sprites[index],(int)x,(int)y, null);
			g2.rotate(Math.toRadians(-2), this.getX() + width/2, this.getY() + height/2);
			
		} else {
			g2.rotate(Math.toRadians(-2), this.getX() + width/2, this.getY() + height/2);
			g.drawImage(sprites[index],(int)x,(int)y, null);
			g2.rotate(Math.toRadians(2), this.getX() + width/2, this.getY() + height/2);
		}
	}
	
}
