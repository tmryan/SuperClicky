package tryan.inq.gfx;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import tryan.inq.overhead.QResourceManager;
import tryan.inq.state.QInventoryCellState;

public class QInventoryCell extends QActor {
		
	public QInventoryCell(BufferedImage img, QResourceManager resMan, int layer) {
		super(img, resMan, layer);
	}
	
	@Override
	public void draw(Graphics2D g2) {
		if(getImg() != null) {
			if(((QInventoryCellState) getActorState()).isEmpty()) {
				g2.drawImage(getImg(), null, getX(), getY());
			} else {
				g2.drawImage(getImg(), null, getX(), getY());
				g2.drawImage(resMan().getImage(((QInventoryCellState) getActorState()).getItemState().getItemType().getTypeName()), null, getX(), getY());
			}
			
			if(getActorState().isHighlightable() && getActorState().isHighlighted()) {
				g2.drawImage(resMan().getImage("cellHighlight"), null, getX(), getY());
			}
		}
	}
	
}
