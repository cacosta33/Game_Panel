import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
/**
 * Represent the CardLayout class, connects each game and the welcome panel together.
 * @author Acosta and Chen
 * @version 1.5 4/8/14
 */
public class OuterGamePanel extends JPanel
{
	private CardLayout c1;
	private JPanel outerPane, mainPanel, tttInstructions, tetrisOptions;
	private JLabel welcome1, welcome2, screenTwoGreet;
	private JButton playTTT, playTetris, zeroP, oneP, fourP; //P stands for pieces to see ahead for tetris
	private TetrisPanel tetris;
	private TicTacToePanel ttt;
	/**
	 * Constructor, creates and implements all the game and instruction panels.
	 */
	public OuterGamePanel()
	{
		c1 = new CardLayout();
		
		outerPane = new JPanel();
		outerPane.setLayout(c1);
		
		//Set Welcome Panel called outerPane	
		mainPanel  = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		welcome1 = new JLabel("Welcome to Cesar and Chen's Arcade!");
		welcome1.setFont(new Font("Broadway", Font.BOLD, 33));
		welcome1.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		welcome2 = new JLabel("Please select a game to play:");
		welcome2.setFont(new Font("Arial", Font.BOLD, 20));
		welcome2.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		playTetris = new JButton(new ImageIcon("tetrisLogo.png"));
		playTetris.setAlignmentX(Component.CENTER_ALIGNMENT);
		playTetris.addActionListener(new CardListener());
		
		playTTT = new JButton(new ImageIcon("ticTacToeLogo.gif"));
		playTTT.setAlignmentX(Component.CENTER_ALIGNMENT);
		playTTT.addActionListener(new CardListener());
		
		mainPanel.add(Box.createRigidArea(new Dimension(0,100)));
		mainPanel.add(welcome1);
		mainPanel.add(welcome2);
		mainPanel.add(Box.createRigidArea(new Dimension(0,50)));
		mainPanel.add(playTetris);
		mainPanel.add(Box.createRigidArea(new Dimension(0,50)));
		mainPanel.add(playTTT);
		mainPanel.setBackground(Color.orange);
		mainPanel.setMinimumSize(new Dimension(780,500));
		
		//Set TTT Instructions Panel called tttInstructions
		tttInstructions = new JPanel();
		tttInstructions.setLayout(new BoxLayout(tttInstructions, BoxLayout.Y_AXIS));
		
		JTextArea tttInstr = new JTextArea();
		tttInstr.setBackground(Color.CYAN);
		tttInstr.setFont(new Font("Times New Roman", Font.BOLD, 20));
		tttInstr.setText("\n\tInstructions of TicTacToe:\n\n"
				+ "\tTwo players (Player X and Player O) can play this game\n"
				+ "\t\tby putting X's and O's on the board.\n\n"
				+ "\tPlayer X starts first, and then it alternates between players.\n"
				+ "\tIf a player has three of his/her shape (X's or O's) in a row,\n"
				+ "\t\tthis player wins for this turn.\n"
				+ "\tIf the board is filled up, then it is a tie.\n"
				+ "\tIn the next game, the other player will start first.\n\n"
				+ "\tThe scores will be recorded.\n"
				+ "\tClick Undo or Press Control-Z to undo one step.");
		
		JLabel tttGreet = new JLabel("Click Continue:");
		tttGreet.setFont(new Font("Times New Roman", Font.BOLD, 25));
		tttGreet.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		TTTContinueListener tcl = new TTTContinueListener();
		
		JButton tttContinue = new JButton("Continue");
		tttContinue.addActionListener(tcl);
		tttContinue.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		tttInstructions.add(tttInstr);
		tttInstructions.add(Box.createRigidArea(new Dimension(0,50)));
		tttInstructions.add(tttGreet);
		tttInstructions.add(Box.createRigidArea(new Dimension(0,50)));
		tttInstructions.add(tttContinue);
		tttInstructions.add(Box.createRigidArea(new Dimension(0,70)));
		tttInstructions.setBackground(Color.GREEN);
		
		//Set Tetris Instruction and Option Panel called tetrisOptions
		tetrisOptions = new JPanel();
		tetrisOptions.setLayout(new BoxLayout(tetrisOptions, BoxLayout.Y_AXIS));
		
		JTextArea tetrisInstr = new JTextArea();
		tetrisInstr.setBackground(Color.CYAN);
		tetrisInstr.setFont(new Font("Times New Roman", Font.BOLD, 20));
		tetrisInstr.setText("\n\n\tInstructions of Tetris:\n\n"
				+ "\tYou can move a dropping piece and place it in a correct place.\n\n"
				+ "\t\tLEFT key to move the piece to the left\n"
				+ "\t\tRIGHT key to move the piece to the right\n"
				+ "\t\tUP key to rotate the piece\n"
				+ "\t\tDOWN key to accelerate the dropping\n\n"
				+ "\tIf any row is fully filled, then it clears and you gain a lot of points!\n"
				+ "\tYou will have a total of 200 pieces, score as much as you can!");
		
		screenTwoGreet = new JLabel("Please choose an option for the number of pieces"
				+ " you want to view ahead: ");
		screenTwoGreet.setFont(new Font("Times New Roman", Font.BOLD, 25));
		screenTwoGreet.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		TetrisOptionsListener tol = new TetrisOptionsListener();
		
		zeroP = new JButton("No Piece Ahead");
		zeroP.addActionListener(tol);
		zeroP.setAlignmentX(Component.CENTER_ALIGNMENT);
		oneP = new JButton("1 Piece Ahead");
		oneP.addActionListener(tol);
		oneP.setAlignmentX(Component.CENTER_ALIGNMENT);
		fourP = new JButton("4 Pieces Ahead");
		fourP.addActionListener(tol);
		fourP.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		tetrisOptions.add(tetrisInstr);
		tetrisOptions.add(Box.createRigidArea(new Dimension(0,20)));
		tetrisOptions.add(screenTwoGreet);
		tetrisOptions.add(Box.createRigidArea(new Dimension(0,20)));
		tetrisOptions.add(zeroP);
		tetrisOptions.add(Box.createRigidArea(new Dimension(0,20)));
		tetrisOptions.add(oneP);
		tetrisOptions.add(Box.createRigidArea(new Dimension(0,20)));
		tetrisOptions.add(fourP);
		tetrisOptions.add(Box.createRigidArea(new Dimension(0,50)));
		tetrisOptions.setBackground(Color.GREEN);
		
		//Adding the mainPanel to the Card Layout
		outerPane.add(mainPanel,"1");
		outerPane.setPreferredSize(new Dimension(800,600));
		add(outerPane);
	}
	/**
	 * ActionListener for the CardLayout
	 * @author Acosta and Chen
	 * @version 1.1 4/8/14
	 */
	private class CardListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == playTetris)
			{
				outerPane.add(tetrisOptions, "4");
				c1.show(outerPane, "4");
			}
			else if(e.getSource() == playTTT)
			{
				outerPane.add(tttInstructions, "5");
				c1.show(outerPane, "5");
			}
		}
	}
	/**
	 * ActionListener for the TicTacToe Continue Button
	 * @author Acosta and Chen
	 * @version 1.0 4/6/14
	 */
	private class TTTContinueListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			ttt = new TicTacToePanel();
			outerPane.add(ttt, "3");
			c1.show(outerPane, "3");
			ttt.requestFocus();
		}
	}
	/**
	 * ActionListener for the Tetris Piece(s) display Options
	 * @author Acosta and Chen
	 * @version 1.0 4/7/14
	 */
	private class TetrisOptionsListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == zeroP)
			{
				tetris = new TetrisPanel(0);
				outerPane.add(tetris, "2");
				c1.show(outerPane, "2");
				tetris.requestFocus();
			}
			else if (e.getSource() == oneP)
			{
				tetris = new TetrisPanel(1);
				outerPane.add(tetris, "2");
				c1.show(outerPane, "2");
				tetris.requestFocus();
			}
			else if (e.getSource() == fourP)
			{
				tetris = new TetrisPanel(4);
				outerPane.add(tetris, "2");
				c1.show(outerPane, "2");
				tetris.requestFocus();
			}
		}
	}

}


