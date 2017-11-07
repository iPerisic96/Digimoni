package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;

import javax.security.auth.x500.X500Principal;

import javafx.scene.transform.Scale;
import rafgfxlib.ImageViewer;
import rafgfxlib.Util;

public class SpriteSheet {
	
	private BufferedImage sheet;
	private int frameW, frameH;
	private int sheetW, sheetH;
	private int offsetX = 0, offsetY = 0;
	private String sheetName;
	private BufferedImage evolvedSheet;
	BufferedImage evolution=Util.loadImage("slike/evolucija.png");
	private float scale = 1f;
	private float scaleReverse=0f;
	private boolean isScaleTime=false;
	
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
	
	public void grayScale(Graphics g, int posX, int posY, int frameX, int frameY, SpriteMove spriteMove){
		
		
		float rgb[] = new float[3];
		
		BufferedImage newSheet = new BufferedImage(sheet.getWidth(), sheet.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int y = 0; y < newSheet.getHeight(); y++){
			for(int x = 0; x < newSheet.getWidth(); x++){
				Color color = new Color(sheet.getRGB(x, y),true);
				rgb[0]=color.getRed();
				rgb[1]=color.getGreen();
				rgb[2]=color.getBlue();
				float alpha = color.getAlpha();
				float i = (rgb[0] * 0.30f + rgb[1] * 0.59f + rgb[2] * 0.11f);				
				rgb[0] = i;
				rgb[1] = i;
				rgb[2] = i;
				Color newCOlor = new Color(rgb[0]/255, rgb[1]/255, rgb[2]/255,alpha/255);
				newSheet.setRGB(x, y, newCOlor.getRGB());
			}
		}
		
		
		if(sheet == null) return;
		if(frameX < 0 || frameY < 0 || frameY >= sheetH) return;
		
		if(frameX>9){
			frameX=frameX%10;
			frameY++;
		}else if(frameX>19&&frameX<30){
			frameX=frameX%10;
			frameY+=2;
		}
		else if(frameX>29&&frameX<40){
			frameX=frameX%10;
			frameY+=3;
		}
		
		//g.drawImage(newSheet, 0, 0, null);
		g.drawImage(newSheet, posX - offsetX, posY - offsetY, posX - offsetX + frameW-1, posY - offsetY + frameH-1, frameX * frameW+1, frameY * frameH+1, frameX * frameW + frameW-1, frameY * frameH + frameH-1, null);
		evolvedSheet=newSheet;
	}
	public void negative(Graphics g, int posX, int posY, int frameX, int frameY, SpriteMove spriteMove){
		float rgb[] = new float[3];
		
		BufferedImage newSheet = new BufferedImage(evolvedSheet.getWidth(), sheet.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int y = 0; y < newSheet.getHeight(); y++){
			for(int x = 0; x < newSheet.getWidth(); x++){
				Color color = new Color(sheet.getRGB(x, y),true);
				rgb[0]=color.getRed();
				rgb[1]=color.getGreen();
				rgb[2]=color.getBlue();
				
				float alpha = color.getAlpha();
				
				rgb[0] = 255-rgb[0];
				rgb[1] = 255-rgb[1];
				rgb[2] = 255-rgb[2];
				Color newCOlor = new Color(rgb[0]/255, rgb[1]/255, rgb[2]/255,alpha/255);
				newSheet.setRGB(x, y, newCOlor.getRGB());
			}
		}
		
		
		if(sheet == null) return;
		if(frameX < 0 || frameY < 0 || frameY >= sheetH) return;
		
		if(frameX>9){
			frameX=frameX%10;
			frameY++;
		}else if(frameX>19&&frameX<30){
			frameX=frameX%10;
			frameY+=2;
		}
		else if(frameX>29&&frameX<40){
			frameX=frameX%10;
			frameY+=3;
		}
		
		//g.drawImage(newSheet, 0, 0, null);
		g.drawImage(newSheet, posX - offsetX, posY - offsetY, posX - offsetX + frameW-1, posY - offsetY + frameH-1, frameX * frameW+1, frameY * frameH+1, frameX * frameW + frameW-1, frameY * frameH + frameH-1, null);
		evolvedSheet=newSheet;
	
	}
	public void blinkingNegative(Graphics g, int posX, int posY, int frameX, int frameY, SpriteMove spriteMove){
		float rgb[] = new float[3];
		
		BufferedImage newSheet = new BufferedImage(evolvedSheet.getWidth(), sheet.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int y = 0; y < newSheet.getHeight(); y++){
			for(int x = 0; x < newSheet.getWidth(); x++){
				Color color = new Color(evolvedSheet.getRGB(x, y),true);
				rgb[0]=color.getRed();
				rgb[1]=color.getGreen();
				rgb[2]=color.getBlue();
				
				float alpha = color.getAlpha();
				
				rgb[0] = 255-rgb[0];
				rgb[1] = 255-rgb[1];
				rgb[2] = 255-rgb[2];
				Color newCOlor = new Color(rgb[0]/255, rgb[1]/255, rgb[2]/255,alpha/255);
				newSheet.setRGB(x, y, newCOlor.getRGB());
			}
		}
		
		
		if(sheet == null) return;
		if(frameX < 0 || frameY < 0 || frameY >= sheetH) return;
		
		if(frameX>9){
			frameX=frameX%10;
			frameY++;
		}else if(frameX>19&&frameX<30){
			frameX=frameX%10;
			frameY+=2;
		}
		else if(frameX>29&&frameX<40){
			frameX=frameX%10;
			frameY+=3;
		}
		
		//g.drawImage(newSheet, 0, 0, null);
		g.drawImage(newSheet, posX - offsetX, posY - offsetY, posX - offsetX + frameW-1, posY - offsetY + frameH-1, frameX * frameW+1, frameY * frameH+1, frameX * frameW + frameW-1, frameY * frameH + frameH-1, null);
		evolvedSheet=newSheet;
	
	}
	public static int clamp(int value, int min, int max)
	{
		if(value < min) return min;
		if(value > max) return max;
		return value;
	}
	
