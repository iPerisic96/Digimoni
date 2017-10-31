package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class MenuButton {

	int x;
	int y;
	int height;
	int width;
	Color boja;
	String tekst;
	public MenuButton(int x, int y, int height, int wight, Color boja, String tekst) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = wight;
		this.boja = boja;
		this.tekst = tekst;
	}

	public void render(Graphics2D g, int sw, int sh)
	{
		g.setColor(boja);
		g.fillRect(x, y, width, height);
		g.setColor(Color.WHITE);
		g.drawString(tekst, x+10, y+10);
		
	}
	
	
	public void update(int MouseX, int MouseY)
	{
		if(MouseX>= x  && MouseX<=x+width && MouseY>=y && MouseY<=y+height){
			boja=Color.MAGENTA;
		}else{
			boja=Color.CYAN;
		}
	}
	
}
