

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JApplet;

public class GameVolta extends JApplet implements Runnable, ActionListener {

	private static final long serialVersionUID = 4577653112008649452L;
	private Thread t;
	private boolean running = true;
	public boolean enteredNick = false;
	public boolean gotHighscoreLock = false;
	
	/**
	 * If true the game will be even harder.
	 */
	public boolean nightmare = false;
	
	public HashMap<Integer, Volt> voltages;
	public HashMap<Integer, Entity> bulletss;
	public HashMap<Integer, ScoreThing> par1Hasm;
	public HashMap<Integer, HighScore> par2Hasm;
	
	public Player pl;
	private Image hearth;
	private Image[] deathImages = new Image[5];
	
	private long fps = 0;
	
	/* Diffi settings */
	public int rarenes = 6;
	public int fallingSpeed = 2;
	public int lvl = 1;
	public float timed = 0;
	
	public int score = 0;
	
	private Image offscreen;
	private Graphics gd;
	
	public boolean A = false;
	public boolean D = false;
	
	public ImageIcon pls;
	public Image plSprite;
	private int vids;
	
	private boolean inited = false;
	public boolean gameover;
	private Image bullet;
	public int bullets;
	private int bids;
	private int ssids;
	
	private int in1 = 0;
	private int in2 = 0;
	
	@SuppressWarnings("unused")
	private HighscoreHandler highscore;
	private TextField inputLine;
	private Button button1;
	private Button button2;
	private Panel pan;

	public void init(){
		setSize(750, 800);
		addKeyListener(new KeyHandler(this));
		requestFocus();
		setFocusable(true);	
		
		offscreen = createImage(750, 800);
		gd = offscreen.getGraphics();	

		pan = new Panel(new FlowLayout(FlowLayout.CENTER));
		inputLine = new TextField(16);
		
		button1 = new Button("Start >");
		button1.addActionListener(this);
		
		button2 = new Button("Nightmare >");
		button2.addActionListener(this);

		pan.setSize(new Dimension(150, 30));
		pan.setLocation(this.getCenterValueToObjectX(gd, button1) + 50, this.getCenterValueToObjectY(gd, button1) + 150);
		pan.setVisible(false);

		pan.add(inputLine);
		pan.add(button1);
		pan.add(button2);
		add(pan, BorderLayout.SOUTH);
		
		t = new Thread(this);
		t.start();
		
		System.out.println("VoltS 0.8");
		
		for(int var31 = 0; var31 < 5; ++var31){
			deathImages[var31] = new ImageIcon(this.getClass().getResource("dimg" + var31 + ".png")).getImage();
			in1++;
		}
		
		startG();		
	}
	
	public void startG(){
		voltages = new HashMap<Integer, Volt>();
		bulletss = new HashMap<Integer, Entity>();
		par1Hasm = new HashMap<Integer, ScoreThing>();
		
		pl = new Player();
		pl.setHealth(10);
		pl.setX((this.getWidth() - pl.getWidth()) / 2);
		/* pl.setSpritePath("sprite_still.png"); Unused */
		
		pls = new ImageIcon(this.getClass().getResource(pl.getSprite()));
		plSprite = pls.getImage();
		
		vids = 0;
		
		hearth = new ImageIcon(this.getClass().getResource("hearth.png")).getImage();
		bullet = new ImageIcon(this.getClass().getResource("bullet.png")).getImage();
		
		this.highscore = new HighscoreHandler(this, gd);
	}
	
