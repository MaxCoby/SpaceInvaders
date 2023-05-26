import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*********************************************************************************
 * Enemy class
 * 
 * Stores all of the information about a single Enemy including:
 * length, height, move amounts
 * 
 * It provides methods for the Defender to draw itself, detect a hit with the 
 * defender's bullet, bounce off the edges, and to move itself
 ***********************************************************************************/

class Enemy
{
	// DATA:
	private static final int ENEMY_LENGTH = 50;					// Length of the Enemy
	private static final int ENEMY_HEIGHT = 50;					// Height of the Enemy	
	private static final int MOVE_AMOUNT_VERTICAL = 10;			// Vertical movement
	private static int moveAmountHor = 1;						// Horizontal movement
	private static int moveDown = 0;							// Vertical movement
	File hit = new File("EnemyHit.wav");
	
	private int x, y;											// Top Left of the Enemy
	private int xCord, yCord;									// x and y coordinates of the Enemy
	private boolean defenderHitEnemy = false;					// Checks if bullet hits
	private BufferedImage enemy;
	
	// METHODS:
	
	/**
	 * Enemy constructor initializes the Enemy object
	 * 
	 * @param xIn				x coordinate of top left
	 * @param yIn				y coordinate of top left
	 * @param imageName			name of the image to be implemented
	 * @param xCordIn			number of the row
	 * @param yCordIn			number of the column
	 * @throws IOException 
	 */
	public Enemy (int xIn, int yIn, String imageName, int xCordIn, int yCordIn) throws IOException 
	{
		// Nothing to do but save the data in the object's data fields.
		x = xIn;
		y = yIn;
		enemy = ImageIO.read(new File(imageName));
		xCord = xCordIn;
		yCord = yCordIn;
	}
	
	/**
	 * Check if the Enemy should gets hit by the defender's bullet
	 * 
	 * @param bulletX			x coord of the bullet
	 * @param bulletY			y coord of the bullet
	 */
	public boolean detectHitWithBullet(int bulletX, int bulletY)
	{
		if ((bulletX > x && bulletX < (x + ENEMY_LENGTH)) && (bulletY < (y + (ENEMY_HEIGHT / 2) + moveDown)))
		{
			defenderHitEnemy = true;
			
			// Playing Audio Clip
			try{
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(hit));
				clip.start();
				
			} catch (Exception e)
			{
				
			}
		}
		else
		{
			defenderHitEnemy = false;
		}
		return defenderHitEnemy;
	}
	
	/**
	 * Check if the Enemy should bounce off any of the walls.  It will only
	 * bounce if it was heading toward the wall and went a bit past it. 
	 * 
	 * @param xLow			x coord of left wall
	 * @param xHigh			x coord of right wall
	 */
	public void bounceHorizontal(int xLow, int xHigh)
	{
		// Check for an x bounce.  Note that we bounce if the x is too
		// low or too high AND IS HEADING IN THE WRONG DIRECTION.
		if ((x - ENEMY_LENGTH / 4 <= xLow && moveAmountHor < 0) || (x + ENEMY_LENGTH >= xHigh && moveAmountHor > 0))
		{
			moveAmountHor = -moveAmountHor;
			moveDown += MOVE_AMOUNT_VERTICAL;
		}
	}
	
	/**
	 * Move the Enemy
	 */
	public void move()
	{
		x += moveAmountHor;
	}
	
	/**
	 * Draw the Enemy.
	 * 
	 * @param g			Graphics object in which to draw
	 */
	public void draw(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawImage(enemy, x, y + moveDown, ENEMY_LENGTH, ENEMY_HEIGHT, null);
	}

	public int getBottomX()
	{
		return x + (ENEMY_LENGTH / 2);
	}
	
	public int getBottomY()
	{
		return y + (ENEMY_HEIGHT) + moveDown;
	}
	
	public int getCordX()
	{
		return xCord;
	}
	
	public int getCordY()
	{
		return yCord;
	}
}