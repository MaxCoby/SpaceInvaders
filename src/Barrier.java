import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*********************************************************************************
 * Barrier class
 * 
 * Stores all of the information about a single Barrier including:
 * length, height
 * 
 * It provides methods for the Defender to draw itself, to detect a hit with the
 * bullet of the defender, and to detect a hit with the bullet of the enemy
 ***********************************************************************************/

class Barrier
{
	// DATA:
	private static final Color COLOR = new Color (255, 0, 0); 	// Color of the Barrier
	
	private int x, y;											// Top Left of the Barrier
	private int barrierLength;									// Length of the Barrier
	private int barrierHeight;									// Height of the Barrier
	private boolean defenderHitBarrier = false;					// Checks if bullet hits
	private boolean enemyHitBarrier = false;					// Checks if enemy bullet hits
	File hit = new File("BarrierHit.wav");
	
	// METHODS:
	
	/**
	 * Barrier constructor initializes the Barrier object
	 * @param xIn				x coordinate of top left
	 * @param yIn				y coordinate of top left
	 * @param barrierLengthIn	length of barrier
	 * @param barrierHeightIn	height of barrier
	 */
	public Barrier (int xIn, int yIn, int barrierLengthIn, int barrierHeightIn)
	{
		// Nothing to do but save the data in the object's data fields.
		x = xIn;
		y = yIn;
		barrierLength = barrierLengthIn;
		barrierHeight = barrierHeightIn;
	}
	
	/**
	 * Check if the Barrier comes in contact with the Defender's bullets
	 * 
	 * @param bulletX			x coord of the bullet
	 * @param bulletY			y coord of the bullet
	 */
	public boolean detectHitWithDefender(int bulletX, int bulletY)
	{
		if ((bulletX >= x && bulletX <= (x + barrierLength)) && (bulletY >= y) && (bulletY <= (y + barrierHeight)))
		{
			defenderHitBarrier = true;
			
			// Plays Audio Clip
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
			defenderHitBarrier = false;
		}
		return defenderHitBarrier;
	}
	
	/**
	 * Check if the Barrier comes in contact with the Enemy's bullets
	 * 
	 * @param bulletX			x coord of the bullet
	 * @param bulletY			y coord of the bullet
	 */
	public boolean detectHitWithEnemy(int bulletX, int bulletY)
	{
		if ((bulletX > x && bulletX < (x + barrierLength)) && (bulletY >= y))
		{
			enemyHitBarrier = true;
			
			// Plays Audio Clip
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
			enemyHitBarrier = false;
		}
		return enemyHitBarrier;
	}
	
	/**
	 * Draw the Barrier.
	 * 
	 * @param g			Graphics object in which to draw
	 */	public void draw(Graphics g)
	{
		g.setColor(COLOR);
		g.fillRect(x, y, barrierLength, barrierHeight);
	}

}