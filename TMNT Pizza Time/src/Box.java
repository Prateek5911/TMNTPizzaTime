
import java.util.*;
import javax.swing.*;

public class Box 
{
	private ImageIcon img;
	private int xPos, yPos, dir; 
	private final int UP = 0, DOWN = 1; 
	private Random rand;
	private int height, width; 
	
	public Box()
	{
		rand = new Random();
		img = new ImageIcon("Pizza Box.png");
		
		height = img.getIconHeight();
		width = img.getIconWidth();
		
		xPos = 800 - img.getIconWidth() - 10;
		yPos = rand.nextInt(800 - (img.getIconHeight()- 50)) + 1;
		
		dir = DOWN; 
	}
	public int getHeight()
	{
		return height;
	}
	public int getWidth()
	{
		return width; 
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

