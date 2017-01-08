import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The DisplayPane class, stores variables associated with drawing and running
 * the game
 * @author Felix Sung and Jonathan Sun
 *
 */
public class DisplayPane extends JPanel
{
	// Initializes key variables, as well as constants to be used in the code
	private static final long serialVersionUID = 1L;

	Map mainMap;

	private Point mousePos;
	private Timer timer;

	private final int TIME_INTERVAL = 20;
	private final int MOVEMENT_DIVIDER = 100;

	public final int SCREEN_WIDTH;
	public final int SCREEN_HEIGHT;

	private int backDisplacementX = 0;
	private int backDisplacementY = 0;

	private double playerDisplacementX = 0;
	private double playerDisplacementY = 0;

	int displacementX;
	int displacementY;

	private int trueScreenCenterX;
	private int trueScreenCenterY;
	private int screenCenterX;
	private int screenCenterY;
	private int healthWhenEnteredPit;

	private boolean evolutionPitOpen;
	private boolean gameOver;

	// Graphics memory

	MediaTracker tracker;

	private EvolutionChamber evolutionPit;
	private InformationOverlay infoOverlay;
	private GameOverScreen gameOverOverlay;

	private Image oceanImage;
	private Image herbivoreImage, omnivoreImage, carnivoreImage;

	MouseHandler mouseHandle;
	MouseMotionHandler mouseMotionHandle;

	/**
	 * The constructor for the DisplayPane object
	 */
	public DisplayPane()
	{
		// Adds mouse handlers to the frame

		mouseHandle = new MouseHandler();
		mouseMotionHandle = new MouseMotionHandler();
		this.addMouseListener(mouseHandle);
		this.addMouseMotionListener(mouseMotionHandle);

		// Instantiates key variables
		evolutionPitOpen = false;
		gameOver = false;
		mainMap = new Map();
		infoOverlay = new InformationOverlay();
		evolutionPit = new EvolutionChamber(mainMap.getMainPlayer());
		gameOverOverlay = new GameOverScreen();

		// Loads in the images for the different organisms and the background
		carnivoreImage = new ImageIcon("carnivore.png").getImage();
		herbivoreImage = new ImageIcon("Herbivore.png").getImage();
		omnivoreImage = new ImageIcon("Omnivore.png").getImage();
		oceanImage = new ImageIcon("OceanTile.jpg").getImage();

		tracker = new MediaTracker(this);
		try
		{
			tracker.waitForID(0);

		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}

		// Gets information about the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		SCREEN_WIDTH = (int) (screenSize.getWidth());
		SCREEN_HEIGHT = (int) (screenSize.getHeight());

		displacementX = SCREEN_WIDTH / 2;
		displacementY = SCREEN_HEIGHT / 2;

		trueScreenCenterX = SCREEN_WIDTH / 2;
		trueScreenCenterY = SCREEN_HEIGHT / 2;

		screenCenterX = trueScreenCenterX
				- mainMap.getMainPlayer().getPlayerImage().getWidth(null) / 2;
		screenCenterY = trueScreenCenterY
				- mainMap.getMainPlayer().getPlayerImage().getHeight(null) / 2;

		mousePos = new Point(trueScreenCenterX, trueScreenCenterY);

		// Starts the timer to run certin methods
		timer = new Timer(TIME_INTERVAL, new TimerEventHandler());
		timer.start();
	}

	/**
	 * The paint method of the DisplayPane
	 * @param g the Graphics component
	 */
	public void paintComponent(Graphics g)
	{
		// Invokes the super constructor
		super.paintComponent(g);
		g.setFont(new Font("Arial", Font.PLAIN, 20));

		// Draws the background
		for (int i = -250; i < SCREEN_WIDTH + 250; i += 250)
		{
			for (int j = -250; j < SCREEN_HEIGHT + 250; j += 250)
			{
				g.drawImage(oceanImage, i + backDisplacementX / 2 % 250, j
						+ backDisplacementY / 2 % 250, null);
			}
		}

		// Draws the PlantMatter objects
		for (int i = 0; i < mainMap.noOfPlants; i++)
		{
			mainMap.getPlantMatter()[i].drawFood(g, (int) playerDisplacementX
					+ displacementX,
					(int) playerDisplacementY + displacementY);

		}

		// Draws the Meat objects
		for (int i = 0; i < mainMap.noOfMeat; i++)
		{
			if (mainMap.getMeat()[i] != null)
			{
				mainMap.getMeat()[i].drawFood(g, (int) playerDisplacementX
						+ displacementX, (int) playerDisplacementY
						+ displacementY);
			}
		}

		// Draws the Player object
		if (!gameOver)
		{
			mainMap.getMainPlayer()
					.drawPlayerImage(g, screenCenterX, screenCenterY);
		}

		// Draws the Carnivores
		for (int i = 0; i < mainMap.noOfC; i++)
		{
			g.drawImage(carnivoreImage, mainMap.getCList()[i].getLocation().x
					+ (int) playerDisplacementX + displacementX,
					mainMap.getCList()[i].getLocation().y
							+ (int) playerDisplacementY + displacementY, this);
		}

		// Draws the Herbivores
		for (int i = 0; i < mainMap.noOfH; i++)
		{
			g.drawImage(herbivoreImage, mainMap.getHList()[i].getLocation().x
					+ (int) playerDisplacementX + displacementX,
					mainMap.getHList()[i].getLocation().y
							+ (int) playerDisplacementY + displacementY, this);
		}

		// Draws the Omnivores
		for (int i = 0; i < mainMap.noOfO; i++)
		{
			g.drawImage(omnivoreImage, mainMap.getOList()[i].getLocation().x
					+ (int) playerDisplacementX + displacementX,
					mainMap.getOList()[i].getLocation().y
							+ (int) playerDisplacementY + displacementY, this);
		}
		// Draws the evolution pit
		if (evolutionPitOpen)
		{
			evolutionPit.drawPit(g, SCREEN_WIDTH, SCREEN_HEIGHT);
		}
		// Draws the information overlay
		infoOverlay.drawOverlay(g, mainMap.getMainPlayer());
		// Draws the game over screen
		if (gameOver)
		{
			gameOverOverlay.drawGameOverScreen(g, SCREEN_WIDTH, SCREEN_HEIGHT);
		}
	}

