package tryan.inq.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.TreeMap;

import tryan.inq.overhead.QResourceManager;
import tryan.inq.state.QInventoryCellState;
import tryan.inq.state.QInventoryState;

public class QInventoryScene extends QScene {
	// Note: Prefereably only the tiles will need to be duplicated and not cells as well
	TreeMap<Integer, QActor> winConditionActors;
	QInventoryItem heldItem;
		
	public QInventoryScene(int width, int height, QResourceManager resMan, BufferedImage bgImg) {
		super(width, height, resMan, bgImg);
		
		winConditionActors = new TreeMap<Integer, QActor>();
		heldItem = new QInventoryItem(null, resMan, 2);
	}
	
	///////////////
	// updateView()
	////////////
	
	@Override
	public void updateView(long tickTime) {
		if(!((QInventoryState) getSceneState()).isHintEnabled()) {
			for(Integer id : actors.keySet()) {
				actors.get(id).updateView(tickTime);
			}
		} else {
			for(Integer id : winConditionActors.keySet()) {
				winConditionActors.get(id).updateView(tickTime);
			}
		}
		
		if(((QInventoryState) getSceneState()).getHeldItem() != null) {
			heldItem.setItemType(((QInventoryState) getSceneState()).getHeldItem().getItemType());
			heldItem.setX(((QInventoryState) getSceneState()).getHeldItem().getX());
			heldItem.setY(((QInventoryState) getSceneState()).getHeldItem().getY());
		}
	}
	
	/////////
	// draw()
	//////
	
	@Override
	public void draw(Graphics2D g2) {
		// Drawing actors
		
		if(!((QInventoryState) getSceneState()).isHintEnabled()) {
			for(Integer id : actors.keySet()) {
				actors.get(id).draw(g2);
			}
		} else {
			for(Integer id : winConditionActors.keySet()) {
				winConditionActors.get(id).draw(g2);
			}
		}
		
		if(((QInventoryState) getSceneState()).getHeldItem() != null && ((QInventoryState) getSceneState()).getHeldItem().getItemType() != null) {
			heldItem.draw(g2);
		}
	}
	
	public void addWinConditionActor(QActor actor) {
		winConditionActors.put(actor.getActorId(), actor);
	}
	
}
