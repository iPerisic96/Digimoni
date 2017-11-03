package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MenuButton {

	int x;
	int y;
	BufferedImage image;
	BufferedImage hover;
	BufferedImage normalno;
	
	
	
	public MenuButton(int x, int y, BufferedImage hover, BufferedImage normalno) {
		super();
		this.x = x;
		this.y = y;
		this.hover = hover;
		this.normalno = normalno;
		this.image=hover;
	}

	public void render(Graphics2D g, int sw, int sh)
	{
		g.drawImage(image, x, y, null);
		
	}
	
	
	public void update(int MouseX, int MouseY)
	{
		if(MouseX>= x  && MouseX<=x+image.getWidth() && MouseY>=y && MouseY<=y+image.getHeight()){
			image = hover;
		}else{
			image = normalno;
		}
	}
	
}
