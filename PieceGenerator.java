import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
/**
 * Generate a queue of 200 pieces for the Tetris game
 * @author Acosta and Chen
 * @version 1.1 4/6/14
 */
public class PieceGenerator
{
	private Queue<SinglePiece> pieceQueue = new LinkedList<SinglePiece>();
	/**
	 * Constructor, implement the queue of pieces
	 */
	public PieceGenerator()
	{	
		Random r = new Random();
		for (int count=0; count<200; count++)
			pieceQueue.offer(new SinglePiece(r.nextInt(7), r.nextInt(4)));
		//assert pieceQueue.size() == 200;
	}
	/**
	 * Return the queue of pieces
	 * @return the queue of pieces
	 */
	public Queue<SinglePiece> getPieceQueue()
	{
		return pieceQueue;
	}
}
