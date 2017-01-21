package tryan.inq.state;

public class QInventoryCellState extends QActorState {
	private QInventoryItemState itemState;
	private boolean isEmpty;
	private int row;
	private int col;
	
	public QInventoryCellState(int x, int y, int width, int height, int row, int col, int id) {
		super(x, y, width, height, id);
		
		itemState = null;
		isEmpty = true;
		
		this.row = row;
		this.col = col;
	}

	public void removeItemState() {
		itemState = null;
		isEmpty = true;
	}
	
	public void setItemState(QInventoryItemState itemState) {
		this.itemState = itemState;
		isEmpty = false;
	}
	
	public QInventoryItemState getItemState() {
		return itemState;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
		
}
