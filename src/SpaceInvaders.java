import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/************************************************
 * Name:					Max Chou
 * Block: 					B
 * Date Last Modified: 		5/5/16
 * 
 * Program: SpaceInvaders.java
 * 
 * Description: Implements all classes 
 * together to create a game of Space Invaders!
 * A single player game with an objective to 
 * shoot down all the enemies before they are 
 * able to reach you!
 * 
***********************************************/

public class SpaceInvaders 
						extends JFrame 
						implements ActionListener, KeyListener 
{
	// DATA:
	private static Defender d;													// The Defender
	private static ArrayList<Bullet> b = new ArrayList<Bullet>();				// The Bullets
	private static ArrayList<Barrier> bar = new ArrayList<Barrier>();			// The Barriers
	private static ArrayList<Enemy> enemy = new ArrayList<Enemy>();				// The Enemies
	private static ArrayList<EnemyBullet> eb = new ArrayList<EnemyBullet>();	// The Enemy Bullets
	private static int lives = 3;
	private static int score = 0;

	private static final int MAX_WIDTH = 600;								// Window size
	private static final int MAX_HEIGHT = 700;								// Window size
	private static final int TOP_OF_WINDOW = 22;							// Top of the visible window
	
	private static final int DELAY_IN_MILLISEC = 30;  						// Time delay between updates
	
	private int defenderX = MAX_WIDTH / 2;									// Starting X
	private int defenderY = MAX_HEIGHT - 50;								// Starting Y
	
	private int barrierSpacing = 108;						// Spacing between each barrier
	private int barrierMidRowNum = 2;						// Number of smaller barriers that make up each larger barrier
	private int barrierMidRowSpacing = 34;					// Spacing between the two smaller areas
	private int barrierX1 = barrierSpacing;					// X of first
	private int barrierX2 = barrierX1 + barrierSpacing;		// X of second
	private int barrierX3 = barrierX2 + barrierSpacing;		// X of third
	private int barrierX4 = barrierX3 + barrierSpacing;		// X of fourth
	private int barrierY = MAX_HEIGHT - 150;				// Y of all
	private int barrierLength = 60;							// Larger length
	private int barrierMiniLength = 10;						// Length of smaller barriers that make up each larger barrier
	private int barrierHeight = 10;							// Height of each small barrier
	
	private int enemyX = 50;								// X of the enemy
	private int enemyY = 50;								// Y of the enemy
	private int enemySpacingHor = 50;						// Spacing between the Enemies Horizontally
	private int enemySpacingVer = 50;						// Spacing between the Enemies Vertically
	private int numPerRow = 10;								// Number of Enemies per row
	private int numRows = 5;								// Number of rows of Enemies
	
	private long lastPressProcessed = 0;					// Time between shots
	private BufferedImage loseScreen;
	private BufferedImage enemyStartScreen;
	private BufferedImage winScreen;
	private boolean start = false;							// Determine whether to start
	private boolean invadersWin = false;					// Determine whether you lose
	private boolean win = false;							// Determine whether you win
	private boolean finalWinScreen = false;					// Determine whether to show winScreen
	private boolean finalLoseScreen = false;				// Determine wheter to show loseScreen
		
	// METHODS:
	
	/**
	 * main -- Start up the window.
	 * 
	 * @param	args
	 */
	public static void main(String args[])
	{
		// Create the window.
		SpaceInvaders mb = new SpaceInvaders();
		mb.setSize(MAX_WIDTH, MAX_HEIGHT);
		mb.setVisible(true);
		  
		mb.addKeyListener(mb);
	}
	
	public SpaceInvaders()
	{
		// Making the Defender
		d = new Defender (defenderX, defenderY);
		
		// Making the Enemies
		for (int i = 0; i < numRows; i++)
		{
			for (int j = 0; j < numPerRow; j++)
			{
				
				try {
					enemy.add(new Enemy (enemyX + (j * enemySpacingHor), enemyY + (i * enemySpacingVer), "Enemy.png", j, i));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				
			}
		}
		 
		// First Barrier
		for (int i = 0; i < (int) (barrierLength / barrierMiniLength); i++)
		{
			bar.add(new Barrier (barrierX1 + (i * barrierMiniLength), barrierY - (0 * barrierHeight), barrierMiniLength, barrierHeight));
			bar.add(new Barrier (barrierX1 + (i * barrierMiniLength), barrierY - (1 * barrierHeight), barrierMiniLength, barrierHeight));
		}
		
		for (int i = 0; i < barrierMidRowNum; i++)
		{
			bar.add(new Barrier (barrierX1 + (i * barrierMiniLength), barrierY + (1 * barrierHeight), barrierMiniLength, barrierHeight));
			bar.add(new Barrier (barrierX1 + (i * barrierMiniLength) + (barrierLength / barrierMiniLength + barrierMidRowSpacing), barrierY + (1 * barrierHeight), barrierMiniLength, barrierHeight));
		}
		
		// Second Barrier
		for (int i = 0; i < (int) (barrierLength / barrierMiniLength); i++)
		{
			bar.add(new Barrier (barrierX2 + (i * barrierMiniLength), barrierY - (0 * barrierHeight), barrierMiniLength, barrierHeight));
			bar.add(new Barrier (barrierX2 + (i * barrierMiniLength), barrierY - (1 * barrierHeight), barrierMiniLength, barrierHeight));
		}
		
		for (int i = 0; i < barrierMidRowNum; i++)
		{
			bar.add(new Barrier (barrierX2 + (i * barrierMiniLength), barrierY + (1 * barrierHeight), barrierMiniLength, barrierHeight));
			bar.add(new Barrier (barrierX2 + (i * barrierMiniLength) + (barrierLength / barrierMiniLength + barrierMidRowSpacing), barrierY + (1 * barrierHeight), barrierMiniLength, barrierHeight));
		}
		
		// Third Barrier
		for (int i = 0; i < (int) (barrierLength / barrierMiniLength); i++)
		{
			bar.add(new Barrier (barrierX3 + (i * barrierMiniLength), barrierY - (0 * barrierHeight), barrierMiniLength, barrierHeight));
			bar.add(new Barrier (barrierX3 + (i * barrierMiniLength), barrierY - (1 * barrierHeight), barrierMiniLength, barrierHeight));
		}
		
		for (int i = 0; i < barrierMidRowNum; i++)
		{
			bar.add(new Barrier (barrierX3 + (i * barrierMiniLength), barrierY + (1 * barrierHeight), barrierMiniLength, barrierHeight));
			bar.add(new Barrier (barrierX3 + (i * barrierMiniLength) + (barrierLength / barrierMiniLength + barrierMidRowSpacing), barrierY + (1 * barrierHeight), barrierMiniLength, barrierHeight));
		}
		
		// Forth Barrier
		for (int i = 0; i < (int) (barrierLength / barrierMiniLength); i++)
		{
			bar.add(new Barrier (barrierX4 + (i * barrierMiniLength), barrierY - (0 * barrierHeight), barrierMiniLength, barrierHeight));
			bar.add(new Barrier (barrierX4 + (i * barrierMiniLength), barrierY - (1 * barrierHeight), barrierMiniLength, barrierHeight));
		}
		
		for (int i = 0; i < barrierMidRowNum; i++)
		{
			bar.add(new Barrier (barrierX4 + (i * barrierMiniLength), barrierY + (1 * barrierHeight), barrierMiniLength, barrierHeight));
			bar.add(new Barrier (barrierX4 + (i * barrierMiniLength) + (barrierLength / barrierMiniLength + barrierMidRowSpacing), barrierY + (1 * barrierHeight), barrierMiniLength, barrierHeight));
		}
		
		// Show the window with the Spaceship in its initial position.
		setSize(MAX_WIDTH, MAX_HEIGHT);
		setVisible(true);

		// Sets up a timer but does not start it.  Once started, the timer will go
		// off every DELAY_IN_MILLISEC milliseconds.  When it goes off all it does
		// is call this.actionPerformed().  It then goes back to sleep for another
		// DELAY_IN_MILLISEC.  
		Timer clock = new Timer(DELAY_IN_MILLISEC, this);			
													
		// Now actually start the timer.
		clock.start();								
	}
	
	/**
	 * keyPressed() is called when hitting spacebar, the right arrow, the left arrow, or enter
	 * If so, it acts appropriately to either shoot, move right, move left, or start the game
	 * 
	 * @param e		Contains info about the event that caused this method to be called
	 */
	public void keyPressed(KeyEvent e)
	{
		// Invoked when a key has been pressed.
		int keyCode = e.getKeyCode();
			
		// Delay the shooting
		if (System.currentTimeMillis() - lastPressProcessed > 500 && start == true) 
		{
			if (keyCode == KeyEvent.VK_SPACE)
			{
				// Shoot a bullet
				b.add(new Bullet (d.getTipX(), d.getTipY(), d.getTipLength()));
			}
			lastPressProcessed = System.currentTimeMillis();
		}         
		
		if (keyCode == KeyEvent.VK_RIGHT && start == true)
		{
			// Move defender to the right
			d.moveRight();
		}
		else if (keyCode == KeyEvent.VK_LEFT && start == true)
		{
			// Move defender to the left
			d.moveLeft();
		}
		else if (keyCode == KeyEvent.VK_ENTER)
		{
			// Start the game
			start = true;
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		// Invoked when a key has been released.
	}
	
	public void keyTyped(KeyEvent e)
	{
		// Invoked when a key has been typed.
	}
	
	/**
	 * actionPerformed() is called automatically by the timer every time the requested 
	 * delay has elapsed.  It will keep being called until the clock is stopped or the
	 * program ends.  All actions that we want to happen then should be performed here.
	 * Any class that implements ActionListener MUST have this method.
	 * 
	 * @param e		Contains info about the event that caused this method to be called
	 */
	public void actionPerformed(ActionEvent e)		
	{		
		if (start == true && finalWinScreen == false && finalLoseScreen == false)
		{
			Random r = new Random();

			d.wrap(0, MAX_WIDTH, d.getLength()/2);
			
			// Enemies Bouncing
			for (int i = 0; i < enemy.size(); i++)
			{
				enemy.get(i).bounceHorizontal(0, MAX_WIDTH);
			}
		
			// Move the bullet
			for (int i = 0; i < b.size(); i++)
			{
				b.get(i).move();
			}
			
			// Move the enemy
			for (int i = 0; i < enemy.size(); i++)
			{
				enemy.get(i).move();
			}
		
			// Move the enemy bullet
			for (int i = 0; i < eb.size(); i++)
			{
				eb.get(i).move();
			}
		
			// Figure out which are the bottom Enemy's
			Enemy[] bottomEnemy = new Enemy[numPerRow];
			for (int i = 0; i < enemy.size(); i++) 
			{
				int cordX = enemy.get(i).getCordX();
				int cordY = enemy.get(i).getCordY();
		
				if (bottomEnemy[cordX] == null || cordY > bottomEnemy[cordX].getCordY()) 
				{
					bottomEnemy[cordX] = enemy.get(i);
				}
			}
			
			// Randomly shoot the bottom Enemy's shot
			for (int i = 0; i < bottomEnemy.length; ++i) 
			{
				Enemy currentEnemy = bottomEnemy[i];
				if (currentEnemy != null && r.nextInt(1000) + 1 <= 5)  // Rate of fire
				{	
					eb.add(new EnemyBullet(currentEnemy.getBottomX(), currentEnemy.getBottomY()));	
				}
			}
		
			// Bullet and Barrier Collision
			ArrayList<Bullet> removeListBullet = new ArrayList<>();
			for (int i = 0; i < b.size(); i++)
			{
				for (int j = 0; j < bar.size(); j++)
				{
					if (bar.get(j).detectHitWithDefender(b.get(i).getTopX(), b.get(i).getTopY()) == true)
					{
						removeListBullet.add(b.get(i));
						bar.remove(j);
						
						break;
					}
				}
			}
		
			for (int i = 0; i < removeListBullet.size(); ++i) 
			{
				b.remove(removeListBullet.get(i));
			}
					
			// Bullet and Enemy Collision
			removeListBullet.clear();
			for (int i = 0; i < b.size(); i++)
			{
				for (int j = 0; j < enemy.size(); j++)
				{
					if (enemy.get(j).detectHitWithBullet(b.get(i).getTopX(), b.get(i).getTopY()) == true)
					{
						removeListBullet.add(b.get(i));
						enemy.remove(j);
						score += 1;
						
						break;
					}
				}
			}
			for (int i = 0; i < removeListBullet.size(); ++i) 
			{
				b.remove(removeListBullet.get(i));
			}
			
			// Enemy Bullet and Barrier Collision
			ArrayList<EnemyBullet> removeListEnemyBullet = new ArrayList<>();
			for (int i = 0; i < eb.size(); i++)
			{
				for (int j = 0; j < bar.size(); j++)
				{
					if (bar.get(j).detectHitWithDefender(eb.get(i).getBottomX(), eb.get(i).getBottomY()) == true)
					{
						removeListEnemyBullet.add(eb.get(i));
						bar.remove(j);
		
						break;
					}
				}
			}
			for (int i = 0; i < removeListEnemyBullet.size(); ++i) 
			{
				eb.remove(removeListEnemyBullet.get(i));
			}
			
			// Enemy Bullet and Defender
			removeListEnemyBullet.clear();
			for (int i = 0; i < eb.size(); i++)
			{
				if (eb.get(i).hitDefender(d.getTipX(), d.getTipY(), d.getLength(), d.getHeight()) == true)
				{
					removeListEnemyBullet.add(eb.get(i));
					lives--;
					
					break;
				}
			}
			for (int i = 0; i < removeListEnemyBullet.size(); ++i) 
			{
				eb.remove(removeListEnemyBullet.get(i));
			}
					
		
			// Remove bullets from ArrayList once they leave the screen
			removeListBullet.clear();
			for (int i = 0; i < b.size(); i++)
			{
				if (b.get(i).getTopY() < TOP_OF_WINDOW)
				{
					removeListBullet.add(b.get(i));
				}
			}
			for (int i = 0; i < removeListBullet.size(); ++i) 
			{
				b.remove(removeListBullet.get(i));
			}
			
			// Remove enemy bullets from ArrayList once they leave the screen
			removeListEnemyBullet.clear();
			for (int i = 0; i < eb.size(); i++)
			{
				if (eb.get(i).getTopY() < TOP_OF_WINDOW)
				{
					removeListEnemyBullet.add(eb.get(i));
				}
			}
			for (int i = 0; i < removeListEnemyBullet.size(); ++i) 
			{
				eb.remove(removeListEnemyBullet.get(i));
			}

			if (lives < 0)
			{
				invadersWin = true;
			}
			
			for (int i = 0; i < enemy.size(); i++)
			{
				if (enemy.get(i).getBottomY() > (d.getTipY() - 50))
				{
					invadersWin = true;
				}
			}
			
			if (enemy.size() == 0)
			{
				win = true;
			}
			// Update the window.
			repaint();
		}
	}
	
	/**
	 * paint 		draw the window
	 * 
	 * @param g		Graphics object to draw on
	 */
	public void paint(Graphics g)
	{
		// Reading the LoseScreen picture
		try {
			loseScreen = ImageIO.read(new File("LoseScreen.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Reading the Enemy picture
		try {
			enemyStartScreen = ImageIO.read(new File("Enemy.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Reading the YouWin picture
		try {
			winScreen = ImageIO.read(new File("YouWin.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (start == false && win == false && invadersWin == false)
		{
			// Clear the window.
			g.setColor(Color.black);
			g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
			
			// Creating the Font
			g.setColor(Color.white);
			
			Font arcade = null;
			try {
				arcade = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File ("ArcadeFont.ttf"))).deriveFont(Font.PLAIN, 35);
			} catch (FontFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			g.setFont(arcade);
			g.drawString("SPACE INVADERS", MAX_WIDTH - 535, MAX_HEIGHT - 550);
			g.drawImage(enemyStartScreen, MAX_WIDTH - 500, MAX_HEIGHT - 500, 100, 100, null);
			g.drawImage(enemyStartScreen, MAX_WIDTH - 200, MAX_HEIGHT - 500, 100, 100, null);
			g.drawString("PRESS ENTER", MAX_WIDTH - 500, MAX_HEIGHT - 300);
			g.drawString("TO START", MAX_WIDTH - 445, MAX_HEIGHT - 250);
		}
		else if (win == true)
		{
			if (!finalWinScreen)
			{
				// Clear the window.
				g.setColor(Color.white);
				g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
							
				g.drawImage(winScreen, MAX_WIDTH - 550, MAX_HEIGHT - 650, 500, 500, null);
				
				// Creating the Font
				g.setColor(Color.black);
				
				Font arcade = null;
				try {
					arcade = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File ("ArcadeFont.ttf"))).deriveFont(Font.PLAIN, 30);
				} catch (FontFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				g.setFont(arcade);
				g.drawString("Final Score: " + score, MAX_WIDTH - 525, MAX_HEIGHT - 100);
				
				finalWinScreen = true;
			}
		}
		else if (invadersWin == true)
		{
			if (!finalLoseScreen)
			{
				// Clear the window.
				g.setColor(Color.black);
				g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
							
				g.drawImage(loseScreen, MAX_WIDTH - 575, MAX_HEIGHT - 650, MAX_WIDTH - 50, MAX_HEIGHT - 200, null);
				
				// Creating the Font
				g.setColor(Color.white);
				
				Font arcade = null;
				try {
					arcade = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File ("ArcadeFont.ttf"))).deriveFont(Font.PLAIN, 30);
				} catch (FontFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				g.setFont(arcade);
				g.drawString("Final Score: " + score, MAX_WIDTH - 525, MAX_HEIGHT - 200);
				
				finalLoseScreen = true;
			}
		}
		else if (start == true && win == false && invadersWin == false)
		{
			// Clear the window.
			g.setColor(Color.black);
			g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
			
			// Tell the Defender to draw itself.
			d.draw(g);
			
			// Tell the Barrier to draw itself.
			for (int i = 0; i < bar.size(); i++)
			{
				bar.get(i).draw(g);
			}
			
			// Tell the Enemies to draw itself.
			for (int i = 0; i < enemy.size(); i++)
			{
				enemy.get(i).draw(g);
			}
			
			// Tell the Bullet to draw itself
			for (int i = 0; i < b.size(); i++)
			{
				b.get(i).draw(g);
			}
			
			// Tell the Enemy Bullet to draw itself
			for (int i = 0; i < eb.size(); i++)
			{
				eb.get(i).draw(g);
			}
			
			// Creating the Font
			g.setColor(Color.white);
			
			Font arcade = null;
			try {
				arcade = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File ("ArcadeFont.ttf"))).deriveFont(Font.PLAIN, 18);
			} catch (FontFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			g.setFont(arcade);
			g.drawString("Score: " + score, MAX_WIDTH - 550, 50);
		    g.drawString("Lives Left: " + lives, MAX_WIDTH - 250, 50);
		}
	}
}