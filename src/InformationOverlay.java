import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * The InformationOverlay class, which displays the information about the Player
 * @author Felix Sung and Jonathan Sun
 *
 */
public class InformationOverlay
{
	// Initializes the image variable
	private Image overlayImage;
	
	/**
	 * The constructor for the InformationOverlay object
	 */
	public InformationOverlay()
	{
		// Loads in the overlay image
		overlayImage = new ImageIcon("HealthPanel.png").getImage();
	}
	/**
	 * The draw method for the information overlay
	 * @param g
	 * @param player
	 */
	public void drawOverlay(Graphics g, Player player)
	{
		// Sets the size of the health and food bar
		int healthBarWidth = (int) (player.getCurrentHealth() * 500.0
				/ player.getMaxHealth());
		int foodBarWidth = (int) (player.getFoodEaten() * 500.0
				/ player.getFoodGoal());
		
		// Draws the bars and overlayImage
		g.drawImage(overlayImage,0,0,null);
		g.setColor(new Color(255, 0, 0));
		g.fillRect(149, 15, healthBarWidth, 16);
		g.setColor(new Color(255, 165, 0));
		g.fillRect(149, 44, foodBarWidth, 16);
	
		// Displays the value of the health and food consumed
		g.setColor(new Color(255,255,255));
		g.drawString(player.getCurrentHealth()+"/"+player.getMaxHealth(), 655, 28);
		g.drawString(player.getFoodEaten()+"/"+player.getFoodGoal(), 655, 58);
	}
}
