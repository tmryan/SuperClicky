package tryan.inq.state;

import tryan.inq.gfx.Q2DCoords;

public class QInventoryState extends QSceneState {
	private int cellWidth;
	private long elapsedTime;
	private boolean hintModeEnabled;
	private QInventoryCellState[][] cellGrid;
	private QInventoryItemState heldItem;
	private QGameWinConditions winConditions;
	private int hintDelay;
	// round timer goes here
	
	public QInventoryState(int width, int height, QGameState gameState, int rowSize, int cellWidth, int hintDelay) {
		super(width, height, gameState);

		this.cellWidth = cellWidth;
		hintModeEnabled = true;
		heldItem = null;
		cellGrid = new QInventoryCellState[rowSize][rowSize];
		winConditions = null;
		this.hintDelay = hintDelay;
		// timer is null
	}
	
	public void onTick(long tickTime) {	
		super.onTick(tickTime);
		
		elapsedTime += tickTime;
		
		// If hint displayed for hintDelay milliseconds
		if(elapsedTime > hintDelay) {
			hintModeEnabled = false;
		}
	}
	
	@Override
	public void resolveMouseClick(int x, int y) {
		if(heldItem == null) {
			for(QInventoryCellState[] row : cellGrid) {
				for(QInventoryCellState cell : row) {
					if(!cell.isEmpty() && cell.getBounds().containsCoords(x, y)) {
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
				heldItem.setX(cellLoc.getX());
				heldItem.setY(cellLoc.getY());
				cellGrid[row][col].setItemState(heldItem);
				heldItem = null;
			} else {
				QInventoryItemState temp = cellGrid[row][col].getItemState();
				heldItem.setX(cellLoc.getX());
				heldItem.setY(cellLoc.getY());
				cellGrid[row][col].setItemState(heldItem);
				heldItem = temp;
			}
			
			// Checking win conditions
			if(checkWinConditions()) {	
				if(winConditions.getNextSceneState() != 9999) {
					// Advancing state to next round
					System.out.println("Congratulations! Next round!");
					getGameState().loadScene(winConditions.getNextSceneState());
				} else {
					// Game over, man! Game over!
					// Note: Need to win gracefully
					System.out.println("You did it!");
				}
			}
		}
	}
	
	@Override
	public void resolveMousePosition(Q2DCoords mousePos) {
		super.resolveMousePosition(mousePos);
		
		if(heldItem != null) {
			// Centering held item on cursor
			heldItem.setX(mousePos.getX() - heldItem.getWidth()/2);
			heldItem.setY(mousePos.getY() - heldItem.getHeight()/2);
		}
	}
	
	public Q2DCoords getClickedCell(int x, int y) {
		int x2 = 0;
		int y2 = 0;

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
	
	// Check win conditions
	private boolean checkWinConditions() {
		boolean uWon = true;
		
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
		// set round timer from conditions
	}
	
	public boolean isHintEnabled() {
		return hintModeEnabled;
	}
	
}
