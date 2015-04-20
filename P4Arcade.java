import javax.swing.JFrame;

/**
 * This class is the driver class of the arcade.
 * @author Acosta and Chen
 * @version 1.3 4/8/14
 */
public class P4Arcade
{
	/**
	 * Create the frame, the panel, and start the arcade.
	 * @param args not used.
	 */
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Arcade");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		OuterGamePanel superPanel = new OuterGamePanel();
		frame.getContentPane().add(superPanel);
		
		frame.setSize(900, 700);
		frame.setVisible(true);
	}
}