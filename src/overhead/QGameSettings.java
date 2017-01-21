package tryan.inq.overhead;

public class QGameSettings {
	private static int winWidth;
	private static int winHeight;
	private static int delay;
	
	public QGameSettings(int windowWidth, int windowHeight, int loopDelay) {
		winWidth = windowWidth;
		winHeight = windowHeight;
		delay = loopDelay;
	}
	
	public static int getWinWidth() {
		return winWidth;
	}
	
	public static void setWinWidth(int width) {
		winWidth = width;
	}
	
	public static int getWinHeight() {
		return winHeight;
	}
	
	public static void setWinHeight(int height) {
		winHeight = height;
	}

	public static int getDelay() {
		return delay;
	}

	public static void setDelay(int delay) {
		QGameSettings.delay = delay;
	}
		
}