	public static int saturate(int value)
	{
		return clamp(value, 0, 255);
	}
	public void coloredNoise(Graphics g, int posX, int posY, int frameX, int frameY, SpriteMove spriteMove){
		int rgb[] = new int[3];
		System.out.println("CRTAM I JA");
		BufferedImage newSheet = new BufferedImage(evolvedSheet.getWidth(), sheet.getHeight(), BufferedImage.TYPE_INT_ARGB);
		int noise = 50;
		Random rnd = new Random();
		for(int y = 0; y < newSheet.getHeight(); y++){
			for(int x = 0; x < newSheet.getWidth(); x++){
				Color color = new Color(sheet.getRGB(x, y),true);
				rgb[0]=color.getRed();
				rgb[1]=color.getGreen();
				rgb[2]=color.getBlue();
				
				float alpha = color.getAlpha();
				
				rgb[0] = saturate(rgb[0] + rnd.nextInt(noise) - noise / 2);
				rgb[1] = saturate(rgb[1] + rnd.nextInt(noise) - noise / 2);
				rgb[2] = saturate(rgb[2] + rnd.nextInt(noise) - noise / 2);
				
				Color newCOlor = new Color(rgb[0]/255, rgb[1]/255, rgb[2]/255,alpha/255);
				newSheet.setRGB(x, y, newCOlor.getRGB());
			}
		}
		
		
		if(sheet == null) return;
		if(frameX < 0 || frameY < 0 || frameY >= sheetH) return;
		
		if(frameX>9){
			frameX=frameX%10;
			frameY++;
		}else if(frameX>19&&frameX<30){
			frameX=frameX%10;
			frameY+=2;
		}
		else if(frameX>29&&frameX<40){
			frameX=frameX%10;
			frameY+=3;
		}
		
		//g.drawImage(newSheet, 0, 0, null);
		g.drawImage(newSheet, posX - offsetX, posY - offsetY, posX - offsetX + frameW-1, posY - offsetY + frameH-1, frameX * frameW+1, frameY * frameH+1, frameX * frameW + frameW-1, frameY * frameH + frameH-1, null);
	
	}
	public void drawMagicCircle(Graphics2D g, int posX, int posY, int frameX, int frameY, SpriteMove spriteMove){
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToIdentity();
		affineTransform.translate(posX-offsetX+12, posY-offsetY+12);
		affineTransform.scale(scale, scale);
		g.drawImage(evolution,affineTransform,null);
		if(scale>1.75){
			isScaleTime=true;
		}
		if(isScaleTime){
			scale-=0.036;
		}else{
			scale+=0.024;
		}
		if(scale<0){
			scale=0;
		}
		
	}
	public void drawReverseMagicCircle(Graphics2D g, int posX, int posY, int frameX, int frameY, SpriteMove spriteMove){
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.setToIdentity();
		affineTransform.translate(posX-offsetX+12, posY-offsetY+12);
		affineTransform.scale(scaleReverse, scaleReverse);
		g.drawImage(evolution,affineTransform,null);
		if(scale==0){
			isScaleTime=true;
		}
		if(isScaleTime){
			scaleReverse+=0.036;
		}else{
			scaleReverse-=0.024;
		}
		if(scale>1.75){
			scaleReverse=(float) 1.75;
		}
		
	}
	
