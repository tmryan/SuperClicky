package tryan.inq.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.TreeMap;

import tryan.inq.overhead.QResourceManager;
import tryan.inq.state.QSceneState;

public class QScene {
	private int sceneWidth;
	private int sceneHeight;
	private QSceneState sceneState;
	private BufferedImage backGroundImg;
	private QCameraActor camera;
	private QResourceManager resMan;
	
	// Note: Need priority queue for discerning draw order?
	// 		 Or separate player and other important actors from actor map and draw last?
	//		 May only need priority for actors that can change their order
	TreeMap<Integer, QActor> actors;
		
	public QScene(int width, int height, QResourceManager resMan, BufferedImage bgImg) {
		sceneState = null;
		backGroundImg = bgImg;
		sceneWidth = width;
		sceneHeight = height;
		actors = new TreeMap<Integer, QActor>();
		camera = new QCameraActor(0, 0);
		this.resMan = resMan;
	}
	
	///////////////
	// updateView()
	////////////
	
	public void updateView(long tickTime) {
		for(int id : actors.keySet()) {
			actors.get(id).updateView(tickTime);
		}
	}
	
	/////////
	// draw()
	//////
	
	public void draw(Graphics2D g2) {
		// Drawing background first
		if(backGroundImg != null) {
			g2.drawImage(backGroundImg, null, 0, 0);
		}
		
		// Drawing actors
		for(Integer id : actors.keySet()) {
			actors.get(id).draw(g2);
		}
	}
	
	/////////////////
	// Other Methods
	//////////////
	
	public void attachSceneState(QSceneState sceneState) {
		this.sceneState = sceneState;
	}
	
	public void addActor(QActor actor) {
		actors.put(actor.getActorId(), actor);
	}
	
	public BufferedImage getBackgroundImg() {
		return backGroundImg;
	}
		
	public void setBackgroundImg(BufferedImage backGroundImg) {
		this.backGroundImg = backGroundImg;
	}
	
	public int getSceneWidth() {
		return sceneWidth;
	}
	
	public int getSceneHeight() {
		return sceneHeight;
	}
	
	public QSceneState getSceneState() {
		return sceneState;
	}
	
	public QCameraActor getCamera() {
		return camera;
	}
	
	protected QResourceManager resMan() {
		return resMan;
	}
}
