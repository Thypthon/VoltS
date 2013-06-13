

public enum PlayerSpriteType {
	
	STILL("sprite_still.png", 68, 137),
	WEST("sprite_walking_A.png", 53, 132),
	EAST("sprite_walking_D.png", 54, 133);

	private String name;
	private int w;
	private int h;
	
	PlayerSpriteType(String arg0, int w, int h){
		this.name = arg0;
		this.w = w;
		this.h = h;
	}
	
	public String getName(){
		return name;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}
	
}
