package main;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import rafgfxlib.Util;

public class Mountains{
	
	private int rgba [] = new int [4];
	private WritableRaster raster;
	private BufferedImage image;
	private int width;
	private int height; 
	private double scal;
	private int pom;
	private int amplitudeHeight;
	private int position;
	private int left[];
	private int right[];
	private double gradientHeight;

	public Mountains(int width, int height, double scal, int pom, int amplitudeHeight, int position, int left[], int right[], double gradientHeight) {

		this.raster = Util.createRaster(width, height, true);
		this.width = width;
		this.height = height;
		this.scal = scal;
		this.pom = pom;
		this. amplitudeHeight = amplitudeHeight;
		this.position = position;
		this.left = left;
		this.right = right;
		this.gradientHeight = gradientHeight;
		
	}
	
	public int lerp (int a, int b, double x){
		return (int)(a + (b - a) * x);
	}

	public BufferedImage createMountain(){
		
		for(int y = 0; y < raster.getHeight(); y++)
		{
			for(int x = 0; x < raster.getWidth(); x++)
			{
				double sineY = Math.sin(x * scal + pom) * amplitudeHeight + position;
				
				//double delta = Math.abs(y - sineY);
				
				if (y > sineY ){
					
					double fy = (y % (int)gradientHeight) / gradientHeight ;
					rgba[0] = lerp(left[0], right[0], fy);
					rgba[1] = lerp(left[1], right[1], fy);
					rgba[2] = lerp(left[2], right[2], fy);
					rgba[3] = 255;
					
				} else {
					
					rgba[0] = 0;
					rgba[1] = 0;
					rgba[2] = 56;
					rgba[3] = 0;
				}
				
				raster.setPixel(x, y, rgba);
			
			}
			
		}
		image = Util.rasterToImage(raster);
		
		return image;
	}
	
	
}
