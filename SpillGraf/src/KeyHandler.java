

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	private GameVolta gv;
	
	public KeyHandler(GameVolta main){
		gv = main;
	}
	
	
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		if(key == KeyEvent.VK_A){
			gv.getPlayer().setSprite(PlayerSpriteType.WEST);
			gv.A = true;
		}
		if(key == KeyEvent.VK_D){
			gv.getPlayer().setSprite(PlayerSpriteType.EAST);
			gv.D = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		if(key == KeyEvent.VK_A){
			gv.A = false;
		}
		if(key == KeyEvent.VK_D){
			gv.D = false;
		}
		if(key == KeyEvent.VK_ESCAPE){
			gv.gameover = false;
			gv.score = 0;
			gv.lvl = 1;
			gv.fallingSpeed = 2;
			gv.rarenes = 6;
			gv.timed = 0;
			gv.bullets = 0;
			gv.gotHighscoreLock = false;
			gv.pl.setHealth(10);
			gv.voltages.clear();
		}
		if(key == KeyEvent.VK_SPACE){
			gv.fireBullet();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
