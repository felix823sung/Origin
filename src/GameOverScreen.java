import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Class to keep track of the game over screen
 * @author Felix Sung and Jonathan Sun
 *
 */
public class GameOverScreen
{
	// Instantiates the game image
	private Image gameOverImage;

	/**
	 * constructor for the game over screen
	 */
	public GameOverScreen()
	{
		gameOverImage = new ImageIcon("GameOverImage.png").getImage();
	}

	/**
	 * draws the game over screen
	 * @param g the graphics object to use
	 */
	public void drawGameOverScreen(Graphics g,int screenWidth,int screenHeight)
	{
		g.drawImage(gameOverImage, screenWidth/2-gameOverImage.getWidth(null)/2, screenHeight/2-gameOverImage.getHeight(null)/2, null);
	}
}
