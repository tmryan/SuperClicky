package tryan.inq.state;

import java.util.HashMap;

import tryan.inq.controls.QKeyboardManager;
import tryan.inq.controls.QMouseManager;

public class QGameState {
	private boolean mouseClicked;
	private boolean isGameOver;
	private static int nextActorId;
	private static int nextSceneid;
	private QMouseManager mouseMan;
	private QKeyboardManager keyMan;
	private QUserInterfaceState uiState;
	private HashMap<Integer, QSceneState> sceneStates;
	private int currentScene;
	
	public QGameState(QMouseManager mouseMan, QKeyboardManager keyMan) {
		mouseClicked = false;
		
		nextActorId = 1;
		nextSceneid = 1;
		this.mouseMan = mouseMan;
		this.keyMan = keyMan;
		uiState = new QUserInterfaceState();
		sceneStates = new HashMap<Integer, QSceneState>();
		currentScene = 0;
	}
	
	public QGameState() {
		mouseClicked = false;
		
		nextActorId = 1;
		// Starting at 0 for main menu scene
		nextSceneid = 0;
		mouseMan = null;
		keyMan = null;
		uiState = new QUserInterfaceState();
		sceneStates = new HashMap<Integer, QSceneState>();
		currentScene = 0;
	}
	
	public void onTick(long tickTime) {
		// Note: Temporary solution for high initial value from System.nanotime()
		if(tickTime < 1000) {
			resolveMousePosition();
			resolveKeyCommands(tickTime);
			sceneStates.get(currentScene).onTick(tickTime);
		}
	}
	
	public static int generateActorId() {
		return nextActorId++;
	}
	
	public static int generateSceneId() {
		return nextSceneid++;
	}
	
	public void resolveMouseClick(int x, int y) {
		sceneStates.get(currentScene).resolveMouseClick(x, y);
	}
	
	public void resolveMousePosition() {
		sceneStates.get(currentScene).resolveMousePosition(mouseMan.getCurrentMouseCoords());
	}
	
	public void resolveKeyCommands(long tickTime) {
		sceneStates.get(currentScene).resolveKeyCommands(tickTime);
	}
	
	public void addSceneState(QSceneState sceneState) {
		sceneStates.put(sceneState.getSceneStateId(), sceneState);
	}
	
	public void loadScene(int sceneID) {
		currentScene = sceneID;
	}
	
	public int getCurrentSceneID() {
		return sceneStates.get(currentScene).getSceneStateId();
	}
	
	public QSceneState getSceneStateById(int sceneId) {
		return sceneStates.get(sceneId);
	}
	
	public QKeyboardManager getKeyManager() {
		return keyMan;
	}
	
	public void setKeyboardManager(QKeyboardManager keyMan) {
		this.keyMan = keyMan;
	}
	
	public QMouseManager getMouseManager() {
		return mouseMan;
	}
	
	public void setMouseManager(QMouseManager mouseMan) {
		this.mouseMan = mouseMan;
	}
	
	public void endGame() {
		isGameOver = true;
	}
	
	public boolean isGameOver() {
		return isGameOver;
	}
	
}
