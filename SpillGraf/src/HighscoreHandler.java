import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class HighscoreHandler {
	
	@SuppressWarnings("unused")
	private Graphics gdd;
	private GameVolta gv;

	public HighscoreHandler(GameVolta a, Graphics gr){
		this.gdd = gr;
		this.gv = a;
	}

	@SuppressWarnings("unused")
	public void insert(String name, int score) {
		try {
			URL url = new URL("http://www.thehultberg.net/g/v/highscores.php?action=submit&n="+(gv.nightmare ? 1 : 0)+"&player=" + name + "&score=" + score + "&access_code=34987529345792834598234597328457324572345982345983245901324590230952305");
			InputStream in = url.openStream();
			BufferedReader b = new BufferedReader(new InputStreamReader(in));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	public HashMap<Integer, HighScore> getHighscore(){
		HashMap<Integer, HighScore> tmp = new HashMap<Integer, HighScore>(10);
		
		try {
			URL url = new URL("http://www.thehultberg.net/g/v/highscores.php?action=list&n=" + (gv.nightmare ? 1 : 0) + "&access_code=34987529345792834598234597328457324572345982345983245901324590230952305");
			InputStream in = url.openStream();
			BufferedReader b = new BufferedReader(new InputStreamReader(in));
			String sCurrentLine;
			int i = 0;
			while ((sCurrentLine = b.readLine()) != null) {
				i++;
				// Format and so on.
				String[] nn = b.readLine().split("-");
				tmp.put(i, new HighScore(nn[0], Integer.parseInt(nn[1])));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tmp;
	}
    
}
