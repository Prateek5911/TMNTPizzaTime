
import javax.swing.*;

public class Player 
{
	private ImageIcon img;
	private int xPos, yPos, dir, width, height; 
	private final int UP = 0, DOWN = 1; 
	
	public Player()
	{
		img = new ImageIcon("Player.png");
		
		xPos = 10; 
		yPos = 10; 
		
		width = img.getIconWidth();
		height = img.getIconHeight();
		
		dir = DOWN; 
	}
	public int getWidth()
	{
		return width; 
	}
	public int getHeight()
	{
		return height;
	}
	public int getX()
	{
		return xPos; 
	}
	public int getY()
	{
		return yPos;
	}
	public ImageIcon getImage()
	{
		return img; 
	}
	public void move()
	{
		if (dir == UP)
		{
			yPos -= 10; 
		}
		else if (dir == DOWN)
		{
			yPos += 10;
		}
	}
	public void setDirection(int yDir)
	{
		dir = yDir; 
	}
	public int getDirection()
	{
		return dir; 
	}
}
