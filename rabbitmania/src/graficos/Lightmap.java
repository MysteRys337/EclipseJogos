package graficos;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;

import zelda.Game;

public class Lightmap {
	
	private int width,height;
	private int[] pixels,lightmapPixels;
	private BufferedImage lightmap;
	
	public Lightmap (int width, int height) throws IOException {
		
		this.width = width;
		this.height = height;
		
		pixels = ((DataBufferInt)Game.layer.getRaster().getDataBuffer()).getData();
		lightmap = ImageIO.read(getClass().getResource("/lightmap.png"));
		lightmapPixels = new int[lightmap.getWidth()*lightmap.getHeight()];
		lightmap.getRGB(0, 0,lightmap.getWidth(),lightmap.getHeight(),lightmapPixels,0,lightmap.getWidth());
	}
	
	public void render() {
		
		for (int x = 0; x < width; x++) {
			
			for (int y = 0; y < height; y++) {
	
				if (lightmapPixels[x+(y * width)] == 0xffffffff) {
					
					int pixel = Pixel.getLightBlend(pixels[x+y*Game.getWIDTH()],0x808080,0);
					pixels[x + (y * width)] = pixel; 
				}
			}
		}
	}
}
