package tryan.inq.controls;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import tryan.inq.gfx.QGraphics;
import tryan.inq.gfx.QUserInterface;
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
	private QUserInterface ui;
	
	public QGameController(QResourceManager resMan, QGameState gameState, QGraphics gfx, QGameSettings gameSettings) {
		mouseMan = new QMouseManager();
		keyMan = new QKeyboardManager();
		this.gameState = gameState;
		this.gameState.setKeyboardManager(keyMan);
		this.gameState.setMouseManager(mouseMan);
		this.gfx = gfx;
		
		// Keeping game settings in scope so the JVM won't send it to the void
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
				keyMan.setKeyState(e.getKeyCode(), true);
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				keyMan.setKeyState(e.getKeyCode(), false);
			}
		});
		
		gfx.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					gameState.resolveMouseClick(e.getX(), e.getY());
				}
			}
		});
		
		gfx.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseMan.setCurrentMousePosition(e.getX(), e.getY());
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
		//int totalTicks = 0;
		lastUpdate = 0;
			
		/* 
		 * Note: Need game tick and art tick?
		 * 
		 * Note: CPU may be over used, throttle loop?
		 * 
		 * Note: Need to update from game settings here as well
		 * 
		 * Note: Track FPS and add frame counter
		 * 
		 * Note: After some light reading, it sounds like using OpenGL may be the 
		 * 		 best way to improve frame rate
		 */
		
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
				
				// Track FPS per 1000 ms
				if(secondTime > 1000) {
					// Setting current FPS value to frames
					//gfx.setFPS(frames);
					frames = 0;
					secondTime = 0;
				}
				
				// Note: May need to track both gfx update and state updates at some point
				// Tracking update time and game ticks
				lastUpdate = currentTime;
				//totalTicks++;
			}
		}
	}

}
