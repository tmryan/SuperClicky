package tryan.inq.controls;

import java.awt.event.KeyEvent;

public class QKeyboardManager {
	private boolean[] keyStates;
	
	public QKeyboardManager() {
		keyStates = new boolean[1];
		
		init();
	}

	private void init() {
		for(int i = 0; i < keyStates.length; i++) {
			keyStates[i] = false;
		}
	}
	
	public void setKeyState(int key, boolean isPressed) {
		switch(key) {
			// Entering hint mode for debugging
			// P.S. This isn't in the demo
			case KeyEvent.VK_W:
				keyStates[0] = isPressed;
				break;
			default:
				break;
		}
	}
	
	public boolean[] getKeyboardState() {
		return keyStates;
	}

}