	@Override
	public void run() {
		while(running){
			movmentControl();
			repaint();
			
			long time = System.currentTimeMillis();
			time = (1000 / 60) - (System.currentTimeMillis() - time);
			if(pl != null && pl.getHealth() == 0)
				time = 150;
				
			if(time > 0){
				try {
					fps = time;
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			timed = timed + (time / 7);
		}
	}
	
	private void gameover(GameoverReason reason) {
		gd.clearRect(0, 0, 750, 800);
		AttributedString as;
		String scoree = "Score: " + score + " on level " + lvl + (nightmare ? " WITH NIGHTMARE!" : "");
		String esc = "Press 'ESC'/escape to try again.";
		
		gd.drawImage(new ImageIcon(
				this.getClass().getResource("gameover.png")).getImage(), 0, 0, 750, 800, this);
		
		if(reason == GameoverReason.MAX_SCORE){
			String notice = "You score reached the max score level.";
			as = new AttributedString(notice);
			as.addAttribute(TextAttribute.FONT, new Font("Dialog", Font.PLAIN, 20));
			as.addAttribute(TextAttribute.FOREGROUND, Color.white, 0, notice.length());
			gd.drawString(as.getIterator(), 200, 330);
		}
			
		as = new AttributedString(scoree);
		as.addAttribute(TextAttribute.FONT, new Font("Dialog", Font.PLAIN, 20));
		as.addAttribute(TextAttribute.FOREGROUND, Color.white, 0, scoree.length());
		gd.drawString(as.getIterator(), 200, 320);
		
		as = new AttributedString(esc);
		as.addAttribute(TextAttribute.FONT, new Font("Dialog", Font.PLAIN, 12));
		as.addAttribute(TextAttribute.FOREGROUND, Color.white, 0, esc.length());		
		gd.drawString(as.getIterator(), 200, 340);
		
//		String myScore = highscore.getUsersHighScore(pl.getName());
//		if(myScore != null){
//			myScore = "Din highscore: " + myScore;
//			as = new AttributedString(myScore);
//			as.addAttribute(TextAttribute.FONT, new Font("Dialog", Font.PLAIN, 12));
//			as.addAttribute(TextAttribute.FOREGROUND, Color.white, 0, myScore.length());		
//			gd.drawString(as.getIterator(), 200, 370);
//		}
		
//		if(!gotHighscoreLock){
//			highscore.insert(pl.getName(), score);
//			par2Hasm = highscore.getHighscore();
//			gotHighscoreLock = true;
//		}
		
//		if(!par2Hasm.isEmpty()){
//			AttributedString aa = new AttributedString("-- HIGHTSCORES --");
//			aa.addAttribute(TextAttribute.FONT, new Font("Dialog", Font.PLAIN, 30));
//			aa.addAttribute(TextAttribute.FOREGROUND, Color.white, 0, 17);			
//			gd.drawString(aa.getIterator(), 200, 400);
//			
//			Iterator it = par2Hasm.entrySet().iterator();
//			int yatm = 400;
//			while(it.hasNext()){
//				Map.Entry pairs = (Map.Entry)it.next();
//				HighScore v = (HighScore) pairs.getValue();
//				if(v != null){
//					yatm += 18;
//					String theS = ((int) pairs.getKey()) + " | " + v.getPlayer() + " - " + v.getScore();
//					AttributedString aa1 = new AttributedString(theS);
//					aa1.addAttribute(TextAttribute.FONT, new Font("Dialog", Font.PLAIN, 15));
//					aa1.addAttribute(TextAttribute.FOREGROUND, Color.WHITE, 0, theS.length());
//					gd.drawString(aa1.getIterator(), 200, yatm);
//				}
//			}
//		}
		
		this.getGraphics().drawImage(offscreen, 0, 0, this);
		gameover = true;
	}
	
	@SuppressWarnings("rawtypes")
	private void updateEntites() {
		/* Generate voltages and update them */
		if(!voltages.isEmpty()){
			Iterator it = voltages.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry pairs = (Map.Entry)it.next();
				Volt v = (Volt) pairs.getValue();
				if(v != null){
					int y = v.getY();
					if(y > 800){
						it.remove();
						voltages.remove(v);
					} else {
						if(v.getAType() == 1){
							gd.fillOval(v.getX(), v.getY(), 10, 10);
						}
						
						v.setY(v.getY() + fallingSpeed);
						gd.drawOval(v.getX(), v.getY(), 10, 10);						
						Rectangle plb = pl.getBounds();
						Rectangle vob = new Rectangle(v.getX(), y, 10, 10);					
						if(plb.intersects(vob)){
							if(v.getAType() == 1){
								// Give 2 bullets
								bullets += 2;
								it.remove();
								par1Hasm.put(ssids, new ScoreThing(2, 0, (800 - pl.getHeight() + 5), 1));
							} else {
								pl.addVoltage(v.getVoltage());
								it.remove();
								pl.setHealth(pl.getHealth() - 1);
							}
						}
						
						/* Bullets */
						if(!bulletss.isEmpty()){
							Iterator it1 = bulletss.entrySet().iterator();
							while(it1.hasNext()){
								Map.Entry pairs1 = (Map.Entry)it1.next();
								Entity e = (Entity) pairs1.getValue();
								if(e != null){
									int y1 = e.getY();
									if(y1 < 0){
										it1.remove();
									} else {							
										e.setY(e.getY() - 2);
										gd.drawImage(bullet, e.getX(), e.getY(), bullet.getWidth(this), bullet.getHeight(this), this);
										Rectangle bob = new Rectangle(e.getX(), e.getY(), bullet.getWidth(this), bullet.getHeight(this));					
										if(bob.intersects(vob)){
											if(v.getAType() == 1){
												par1Hasm.put(ssids, new ScoreThing(2, 0, (800 - pl.getHeight() + 5), 1));
												bullets += 2;
											} else {
												par1Hasm.put(ssids, new ScoreThing((50 * lvl), 0, (800 - pl.getHeight() + 5), 0));
												score += 50 * lvl;
												it.remove();
												it1.remove();
												ssids++;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void generateVoltages() {
		Random rand = new Random();
		int var8 = (nightmare ? 2 : 1);
		int var7 = rarenes + 100 * var8;
		if(rand.nextInt((nightmare ? rarenes * 6 : 80)) <= rarenes && voltages.size() < var7){
			// Generate this voltage.
			int x = (int) (7 + Math.random() * (740 - 7));
			Volt v = new Volt(x);
			v.generateVoltage();
			
			if(rand.nextInt(100 * lvl) <= (nightmare ? 1 : 2)){
				// More bullets volt.
				v.setType(1);
			}
			
			vids++;
			voltages.put(vids, v);
		}
	}

	private void movmentControl() {
		
		if(A)
			if(pl.getX() >= 0)
				pl.setX(pl.getX() - (7 + lvl * (nightmare ? 4 : 2)));
				
		if(D)
			if(pl.getX() <= (750 - plSprite.getWidth(this)))
				pl.setX(pl.getX() + (7 + lvl * (nightmare ? 4 : 2)));
				
		if(A){
			pls = new ImageIcon(this.getClass().getResource(PlayerSpriteType.WEST.getName()));
			plSprite = pls.getImage();
		} else if(D){
			pls = new ImageIcon(this.getClass().getResource(PlayerSpriteType.EAST.getName()));
			plSprite = pls.getImage();
		} else if(!A && !D){
			pls = new ImageIcon(this.getClass().getResource(PlayerSpriteType.STILL.getName()));
			plSprite = pls.getImage();
		}
	}

	public void paint(Graphics g){
		update(g);
	}
	
	public void update(Graphics g){
		gd.clearRect(0, 0, 750, 800);
		
		if(gameover){
			gameover(GameoverReason.DIED);
		} else {
			if(inited){
				if(pl.getHealth() != 0){
					/* LVL handler, total lvls: 10. Multiply by three each then divide with four. */
					float var6 = lvl * 1500;
					if(timed > var6 && lvl < 10){
						if(pl.getHealth() <= (10 - lvl))
							pl.setHealth(pl.getHealth() + lvl);
							par1Hasm.put(ssids, new ScoreThing(lvl, 0, (800 - pl.getHeight() + 5), 2));
						
						lvl++;
						rarenes += (nightmare ? 4 * lvl : 2);
						fallingSpeed += (nightmare ? 4 : 2);
						timed = 0;
						bullets += (nightmare ? 1 : 2);
					}
					
					updateEntites();
					generateVoltages();
					drawPlayer();
					drawScoreOverPlayer();
					
					/* Hearths */
					int var1 = pl.getHealth();
					for(int var2 = 1; var2 < (nightmare ? 11 * 2 : 11); ++var2){
						int var3 = 21;
						int var4 = 750 - var3;
						int var5 = var4 - (var3 * var2);
						int var7 = 10;
						
						if(var2 == (nightmare ? 20 : 10)){
							if(var1 == (nightmare ? 20 : 10))
								gd.drawImage(hearth, var5, var7, 17, 21, this);
						} else {
							if(var1 >= var2)				
								gd.drawImage(hearth, var5, var7, 17, 21, this);
						}
					}

					if(score >= Integer.MAX_VALUE){
						gameover(GameoverReason.MAX_SCORE);
					}
					int nxpb = nightmare ? 2 + lvl : 0;
					score = score + lvl + nxpb;
				} else {
					drawPlayerDeath();
				}
				
				/* Text */
				int totalxpb = lvl + (nightmare ? 2 + lvl : 0);
				gd.drawString("VOLT 0.8 ("+fps+" fps)", 3, 10);
				gd.drawString("Level: " + lvl, 3, 25);
				gd.drawString("Score: " + score + " +" + totalxpb, 3, 40);
				gd.drawString("Bullets: " + bullets, 3, 55);
				if(nightmare){
					AttributedString asN = new AttributedString("NIGHTMARE");
					asN.addAttribute(TextAttribute.FOREGROUND, Color.RED, 0, 9);
					gd.drawString(asN.getIterator(), 3, 70);
				}
			} else {
				// logo
				drawLogo();
			}
		}
		g.drawImage(offscreen, 0, 0, this);
	}
	
	/**
	 * Render player death animation.
	 */
	private void drawPlayerDeath() {
		if(in2 >= in1){
			gameover(GameoverReason.DIED);
			gameover = true;
		} else {
			try {
				Image img = deathImages[in2];
				if(img != null){
					gd.drawImage(img, pl.getX(), pl.getY(), img.getWidth(this), img.getHeight(this), this);
				}
			} catch(ArrayIndexOutOfBoundsException e){
				// ignore.
			}

			in2++;
		}
	}

	/**
	 * Called when a player hits SPLACE and fires a bullet on the ++Y axis.
	 */
	public void fireBullet() {
		if(bullets <= 0)
			return;
			
		bullets--;
		bids++;
		Entity e = new Entity(EntityType.BULLET);
		e.setHealth(1);
		e.setX(pl.getX());
		e.setY((800 - pl.getHeight()) + 5);			
		bulletss.put(bids, e);		
	}

	public void drawLogo(){
		if(timed > 300){
			if(timed > 800 && enteredNick){
				timed = 0;
				inited = true;
				System.out.println("Game started!");
			}
			Font font = new Font("Arial", Font.BOLD, 70);
			Font font2 = new Font("Arial", Font.PLAIN, 15);
			Font font3 = new Font("Dialog", Font.PLAIN, 12);
			Font font4 = new Font("Dialog", Font.PLAIN, 15);
			String tt = "______VoltS______";
			String tt2 = "Dodge the Volts, move with A and D keys and shoot with SPACE. Ovals with black fills gives more bullets";
			String tt4 = "Type in a nickname under, this nick will be used on the highscore list (Max length is 16)";
			String tt3 = "Developed by Edvin Hultberg 2013";
			AttributedString as = new AttributedString(tt);
			as.addAttribute(TextAttribute.FONT, font);
			as.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 0, tt.length());
			
			gd.drawString(as.getIterator(), this.getCenterValueToStringX(gd, font, tt), this.getCenterValueToStringY(gd, font, tt));
			
			AttributedString as1 = new AttributedString(tt2);
			as1.addAttribute(TextAttribute.FONT, font2);
			as1.addAttribute(TextAttribute.FOREGROUND, Color.BLACK, 0, tt2.length());
			
			/* Nickname */
			pan.setVisible(true);
			this.revalidate();
			
			AttributedString as5 = new AttributedString(tt4);
			as5.addAttribute(TextAttribute.FONT, font4);
			
			gd.drawString(as1.getIterator(), this.getCenterValueToStringX(gd, font2, tt2), this.getCenterValueToStringY(gd, font2, tt2) + 10);
			gd.drawString(tt3, this.getCenterValueToStringX(gd, font3, tt3), this.getCenterValueToStringY(gd, font3, tt3) + 30);
			gd.drawString(as5.getIterator(), this.getCenterValueToStringX(gd, font4, tt4), this.getCenterValueToStringY(gd, font4, tt4) + 350);
		} else {
			Font font = new Font("Times New Roman", Font.BOLD, 40);
			String tt = "hultberg productions";
			AttributedString as = new AttributedString(tt);
			as.addAttribute(TextAttribute.FONT, font);
			as.addAttribute(TextAttribute.FOREGROUND, Color.WHITE, 0, tt.length());
			
			gd.draw3DRect((750 - 400) / 2, (800 - 100) / 2, 400, 100, false);
			gd.fill3DRect((750 - 400) / 2, (800 - 100) / 2, 400, 100, false);
			gd.drawString(as.getIterator(), ((750 - 400) / 2) + 46, ((800 - 200) / 2) + 139);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void drawScoreOverPlayer(){		
		if(!par1Hasm.isEmpty()){
			Iterator it2 = par1Hasm.entrySet().iterator();
			while(it2.hasNext()){
				Map.Entry pairs2 = (Map.Entry)it2.next();
				ScoreThing e = (ScoreThing) pairs2.getValue();
				if(e != null){
					if(e.getTime() > 30){
						it2.remove();
					} else {
						e.setTime(e.getTime() + 1);
						e.setY(e.getY() - 1);
						if(e.getType() == 1){
							gd.drawString("B +" + e.getHoldingScore(), pl.getX(), e.getY());							
						} else if(e.getType() == 2){
							gd.drawString(e.getHoldingScore() + " up", pl.getX(), e.getY());							
						} else {
							gd.drawString("+" + e.getHoldingScore(), pl.getX(), e.getY());
						}
					}
				}
			}
		}
	}
	
	private void drawPlayer() {
		int h = plSprite.getHeight(this);
		gd.drawImage(plSprite, pl.getX(), (800 - h), plSprite.getWidth(this), h, this);
	}

	public Player getPlayer(){
		return pl;
	}
	
	public int getCenterValueToStringX(Graphics g, Font f, String s){
		FontMetrics fm   = g.getFontMetrics(f);
		java.awt.geom.Rectangle2D rect = fm.getStringBounds(s, g);
		
		int textWidth  = (int)(rect.getWidth());
		int panelWidth = this.getWidth();

		return (panelWidth  - textWidth)  / 2;
	}
	
	public int getCenterValueToStringY(Graphics g, Font f, String s){
		FontMetrics fm   = g.getFontMetrics(f);
		java.awt.geom.Rectangle2D rect = fm.getStringBounds(s, g);
		
		int textHeight  = (int)(rect.getHeight());
		int panelHeight = this.getHeight();

		return (panelHeight  - textHeight)  / 2;
	}
	
	public int getCenterValueToObjectX(Graphics g, TextField s){
		int width = s.getWidth();
		int panelWidth = this.getWidth();

		return (panelWidth - width)  / 2;
	}
	
	public int getCenterValueToObjectX(Graphics g, Button s){
		int width = s.getWidth();
		int panelWidth = this.getWidth();

		return (panelWidth - width)  / 2;
	}
	
	public int getCenterValueToObjectY(Graphics g, TextField s){
		int width = s.getHeight();
		int panelWidth = this.getHeight();

		return (panelWidth - width)  / 2;
	}
	
	public int getCenterValueToObjectY(Graphics g, Button s){
		int width = s.getHeight();
		int panelWidth = this.getHeight();

		return (panelWidth - width)  / 2;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == button1){
			if(inputLine.getText().equals("") || inputLine.getText().equals(null) || inputLine.getText().length() > 16){
				return;
			}
			
			String nick = inputLine.getText().trim();
			enteredNick = true;
			pl.setName(nick);
			nightmare = false;
			inputLine.setVisible(false);
			button1.setVisible(false);
			button2.setVisible(false);
			this.revalidate();			
		} else if(arg0.getSource() == button2){
			if(inputLine.getText().equals("") || inputLine.getText().equals(null) || inputLine.getText().length() > 16){
				return;
			}
			
			String nick = inputLine.getText().trim();
			enteredNick = true;
			pl.setName(nick);
			nightmare = true;
			pl.setHealth(20);
			inputLine.setVisible(false);
			button1.setVisible(false);
			button2.setVisible(false);
			this.revalidate();
		}
	}
	
	public static int safeLongToInt(long l) {
	    if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
	        throw new IllegalArgumentException
	            (l + " cannot be cast to int without changing its value.");
	    }
	    return (int) l;
	}
	
	public static void save(Object obj, File binFile) throws Exception {
		ObjectOutputStream oops = new ObjectOutputStream(new FileOutputStream(binFile));
		oops.writeObject(obj);
		oops.flush();
		oops.close();
	}
	
	public static Object load(File binFile) throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(binFile));
		Object result = ois.readObject();
		ois.close();
		return result;
	}
	
}
