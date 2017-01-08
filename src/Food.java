import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * class for food objects
 * @author Jonathan Sun and Felix Sung
 *
 */
public class Food
{
	// Instantiate the key variables
	private int nutrientValue;
	private Point location;
	private int index;
	private Image foodImage;

	/**
	 * constructs a foodObject
	 * @param unitToSpawnAround the player that the food will spawn around
	 */
	public Food(Player unitToSpawnAround)
	{

	}

	/**
	 * fetches the food's nutrient value
	 * @return the food's nutrient value
	 */
	public int getNutrientValue()
	{
		return nutrientValue;
	}

	/**
	 * sets the food's nutrient value
	 * @param nutrientValue the nutrient value to set
	 */
	public void setNutrientValue(int nutrientValue)
	{
		this.nutrientValue = nutrientValue;
	}

	/**
	 * gets the food's location
	 * @return the location of the food
	 */
	public Point getLocation()
	{
		return location;
	}

	/**
	 * sets the location of the food
	 * @param location the location to set
	 */
	public void setLocation(Point location)
	{
		this.location = location;
	}

	/**
	 * fetches the index of a food
	 * @return the index of the food
	 */
	public int getIndex()
	{
		return index;
	}

	/**
	 * sets the index of the food
	 * @param index the index to set
	 */
	public void setIndex(int index)
	{
		this.index = index;
	}

	/**
	 * fetches the food's image
	 * @return the food's image
	 */
	public Image getImage()
	{
		return foodImage;
	}

	/**
	 * sets the food's image
	 * @param imageOfFood the image to set
	 */
	public void setImage(Image imageOfFood)
	{
		foodImage = imageOfFood;
	}

	/**
	 * draws the food object
	 * @param g the graphics object to use
	 * @param displacementX the horizontal shift to apply
	 * @param displacementY the vertical shift to apply
	 */
	public void drawFood(Graphics g, int displacementX, int displacementY)
	{
		g.drawImage(foodImage,
				location.x + displacementX - foodImage.getWidth(null) / 2,
				location.y + displacementY - foodImage.getHeight(null) / 2,
				null);
	}

}
