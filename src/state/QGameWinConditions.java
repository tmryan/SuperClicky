package tryan.inq.state;

public class QGameWinConditions {
	private QInventoryCellState[][] cellGrid;
	private int nextSceneState;
	
	public QGameWinConditions(int rowSize, int nextSceneState) {
		cellGrid = new QInventoryCellState[rowSize][rowSize];
		this.nextSceneState = nextSceneState;
	}

	public QInventoryCellState[][] getCellGrid() {
		return cellGrid;
	}

	public int getNextSceneState() {
		return nextSceneState;
	}
	
}
