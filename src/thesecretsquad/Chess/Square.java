//****************************************************
//File: Square
//
//Purpose: Represents a single square on a chess board.
//
//Written By: Peter DiSalvo 
//****************************************************

package thesecretsquad.Chess;

public class Square
{
	private boolean		m_isVisited;			// True if the square has been visited, otherwise false
	private int 		m_moveNumber;			// The current number from the accumulated number of moves taken so far
	private int 		m_accessibility;		// The number of possible moves from the square
	
	/*--NOTE--
	 * Square locations haven not proven useful
	private int			m_iRowLocation;			// Square knows what row it is in
	private int			m_iColLocation;			// Square knows what column it is in
	private int			m_iDistanceFromEdge;	// Distance from the squares nearest edge
	*/
	
	/*--NOTE--
	 * Quadrants have not proven useful
	private	int			m_iQuadrant;	// Square knows what quadrant of the board it is in
	*/											
	
	//************************************************
	//	Method: Square()
	//
	//	Purpose: Constructor
	//************************************************
	public Square()
	{
		m_isVisited = false;
		m_moveNumber = 0;
	}
	
	/*--NOTE--
	 * Square locations haven not proven useful
	public void setLocation(int in_iRow, int in_iCol)
	{
		m_iRowLocation = in_iRow;
		m_iColLocation = in_iCol;
	}
	
	public int getRowLocation()
	{
		return m_iRowLocation;
	}
	
	public int getColLocation()
	{
		return m_iColLocation;
	}
	
	public void setDistaneFromEdge(int in_iDistance)
	{
		m_iDistanceFromEdge = in_iDistance;
	}
	
	public int getDistanceFromEdge()
	{
		return m_iDistanceFromEdge;
	}
	*/
	
	/*--NOTE--
	 * Quadrants have not proven useful
	//************************************************
	//	Method: setQuadrant()
	//
	//	Purpose: Constructor
	//************************************************
	public void setQuadrant(int in_iQuadrant)
	{
		m_iQuadrant = in_iQuadrant;
	}
	*/
	
	/*--NOTE--
	 * Quadrants have not proven useful
	//************************************************
	//	Method: getQuadrant()
	//
	//	Purpose: Constructor
	//************************************************
	public int getQuadrant()
	{
		return m_iQuadrant;
	}
	*/
	
	//************************************************
	//	Method: setVisited()
	//
	//	Purpose: Receives a boolean value and sets the
	//			 visited attribute of the square
	//************************************************
	public void setVisited(boolean in_bVisited)
	{
		m_isVisited = in_bVisited;
	}
	
	//************************************************
	//	Method: getVisited()
	//
	//	Purpose: Returns the value of the visited attribute.
	//************************************************
	public boolean isVisited()
	{
		return m_isVisited;
	}
	
	//************************************************
	//	Method: setMoveNumber()
	//
	//	Purpose: Receives the current number of moves
	//			 taken by the player and marks the square
	//			 with the number.
	//************************************************
	public void setMoveNumber(int in_iMoveNumber)
	{
		m_moveNumber = in_iMoveNumber;
	}
	
	//************************************************
	//	Method: getMoveNumber()
	//
	//	Purpose: Returns the value of the move number
	//			 of the square.
	//************************************************
	public int getMoveNumber()
	{
		return m_moveNumber;
	}
	
	//************************************************
	//	Method: setAccessibility()
	//
	//	Purpose: Receives an int value and sets the
	//			 accessibility value of the square.
	//************************************************
	public void setAccessibility(int in_iAccessibility)
	{
		m_accessibility = in_iAccessibility;
	}
	
	//************************************************
	//	Method: getAccessibility()
	//
	//	Purpose: Returns the accessibility value of the
	//			 square.
	//************************************************
	public int getAccessibility()
	{
		return m_accessibility;
	}
	
	//************************************************
	//	Method: decrAccessibility()
	//
	//	Purpose: Decrements the accessibility value of
	//			 the square by 1.
	//************************************************
	public void decrAccessibility()
	{
		m_accessibility--;
	}
}
