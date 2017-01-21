package tryan.inq.state;

public class QInventoryItemState extends QActorState {
	QItemType itemType;
	
	public QInventoryItemState(int x, int y, int width, int height, int id, QItemType itemType) {
		super(x, y, width, height, id);

		this.itemType = itemType;
	}
	
	public QItemType getItemType() {
		return itemType;
	}
	
	public void setItemType(QItemType itemType) {
		this.itemType = itemType;
	}
	
}
