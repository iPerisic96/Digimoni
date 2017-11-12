package main;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;




public class Image {

	BufferedImage promena;
	BufferedImage i;
	BufferedImage image;
	int x;
	int y;
	int h;
	int w;
	
	public ImageAnimation ia;
	
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
		if (!ia.clicked)
			g.drawImage(image, x, y, w, h, null);
		else{
			ia.render(g, sw, sh);
		}
	}
	public void update(int MouseX, int MouseY){
		if (!ia.clicked){
		if(MouseX>= x  && MouseX<=x+w && MouseY>=y && MouseY<=y+h){
			//if(br==1){
				image=promena;
			//}
		}else image=i;
		} else {
			ia.update();
		}
	}
	

}
