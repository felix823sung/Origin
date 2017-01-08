import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * The Meat class, a subclass of the Food class
 * @author Felix Sung and Jonathan Sun
 *
 */
public class Meat extends Food
{

	/**
	 * The constructor for the Meat object
	 * @param unitToSpawnAround the Player object, passed into the super
	 *            constructor
	 * @param point the location at which to create a Meat object
	 */
	public Meat(Player unitToSpawnAround, Point point)
	{
		// Invokes the super constructor of the Food class, passing in the
		// Player object
		super(unitToSpawnAround);
		// Sets up the image for a Meat object, as well as the nutrient value
		// and the location to spawn
		setNutrientValue((int) (Math.random() * 10 + 5));
		setLocation(new Point(point.x + 75, point.y + 75));
		setImage(new ImageIcon("Meat.png").getImage());
	}
}
