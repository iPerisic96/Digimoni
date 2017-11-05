package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import rafgfxlib.Util;


public class AnimatedSprite{
	/**
	 * 
	 */
	private SpriteSheet spriteSheet1;
	private SpriteSheet spriteSheet2;
	private Animation player1;
	private Animation player2;
	private Color backgroundColor = new Color(92, 198, 237);//32,64,0
	
	private BufferedImage cloud = null;
	private BufferedImage village = null;
	
	private BufferedImage platform = null;
	
	private static final int IDLE = 0;
	private static final int WALK = 1;
	private static final int RUN = 2;
	private static final int STOP = 3;
	private static final int GUARD = 4;
	private static final int GUARDINAIR = 5;
	private static final int HURT = 6;
	private static final int KNOCKBACK = 7;
	private static final int ENERGYCHANNEL = 8;
	private static final int LIGHTATTACK = 9;
	private static final int HEAVYATTACK = 10;
	private static final int SPECIAL = 11;
	private static final int ULTIMATE = 12;
	private static final int TAUNT = 13;
	private static final int WIN = 14;
	private static final int LOSE = 15;
	private static final int THROW = 16;
	private static final int JUMP = 17;
	
	
	private static final int PLAYER_SPEED = 3;
	
    private static int lastKey = -1;

	
	public AnimatedSprite(String firstPlayerSpriteSheet, String secondPlayerSpriteSheet) throws NumberFormatException, IOException{
		
		//super("PrimerPozadine", 640, 480);
		
		//setUpdateRate(60);
		
		cloud = Util.loadImage("clouds/Cloud1.png");
		village = Util.loadImage("village.png");
		platform = Util.loadImage("tile.png");
		
		spriteSheet1 = new SpriteSheet(firstPlayerSpriteSheet, 10, 30);
		spriteSheet1.setOffsets(50, 50);
		
		spriteSheet2 = new SpriteSheet(secondPlayerSpriteSheet, 10, 30);
		spriteSheet2.setOffsets(50, 50);
		
		player1 = new Animation(spriteSheet1, "Gatomon", 1500, 1000, 200 ,550);
		player1.play();
		
		player2 = new Animation(spriteSheet2, "Gabumon", 1500, 1000, 600, 550);
		player2.play();
		//startThread();
	}

	
	//public void handleWindowInit(){
		
	//}

	
	//public void handleWindowDestroy(){
		
	//}
	
	public void lastKeyPressed(int currentKey){
		if(lastKey==-1 || lastKey!=currentKey){
			lastKey=currentKey;
		}
	}
	
	
	public void render(Graphics2D g, int sw, int sh){
		
		g.setBackground(backgroundColor);
		g.clearRect(0, 0, sw, sh);
		
		g.drawImage(cloud, 0, 50, null);
		
		for (int x = 1; x <= sw; x++){
			g.drawImage(platform, sw - x * (platform.getWidth()-1), sh-platform.getHeight(),  null);
		}
		for (int y = 1; y <= sw; y++){
			g.drawImage(village, sw - y*(village.getWidth()), sh-village.getHeight()-platform.getHeight(), null);
		}
		player1.draw(g);
		player2.draw(g);
	}
	
	
	