	public void drawTo(Graphics g, int posX, int posY, int frameX, int frameY, SpriteMove spriteMove){
		
		if(sheet == null) return;
		if(frameX < 0 || frameY < 0 || frameY >= sheetH) return;
		//Ovde treba poraditi
		/*int destcornerx=frameW-spriteMove.getSprites().get(frameX).getPxwidth()/2;
		if(destcornerx%2!=0)destcornerx++;
		int destcornery=frameH-spriteMove.getSprites().get(frameX).getPxheight()/2;
		if(destcornery%2!=0)destcornery++;*/
		if(frameX>9&&frameX<20){
			frameX=frameX%10;
			frameY++;
		}else if(frameX>19&&frameX<30){
			frameX=frameX%10;
			frameY+=2;
		}
		else if(frameX>29&&frameX<40){
			frameX=frameX%10;
			frameY+=3;
		}
		
		//System.out.println("Frame X: "+frameX+" FrameY: "+frameY+" FrameW: "+frameW+" FrameH: "+frameH);
		
		g.drawImage(sheet, posX - offsetX, posY - offsetY, posX - offsetX + frameW-1, posY - offsetY + frameH-1, frameX * frameW+1, frameY * frameH+1, frameX * frameW + frameW-1, frameY * frameH + frameH-1, null);
		
		/*Rotated
		g.drawImage(sheet, posX - offsetX, posY - offsetY, posX - offsetX + frameW-1, posY - offsetY + frameH-1, frameX * frameW + frameW-1, frameY * frameH+1,frameX * frameW+1,frameY * frameH + frameH-1, null);
		*/
		
		//g.drawImage(sheet, posX - offsetX, posY - offsetY, posX - offsetX + destcornerx, posY - offsetY + destcornery, frameX * frameW+destcornerx, frameY * frameH+destcornery, frameX * frameW + spriteMove.getSprites().get(frameX).getPxwidth(), frameY * frameH + spriteMove.getSprites().get(frameX).getPxheight(), null);
		//g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)
	}
public void drawToRotated(Graphics g, int posX, int posY, int frameX, int frameY, SpriteMove spriteMove){
		
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
		}else if(frameX>19&&frameX<30){
			frameX=frameX%10;
			frameY+=2;
		}
		else if(frameX>29&&frameX<40){
			frameX=frameX%10;
			frameY+=3;
		}
		
		//System.out.println("Frame X: "+frameX+" FrameY: "+frameY+" FrameW: "+frameW+" FrameH: "+frameH);
		//g.drawImage(sheet, posX - offsetX, posY - offsetY, posX - offsetX + frameW-1, posY - offsetY + frameH-1, frameX * frameW+1, frameY * frameH+1, frameX * frameW + frameW-1, frameY * frameH + frameH-1, null);
		//Rotated
		g.drawImage(sheet, posX - offsetX, posY - offsetY, posX - offsetX + frameW-1, posY - offsetY + frameH-1, frameX * frameW + frameW-1, frameY * frameH+1,frameX * frameW+1,frameY * frameH + frameH-1, null);
		
		
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
