package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Image {

	BufferedImage i;
	int x;
	int y;
	int h;
	int w;
	public Image(BufferedImage i, int x, int y, int h, int w) {
		super();
		this.i = i;
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
	}
	
	public void render(Graphics2D g, int sw, int sh)
	{
		g.drawImage(i, x, y, h, w, null);
		
	}
	public void update(){
		i=i;
	}
}
