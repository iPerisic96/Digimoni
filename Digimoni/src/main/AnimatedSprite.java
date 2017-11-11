package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import javax.xml.stream.events.StartDocument;

import com.sun.media.jfxmedia.events.PlayerEvent;

import rafgfxlib.GameFrame;
import rafgfxlib.GameFrame.GFMouseButton;
import rafgfxlib.ImageViewer;
import rafgfxlib.Util;


public class AnimatedSprite{
	/**
	 * 
	 */
	private SpriteSheet spriteSheet1;
	private SpriteSheet spriteSheet2;
	private Animation player1;
	private Animation player2;
	//private Color backgroundColor = new Color(92, 198, 237);//32,64,0
	
	private ArrayList<BufferedImage> clouds = new ArrayList<BufferedImage>();
	
	private BufferedImage background;
	private BufferedImage cloud = null;
	private BufferedImage village = null;
	private BufferedImage platform = null;
	private BufferedImage mountain = null;
	private BufferedImage mountain2;
	private BufferedImage mountain3;
	private BufferedImage mountain4;
	
	private BufferedImage zawarudo = null;
	private BufferedImage[] cogwheels = new BufferedImage[5];
	private int zawarudocount=0;
	
	private Mountains mountain0;
	private Mountains mountain01;
	private Mountains mountain02;
	private Mountains mountain03;
	
	private int countX = 0;
	private int countW = 0;
	private int counter = 0;
	private WritableRaster raster;
	private long realtime = System.currentTimeMillis();
	private int pcountX = 0;
	private int pcountY = 0;
	private int anglecount=0;
	private int anglebrojac=0;

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
	
	private long starttime1=-1;
	private long starttime2=-1;
	
	private Lyne lyne;
	private Point startingPoint;
	private boolean isLightning=false;
			
	private int ground = 782;
	private float jump_speed1=3;
	private float jump_speed2=3;
	private float gravity = 0.05f;
	
	private int playerleft = 0;
	private int playerright = 0;
	
	private static final float PLAYER_SPEED = 3;
	
    private static int lastKey = -1;

    private boolean isGamePaused=false;
	
	public AnimatedSprite(String firstPlayerSpriteSheet, String secondPlayerSpriteSheet) throws NumberFormatException, IOException{
		
		//super("PrimerPozadine", 640, 480);
		
		//setUpdateRate(60);
		for (int i = 1; i < 8; i++){
			cloud = Util.loadImage("clouds/Cloud" + i + ".png");
			clouds.add(cloud);
		}
		
		village = Util.loadImage("village.png");
		platform = Util.loadImage("tile.png");
		//mountain = Util.loadImage("mountain.png");
		
	// background ----------------------------------------
		raster = Util.createRaster(1000, 800, false);
		
		int rgb [] = new int [3];
		
		int bottom [] = new int [3];
		bottom[0] = 255;
		bottom[1] = 255;
		bottom[2] = 255;
		
		int top [] = new int [3];
		top[0] = 92;
		top[1] = 198;
		top[2] = 237;
		
		for (int y = 0; y < raster.getHeight(); y++){
			for (int x = 0; x < raster.getWidth(); x++){
				double fy = y / (double)raster.getHeight();
				
				rgb[0] = lerp(top[0], bottom[0], fy);
				rgb[1] = lerp(top[1], bottom[1], fy);
				rgb[2] = lerp(top[2], bottom[2], fy);
				
				raster.setPixel(x, y, rgb);
				
			}
		}
		background = Util.rasterToImage(raster);
		
	// mountains ------------------------------------------
		
		int levo [] = new int [3];
		levo[0] = 199;
		levo[1] = 225;
		levo[2] = 31;
		
		int desno [] = new int [3];
		desno[0] = 119;
		desno[1] = 181;
		desno[2] = 0;

		mountain0 = new Mountains(1000, 800, 0.005, -2, 20, 700, levo, desno, 200.0);
		mountain = mountain0.createMountain();
		
		mountain01 = new Mountains(1000, 800, 0.005, -2, 40, 650, levo, desno, 300.0);
		mountain2 = mountain01.createMountain();
		
		levo[0] += 34;
		levo[1] += 24;
		levo[2] += 81;
		
		mountain02 = new Mountains(1000, 800, 0.006, 1, 40, 620, levo, desno, 400.0);
		mountain3 = mountain02.createMountain();

		levo[0] = 230;
		levo[1] = 200;
		levo[2] = 100;
		
		desno[0] = 255;
		desno[1] = 255;
		desno[2] = 255;
		
		
		mountain03 = new Mountains(1000, 800, 0.02, 0, 40, 550, levo, desno, 400.0);
		mountain4 = mountain03.createMountain();
		
	// ----------------------------------------------------
		
		spriteSheet1 = new SpriteSheet(firstPlayerSpriteSheet, 10, 30);
		spriteSheet1.setOffsets(50, 50);
		
		spriteSheet2 = new SpriteSheet(secondPlayerSpriteSheet, 10, 30);
		spriteSheet2.setOffsets(50, 50);
		
		player1 = new Animation(spriteSheet1, "Gatomon", 1500, 1000, 200 , ground);
		player1.play();
		
		player2 = new Animation(spriteSheet2, "Gabumon", 1500, 1000, 800, ground);
		player2.play();
		
		playerleft=player1.getPositionX();
		playerright=player2.getPositionX();
		player1.setOrientation("LEFT");
		player2.setOrientation("RIGHT");
		
		//startThread();
		
		
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//	ZA WARUDO
		
		zawarudo = Util.loadImage("intropause/zawarudo.png"); 
		for(int i=0;i<5;i++){
			cogwheels[i]=Util.loadImage("intropause/"+i+".png");
		}
	}
	
