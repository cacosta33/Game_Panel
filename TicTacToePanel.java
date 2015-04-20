import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.Stack;
/**
 * Represent the TicTacToe game Panel
 * @author Acosta and Chen
 * @version 1.5 4/8/14
 */
public class TicTacToePanel extends JPanel
{
	private int turn = 1;//PlayerX's turn == 1, PlayerO's turn == 2
	private int playerXWin = 0, playerOWin = 0;
	private int gameCount = 1;
	private Cell[][] cell = new Cell[3][3];
	
	private JPanel boardPanel = new JPanel();//boardPanel is the 3*3 game board.
	private JLabel statusLabel = new JLabel("PlayerX starts this time.");//statusLabel is the label showing current status.
	private JLabel titleLabel = new JLabel("Tic Tac Toe Game 1");
	private JLabel infoLabel= new JLabel("X:O = "+playerXWin+":"+playerOWin);
	private JButton undo = new JButton("UNDO");
	
	private Stack<Cell> stack = new Stack<Cell>();
	/**
	 * Constructor, creates and implements all the components for display.
	 */
	public TicTacToePanel()
	{
		boardPanel.setLayout(new GridLayout(3,3,0,0));
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++)
				boardPanel.add(cell[i][j] = new Cell(i,j));
		boardPanel.setBorder(new LineBorder(Color.blue,1));
		
