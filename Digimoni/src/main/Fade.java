package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;



import rafgfxlib.GameFrame;
import rafgfxlib.GameFrame.GFMouseButton;

public class Fade {

	public static int width,height;

	BufferedImage flash, white;

	int x1, y1, a1, b1;
	int x2, y2, a2, b2;

	int o = 100;
	int temp = 11;
	int anim = 0;
	
	boolean whitening = false;
	float whiteAlpha = 0;

	int centerX, centerY, radius;

	public ArrayList<Line> lines = new ArrayList<>();

	public Fade(int width, int height) {
		this.width=width;
		this.height= height;
		
		x1 = width * 23 / 48;
		y1 = height / 2;
		a1 = width / 4;
		b1 = height;

		x2 = width * 25 / 48;
		y2 = height / 2;
		a2 = width * 3 / 4;
		b2 = height;

		for (int i = 0; i < 6; i++) {
			Line l = new Line(x1, y1, x2, y2);
			Line l2 = new Line(x1, y1 - 2, x2, y2 - 2);
			if (i > 0) {
				int b = height * i / 12;
				l.calculate(b, height / 2, width / 48, x1, x2, temp);
				l.y += b;
				l.b += b;
				l2.calculate(b, height / 2, width / 48, x1, x2, temp);
				l2.y += b;
				l2.b += b;
			}
			lines.add(l);
			lines.add(l2);
		}

		centerX = width / 2;
		centerY = height / 2 - 30;
		radius = 50;
		flash = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		white = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		circleGradient();
		makeWhiteImage();
	}



	
	public void render(Graphics2D g, int sw, int sh) {
		g.setStroke(new BasicStroke(14));
		g.setColor(Color.white);
		g.fillOval(width / 2 - (o*3/4)/2, height / 2 - o / 2, o*3/4, o);
		g.setColor(Color.black);
		g.fillRect(0, height / 2, width, height);
		g.setColor(Color.white);
		g.drawLine(x1, y1, a1, b1);
		g.drawLine(x2, y2, a2, b2);

		/*
		 * QuadCurve2D q = new QuadCurve2D.Float(); q.setCurve(0, height,
		 * width/2, height/8, width, height); g.draw(q);
		 */
		// g.setColor(Color.RED);
		for (int i = 0; i < lines.size(); i += 2) {
			g.setStroke(new BasicStroke(11));
			g.setColor(Color.GRAY);
			g.drawLine(lines.get(i + 1).x, lines.get(i + 1).y, lines.get(i + 1).a, lines.get(i + 1).b);
			g.setStroke(new BasicStroke(5));
			g.setColor(Color.white);
			g.drawLine(lines.get(i).x, lines.get(i).y, lines.get(i).a, lines.get(i).b);
		}
		g.drawImage(flash, 0, 0, null);
		if (whitening){
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, whiteAlpha);
			g.setComposite(ac);
			g.drawImage(white, 0, 0, null);
		}
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
	}

	public void update() {
		anim++;
		if (anim % 2 == 0) {
			if (temp > 8) {
				x1--;
				x2++;
				if (x1 % 20 == 0)
					temp--;
				o++;
			}
		}
		if (temp > 8)
			o++;
		for (int i = 0; i < lines.size(); i++) {
			Line l = lines.get(i);
			int b = 5;
			if (l.y + 5 > height) {
				l.y = y1;
				l.b = y1;
				l.calculate(0, height / 2, width / 48, x1, x2, temp);
			} else {
				l.calculate(b, height / 2, width / 48, x1, x2, temp);
				l.y += b;
				l.b += b;
			}
		}
		radius += 5;
		circleGradient();
		if (radius>650 && whiteAlpha<0.99f){
			whitening = true;
			whiteAlpha += 0.008f;
		}
		if(whiteAlpha>=0.99f){
			whiteAlpha=1f;
			OpeningScreen.binary=true;
		}
	}

	public void circleGradient() {
		Color c;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int dx = centerX - x;
				int dy = centerY - y;
				int daljina = (int) Math.sqrt(dx * dx + dy * dy);
				if (daljina <= radius) {
					float fD = 1 - (daljina / (float) radius);
					c = new Color(1f, 1f, 1f, fD);
				} else {
					c = new Color(0f, 0f, 0f, 0f);
				}
				flash.setRGB(x, y, c.getRGB());
			}
		}
	}
	
	public void makeWhiteImage(){
		Color c = new Color(1f,1f,1f,1f);
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				white.setRGB(x, y, c.getRGB());
			}
		}
	}
	
	public void renderFadeOut(Graphics2D g, float alpha){
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g.setComposite(ac);
		g.drawImage(white, 0, 0, null);
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g.setComposite(ac);
	}


}
