import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 * class to track the player
 * @author Jonathan Sun and Felix Sung
 *
 */
public class Player
{
	private int foodEaten;
	private int typeOfOrganism;
	private int currentHealth;
	private int maxHealth = 100;;
	private int maxFood;
	private int angle;

	private double speedMultiplyer;

	private ArrayList<Mouth> listOfMouths;
	private ArrayList<Trait> listOfTraits;

	double playerPosX;
	double playerPosY;

	private boolean isVisible;

	private RotatingImage microbeImage;

	/**
	 * constructs a player object
	 * @param typeOfOrganism the kind that the organism is
	 */
	Player(int typeOfOrganism)
	{
		// Initializes the player image
		microbeImage = new RotatingImage("Microbe.png");
		// Initialize the type of organism
		this.typeOfOrganism = typeOfOrganism;
		// Initializes the player position as well as the health and food
		// information
		playerPosX = 0;
		playerPosY = 0;
		foodEaten = 300;
		maxFood = 500;
		currentHealth = maxHealth;
		angle = 0;
		speedMultiplyer = 1.3;
		isVisible = true;

		// Creates a mouth for the plaeyr depending on what type of organism it
		// is
		listOfMouths = new ArrayList<Mouth>();
		if (typeOfOrganism == 0)
		{
			this.listOfMouths.add(new FilterMouth(0));
		}
		else if (typeOfOrganism == 1)
		{
			this.listOfMouths.add(new Jaw(0));
		}
		else
		{
			this.listOfMouths.add(new Proboscis(0));
		}
		listOfTraits = new ArrayList<Trait>();
	}

	/**
	 * adds a given food value to the player's "stomach"
	 * @param nutrientValue the amount of food to add
	 */
	public void eat(int nutrientValue)
	{
		if (foodEaten < maxFood)
		{
			foodEaten = Math.min(foodEaten + nutrientValue, maxFood);
		}
		if (currentHealth < maxHealth)
		{
			currentHealth += 1;
		}
	}

	public int getPlayerType()
	{
		return typeOfOrganism;
	}

	public double getPlayerPosX()
	{
		return playerPosX;
	}

	public double getPlayerPosY()
	{
		return playerPosY;
	}

	public void addToPlayerPosX(double xAdd)
	{
		playerPosX += xAdd;
	}

	public void addToPlayerPosY(double yAdd)
	{
		playerPosY += yAdd;
	}

	public int getFoodEaten()
	{
		return foodEaten;
	}

	public void setFoodEaten(int foodValue)
	{
		foodEaten = foodValue;
	}

	public int getCurrentHealth()
	{
		return currentHealth;
	}

	public void takeDamage(int damage)
	{
		if (currentHealth > damage)
		{
			currentHealth -= damage;
		}
		else
		{
			currentHealth = 0;
		}
	}

	public void setCurrentHealth(int health)
	{
		if (health <= maxHealth)
		{
			currentHealth = health;
		}
	}

	public int getMaxHealth()
	{
		return maxHealth;
	}

	public void setMaxHealth(int newMaxHealth)
	{
		maxHealth = newMaxHealth;
	}

	public int getFoodGoal()
	{
		return maxFood;
	}

	public int getAngle()
	{
		return angle;
	}

	public void setAngle(int angleToSet)
	{
		angle = angleToSet;
	}

	public ArrayList<Mouth> getListOfMouths()
	{
		return listOfMouths;
	}

	public ArrayList<Trait> getListOfTraits()
	{
		return listOfTraits;
	}

	public double getSpeedMultiplyer()
	{
		return speedMultiplyer;
	}

	public void setSpeedMultiplyer(double multiplyer)
	{
		speedMultiplyer = multiplyer;
	}

	public Image getPlayerImage()
	{
		return microbeImage.getImage();
	}

	public void setIsVisible(boolean isVisible)
	{
		this.isVisible = isVisible;
	}

	public boolean isVisible()
	{
		return this.isVisible;
	}

	public void drawPlayerImage(Graphics g, int screenCenterX, int screenCenterY)
	{
		int amountOfMouths = listOfMouths.size();
		for (int i = 0; i < amountOfMouths; i++)
		{
			listOfMouths.get(i).drawTrait(g, this, screenCenterX,
					screenCenterY);
		}
		int amountOfTraits = listOfTraits.size();
		for (int i = 0; i < amountOfTraits; i++)
		{
			listOfTraits.get(i).drawTrait(g, this, screenCenterX,
					screenCenterY);
		}
		microbeImage.draw(g, screenCenterX, screenCenterY, angle + 90);
	}
}
