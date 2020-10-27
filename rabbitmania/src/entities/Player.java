package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import world.Camera;
import world.World;
import zelda.Game;
import zelda.Sound;

public class Player extends Entity{

	//Variaveis de movimento
	public boolean right,up,left,down;
	private byte dir;
	public int mx,my;
	public double speed = 1.4;
	
	//Variaveis de estatisticas
	private double life = 100,maxLife = 100;
	private byte deathFrames = 60;
	public boolean isDamaged = false;
	public boolean isShooting = false,isShootingMouse = false,isReloading = false;

	public Weapon[] weapons;
	public byte weaponSelected = 0; 
	public int[] reserveAmmo;
	public boolean weaponUp,weaponDown;
	/*
	//fake jump
	public boolean jump = false,isJumping = false,jumpUp = false,jumpDown = false;
	public int jumpFrames = 35,jumpHeight = 0,jumpSpeed = 2;
	*/
	
	//Variaveis de animacao do movimento
	private byte frames = 0,maxFrames = 6,index = 0,maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer,leftPlayer,upPlayer,downPlayer;
	
	private static BufferedImage playerDamage;
	
	//Metodos get
	public double getMaxLife() {
		
		return this.maxLife;
	}
	public double getLife() {
		
		return this.life;
	}
	
	public int getAmmo() {
		
		return weapons[weaponSelected].getAmmo();
	}
	
	public int getReserveAmmo() {
		return reserveAmmo[weaponSelected];
	}
	
	public Weapon getWeapon() {
		return weapons[weaponSelected];
	}
	
	//Metodos set
	public void setLife(int life) {
		this.life = life;
	}
	
	public boolean hasGun() {
		return (weapons[weaponSelected].getType() == -1) ? false : true; 
	}
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		
		//Chamando construtor da classe Entity
		super(x, y, width, height, sprite);
		
		weapons = new Weapon[2];
		for (int i = 0 ; i < weapons.length; i++) {
			
			weapons[i] = new Weapon();
		}
		reserveAmmo = new int[2];
		
		depth = 1;
		
		setMask(2,5,8,8);
		
		//Iniciando os sprites do jogador
		
		//Sprite de dano
		playerDamage = Game.spritesheet.getSprite(0, 96, 16, 16);
		
		//Iniciando todos arrays de sprites 
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		//Inserindo todos os sprites em todos vetores
		for (int i = 0; i < rightPlayer.length; i++ ) {
			
			rightPlayer[i] = Game.spritesheet.getSprite((i)*16, 32, 16, 16);
		}
		
		for (int i = 0; i < leftPlayer.length; i++ ) {
			
			leftPlayer[i] = Game.spritesheet.getSprite((i)*16, 48, 16, 16);
		}
		
		for (int i = 0; i < rightPlayer.length; i++ ) {
			
			upPlayer[i] = Game.spritesheet.getSprite((i)*16, 64, 16, 16);
		}
		
