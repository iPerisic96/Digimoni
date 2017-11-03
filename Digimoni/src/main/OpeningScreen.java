package main;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.ArrayList;

import com.sun.glass.ui.TouchInputSupport;

import rafgfxlib.GameFrame;
import rafgfxlib.Util;

public class OpeningScreen extends GameFrame
{
	private static final long serialVersionUID = -7968395365237904502L;
	
	private static final int MAX_SPRITES = 2;
	private BufferedImage[] partImages = new BufferedImage[MAX_SPRITES];

	MenuButton start;
	MenuButton controls;
	MenuButton credits;
	MenuButton exit;
	MenuButton back;
	ArrayList<MenuButton> buttons= new ArrayList<>();
	boolean startb=false;
	AnimatedSprite animatedSprite;
	BufferedImage starth = Util.loadImage("dugmad/start1.png");
	BufferedImage controlsh = Util.loadImage("dugmad/controls1.png");
	BufferedImage creditsh= Util.loadImage("dugmad/credits1.png");
	BufferedImage exith = Util.loadImage("dugmad/exit1.png");
	BufferedImage backh = Util.loadImage("dugmad/back1.png");
	BufferedImage startn = Util.loadImage("dugmad/start.png");
	BufferedImage controlsn = Util.loadImage("dugmad/controls.png");
	BufferedImage creditsn = Util.loadImage("dugmad/credits.png");
	BufferedImage exitn= Util.loadImage("dugmad/exit.png");
	BufferedImage backn= Util.loadImage("dugmad/back.png");
	
	BufferedImage zekice = Util.loadImage("maxresdefault.jpg");
	
	boolean backb=false;
	
	
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
		

		try {
			animatedSprite  = new AnimatedSprite("SpriteSheets/GatomonSpriteSheetCombined.png");
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		start = new MenuButton(450, 200, starth, startn );
		buttons.add(start);
		
		controls = new MenuButton(450, 300, controlsh, controlsn);
		buttons.add(controls);
		
		credits = new MenuButton(450, 400, creditsh, creditsn);
		buttons.add(credits);
		
		exit= new MenuButton(450, 500, exith, exitn);
		buttons.add(exit);
		
		back = new MenuButton(100, 100, backh, backn);
		
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
		if(startb){
			animatedSprite.render(g, sw, sh); return;
		}
		//button.render(g, sw, sh);
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
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                1f));
		//g.setColor(null);
		if(!backb){
			for(MenuButton b : buttons){
				b.render(g, sw, sh);
			}
		}else{
			back.render(g, sw, sh);
			g.drawImage(zekice, 200, 200,500, 500, null);
		}
		
		
	}

	@Override
	public void update()
	{	
		if(startb){
			animatedSprite.update(isKeyDown(KeyEvent.VK_RIGHT), isKeyDown(KeyEvent.VK_LEFT), isKeyDown(KeyEvent.VK_DOWN), isKeyDown(KeyEvent.VK_UP), isKeyDown(KeyEvent.KEY_PRESSED));
			return;
		}
		
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
		if(!backb){
			for(MenuButton b : buttons){
				b.update(getMouseX(), getMouseY());
			}
				
			
		}else{
			back.update(getMouseX(), getMouseY());
		}
		
		
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
		/*GameFrame gFrame;
		try {
			gFrame = new AnimatedSprite("SpriteSheets/GatomonSpriteSheetCombined.png");
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	
	@Override
	public void handleWindowInit() { }

	@Override
	public void handleWindowDestroy() { }
	
	@Override
	public void handleMouseUp(int x, int y, GFMouseButton button){
		if (startb==false){
			if(x>start.x && x<(start.x+start.image.getWidth()) && y>start.y && y<(start.y+start.image.getHeight())){
				startb=true;
			}
		}
		
		if(backb==false){
			if(x>controls.x && x<(controls.x+controls.image.getWidth()) && y>controls.y && y<(controls.y+controls.image.getHeight())){
				backb=true;
			}
		}
		if(backb==true){
			if(x>back.x && x<(back.x+back.image.getWidth()) && y>back.y && y<(back.y+back.image.getHeight())){
				backb=false;
			}
		}
	}

	@Override
	public void handleMouseMove(int x, int y) { }

	@Override
	public void handleKeyDown(int keyCode) {
		if(startb){
			animatedSprite.handleKeyDown(keyCode);
		}
	}

	@Override
	public void handleKeyUp(int keyCode) { 
		if(startb){
			animatedSprite.handleKeyUp(keyCode);
		}
	}

}