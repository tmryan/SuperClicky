package tryan.inq.state;

public enum QAnimState {
	IDLE(0, "idle", true),
	GLOWING(0, "glow", true), 
	BLINKING(0, "blink", true),
	SPARKLING(0, "sparkle", true);
	
	private final int animState;
	private final String animName;
	private final boolean isLooping;
	
	private QAnimState(int animState, String animName, boolean isLooping) {
		this.animState = animState;
		this.animName = animName;
		this.isLooping = isLooping;
	}
	
	public int getAnimState() { return animState; }
	public String getAnimName() { return animName; }
	public boolean isLooping() { return isLooping; }
}
