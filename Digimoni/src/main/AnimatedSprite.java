package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;


public class AnimatedSprite{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8422877228138274014L;
	private SpriteSheet spriteSheet;
	private Animation player;
	private Color backgroundColor = new Color(32, 64, 0);
	
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
	
	private static final int ANIM_DOWN = 0;
	private static final int ANIM_LEFT = 1;
	private static final int ANIM_UP = 2;
	private static final int ANIM_RIGHT = 3;
	
	private static final int PLAYER_SPEED = 3;
	
    private static int lastKey = -1;

	
	public AnimatedSprite(String firstPlayerSpriteSheet) throws NumberFormatException, IOException{
		
		//super("PrimerPozadine", 640, 480);
		
		//setUpdateRate(60);
		
		spriteSheet = new SpriteSheet(firstPlayerSpriteSheet, 10, 30);
		spriteSheet.setOffsets(50, 50);
		
		player = new Animation(spriteSheet, "Gatomon",1500, 1000, 320, 320);
		
		player.play();
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
		
		player.draw(g);
	}
	
	
	
	
	public void update(boolean right, boolean left, boolean down, boolean up, boolean pressed) {	
		
		if(right){
			player.move(PLAYER_SPEED, 0);
			player.update(RUN);
		}
		else if(left){
			player.move(-PLAYER_SPEED, 0);
			player.update(WALK);
		}
		else if(down){
			player.move(0, 0);
			player.update(GUARD);
		}
		else if(up){
			//NEEDS FIXING
			player.move(0, -PLAYER_SPEED);
			player.update(JUMP);
		}
		else if(pressed==false){
			player.move(0,0);
			player.update(IDLE);
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
		
		if(keyCode == KeyEvent.VK_UP){
			if(keyCode!=lastKey){
				player.setFrame(0, JUMP);
				lastKeyPressed(keyCode);
			}
			player.setAnimation(player.getSpriteMoves().get(JUMP).getPosinsheet());
			player.play();

		}
		else if(keyCode == KeyEvent.VK_LEFT){
			if(keyCode!=lastKey){
				player.setFrame(0, WALK);
				lastKeyPressed(keyCode);
			}
			player.setAnimation(player.getSpriteMoves().get(WALK).getPosinsheet());
			player.play();

		}
		else if(keyCode == KeyEvent.VK_RIGHT){
			if(keyCode!=lastKey){
				player.setFrame(0, RUN);
				lastKeyPressed(keyCode);
			}
			player.setAnimation(player.getSpriteMoves().get(RUN).getPosinsheet());
			player.play();

		}
		else if(keyCode == KeyEvent.VK_DOWN){
			if(keyCode!=lastKey){
				player.setFrame(0, GUARD);
				lastKeyPressed(keyCode);
			}
			player.setAnimation(player.getSpriteMoves().get(GUARD).getPosinsheet());
			player.play();		


		}
		
	}
	
	//@Override
	public void handleKeyUp(int keyCode) 
	{ 
		if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP ||
				keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)
		{
			lastKey=-1;
			player.stop();
			player.setAnimation(player.getSpriteMoves().get(IDLE).getPosinsheet());
			player.play();
			player.setFrame(0,IDLE);
		}
	}
}
