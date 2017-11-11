package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class StarField {

	private TwinklingStar[] starField;
	private Random random;
	private int left, top, width, height;
	private Meteor meteor, m2, m3;
	private int numberOfStars;
	
	
	public StarField(int left, int top, int width, int height, int numberOfStars){
		this(left, top, width, height, numberOfStars, (long)System.nanoTime());
	}
	
	public StarField(int left, int top, int width, int height, int numberOfStars, long seed){
		random = new Random(seed); 
		
		this.numberOfStars = numberOfStars;
		starField = new TwinklingStar[numberOfStars];
		this.width = width;
		this.height = height; 
		this.left = left;
		this.top = top;
		
		StarSet starsRed = new StarSet(new Color(255, 224, 224));
		StarSet starsWhite = new StarSet();
		StarSet starsBlue = new StarSet(new Color(232, 232, 255));
		StarSet currStars = starsRed;
		
		meteor = new Meteor(starsRed.getStar(0, 0), left, top, width, height);
		m2 = new Meteor(starsWhite.getStar(0, 0), left, top, width, height);
		m3 = new Meteor(starsRed.getStar(1, 2), left, top, width, height);
		
		
		int incr = 0;
		while (incr < numberOfStars){
			// choose R, W, or B
			int color = random.nextInt(3);
			
			switch(color)
			{
			case 0:
				currStars = starsRed;
				break;
			case 1:
				currStars = starsWhite;
				break;
			case 2:
				currStars = starsBlue;
			}
			
			// choose size (five sizes possible)
			int size = 4 - (int)Math.log1p(random.nextInt(148));
				
			// chose brightness (five brightnesses possible)
			int brightness = random.nextInt(5);
			
			// location, x & y
			int xloc = random.nextInt(width - 5);
			int yloc = random.nextInt(height - 12);
			
			starField[incr] = new TwinklingStar(xloc + left, yloc + top, currStars.getStar(size, brightness), currStars.getStar(size, brightness + 1));	
			incr++;
		}
	}
	
	public void update(){
		for (int i = 0, n = numberOfStars / 16; i < n; i++){
			starField[random.nextInt(starField.length)].update();
		}
		meteor.update();
		m2.update();
		m3.update();
	}
	
	public void draw(Graphics2D g2){
		Rectangle2D rect = new Rectangle2D.Double(left, top, width, height);
		
		// slight atmospheric blueness
		g2.setPaint(new Color(0, 0, 255, 24));
		g2.fill(rect);
		
		
		for (TwinklingStar ts: starField){
			ts.draw(g2);
		}
		meteor.draw(g2);
		m2.draw(g2);
		m3.draw(g2);
	}
}
