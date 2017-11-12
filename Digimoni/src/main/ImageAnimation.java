package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


import rafgfxlib.GameFrame.GFMouseButton;


public class ImageAnimation {
	
	public int width,height;
	BufferedImage image;

	public static int MAX_PARTICLES;

	public ArrayList<Particles> particles = new ArrayList<>();
	public boolean clicked = false;
	public boolean gen = false;
	boolean showImage = true;

	int heriX, heriY;

	public ImageAnimation(BufferedImage image, int width, int height, int x, int y) {
		this.image=image;
		
		this.width = width;
		this.height = height;

		this.heriX=x;
		this.heriY=y;
		MAX_PARTICLES = image.getWidth() * image.getHeight();
		MAX_PARTICLES /= 2;

		for (int i = 0; i < MAX_PARTICLES; i++) {
			particles.add(new Particles());
		}

	}

	
	public void render(Graphics2D g, int sw, int sh) {
		for (Particles p : particles) {
			if (p.life > 0) {
				g.setColor(p.c);
				g.fillRect((int) p.posX, (int) p.posY, 1, 1);
			}
		}
	}

	
	public void update() {
		if (gen && clicked){
			generateParticles();
			gen = false;
		}
		int counter = 0;
		for (Particles p : particles) {
			if (p.life <= 0){
				counter++;
				if (counter >= particles.size()) clicked = false;
				continue;
			}

			p.life--;
			p.posX += p.dX;
			p.posY += p.dY;
			p.dX *= 0.99f;
			p.dY *= 0.99f;
			p.dY += 0.1f;

			if (p.posX < 0) {
				p.posX = 0.01f;
				p.dX = Math.abs(p.dX) * (float) Math.random() * 0.6f;
			}

			if (p.posY < 0) {
				p.posY = 0.01f;
				p.dY = Math.abs(p.dY) * (float) Math.random() * 0.6f;
			}

			if (p.posX > width) {
				p.posX = width - 0.01f;
				p.dX = Math.abs(p.dX) * (float) Math.random() * -0.6f;
			}

			if (p.posY > height) {
				p.posY = height - 0.01f;
				p.dY = Math.abs(p.dY) * (float) Math.random() * -0.6f;
			}
		}
	}

	public void generateParticles() {
		int counter = 0;
		for (int i = 0; i < image.getHeight(); i+=2) {
			for (int j = 0; j < image.getWidth(); j+=2) {
				genEx(heriX + j, heriY + i, 20.0f, 200, new Color(image.getRGB(j, i)), particles.get(counter++));
			}
		}
	}

	private void genEx(float cX, float cY, float radius, int life, Color color, Particles p) {
		p.life = (int) (Math.random() * life * 0.5) + life / 2;
		p.posX = cX;
		p.posY = cY;
		System.out.println(p.posX+"   "+p.posY);
		double angle = Math.random() * Math.PI * 2.0;
		double speed = Math.random() * radius;
		p.dX = (float) (Math.cos(angle) * speed);
		p.dY = (float) (Math.sin(angle) * speed);
		p.c = color;
	}

	

	
	public void handleMouseUp(int x, int y, GFMouseButton button) {
		if (x >= heriX && x <= heriX + image.getWidth() && y >= heriY && y <= heriY + image.getHeight()){
			if (!clicked && !gen){
			clicked = true;
			gen = true;}
		}
			
	}




}
