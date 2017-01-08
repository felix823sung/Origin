/**
 * The Map class, which contains the information regarding the Player object, as
 * well as the arrays that contain the different AI organisms and foods
 * @author Felix Sung and Jonathan Sun
 *
 */
public class Map
{

	// Initialize key variables, such as the Player object and organism arrays
	private Player mainPlayer;
	private PlantMatter[] plantFood;
	private Carnivore[] cList;
	private Herbivore[] hList;
	private Omnivore[] oList;
	public final int noOfC = 80;
	public final int noOfH = 60;
	public final int noOfO = 50;
	public final int noOfPlants = 200;
	public final int noOfMeat = 500;
	private Meat[] listOfMeat;

	/**
	 * The constructor for the Map object
	 */
	public Map()
	{
		// Instantiates key variables, fills up the arrays
		mainPlayer = new Player(0);
		listOfMeat = new Meat[noOfMeat];
		plantFood = new PlantMatter[noOfPlants];
		for (int foodNumber = 0; foodNumber < noOfPlants; foodNumber++)
		{
			plantFood[foodNumber] = new PlantMatter(mainPlayer);
			plantFood[foodNumber].setIndex(foodNumber);
		}
		cList = new Carnivore[noOfC];
		hList = new Herbivore[noOfH];
		oList = new Omnivore[noOfO];
		for (int hNo = 0; hNo < noOfH; hNo++)
		{
			hList[hNo] = new Herbivore(mainPlayer, plantFood);
		}

		for (int oNo = 0; oNo < noOfO; oNo++)
		{
			oList[oNo] = new Omnivore(mainPlayer, cList, hList, plantFood,
					listOfMeat);
		}

		for (int cNo = 0; cNo < noOfC; cNo++)
		{
			cList[cNo] = new Carnivore(mainPlayer, oList, hList, listOfMeat);
		}
	}

	/**
	 * A method to generate a random integer from 0 to 9 inclusive
	 * @return a random integer from 0 to 9
	 */
	public int generate()
	{
		return (int) (Math.random() * 10);
	}

	/**
	 * Adds a Meat object to the Meat array at the first empty index in the
	 * array
	 * @param meat the meat object to be added
	 */
	public void addMeat(Meat meat)
	{
		int check = 0;
		while (listOfMeat[check] != null)
		{
			check++;
		}
		listOfMeat[check] = meat;
	}

	/**
	 * A method to get the array of Meat objects
	 * @return the array of Meat objects
	 */
	public Meat[] getMeat()
	{
		return listOfMeat;
	}

	/**
	 * A method to get the array of Carnivores
	 * @return the array of Carnivores
	 */
	public Carnivore[] getCList()
	{
		return cList;
	}

	/**
	 * A method to get the array of Herbivores
	 * @return the array of Herbivores
	 */
	public Herbivore[] getHList()
	{
		return hList;
	}

	/**
	 * A method to get the array of Omnivores
	 * @return the array of Omnivores
	 */
	public Omnivore[] getOList()
	{
		return oList;
	}

	/**
	 * A method to get the array of PlantMatter
	 * @return the array of PlantMatter
	 */
	public PlantMatter[] getPlantMatter()
	{
		return plantFood;
	}

	/**
	 * A method to get a reference to the Player object
	 * @return
	 */
	public Player getMainPlayer()
	{
		return mainPlayer;
	}

	/**
	 * A method to set the reference to the Player object to another Player
	 * object
	 * @param player
	 */
	public void setMainPlayer(Player player)
	{
		mainPlayer = player;
	}

	/**
	 * A method to update the PlantMatter array
	 */
	public void updateFood()
	{
		// Goes through the array of PlantMatter
		for (int foodNumber = 0; foodNumber < noOfPlants; foodNumber++)
		{
			// Calculates the distance from each PlantMatter to the Player
			// object
			double dx = plantFood[foodNumber].getLocation().x
					- mainPlayer.getPlayerPosX();
			double dy = plantFood[foodNumber].getLocation().y
					- mainPlayer.getPlayerPosY();
			int distance = (int) Math.sqrt(dx * dx + dy * dy);
			// If the PlantMatter object is too far away from the Player object,
			// replaces it with a new PlantMatter object closer to the Player
			// object
			if (distance > 10000)
			{
				plantFood[foodNumber] = new PlantMatter(mainPlayer);
			}
		}
	}

