package tryan.inq.state;

public enum QItemType {
	RED(0, "redItem"),
	BLUE(1, "blueItem"),
	PURPLE(2, "purpleItem"),
	GREEN(3, "greenItem"),
	PH(4, "phItem"),
	EMPTY(3, "empty");
	
	private final int itemType;
	private final String typeName;
	
	private QItemType(int itemType, String typeName) {
		this.itemType = itemType;
		this.typeName = typeName;
	}
	
	public int getItemType() { return itemType; }
	public String getTypeName() { return typeName; }
}
