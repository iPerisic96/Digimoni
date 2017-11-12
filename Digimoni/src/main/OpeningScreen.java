package main;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;

import rafgfxlib.GameFrame;
import rafgfxlib.Util;

public class OpeningScreen extends GameFrame
{
	private static final long serialVersionUID = -7968395365237904502L;
	
	private static final int MAX_SPRITES = 2;
	private BufferedImage[] partImages = new BufferedImage[MAX_SPRITES];
	
	public static OpeningScreen nekako;
	
	

	MenuButton start;
	MenuButton controls;
	MenuButton credits;
	MenuButton exit;
	MenuButton back;
	ArrayList<MenuButton> buttons= new ArrayList<>();
	boolean startb=false;
	boolean backcredits = false;
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
	
	BufferedImage controlsImage = Util.loadImage("controls.PNG");
	
	BufferedImage ivanslika = Util.loadImage("tim/grafikaivan.jpg");
	BufferedImage andrejslika = Util.loadImage("tim/grafikaandrej.jpg");
	BufferedImage dubislika = Util.loadImage("tim/grafikadubi.jpg");
	
	Image andrej;
	ImageAnimation andrejIA;
	boolean ivanIAb=false;
	Image ivan;
	ImageAnimation ivanIA;
	Image dubravka;
	ImageAnimation dubravkaIA;
	boolean ivanb=false;
	

	boolean backcontrols=false;
	boolean exitboolean = false;
	
	Fade voz;
	static boolean binary;
	
	boolean fadeOut;
	float alphaFadeOut=1;
	
	
	public static class Binarni
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

	
	private static final int BINARNI_MAX = 1000;
	
	private Binarni[] brojevi = new Binarni[BINARNI_MAX];
	
	private Color[] grayscale = new Color[256];
	
	private float speed = 10.0f;
	private static final float MAX_Z = 2000.0f;
	
	

