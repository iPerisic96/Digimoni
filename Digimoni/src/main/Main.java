 package main;

import java.io.IOException;

import rafgfxlib.GameFrame;

public class Main {
	
	public static GameFrame gf;

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		
		gf = new OpeningScreen("Digimoni", 1000, 800);
		gf.initGameWindow();
		//GameFrame gFrame = new AnimatedSprite("SpriteSheets/GatomonSpriteSheetCombined.png");
		//gFrame.initGameWindow();
	}

}
 