package main;

import java.util.ArrayList;

public class SpriteMove {
	
	private int posinsheet;
	private int lengthofmove;
	private boolean doesmovehaveparticles;
	private String mobilityofparticles;
	private ArrayList<Sprite> sprites;
	
	public SpriteMove(int posinsheet, int lengthofmove, boolean doesmovehaveparticles, String mobilityofparticles,
			ArrayList<Sprite> sprites) {
		super();
		this.posinsheet = posinsheet;
		this.lengthofmove = lengthofmove;
		this.doesmovehaveparticles = doesmovehaveparticles;
		this.mobilityofparticles = mobilityofparticles;
		this.sprites = sprites;
	}
	public int getPosinsheet() {
		return posinsheet;
	}
	public void setPosinsheet(int posinsheet) {
		this.posinsheet = posinsheet;
	}
	public int getLengthofmove() {
		return lengthofmove;
	}
	public void setLengthofmove(int lengthofmove) {
		this.lengthofmove = lengthofmove;
	}
	public boolean isDoesmovehaveparticles() {
		return doesmovehaveparticles;
	}
	public void setDoesmovehaveparticles(boolean doesmovehaveparticles) {
		this.doesmovehaveparticles = doesmovehaveparticles;
	}
	public String getMobilityofparticles() {
		return mobilityofparticles;
	}
	public void setMobilityofparticles(String mobilityofparticles) {
		this.mobilityofparticles = mobilityofparticles;
	}
	public ArrayList<Sprite> getSprites() {
		return sprites;
	}
	public void setSprites(ArrayList<Sprite> sprites) {
		this.sprites = sprites;
	}
}
