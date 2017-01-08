/**
 * The FilterMouth class, a subclass of the Mouth class
 * @author Felix Sung and Jonathan Sun
 *
 */
public class FilterMouth extends Mouth
{

	/**
	 * The constructor for the FilterMouth object
	 */
	public FilterMouth(int angleToDisplace)
	{
		// Invokes the super constructor of the Mouth object. Since it is a
		// filter mouth, damage is set to 0
		super(0,angleToDisplace);
		// Sets up the image for this mouth and also the properties of this type
		// of mouth
		this.setImage(new RotatingImage("FilterMouth.png"));
		this.setCanEatPlants(true);
	}

}
