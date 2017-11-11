package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class StarSet {
private final BufferedImage[][] stars;
	
	public BufferedImage getStar(int size, int brightness)
	{
		return stars[size][brightness];
	}
	
	public StarSet()
	{
		this(Color.WHITE);
	}
	
	
	public StarSet(Color color)
	{
		stars = new BufferedImage[5][6];
		
		int[] pixel = new int[4];

		for (int i = 0; i < 6; i++)
		{
			BufferedImage newImage = 
					new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);	
			WritableRaster raster = newImage.getRaster();
			
			pixel[0] = Math.max(0, color.getRed() - i * 24);
			pixel[1] = Math.max(0, color.getGreen() - i * 24);
			pixel[2] = Math.max(0, color.getBlue() - i * 24);
			pixel[3] = 255;
		
			raster.setPixel(0, 0, pixel);
			stars[0][i] = newImage;
			
		}
		
		for (int i = 0; i < 6; i++)
		{
			BufferedImage newImage = 
					new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);	
			WritableRaster raster = newImage.getRaster();
			
			pixel[0] = Math.max(0, color.getRed() - i * 24);
			pixel[1] = Math.max(0, color.getGreen() - i * 24);
			pixel[2] = Math.max(0, color.getBlue() - i * 24);
			pixel[3] = 255;
		
			raster.setPixel(1, 1, pixel);
			
			pixel[3] = 96;
			raster.setPixel(0, 1, pixel);
			raster.setPixel(1, 0, pixel);
			raster.setPixel(1, 2, pixel);
			raster.setPixel(2, 1, pixel);
			
			pixel[3] = 32;
			raster.setPixel(0, 0, pixel);
			raster.setPixel(0, 2, pixel);
			raster.setPixel(2, 0, pixel);
			raster.setPixel(2, 2, pixel);			
			
			stars[1][i] = newImage;
			
		}
		
		for (int i = 0; i < 6; i++)
		{
			BufferedImage newImage = 
					new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);	
			WritableRaster raster = newImage.getRaster();
			
			pixel[0] = Math.max(0, color.getRed() - i * 24);
			pixel[1] = Math.max(0, color.getGreen() - i * 24);
			pixel[2] = Math.max(0, color.getBlue() - i * 24);
			pixel[3] = 255;
		
			raster.setPixel(2, 2, pixel);
			
			pixel[3] = 128;
			raster.setPixel(1, 2, pixel);
			raster.setPixel(3, 2, pixel);
			raster.setPixel(2, 1, pixel);
			raster.setPixel(2, 3, pixel);

			pixel[3] = 96;
			raster.setPixel(1, 1, pixel);
			raster.setPixel(1, 3, pixel);
			raster.setPixel(3, 1, pixel);
			raster.setPixel(3, 3, pixel);
			
			pixel[3] = 64;
			raster.setPixel(0, 2, pixel);
			raster.setPixel(4, 2, pixel);		
			raster.setPixel(2, 0, pixel);
			raster.setPixel(2, 4, pixel);
			
			pixel[3] = 32;
			raster.setPixel(0, 1, pixel);
			raster.setPixel(0, 3, pixel);
			raster.setPixel(4, 1, pixel);
			raster.setPixel(4, 3, pixel);
			raster.setPixel(1, 0, pixel);
			raster.setPixel(1, 4, pixel);
			raster.setPixel(3, 0, pixel);
			raster.setPixel(3, 4, pixel);
			
			pixel[3]=16;
			raster.setPixel(0, 0, pixel);
			raster.setPixel(0, 4, pixel);
			raster.setPixel(4, 0, pixel);
			raster.setPixel(4, 4, pixel);
			
			stars[2][i] = newImage;
			
		}	
		

			
		for (int i = 0; i < 6; i++)
		{
			BufferedImage newImage = 
					new BufferedImage(7, 7, BufferedImage.TYPE_INT_ARGB);	
			WritableRaster raster = newImage.getRaster();
			
			pixel[0] = Math.max(0, color.getRed() - i * 24);
			pixel[1] = Math.max(0, color.getGreen() - i * 24);
			pixel[2] = Math.max(0, color.getBlue() - i * 24);
			pixel[3] = 255;
	
			raster.setPixel(3, 3, pixel);
			
			pixel[3] = 192;
			raster.setPixel(2, 3, pixel);
			raster.setPixel(3, 2, pixel);
			raster.setPixel(3, 4, pixel);
			raster.setPixel(4, 3, pixel);

			
			pixel[3] = 128;
			raster.setPixel(1, 3, pixel);
			raster.setPixel(2, 2, pixel);
			raster.setPixel(2, 4, pixel);
			raster.setPixel(3, 1, pixel);
			raster.setPixel(3, 5, pixel);
			raster.setPixel(4, 2, pixel);
			raster.setPixel(4, 4, pixel);
			raster.setPixel(5, 3, pixel);

			
			pixel[3] = 96;
			raster.setPixel(0, 3, pixel);
			raster.setPixel(1, 2, pixel);
			raster.setPixel(1, 4, pixel);
			raster.setPixel(2, 1, pixel);
			raster.setPixel(2, 5, pixel);
			raster.setPixel(3, 0, pixel);
			raster.setPixel(3, 6, pixel);
			raster.setPixel(4, 1, pixel);
			raster.setPixel(4, 5, pixel);
			raster.setPixel(5, 2, pixel);
			raster.setPixel(5, 4, pixel);
			raster.setPixel(6, 3, pixel);
			
			pixel[3] = 64;
			raster.setPixel(0, 2, pixel);
			raster.setPixel(0, 4, pixel);
			raster.setPixel(1, 1, pixel);
			raster.setPixel(1, 5, pixel);
			raster.setPixel(2, 0, pixel);
			raster.setPixel(2, 6, pixel);
			raster.setPixel(4, 0, pixel);
			raster.setPixel(4, 6, pixel);
			raster.setPixel(5, 1, pixel);
			raster.setPixel(5, 5, pixel);
			raster.setPixel(6, 2, pixel);			
			raster.setPixel(6, 4, pixel);
			
			pixel[3] = 32;
			raster.setPixel(0, 1, pixel);
			raster.setPixel(0, 5, pixel);
			raster.setPixel(1, 0, pixel);
			raster.setPixel(1, 6, pixel);
			raster.setPixel(5, 0, pixel);
			raster.setPixel(5, 6, pixel);
			raster.setPixel(6, 1, pixel);
			raster.setPixel(6, 4, pixel);

			pixel[3] = 16;
			raster.setPixel(0, 0, pixel);
			raster.setPixel(0, 6, pixel);
			raster.setPixel(6, 0, pixel);
			raster.setPixel(6, 6, pixel);
			
			
			stars[3][i] = newImage;			
			
		}			

		for (int i = 0; i < 6; i++)
		{
			BufferedImage newImage = 
					new BufferedImage(9, 9, BufferedImage.TYPE_INT_ARGB);	
			WritableRaster raster = newImage.getRaster();
			
			pixel[0] = Math.max(0, color.getRed() - i * 24);
			pixel[1] = Math.max(0, color.getGreen() - i * 24);
			pixel[2] = Math.max(0, color.getBlue() - i * 24);
			pixel[3] = 255;
			
			raster.setPixel(4, 4, pixel);
			
			pixel[3] = 224;
			raster.setPixel(3, 4, pixel);
			raster.setPixel(4, 3, pixel);
			raster.setPixel(4, 5, pixel);
			raster.setPixel(5, 4, pixel);
			
			pixel[3] = 192;
			raster.setPixel(2, 4, pixel);
			raster.setPixel(3, 3, pixel);
			raster.setPixel(3, 5, pixel);
			raster.setPixel(4, 2, pixel);
			raster.setPixel(4, 6, pixel);
			raster.setPixel(5, 3, pixel);
			raster.setPixel(5, 5, pixel);
			raster.setPixel(6, 4, pixel);
			
			pixel[3] = 128;
			raster.setPixel(1, 4, pixel);
			raster.setPixel(2, 3, pixel);
			raster.setPixel(2, 5, pixel);
			raster.setPixel(3, 2, pixel);
			raster.setPixel(3, 6, pixel);
			raster.setPixel(4, 1, pixel);
			raster.setPixel(4, 7, pixel);
			raster.setPixel(5, 2, pixel);
			raster.setPixel(5, 6, pixel);
			raster.setPixel(6, 3, pixel);
			raster.setPixel(6, 5, pixel);
			raster.setPixel(7, 4, pixel);

			pixel[3] = 64;
			raster.setPixel(0, 4, pixel);
			raster.setPixel(1, 3, pixel);
			raster.setPixel(1, 5, pixel);
			raster.setPixel(2, 2, pixel);
			raster.setPixel(2, 6, pixel);
			raster.setPixel(3, 1, pixel);
			raster.setPixel(3, 7, pixel);
			raster.setPixel(4, 0, pixel);
			raster.setPixel(4, 8, pixel);
			raster.setPixel(5, 1, pixel);
			raster.setPixel(5, 7, pixel);
			raster.setPixel(6, 2, pixel);
			raster.setPixel(6, 6, pixel);
			raster.setPixel(7, 3, pixel);
			raster.setPixel(7, 5, pixel);
			raster.setPixel(8, 4, pixel);

			
			pixel[3] = 48;
			raster.setPixel(0, 3, pixel);
			raster.setPixel(0, 5, pixel);
			raster.setPixel(1, 2, pixel);
			raster.setPixel(1, 6, pixel);
			raster.setPixel(2, 1, pixel);
			raster.setPixel(3, 7, pixel);
			raster.setPixel(3, 0, pixel);
			raster.setPixel(3, 8, pixel);
			raster.setPixel(5, 0, pixel);
			raster.setPixel(5, 8, pixel);
			raster.setPixel(6, 1, pixel);
			raster.setPixel(6, 7, pixel);
			raster.setPixel(7, 2, pixel);
			raster.setPixel(7, 6, pixel);
			raster.setPixel(8, 3, pixel);
			raster.setPixel(8, 5, pixel);
			
			pixel[3] = 32;
			raster.setPixel(0, 2, pixel);
			raster.setPixel(0, 6, pixel);
			raster.setPixel(1, 1, pixel);
			raster.setPixel(1, 7, pixel);
			raster.setPixel(2, 0, pixel);
			raster.setPixel(2, 8, pixel);
			raster.setPixel(6, 0, pixel);
			raster.setPixel(6, 0, pixel);
			raster.setPixel(7, 1, pixel);
			raster.setPixel(7, 7, pixel);
			raster.setPixel(8, 2, pixel);			
			raster.setPixel(8, 6, pixel);
			
			pixel[3] = 24;
			raster.setPixel(0, 1, pixel);
			raster.setPixel(0, 7, pixel);
			raster.setPixel(1, 0, pixel);
			raster.setPixel(1, 8, pixel);
			raster.setPixel(7, 0, pixel);
			raster.setPixel(7, 8, pixel);
			raster.setPixel(8, 1, pixel);
			raster.setPixel(8, 7, pixel);

			pixel[3] = 16;
			raster.setPixel(0, 0, pixel);
			raster.setPixel(0, 8, pixel);
			raster.setPixel(8, 0, pixel);
			raster.setPixel(8, 8, pixel);
			
			stars[4][i] = newImage;			
			
		}				
	}
}
