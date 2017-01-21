package tryan.inq.gfx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import tryan.inq.overhead.QGameSettings;
import tryan.inq.state.QGameState;

@SuppressWarnings("serial")
public class QGraphics extends Canvas {
	private QGameState gState;
	private HashMap<Integer, QScene> scenes;
	private QUserInterface ui;
	private BufferStrategy bufferMan;
	private JFrame frame;
	
	// Note: Temporarily here until UI implemented
	int fps;
	long tDelta;
	
	public QGraphics(QGameState gState) {
		this.gState = gState;
		scenes = new HashMap<Integer, QScene>();
		frame = new JFrame();

		// Note: add UI object here
		fps = 0;
		tDelta = 0;

		init();
	}
	
	public void init() {
		// JFrame and Canvas settings
		frame.setIgnoreRepaint(true);
		frame.setSize(QGameSettings.getWinWidth() + 6, QGameSettings.getWinHeight() + 28);
		frame.setTitle("Clicky Game");
		
		setIgnoreRepaint(true);
		setSize(QGameSettings.getWinWidth() + 6, QGameSettings.getWinHeight() + 28);
		
		frame.add(this);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		// Note: If resizable is true, need x+16 and y+38
		frame.setResizable(false);
		frame.setVisible(true);
		
		// Creating double buffering strategy for this Canvas
		createBufferStrategy(2);
		refreshGfx();
	}
	
	public void updateView(long tickTime) {
		scenes.get(gState.getCurrentSceneID()).updateView(tickTime);
	}
	
	public void render() {
		// Note: Only creating this image when no longer compatible saves a few FPS
		BufferedImage bImg = getGraphicsConfiguration().createCompatibleImage(scenes.get(gState.getCurrentSceneID()).getSceneWidth(), 
				scenes.get(gState.getCurrentSceneID()).getSceneHeight());
		Graphics2D bg2 = bImg.createGraphics();
		Graphics2D g2 = null;
		QScene scene = scenes.get(gState.getCurrentSceneID());
		
		do {
			try {
				g2 = (Graphics2D) bufferMan.getDrawGraphics();
				
				// Clearing back buffer before painting
				g2.setColor(Color.BLACK);
				g2.fillRect(0, 0, scene.getSceneWidth(), scene.getSceneWidth());

				// Drawing scene to BufferedImage
				scenes.get(gState.getCurrentSceneID()).draw(bg2);

				// Copying area of scene inside camera viewport onto back buffer
				g2.drawImage(bImg, null, scene.getCamera().getX(), scene.getCamera().getY());
				
				//g2.drawString("FPS: " + fps, 20, 20);
				//g2.drawString("tDelta: " + tDelta, 20, 35);
			} finally {
				g2.dispose();
				bg2.dispose();
			}
			
			// Swap or blt back buffer
			bufferMan.show();
		} while(bufferMan.contentsLost() || bufferMan.contentsRestored());
	}
	
	// For resizing window when user adjusts graphics settings
	public void resizeWindow() {
		frame.setSize(QGameSettings.getWinWidth(), QGameSettings.getWinHeight());
		setSize(QGameSettings.getWinWidth(), QGameSettings.getWinHeight());
		
		refreshGfx();
	}
	
	public void refreshGfx() {	
		bufferMan = getBufferStrategy();
	}
	
	public void addScene(QScene scene, int id) {
		scenes.put(id, scene);
	}
	
//	public void setFPS(int fps) {
//		this.fps = fps;
//	}
}
