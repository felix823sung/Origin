import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * The Carnivore class of organism, extending the AIOrganism superclass.
 * Carnivores only eat other microbes
 * @author Felix and Jonathan Sun
 *
 */
public class Carnivore extends AIOrganism
{
	// Initializes variables, important ones being the arrays that keep track of
	// the organism's edible food
	private Omnivore[] oList;
	private Herbivore[] hList;
	private Meat[] mList;
	private int chasingAI, chasingType;
	private Point point;
	private Timer timer;
	private int time;
	private Player player;

	/**
	 * The constructor for the Carnivore organism
	 * @param player the player that the user is controlling
	 * @param oList the array of Omnivores that the Carnivore organism can eat
	 * @param hList the array of Herbivores that the Carnivore organism can eat
	 * @param mList the array of Meat objects that the Carnivore organism can
	 *            eat
	 */
	public Carnivore(Player player, Omnivore[] oList, Herbivore[] hList,
			Meat[] mList)
	{
		// Calls the super-constructor, instantiates certain variables, and
		// starts the timer that will search for and chase other microbes
		super(player);
		this.oList = oList;
		this.hList = hList;
		this.mList = mList;
		this.player = player;
		time = 0;
		timer = new Timer(10, new TimeEventHandler());
		timer.setInitialDelay(1000);
		timer.start();
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
			// Searches for an organism to attack if there is no current target
			if (point == null)
			{
				search();
			}
			else
			{
				// Checks if the target has gotten too far away to viably chase
				// it
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
					// If chasing a herbivore
					if (chasingType == 1)
					{
						point = hList[chasingAI].getLocation();
						chase();
					}
					// If chasing an omnivore
					else if (chasingType == 2)
					{
						point = oList[chasingAI].getLocation();
						chase();
					}
					// If going to a Meat object
					else if (chasingType == 0)
					{
						if (mList[chasingAI] != null)
						{
							point = mList[chasingAI].getLocation();
							chase();
						}
						else
						{
							point = null;
							setBehaviour(0);
						}
					}
					// If chasing the player
					else
					{
						// Checks if the player is still alive
						if (!player.isVisible())
						{
							point = null;
							setBehaviour(0);
						}
						else
						{
							point = new Point((int) player.getPlayerPosX(),
									(int) player.getPlayerPosY());
							chase();
						}
					}
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
	 * Checks whether the organism has reached its target
	 */
	public void check()
	{
		// Runs if there is a point to follow
		if (point != null)
		{
			// If chasing a herbivore
			if (chasingType == 1)
			{
				// If the two rectangles (from this organism and the one it's
				// chasing) intersect, the target takes 150 damage. This
				// organism's behaviour and points are reset
				if (hList[chasingAI].getBounds().intersects(getBounds()))
				{
					hList[chasingAI].takeDamage(150);
					point = null;
					setBehaviour(0);
				}
			}
			// If chasing an omnivore
			else if (chasingType == 2)
			{
				// Follows a similar process to chasing a herbivore
				if (oList[chasingAI].getBounds().intersects(getBounds()))
				{
					oList[chasingAI].takeDamage(150);
					point = null;
					setBehaviour(0);
				}
			}
			// If going to a meat object
			else if (chasingType == 0)
			{
				// Calculates the distance from the Meat object to the
				// center
				// of this organism
				int dx = point.x - getLocation().x - 75;
				int dy = point.y - getLocation().y - 75;
				int distance = (int) Math.sqrt(dx * dx + dy * dy);
				// Should the Meat be within 50 pixels of the center
				// (basically within the visible image), it is consumed.
				// Organism's behaviour and points are reset
				if (distance < 50)
				{
					mList[chasingAI] = null;
					point = null;
					setBehaviour(0);
				}
			}
			// If chasing the Player
			else
			{
				// Checks if the player is still alive
				if (!player.isVisible())
				{
					point = null;
					setBehaviour(0);
				}
				else
				{
					// Calculates the distance from the Player to the
					// center
					// of this organism
					int dx = point.x - getLocation().x - 75;
					int dy = point.y - getLocation().y - 75;
					int distance = (int) Math.sqrt(dx * dx + dy * dy);
					// Should the Player be within 50 pixels of the center
					// (basically within the visible image), damages the player.
					// Organism's behaviour and points are reset
					if (distance < 100)
					{
						player.takeDamage(8);
						point = null;
						setBehaviour(0);
					}
				}
			}
		}
	}

	/**
	 * The method that allows the organism to search its vicinity for a target.
	 * Priority of targets are meat>herbivore>player>omnivore
	 */
	public void search()
	{
		int dx, dy, distance;
		// Goes through the array of Meat
		for (int mIndex = 0; mIndex < mList.length; mIndex++)
		{
			if (mList[mIndex] != null)
			{
				// Determines the distance from each Meat to this
				// organism's location
				dx = mList[mIndex].getLocation().x - getLocation().x;
				dy = mList[mIndex].getLocation().y - getLocation().y;
				distance = (int) Math.sqrt(dx * dx + dy * dy);
				// If it is within 500 pixels, set it as a target and begin
				// chasing it. Exits the method if a target has been found
				if (distance < 500)
				{
					setBehaviour(2);
					chasingAI = mIndex;
					chasingType = 0;
					point = mList[chasingAI].getLocation();
					chase();
					return;
				}
			}

		}
		// Goes through the array of herbivores,
		for (int hIndex = 0; hIndex < hList.length; hIndex++)
		{
			// Determines the distance from each herbivore to this organism's
			// location
			dx = hList[hIndex].getLocation().x - getLocation().x;
			dy = hList[hIndex].getLocation().y - getLocation().y;
			distance = (int) Math.sqrt(dx * dx + dy * dy);
			// If it is within 500 pixels, set it as a target and begin chasing
			// it. Exits the method if a target has been found
			if (distance < 500)
			{
				setBehaviour(2);
				chasingAI = hIndex;
				chasingType = 1;
				point = hList[chasingAI].getLocation();
				chase();
				return;
			}

		}
		// Checks if the player is still alive
		if (player.isVisible())
		{
			// Determines the distance from the player to this organism's
			// location
			dx = (int) (player.getPlayerPosX() - getLocation().x);
			dy = (int) (player.getPlayerPosY() - getLocation().y);
			distance = (int) Math.sqrt(dx * dx + dy * dy);
			// If the player is within 500 pixels, set it as a target and begin
			// chasing it. Exits the method if a target has been found
			if (distance < 500)
			{
				setBehaviour(2);
				chasingType = 5;
				point = new Point((int) player.getPlayerPosX(),
						(int) player.getPlayerPosY());
				chase();
				return;
			}
		}

		// Follows a similar process to search through the array of omnivores
		for (int oIndex = 0; oIndex < oList.length; oIndex++)
		{
			dx = oList[oIndex].getLocation().x - getLocation().x;
			dy = oList[oIndex].getLocation().y - getLocation().y;
			distance = (int) Math.sqrt(dx * dx + dy * dy);
			if (distance < 500)
			{
				setBehaviour(2);
				chasingAI = oIndex;
				chasingType = 2;
				point = oList[chasingAI].getLocation();
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
				double x = difX / 10.0;
				getVelocity().setLocation(difX / x, difY / x);
			}
			else
			{
				double x = difX / -10.0;
				getVelocity().setLocation(difX / x, difY / x);
			}
		}
		// Follows similar process as above
		else
		{

			if (difY > 0)
			{
				double x = difY / 10.0;
				getVelocity().setLocation(difX / x, difY / x);
			}
			else
			{
				double x = difY / -10.0;
				getVelocity().setLocation(difX / x, difY / x);
			}
		}

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

}
