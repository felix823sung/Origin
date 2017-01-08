/**
 * The Mouth class, a subclass of the Trait class
 * @author Felix Sung and Jonathan Sun
 *
 */
public class Mouth extends Trait
{
	// Initializes key variables, such as mouth damage and mouth properties
	private boolean canEatMeat;
	private boolean canEatPlants;
	private int damage;

	/**
	 * The constructor for the Mouth object
	 * @param damage the damage that the Mouth object will do
	 */
	public Mouth(int damage,int angleToDisplace)
	{
		// Invokes the super constructor of the Traits class
		super(angleToDisplace);
		// Instantiates key variables;
		this.damage = damage;
		canEatMeat = false;
		canEatPlants = false;
	}

	/**
	 * A method to set whether the mouth can eat meat
	 * @param status true/false if the mouth can eat meat
	 */
	public void setCanEatMeat(boolean status)
	{
		canEatMeat = status;
	}

	/**
	 * A method to set whether the mouth can eat plants
	 * @param status true/false if the mouth can eat plants
	 */
	public void setCanEatPlants(boolean status)
	{
		canEatPlants = status;
	}

	/**
	 * A method that determines whether the mouth can eat meat
	 * @return true/false the value of the canEatMeat variable
	 */
	public boolean getCanEatMeat()
	{
		return canEatMeat;
	}

	/**
	 * A method that determines whether the mouth can eat plants
	 * @return true/false the value of the canEatPlants variable
	 */
	public boolean getCanEatPlants()
	{
		return canEatPlants;
	}

	/**
	 * A method that determines the amount of damage that the mouth deals;
	 * @return
	 */
	public int getDamage()
	{
		return damage;
	}

	/**
	 * A method that allows the damage of the mouth to be set to another value
	 * @param damage the new amount of damage the mouth will do
	 */
	public void setDamage(int damage)
	{
		this.damage = damage;
	}

}
