import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

/**
 * The superclass for all of the AI, outlines some common methods and variables
 * shared between the three types of organisms
 * @author Felix Sung and Jonathan Sun
 *
 */
public class AIOrganism
{
	// Initializes key variables that are shared across all three types of
	// organisms
	private int health = 100;
	private Timer movementTimer;
	private Point location;
	private Point velocity;
	private Random random;
	private int behaviourType;
	private boolean isActive;

	/**
	 * The constructor for an AIOrganism
	 * @param player the player that the user is controlling
	 */
	public AIOrganism(Player player)
	{
		// Instantiates some of the variables, starts the timer that will allow
		// the AI to move independently, and also sets a location for it to
		// spawn at
		isActive = true;
		random = new Random();
		setLocation(player);
		this.velocity = new Point(random.nextInt() % 8,
				random.nextInt() % 8);
		movementTimer = new Timer(70, new Move());
		movementTimer.start();
		behaviourType = 0;
	}

	/**
	 * Sets the organism's behaviour type, 0 being normal, 1 being fleeing, and
	 * 2 being chasing/attacking
	 * @param type
	 */
	public void setBehaviour(int type)
	{
		behaviourType = type;
	}

	/**
	 * Gets the velocity point of the organism, which dictates in which
	 * direction it will move next
	 * @return
	 */
	public Point getVelocity()
	{
		return velocity;
	}

	/**
	 * Sets the spawing location of the organism in the game
	 * @param player
	 */
	public void setLocation(Player player)
	{
		// Gets various parameters to restrict location generation
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) (screenSize.getWidth());
		int screenHeight = (int) (screenSize.getHeight());

		int xLoc;
		int yLoc;

		// Gets the position of the player
		double playerX = player.getPlayerPosX();
		double playerY = player.getPlayerPosY();

		// Define limits on the location generation
		int xUpper = (int) (playerX + screenWidth / 2 + 2000);
		int xLower = (int) (playerX - screenWidth / 2 - 2000);

		int yUpper = (int) (playerY + screenHeight / 2 + 2000);
		int yLower = (int) (playerY - screenHeight / 2 - 2000);

		// Generates a random location until one that fits the criteria is found
		do
		{

			xLoc = (int) (Math.random() * (xUpper - xLower)) + xLower;
			yLoc = (int) (Math.random() * (yUpper - yLower)) + yLower;
		}
		while (xLoc >= playerX - screenWidth / 2
				&& xLoc <= playerX + screenWidth / 2
				&& yLoc <= playerY + screenWidth / 2
				&& yLoc >= playerY - screenWidth / 2);

		// Sets the location of the organism
		location = new Point(xLoc, yLoc);
	}

	/**
	 * A private Timer event class to handle organism movement
	 * @author Felix Sung and Jonathan Sun
	 *
	 */
	private class Move implements ActionListener
	{

		/**
		 * The ActionPerformed method, which runs every 110 milliseconds,
		 * causing the organism to swim
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			swim();
		}

	}

	/**
	 * Stops the movementTimer of the organism, for when it has been eaten and
	 * should be removed
	 */
	public void stop()
	{
		movementTimer.stop();
	}

	/**
	 * Checks whether the organism is still active, or if it has no health and
	 * has been deactivated
	 * @return true/false based on the isActive variable;
	 */
	public boolean isActive()
	{
		return isActive;
	}

	/**
	 * Gets the location of the organism in the game, also the upper left point
	 * of the images for the organism
	 * @return the location of the organism in the form of a Point object
	 */
	public Point getLocation()
	{
		return location;
	}

	/**
	 * The swim method, which, based on behaviour type, will either generate a
	 * new velocity based off a random number, or will just add the current
	 * velocity values to the location values
	 */
	public void swim()
	{

		// If the organism has normal behaviour, generates new values for
		// velocity within certain limits
		if (behaviourType == 0)
		{
			if (random.nextInt() % 7 <= 1)
			{
				velocity.x += random.nextInt() % 4;
				velocity.x = Math.min(velocity.x, 8);
				velocity.x = Math.max(velocity.x, -8);
				velocity.y += random.nextInt() % 4;
				velocity.y = Math.min(velocity.y, 8);
				velocity.y = Math.max(velocity.y, -8);
			}
		}
		// If the organism has fleeing behaviour, generates new values for
		// velocity with looser limits, in essence doubling the speed
		else if (behaviourType == 1)
		{
			if (random.nextInt() % 7 <= 1)
			{
				velocity.x += random.nextInt() % 8;
				velocity.x = Math.min(velocity.x, 16);
				velocity.x = Math.max(velocity.x, -16);
				velocity.y += random.nextInt() % 8;
				velocity.y = Math.min(velocity.y, 16);
				velocity.y = Math.max(velocity.y, -16);
			}
		}
		// Adds the x and y values of the velocity to location's x and y values
		location.x += velocity.x;
		location.y += velocity.y;
	}

	/**
	 * A method that damages the organism
	 * @param damage the amount of damage to take
	 */
	public void takeDamage(int damage)
	{
		// Checks if the amount of damage exceeds the current health
		if (health > damage)
		{
			health -= damage;
		}
		else
		{
			// When health reaches 0, the organism is deactivated
			health = 0;
			isActive = false;
		}
	}

	/**
	 * A method that heals the organism by an amount
	 * @param health the amount of health to be recovered
	 */
	public void heal(int health)
	{
		this.health += health;
	}
}
