//Autor: Robert Reininger, c1210537020

package ecc;

public class Point {
	public double x;
	public double y;
	
	public Point() {
		this.x = 0;
		this.y = 0;
	}
	public Point (double px, double py, double x) {
		this.y = Math.sqrt(Math.pow(x, 3) + px * x + py);
		this.x = x;
	}
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	} 
}
