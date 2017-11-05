package main;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import rafgfxlib.Util;

public class SpriteSheet {
	
	private BufferedImage sheet;
	private int frameW, frameH;
	private int sheetW, sheetH;
	private int offsetX = 0, offsetY = 0;
	private String sheetName;
	
	
	public SpriteSheet(String imageName, int columns, int rows){
		
		sheet = Util.loadImage(imageName);
		if(imageName == null){
			sheet = null;
			System.out.println("Error loading sprite sheet!");
			return;
		}
		
		sheetW = columns;
		sheetH = rows;
		frameW = sheet.getWidth() / sheetW;
		frameH = sheet.getHeight() / sheetH;
		sheetName = imageName;
	}
	
	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public int getColumnCount(){
		return sheetW; 
	}
	public int getRowCount(){
		return sheetH; 
	}
	public int getFrameWidth(){ 
		return frameW; 
	}
	public int getFrameHeight(){
		return frameH; 
	}
	
	public void drawTo(Graphics g, int posX, int posY, int frameX, int frameY, SpriteMove spriteMove){
		
		if(sheet == null) return;
		if(frameX < 0 || frameY < 0 || frameY >= sheetH) return;
		//Ovde treba poraditi
		/*int destcornerx=frameW-spriteMove.getSprites().get(frameX).getPxwidth()/2;
		if(destcornerx%2!=0)destcornerx++;
		int destcornery=frameH-spriteMove.getSprites().get(frameX).getPxheight()/2;
		if(destcornery%2!=0)destcornery++;*/
		if(frameX>9){
			frameX=frameX%10;
			frameY++;
		}
		/*AffineTransform aTransform = new AffineTransform();
		aTransform.setToIdentity();
		aTransform.setToScale(-1, 1);*/
		
		System.out.println("Frame X: "+frameX+" FrameY: "+frameY+" FrameW: "+frameW+" FrameH: "+frameH);
		g.drawImage(sheet, posX - offsetX, posY - offsetY, posX - offsetX + frameW-1, posY - offsetY + frameH-1, frameX * frameW+1, frameY * frameH+1, frameX * frameW + frameW-1, frameY * frameH + frameH-1, null);
		
		//g.drawImage(sheet, posX - offsetX, posY - offsetY, posX - offsetX + destcornerx, posY - offsetY + destcornery, frameX * frameW+destcornerx, frameY * frameH+destcornery, frameX * frameW + spriteMove.getSprites().get(frameX).getPxwidth(), frameY * frameH + spriteMove.getSprites().get(frameX).getPxheight(), null);
		//g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)
	}
	
	public void setOffsets(int x, int y){
		offsetX = x;
		offsetY = y;
	}
	
	public void setOffsetX(int x){
		offsetX = x;
	}
	public void setOffsetY(int y){
		offsetY = y;
	}
	public int getOffsetX(){ 
		return offsetX;
	}
	public int getOffsetY(){
		return offsetY; 
	}
}
