
// Import all of the required packages
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.*;

public class TMNTPizzaTime extends JPanel implements ActionListener, KeyListener {

	private JFrame frame; // Creates the variable for the JFrame
	private Timer startTimer = new Timer(3000, this); // Creates the variable for the starting timer for 3s
	private ImageIcon startImg = new ImageIcon("start.png"); // Creates the starting image
	private ImageIcon pizzaBackground = new ImageIcon("pbackground.jpg"); // Creates the main background
	private ImageIcon pizzaImg = new ImageIcon("Pizza.png"); // Creates the image for the pizza
	private boolean startDone = false; // Creates boolean variable to check if the starting phase is done
	private int pizzaCount = 3; // Creates the variable for storing the pizza's remaining
	private Player player = new Player(); // Created the Player object from player class
	private Box box = new Box(); // Created the Box object from box class
	private final int UP = 0, DOWN = 1; // Create static numerical values for up/down directions
	private boolean up = false, down = false; // Create boolean values to check if entity is travelling up or down
	private Timer playerTimer = new Timer(10, this), boxTimer = new Timer(100, this), pizzaTimer = new Timer(100, this); // Create necessary timers for entities & projectile
	private boolean isFired = false; // Created variable to check if projectile is fired
	private Font font = new Font("Arial", Font.BOLD, 40); // Creates the font object
	private FontMetrics fm = getFontMetrics(font); // Creates the variable for font metrics
	private int strWidth = fm.stringWidth("Pizza Count: 3"); // Creates the variable for string width
	private Rectangle2D pBoxMask; // Creates the pizza box's rectangle mask
	private Ellipse2D pizzaMask; // Creates the pizza's circular mask
	private int xPosPizza = -100, yPosPizza = -100; // Create the variables for the projectile's X & Y
	private int exitChoice; // Choice to exit or replay the program

	public static void main(String[] args) {
		new TMNTPizzaTime(); // Call the main constructor
	}

	public TMNTPizzaTime () {
		frame = new JFrame(); // Instantiates new object for frame
		frame.setSize(423, 512); // The initial frame size is 423x512 pixels
		frame.setContentPane(this); // Adds JPanel components to- JFrame
		frame.setTitle("TMNT PIZZA TIME"); // Setting the frame's title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Closes program on exit
		frame.setLocationRelativeTo(null); // Centers the program on the screen
		frame.setResizable(false); // Sets the frame to be non-resizable
		frame.setVisible(true); // Sets the frame to be visible
		
		// Creates the rectangle and circular object masks for the pizza and box
		pizzaMask = new Ellipse2D.Double(xPosPizza, yPosPizza, pizzaImg.getIconWidth(), pizzaImg.getIconHeight());
		pBoxMask = new Rectangle2D.Double(box.getX(),box.getY(), box.getWidth(), box.getHeight());
		
		// Required methods for keyboard movement (smooth)
		addKeyListener(this);
		setFocusable(true);
		requestFocus();

		startTimer.start(); // Starts the starting timer
		playerTimer.start();
		boxTimer.start();
	}

