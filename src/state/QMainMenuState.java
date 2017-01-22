package tryan.inq.state;

public class QMainMenuState extends QSceneState {	
	public QMainMenuState(int width, int height, QGameState gameState) {
		super(width, height, gameState);
	}

	public void resolveMouseClick(int x, int y) {
		getGameState().loadScene(1);
	}
}
