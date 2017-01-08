/**
 * The Jaw class, a subclass of the Mouth class
 * @author Felix Sung and Jonathan Sun
 * 
 */
public class Jaw extends Mouth
{
	/**
	 * The constructor for the Jaw object
	 * @param damage the amount of damage this mouth will do
	 */
	public Jaw(int angleToDisplace)
	{
		// Invokes the super constructor of the Mouth object. Sets the mouth
		// damage to the received value
		super(25,angleToDisplace);
		// Sets up the image for this mouth and also the properties of this type
		// of mouth
		this.setImage(new RotatingImage("Jaw.png"));
		this.setCanEatMeat(true);
	}
}