		for (int i = 0; i < rightPlayer.length; i++ ) {
			
			downPlayer[i] = Game.spritesheet.getSprite((i)*16, 80, 16, 16);
		}
		
	}

	public void tick() throws IOException {
		
		
		//Movimentacao
		movement();
		
		//Checando colisao com todos os items no mapa
		checkItemCollision();
		
		//Verificando se o jogador tomou dano
		if(isDamaged) {
			
			deathFrames++;
			if( deathFrames >= 30) {
				
				isDamaged = false;
			}
		}
		
		//Funcao com todas as interacoes com armas
		gunInteractions();
		
		//Verificando se o jogador ainda está vivo
		if ( life <= 0 ) {
			
			life = 0;
			Game.gameState = "GAME OVER";
		}
		
		updateCamera();
	}
	
	public void movement() {
		
		//Movimentacao
		
		/*if (jump) {
			
			if (!isJumping) {
				
				jump = false;
				isJumping = true;
				jumpUp = true;
			}
		}
		
		if (isJumping) {
		
				if(jumpUp) {
					
					jumpHeight+=jumpSpeed;
				}
				else if ( jumpDown ) {
					
					jumpHeight-=jumpSpeed;
					if (jumpHeight <= 0) {
						
						isJumping = false;
						jumpDown = false;
						jumpUp = false;
					}
				}
				z = jumpHeight;
				if (jumpHeight >= jumpFrames) {
					
					jumpUp = false;
					jumpDown = true;
				}
		}
		*/
		
		if(right && World.isFree((int)(x+speed),(int)y,width,height,z)) {
			
			moved = true;
			dir = 0;
			x+=speed;
		}
		else if(left && World.isFree((int)(x-speed),(int)y,width,height,z)) {
			
			moved = true;
			dir = 1;
			x-=speed;
		}
		if (up && World.isFree((int)x,(int)(y-speed),width,height,z)) {
			
			moved = true;
			dir = 2;
			y-=speed;
		}
		else if (down && World.isFree((int)x,(int)(y+speed),width,height,z)) {
			
			moved = true;
			dir = 3;
			y+=speed;
		}
		
		if (moved) {
			
			moved = false;
			frames++;
			if (frames == maxFrames) {
				
				frames = 0;
				index++;
				if ( index > maxIndex) {
					
					index = 0;
				}
			}
		}
	}
	
	public void gunInteractions() {
		
		for ( int i = 0 ; i < weapons.length; i++ ) {
			weapons[i].delay++;
		}
		
		if(weaponUp) {
			
			isReloading = false;
			for (int i = 0; i < weapons.length ; i++)
				weapons[i].resetReloadFrames();
			
			weaponUp = false;
			if(weaponSelected == weapons.length -1 ) {
					
				weaponSelected = 0;
			}
			else {
					
				weaponSelected++;
			}
		}
		else if(weaponDown) {
			
			isReloading = false;
			for (int i = 0; i < weapons.length ; i++)
				weapons[i].resetReloadFrames();
			
			weaponDown = false;
			if(weaponSelected == 0) {
					
				weaponSelected = (byte)(weapons.length - 1);
			}
			else {
					
				weaponSelected--;
			}
		}
		
		if(isReloading) {
			
			reserveAmmo[weaponSelected] = weapons[weaponSelected].reload(reserveAmmo[weaponSelected]);
			
		}
		
		//Sistema de atirar com o "espaco"
		if (isShooting && hasGun() && Game.player.getAmmo() > 0 && !isReloading ) {

			Game.sound.play(Sound.pistolFire);
			weapons[weaponSelected].fire();
			int dx = 0,dy = 0,px = 0,py = 0;
			isShooting = false;
			
			Bullet bullet = null;
			
				if(dir == 0) { // direita
					
					dx = 1;
					py = -2;
					px = 16;
				}
				else if (dir == 1){ // esquerda
					
					dx = -1;
					py = -2;
					px = -10;
				}
				else if (dir == 2) { // cima
					
					dy = -1;
					px = 6;
				}
				else { // baixo
					
					dy = 1;
					py = 4;
					px = 7;
				}
				
				bullet = new Bullet((int)(this.getX() + px),(int)(this.getY() + py),width,height,null,dx,dy,getWeapon().getDamage(),0);
				Game.bullets.add(bullet);

			
		}
		else if (isShootingMouse && hasGun() && Game.player.getAmmo() > 0 && !isReloading) {
			 
			Game.sound.play(Sound.pistolFire);
			
			weapons[weaponSelected].fire();
			
			double dx = 0,dy = 0,px = 0,py = 0;
			
			isShootingMouse = false;
			
			double angle = Math.atan2(my - (this.getY() - Camera.y), mx - (this.getX()+6 - Camera.x));
			dx = Math.cos(angle);
			dy = Math.sin(angle);
				
				if(dir == 0) {
					
					px = 20;
				}
				else if (dir == 1){
					
					px = -6;
				}
				else if (dir == 2) {
	
					px = 10;
				}
				else {
	
					py = 4;
					px = 9;
				}
				
				
				Bullet bullet = new Bullet((int)(this.getX() + px),(int)(this.getY() + py),width,height,null,dx,dy,getWeapon().getDamage(),0);
				Game.bullets.add(bullet);


		}
		
	}
	
	public void updateCamera() {
		
		//Configurando a camera para seguir o jogador
		Camera.x = Camera.clamp((int)x - (Game.getWIDTH()/2),0,(World.WIDTH*16) - Game.getWIDTH());
		Camera.y = Camera.clamp((int)y - (Game.getHEIGHT()/2),0,(World.HEIGHT*16) - Game.getHEIGHT());	
	}
	
	//Checar colisão com todos os itens
	public void checkItemCollision() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			
			Entity e = Game.entities.get(i);
			if ( e instanceof LifePack) { // Pegando pacote de vida
				
				if ( Entity.isColidding(this,e) && life < 100) {
					
					life += Game.gerador.nextInt(20) + 5;
					if ( life >= 100) {
						
						life = 100;
					}
					Game.entities.remove(i);
					return;
				}
			}
			else if ( e instanceof Ammo) { // Pegando municao
				
				if ( Entity.isColidding(this,e)) {
					
					if ( e.getType() == 0 ) 
						
						reserveAmmo[e.getType()] += 13;
					
					else 
						
						reserveAmmo[e.getType()] += 32;
					
					if ( reserveAmmo[e.getType()] >= 99) {
						
						reserveAmmo[e.getType()] = 99;
					}
					Game.entities.remove(i);
					return;
				}
			}
			else if ( e instanceof Weapon) { // Pegando a arma 
				
				if ( Entity.isColidding(this,e)) {
					
					weapons[e.getType()] = (Weapon)e;
					Game.entities.remove(i);
					
					return;
				}
			}
		}
	}
	
	//Diminuindo a vida do jogador se o mesmo for atacado
	public void decreaseLife() {
		
		if ( Game.gerador.nextInt(100) > 90 ) { 
			
			Game.sound.play(Sound.playerDamage);
			isDamaged = true;
			deathFrames = 0;
			life -= Game.gerador.nextInt(10);
		}
	}
	
	//Renderizar o jogador na tela
	public void render(Graphics g) {
		
		if (!isDamaged) { // Renderizando sprite quando o jogador nao esta tomando dano
			switch(dir) {
			
			case 0: // direita
				
				g.drawImage(rightPlayer[index],(int)x - Camera.x, (int)y - Camera.y - z, null);
				if( weapons[weaponSelected].getType() != -1 ) {
					
					if (weapons[weaponSelected].getType() == 0 )
						
						g.drawImage(Entity.GUN_RIGHT,(int)(x - Camera.x + 5), (int)y - Camera.y - z, null);
					else
						
						g.drawImage(Entity.GUN2_RIGHT,(int)(x - Camera.x + 9), (int)y - Camera.y - z, null);
				}
				break;
			case 1: // esquerda
				
				g.drawImage(leftPlayer[index],(int)x - Camera.x , (int)y - Camera.y - z, null);
				if ( weapons[weaponSelected].getType() != -1  ) {
					
					if (weapons[weaponSelected].getType() == 0 )
						
						g.drawImage(Entity.GUN_LEFT,(int)(x - Camera.x - 11), (int)y - Camera.y - z, null);
					else
						
						g.drawImage(Entity.GUN2_LEFT,(int)(x - Camera.x - 15), (int)y - Camera.y - z, null);
				}
				break;
			case 2: // cima
				
				g.drawImage(upPlayer[index],(int)x - Camera.x , (int)y - Camera.y - z, null);
				break;
			case 3: // baixo
				
				g.drawImage(downPlayer[index],(int)x - Camera.x , (int)y - Camera.y - z, null);
				if ( weapons[weaponSelected].getType() != -1  ) {
					
					if (weapons[weaponSelected].getType() == 0)
						
						g.drawImage(Entity.GUN_DOWN,(int)(x - Camera.x ), (int)y - Camera.y - z, null);
					else
						
						g.drawImage(Entity.GUN2_DOWN,(int)(x - Camera.x ), (int)y - Camera.y - z, null);
				}
				break;
			default:
				
				g.drawImage(rightPlayer[index],(int)x - Camera.x, (int)y - Camera.y - z, null);
				break;
			}
		}
		else { // Renderizando sprite de dano
			
			g.drawImage(playerDamage, (int)(x-Camera.x),(int)(y-Camera.y) - z,null);
		}
		/*
		if(isJumping) {
			g.setColor(Color.BLACK);
			g.fillOval( (int)(x-Camera.x)+5,(int)(y-Camera.y)+15,8,8);
		}
		*/
		
	}
	
}
