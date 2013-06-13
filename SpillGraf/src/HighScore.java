
public class HighScore {

	private String player;
	private int score;
	
	public HighScore(String arg0, int arg1){
		this.player = arg0;
		this.score = arg1;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setPlayer(String player) {
		this.player = player;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
}