	public void paint(Graphics g) {
		super.paint(g); // Repaints the frame and its components
		Graphics2D g2 = (Graphics2D)g; // Instantiate a Graphics2D object
		
		if (!startDone) { // Checks if the starting phase is not complete
			g2.drawImage(startImg.getImage(), 0, 5, this); // Draw the starting image
		}
		else { // Checks if the starting phase is complete (default condition)
			g2.drawImage(pizzaBackground.getImage(), 0, 0, this); // Draw the main background
			g2.setFont(font); // Sets the Graphics2D object's font
			g2.drawString("Pizza Count: " + pizzaCount, getWidth() / 2 - strWidth / 2, 650); // Draws the pizza's remaining
			
			// Draw the player and the box at all times. 
			g2.drawImage(player.getImage().getImage(),player.getX(),player.getY(),this);
			g2.drawImage(box.getImage().getImage(),box.getX(),box.getY(),this);

			if (isFired == true)
			{
				// Draw the pizza. 
				g2.drawImage(pizzaImg.getImage(),xPosPizza,yPosPizza,this);
			}
		}
		
		repaint(); // Repaints the frame's components
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			up = true; 
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			down = true; 
		}
		else if (e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			if (isFired == false)
			{
				// Set the pizza location to the players location. 
				xPosPizza = player.getX() + player.getWidth(); // Shift the pizza to the right so it does not appear behind the player. 
				yPosPizza = player.getY() + player.getHeight() / 4;	// Shoot around the middle of the player.			

				// Start the pizza timer so we can animate it. 
				pizzaTimer.start();

				// Change to true so that paint knows to draw a pizza. 
				isFired = true; 
			}

		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			up = false; 
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			down = false; 
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == startTimer) { // Checks if the starting timer is running
			startTimer.stop(); // Stops the starting timer
			startDone = true; // Sets the start phase boolean to true
			
			frame.setSize(900, 803); // Resizes the frame to 900x803 pixels
			frame.setLocationRelativeTo(null); // Recenters the program on the screen
		}
		
		if (e.getSource() == playerTimer)
		{
			if (up == true)
			{
				player.setDirection(UP);

				if (player.getY()>= 0)
				{
					player.move();
				}
			}
			else if (down == true)
			{
				player.setDirection(DOWN);

				if (player.getY() + player.getHeight() + 45 <= frame.getHeight())
				{
					player.move();
				}
			}

		}
		if (e.getSource() == boxTimer)
		{
			// Boundary checking for the Pizza box on the BOTTOM of the window. 
			if ((box.getY() + 40) + box.getHeight() >= frame.getHeight())
			{
				// Change the direction to UP so that the box now moves up. 
				box.setDirection(UP);
			}
			// Check if the pizza box hits the TOP of the window. 
			else if (box.getY() <= 0)
			{
				// Change the direction to DOWN so the box moves down. 
				box.setDirection(DOWN);
			}
			box.move();

			pBoxMask = new Rectangle2D.Double(box.getX(),box.getY(), box.getWidth(), box.getHeight());
		}
		if (e.getSource() == pizzaTimer)
		{
			// Move the pizza to the right. 
			xPosPizza += 30;

			// Draw the pizza mask since this timer only runs when a pizza is made. 
			pizzaMask = new Ellipse2D.Double(xPosPizza, yPosPizza, pizzaImg.getIconWidth(), pizzaImg.getIconHeight());

			// Stop the timer if the pizza left the room. 
			if (isFired == true && xPosPizza + pizzaImg.getIconWidth() >= getWidth())
			{
				isFired = false;
				pizzaTimer.stop();
			}

			// Check if the pizza collided with the pizza box.
			if (pizzaMask.intersects(pBoxMask))
			{
				
				isFired = false;
				
				// Decrease the pizza count
				pizzaCount--;
				
				xPosPizza = player.getX();
				yPosPizza = player.getY();
				
				if (pizzaCount <= 0) {
					// Tell the user they were successful and end the game. 
					JOptionPane.showMessageDialog(null, "Cowabunga!\nYou delivered all the Pizza", "TMNT Pizza Time", JOptionPane.INFORMATION_MESSAGE);
					
					
					exitChoice = JOptionPane.showConfirmDialog(null, "Do you want you to "
							+ "play again?", "TMNT PIZZA TIME", JOptionPane.YES_NO_OPTION);
					
					if (exitChoice == 0) {
						pizzaCount = 3;
					}
					
					if (exitChoice == 1) {
						JOptionPane.showMessageDialog(null, "Thanks for Playing!", "TMNT PIZZA TIME", JOptionPane.INFORMATION_MESSAGE);	
						System.exit(0);	
					}
					
					
				}
			}

		}

	}

}
