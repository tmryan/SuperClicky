package tryan.inq.gfx;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import tryan.inq.overhead.QResourceManager;
import tryan.inq.state.QItemType;

public class QInventoryItem extends QActor {
	QItemType itemType;
	
	public QInventoryItem(BufferedImage img, QResourceManager resMan, int layer) {
		super(img, resMan, layer);
		
		itemType = null;
	}
	
	@Override
	public void draw(Graphics2D g2) {		
		if(itemType != null) {
			g2.drawImage(resMan().getImage(itemType.getTypeName()), null, getX(), getY());
		}
	}
	
	public void setItemType(QItemType itemType) {
		this.itemType = itemType;
	}
	
}
