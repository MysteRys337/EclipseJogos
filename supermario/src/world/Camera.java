package world;

import main.Game;

public class Camera {

	public static int x = 0;
	public static int y = 0;
	
	public static int clamp(int xAtual,int xMin,int xMax) {
		if (xAtual < xMin) {
			xAtual = xMin;
		}
		if (xAtual > xMax) {
			xAtual = xMax;
		}
		
		return xAtual;
	}
	
	public static String getCoordinates() {
		return getX() + "|" + getY();
	}
	
	public static int getX() {
		
		int xstart = x >> 4,xfinal = xstart + (Game.getWIDTH() >> 4);
		return xfinal - xstart;
	}
	
	public static int getY() {
		
		int ystart = y >> 4,yfinal = ystart + (Game.getWIDTH() >> 4);
		return yfinal - ystart;
	}
	
	public static void setX(int newX) {
		x = newX;
	}
	
	public static void setY(int newY) {
		y = newY;
	}
}
