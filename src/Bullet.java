import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*********************************************************************************
 * Bullet class
 * 
 * Stores all of the information about a single Bullet including:
 * length, height, and speed
 * 
 * It provides methods for the Bullet to draw itself and to move itself
 ***********************************************************************************/

class Bullet
{
	// DATA:
	private static final int BULLET_SPEED = 14;					// Speed of the bullet
	private static final int BULLET_LENGTH = 3;					// Length of the bullet
	private static final int BULLET_HEIGHT = 10;				// Height of the bullet
	File shoot = new File("DefenderShoot.wav");
	
	private int x, y;											// Center of the bullet
	private int defenderTipLength;								// Length of the Defender's tip
	
	// METHODS:
	
	/**
	 * Bullet constructor initializes the Bullet object
	 * @param xIn					x coordinate of top left
	 * @param yIn					y coordinate of top left
	 * @param defenderTipLengthIn	length of defender tip
	 */
	public Bullet (int xIn, int yIn, int defenderTipLengthIn)
	{
		// Nothing to do but save the data in the object's data fields.
		x = xIn;
		y = yIn;
		defenderTipLength = defenderTipLengthIn;
		
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
	 * Move the Bullet
	 */
	public void move()
	{	
		y -= BULLET_SPEED;
	}

	/**
	 * Draw the Bullet.
	 * 
	 * @param g			Graphics object in which to draw
	 */	
	public void draw(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.fillRect((int) ((int) x + (defenderTipLength / 2)) , (int) y, BULLET_LENGTH, BULLET_HEIGHT);
	}
	
	public int getTopX()
	{
		return (int) (x + (defenderTipLength / 2));
	}
	
	public int getTopY()
	{
		return (int) y;
	}
}