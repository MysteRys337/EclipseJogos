package graphics;

import java.awt.Graphics;

public class Interface {

	protected int width;
	protected int height;
	protected int scale;
	
	//Variaveis para armazenar a divisão entre a altura, comprimento com a escala da tela
	protected final int dws;
	protected final int dhs;
	
	//Variaveis para armazenar a multiplicacao entre a altura, comprimento com a escala da tela
	protected final int mws;
	protected final int mhs;
	
	public Interface(int width,int height,int scale) {
		
		this.width  = width;
		this.height = height;
		this.scale  = scale;
		
		this.dws = width/scale;
		this.dhs = height/scale;
		
		this.mws = width*scale;
		this.mhs = height*scale;
		
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getScale() {
		return this.scale;
	}
	
	public void render(Graphics g) {
		
	}
}
