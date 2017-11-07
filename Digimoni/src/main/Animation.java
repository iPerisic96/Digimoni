package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Animation{
	
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
	private boolean isAnimationActive = false;
	private boolean isOnGround = true;
	private boolean isJumping = true;
	private boolean isFalling = false;
	private String currentActiveAnimation = "";
	private int animCounter=-1;
	private String orientation="";
	private boolean isEvolving=false;
	private boolean isEvolutionFinished=false;
	private boolean isBeingBorn=true;
	
	public ArrayList<SpriteMove> allocateMoves(String character) throws NumberFormatException, IOException{
		ArrayList<SpriteMove> methodSpriteMoves = new ArrayList<>();
		BufferedReader bReader = new BufferedReader(new FileReader(new File("SpriteInfo/"+character+"Moves.txt")));
		String line;
		for(int i=0;i<18;i++){
			if((line = bReader.readLine())!=null){
				//System.out.println(line);
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
		//System.out.println(methodSpriteMoves.get(0).getSprites().size());
		bReader.close();
		return methodSpriteMoves;
	}
	
	
	
	public Animation(SpriteSheet sheet, String character, int health, int energy, int X, int Y) throws NumberFormatException, IOException{
		posX = X;
		posY = Y;
		mySheet = sheet;
		characterName = character;
		healthPoints = health;
		energyPoints = energy;
		spriteMoves=allocateMoves(character);
		currentMove=0;
		

	}
	
	
	public void update(int move){
		if(animPlaying){
			frameCountdown--;
			if(frameCountdown < 0){
				if(animFrame+1!=spriteMoves.get(move).getLengthofmove()){
					isAnimationActive=true;
				}else isAnimationActive=false;
				animFrame = (animFrame+1) % spriteMoves.get(move).getLengthofmove();
				frameCountdown = frameInterval;
				currentMove=move;
				
			}
		}
	}
	public void evolve(Graphics2D g, long starttime){
		long razlika = System.currentTimeMillis()-starttime;
		System.out.println("RUPA U VREMENU: "+razlika);
		if(razlika<700){
			mySheet.grayScale(g, posX, posY, animFrame, animationID, spriteMoves.get(currentMove));}
		else if(razlika<1400){
			mySheet.negative(g, posX, posY, animFrame, animationID, spriteMoves.get(currentMove));}
		else if(razlika<2100){
			mySheet.blinkingNegative(g, posX, posY, animFrame, animationID, spriteMoves.get(currentMove));}
		else if(razlika<2800){
			mySheet.coloredNoise(g, posX, posY, animFrame, animationID, spriteMoves.get(currentMove));
		}
		else if(razlika<4100){
			mySheet.drawMagicCircle(g, posX, posY, animFrame, animationID, spriteMoves.get(currentMove));
		}else{
			isEvolutionFinished=true;
		}
	}
	public void devolve(Graphics2D g, long starttime){
		long razlika = System.currentTimeMillis()-starttime;
		System.out.println("RUPA U VREMENU: "+razlika);
		if(razlika<700){			
			mySheet.drawMagicCircle(g, posX, posY, animFrame, animationID, spriteMoves.get(currentMove));}
		else if(razlika<1400){
			mySheet.coloredNoise(g, posX, posY, animFrame, animationID, spriteMoves.get(currentMove));}
		else if(razlika<2100){
			mySheet.blinkingNegative(g, posX, posY, animFrame, animationID, spriteMoves.get(currentMove));}
		else if(razlika<2800){
			mySheet.negative(g, posX, posY, animFrame, animationID, spriteMoves.get(currentMove));}
		else if(razlika<4100){
			mySheet.grayScale(g, posX, posY, animFrame, animationID, spriteMoves.get(currentMove));}
			else{
			isBeingBorn=false;
		}
	}
	public void draw1(Graphics g){
		mySheet.drawTo(g, posX, posY, animFrame, animationID, spriteMoves.get(currentMove));
	}
	public void draw2(Graphics g){
		mySheet.drawToRotated(g, posX, posY, animFrame, animationID, spriteMoves.get(currentMove));
	}
	
	public int getAnimation(){
		return animationID; 
	}
	
	public void setAnimation(int anim){
		if(anim >= 0 && anim < mySheet.getRowCount())
			//System.out.println("ANIMATIONID NUMBER: "+anim);
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
	
	public void move(float movX, float movY){
		posX += movX;
		posY += movY;
	}
	
	public boolean isCollisionDetected(int player1X, int player2X, int player1Y, int player2Y,int player){
		System.out.println("Player1: X= "+player1X+"| Y= "+player1Y);
		System.out.println("Player2: X= "+player2X+"| Y= "+player2Y);
		if(player==1){
			if((player1X==player2X-25||player1X==player2X-24||player1X==player2X-23) && player1Y==player2Y){
				return true;
			}
			else return false;
	
		}else if(player==2){
			if((player1X==player2X+25 || player1X==player2X+26||player1X==player2X+27) && player1Y==player2Y){
				return true;
			}
			else return false;
		}
		else return false;
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



	public boolean isAnimationActive() {
		return isAnimationActive;
	}



	public void setAnimationActive(boolean isAnimationActive) {
		this.isAnimationActive = isAnimationActive;
	}



	public boolean isOnGround() {
		
		return isOnGround;
	}



	public void setOnGround(boolean isOnGround) {
		this.isOnGround = isOnGround;
	}



	public boolean isJumping() {
		return isJumping;
	}



	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}



	public boolean isFalling() {
		return isFalling;
	}



	public void setFalling(boolean isFalling) {
		this.isFalling = isFalling;
	}



	public int getAnimCounter() {
		return animCounter;
	}



	public void setAnimCounter(int animCounter) {
		this.animCounter = animCounter;
	}



	public String getCurrentActiveAnimation() {
		return currentActiveAnimation;
	}



	public void setCurrentActiveAnimation(String currentActiveAnimation) {
		this.currentActiveAnimation = currentActiveAnimation;
	}



	public String getOrientation() {
		return orientation;
	}



	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	
	public void switchOrientation(){
		if(orientation.equals("RIGHT")){
			orientation="LEFT";
		}else if(orientation.equals("LEFT")){
			orientation="RIGHT";
		}else{
			System.out.println("Orientacija nepostojeca.");
			orientation="";
		}
	}



	public boolean isEvolving() {
		return isEvolving;
	}



	public void setEvolving(boolean isEvolving) {
		this.isEvolving = isEvolving;
	}



	public boolean isEvolutionFinished() {
		return isEvolutionFinished;
	}



	public void setEvolutionFinished(boolean isEvolutionFinished) {
		this.isEvolutionFinished = isEvolutionFinished;
	}



	public boolean isBeingBorn() {
		return isBeingBorn;
	}



	public void setBeingBorn(boolean isBeingBorn) {
		this.isBeingBorn = isBeingBorn;
	}
	
}
