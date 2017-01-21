package tryan.inq.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import tryan.inq.overhead.QResourceManager;
import tryan.inq.state.QActorState;

public class QActor {
	private int id;
	private int x;
	private int y;
	private int layer;
	private int tDelta;
	private BufferedImage img;
	private QActorState actorState;
	private QResourceManager resMan;
	
	public QActor(BufferedImage img, QResourceManager resMan, int layer) {
		id = 0;
		this.img = img;
		x = 0;
		y = 0;
		this.resMan = resMan;
		this.layer = layer;
		tDelta = 0;
		actorState = null;
	}
	
	// Update view from current model state
	public void updateView(long tickTime) {
		tDelta += tickTime;
		
		x = actorState.getX();
		y = actorState.getY();
	}
		
	public void draw(Graphics2D g2) {
		if(img != null) {
			g2.drawImage(img, null, x, y);
		}
	}
	
	public void attachActorState(QActorState actorState) {
		x = actorState.getX();
		y = actorState.getY();
		this.actorState = actorState;
		id = actorState.getActorId();
	}

	public QActorState getActorState() {
		return actorState;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return actorState.getWidth();
	}
	
	public int getHeight() {
		return actorState.getHeight();
	}
	
	public int getActorId() {
		return id;
	}
		
	public BufferedImage getImg() {
		return img;
	}
	
	public int getTDelta() {
		return tDelta;
	}
	
	public void setTDelta(int time) {
		tDelta = time;
	}
	
	public int getLayer() {
		return layer;
	}
	
	protected QResourceManager resMan() {
		return resMan;
	}
	
}