	/**
	 * A method to update the different organism arrays
	 */
	public void updateBots()
	{
		// Goes through the array of Carnivores
		for (int i = 0; i < noOfC; i++)
		{
			// Checks if the organism at this index is active, if not, has a
			// change to spawn a Meat object, and replaces the organism with a
			// new organism
			if (!cList[i].isActive())
			{
				cList[i].stop();
				if (generate() % 4 == 0)
				{
					addMeat(new Meat(mainPlayer, cList[i].getLocation()));
				}
				cList[i] = new Carnivore(mainPlayer, oList, hList,
						listOfMeat);
			}
			// If the organism is active, checks the distance from the organism
			// to the Player, if too far, replaces it with a new organism closer
			// to the Player
			else
			{
				double dx = cList[i].getLocation().x
						- mainPlayer.getPlayerPosX();
				double dy = cList[i].getLocation().y
						- mainPlayer.getPlayerPosY();
				int distance = (int) Math.sqrt(dx * dx + dy * dy);
				if (distance > 10000)
				{
					cList[i] = new Carnivore(mainPlayer, oList, hList,
							listOfMeat);
				}
			}

		}
		// Follows a similar process to update the array of Herbivores
		for (int i = 0; i < noOfH; i++)
		{

			if (!hList[i].isActive())
			{
				hList[i].stop();
				if (generate() % 4 == 0)
				{
					addMeat(new Meat(mainPlayer, hList[i].getLocation()));
				}
				hList[i] = new Herbivore(mainPlayer, plantFood);
			}
			else
			{
				double dx = hList[i].getLocation().x
						- mainPlayer.getPlayerPosX();
				double dy = hList[i].getLocation().y
						- mainPlayer.getPlayerPosY();
				int distance = (int) Math.sqrt(dx * dx + dy * dy);
				if (distance > 10000)
				{
					hList[i] = new Herbivore(mainPlayer, plantFood);
				}
			}

		}
		// Follows a similar process to update the array of Omnivores
		for (int i = 0; i < noOfO; i++)
		{

			if (!oList[i].isActive())
			{
				oList[i].stop();
				if (generate() % 4 == 0)
				{
					addMeat(new Meat(mainPlayer, oList[i].getLocation()));
				}
				oList[i] = new Omnivore(mainPlayer, cList, hList,
						plantFood, listOfMeat);
			}
			else
			{
				double dx = oList[i].getLocation().x
						- mainPlayer.getPlayerPosX();
				double dy = oList[i].getLocation().y
						- mainPlayer.getPlayerPosY();
				int distance = (int) Math.sqrt(dx * dx + dy * dy);
				if (distance > 10000)
				{
					oList[i] = new Omnivore(mainPlayer, cList, hList,
							plantFood, listOfMeat);
				}
			}
		}

	}

	/**
	 * Determines whether the Player object can eat any PlantMatter object
	 * @param player the Player object to use as reference
	 * @return the index of the PlantMatter object in the array
	 */
	public int getEatiblePlantIndex(Player player)
	{
		// Goes through the array of PlantMatter
		for (int i = 0; i < noOfPlants; i++)
		{
			// Goes through the mouth arrayList of the Player object
			for (int j = 0; j < player.getListOfMouths().size(); j++)
			{
				// Checks whether the player can eat PlantMatter
				if (player.getListOfMouths().get(j).getCanEatPlants())
				{
					// Gets the locations of the food and player mouth, can
					// calculates the difference between the x and y values
					int xCenter = plantFood[i].getLocation().x;
					int yCenter = plantFood[i].getLocation().y;

					int xPoint = player.getListOfMouths().get(j).getXLocation();
					int yPoint = player.getListOfMouths().get(j).getYLocation();

					int difX = xCenter - xPoint;
					int difY = yCenter - yPoint;

					// Determines whether the mouth object is touching or within
					// the PlantMatter object. If so, returns the index of the
					// PlantMatter object in the array
					if (Math.pow(difX, 2) + Math.pow(difY, 2) <= Math.pow(
							plantFood[i].getImage().getWidth(null) / 2, 2))
					{
						return i;
					}
				}
			}
		}
		// If no PlantMatter object can be eaten, returns -1
		return -1;
	}

