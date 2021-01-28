package graphics;

import java.awt.Graphics;

public class Interface {

	protected int width;
	protected int height;
	protected int scale;
	
	//Variaveis para armazenar a divisão entre a altura, comprimento com a escala da tela
	protected final int rws;
	protected final int rhs;
	
	//Variaveis para armazenar a divisão entre a altura, comprimento com a escala da tela
	protected final double dwh;
	protected final double dhw;
	
	//Variaveis para armazenar a multiplicacao entre a altura, comprimento com a escala da tela
	protected final int mws;
	protected final int mhs;
	
	//Variaveis para armazenar a multiplicacao entre a altura, comprimento com a escala da tela
	protected final int mwh;
	
	public Interface(int width,int height,int scale) {
		
		this.width  = width;
		this.height = height;
		this.scale  = scale;
		
		this.rws = width%scale;
		this.rhs = height%scale;
		
		this.dwh = width/height;
		this.dhw = height/width;
		
		this.mws = width*scale;
		this.mhs = height*scale;
		
		this.mwh = width*height;
		
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
