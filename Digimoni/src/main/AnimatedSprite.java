package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;

import rafgfxlib.GameFrame;
import rafgfxlib.GameFrame.GFMouseButton;

public class AnimatedSprite extends GameFrame{
	private SpriteSheet spriteSheet;
	private Animation player;
	private Color backgroundColor = new Color(32, 64, 0);
	
	private static final int IDLE = 0;
	private static final int WALK = 1;
	private static final int RUN = 2;
	private static final int STOP = 3;
	private static final int JUMP = 4;
	private static final int GUARD = 5;
	private static final int GUARDINAIR = 6;
	private static final int HURT = 7;
	private static final int KNOCKBACK = 8;
	private static final int ENERGYCHANNEL = 9;
	private static final int LIGHTATTACK = 10;
	private static final int HEAVYATTACK = 11;
	private static final int SPECIAL = 12;
	private static final int ULTIMATE = 13;
	private static final int TAUNT = 14;
	private static final int WIN = 15;
	private static final int LOSE = 16;
	private static final int THROW = 17;
	
	private static final int ANIM_DOWN = 0;
	private static final int ANIM_LEFT = 1;
	private static final int ANIM_UP = 2;
	private static final int ANIM_RIGHT = 3;
	
	private static final int PLAYER_SPEED = 3;
	
	
	public AnimatedSprite(String firstPlayerSpriteSheet) throws NumberFormatException, IOException{
		
		super("PrimerPozadine", 640, 480);
		
		setUpdateRate(60);
		
		spriteSheet = new SpriteSheet(firstPlayerSpriteSheet, 10, 30);
		spriteSheet.setOffsets(50, 50);
		
		player = new Animation(spriteSheet, 320, 320);
		
		
		startThread();
	}

	@Override
	public void handleWindowInit(){
		
	}

	@Override
	public void handleWindowDestroy(){
		
	}

	@Override
	public void render(Graphics2D g, int sw, int sh){
		
		g.setBackground(backgroundColor);
		g.clearRect(0, 0, sw, sh);
		
		player.draw(g);
	}

	@Override
	public void update() {	
		
		if(!isKeyDown(KeyEvent.KEY_PRESSED)){
			player.move(0,0);
			player.update(IDLE);
		}
		/*else if(isKeyDown(KeyEvent.VK_DOWN))
			player.move(0, PLAYER_SPEED);
		else if(isKeyDown(KeyEvent.VK_UP))
			player.move(0, -PLAYER_SPEED);
		else if(isKeyDown(KeyEvent.VK_LEFT))
			player.move(-PLAYER_SPEED, 0);
		else if(isKeyDown(KeyEvent.VK_RIGHT))
			player.move(PLAYER_SPEED, 0);
		
		player.update();*/
	}

	@Override
	public void handleMouseDown(int x, int y, GFMouseButton button) { }

	@Override
	public void handleMouseUp(int x, int y, GFMouseButton button) { }

	@Override
	public void handleMouseMove(int x, int y) { }

	@Override
	public void handleKeyDown(int keyCode) 
	{ 		
		if(keyCode == KeyEvent.VK_UP){
			player.setAnimation(ANIM_UP);
			player.play();
		}
		else if(keyCode == KeyEvent.VK_LEFT){
			player.setAnimation(ANIM_LEFT);
			player.play();
		}
		else if(keyCode == KeyEvent.VK_RIGHT){
			player.setAnimation(ANIM_RIGHT);
			player.play();
		}
		
	}
	
	@Override
	public void handleKeyUp(int keyCode) 
	{ 
		if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP ||
				keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)
		{
			player.stop();
			player.setAnimation(player.getSpriteMoves().get(IDLE).getPosinsheet());
			player.play();
			player.setFrame(5);
		}
	}
}
