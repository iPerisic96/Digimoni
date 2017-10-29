package main;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
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
	private int currentMove;
	
	public ArrayList<SpriteMove> getSpriteMoves() {
		return spriteMoves;
	}



	public void setSpriteMoves(ArrayList<SpriteMove> spriteMoves) {
		this.spriteMoves = spriteMoves;
	}



	public ArrayList<SpriteMove> allocateMoves(SpriteSheet sheet) throws NumberFormatException, IOException{
		ArrayList<SpriteMove> methodSpriteMoves = new ArrayList<>();
		if(sheet.getSheetName().equals("SpriteSheets/GatomonSpriteSheetCombined.png")){
			BufferedReader bReader = new BufferedReader(new FileReader(new File("SpriteInfo/GatomonMoves.txt")));
			String line;			for(int i=0;i<1;i++){
				if((line = bReader.readLine())!=null){
					System.out.println(line);
					ArrayList<Sprite> movesprites=new ArrayList<>();
					String[]linijafajla=line.split(";");
					/*methodSpriteMoves.get(i).setPosinsheet(Integer.parseInt(linijafajla[0]));
					methodSpriteMoves.get(i).setLengthofmove(Integer.parseInt(linijafajla[1]));
					methodSpriteMoves.get(i).setDoesmovehaveparticles(linijafajla[2]);*/
					String[]pikselisprajtova=linijafajla[3].split(",");
					for(int j=0;j<pikselisprajtova.length;j++){
						String[]pojedinacnipikseli=pikselisprajtova[j].split("x");
						Sprite sprite = new Sprite(Integer.parseInt(pojedinacnipikseli[0]),Integer.parseInt(pojedinacnipikseli[1]));
						movesprites.add(sprite);
					}
					SpriteMove thismethodSpriteMove= new SpriteMove(Integer.parseInt(linijafajla[0]), Integer.parseInt(linijafajla[1]), linijafajla[2], linijafajla[4], movesprites);
					methodSpriteMoves.add(thismethodSpriteMove);
					/*methodSpriteMoves.get(i).setSprites(movesprites);
					methodSpriteMoves.get(i).setMobilityofparticles(linijafajla[4]);*/
				}
				else{
					System.out.println("Fajl ima manje linija nego predvidjeno");
					return null;
				}
				
			}
			
			System.out.println(methodSpriteMoves.get(0).getSprites().size());
		}
		else{
			System.out.println("Ne postoji SpriteSheet. "+sheet.getSheetName());
		}
		return methodSpriteMoves;
		
	}
	
	
	
	public Animation(SpriteSheet sheet, int X, int Y) throws NumberFormatException, IOException{
		posX = X;
		posY = Y;
		mySheet = sheet;
		spriteMoves=allocateMoves(sheet);
		currentMove=0;

	}
	
	
	public void update(int move){
		if(animPlaying){
			frameCountdown--;
			if(frameCountdown < 0){
				animFrame = (animFrame+1) % spriteMoves.get(move).getLengthofmove();
				
				frameCountdown = frameInterval;
				currentMove=move;
			}
		}
	}
	
	public void draw(Graphics g){
		mySheet.drawTo(g, posX, posY, animFrame, animationID, spriteMoves.get(currentMove));
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
