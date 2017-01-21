package tryan.inq.controls;


import tryan.inq.gfx.Q2DCoords;

public class QMouseManager {
	private Q2DCoords lastMousePos;
	private Q2DCoords currentMousePos;
	
	public QMouseManager() {
		lastMousePos = new Q2DCoords(0, 0);
		currentMousePos = new Q2DCoords(0, 0);
	}
	
	public Q2DCoords getLastPressedCoords() {
		return lastMousePos;
	}
	
	public void setLastMousePosition(int x, int y) {
		lastMousePos.setX(x);
		lastMousePos.setY(y);
	}
	
	public Q2DCoords getCurrentMouseCoords() {
		return currentMousePos;
	}
	
	public void setCurrentMousePosition(int x, int y) {
		currentMousePos.setX(x);
		currentMousePos.setY(y);
	}
}
