import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*********************************************************************************
 * EnemyBullet class
 * 
 * Stores all of the information about a single EnemyBullet including:
 * length, height, speed
 * 
 * It provides methods for the EnemyBullet to draw itself, to check for a hit
 * with the defender, and to move itself
 ***********************************************************************************/

class EnemyBullet
{
	// DATA:
	private static final int BULLET_SPEED = 8;					// Speed of the EnemyBullet
	private static final int BULLET_LENGTH = 3;					// Length of the EnemyBullet
	private static final int BULLET_HEIGHT = 10;				// Height of the EnemyBullet
	
	File shoot = new File("EnemyShoot.wav");
	File hitDefender = new File("DefenderGotShot.wav");
	
	private int x, y;											// Center of the EnemyBullet
	
	// METHODS:
	
	/**
	 * EnemyBullet constructor initializes the EnemyBullet object
	 * @param xIn					x coordinate of top left
	 * @param yIn					y coordinate of top left
	 */
	public EnemyBullet (int xIn, int yIn)
	{
		// Nothing to do but save the data in the object's data fields.
		x = xIn;
		y = yIn;
		
		// Playing Audio Clip
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(shoot));
			clip.start();
			
		} catch (Exception e)
		{
			
		}
	}
	
	/**
	 * Check if the EnemyBullet comes in contact with the Defender
	 * 
	 * @param defenderX			x coord of the defender
	 * @param defenderY			y coord of the defender
	 * @param defenderLength	length of the defender
	 * @param defenderHeight	height of the defender
	 */
	public boolean hitDefender(int defenderX, int defenderY, int defenderLength, int defenderHeight)
	{
		boolean checkHit = false;
		if (x >= defenderX - (defenderLength) / 2 && x <= defenderX + defenderLength - (defenderLength) / 2 && y >= defenderY && y <= defenderY + defenderHeight)
		{
			checkHit = true;
			
			// Playing Audio Clip
			try{
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(hitDefender));
				clip.start();
				
			} catch (Exception e)
			{
				
			}
		}
		else
		{
			checkHit = false;
		}
		
		return checkHit;
	}
	
	/**
	 * Move the EnemyBullet
	 */
	public void move()
	{	
		y += BULLET_SPEED;
	}
	
	/**
	 * Draw the EnemyBullet.
	 * 
	 * @param g			Graphics object in which to draw
	 */
	public void draw(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect((int) ((int) x + (BULLET_LENGTH / 2)) , (int) y, BULLET_LENGTH, BULLET_HEIGHT);
	}
	
	public int getBottomX()
	{
		return (int) x + (BULLET_LENGTH / 2);
	}
	
	public int getBottomY()
	{
		return (int) y + BULLET_HEIGHT;
	}
	
	public int getTopY()
	{
		return (int) y;
	}
}