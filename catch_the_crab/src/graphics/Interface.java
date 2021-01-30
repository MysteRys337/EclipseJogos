package graphics;

import java.awt.Graphics;

public class Interface {

	protected int width;
	protected int height;
	protected int scale;
	
	public Interface(int width,int height,int scale) {
		
		this.width  = width;
		this.height = height;
		this.scale  = scale;
		
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
