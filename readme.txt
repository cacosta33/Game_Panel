1.)Names: Cesar Acosta and Chen Chen
2.)Class: CSCI 1302
3.)Cesar Acosta's ID: 810-359-2250
   Chen Chen's ID: 810-304-9140
4.)Date: 4/8/14
5.)Instructions on how to compile and run the program:
	-With the use of the makefile through command line, issue the following commands
	when in the correct source folder:
		a.)"make compile" to compile all the necessary programs
		b.)"make run", this will run the driver class P4Arcade that will execute
			every other program by opening it up in the main frame.
		c.)"make clean" to remove all class files when you are done with the program.

6.)Instructions when the program is running:
	a.)When the program opens, you will be greeted by the welcome screen.
	b.) You are given two options: Tetris or Tic Tac Toe
		--For Tetris:
				You can move a dropping piece and place it in a correct place.
				LEFT key to move the piece to the left
				RIGHT key to move the piece to the right
				UP key to rotate the piece
				DOWN key to accelerate the dropping
				If any row is fully filled, then it clears and you gain a lot of points!
				You will have a total of 200 pieces, score as much as you can!"


		--For Tic Tac Toe:
				Two players (Player X and Player O) can play this game
				by putting X's and O's on the board.
				Player X starts first, and then it alternates between players.
				If a player has three of his/her shape (X's or O's) in a row,
				this player wins for this turn.
				If the board is filled up, then it is a tie.
				In the next game, the other player will start first.
				The scores will be recorded.
				Click Undo or Press Control-Z to undo one step.


7.) If you want to run the program without the use of the make file
(through use of command line):
	a.) First compile by issuing the following command:
		--"javac *.java"
	b.)Run the main program by issuing the following command:
		--"java P4Arcade"
	c.)Remove all class files by issuing the following command:
		--"rm *.class"