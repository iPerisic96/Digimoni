package main;

public class Line {

	public int x,y,a,b;
	int stroke = 5;

	public Line(int x, int y, int a, int b) {
		super();
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;
	}
	
	public void calculate(int amount, int height, int width, int p, int k, int temp){
		//tempHeight:height = x : width
		//x = width*tempHeight/height
		int tempHeight = (y-height) + amount;
		int offset = (width*tempHeight)/height;
		this.x = p - (offset*temp);
		this.a = k + (offset*temp);
	}
}
