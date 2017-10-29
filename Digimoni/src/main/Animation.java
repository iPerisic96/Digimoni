package main;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Animation {
	
	private ArrayList<SpriteMove> spriteMoves;
	private int posX, posY;
	private SpriteSheet mySheet;
	private int animationID = 0;
	private int animFrame = 0;
	private boolean animPlaying = false;
	private int frameInterval = 2;
	private int frameCountdown=0;
	
	private int idle;
	private int walk;
	private int run;
	private int stop;
	private int jump;
	private int guard;
	private int guardInAir;
	private int hurt;
	private int knockback;
	private int energyChannel;
	private int lightAttack;
	private int heavyAttack;
	private int special;
	private int ultimate;
	private int taunt;
	private int win;
	private int lose;
	private int throwenemy;
	
	public void allocateMoves(SpriteSheet sheet, ArrayList<SpriteMove>theseSpriteMoves) throws NumberFormatException, IOException{
		if(sheet.toString().equals("/Digimoni/SpriteSheets/GatomonSpriteSheetCombined.png")){
			BufferedReader bReader = new BufferedReader(new FileReader(new File("/Digimoni/SpriteInfo/GatomonMoves.txt")));
			String line;
			for(int i=0;i<1;i++){
				if((line = bReader.readLine())!=null){
					ArrayList<Sprite>movesprites=new ArrayList<>();
					String[]linijafajla=line.split(";");
					theseSpriteMoves.get(i).setPosinsheet(Integer.parseInt(linijafajla[0]));
					theseSpriteMoves.get(i).setLengthofmove(Integer.parseInt(linijafajla[1]));
					theseSpriteMoves.get(i).setDoesmovehaveparticles(Boolean.parseBoolean(linijafajla[2]));
					String[]pikselisprajtova=linijafajla[3].split(",");
					for(int j=0;j<pikselisprajtova.length;j++){
						String[]pojedinacnipikseli=pikselisprajtova[j].split("x");
						Sprite sprite = new Sprite(Integer.parseInt(pojedinacnipikseli[0]),Integer.parseInt(pojedinacnipikseli[1]));
						movesprites.add(sprite);
					}
					theseSpriteMoves.get(i).setSprites(movesprites);
					if(theseSpriteMoves.get(i).isDoesmovehaveparticles()){
						theseSpriteMoves.get(i).setMobilityofparticles(linijafajla[4]);
					}
					
					else{
						System.out.println("Fajl ima manje linija nego predvidjeno");
						return;
					}
				}
				
			}
		}
	}
	
	
	public Animation(SpriteSheet sheet, int X, int Y){
		posX = X;
		posY = Y;
		mySheet = sheet;
	}
	
	
	public void update(){
		if(animPlaying){
			frameCountdown--;
			if(frameCountdown < 0){
				animFrame = (animFrame + 1) % mySheet.getColumnCount();
				frameCountdown = frameInterval;
			}
		}
	}
	
	public void draw(Graphics g){
		mySheet.drawTo(g, posX, posY, animFrame, animationID);
	}
	
	public int getAnimation(){
		return animationID; 
	}
	
	public void setAnimation(int anim){
		if(anim >= 0 && anim < mySheet.getRowCount())
			animationID = anim;
	}
	
	public int getFrame(){
		return animFrame; 
	}
	
	public void setFrame(int frame){
		if(frame >= 0 && frame < mySheet.getColumnCount())
			animFrame = frame;
	}
	
	public void play(){
		animPlaying = true;
	}
	public void pause(){
		animPlaying = false;
	}
	public void stop(){
		animPlaying = false; animFrame = 0; frameCountdown = frameInterval;
	}
	
	public boolean isPlaying(){
		return animPlaying; 
	}
	
	public int getAnimationInterval(){
		return frameInterval; 
	}
	public void setAnimationInterval(int i){
		if(i >= 0)
			frameInterval = i;
	}
	
	public void setPosition(int x, int y){
		posX = x;
		posY = y;
	}
	
	public int getPositionX(){
		return posX; 
	}
	public int getPositionY(){
		return posY;
	}
	
	public void move(int movX, int movY){
		posX += movX;
		posY += movY;
	}
}
