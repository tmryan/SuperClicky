package tryan.inq.state;

import tryan.inq.gfx.Q2DCoords;

public class QInventoryState extends QSceneState {
	private int cellWidth;
	private long elapsedTime;
	private int hintDelay;
	private int breakDelay;
	private boolean isBreakTime;
	private boolean isHintEnabled;
	private boolean isLevelWon;
	private boolean isGameWon;
	private QInventoryCellState[][] cellGrid;
	private QInventoryItemState heldItem;
	private QGameWinConditions winConditions;
	// timer goes here
	
	public QInventoryState(int width, int height, QGameState gameState, int rowSize, int cellWidth, int hintDelay) {
		super(width, height, gameState);

		this.cellWidth = cellWidth;
		breakDelay = 1500;
		this.hintDelay = hintDelay + breakDelay;
		isBreakTime = true;
		isHintEnabled = true;
		isLevelWon = false;
		isGameWon = false;
		heldItem = null;
		cellGrid = new QInventoryCellState[rowSize][rowSize];
		winConditions = null;
		// timer instantiation
	}
	
	public void onTick(long tickTime) {	
		super.onTick(tickTime);
		
		elapsedTime += tickTime;
		
		// If hint displayed for hintDelay milliseconds
		if(elapsedTime > hintDelay) {
			isHintEnabled = false;
		} else if(elapsedTime > breakDelay) {
			isBreakTime = false;
		}
		
		// Waiting for a second at end of level
		if(isLevelWon && elapsedTime > 1000) {
			// Advancing state to next round
			getGameState().loadScene(winConditions.getNextSceneState());
		} else if(isGameWon && elapsedTime > 20000) {
			// Closing game after 20 seconds
			getGameState().endGame();
		}
	}
	
	@Override
	public void resolveMouseClick(int x, int y) {
		if(!isHintEnabled && !isLevelWon && !isGameWon) {
			if(heldItem == null) {
				for(QInventoryCellState[] row : cellGrid) {
					for(QInventoryCellState cell : row) {
						if(!cell.isEmpty() && cell.getBounds().containsCoords(x, y)) {
							// If no item on cursor and item in cell then pick it up
							heldItem = cell.getItemState();
							heldItem.setX(cell.getX());
							heldItem.setY(cell.getY());
							cell.removeItemState();
						}
					}
				}
			} else {
				Q2DCoords cellLoc = getClickedCell(x, y);
				int row = cellLoc.getX() / cellWidth;
				int col = cellLoc.getY() / cellWidth;
				
				if(cellGrid[row][col].isEmpty()) {
					// If no item in cell and item on cursor then place item
					heldItem.setX(cellLoc.getX());
					heldItem.setY(cellLoc.getY());
					cellGrid[row][col].setItemState(heldItem);
					heldItem = null;
				} else {
					// If item in cell and on cursor then swap
					QInventoryItemState temp = cellGrid[row][col].getItemState();
					heldItem.setX(cellLoc.getX());
					heldItem.setY(cellLoc.getY());
					cellGrid[row][col].setItemState(heldItem);
					heldItem = temp;
				}
				
				// Checking win conditions
				if(checkWinConditions()) {	
					if(winConditions.getNextSceneState() != 9999) {
						elapsedTime = 0;
						isLevelWon = true;
					} else {
						// Game over, man! Game over!
						elapsedTime = 0;
						isGameWon = true;
					}
				}
			}
		}
	}
	
	@Override
	public void resolveMousePosition(Q2DCoords mousePos) {	
		if(heldItem != null) {
			super.resolveMousePosition(mousePos);

			// Centering held item on cursor
			heldItem.setX(mousePos.getX() - heldItem.getWidth()/2);
			heldItem.setY(mousePos.getY() - heldItem.getHeight()/2);
		}
	}
	
	// Returns the upper left coordinate of a clicked cell
	public Q2DCoords getClickedCell(int x, int y) {
		int x2 = 0;
		int y2 = 0;

		// Searching for cell containing mouse click coords
		for(int i = 0; i < cellGrid.length; i++) {
			for(int j = 0; j < cellGrid[0].length; j++) {
				if(cellGrid[i][j].containsCoords(x, y)) {
					x2 = cellGrid[i][j].getX();
					y2 = cellGrid[i][j].getY();
				}
			}
		}
		
		return new Q2DCoords(x2, y2);
	}
	
	@Override
	public void resolveKeyCommands(long tickTime) {
		// Note: Displaying hint on key press for debugging
		if(getGameState().getKeyManager().getKeyboardState()[0] == true) {
			elapsedTime = 0;
			hintDelay = 300;
			isHintEnabled = true;
		}
	}
	
	// Checks win conditions
	private boolean checkWinConditions() {
		boolean uWon = true;
		
		// Note: Only using tile placement condition in this version
		// Verifying all cells contain the appropriate tile type or nothing if empty
		for(int i = 0; i < cellGrid.length; i++) {
			for(int j = 0; j < cellGrid[0].length; j++) {
				if(!winConditions.getCellGrid()[i][j].isEmpty() && !cellGrid[i][j].isEmpty()) {
					if(!winConditions.getCellGrid()[i][j].getItemState().getItemType().equals(cellGrid[i][j].getItemState().getItemType())) {
						uWon = false;
					}
				} else if(winConditions.getCellGrid()[i][j].isEmpty() && !cellGrid[i][j].isEmpty()) {
					uWon = false;
				} else if(!winConditions.getCellGrid()[i][j].isEmpty() && cellGrid[i][j].isEmpty()) {
					uWon = false;
				}
			}
		}

		return uWon;
	}
	
	public QInventoryCellState[][] getCellGrid() {
		return cellGrid;
	}
	
	public QInventoryItemState getHeldItem() {
		return heldItem;
	}
	
	public void setWinConditions(QGameWinConditions winConditions) {
		this.winConditions = winConditions;
		// Set round timer from conditions here
	}
	
	public boolean isHintEnabled() {
		return isHintEnabled;
	}
	
	public boolean isBreakTime() {
		return isBreakTime;
	}

	public boolean isGameWon() {
		return isGameWon;
	}
	
	public boolean isLevelWon() {
		return isLevelWon;
	}
	
}
