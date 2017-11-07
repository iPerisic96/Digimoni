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
	public Image(BufferedImage i, BufferedImage promena, int x, int y, int h, int w) {
		super();
		this.i = i;
		this.promena= promena;
		this.image= promena;
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
	}
	
	public void render(Graphics2D g, int sw, int sh)
	{
		g.drawImage(i, x, y, h, w, null);
		
	}
	public void update(int br, int MouseX, int MouseY){
		if(MouseX>= x  && MouseX<=x+image.getWidth() && MouseY>=y && MouseY<=y+image.getHeight()){
			if(br==1){
				image=promena;
			}
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
