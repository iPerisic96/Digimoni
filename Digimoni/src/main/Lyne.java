package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;



public class Lyne {
	
	
	Random random;
	ArrayList<HashPoint> hashPoints;
	
	
	public Lyne(Point origin, Point endpoint){
		random=new Random();
		hashPoints=new ArrayList<HashPoint>();
		hashPoints.add(new HashPoint(origin, endpoint));
	}
	
	public void generateLightning(int generations){
		for(int t=0;t<generations;t++){
			int velicina=hashPoints.size();
			for(int i=0;i<velicina;i++){
				int tx=(int) (hashPoints.get(i).start.getX()<hashPoints.get(i).end.getX()?hashPoints.get(i).start.getX():hashPoints.get(i).end.getX());
				int ty=(int) (hashPoints.get(i).start.getY()<hashPoints.get(i).end.getY()?hashPoints.get(i).start.getY():hashPoints.get(i).end.getY());
				int munjax=(int) Math.abs((hashPoints.get(i).start.getX()-hashPoints.get(i).end.getX()))/2;
				int munjay=(int) Math.abs((hashPoints.get(i).start.getY()-hashPoints.get(i).end.getY()))/2;
				int spointx=tx+munjax;
				int spointy=ty+munjay;
				
				Point S = new Point(spointx+random.nextInt(15)-random.nextInt(15),spointy+random.nextInt(15)-random.nextInt(15));		
				Point A = hashPoints.get(i).start;
				Point B = hashPoints.get(i).end;
				hashPoints.remove(i);
				hashPoints.add(new HashPoint(A, S));
				hashPoints.add(new HashPoint(S, B));
			}
		
		}
	}
	
	public void drawLightning(Graphics2D graphics2d){

			Color color;
			for(int z=0;z<hashPoints.size();z++){
				color =new Color(215, 172, 230);
				graphics2d.setColor(color);
				graphics2d.setStroke(new BasicStroke(4));
				graphics2d.drawLine((int)hashPoints.get(z).end.getX(), (int)hashPoints.get(z).end.getY(), (int)hashPoints.get(z).start.getX(), (int)hashPoints.get(z).start.getY());
				
				color = new Color(223,234,245);
				graphics2d.setColor(color);
				graphics2d.setStroke(new BasicStroke(2));
				graphics2d.drawLine((int)hashPoints.get(z).end.getX(), (int)hashPoints.get(z).end.getY(), (int)hashPoints.get(z).start.getX(), (int)hashPoints.get(z).start.getY());

		}
	}
	
	public ArrayList<HashPoint> getHashPoints() {
		return hashPoints;
	}

	public void setHashPoints(ArrayList<HashPoint> hashPoints) {
		this.hashPoints = hashPoints;
	}

}
