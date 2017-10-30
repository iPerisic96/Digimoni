package main;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.IOException;


import rafgfxlib.GameFrame;
import rafgfxlib.Util;

public class OpeningScreen extends GameFrame
{
	private static final long serialVersionUID = -7968395365237904502L;
	
	private static final int MAX_SPRITES = 2;
	private BufferedImage[] partImages = new BufferedImage[MAX_SPRITES];

	MenuButton button;
	
	public static class Star
	{
		public float posX;
		public float posY;
		public float posZ;
		public String broj;
	}
	
	public static class Particle
	{
		public float posX;
		public float posY;
		public float dX;
		public float dY;
		public int life = 0;
		public int lifeMax = 0;
		public int imageID = 0;
		public float angle = 0.0f;
		public float rot = 0.0f;
	}
	
	private static final int PARTICLE_MAX = 1000;
	
	private Particle[] parts = new Particle[PARTICLE_MAX];

	
	private static final int STAR_MAX = 1000;
	
	private Star[] stars = new Star[STAR_MAX];
	
	private Color[] grayscale = new Color[256];
	
	private float speed = 10.0f;
	private static final float MAX_Z = 2000.0f;
	
	

	public OpeningScreen(String title, int sizeX, int sizeY)
	{
 
		
		super(title, sizeX, sizeY);
		setUpdateRate(60);
		

		button = new MenuButton(45, 60, 100, 100, Color.CYAN, "start");

		
		for(int i = 0; i < STAR_MAX; ++i)
		{
			stars[i] = new Star();
			stars[i].posX = (float)(Math.random() * 2000.0) - 1000.0f;
			stars[i].posY = (float)(Math.random() * 2000.0) - 1000.0f;
			stars[i].posZ = (float)(Math.random() * MAX_Z);
			if (Math.random() < 0.5) {
				stars[i].broj = "0";
			} else {
				stars[i].broj = "1";
			}
		}
		
		for(int i = 0; i < 256; ++i)
			grayscale[i] = new Color(0, i, 0);

		
		for(int i = 0; i < PARTICLE_MAX; ++i)
			parts[i] = new Particle();
		
		for(int i = 0; i < MAX_SPRITES; ++i)
			partImages[i] = Util.loadImage("slike/broj" + i + ".png");
		

		
		startThread();
	}
	

	
	

	@Override
	public void render(Graphics2D g, int sw, int sh)
	{	
		button.render(g, sw, sh);
		for(Star s : stars)
		{	
			float sX1 = sw / 2 + s.posX * (400.0f / s.posZ);
			float sY1 = sh / 2 + s.posY * (400.0f / s.posZ);
			
			float sX2 = sw / 2 + s.posX * (400.0f / (s.posZ + speed));
			float sY2 = sh / 2 + s.posY * (400.0f / (s.posZ + speed));
			
			int brightness = (int)(255 - (s.posZ / MAX_Z) * 255.0f);
			g.setColor(grayscale[brightness]);
			
			//g.drawLine((int)sX1, (int)sY1, (int)sX2, (int)sY2);
			g.drawString(s.broj, sX1, sY1);

			
		}
		
		
		AffineTransform transform = new AffineTransform();
		
		for(Particle p : parts)
		{
			if(p.life <= 0) continue;
			
			transform.setToIdentity();
			transform.translate(p.posX, p.posY);
			transform.rotate(p.angle);
			transform.translate(-16.0, -16.0);
			
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    (float)p.life / (float)p.lifeMax));

			g.drawImage(partImages[p.imageID], transform, null);

		}
		//g.setColor(null);
		button.render(g, sw, sh);
		
	}

	@Override
	public void update()
	{	
		for(Star s : stars)
		{
			s.posZ -= speed;
			if(s.posZ < 1.0)
			{
				s.posZ += MAX_Z;
				s.posX = (float)(Math.random() * 2000.0) - 1000.0f;
				s.posY = (float)(Math.random() * 2000.0) - 1000.0f;
			}
		}
		
		
		if(isMouseButtonDown(GFMouseButton.Right))
			genEx(getMouseX(), getMouseY(), 3.0f, 200, 2);
		
		for(Particle p : parts)
		{
			if(p.life <= 0) continue;
			
			p.life--;
			p.posX += p.dX;
			p.posY += p.dY;
			p.dX *= 0.99f;
			p.dY *= 0.99f;
			p.dY += 0.1f;
			p.angle += p.rot;
			p.rot *= 0.99f;
		}
		
		button.update(getMouseX(), getMouseY());
	}
	
	private void genEx(float cX, float cY, float radius, int life, int count)
	{
		for(Particle p : parts)
		{
			if(p.life <= 0)
			{
				p.life = p.lifeMax = (int)(Math.random() * life * 0.5) + life / 2;
				p.posX = cX;
				p.posY = cY;
				double angle = Math.random() * Math.PI * 2.0;
				double speed = Math.random() * radius;
				p.dX = (float)(Math.cos(angle) * speed);
				p.dY = (float)(Math.sin(angle) * speed);
				p.angle = (float)(Math.random() * Math.PI * 2.0);
				p.rot = (float)(Math.random() - 0.5) * 0.3f;
				
				p.imageID = (int)(Math.random() * MAX_SPRITES);
				
				count--;
				if(count <= 0) return;
			}
		}
	}
	
	
	
	

	@Override

	public void handleMouseDown(int x, int y, GFMouseButton button) {
		genEx(x, y, 10.0f, 300, 50);
	}

	@Override
	public void handleWindowInit() { }

	@Override
	public void handleWindowDestroy() { }
	
	@Override
	public void handleMouseUp(int x, int y, GFMouseButton button){
		
	}

	@Override
	public void handleMouseMove(int x, int y) { }

	@Override
	public void handleKeyDown(int keyCode) { }

	@Override
	public void handleKeyUp(int keyCode) { }

}