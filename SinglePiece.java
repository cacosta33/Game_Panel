/**
 * Represent a piece of the Tetris game
 * @author Acosta and Chen
 * @version 1.5 4/8/14
 */
public class SinglePiece
{
	//Represent each status of each shape in a 4*4 grid: [shape][status][grid]:
	public static final int[][][] PIECES = 
	{
		// Shape "I" (has a vertical and a horizontal status)
			{	{0, 0, 0, 0,
				 1, 1, 1, 1,
				 0, 0, 0, 0,
				 0, 0, 0, 0},//first status of "I"
				{0, 1, 0, 0,
				 0, 1, 0, 0,
				 0, 1, 0, 0,
				 0, 1, 0, 0},//second status of "I"
				{0, 0, 0, 0,
				 1, 1, 1, 1,
				 0, 0, 0, 0,
				 0, 0, 0, 0},//third status of "I"
				{0, 1, 0, 0,
				 0, 1, 0, 0,
				 0, 1, 0, 0,
				 0, 1, 0, 0}},//fourth status of "I"
		
		// Shape "O" (all status are the same)
			{	{1, 1, 0, 0,
				 1, 1, 0, 0,
				 0, 0, 0, 0,
				 0, 0, 0, 0},//first status of "O"
				{1, 1, 0, 0,
				 1, 1, 0, 0,
				 0, 0, 0, 0,
				 0, 0, 0, 0},//second status of "O"
				{1, 1, 0, 0,
				 1, 1, 0, 0,
				 0, 0, 0, 0,
				 0, 0, 0, 0},//third status of "O"
				{1, 1, 0, 0,
				 1, 1, 0, 0,
				 0, 0, 0, 0,
				 0, 0, 0, 0}},//fourth status of "O"
		
		// Shape "S" (has two different status)
			{	{0, 1, 1, 0,
				 1, 1, 0, 0,
				 0, 0, 0, 0,
				 0, 0, 0, 0},//first status of "S"
				{1, 0, 0, 0,
				 1, 1, 0, 0,
				 0, 1, 0, 0,
				 0, 0, 0, 0},//second status of "S"
				{0, 1, 1, 0,
				 1, 1, 0, 0,
				 0, 0, 0, 0,
				 0, 0, 0, 0},//third status of "S"
				{1, 0, 0, 0,
				 1, 1, 0, 0,
				 0, 1, 0, 0,
				 0, 0, 0, 0}},//fourth status of "S"
		
		// Shape "Z" (has two different status)
			{	{1, 1, 0, 0,
				 0, 1, 1, 0,
				 0, 0, 0, 0,
				 0, 0, 0, 0},//first status of "Z"
				{0, 1, 0, 0,
				 1, 1, 0, 0,
				 1, 0, 0, 0,
				 0, 0, 0, 0},//second status of "Z"
				{1, 1, 0, 0,
				 0, 1, 1, 0,
				 0, 0, 0, 0,
				 0, 0, 0, 0},//third status of "Z"
				{0, 1, 0, 0,
				 1, 1, 0, 0,
				 1, 0, 0, 0,
				 0, 0, 0, 0}},//fourth status of "Z"
		
		// Shape "J" (has four different status)
			{	{0, 1, 0, 0,
				 0, 1, 0, 0,
				 1, 1, 0, 0,
				 0, 0, 0, 0},//first status of "J"
				{1, 0, 0, 0,
				 1, 1, 1, 0,
				 0, 0, 0, 0,
				 0, 0, 0, 0},//second status of "J"
				{1, 1, 0, 0,
				 1, 0, 0, 0,
				 1, 0, 0, 0,
				 0, 0, 0, 0},//third status of "J"
				{1, 1, 1, 0,
				 0, 0, 1, 0,
				 0, 0, 0, 0,
				 0, 0, 0, 0}},//fourth status of "J"
		
		// Shape "L" (has four different status)
			{	{1, 0, 0, 0,
				 1, 0, 0, 0,
				 1, 1, 0, 0,
				 0, 0, 0, 0},//first status of "L"
				{1, 1, 1, 0,
				 1, 0, 0, 0,
				 0, 0, 0, 0,
				 0, 0, 0, 0},//second status of "L"
				{1, 1, 0, 0,
				 0, 1, 0, 0,
				 0, 1, 0, 0,
				 0, 0, 0, 0},//third status of "L"
				{0, 0, 1, 0,
				 1, 1, 1, 0,
				 0, 0, 0, 0,
				 0, 0, 0, 0}},//fourth status of "L"
		
		// Shape "T" (has four different status)
			{	{1, 1, 1, 0,
				 0, 1, 0, 0,
				 0, 0, 0, 0,
				 0, 0, 0, 0},//first status of "T"
				{0, 1, 0, 0,
				 0, 1, 1, 0,
				 0, 1, 0, 0,
				 0, 0, 0, 0},//second status of "T"
				{0, 1, 0, 0,
				 1, 1, 1, 0,
				 0, 0, 0, 0,
				 0, 0, 0, 0},//third status of "T"
				{0, 1, 0, 0,
				 1, 1, 0, 0,
				 0, 1, 0, 0,
				 0, 0, 0, 0}}//fourth status of "T"
	};
	private int shape;
	private int status;
	/**
	 * Constructor, set the shape and status of this piece
	 * @param shape the shape of this piece
	 * @param status the status of this piece
	 */
	public SinglePiece(int shape, int status)
	{
		this.shape = shape;
		this.status = status;
	}
	/**
	 * @return the shape of this piece.
	 */
	public int getShape()
	{
		return shape;
	}
	/**
	 * @return the status of this piece.
	 */
	public int getStatus()
	{
		return status;
	}
}