	public int lerp (int a, int b, double x){
		return (int)(a + (b - a) * x);
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
	 
	public void render(Graphics2D g, int sw, int sh) throws NumberFormatException, IOException{
		//g.setBackground(backgroundColor);
		g.clearRect(0, 0, sw, sh);
		g.drawImage(background, 0, 0, null);

		//clouds
		countX += 1;
		if (countX > 2250){
			countX = 0;
		}
		System.out.println(countX);
		
		g.drawImage(clouds.get(0), sw - countX , 50, null);
		g.drawImage(clouds.get(1), sw + clouds.get(1).getWidth() - countX-10, clouds.get(1).getWidth()/2, null);
		g.drawImage(clouds.get(2), sw + clouds.get(2).getWidth() + clouds.get(1).getWidth() - countX, 50, null);
		g.drawImage(clouds.get(3), sw +clouds.get(2).getWidth()+ clouds.get(1).getWidth() + clouds.get(3).getWidth() -countX, 50, null);
		g.drawImage(clouds.get(4), sw +clouds.get(2).getWidth()+ clouds.get(1).getWidth() + clouds.get(3).getWidth()  -countX, 50, null);
		g.drawImage(clouds.get(1), sw +clouds.get(2).getWidth()+ clouds.get(1).getWidth() + clouds.get(3).getWidth() + clouds.get(4).getWidth()  -countX, 50, null);
		
		//healthBar1
		g.setColor(Color.GRAY);
		g.fillRoundRect(8, 8, 300, 30, 30, 55);
									
		g.setColor(Color.GREEN);
		g.fillRoundRect(8, 8, 300, 30, 30, 55);
		
		g.setColor(Color.WHITE);
		g.drawRoundRect(8, 8, 300, 30, 30, 55);
						
		//healthBar2
		g.setColor(Color.GRAY);
		g.fillRoundRect(692, 8, 300, 30, 30, 55); //1000-300-8 = 692
		
		g.setColor(Color.GREEN);
		g.fillRoundRect(692, 8, 300, 30, 30, 55);
		
		g.setColor(Color.WHITE);
		g.drawRoundRect(692, 8, 300, 30, 30, 55);
		
		//energyBar1
		
		g.setColor(Color.WHITE);
		g.fillRect(20, 48, 100, 20); // y = 8 + 30 + 10
		
		g.setColor(Color.RED);
		g.drawLine(70, 68, 70, 48);  //20+50
		
		g.setColor(Color.GRAY);
		g.drawRect(20, 48, 100, 20); // y = 8 + 30 + 10
		
		//energyBar2
		
		g.setColor(Color.WHITE);
		g.fillRect(880, 48, 100, 20); // y = 8 + 30 + 10, x = 1000-20-100
		
		g.setColor(Color.RED);
		g.drawLine(930, 68, 930, 48);  //880+50

		g.setColor(Color.GRAY);
		g.drawRect(880, 48, 100, 20); // y = 8 + 30 + 10
				
		
		//mountains
		g.drawImage(mountain4, 0, sh - mountain4.getHeight(), null);
		g.drawImage(mountain3, 0, sh - mountain3.getHeight(), null);
		g.drawImage(mountain2, 0, sh - mountain2.getHeight(), null);
		for (int i = 0; i < 3; i++){
			
			g.drawImage(mountain, i * mountain.getWidth() , sh - mountain.getHeight(), null);
		}
		
		//platform
		for (int x = 0; x < sw; x++){
			g.drawImage(platform, x * (platform.getWidth()-1), sh-platform.getHeight(),  null);
				
		}
		
		//village
		for (int y = 0; y < 3; y++){
			g.drawImage(village, y*(village.getWidth()), sh-village.getHeight()-platform.getHeight(), null);
		}
		

		if(playerright-playerleft>0){
			if(player1.getOrientation()=="RIGHT"||player2.getOrientation()==""){
				player1.switchOrientation();
				player2.switchOrientation();
			}
			if(player1.isEvolving()){
				if(starttime1==-1)starttime1=System.currentTimeMillis();
				player1.evolve(g,starttime1);
				if(player1.isEvolutionFinished()){
					int tempX = player1.getPositionX();
					spriteSheet1=new SpriteSheet("SpriteSheets/GraySpriteSheet.png", 10, 30);
					spriteSheet1.setOffsets(80, 80);
					player1 = new Animation(spriteSheet1, "Gray", 2000, 1000, tempX, ground);
					player1.play();
				}
			}else{
				player1.draw1(g);
			}
			if(player2.isEvolving()){
				if(starttime2==-1)starttime2=System.currentTimeMillis();
				player2.evolve(g,starttime2);
			}else{
				player2.draw2(g);
			}
		}else{
			if(player1.getOrientation()=="LEFT"||player2.getOrientation()=="RIGHT"){
				player1.switchOrientation();
				player2.switchOrientation();
			}
			if(player1.isEvolving()){
				if(starttime1==-1)starttime1=System.currentTimeMillis();
				player1.evolve(g,starttime1);
			}else{
				player1.draw2(g);
			}
			if(player2.isEvolving()){
				if(starttime2==-1)starttime2=System.currentTimeMillis();
				player2.evolve(g,starttime2);
			}else{
				player2.draw1(g);
			}
		}
		
		if(isLightning){
			Random random = new Random();
			int gpx=random.nextInt(750);
			
			Point groundPoint = new Point(gpx+150,ground);
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+startingPoint.getX()+startingPoint.getY());
			lyne = new Lyne(startingPoint, groundPoint);
			lyne.generateLightning(g, 5);
		}
		
		if(isGamePaused==true){
			summonPausePics(g);
		}
		
		
	}
	public void summonPausePics(Graphics2D g){
		
		pcountX+=3;
		pcountY+=3;
		anglecount+=5;
		if(anglecount==360){
			anglecount=0;
		}
		long razlika = System.currentTimeMillis()-realtime;
		System.out.println("RAZLIKA: "+razlika);
		float alpha = 0.9f;
		AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		g.setComposite(alphaComposite);
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, 1000, 800);
		//Random rotation, great for cogwheels
		
