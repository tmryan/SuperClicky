package tryan.inq.state;

import java.util.TreeMap;

import tryan.inq.gfx.Q2DCoords;
import tryan.inq.gfx.QBounds;
import tryan.inq.state.event.QAreaTrigger;
import tryan.inq.state.event.QGameEventManager;
import tryan.inq.state.event.QTimedEvent;

// Note: Weather and time systems will belong to QSceneState

public class QSceneState {
	private QBounds bounds;
	private int id;
	private TreeMap<Integer, QActorState> sceneryStates;
	// Note: Actor states will be abstracted into two categories eventually
	//		 So far I think only static and dynamic categories are necessary
	private TreeMap<Integer, QActorState> interactableStates;
	private QActorState highlightedActor;
	private QGameEventManager eventMan;
	private QGameState gameState;
	
	public QSceneState(int width, int height, QGameState gameState) {
		bounds = new QBounds(0, 0, width, height);
		id = QGameState.generateSceneId();
		
		sceneryStates = new TreeMap<Integer, QActorState>();
		interactableStates = new TreeMap<Integer, QActorState>();
		highlightedActor = null;
		
		eventMan = new QGameEventManager();
		
		this.gameState = gameState;
	}
	
	public void onTick(long tickTime) {
		// Note: Need to check all area triggers and timed events here
		eventMan.checkTimedEvents(tickTime);
		
		for(int actorId : interactableStates.keySet()) {
			eventMan.addGameEvent(interactableStates.get(actorId).checkAreaTrigger());
		}
		
		// Play queued game events
		playNextEvent();
	}

	public void addSceneryState(QActorState actor) {
		sceneryStates.put(actor.getActorId(), actor);
	}
	
	public void addInteractableState(QActorState actor) {
		interactableStates.put(actor.getActorId(), actor);
	}
	
	public void addAreaTrigger(QAreaTrigger areaTrigger) {
		eventMan.addAreaTrigger(areaTrigger);
	}
	
	public void addTimedEvent (QTimedEvent timedEvent) {
		eventMan.addTimedEvent(timedEvent);
	}
	
	public void resolveMouseClick(int x, int y) {}
	
	public void resolveMousePosition(Q2DCoords mousePos) {
		QActorState foundActor = findInteractableByLocation(mousePos.getX() + bounds.getX(), mousePos.getY() + bounds.getY());	
		
		if(foundActor != null) {
			highlightedActor = foundActor;
		} else if(highlightedActor != null) {
			highlightedActor.removeHighlight();
			highlightedActor = null;
		}
		
		if(highlightedActor != null) {
			foundActor.onHover();
		}
	}

	public void resolveKeyCommands(long tickTime) {}
	
	public void playNextEvent() {
		// Note: Should use greedy technique to choose events
		if(eventMan.hasNextEvent()) {
			eventMan.getNextEvent().playEvent();
		}
	}
	
	public QActorState findInteractableByLocation(int x, int y) {
		QActorState foundActor = null;
		
		/*
		 * Note: For now searching active area for collisions will be enough
		 * 		 Will eventually need to look for actors within some subdivided
		 * 	     scene area and test against relevant AABBs
		 */	
		
		for(int id : interactableStates.keySet()) {
			if(interactableStates.get(id).containsCoords(x, y) && interactableStates.get(id).isHighlightable()) {
				foundActor = interactableStates.get(id);
			}
		}
		
		return foundActor;
	}
	
	public int getSceneStateId() {
		return id;
	}
	
	public QGameState getGameState() {
		return gameState;
	}
	
	public int getWidth() {
		return bounds.getWidth();
	}
	
	public int getHeight() {
		return bounds.getHeight();
	}
	
	public QActorState getHighlightedActor() {
		return highlightedActor;
	}
	
	public void setHightlightedActor(QActorState actorState) {
		highlightedActor = actorState;
	}
	
}
