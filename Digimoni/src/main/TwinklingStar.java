package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TwinklingStar {

	private byte state;
	private long lastChange;
	private final int xLoc, yLoc;
	private final BufferedImage[] star;
	
	public TwinklingStar(int x, int y, BufferedImage darkerStar, BufferedImage brighterStar)
	{
		xLoc = x;
		yLoc = y;
		star = new BufferedImage[2];
		star[0] = darkerStar;
		star[1] = brighterStar;
		lastChange = System.nanoTime();
	}
	
	public void update()
	{
		if (System.nanoTime() - lastChange > 200000000)
		{
			state++;
			state &= 0x1;
			lastChange = System.nanoTime();
		}
	}
	
	public void draw(Graphics2D g2)
	{
		g2.drawImage(star[state], xLoc, yLoc, null);
	}
}
