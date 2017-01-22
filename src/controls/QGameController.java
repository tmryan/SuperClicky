package tryan.inq.controls;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import tryan.inq.gfx.QGraphics;
import tryan.inq.overhead.QGameSettings;
import tryan.inq.overhead.QResourceManager;
import tryan.inq.state.QGameState;

public class QGameController {
	private QGameState gameState;
	private static long lastUpdate;
	private QMouseManager mouseMan;
	private QKeyboardManager keyMan;
	private QGameSettings gameSettings;
	private QGraphics gfx;
	
	public QGameController(QResourceManager resMan, QGameState gameState, QGraphics gfx, QGameSettings gameSettings) {
		mouseMan = new QMouseManager();
		keyMan = new QKeyboardManager();
		this.gameState = gameState;
		this.gameState.setKeyboardManager(keyMan);
		this.gameState.setMouseManager(mouseMan);
		this.gfx = gfx;
		
		// Keeping gameSettings in scope so JVM won't banish it to the void
		this.gameSettings = gameSettings;
		
		// Initializing input listeners and starting game loop
		init();
	}
	
	public void init() {
		
		///////////////
		// Controller
		///////////
		
		gfx.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() != KeyEvent.VK_ESCAPE) {
					// Setting pressed key state
					keyMan.setKeyState(e.getKeyCode(), true);
				} else {
					// Note: This can be handled by the key manager
					//		 if clean up is needed before exit
					// If esc key pressed then exit program
					System.exit(0);
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// Clearing released key state
				keyMan.setKeyState(e.getKeyCode(), false);
			}
		});
		
		gfx.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e) && !mouseMan.isMouseDragging()) {
					// Delegating mouse event handling to gameState
					gameState.resolveMouseClick(e.getX(), e.getY());
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e) && mouseMan.isMouseDragging()) {
					// Clearing mouse drag flag and delegating mouse event handling to gameState
					mouseMan.mouseDragReleased();
					gameState.resolveMouseClick(e.getX(), e.getY());
				}
			}
		});
		
		gfx.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(!mouseMan.isMouseDragging()) {
					// Logging current mouse motion state
					mouseMan.setCurrentMousePosition(e.getX(), e.getY());
				}
			}
			
			@Override 
			public void mouseDragged(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					// Setting mouse drag motion flag and tracking mouse position
					mouseMan.mouseDragCommenced();
					mouseMan.setCurrentMousePosition(e.getX(), e.getY());
				}
			}
		});
						
		// Initializing buffers
		gfx.refreshGfx();
				
		// Starting game loop
		startGameLoop();
	}
	
	//////////////
	// Game Loop
	//////////
	
	public void startGameLoop() {
		int msDelay = QGameSettings.getDelay();
		boolean run = true;
		long currentTime = 0;
		long deltaTime = 0;
		long secondTime = 0;
		int frames = 0;
		lastUpdate = 0;

		while(run) {
			currentTime = System.nanoTime() / 1000000L;
			deltaTime = (int) (currentTime - lastUpdate);
			secondTime += deltaTime;
			
			if(deltaTime > msDelay) {				
				// Updating model
				gameState.onTick(deltaTime);
					
				// Updating view
				gfx.updateView(deltaTime);
				gfx.render();
				frames++;
				
				// Note: There is a better way of doing this
				// Track FPS per 1000 ms
				if(secondTime > 1000) {
					// Setting current FPS value to frames
					//gfx.setFPS(frames);
					frames = 0;
					secondTime = 0;
				}
				
				// Tracking update time
				lastUpdate = currentTime;
			
				// Checking game over flag
				if(gameState.isGameOver()) {
					run = false;
				}
			}
		}
	}

}
