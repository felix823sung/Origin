import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * The PlantMatter class, a subclass of the Food class
 * @author Felix Sung and Jonathan Sun
 *
 */
public class PlantMatter extends Food
{
	/**
	 * The constructor of the PlantMatter object
	 * @param unitToSpawnAround the Player object to spawn around
	 */
	public PlantMatter(Player unitToSpawnAround)
	{
		// Invokes the super constructor of the Food class
		super(unitToSpawnAround);

		// Sets the image for the PlantMatter, as well as giving it a nutrient
		// value
		setImage(new ImageIcon("Vegetable.png").getImage());
		setNutrientValue((int) (Math.random() * 5 + 1));

		// Initialize variables
		int xLoc;
		int yLoc;

		// Gets the location of the player
		double playerX = unitToSpawnAround.getPlayerPosX();
		double playerY = unitToSpawnAround.getPlayerPosY();

		// Define the limits on the location generation
		int xUpper = (int) (playerX + 5000);
		int xLower = (int) (playerX - 5000);

		int yUpper = (int) (playerY + 5000);
		int yLower = (int) (playerY - 5000);

		// Generates a random set of coordinates
		xLoc = (int) (Math.random() * (xUpper - xLower)) + xLower;
		yLoc = (int) (Math.random() * (yUpper - yLower)) + yLower;

		// Sets the coordinates of the PlantMatter to the generated coordinates
		setLocation(new Point(xLoc, yLoc));
	}
}
