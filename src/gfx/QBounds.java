package tryan.inq.gfx;

public class QBounds {
	private int x;
	private int y;
	private int width;
	private int height;
	
	public QBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean containsCoords(int x2, int y2) {
		boolean contains = false;

		if(x2 > x && y2 > y && x2 < x+width && y2 < y+height) {
			contains = true;
		}
		
		return contains;
	}
	
	// Note: Need a contains bounds method for intersection
	//		 and need to take time gradient into account
	public boolean isContainedWithin(int x2, int y2, int x3, int y3) {
		boolean contained = false;
		if(x2 < x && y2 < y && x3 > x && y3 > y) {
			contained = true;
		}
		
		return contained;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
