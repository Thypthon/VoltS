
public enum ErrorMessage {

	GET_HIGHSCORES("Error while retrieving highscores."),
	CONNECT_MYSQL("Error while connecting to mysql.");
	
	private String reason;

	ErrorMessage(String reason){
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}
	
	public int getLength(){
		return reason.length();
	}
	
}