	public void update(boolean h, boolean c, boolean v, boolean f, boolean g, boolean e, boolean l, boolean la2, boolean ha2, boolean sa2, boolean ua2, boolean e2, boolean d, boolean a, boolean s, boolean w, boolean right, boolean left, boolean down, boolean up, boolean pressed) {	
		
		//Player1
		
		if(d){
			player1.move(PLAYER_SPEED, 0);
			player1.update(RUN);
		}
		else if(a){
			player1.move(-PLAYER_SPEED, 0);
			player1.update(WALK);
		}
		else if(s){
			player1.move(0, 0);
			player1.update(GUARD);
		}
		else if(w){
			//NEEDS FIXING
			player1.move(0, -PLAYER_SPEED);
			player1.update(JUMP);
		}
		else if(h){
			player1.move(0, 0);
			player1.update(ENERGYCHANNEL);
		}
		else if(c){
			player1.move(0, 0);
			player1.update(LIGHTATTACK);
		}
		else if(v){
			player1.move(0, 0);
			player1.update(HEAVYATTACK);
		}
		else if(f){
			player1.move(0, 0);
			player1.update(SPECIAL);
		}
		else if(g){
			player1.move(0, 0);
			player1.update(ULTIMATE);
		}
		else if(e){
			player1.move(0, 0);
			player1.evolve();
		}
		else if(pressed==false){
			player1.move(0,0);
			player1.update(IDLE);
		}
		

		//Player2
		
		if(right){
			player2.move(PLAYER_SPEED, 0);
			player2.update(RUN);
		}
		else if(left){
			player2.move(-PLAYER_SPEED, 0);
			player2.update(WALK);
		}
		else if(down){
			player2.move(0, 0);
			player2.update(GUARD);
		}
		else if(up){
			//NEEDS FIXING
			player2.move(0, -PLAYER_SPEED);
			player2.update(JUMP);
		}
		else if(l){
			player2.move(0, 0);
			player2.update(ENERGYCHANNEL);
		}
		else if(la2){
			player2.move(0, 0);
			player2.update(LIGHTATTACK);
		}
		else if(ha2){
			player2.move(0, 0);
			player2.update(HEAVYATTACK);
		}
		else if(sa2){
			player2.move(0, 0);
			player2.update(SPECIAL);
		}
		else if(ua2){
			player2.move(0, 0);
			player2.update(ULTIMATE);
		}
		else if(e2){
			player2.move(0, 0);
			player2.evolve();
		}
		else if(pressed==false){
			player2.move(0,0);
			player2.update(IDLE);
		}
		
	}

