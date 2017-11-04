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
	private int frameInterval = 5;
	private int frameCountdown=0;
	private int currentMove;
	private int healthPoints;
	private int energyPoints;
	private String characterName;
	


	public ArrayList<SpriteMove> allocateMoves(SpriteSheet sheet, String character) throws NumberFormatException, IOException{
		ArrayList<SpriteMove> methodSpriteMoves = new ArrayList<>();
		if(sheet.getSheetName().equals("SpriteSheets/GatomonSpriteSheetCombined.png")&& character.equals("Gatomon")){
			BufferedReader bReader = new BufferedReader(new FileReader(new File("SpriteInfo/GatomonMoves.txt")));
			String line;
			for(int i=0;i<18;i++){
				if((line = bReader.readLine())!=null){
					System.out.println(line);
					ArrayList<Sprite> movesprites=new ArrayList<>();
					String[]linijafajla=line.split(";");
					String[]pikselisprajtova=linijafajla[4].split(",");
					for(int j=0;j<pikselisprajtova.length;j++){
						String[]pojedinacnipikseli=pikselisprajtova[j].split("x");
						Sprite sprite = new Sprite(Integer.parseInt(pojedinacnipikseli[0]),Integer.parseInt(pojedinacnipikseli[1]));
						movesprites.add(sprite);
					}
					SpriteMove thismethodSpriteMove= new SpriteMove(Integer.parseInt(linijafajla[0]), Integer.parseInt(linijafajla[1]), Integer.parseInt(linijafajla[2]), linijafajla[3], linijafajla[5], movesprites);
					methodSpriteMoves.add(thismethodSpriteMove);
				}
				else{
					System.out.println("Fajl ima manje linija nego predvidjeno");
					return null;
				}
			}
		}
			
		System.out.println(methodSpriteMoves.get(0).getSprites().size());
		return methodSpriteMoves;
		
	}
	
	
	
	public Animation(SpriteSheet sheet, String character, int health, int energy, int X, int Y) throws NumberFormatException, IOException{
		posX = X;
		posY = Y;
		mySheet = sheet;
		characterName = character;
		healthPoints = health;
		energyPoints = energy;
		spriteMoves=allocateMoves(sheet, character);
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
			System.out.println("ANIMATIONID NUMBER: "+anim);
			animationID = anim;
	}
	
	public int getFrame(){
		return animFrame; 
	}
	
	public void setFrame(int frame, int move){
		if(frame >= 0 && frame < spriteMoves.get(move).getLengthofmove())
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
	
	public int getHealthPoints() {
		return healthPoints;
	}



	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}



	public int getEnergyPoints() {
		return energyPoints;
	}



	public void setEnergyPoints(int energyPoints) {
		this.energyPoints = energyPoints;
	}



	public ArrayList<SpriteMove> getSpriteMoves() {
		return spriteMoves;
	}



	public void setSpriteMoves(ArrayList<SpriteMove> spriteMoves) {
		this.spriteMoves = spriteMoves;
	}



	public String getCharacterName() {
		return characterName;
	}



	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	
}
