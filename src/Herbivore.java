import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * The Herbivore class of organism, extend the AIOrganism superclass. Herbivores
 * can only eat plant matter
 * @author Felix Sung and Jonathan Sun
 *
 */
public class Herbivore extends AIOrganism
{
	// Initializes variables, important ones being the arrays that keep track of
	// the organism's edible food
	private PlantMatter[] pList;
	private Timer timer;
	private Point point;
	private int chasingPlant;
	private int time;
	private Player player;

	/**
	 * The constructor for the Herbivore organism
	 * @param player the player that the user is controlling
	 * @param pList the array of PlantMatter that the organism can eat
	 */
	public Herbivore(Player player, PlantMatter[] pList)
	{
		// Calls the super-constructor, instantiates certain variables, and
		// starts the timer that will search for and go to PlantMatter objects
		super(player);
		this.player = player;
		time = 0;
		this.pList = pList;
		timer = new Timer(10, new TimeEventHandler());
		timer.setInitialDelay(1000);
		timer.start();
	}

	/**
	 * A method that stops the timer of this class, invokes the stop() method of
	 * the AIOrganism superclass
	 */
	public void stop()
	{
		super.stop();
		timer.stop();
	}

	/**
	 * A private Timer event class to handle searching for and chasing other
	 * microbes
	 * @author Felix Sung and Jonathan Sun
	 *
	 */
	private class TimeEventHandler implements ActionListener
	{
		/**
		 * The ActionPerformed method, which runs every 10 milliseconds. A int
		 * variable increases by 1 each time this method is called, and is used
		 * to make certain methods run at certain intervals
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			time++;
			// Searches for a PlantMatter object if there is none targeted
			if (point == null)
			{
				search();
			}
			else
			{
				// Checks if the PlantMatter object has already been eaten, and
				// has respawned too far to viably follow
				int dx = point.x - getLocation().x;
				int dy = point.y - getLocation().y;
				int distance = (int) Math.sqrt(dx * dx + dy * dy);
				if (distance > 750)
				{
					point = null;
					setBehaviour(0);
				}
			}
			// Every 500 millisecond interval, updates the target point with the
			// current point of the target
			if (time % 5 == 0)
			{
				if (point != null)
				{
					point = pList[chasingPlant].getLocation();
					chase();
				}
			}
			// Every 1000 milliseconds, checks if the organism has reached its
			// target
			if (time % 10 == 0)
			{
				check();
			}
		}

	}

	/**
	 * Checks whether the organism has reached its target
	 */
	public void check()
	{
		// Runs if there is a point to follow
		if (point != null)
		{
			// Calculates the distance from the PlantMatter object to the center
			// of this organism
			int dx = point.x - getLocation().x - 75;
			int dy = point.y - getLocation().y - 75;
			int distance = (int) Math.sqrt(dx * dx + dy * dy);
			// Should the PlantMatter be within 50 pixels of the center
			// (basically within the visible image), it is consumed. Organism's
			// behaviour and points are reset
			if (distance < 50)
			{
				pList[chasingPlant] = new PlantMatter(player);
				point = null;
				setBehaviour(0);
			}
		}
	}

	/**
	 * A method that returns a rectangle that approximately fits around the
	 * visible image
	 * @return a Rectangle object outlining the conditions
	 */
	public Rectangle getBounds()
	{
		return new Rectangle(getLocation().x + 30, getLocation().y - 30,
				90, 90);
	}

	/**
	 * The method that allows the organism to search its vicinity for a target.
	 * Can only search through the PlantMatter array
	 */
	public void search()
	{
		// Goes through the array of PlantMatter
		for (int pIndex = 0; pIndex < pList.length; pIndex++)
		{
			// Determines the distance from each PlantMatter to this organism's
			// location
			int dx = pList[pIndex].getLocation().x - getLocation().x;
			int dy = pList[pIndex].getLocation().y - getLocation().y;
			int distance = (int) Math.sqrt(dx * dx + dy * dy);
			// If it is within 500 pixels, set it as a target and begin chasing
			// it. Exits the method if a target has been found
			if (distance < 500)
			{
				setBehaviour(2);
				chasingPlant = pIndex;
				point = pList[chasingPlant].getLocation();
				chase();
				return;
			}

		}
	}

	/**
	 * The method that changes the value of the organism's velocity so that it
	 * will move towards its target
	 */
	public void chase()
	{
		// Gets the difference between the target's location and this organism's
		// location
		int difX = point.x - getLocation().x - 75;
		int difY = point.y - getLocation().y - 75;
		// Determines the greater magnitude between difX and difY
		if (Math.abs(difX) > Math.abs(difY))
		{
			// Checks both cases, positive and negative, and determines the
			// denominator needed to move the organism at a constant speed
			// towards its target
			if (difX > 0)
			{
				double x = difX / 6.0;
				getVelocity().setLocation(difX / x, difY / x);
			}
			else
			{
				double x = difX / -6.0;
				getVelocity().setLocation(difX / x, difY / x);
			}
		}
		// Follows similar process as above
		else
		{

			if (difY > 0)
			{
				double x = difY / 6.0;
				getVelocity().setLocation(difX / x, difY / x);
			}
			else
			{
				double x = difY / -6.0;
				getVelocity().setLocation(difX / x, difY / x);
			}
		}

	}

}
