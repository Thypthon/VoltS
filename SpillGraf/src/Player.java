

import java.awt.Rectangle;

public class Player extends Entity {

	private String name;
	
	/* Movement Sprites */
	private String m_a = "sprite_walking_A.png";
	private String m_d = "sprite_walking_D.png";
	private String m = "sprite_still.png";
	private String spriteAtm;
	private int width;
	private int height;
	private int totalVoltage;
	
	public Player() {
		super(EntityType.PLAYER);
		this.spriteAtm = PlayerSpriteType.STILL.getName();
		this.width = PlayerSpriteType.STILL.getW();
		this.height = PlayerSpriteType.STILL.getH();
		this.y = 800 - height;
	}
	
	public void setName(String a) {
		this.name = a;
	}

	public String getName() {
		return name;
	}
	
	public void setSprite(PlayerSpriteType t){
		this.spriteAtm = t.getName();
		this.width = t.getW();
		this.height = t.getH();
	}
	
	public String getSprite(){
		return spriteAtm;
	}

	public String getMovementSpriteA() {
		return m_a;
	}

	public String getMovementSpriteD() {
		return m_d;
	}

	public String getMovementSpriteStill() {
		return m;
	}	
	
	public Rectangle getBounds() {
        return new Rectangle(x, y, width, height / 2);
    }

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getTotalVoltage() {
		return totalVoltage;
	}

	public void setTotalVoltage(int totalVoltage) {
		this.totalVoltage = totalVoltage;
	}
	
	public void addVoltage(int a){
		this.totalVoltage = this.totalVoltage + a;
	}

	

}
