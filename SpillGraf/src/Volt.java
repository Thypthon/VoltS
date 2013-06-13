

import java.util.Random;

public class Volt extends Entity {

	private int voltage;
	private int aniS;
	private int atype;
	
	public Volt(int x){
		super(EntityType.VOLT);
		this.setHealth(1);
		this.x = x;
		this.aniS = 0;
		voltage = 4; // Default.
		this.atype = 0;
	}
	
	public int getAType() {
		return atype;
	}

	public void setType(int type) {
		this.atype = type;
	}

	public int getVoltage(){
		return this.voltage;
	}
	
	public void setVoltage(int v){
		this.voltage = v;
	}
	
	public void generateVoltage(){
		Random randed = new Random();
		setVoltage(randed.nextInt(50));
	}

	public int getAniS() {
		return aniS;
	}

	public void setAniS(int aniS) {
		this.aniS = aniS;
	}
	
	public void addAniS(int a) {
		this.aniS = this.aniS + a;
	}
	
}
