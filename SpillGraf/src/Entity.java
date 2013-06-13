


public class Entity {

	private String spritePath;
	private EntityType type;
	private boolean alive;
	public int health;
	public int x;
	public int y;
	
	public Entity(EntityType type){
		this.type = type;
		this.x = 0;
		this.y = 0;
		this.alive = true;
		this.health = 0;
		this.spritePath = "";
	}
	
	public String getSpritePath() {
		return spritePath;
	}

	public void setSpritePath(String spritePath) {
		this.spritePath = spritePath;
	}

	public boolean isAlive() {
		return alive;
	}

	public int getHealth() {
		return health;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}	
	
}
