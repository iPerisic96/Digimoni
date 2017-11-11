package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Meteor {
	  private int width;
	  private int height;
	  private int left;
	  private int top;
	  private double xLoc;
	  private double yLoc;
	  private double xVel;
	  private double yVel;
	  private final BufferedImage star;
	  private long startTime;
	  private long endTime;
	  private Random random;
	  
	  public Meteor(BufferedImage star, int left, int top, int width, int height)
	  {
	    this.random = new Random();
	    this.left = left;
	    this.top = top;
	    this.width = width;
	    this.height = height;
	    
	    this.star = star;
	  }
	  
	  private void initialize()
	  {
	    this.xLoc = (this.left + this.random.nextInt(this.width - 5));
	    this.yLoc = (this.top + this.random.nextInt(this.height - 5));
	    int degrees = this.random.nextInt(180);
	    int speed = this.random.nextInt(3) + 5;
	    this.xVel = (Math.cos(degrees) * speed);
	    this.yVel = (Math.sin(degrees) * speed);
	    
	    this.startTime = 
	      (System.currentTimeMillis() + (this.random.nextInt(20) + 5) * 1000);
	    this.endTime = (this.startTime + (this.random.nextInt(8) + 4) * 100);
	  }
	  
	  public void update()
	  {
	    long now = System.currentTimeMillis();
	    if (now > this.startTime)
	    {
	      this.xLoc += this.xVel;
	      this.yLoc += this.yVel;
	      if (now > this.endTime) {
	        initialize();
	      }
	      if ((this.xLoc > this.left + this.width - 5) || (this.yLoc > this.top + this.height - 5) || 
	        (this.xLoc < this.left) || (this.yLoc < this.top)) {
	        initialize();
	      }
	    }
	  }
	  
	  public void draw(Graphics2D g2)
	  {
	    if (System.currentTimeMillis() > this.startTime) {
	      g2.drawImage(this.star, (int)this.xLoc, (int)this.yLoc, null);
	    }
	  }
}
