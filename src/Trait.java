import java.awt.Graphics;

/**
 * characteristics of the player
 * @author Jonathan Sun and Felix Sung
 *
 */
public class Trait
{
	private int xLocation;
	private int yLocation;
	private RotatingImage traitImage;
	private int angleDisplacement;

	/**
	 * constructs a new trait
	 * @param angleDisplacement the angle that the trait will be displaced
	 *            around the microbe
	 */
	public Trait(int angleDisplacement)
	{
		this.angleDisplacement = angleDisplacement;
		xLocation = 0;
		yLocation = 100;
	}

	/**
	 * gets the X coordinate of the trait
	 * @return the xLocation of the trait
	 */
	public int getXLocation()
	{
		return xLocation;
	}

	/**
	 * gets the Y coordinate of the trait
	 * @return the yCordinate of the trait
	 */
	public int getYLocation()
	{
		return yLocation;
	}

	/**
	 * updates the trait's X coordinate relative to the player's location and
	 * rotation
	 * @param player the player who has the trait
	 */
	public void updatePos(Player player)
	{
		// calculates the x location
		xLocation = (int) ((75.5 * Math.sin(Math.toRadians(player.getAngle()
				+ angleDisplacement))
				+ player.getPlayerPosX()));
		// calculates the x location
		yLocation = (int) ((-75.5
				* Math.cos(Math.toRadians(player.getAngle() + angleDisplacement))
				+ player.getPlayerPosY()));
	}

	/**
	 * fetches the trait image
	 * @return the trait image
	 */
	public RotatingImage getImage()
	{
		return traitImage;
	}

	/**
	 * sets the image of the trait
	 * @param imageOfTrait the image to set as the trait image
	 */
	public void setImage(RotatingImage imageOfTrait)
	{
		traitImage = imageOfTrait;
	}

	/**
	 * draws the trait on the corresponding player
	 * @param g the graphics object to use
	 * @param player the player to draw the trait around
	 * @param x the x location to draw the image at
	 * @param y the y location to draw the image at
	 */
	public void drawTrait(Graphics g, Player player, int x, int y)
	{
		traitImage.draw(g, x, y, player.getAngle() + 90 + angleDisplacement);
	}
}
