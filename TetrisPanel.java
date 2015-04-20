import java.awt.*;
import java.awt.event.*;
import java.util.Queue;
import javax.swing.*;
/**
 * Represent the Tetris game Panel
 * @author Acosta and Chen
 * @version 1.7 4/8/14
 */
public class TetrisPanel extends JPanel
{
	final int WIDTH = 12, HEIGHT = 22;
	final int SPACE = 0, BLOCK = 1, WALL = 9;
	final int SIDE = 25;//each grid is of SIDE pixels wide and high.
	
	private Queue<SinglePiece> pieceQueue;//queue of the pieces to be displayed
	private SinglePiece next, secondNext, thirdNext, fourthNext;//represent the coming pieces
	
	private int x, y;//represent the upper-left grid of the piece as its coordinate
	private int shape;//represent the shape of the current piece
	private int status;//represent the status of the piece
	private Color colorOfThisPiece;//represent the color of the current piece
	private int[][] board = new int[WIDTH+1][HEIGHT+1];//the board is WIDTH grids wide & HEIGHT grids high
	private int foresee;//pieces the player wants to foresee
	private int score=0, highScore=500;
	private int remaining;//number of pieces remained
	private Timer timer;//the timer to control the dropping down of the piece
	/**
	 * Constructor, create the board, the piece queue, and start the first piece
	 * @param foresee how many pieces the player wants to foresee
	 */
	public TetrisPanel(int foresee)
	{
		this.foresee = foresee;
		timer = new Timer(1000, new TimerListener()); timer.start();//handle timer listener
    	setFocusable(true); requestFocusInWindow();
    	addKeyListener(new KeyboardListener());//handle key listener
		newBoard();
		newPieceQueue();
		newPiece();
	}
	/**
	 * Paint the display of the panel.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//draw the current piece:
		g.setColor(colorOfThisPiece);
		for (int k = 0; k < 16; k++)
			if (SinglePiece.PIECES[shape][status][k] == BLOCK)
				g.fillRect((k%4+1 + x)*SIDE+51, (k/4 + y)*SIDE+1, SIDE-1, SIDE-1);
		//draw the current board:
		g.setColor(Color.GRAY);
		for (int j = 0; j < HEIGHT; j++)
			for (int i = 0; i < WIDTH; i++)
			{
				if (board[i][j] == BLOCK)
					g.fillRect(i*SIDE+51, j*SIDE+1, SIDE-2, SIDE-2);
				if (board[i][j] == WALL)
					g.drawRect(i*SIDE+51, j*SIDE+1, SIDE-2, SIDE-2);
			}
		//draw score:
		g.setColor(Color.BLUE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		g.drawString("Score = "+score, WIDTH*SIDE+75, SIDE);
		g.drawString("High Score = "+highScore, WIDTH*SIDE+75, 2*SIDE);
		//draw coming piece(s):
		g.drawString("Remaining Pieces: "+remaining,WIDTH*SIDE+75,4*SIDE);
		if (foresee == 0) return;//foresee no piece.
		g.drawString("What's Coming:",WIDTH*SIDE+75,5*SIDE);
		g.setColor(Color.DARK_GRAY);
		for (int k = 0; k < 16; k++)
			if (next != null &&
				SinglePiece.PIECES[next.getShape()][next.getStatus()][k] == BLOCK)
				g.fillRect((k%4+1)*SIDE+WIDTH*SIDE+85, (k/4)*SIDE+6*SIDE, SIDE-1, SIDE-1);
		if (foresee == 1) return;//foresee 1 piece; otherwise foresee 4 pieces
		g.setColor(Color.DARK_GRAY);
		for (int k = 0; k < 16; k++)
			if (secondNext != null &&
				SinglePiece.PIECES[secondNext.getShape()][secondNext.getStatus()][k] == BLOCK)
				g.fillRect((k%4+1)*SIDE+WIDTH*SIDE+85, (k/4)*SIDE+10*SIDE+1, SIDE-1, SIDE-1);
		g.setColor(Color.GRAY);
		for (int k = 0; k < 16; k++)
			if (thirdNext != null &&
				SinglePiece.PIECES[thirdNext.getShape()][thirdNext.getStatus()][k] == BLOCK)
				g.fillRect((k%4+1)*SIDE+WIDTH*SIDE+85, (k/4)*SIDE+14*SIDE+2, SIDE-1, SIDE-1);
		g.setColor(Color.LIGHT_GRAY);
		for (int k = 0; k < 16; k++)
			if (fourthNext != null &&
				SinglePiece.PIECES[fourthNext.getShape()][fourthNext.getStatus()][k] == BLOCK)
				g.fillRect((k%4+1)*SIDE+WIDTH*SIDE+85, (k/4)*SIDE+18*SIDE+3, SIDE-1, SIDE-1);
	}
	/**
	 * Create a new game board (field)
	 */
	private void newBoard()
	{
		//Draw the empty game board
		for (int i = 0; i < WIDTH; i++)
			for (int j = 0; j < HEIGHT; j++)
				board[i][j] = SPACE;
		//Draw the bottom floor
		for (int i = 0; i < WIDTH; i++)
			board[i][HEIGHT-1] = WALL;
		//Draw two side wall
		for (int j = 0; j < HEIGHT; j++)
		{
			board[0][j] = WALL;
			board[WIDTH-1][j] = WALL;
		}
	}
	/**
	 * Create a new queue of pieces using PieceGenerator class.
	 */
	private void newPieceQueue()
	{
		PieceGenerator pg = new PieceGenerator();
		pieceQueue = pg.getPieceQueue();
		remaining = pieceQueue.size();
		
		next = pieceQueue.poll();
		secondNext = pieceQueue.poll();
		thirdNext = pieceQueue.poll();
		fourthNext = pieceQueue.poll();
	}
	/**
	 * Display the next piece on the top of the board.
	 */
	private void newPiece()
	{
		if (next == null)
		{//Game Over when all pieces were polled out
			timer.stop();
			JOptionPane.showMessageDialog(null,"GAME FINISHED! Your score:"+score,
					"Message", JOptionPane.INFORMATION_MESSAGE);
			newGame();//Start a new game.
		}
		x = 4; y = 0;//each new piece starts at coordinate (4,0)
		if (gameOver(x, y, next.getShape(), next.getStatus()))
		{//Game Over when a new piece cannot enter in the board
			timer.stop();
			JOptionPane.showMessageDialog(null,"GAME OVER! Your score:"+score,
					"Message", JOptionPane.INFORMATION_MESSAGE);
			newGame();//Start a new game.
		}
		
		Color[] colorChoices = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, 
								Color.CYAN, Color.BLUE, Color.PINK, Color.MAGENTA};
		colorOfThisPiece = colorChoices[(int)(Math.random()*colorChoices.length)];
		shape = next.getShape();
		status = next.getStatus();
		next = secondNext;
		secondNext = thirdNext;
		thirdNext = fourthNext;
		fourthNext = pieceQueue.poll();
		remaining--;
	}
	/**
	 * Rotate the piece.
	 */
	private void rotate()
	{
		int temp = (status+1) % 4;//store the next status of this shape to temp
		if (isValid(x,y,shape,temp))//if the rotation is valid
			status = temp;//update the status
		repaint();
	}
	/**
	 * Move the piece to the left.
	 */
	private void left()
	{
		if (isValid(x-1,y,shape,status))//if moving the piece to left is valid
			x--;//move the piece to left
		repaint();
	}
	/**
	 * Move the piece to the right.
	 */
	private void right()
	{
		if (isValid(x+1,y,shape,status))//if moving the piece to right is valid
			x++;//move the piece to right
		repaint();
	}
	/**
	 * Drop the piece down.
	 */
	private void down()
	{
		if (isValid(x,y+1,shape,status))//if moving the piece down is valid
			y++;//move the piece down to one grid
		else//if the piece cannot be moved down any more
		{
			updateBoard(x,y,shape,status);//add the piece to the board, remove, and score if needed
			newPiece();//initiate the next piece
		}
		repaint();
	}
	/**
	 * Decide a new position for the piece is valid or not.
	 * @param x x_coordinate of the new position
	 * @param y y_coordinate of the new position
	 * @param shape the shape of the piece
	 * @param status the (new) status of the piece
	 * @return true if the new position is valid.
	 */
	private boolean isValid(int x, int y, int shape, int status)
	{
		for (int j=0; j<4; j++)
			for (int i=0; i<4; i++)
				if ( ((SinglePiece.PIECES[shape][status][j*4+i]==BLOCK)&&(board[x+i+1][y+j]==BLOCK)) ||
					 ((SinglePiece.PIECES[shape][status][j*4+i]==BLOCK)&&(board[x+i+1][y+j]==WALL)) )
					 return false;
		return true;
	}
	/**
	 * Called when a piece hit the ground (board), clear the filled row (if needed) and update the score.
	 * @param x x_coordinate of the piece to be updated to board
	 * @param y y_coordinate of the piece to be updated to board
	 * @param shape the shape of the piece to be updated to board
	 * @param status the status of the piece to be updated to board
	 */
	private void updateBoard(int x, int y, int shape, int status)
	{
		int k = 0;//k iterates all 16 grids of the piece
		//update the 4*4 grid of the piece to the board
		for (int i=0; i<4; i++)
			for (int j=0; j<4; j++)
			{
				if (board[x+j+1][y+i] == SPACE) board[x+j+1][y+i] = SinglePiece.PIECES[shape][status][k];
				k++;
			}
		//remove the full row(s)
		int count = 0, numOfFull = 0;
		for (int j=0; j<HEIGHT; j++)
		{
			for (int i=0; i<WIDTH; i++)
			
				if (board[i][j] == BLOCK)
				
					count++;
					if (count == WIDTH-2)//this row is filled full
					
						numOfFull++;
						for (int n=j; n>0; n--)
							for (int m=0; m<WIDTH-1; m++)
								bhttp://www.webassign.net/oard[m][n]=board[m][n-1];
					
				
			
			count = 0;
		}
		//update score if removing row(s) happened
		if (numOfFull==0) score ++;
		if (numOfFull==1) score += 10;
		if (numOfFull==2) score += 30;//bonus for two rows removing
		if (numOfFull==3) score += 60;//bonus for three rows removing
		if (numOfFull==4) score += 100;//bonus for four rows removing
		if (score>highScore) highScore = score;
	}
	/**
	 * Called when a new piece entered the board to see if the game is over due to its entrance
	 * @param x x_coordinate of the piece
	 * @param y y_coordinate of the piece
	 * @param shape the shape of the piece
	 * @param status the status of the piece
	 * @return true if the game is over due to this new piece's entrance
	 */
	private boolean gameOver(int x, int y, int shape, int status)
	{
		if (!isValid(x,y,shape,status))//if the new piece cannot be initialized at initial coordinate (4,0)
			return true;
		return false;
	}
	/**
	 * Renew the board, the queue of the pieces, and score to start a new game.
	 */
	private void newGame(){
		newBoard();
		newPieceQueue();
		score = 0;
		timer.start();
	}
	/**
	 * ActionListener for the timer
	 * @author Acosta and Chen
	 * @version 1.2 4/6/14
	 */
	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			down();
		}
	}
	/**
	 * KeyListener for the control of the piece
	 * @author Acosta and Chen
	 * @version 1.4 4/7/14
	 */
	private class KeyboardListener implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_LEFT:
					left();
					break;
				case KeyEvent.VK_RIGHT:
					right();
					break;
				case KeyEvent.VK_UP:
					rotate();
					break;
				case KeyEvent.VK_DOWN:
					down();
					break;
			}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}
}