	/**
	 * A private class to handle mouse movements
	 * @author Felix Sung and Jonathan Sun
	 *
	 */
	private class MouseMotionHandler extends MouseMotionAdapter
	{
		/**
		 * The mouseMoved method
		 */
		public void mouseMoved(MouseEvent event)
		{
			// Updates the angle that the Player is facing
			mousePos = event.getPoint();
			int dx = mousePos.x - trueScreenCenterX;
			int dy = mousePos.y - trueScreenCenterY;
			mainMap.getMainPlayer().setAngle(90 +
					(int) Math.toDegrees(Math.atan((double) dy / dx)));
			if (dx < 0)
			{
				mainMap.getMainPlayer().setAngle(
						mainMap.getMainPlayer().getAngle() + 180);
			}
			// Checks if the evolutionPit is opened
			if (evolutionPitOpen)
			{
				evolutionPit.mouseMotionListenerExtension(mousePos);
			}
		}
	}

	/**
	 * A private class to handler mouse actions
	 * @author Felix Sung and Jonathan Sun
	 *
	 */
	private class MouseHandler extends MouseAdapter
	{
		/**
		 * The mousePressed method
		 */
		public void mousePressed(MouseEvent event)
		{
			// Gets the location of the mouse click
			Point pressedPoint = event.getPoint();

			int xCenter = 69;
			int yCenter = 70;

			int xPoint = pressedPoint.x;
			int yPoint = pressedPoint.y;

			int difX = xCenter - xPoint;
			int difY = yCenter - yPoint;

			// If the user has clicked the evolutionPit button, opens the
			// evolutionPit
			if (Math.pow(difX, 2) + Math.pow(difY, 2) <= Math.pow(55, 2))
			{
				if (!evolutionPitOpen)
				{
					evolutionPitOpen = true;
					healthWhenEnteredPit = mainMap.getMainPlayer()
							.getCurrentHealth();
				}
				else
				{
					evolutionPitOpen = false;
				}
			}
			else if (evolutionPitOpen)
			{
				evolutionPit.mouseListenerExtension(pressedPoint);
			}
		}
	}

	/**
	 * A private class to handle Timer events
	 * @author Felix Sung and Jonathan Sun
	 *
	 */
	private class TimerEventHandler implements ActionListener
	{
		/**
		 * The ActionedPerformed method, which is called every 20 milliseconds
		 */
		public void actionPerformed(ActionEvent e)
		{
			// Updates the position of the mouth objects
			for (int i = 0; i < mainMap.getMainPlayer().getListOfMouths()
					.size(); i++)
			{
				mainMap.getMainPlayer().getListOfMouths().get(i)
						.updatePos(mainMap.getMainPlayer());
			}

			// Removes any eaten PlantMatter, Meat, or AIOrganism objects
			mainMap.deletePlant(mainMap.getEatiblePlantIndex(mainMap
					.getMainPlayer()));
			mainMap.deleteMeat(mainMap.getEatibleMeatIndex(mainMap
					.getMainPlayer()));
			mainMap.damageAI(mainMap.getMainPlayer());

			// Updates the Player position
			double dx = mousePos.x - trueScreenCenterX;
			double dy = mousePos.y - trueScreenCenterY;

			if (Math.abs(dx) > 100)
			{
				backDisplacementX -= dx / MOVEMENT_DIVIDER
						* mainMap.getMainPlayer()
								.getSpeedMultiplyer();

				mainMap.getMainPlayer()
						.addToPlayerPosX(
								dx
										/ 100
										* mainMap.getMainPlayer()
												.getSpeedMultiplyer());

			}
			if (Math.abs(dy) > 100)
			{
				backDisplacementY -= dy / MOVEMENT_DIVIDER
						* mainMap.getMainPlayer()
								.getSpeedMultiplyer();
				mainMap.getMainPlayer()
						.addToPlayerPosY(dy / 100 * mainMap.getMainPlayer()
								.getSpeedMultiplyer());
			}
			playerDisplacementX = -mainMap.getMainPlayer().getPlayerPosX();
			playerDisplacementY = -mainMap.getMainPlayer().getPlayerPosY();

			// Updates the Food and AI objects
			mainMap.updateFood();
			mainMap.updateBots();

			// Sets the health of the organism to it's value before opening the
			// evolution pit
			if (evolutionPitOpen)
			{
				mainMap.getMainPlayer().setCurrentHealth(healthWhenEnteredPit);
			}
			if (mainMap.getMainPlayer().getCurrentHealth() == 0)
			{
				gameOver = true;
				removeMouseListener(mouseHandle);
				removeMouseMotionListener(mouseMotionHandle);

			}
			if (gameOver)
			{
				mainMap.getMainPlayer().setIsVisible(false);
				mainMap.getMainPlayer().setCurrentHealth(0);
				mainMap.getMainPlayer().setFoodEaten(0);
			}
			// Repaints the screen
			repaint();
		}
	}
}