	public OpeningScreen(String title, int sizeX, int sizeY){
		
		super(title, sizeX, sizeY);

		nekako=this;

		
		
		

		setUpdateRate(60);
		

		voz = new Fade(sizeX, sizeY);
		
		try {
			animatedSprite  = new AnimatedSprite("SpriteSheets/GatomonSpriteSheetCombined.png","SpriteSheets/GabumonSpriteSheet.png");
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		start = new MenuButton(400, 200, starth, startn );
		buttons.add(start);
		
		controls = new MenuButton(400, 300, controlsh, controlsn);
		buttons.add(controls);
		
		credits = new MenuButton(400, 400, creditsh, creditsn);
		buttons.add(credits);
		
		exit= new MenuButton(400, 500, exith, exitn);
		buttons.add(exit);
		
		back = new MenuButton(100, 100, backh, backn);
		
		ivan = new Image(negativ(ivanslika), ivanslika, 80, 300, 250, 300);
		ivanIA = new ImageAnimation(ivanslika, sizeX, sizeY, 80, 300);
		ivan.ia = ivanIA;
		dubravka = new Image(binary(dubislika), dubislika, 380, 300, 250, 300);
		dubravkaIA = new ImageAnimation(dubislika, sizeX, sizeY, 380, 300);
		dubravka.ia = dubravkaIA;
		andrej = new Image(waves(andrejslika), andrejslika, 680, 300, 250, 300);
		andrejIA = new ImageAnimation(andrejslika, sizeX, sizeY, 680, 300);
		andrej.ia = andrejIA;
		
		for(int i = 0; i < BINARNI_MAX; ++i)
		{
			brojevi[i] = new Binarni();
			brojevi[i].posX = (float)(Math.random() * 2000.0) - 1000.0f;
			brojevi[i].posY = (float)(Math.random() * 2000.0) - 1000.0f;
			brojevi[i].posZ = (float)(Math.random() * MAX_Z);
			if (Math.random() < 0.5) {
				brojevi[i].broj = "0";
			} else {
				brojevi[i].broj = "1";
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
			try {
				animatedSprite.render(g, sw, sh);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} return;
		}
		
		
		
		if(binary){
			if(fadeOut==false){
				voz.renderFadeOut(g, alphaFadeOut);
			}
			
		
		for(Binarni b : brojevi)
		{	
			float sX1 = sw / 2 + b.posX * (400.0f / b.posZ);
			float sY1 = sh / 2 + b.posY * (400.0f / b.posZ);
			
			
			int brightness = (int)(255 - (b.posZ / MAX_Z) * 255.0f);
			g.setColor(grayscale[brightness]);
			
			
			g.drawString(b.broj, sX1, sY1);

			
		}
		
		if (!backcredits){
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
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                1f));
		//g.setColor(null);
		if(!backcontrols && !backcredits){
			for(MenuButton b : buttons){
				b.render(g, sw, sh);
			}
		}else if(backcontrols){
			back.render(g, sw, sh);
			g.drawImage(controlsImage, 50, 200,800, 500, null);
		}else if(backcredits){
			back.render(g, sw, sh);
			andrej.render(g, sw, sh);
			ivan.render(g, sw, sh);
			
			dubravka.render(g, sw, sh);
		}
		}else voz.render(g, sw, sh);
		
	}

	@Override
	public void update()
	{	
		if(startb){
			animatedSprite.update(isKeyDown(KeyEvent.VK_H),isKeyDown(KeyEvent.VK_C),isKeyDown(KeyEvent.VK_V),isKeyDown(KeyEvent.VK_F),isKeyDown(KeyEvent.VK_G),isKeyDown(KeyEvent.VK_E),isKeyDown(KeyEvent.VK_L),isKeyDown(KeyEvent.VK_SLASH),isKeyDown(KeyEvent.VK_PERIOD),isKeyDown(KeyEvent.VK_QUOTE),isKeyDown(KeyEvent.VK_SEMICOLON),isKeyDown(KeyEvent.VK_CLOSE_BRACKET),isKeyDown(KeyEvent.VK_D),isKeyDown(KeyEvent.VK_A),isKeyDown(KeyEvent.VK_S),isKeyDown(KeyEvent.VK_W),isKeyDown(KeyEvent.VK_RIGHT), isKeyDown(KeyEvent.VK_LEFT), isKeyDown(KeyEvent.VK_DOWN), isKeyDown(KeyEvent.VK_UP), isKeyDown(KeyEvent.KEY_PRESSED));
			return;
		}
		
		if(binary){
			
	
			if(fadeOut==false){
				alphaFadeOut-=0.008f;
				if(alphaFadeOut<=0f){
					fadeOut=true;
				}
			}
		for(Binarni s : brojevi)
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
		if(!backcontrols && !backcredits){
			for(MenuButton b : buttons){
				b.update(getMouseX(), getMouseY());
			}
				
			
		}else if(backcontrols){
			back.update(getMouseX(), getMouseY());
		}else if(backcredits){
			back.update(getMouseX(), getMouseY());
			ivan.update(getMouseX(), getMouseY());

			
			dubravka.update(getMouseX(), getMouseY());
			andrej.update(getMouseX(), getMouseY());
		}
		}else voz.update();
		
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
		if (startb==false){
			if(mouseOnButton(x, y, start)){
				startb=true;
			}
		}
		
		if(backcontrols==false){
			if(mouseOnButton(x, y, controls)){
				backcontrols=true;
			}
		}
		if(backcontrols==true){
			if(mouseOnButton(x, y, back)){
				backcontrols=false;
			}
		}
		if(backcredits==false){
			if(mouseOnButton(x, y, credits)){
				backcredits=true;
			}
		}
		if(backcredits==true){
			if(mouseOnButton(x, y, back)){
				backcredits=false;
			}
			ivanIA.handleMouseUp(x, y, button);
			dubravkaIA.handleMouseUp(x, y, button);
			andrejIA.handleMouseUp(x, y, button);
		}
		if(startb){
			animatedSprite.handleMouseUp(x, y, button);
		}
		
	}
	
	public int widthButton(MenuButton b){
		return b.x+b.image.getWidth();
	}
	public int heightButton(MenuButton b){
		return b.y+b.image.getHeight();
	}

	public boolean mouseOnButton(int x, int y, MenuButton b){
		if(x>b.x && x<(widthButton(b)) && y>b.y && y<(heightButton(b))){
			return true;
		}else return false;
	}
	
	
	
	public BufferedImage negativ(BufferedImage i){
		WritableRaster source = i.getRaster();
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);
		
		int rgb[] = new int[3];
		
		for(int y = 0; y < source.getHeight(); y++)
		{
			for(int x = 0; x < source.getWidth(); x++)
			{
				source.getPixel(x, y, rgb);

				rgb[0] = 255 - rgb[0];
				rgb[1] = 255 - rgb[1];
				rgb[2] = 255 - rgb[2];
				
				target.setPixel(x, y, rgb);
			}
		}
		i = Util.rasterToImage(target);
		return i;
	}
	
	public BufferedImage waves(BufferedImage i){
		WritableRaster source = i.getRaster();
		WritableRaster target = Util.createRaster(i.getWidth(), i.getHeight(), false);
		
		int rgb[] = new int[3];
		
		
		float power = 8.0f;
		
		float size = 0.09f;
		
		for(int y = 0; y < i.getHeight(); y++)
		{			
			for(int x = 0; x < i.getWidth(); x++)
			{
				
				float srcX = (float)(x + Math.sin(y * size) * power);
				float srcY = (float)(y + Math.cos(x * size) * power);
				
				Util.bilSample(source, srcX, srcY, rgb);
				target.setPixel(x, y, rgb);
			}
		}
		i = Util.rasterToImage(target);
		return i;
	}
	
	public BufferedImage binary(BufferedImage i){
		WritableRaster source = i.getRaster();
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);
		
		int rgb[] = new int[3];
		
		for(int y = 0; y < source.getHeight(); y++)
		{
			for(int x = 0; x < source.getWidth(); x++)
			{
				source.getPixel(x, y, rgb);

				int br = (int)(rgb[0] * 0.30 + rgb[1] * 0.59 + rgb[2] * 0.11);
				

				if(br > 160)
					br = 255;
				else
					br = 0;
				
				rgb[0] = br;
				rgb[1] = br;
				rgb[2] = br;
				
				target.setPixel(x, y, rgb);
			}
		}
		i = Util.rasterToImage(target);
		return i;
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