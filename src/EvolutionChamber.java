import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * Manages the evolutions of the main player
 * @author Jonathan Sun and Felix Sung
 *
 */
public class EvolutionChamber
{
	// Image for the "evolution chamber" overlay
	private Image evolutionChamberImage;

	// Object that will be used to reference the main player
	private Player mainPlayer;

	// Rectangles to track the locations of the various buttons
	private Rectangle proboscisButton;
	private Rectangle jawButton;
	private Rectangle filterMouthButton;
	private Rectangle celiaButton;

	// Variables and objects regarding the mouse's current status
	boolean mouseHovering;
	private Point mousePos;

	// Colors that will be used in the drawing process
	Color white;
	Color black;

	/**
	 * Constructs a new Evolution Chamber object
	 * @param mainPlayer the player that the evolution chamber will evolve
	 */
	public EvolutionChamber(Player mainPlayer)
	{
		// Initializes the colors
		white = new Color(255, 255, 255);
		black = new Color(0, 0, 0);

		mouseHovering = false;
		mousePos = new Point(0, 0);

		// Initializes the buttons
		proboscisButton = new Rectangle(64, 194, 95, 95);
		jawButton = new Rectangle(64, 312, 95, 95);
		filterMouthButton = new Rectangle(64, 429, 95, 95);
		celiaButton = new Rectangle(64, 545, 95, 95);

		// Sets the evolution chamber overlay image
		evolutionChamberImage = new ImageIcon("EvolutionChamber.png")
				.getImage();
		this.mainPlayer = mainPlayer;
	}

	/**
	 * Draws the evolutionChamber overlay
	 * @param g the graphics object to draw with
	 * @param screenWidth the screen's width
	 * @param screenHeight the screen's height
	 */
	public void drawPit(Graphics g, int screenWidth, int screenHeight)
	{
		// sets the color to blue
		g.setColor(new Color(47, 141, 255));
		// draws a blue background and image overlay
		g.fillRect(0, 0, screenWidth, screenHeight);
		g.drawImage(evolutionChamberImage, 0, 0, null);
		// draws information bubbles if the player is hovering over a button
		if (mouseHovering)
		{
			String text = "";
			if (proboscisButton.contains(mousePos))
			{
				colorOverButton(g, proboscisButton);
				text = "Proboscis: Omnivorous mouth. Costs 100 Food";
			}
			else if (jawButton.contains(mousePos))
			{
				colorOverButton(g, jawButton);
				text = "Jaw: Carnivorous mouth. Costs 50 Food";
			}
			else if (filterMouthButton.contains(mousePos))
			{
				colorOverButton(g, filterMouthButton);
				text = "FilterMouth: Herbivorous mouth. Costs 10 Food";
			}
			else if (celiaButton.contains(mousePos))
			{
				colorOverButton(g, celiaButton);
				text = "Celia: Speed Boost. Costs 150 Food";
			}
			g.setColor(white);
			g.fillRect(mousePos.x, mousePos.y - 20, text.length() * 10, 30);
			g.setColor(black);
			g.drawString(text, mousePos.x,
					mousePos.y);
		}
		mainPlayer.drawPlayerImage(g, screenWidth / 2
				- mainPlayer.getPlayerImage().getWidth(null) / 2, screenHeight
				/ 2 - mainPlayer.getPlayerImage().getHeight(null) / 2);

	}

	/**
	 * method to change the color of the buttons when hovered over
	 * @param g the graphics object to draw with
	 * @param rect the rectangle that tracks the button's coordinates
	 */
	public void colorOverButton(Graphics g, Rectangle rect)
	{
		g.setColor(new Color(0, 0, 0, 50));
		g.fillRect(rect.x, rect.y, (int) rect.getWidth(),
				(int) rect.getHeight());
	}

	/**
	 * listens for when the mouse is clicked
	 * @param mousePoint the current mouse position
	 */
	public void mouseListenerExtension(Point mousePoint)
	{
		this.mousePos = mousePoint;

		// Creates new traits for the player when they choose to spend their
		// accumulated food
		if (proboscisButton.contains(mousePoint))
		{
			if (mainPlayer.getFoodEaten() >= 100)
			{
				mainPlayer.getListOfMouths().set(0, new Proboscis(0));
				mainPlayer.setFoodEaten(mainPlayer.getFoodEaten() - 100);
			}

		}
		else if (jawButton.contains(mousePoint))
		{
			if (mainPlayer.getFoodEaten() >= 50)
			{
				mainPlayer.getListOfMouths().set(0, new Jaw(0));
				mainPlayer.setFoodEaten(mainPlayer.getFoodEaten() - 50);
			}
		}
		else if (filterMouthButton.contains(mousePoint))
		{
			if (mainPlayer.getFoodEaten() >= 10)
			{
				mainPlayer.getListOfMouths().set(0, new FilterMouth(0));
				mainPlayer.setFoodEaten(mainPlayer.getFoodEaten() - 10);
			}
		}
		else if (celiaButton.contains(mousePoint))
		{
			if (mainPlayer.getFoodEaten() >= 150)
			{
				Celia c = new Celia(180);
				mainPlayer.getListOfTraits().add(c);
				mainPlayer.setSpeedMultiplyer(c.getMultiplyer());
				mainPlayer.setFoodEaten(mainPlayer.getFoodEaten() - 150);
			}
		}
	}

	/**
	 * listens for when the mouse is moved
	 * @param mousePoint the current mouse position
	 */
	public void mouseMotionListenerExtension(Point mousePoint)
	{
		this.mousePos = mousePoint;
	
		//sets that the mouse is hovering over one of the buttons
		if (proboscisButton.contains(mousePoint)
				|| jawButton.contains(mousePoint)
				|| (filterMouthButton.contains(mousePoint))
				|| celiaButton.contains(mousePoint))
		{
			mouseHovering = true;
		}

		else
		{
			mouseHovering = false;
		}
	}
}
