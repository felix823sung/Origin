/**
 * class to keep track of the player's celia
 * @author Jonathan Sun and Felix Sung
 *
 */
public class Celia extends Trait
{
	private double speedMultiplyer;

	/**
	 * constructs the celia
	 * @param angleToDisplace the angle to rotate the celia
	 */
	public Celia(int angleToDisplace)
	{
		super(angleToDisplace);
		this.setImage(new RotatingImage("Celia.png"));
		speedMultiplyer = 2;
	}

	/**
	 * gets the speed multiplier of the Celia
	 * @return the celia's speed multiplyer
	 */
	public double getMultiplyer()
	{
		return speedMultiplyer;
	}
}