	/**
	 * Determines whether the Player object can damage any other organisms
	 * @param player the Player object to use as reference
	 */
	public void damageAI(Player player)
	{
		// Goes through the array of Herbivores
		for (int hIndex = 0; hIndex < noOfH; hIndex++)
		{
			// Goes through the mouth arrayList of the Player object
			for (int j = 0; j < player.getListOfMouths().size(); j++)
			{
				// Gets the location of the organism and the player, and
				// calculates the difference between the x and y values
				int xCenter = hList[hIndex].getLocation().x + 75;
				int yCenter = hList[hIndex].getLocation().y + 75;
				int xPoint = player.getListOfMouths().get(j).getXLocation();
				int yPoint = player.getListOfMouths().get(j).getYLocation();
				int difX = xCenter - xPoint;
				int difY = yCenter - yPoint;
				// Determines whether the mouth object is touching the organism.
				// If so, damages the organism by the mouth damage amount
				if (difX * difX + difY * difY <= 2500)
				{
					hList[hIndex].takeDamage(player.getListOfMouths().get(j)
							.getDamage());
				}
			}
		}
		// Follows a similar process to update the array of Carnivores
		for (int cIndex = 0; cIndex < noOfC; cIndex++)
		{
			for (int j = 0; j < player.getListOfMouths().size(); j++)
			{
				int xCenter = cList[cIndex].getLocation().x + 75;
				int yCenter = cList[cIndex].getLocation().y + 75;
				int xPoint = player.getListOfMouths().get(j).getXLocation();
				int yPoint = player.getListOfMouths().get(j).getYLocation();
				int difX = xCenter - xPoint;
				int difY = yCenter - yPoint;
				if (difX * difX + difY * difY <= 2500)
				{
					cList[cIndex].takeDamage(player.getListOfMouths().get(j)
							.getDamage());
				}
			}
		}
		// Follows a similar process to update the array of Omnivores
		for (int oIndex = 0; oIndex < noOfO; oIndex++)
		{
			for (int j = 0; j < player.getListOfMouths().size(); j++)
			{
				int xCenter = oList[oIndex].getLocation().x + 75;
				int yCenter = oList[oIndex].getLocation().y + 75;
				int xPoint = player.getListOfMouths().get(j).getXLocation();
				int yPoint = player.getListOfMouths().get(j).getYLocation();
				int difX = xCenter - xPoint;
				int difY = yCenter - yPoint;
				if (difX * difX + difY * difY <= 2500)
				{
					oList[oIndex].takeDamage(player.getListOfMouths().get(j)
							.getDamage());
				}
			}
		}

	}

	/**
	 * Determines whether the Player object can eat a Meat object
	 * @param player the Player object to use for reference
	 * @return the index of the Meat object in the array
	 */
	public int getEatibleMeatIndex(Player player)
	{
		// Follows a similar process to updating the PlantMatter array
		for (int i = 0; i < noOfMeat; i++)
		{
			if (listOfMeat[i] != null)
			{
				for (int j = 0; j < player.getListOfMouths().size(); j++)
				{
					if (player.getListOfMouths().get(j).getCanEatMeat())
					{
						int xCenter = listOfMeat[i].getLocation().x;
						int yCenter = listOfMeat[i].getLocation().y;

						int xPoint = player.getListOfMouths().get(j)
								.getXLocation();
						int yPoint = player.getListOfMouths().get(j)
								.getYLocation();

						int difX = xCenter - xPoint;
						int difY = yCenter - yPoint;

						if (Math.pow(difX, 2) + Math.pow(difY, 2) <= Math.pow(
								listOfMeat[i].getImage().getWidth(null) / 2, 2))
						{
							return i;
						}
					}
				}
			}
		}
		return -1;
	}

	/**
	 * Removes the PlantMatter object at a specified index in the PlantMatter
	 * array. After removing the object, replaces with a new PlantMatter object
	 * @param foodToDelete the index of the PlantMatter object to remove
	 */
	public void deletePlant(int foodToDelete)
	{
		if (foodToDelete != -1)
		{
			mainPlayer.eat(plantFood[foodToDelete].getNutrientValue());
			plantFood[foodToDelete] = new PlantMatter(mainPlayer);
		}
	}

	/**
	 * Removes the Meat object at a specified index in the Meat array. After
	 * removing the object, replaces with a new Meat object
	 * @param foodToDelete the index of the Meat object to remove
	 */
	public void deleteMeat(int foodToDelete)
	{
		if (foodToDelete != -1)
		{
			mainPlayer.eat(listOfMeat[foodToDelete].getNutrientValue());
			listOfMeat[foodToDelete] = null;
		}
	}
}
