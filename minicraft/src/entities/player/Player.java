package entities.player;

import java.awt.Graphics;

import entities.AnimatedEntity;
import entities.Direcao;
import graphics.UI;
import items.Item;
import scenes.Normal;
import world.Camera;
import world.Context;
import world.World;
import items.blocks.*;

public class Player extends AnimatedEntity{
	
	private short life;
	private short maxLife;

	private boolean transformed;
	private boolean isJumping;
	private boolean isFalling;
	
	private double gravity = 0.4;
	private double vspd = 0;
	
	private UI ui;
	private Inventory inv;

	public Player(int x, int y, int width, int height, double speed,String name,UI ui) {
		super(x, y, width, height, speed, name);
		this.depth = 1;
		
		this.life = 5;
		this.maxLife = 5;
		
		this.ui = ui;
		
		this.frames    = 0;
		this.maxFrames = 7;
		
		this.index    = 0;
		this.minIndex = 0;
		this.maxIndex = 2;
		
		this.dir = Direcao.DIREITA;
		this.inv = new Inventory(ui.getWidth(),ui.getHeight(),ui.getScale());
		
		this.moved = this.transformed = this.isFalling = false;
		
		setMask(5,1,7,14);
		
	}
	
	public int getLife() {
		return (int)this.life;
	}
	
	public int getMaxLife() {
		return (int)this.maxLife;
	}

	public void tick(Context ctx,PlayerAction action) {
		
		//System.out.println(getCoordenates());
		inv.tick(action);
		
		movement(action);
		jumping(action);
		
		action(action);
		
		animation();
		
		updateCamera(ctx);
	}
	
	public void movement(PlayerAction action) {
		
		moved = false;
		
		if(action.getRight() && World.isFree(this.getX() + this.getSpeed(),this.getY(),maskW,maskH)) {
			
			moved = true;
			x+=speed;
			dir = Direcao.DIREITA;
		}
		else if(action.getLeft() && World.isFree(this.getX() - this.getSpeed(),this.getY(),maskW,maskH)) {
			
			moved = true;
			x-=speed;
			dir = Direcao.ESQUERDA;
		}
		if(action.getSpacebar()) {
			
			if(!World.isFree(this.getX(), this.getY() + 1, maskW, maskH)) {
				
				isJumping = true;
			} else {
				
				action.setMouse1(false);
			}
			
		} 
		
		if (World.isFree(this.getX(), this.getY() + this.getSpeed(),maskW,maskH) && !isJumping) {
			
			y += speed;
			isFalling = true;
		}
		else {
			isFalling = false;
		}
		
	}
	
	public void jumping(PlayerAction action) {
		
		vspd+=gravity;
		if(!World.isFree((int)x,(int)(y+1),maskW,maskH ) && isJumping)
		{
			vspd = -8;
			isJumping = false;
		}
		
		if(!World.isFree((int)x,(int)(y+vspd),maskW,maskH)) {
			
			int signVsp = 0;
			if(vspd >= 0)
			{
				signVsp = 1;
			}else  {
				signVsp = -1;
			}
			while(World.isFree((int)x,(int)(y+signVsp),maskW,maskH)) {
				y = y+signVsp;
			}
			vspd = 0;
		}
		
		y = y + vspd;
	}
	
	public void action(PlayerAction action) {
		
		if (action.getMouse1()) {
			
			action.setMouse1(false);
			int mx = action.getX() + Camera.x;
			int my = action.getY() + Camera.y;
			
			Block resp = Normal.removeBlock(mx,my);
			
			if(resp != null && !inv.isInventoryFull())
				inv.addItem(resp);
			
		} else if(action.getMouse2()) {
			action.setMouse2(false);
			
			Item currentItem = inv.getCurrentItem();
			
			if(currentItem == null) 
				return;
			
			if(currentItem instanceof Block) {
				int mx = action.getX() + Camera.x;
				int my = action.getY() + Camera.y;
				
				Normal.placeBlock(mx, my, (Block)currentItem);
			}
		}
	}
	
	public void animation() {
		
		if(transformed) {
			minIndex = 4;
			maxIndex = 6;
			
		} else {
			minIndex = 0;
			maxIndex = 2;
			
		}
		
		if(moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if ( index > maxIndex) 
					index = minIndex;
				
			}
		}
		else {
			index = minIndex;
		}
	}
	
	
	public void updateCamera(Context ctx) {
		
		//Configurando a camera para seguir o jogador
		//System.out.println(Camera.x + "|" + Camera.y);
		Camera.x = Camera.clamp(this.getX() - (ctx.getScreenWIDTH()/2),0,(ctx.getWorldWIDTH()*16)   - ctx.getScreenWIDTH());
		Camera.y = Camera.clamp(this.getY() - (ctx.getScreenHEIGHT()/2),0,(ctx.getWorldHEIGHT()*16) - ctx.getScreenHEIGHT());	
		
	}
	
	public void render(Graphics g) {	
		int direcao = (dir == Direcao.DIREITA ? 0 : 1);
		
		this.sprite = sprites[(isFalling ? 3+(direcao*4) :index+(direcao*4))];
		super.render(g);
		
		ui.render(g,this);
		
		inv.render(g);
	}
	
}