		if(anglebrojac==0&&pcountX<=zawarudo.getWidth()*2&&pcountY<=zawarudo.getHeight()*2){
			g.rotate(Math.toRadians(anglecount), background.getWidth()/2, background.getHeight()/2);
			g.drawImage(zawarudo,background.getWidth()/2+pcountX, background.getHeight()/2+pcountY,pcountX/2,pcountY/2,null );
		}else anglebrojac++;
		
		if(pcountX>0)pcountX-=3;if(pcountY>0)pcountY-=3;
		g.drawImage(zawarudo, pcountX, pcountY, zawarudo.getWidth(), zawarudo.getHeight(), null);
		pcountX-=3;
		pcountY-=3;
		
	}
	
	
	public void update(boolean h, boolean c, boolean v, boolean f, boolean g, boolean e, boolean l, boolean la2, boolean ha2, boolean sa2, boolean ua2, boolean e2, boolean d, boolean a, boolean s, boolean w, boolean right, boolean left, boolean down, boolean up, boolean pressed) {	
		
		
		//Player1
		if(!player1.isDead()&&!player2.isDead()&&!isGamePaused){
		if(player1.isEvolving()==false&&player1.countOfActiveMoves()==1){
			player1.move(0,0);
			player1.update(IDLE);
		}
		
		else if(d){
			if(player1.isCollisionDetected(player1.getPositionX(), player2.getPositionX(), player1.getPositionY(), player2.getPositionY(),1)){
				player1.update(RUN);

			}
			else{
				player1.move(PLAYER_SPEED, 0);
				player1.update(RUN);
			}
			
		}
		else if(a){
			
			player1.move(-PLAYER_SPEED, 0);
			player1.update(WALK);
		}
		else if(s){
			player1.move(0, 0);
			player1.update(GUARD);
		}
		else if(h){
			player1.move(0, 0);
			player1.channelingEnergy();
			player1.update(ENERGYCHANNEL);
		}
		
		if(player1.getIsMoveActive(LIGHTATTACK)&&player1.countOfActiveMoves()!=1){
			if(player1.isCollisionDetected(player1.getPositionX(), player2.getPositionX(), player1.getPositionY(), player2.getPositionY(),1)){
				player2.beingDamaged(player1.getSpriteMoves().get(LIGHTATTACK).getMovedmg());
			}
			player1.move(0, 0);
			player1.update(LIGHTATTACK);
		}
		else if(player1.getIsMoveActive(HEAVYATTACK)&&player1.countOfActiveMoves()!=1){
			if(player1.isCollisionDetected(player1.getPositionX(), player2.getPositionX(), player1.getPositionY(), player2.getPositionY(),1)){
				player2.beingDamaged(player1.getSpriteMoves().get(HEAVYATTACK).getMovedmg());
			}
			player1.move(0, 0);
			player1.update(HEAVYATTACK);
		}
		else if(player1.getIsMoveActive(SPECIAL)&&player1.countOfActiveMoves()!=1){
			if(player1.getEnergyPoints()>=250&&player1.isCollisionDetected(player1.getPositionX(), player2.getPositionX(), player1.getPositionY(), player2.getPositionY(),1)){
				player2.beingDamaged(player1.getSpriteMoves().get(SPECIAL).getMovedmg());
			}
			player1.move(0, 0);
			player1.update(SPECIAL);
		}
		else if(player1.getIsMoveActive(ULTIMATE)&&player1.countOfActiveMoves()!=1){
			if(player1.getEnergyPoints()==player1.getMaxEnergyPoints()&&player1.isCollisionDetected(player1.getPositionX(), player2.getPositionX(), player1.getPositionY(), player2.getPositionY(),1)){
				player2.beingDamaged(player1.getSpriteMoves().get(ULTIMATE).getMovedmg());
			}
			player1.move(0, 0);
			player1.update(ULTIMATE);
		}
		else if(player1.getIsMoveActive(TAUNT)&&player1.countOfActiveMoves()!=1){
			player1.move(0, 0);
			player1.update(TAUNT);
			
		}
		if(player1.isFalling()||player1.isJumping()){
			player1.setVelY(player1.getVelY() + gravity);
			if(player1.getVelY()>PLAYER_SPEED){
				player1.setVelY(PLAYER_SPEED);
			}
		}
		if(player1.getPositionY()>ground){
			player1.setPosition(player1.getPositionX(), ground);
			player1.setVelY(0);
			player1.setJumping(false);
			player1.setFalling(false);
			player1.setAnimation(player1.getSpriteMoves().get(IDLE).getPosinsheet());
			player1.play();		}
		else{
			player1.setFalling(true);
		}
		}else if(player1.isDead()){
			if(player1.getDeathCounter()<0){
				player1.stop();
				player1.setAnimation(player1.getSpriteMoves().get(LOSE).getPosinsheet());
				player1.play();
				player1.setIsMoveActive(true, LOSE);
			}
			player1.setDeathCounter(player1.getDeathCounter()+1);
			if(player1.getDeathCounter()>player1.getSpriteMoves().get(LOSE).getLengthofmove())player1.setDeathCounter(-1);
			player1.update(LOSE);
			
		}else if(!player1.isDead()){
			if(player1.getDeathCounter()<0){
				player1.stop();
				player1.setAnimation(player1.getSpriteMoves().get(WIN).getPosinsheet());
				player1.play();
				player1.setIsMoveActive(true, WIN);
			}
			player1.setDeathCounter(player1.getDeathCounter()+1);
			if(player1.getDeathCounter()>player1.getSpriteMoves().get(WIN).getLengthofmove())player1.setDeathCounter(-1);
			player1.update(WIN);
		}
		System.out.println("COUNTISNOW: "+player1.countOfActiveMoves());
		
		//Player 2
		
		if(!player1.isDead()&&!player2.isDead()&&!isGamePaused){
		if(player2.isEvolving()==false&&player2.countOfActiveMoves()==1){
			player2.move(0,0);
			player2.update(IDLE);
		}
		
		else if(right){
			if(player2.isCollisionDetected(player2.getPositionX(), player1.getPositionX(), player2.getPositionY(), player1.getPositionY(),1)){
				player2.update(RUN);

			}
			else{
				player2.move(PLAYER_SPEED, 0);
				player2.update(RUN);
			}
			
		}
		else if(left){
			
			player2.move(-PLAYER_SPEED, 0);
			player2.update(WALK);
		}
		else if(down){
			player2.move(0, 0);
			player2.update(GUARD);
		}
		else if(l){
			player2.move(0, 0);
			player2.channelingEnergy();
			player2.update(ENERGYCHANNEL);
		}
		
		if(player2.getIsMoveActive(LIGHTATTACK)&&player2.countOfActiveMoves()!=1){
			if(player2.isCollisionDetected(player2.getPositionX(), player1.getPositionX(), player2.getPositionY(), player1.getPositionY(),1)){
				player1.beingDamaged(player2.getSpriteMoves().get(LIGHTATTACK).getMovedmg());
			}
			player2.move(0, 0);
			player2.update(LIGHTATTACK);
		}
		else if(player2.getIsMoveActive(HEAVYATTACK)&&player2.countOfActiveMoves()!=1){
			if(player2.isCollisionDetected(player2.getPositionX(), player1.getPositionX(), player2.getPositionY(), player1.getPositionY(),1)){
				player1.beingDamaged(player2.getSpriteMoves().get(HEAVYATTACK).getMovedmg());
			}
			player2.move(0, 0);
			player2.update(HEAVYATTACK);
		}
		else if(player2.getIsMoveActive(SPECIAL)&&player2.countOfActiveMoves()!=1){
			if(player2.isCollisionDetected(player2.getPositionX(), player1.getPositionX(), player2.getPositionY(), player1.getPositionY(),1)){
				player1.beingDamaged(player2.getSpriteMoves().get(SPECIAL).getMovedmg());
			}
			player2.move(0, 0);
			player2.update(SPECIAL);
		}
		else if(player2.getIsMoveActive(ULTIMATE)&&player2.countOfActiveMoves()!=1){
			if(player2.isCollisionDetected(player2.getPositionX(), player1.getPositionX(), player2.getPositionY(), player1.getPositionY(),1)){
				player1.beingDamaged(player2.getSpriteMoves().get(ULTIMATE).getMovedmg());
			}
			player2.move(0, 0);
			player2.update(ULTIMATE);
		}
		else if(player2.getIsMoveActive(TAUNT)&&player2.countOfActiveMoves()!=1){
			player2.move(0, 0);
			player2.update(TAUNT);
			
		}
		if(player2.isFalling()||player2.isJumping()){
			player2.setVelY(player2.getVelY() + gravity);
			if(player2.getVelY()>PLAYER_SPEED){
				player2.setVelY(PLAYER_SPEED);
			}
		}
		if(player2.getPositionY()>ground){
			player2.setPosition(player2.getPositionX(), ground);
			player2.setVelY(0);
			player2.setJumping(false);
			player2.setFalling(false);
			player2.setAnimation(player2.getSpriteMoves().get(IDLE).getPosinsheet());
			player2.play();
		}
		else{
			player2.setFalling(true);
		}
		}else if(player2.isDead()){
			if(player2.getDeathCounter()<0){
				player2.stop();
				player2.setAnimation(player2.getSpriteMoves().get(LOSE).getPosinsheet());
				player2.play();
				player2.setIsMoveActive(true, LOSE);
			}
			player2.setDeathCounter(player2.getDeathCounter()+1);
			if(player2.getDeathCounter()>player2.getSpriteMoves().get(LOSE).getLengthofmove())player2.setDeathCounter(-1);
			player2.update(LOSE);
		}else if(!player2.isDead()){
			player1.setDeathCounter(player1.getDeathCounter()+1);
			if(player2.getDeathCounter()<0){
				player2.stop();
				player2.setAnimation(player2.getSpriteMoves().get(WIN).getPosinsheet());
				player2.play();
				player2.setIsMoveActive(true, WIN);
			}
			player2.setDeathCounter(player2.getDeathCounter()+1);
			if(player2.getDeathCounter()>player2.getSpriteMoves().get(WIN).getLengthofmove())player2.setDeathCounter(-1);
			player2.update(WIN);
			
		}
		
		playerleft=player1.getPositionX();
		playerright=player2.getPositionX();
		
		/*if(player1.isJumping()&&player1.getIsMoveActive(JUMP)){
			player1.setOnGround(false);
			jump_speed1-=gravity;
			if(jump_speed1<0){
				jump_speed1=0;
				player1.setFalling(true);
				player1.setJumping(false);
				player1.move(0, 0);
				System.out.println("JMP SPEED: "+jump_speed1);
				player1.update(JUMP);
			}else{
				if(w&&d){

					player1.move(0, -jump_speed1);
					System.out.println("JMPMOVRIGHT SPEED: "+jump_speed1);
					player1.update(JUMP);}
				else{
				player1.move(0, -jump_speed1);
				System.out.println("JMP SPEED: "+jump_speed1);
				player1.update(JUMP);}
			}if(w&&a){

				player1.move(0, -jump_speed1);
				System.out.println("JMPMOVLEFT SPEED: "+jump_speed1);
				player1.update(JUMP);}
			else{
			player1.move(0, -jump_speed1);
			System.out.println("JMP SPEED: "+jump_speed1);
			player1.update(JUMP);}
		
		}
		if(player1.isFalling()){
			System.out.println("USLISMOOoooooooooooooooooooooooooooooooooooooooooooooooooooooooooO");
			jump_speed1+=gravity;
			if(jump_speed1>PLAYER_SPEED&&player1.getPositionY()>ground){
				player1.setPosition(player1.getPositionX(), ground);
				jump_speed1=PLAYER_SPEED;
				player1.setFalling(false);
				player1.setJumping(true);
				player1.move(0, 0);
				System.out.println("FALL SPEED: "+jump_speed1);
				player1.update(JUMP);
				
			}else{
				if(w&&d){

					player1.move(0, jump_speed1);
					System.out.println("FALLMOVRIGHT SPEED: "+jump_speed1);
					player1.update(JUMP);
				}else{
				player1.move(0, jump_speed1);
				System.out.println("FALL SPEED: "+jump_speed1);
				player1.update(JUMP);}
			}
			if(w&&a){

				player1.move(0, jump_speed1);
				System.out.println("FALLMOVLEFT SPEED: "+jump_speed1);
				player1.update(JUMP);
			}else{
				player1.move(0, jump_speed1);
				System.out.println("FALL SPEED: "+jump_speed1);
				player1.update(JUMP);}
			}
		 
		/*
		
		if(w){
			//NEEDS FIXING
			
			
		
		else if(e){
			player1.move(0, 0);
			player1.update(TAUNT);
			
		}
	
		

		//Player2
		
		if(left){
			if(player2.isCollisionDetected(player2.getPositionX(), player1.getPositionX(), player2.getPositionY(), player1.getPositionY(),2)){
				player2.update(RUN);

			}
			else{
				player2.move(-PLAYER_SPEED, 0);
				player2.update(RUN);
			}
			
		}
		else if(right){
			player2.move(PLAYER_SPEED, 0);
			player2.update(WALK);
		}
		else if(down){
			player2.move(0, 0);
			player2.update(GUARD);
		}
		else if(up){
			//NEEDS FIXING
			
			if(jump_speed2>0){
				player2.move(0, -jump_speed2);
				jump_speed2--;
				player2.update(JUMP);
			}
			else if(jump_speed2<PLAYER_SPEED){
				jump_speed2++;
				player2.move(0, jump_speed2);
				player2.update(JUMP);
			}
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
			player2.update(IDLE);
			
		}
		else if(pressed==false){
			player2.move(0,0);
			player2.update(IDLE);
		}
		*/
	}

	public void handleMouseDown(int x, int y, GFMouseButton button){
		
	}

	
	public void handleMouseUp(int x, int y, GFMouseButton button){
		
	}

	
	public void handleMouseMove(int x, int y) { }


	//@Override
	public void handleKeyDown(int keyCode) 
	{ 
		if(keyCode==KeyEvent.VK_SPACE){
			PointerInfo aInfo = MouseInfo.getPointerInfo();
			startingPoint = aInfo.getLocation();
			System.out.println("MIS X: "+startingPoint.getX()+" MIS Y: "+startingPoint.getY()); 
			isLightning=true;
		}
		//Player1		
		if(!player1.isDead()&&!player2.isDead()&&!isGamePaused){
		if(keyCode == KeyEvent.VK_W){
			if(keyCode!=lastKey){
				player1.setFrame(0, JUMP);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(JUMP).getPosinsheet());
			player1.play();
			player1.setVelY(-3);
			player1.setJumping(true);


		}
		else if(keyCode == KeyEvent.VK_D){
			if(keyCode!=lastKey){
				player1.setFrame(0, RUN);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(RUN).getPosinsheet());
			player1.play();
			player1.setIsMoveActive(true,RUN);

		}
		else if(keyCode == KeyEvent.VK_A){
			if(keyCode!=lastKey){
				player1.setFrame(0, WALK);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(WALK).getPosinsheet());
			player1.play();
			player1.setIsMoveActive(true,WALK);


		}
		else if(keyCode == KeyEvent.VK_S){
			if(keyCode!=lastKey){
				player1.setFrame(0, GUARD);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(GUARD).getPosinsheet());
			player1.play();
			player1.setIsMoveActive(true,GUARD);


		}
		else if(keyCode == KeyEvent.VK_H){
			if(keyCode!=lastKey){
				player1.setFrame(0, ENERGYCHANNEL);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(ENERGYCHANNEL).getPosinsheet());
			player1.play();
			player1.setIsMoveActive(true,ENERGYCHANNEL);


		}
		else if(keyCode == KeyEvent.VK_C){
			if(keyCode!=lastKey){
				player1.setFrame(0, LIGHTATTACK);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(LIGHTATTACK).getPosinsheet());
			player1.play();
			player1.setIsMoveActive(true,LIGHTATTACK);

		}
		else if(keyCode == KeyEvent.VK_V){
			if(keyCode!=lastKey){
				player1.setFrame(0, HEAVYATTACK);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(HEAVYATTACK).getPosinsheet());
			player1.play();
			player1.setIsMoveActive(true,HEAVYATTACK);

		}
		else if(keyCode == KeyEvent.VK_F){
			if(keyCode!=lastKey){
				player1.setFrame(0, SPECIAL);
				lastKeyPressed(keyCode);
			}
			player1.setAnimation(player1.getSpriteMoves().get(SPECIAL).getPosinsheet());
			player1.play();
			player1.setIsMoveActive(true,SPECIAL);

		}
		else if(keyCode == KeyEvent.VK_G){
			if(keyCode!=lastKey){
				player1.setFrame(0, ULTIMATE);
				lastKeyPressed(keyCode);

			}
			player1.setAnimation(player1.getSpriteMoves().get(ULTIMATE).getPosinsheet());
			player1.play();	
			player1.setIsMoveActive(true,ULTIMATE);

		}
		else if(keyCode == KeyEvent.VK_E){
			if(keyCode!=lastKey){
				player1.setFrame(0, TAUNT);
				lastKeyPressed(keyCode);
				player1.setEvolving(true);
				
			}
			player1.setAnimation(player1.getSpriteMoves().get(TAUNT).getPosinsheet());
			player1.play();
			player1.setIsMoveActive(true, TAUNT);
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
			player2.setVelY(-3);
			player2.setJumping(true);
		}
		else if(keyCode == KeyEvent.VK_RIGHT){
			if(keyCode!=lastKey){
				player2.setFrame(0, WALK);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(WALK).getPosinsheet());
			player2.play();
			player2.setIsMoveActive(true, WALK);

		}
		else if(keyCode == KeyEvent.VK_LEFT){
			if(keyCode!=lastKey){
				player2.setFrame(0, RUN);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(RUN).getPosinsheet());
			player2.play();
			player2.setIsMoveActive(true, RUN);


		}
		else if(keyCode == KeyEvent.VK_DOWN){
			if(keyCode!=lastKey){
				player2.setFrame(0, GUARD);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(GUARD).getPosinsheet());
			player2.play();		
			player2.setIsMoveActive(true, GUARD);


		}
		else if(keyCode == KeyEvent.VK_L){
			if(keyCode!=lastKey){
				player2.setFrame(0, ENERGYCHANNEL);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(ENERGYCHANNEL).getPosinsheet());
			player2.play();
			player2.setIsMoveActive(true, ENERGYCHANNEL);

		}
		else if(keyCode == KeyEvent.VK_SLASH){
			if(keyCode!=lastKey){
				player2.setFrame(0, LIGHTATTACK);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(LIGHTATTACK).getPosinsheet());
			player2.play();
			player2.setIsMoveActive(true, LIGHTATTACK);

		}
		else if(keyCode == KeyEvent.VK_PERIOD){
			if(keyCode!=lastKey){
				player2.setFrame(0, HEAVYATTACK);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(HEAVYATTACK).getPosinsheet());
			player2.play();	
			player2.setIsMoveActive(true, HEAVYATTACK);
	
		}
		else if(keyCode == KeyEvent.VK_QUOTE){
			if(keyCode!=lastKey){
				player2.setFrame(0, SPECIAL);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(SPECIAL).getPosinsheet());
			player2.play();	
			player2.setIsMoveActive(true, SPECIAL);

		}
		else if(keyCode == KeyEvent.VK_SEMICOLON){
			if(keyCode!=lastKey){
				player2.setFrame(0, ULTIMATE);
				lastKeyPressed(keyCode);
			}
			player2.setAnimation(player2.getSpriteMoves().get(ULTIMATE).getPosinsheet());
			player2.play();		
			player2.setIsMoveActive(true, ULTIMATE);

		}
		else if(keyCode == KeyEvent.VK_CLOSE_BRACKET){
			if(keyCode!=lastKey){
				player2.setFrame(0, TAUNT);
				lastKeyPressed(keyCode);
				player2.setEvolving(true);
			}
			//player2.setAnimation(player2.getSpriteMoves().get(GUARD).getPosinsheet());
			//player2.play();
			player2.setIsMoveActive(true, TAUNT);

			System.out.println("Evoluiram drugi.");
		}
		
		}
		if(keyCode == KeyEvent.VK_ESCAPE){
			System.exit(1);
		}
		if(keyCode==KeyEvent.VK_P){
			if(isGamePaused==false){
				isGamePaused=true;
				realtime=System.currentTimeMillis();
				zawarudocount=1;
				pcountX=0;
				pcountY=0;
				anglebrojac=0;
			}
			else{
				isGamePaused=false;
				zawarudocount=0;
			}
		}
	}
	
	//@Override
	public void handleKeyUp(int keyCode) { 
		//Player1
		if(keyCode==KeyEvent.VK_SPACE){
			isLightning=false;
		}
		if(!isGamePaused){
			if(keyCode==KeyEvent.VK_D||keyCode==KeyEvent.VK_S||keyCode==KeyEvent.VK_A||keyCode==KeyEvent.VK_H){
			player1.setIsMoveActive(false, WALK);
			player1.setIsMoveActive(false, RUN);
			player1.setIsMoveActive(false, GUARD);
			player1.setIsMoveActive(false, ENERGYCHANNEL);
		}
		if(player1.countOfActiveMoves()==1){
			lastKey=-1;
			player1.stop();
			player1.setAnimation(player1.getSpriteMoves().get(IDLE).getPosinsheet());
			player1.play();
			player1.setFrame(0,IDLE);
		}
		if(keyCode==KeyEvent.VK_RIGHT||keyCode==KeyEvent.VK_DOWN||keyCode==KeyEvent.VK_LEFT||keyCode==KeyEvent.VK_L){
			player2.setIsMoveActive(false, WALK);
			player2.setIsMoveActive(false, RUN);
			player2.setIsMoveActive(false, GUARD);
			player2.setIsMoveActive(false, ENERGYCHANNEL);
		}
		if(player2.countOfActiveMoves()==1){
			lastKey=-1;
			player2.stop();
			player2.setAnimation(player2.getSpriteMoves().get(IDLE).getPosinsheet());
			player2.play();
			player2.setFrame(0,IDLE);
		}	
	}
		
		
		
		/*
		
		if(player1.isEvolving()==false){
		if(keyCode==KeyEvent.VK_W){
			int br=0;
			jump_speed1=0;
			while(player1.getPositionY()<ground){
				jump_speed1+=gravity;
				if(jump_speed1>PLAYER_SPEED&&player1.getPositionY()>ground){
					player1.setPosition(player1.getPositionX(), ground);
					jump_speed1=PLAYER_SPEED;
					player1.setFalling(false);
					player1.setJumping(true);
					player1.move(0, 0);
					System.out.println("[H]FALL SPEED: "+jump_speed1);
					player1.update(JUMP);
					
				}else{
					player1.move(0, jump_speed1);
					System.out.println("[H]FALL SPEED: "+jump_speed1);
					player1.update(JUMP);
				}
				System.out.println("Counter: "+br++);
			}
			player1.setPosition(player1.getPositionX(), ground);
			jump_speed1=PLAYER_SPEED;
			lastKey=-1;
			player1.stop();
			player1.setAnimation(player1.getSpriteMoves().get(IDLE).getPosinsheet());
			player1.play();
			player1.setFrame(0,IDLE);
		}
	else if(keyCode == KeyEvent.VK_S || 
				keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_E||/*keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_H || keyCode == KeyEvent.VK_C || keyCode == KeyEvent.VK_V || keyCode == KeyEvent.VK_F || keyCode == KeyEvent.VK_G)
		{
			if(keyCode == KeyEvent.VK_E){player1.setEvolving(true);System.out.println("EVOLUIRAM UPRAVO!1");}
			lastKey=-1;
			player1.stop();
			player1.setAnimation(player1.getSpriteMoves().get(IDLE).getPosinsheet());
			player1.play();
			player1.setFrame(0,IDLE);
		}
		
		}
		//Player2
		
		if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP ||
				keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_L || keyCode == KeyEvent.VK_SLASH || keyCode == KeyEvent.VK_PERIOD || keyCode == KeyEvent.VK_QUOTE || keyCode == KeyEvent.VK_SEMICOLON ||keyCode == KeyEvent.VK_CLOSE_BRACKET)
		{
			if(keyCode==KeyEvent.VK_CLOSE_BRACKET){player2.setEvolving(true);System.out.println("EVOLUIRAM UPRAVO!2");}
			lastKey=-1;
			player2.stop();
			player2.setAnimation(player2.getSpriteMoves().get(IDLE).getPosinsheet());
			player2.play();
			player2.setFrame(0,IDLE);
		}
	*/
	}
}
