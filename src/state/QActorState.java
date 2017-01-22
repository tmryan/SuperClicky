package tryan.inq.state;

import java.util.ArrayList;

import tryan.inq.gfx.QBounds;
import tryan.inq.state.event.QAreaTrigger;
import tryan.inq.state.event.QGameEvent;

public class QActorState {
	private int id;
	private QBounds bounds;
	private boolean isHighlightable;
	private boolean isHighlighted;
	private ArrayList<QAreaTrigger> areaTriggers;

	public QActorState(int x, int y, int width, int height, int id) {
		this.id = id;
		bounds = new QBounds(x, y, width, height);
		isHighlightable = false;
		areaTriggers = new ArrayList<QAreaTrigger>();
	}
	
	public void onHover() {
		if(isHighlightable && !isHighlighted) {
			highlight();
		}
	}
	
	public boolean containsCoords(int x2, int y2) {
		return bounds.containsCoords(x2, y2);
	}
	
	public boolean isContainedWithin(int x2, int y2, int x3, int y3) {
		return bounds.isContainedWithin(x2, y2, x3, y3);
	}
	
	// Note: This is a temporary implementation until dynamic and static actors split
	public QGameEvent checkAreaTrigger() {
		// Check any area triggers tied to this actor
		return null;
	}
	
	public void attachAreaTrigger(QAreaTrigger areaTrigger) {
		areaTriggers.add(areaTrigger);
	}
	
	public int getX() {
		return bounds.getX();
	}

	public void setX(int x) {
		bounds.setX(x);
	}

	public int getY() {
		return bounds.getY();
	}

	public void setY(int y) {
		bounds.setY(y);
	}
	
	public int getWidth() {
		return bounds.getWidth();
	}

	public int getHeight() {
		return bounds.getHeight();
	}
	
	public int getActorId() {
		return id;
	}
	
	public QBounds getBounds() {
		return bounds;
	}
	
	public boolean isHighlightable() {
		return isHighlightable;
	}
	
	public void makeHighlightable(boolean isHighlightable) {
		this.isHighlightable = isHighlightable;
	}
	
	public boolean isHighlighted() {
		return isHighlighted;
	}
		
	public void removeHighlight() {
		isHighlighted = false;
	}

	private void highlight() {
		isHighlighted = true;
	}
}
