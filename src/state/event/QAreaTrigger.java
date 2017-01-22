package tryan.inq.state.event;

import tryan.inq.gfx.QBounds;
import tryan.inq.state.QActorState;

//Note: This class has a QBounds, checks to see if a given actor is inside, and creates a QActionEvent object if so
public class QAreaTrigger {
	private QBounds bounds;
	private QGameEvent gameEvent;
	// Note: Only supporting one actorState for now, but eventually will need more
	private QActorState causalActor;
	private boolean isOneShot;
	
	/*
	 *  Note: Need to figure out how to store and execute scripted events
	 *  
	 *  Note: Figure out best way to pass triggering actor in to event at runtime
	 *  
	 *  Note: Triggers need lifetime or flag for removal upon completion
	 */
	
	public QAreaTrigger(QBounds bounds, boolean isOneShot) {
		this.bounds = bounds;
		gameEvent = null;
		this.isOneShot = isOneShot;
		causalActor = null;
	}
	
	public QAreaTrigger(QBounds bounds, boolean isOneShot, QGameEvent gameEvent) {
		this.bounds = bounds;
		this.gameEvent = gameEvent;
		this.isOneShot = isOneShot;
		causalActor = null;
	}
	
	public boolean containsCoords(int x, int y) {
		return bounds.containsCoords(x, y);
	}
	
	// Note: Need to allow for multiple events at some point
	public void addGameEvent(QGameEvent gameEvent) {
		this.gameEvent = gameEvent;
	}

	public QGameEvent getGameEvent() {
		return gameEvent;
	}
	
	public boolean isOneShot() {
		return isOneShot;
	}
	
	public void setCausalActor(QActorState causalActor) {
		this.causalActor = causalActor;
	}
	
	public QActorState getCausalActor() {
		return causalActor;
	}
	
}
