package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import rafgfxlib.Util;

public class Image {

	BufferedImage promena;
	BufferedImage i;
	BufferedImage image;
	int x;
	int y;
	int h;
	int w;
	public Image(BufferedImage promena, BufferedImage i, int x, int y, int w, int h) {
		super();
		this.promena = promena;
		this.i= i;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.image=promena;
	}
	
	public void render(Graphics2D g, int sw, int sh)
	{
		g.drawImage(image, x, y, w, h, null);
		
	}
	public void update(int MouseX, int MouseY){
		if(MouseX>= x  && MouseX<=x+w && MouseY>=y && MouseY<=y+h){
			//if(br==1){
				image=promena;
			//}
		}else image=i;
	}
	
//	public BufferedImage negativ(BufferedImage i){
//		WritableRaster source = i.getRaster();
//		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);
//		
//		int rgb[] = new int[3];
//		
//		for(int y = 0; y < source.getHeight(); y++)
//		{
//			for(int x = 0; x < source.getWidth(); x++)
//			{
//				source.getPixel(x, y, rgb);
//
//				rgb[0] = 255 - rgb[0];
//				rgb[1] = 255 - rgb[1];
//				rgb[2] = 255 - rgb[2];
//				
//				target.setPixel(x, y, rgb);
//			}
//		}
//		i = Util.rasterToImage(target);
//		return i;
//	}
}