		statusLabel.setBorder(new LineBorder(Color.red, 1));
		statusLabel.setFont(new Font("Arial", Font.BOLD, 50));
		titleLabel.setBorder(new LineBorder(Color.black, 1));
		titleLabel.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 60));
		infoLabel.setBorder(new LineBorder(Color.green, 1));
		infoLabel.setFont(new Font("Arial", Font.PLAIN, 60));
		
		undo.addActionListener(new UndoButtonListener());
		undo.addKeyListener(new ControlZListener());
		
		this.setLayout(new BorderLayout());
		this.add(boardPanel, BorderLayout.CENTER);
		this.add(statusLabel, BorderLayout.SOUTH);
		this.add(titleLabel, BorderLayout.NORTH);
		this.add(infoLabel, BorderLayout.EAST);
		this.add(undo, BorderLayout.WEST);
	}
	/**
	 * Decide if the player just won for this turn
	 * @param symbol the symbol for the current player
	 * @return true if the player just won
	 */
	private boolean isWin(char symbol)
	{
		for(int i=0;i<3;i++)
			if((cell[i][0].getSymbol()==symbol)
					&&(cell[i][1].getSymbol()==symbol)&&(cell[i][2].getSymbol()==symbol))
				return true;//if the player win horizontally
		for(int j=0;j<3;j++)
			if((cell[0][j].getSymbol()==symbol)
					&&(cell[1][j].getSymbol()==symbol)&&(cell[2][j].getSymbol()==symbol))
				return true;//if the player win vertically
		if((cell[0][0].getSymbol()==symbol)
				&&(cell[1][1].getSymbol()==symbol)&&(cell[2][2].getSymbol()==symbol))
			return true;//if the player win cis-diagonally
		if((cell[0][2].getSymbol()==symbol)
				&&(cell[1][1].getSymbol()==symbol)&&(cell[2][0].getSymbol()==symbol))
			return true;//if the player win trans-diagonally
		return false;
	}
	/**
	 * Decide if the game is ended as a draw
	 * @return true if the game is ended as a draw
	 */
	private boolean isDraw()
	{
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				if(cell[i][j].getSymbol()==' ') return false;//if there is still empty cell
		return true;
	}
	/**
	 * Reset the game by empty all the symbols on the board.
	 */
	private void gameOver()
	{//Reset the game
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++)
				cell[i][j].setSymbol(' ');
		infoLabel.setText("X:O = "+playerXWin+":"+playerOWin);
		stack.clear();
		turn = (gameCount + 1) % 2 + 1;
		if (turn == 1) statusLabel.setText("PlayerX starts this time.");
		if (turn == 2) statusLabel.setText("PlayerO starts this time.");
		titleLabel.setText("Tic Tac Toe Game "+gameCount);
	}
	/**
	 * Represent a cell in the 3*3 board.
	 * @author Acosta and Chen
	 * @version 1.3 4/5/14
	 */
	private class Cell extends JPanel
	{
		private int i, j;//row and column index of this cell
		private char symbol;//'X', 'O', or ' '
		/**
		 * Get the char symbol of this cell
		 * @return symbol ('X', 'O', or ' ')
		 */
		public char getSymbol()
		{
			return symbol;
		}
		/**
		 * Set the char symbol of this cell, push to the stack, and repaint the panel
		 * @param newSymbol the symbol to set
		 */
		public void setSymbol(char newSymbol)
		{
			symbol = newSymbol;
			if ((symbol == 'X')||(symbol == 'O')) stack.push(this);
			repaint();
		}
		/**
		 * Constructor, create the cell and add mouse listener.
		 * @param i Row index of this cell
		 * @param j Column index of this cell
		 */
		public Cell(int i, int j)
		{
			this.i = i; this.j = j; this.symbol = ' ';
			setBorder(new LineBorder(Color.yellow, 1));
			addMouseListener(new StepListener());
		}
		/**
		 * Paint the display of the panel.
		 */
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			if(symbol == 'X')
			{
				g.drawLine(10, 10, getSize().width-10, getSize().height-10);
				g.drawLine(getSize().width-10, 10, 10, getSize().height-10);
			}
			if(symbol == 'O')
			{
				g.drawOval(10, 10, getSize().width-20, getSize().height-20);
			}
		}
		/**
		 * MouseListener for the cells of the board
		 * @author Acosta and Chen
		 * @version 1.2 4/4/14
		 */
		private class StepListener implements MouseListener
		{
			public void mousePressed(MouseEvent e)
			{
				if(symbol == ' ')//Each step must click an empty cell i.e. symbol is ' '
				{
					if(turn == 1)
					{
						setSymbol('X');
						if(isWin('X'))
						{
							playerXWin++; gameCount++;
							JOptionPane.showMessageDialog(null,"X won! "+playerXWin+":"+playerOWin,
									"Message", JOptionPane.YES_NO_CANCEL_OPTION);
							//statusLabel.setText("X won! Game Over");
							gameOver();
						}
						else if(isDraw())
						{
							gameCount++;
							JOptionPane.showMessageDialog(null,"Draw! "+playerXWin+":"+playerOWin,
									"Message", JOptionPane.INFORMATION_MESSAGE);
							//statusLabel.setText("Draw! Game Over");
							gameOver();
						}
						else
						{
							turn = 2;
							statusLabel.setText("O's turn");
						}
					}
					else if(turn == 2)
					{
						setSymbol('O');
						if(isWin('O'))
						{
							playerOWin++; gameCount++;
							JOptionPane.showMessageDialog(null, "O won! "+playerXWin+":"+playerOWin
									,"Message", JOptionPane.INFORMATION_MESSAGE);
							//statusLabel.setText("O won! Game Over");
							gameOver();
						}
						else if(isDraw())
						{
							gameCount++;
							JOptionPane.showMessageDialog(null,"Draw! "+playerXWin+":"+playerOWin,
									"Message", JOptionPane.INFORMATION_MESSAGE);
							//statusLabel.setText("Draw! Game Over");
							gameOver();
						}
						else
						{
							statusLabel.setText("X's turn");
							turn = 1;
						}
					}
				}
				else
				{//Show the user that this move is invalid
					JOptionPane.showMessageDialog(null,"Not here!",
							"Message", JOptionPane.INFORMATION_MESSAGE);
				}
				undo.requestFocus();
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		}
	}
	/**
	 * ActionListener for the Undo Button.
	 * @author Acosta and Chen
	 * @version 1.0 4/3/14
	 */
	private class UndoButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			undoOneStep();
		}
	}
	/**
	 * KeyListener for the Control-Z.
	 * @author Acosta and Chen
	 * @version 1.0 4/5/14
	 */
	private class ControlZListener implements KeyListener
	{
		public void keyPressed(KeyEvent event)
		{
			if(event.isControlDown() == true)
				if(event.getKeyCode() == KeyEvent.VK_Z)
					undo.doClick();
		}
		public void keyReleased(KeyEvent event){}
		public void keyTyped(KeyEvent event){}
	}
	/**
	 * Undo one step of the game when the game is not finished. Called by the undo button action.
	 */
	private void undoOneStep()
	{
		if (stack.size() > 0)
		{
			Cell oneStep = stack.pop();
			cell[oneStep.i][oneStep.j].setSymbol(' ');
			int temp = 0;
			if (turn == 1) temp = 2;
			else if (turn == 2) temp = 1;
			turn = temp;
			if (turn == 1) statusLabel.setText("X's turn");
			if (turn == 2) statusLabel.setText("O's turn");
		}
	}
}