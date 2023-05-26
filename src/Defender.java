import java.awt.Color;
import java.awt.Graphics;


/*********************************************************************************
 * Defender class
 * 
 * Stores all of the information about a single Defender including:
 * length, height, move amount, dimensions of smaller bases
 * 
 * It provides methods for the Defender to draw itself, move itself, and to 
 * wrap around the screen
 ***********************************************************************************/

class Defender
{
	// DATA:
	private static final int LENGTH = 60;								// Length of the defender
	private static final int HEIGHT = 40;								// Height of the defender
	private static final int MOVE_AMOUNT = 10;							// Acceleration of the defender
	private static final int TIP_LENGTH = (int) (LENGTH / 8);			// Length of the tip
	private static final int MID_BASE_LENGTH = (int) (LENGTH / 1.3);	// Length of the middle base
	private static final int TOP_BASE_LENGTH = (int) (LENGTH / 3.2);	// Length of the top base
	
	private int x, y;													// Top left of the defender
	private int tipX, tipY, tipHeight;									// Tip of defender info
	private int topMidX, topMidY, topMidHeight;							// Upper base 
	private int bottomMidX, bottomMidY, bottomMidHeight;				// Lower base
	
	// METHODS:
	
	/**
	 * Defender constructor initializes the Defender object
	 * 
	 * @param xIn					x coordinate of top left
	 * @param yIn					y coordinate of top left
	 */
	public Defender (int xIn, int yIn)
	{
		// Nothing to do but save the data in the object's data fields.
		x = xIn - (LENGTH / 2);
		y = yIn; 
		
		// Making the Middle Base
		bottomMidX = (int) (x + LENGTH * 0.13);
		bottomMidY = (int) (y + HEIGHT * 0.38);
		bottomMidHeight = (int) (HEIGHT / 4);
		
		// Making the Top Base
		topMidX = (int) (x + LENGTH * 0.37);
		topMidY = (int) (y + HEIGHT * 0.15);
		topMidHeight = (int) (HEIGHT / 3);
		
		// Making the Tip of the Defender
		tipX = (int) (x + LENGTH * 0.46);
		tipY = (int) (y - HEIGHT * 0.05);
		tipHeight = (int) (HEIGHT / 5);
	}
	
	/**
	 * Move the defender right.
	 */
	public void moveRight()
	{
		x += MOVE_AMOUNT;
	}
	
	/**
	 * Move the defender left.
	 */
	public void moveLeft()
	{
		x -= MOVE_AMOUNT;
	}
	
	/**
	 * Check if the defender should wrap off any of the walls.  It will only
	 * wrap if it was heading toward the wall and went a bit past it. 
	 * 
	 * @param xLow			x coord of left wall
	 * @param xHigh			x coord of right wall
	 * @param halfLength	half the length
	 */
	
	public void wrap(int xLow, int xHigh, int halfLength)
	{
		// Check for an x wrap.  
		if (x + halfLength < xLow)
		{
			x = xHigh - halfLength;
		}
		else if (x + halfLength > xHigh)
		{
			x = xLow - halfLength;
		}
	}
	
	/**
	 * Draw the Defender.
	 * 
	 * @param g			Graphics object in which to draw
	 */

	public void draw(Graphics g)
	{	
		// Update the locations
		bottomMidX = (int) (x + LENGTH * 0.13);
		bottomMidY = (int) (y + HEIGHT * 0.38);
		bottomMidHeight = (int) (HEIGHT / 4);
		
		topMidX = (int) (x + LENGTH * 0.37);
		topMidY = (int) (y + HEIGHT * 0.15);
		topMidHeight = (int) (HEIGHT / 3);
		
		tipX = (int) (x + LENGTH * 0.46);
		tipY = (int) (y - HEIGHT * 0.05);
		tipHeight = (int) (HEIGHT / 5);
		
		g.setColor(Color.GREEN); 
		g.fillRect(x, y + HEIGHT / 2, LENGTH, HEIGHT / 2);
		g.fillRect(bottomMidX, bottomMidY, MID_BASE_LENGTH, bottomMidHeight);
		g.fillRect(topMidX, topMidY, TOP_BASE_LENGTH, topMidHeight);
		g.fillRect(tipX, tipY, TIP_LENGTH, tipHeight);
	}
	
	public int getTipX()
	{
		return tipX;
	}
	
	public int getTipY()
	{
		return tipY;
	}
	
	public int getTipLength()
	{
		return TIP_LENGTH;
	}
	
	public int getLength()
	{
		return LENGTH;
	}
	
	public int getHeight()
	{
		return HEIGHT;
	}
}