

public class ScoreThing {

	private int holdingScore;
	private int time;
	private int y;
	private int type;
	
	/**
	 * 
	 * @param arg0 The score
	 * @param arg1 Time
	 */
	public ScoreThing(int arg0, int arg1, int arg2, int arg3){
		this.holdingScore = arg0;
		this.time = arg1;
		this.y = arg2;
		this.type = arg3;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHoldingScore() {
		return holdingScore;
	}
	public int getTime() {
		return time;
	}
	public void setHoldingScore(int holdingScore) {
		this.holdingScore = holdingScore;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
}
