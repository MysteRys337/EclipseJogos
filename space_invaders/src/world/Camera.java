package world;

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
		return x+ "|" + y;
	}
	
	public static void setCoordinates(int newX,int newY) {
		x = newX;
		y = newY;
	}
	
	
}
