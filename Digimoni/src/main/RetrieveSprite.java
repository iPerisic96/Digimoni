package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RetrieveSprite {
	
	private BufferedImage mainSpriteSheet;
	private int TileSize;
	
	public BufferedImage loadSpriteSheet(String file){
		BufferedImage spriteSheet=null;

		try {
			spriteSheet = ImageIO.read(new File("SpriteSheets"+file+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return spriteSheet;
	}
	
	
	
}