	/*@Override
	public void handleMouseDown(int x, int y, GFMouseButton button) { }

	@Override
	public void handleMouseUp(int x, int y, GFMouseButton button) { }

	@Override
	public void handleMouseMove(int x, int y) { }

*/
	//@Override
	public void handleKeyDown(int keyCode) 
	{ 
		
		//Player1
		
		if(keyCode == KeyEvent.VK_W){
			if(keyCode!=lastKey){
				player1.setFrame(0, JUMP);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(JUMP).getPosinsheet());
			player1.play();

		}
		else if(keyCode == KeyEvent.VK_A){
			if(keyCode!=lastKey){
				player1.setFrame(0, WALK);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(WALK).getPosinsheet());
			player1.play();

		}
		else if(keyCode == KeyEvent.VK_D){
			if(keyCode!=lastKey){
				player1.setFrame(0, RUN);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(RUN).getPosinsheet());
			player1.play();

		}
		else if(keyCode == KeyEvent.VK_S){
			if(keyCode!=lastKey){
				player1.setFrame(0, GUARD);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(GUARD).getPosinsheet());
			player1.play();		
		}
		else if(keyCode == KeyEvent.VK_H){
			if(keyCode!=lastKey){
				player1.setFrame(0, ENERGYCHANNEL);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(ENERGYCHANNEL).getPosinsheet());
			player1.play();		
		}
		else if(keyCode == KeyEvent.VK_C){
			if(keyCode!=lastKey){
				player1.setFrame(0, LIGHTATTACK);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(LIGHTATTACK).getPosinsheet());
			player1.play();		
		}
		else if(keyCode == KeyEvent.VK_V){
			if(keyCode!=lastKey){
				player1.setFrame(0, HEAVYATTACK);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(HEAVYATTACK).getPosinsheet());
			player1.play();		
		}
		else if(keyCode == KeyEvent.VK_F){
			if(keyCode!=lastKey){
				player1.setFrame(0, SPECIAL);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(SPECIAL).getPosinsheet());
			player1.play();		
		}
		else if(keyCode == KeyEvent.VK_G){
			if(keyCode!=lastKey){
				player1.setFrame(0, ULTIMATE);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(ULTIMATE).getPosinsheet());
			player1.play();		
		}
		else if(keyCode == KeyEvent.VK_E){
			if(keyCode!=lastKey){
				player1.setFrame(0, IDLE);
				lastKeyPressed(keyCode);
			}
			//player1.setAnimation(player1.getSpriteMoves().get(GUARD).getPosinsheet());
			//player1.play();
			System.out.println("Evoluiram prvi.");
		}
		
		
		
		
		//Player2
		

		if(keyCode == KeyEvent.VK_UP){
			if(keyCode!=lastKey){
				player2.setFrame(0, JUMP);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(JUMP).getPosinsheet());
			player2.play();

		}
		else if(keyCode == KeyEvent.VK_LEFT){
			if(keyCode!=lastKey){
				player2.setFrame(0, WALK);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(WALK).getPosinsheet());
			player2.play();

		}
		else if(keyCode == KeyEvent.VK_RIGHT){
			if(keyCode!=lastKey){
				player2.setFrame(0, RUN);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(RUN).getPosinsheet());
			player2.play();

		}
		else if(keyCode == KeyEvent.VK_DOWN){
			if(keyCode!=lastKey){
				player2.setFrame(0, GUARD);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(GUARD).getPosinsheet());
			player2.play();		


		}
		else if(keyCode == KeyEvent.VK_L){
			if(keyCode!=lastKey){
				player2.setFrame(0, ENERGYCHANNEL);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(ENERGYCHANNEL).getPosinsheet());
			player2.play();
		}
		else if(keyCode == KeyEvent.VK_SLASH){
			if(keyCode!=lastKey){
				player2.setFrame(0, LIGHTATTACK);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(LIGHTATTACK).getPosinsheet());
			player2.play();		
		}
		else if(keyCode == KeyEvent.VK_PERIOD){
			if(keyCode!=lastKey){
				player2.setFrame(0, HEAVYATTACK);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(HEAVYATTACK).getPosinsheet());
			player2.play();		
		}
		else if(keyCode == KeyEvent.VK_QUOTE){
			if(keyCode!=lastKey){
				player2.setFrame(0, SPECIAL);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(SPECIAL).getPosinsheet());
			player2.play();		
		}
		else if(keyCode == KeyEvent.VK_SEMICOLON){
			if(keyCode!=lastKey){
				player2.setFrame(0, ULTIMATE);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(ULTIMATE).getPosinsheet());
			player2.play();		
		}
		else if(keyCode == KeyEvent.VK_CLOSE_BRACKET){
			if(keyCode!=lastKey){
				player2.setFrame(0, IDLE);
				lastKeyPressed(keyCode);
			}
			//player2.setAnimation(player2.getSpriteMoves().get(GUARD).getPosinsheet());
			//player2.play();
			System.out.println("Evoluiram drugi.");
		}
		
		
	}
	
	//@Override
	public void handleKeyUp(int keyCode) 
	{ 
		//Player1
		
		if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_W ||
				keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_H || keyCode == KeyEvent.VK_C || keyCode == KeyEvent.VK_V || keyCode == KeyEvent.VK_F || keyCode == KeyEvent.VK_G || keyCode == KeyEvent.VK_E)
		{
			lastKey=-1;
			player1.stop();
			player1.setAnimation(player1.getSpriteMoves().get(IDLE).getPosinsheet());
			player1.play();
			player1.setFrame(0,IDLE);
		}
		
		//Player2
		
		if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP ||
				keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_L || keyCode == KeyEvent.VK_SLASH || keyCode == KeyEvent.VK_PERIOD || keyCode == KeyEvent.VK_QUOTE || keyCode == KeyEvent.VK_SEMICOLON ||keyCode == KeyEvent.VK_CLOSE_BRACKET)
		{
			lastKey=-1;
			player2.stop();
			player2.setAnimation(player2.getSpriteMoves().get(IDLE).getPosinsheet());
			player2.play();
			player2.setFrame(0,IDLE);
		}
	}
}
