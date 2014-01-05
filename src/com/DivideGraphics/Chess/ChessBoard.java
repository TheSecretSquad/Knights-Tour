//****************************************************
//File: ChessBoard
//
//Purpose: Represents a chess board.
//
//Written By: Peter DiSalvo 
//****************************************************


package com.DivideGraphics.Chess;

public class ChessBoard
{
	private Square[][]	m_playingBoard;			// Two dimensional array representing the playing board
	private Knight		m_currentKnight;	// Reference to the knight currently on the board
	private final int 	m_BOARD_SIZE;		// Dimension of the board, e.g., 8 x 8
	
	//************************************************
	//	Method: ChessBoard(Knight)
	//
	//	Purpose: Constructor
	//************************************************
	public ChessBoard(Knight in_Knight)
	{
		m_BOARD_SIZE = 8;
		m_playingBoard			= new Square[m_BOARD_SIZE][m_BOARD_SIZE];
		m_currentKnight 	= in_Knight;
		
		createSquares();
		createHeuristics();
	}
	
	//************************************************
	//	Method: ChessBoard(Knight)
	//
	//	Purpose: Constructor
	//************************************************
	public ChessBoard(Knight in_Knight, int in_iDimension)
	{
		m_BOARD_SIZE 		= in_iDimension;
		m_playingBoard			= new Square[m_BOARD_SIZE][m_BOARD_SIZE];
		m_currentKnight 	= in_Knight;
		
		createSquares();
		createHeuristics();
	}
	
	//************************************************
	//	Method: createSquares()
	//
	//	Purpose: Creates squares in array.
	//************************************************
	public void createSquares()
	{
		for(int iRow = 0; iRow < m_BOARD_SIZE; iRow++)
		{
			for(int iCol = 0; iCol < m_BOARD_SIZE; iCol++)
			{
				
				m_playingBoard[iRow][iCol] = new Square();
				/*--NOTE--
				 * Square locations haven not proven useful
				setSquareLocation(m_playingBoard[iRow][iCol] = new Square(), iRow, iCol);
				*/
				
				/*--NOTE--
				 * Quadrants have not proven useful
				setSqrQuadrant(iRow, iCol);
				*/
			}
		}
	}
	
	/*--NOTE--
	 * Square locations haven not proven useful
	public void setSquareLocation(Square in_Square, int in_iRow, int in_iCol)
	{
		in_Square.setLocation(in_iRow, in_iCol);
	}
	
	public int getSquareRowLoc(Square in_Square)
	{
		return in_Square.getRowLocation();
	}
	
	public int getSquareColLoc(Square in_Square)
	{
		return in_Square.getColLocation();
	}
	
	public void findEdge(Square in_Square)
	{
		int iDistanceFromRight;
		int iDistanceFromLeft;
		int iDistanceFromTop;
		int iDistanceFromBottom;
		
		iDistanceFromRight = m_BOARD_SIZE - in_Square.getColLocation();
		iDistanceFromLeft = in_Square.getColLocation();
		iDistanceFromTop = in_Square.getRowLocation();
		iDistanceFromBottom = m_BOARD_SIZE - in_Square.getRowLocation();
	}
	*/
	
	/*--NOTE--
	 * Quadrants have not proven useful
	//************************************************
	//	Method: setQuadrant()
	//
	//	Purpose: Divides squares into quadrants
	//			 based on board size
	//************************************************
	public void setSqrQuadrant(int in_iRow, int in_iCol)
	{
		int iRowLimit = m_BOARD_SIZE / 2;
		int iColLimit = m_BOARD_SIZE / 2;
		
		if(in_iRow < iRowLimit)
		{
			if(in_iCol < iColLimit)
			{
				m_playingBoard[in_iRow][in_iCol].setQuadrant(1);
			}
			else
			{
				m_playingBoard[in_iRow][in_iCol].setQuadrant(2);
			}
		}
		else
		{
			if(in_iCol < iColLimit)
			{
				m_playingBoard[in_iRow][in_iCol].setQuadrant(3);
			}
			else
			{
				m_playingBoard[in_iRow][in_iCol].setQuadrant(4);
			}
		}
	}
	*/
	
	//************************************************
	//	Method: addKnight()
	//
	//	Purpose: Returns the number of rows on the board.
	//************************************************
	public void addKnight(Knight in_ktKnight)
	{
		m_currentKnight = in_ktKnight;
	}
	
	//************************************************
	//	Method: getBoardSize()
	//
	//	Purpose: Returns the number of rows on the board.
	//************************************************
	public int getBoardSize()
	{
		return m_BOARD_SIZE;
	}
	
	//************************************************
	//	Method: getSquareAt()
	//
	//	Purpose: Returns the square object at a
	//			 specified row and column
	//************************************************
	public Square getSquareAt(int in_iRow, int in_iCol)
	{
		return m_playingBoard[in_iRow][in_iCol];
	}
	
	//************************************************
	//	Method: setSquareVisited(int, int)
	//
	//	Purpose: Sets the boolean value true on a square
	//			 to indicate that it has been visited.
	//************************************************
	public void setSquareVisited(int in_iRow, int in_Col)
	{
		m_playingBoard[in_iRow][in_Col].setVisited(true);
	}
	
	//************************************************
	//	Method: isSquareVisited(int, int)
	//
	//	Purpose: Returns true if the square specified
	//			 by the parameters has been visited.
	//************************************************
	public boolean isSquareVisited(int in_iRow, int in_Col)
	{
		return m_playingBoard[in_iRow][in_Col].isVisited();
	}
	
	//************************************************
	//	Method: setSquareAccessibility(int, int, int)
	//
	//	Purpose: Sets the accessibility level of
	//			 a square located at iRow and iCol
	//************************************************
	public void setSquareAccessibility(int in_iRow, int in_iCol, int in_iAccessibility)
	{
		m_playingBoard[in_iRow][in_iCol].setAccessibility(in_iAccessibility);
	}
	
	//************************************************
	//	Method: getSquareAccessibility(int, int)
	//
	//	Purpose: Returns the accessibility level of
	//			 a square located at the row and column
	//			 specified by the parameters
	//************************************************
	public int getSquareAccessibility(int in_iRow, int in_iCol)
	{
		return m_playingBoard[in_iRow][in_iCol].getAccessibility();
	}
	
	//************************************************
	//	Method: decrSquareAccessibility(int, int)
	//
	//	Purpose: Decreases the accessibility of the
	//			 square by 1 at the row and column
	//			 specified by the parameters.
	//************************************************
	public void decrSquareAccessibility(int in_iRow, int in_iCol)
	{
		m_playingBoard[in_iRow][in_iCol].decrAccessibility();
	}
	
	//************************************************
	//	Method: setSquareMoveNumber(int, int, int)
	//
	//	Purpose: Sets a square to the knight's current
	//			 move counter value at the location
	//			 specified by the parameters.
	//************************************************
	public void setSquareMoveNumber(int in_iRow, int in_iCol, int in_iMoveCounter)
	{
		m_playingBoard[in_iRow][in_iCol].setMoveNumber(in_iMoveCounter);
	}
	
	//************************************************
	//	Method: getSquareMoveNumber(int, int)
	//
	//	Purpose: Returns the move counter value at the
	//			 location specified by the parameters.
	//************************************************
	public int getSquareMoveNumber(int in_iRow, int in_iCol)
	{
		return m_playingBoard[in_iRow][in_iCol].getMoveNumber();
	}

	//************************************************
	//	Method: markBoardSquare(int, int, int)
	//
	//	Purpose: Marks the board square located at the
	//			 row and column supplied by the parameters
	//			 with the number of the knight's
	//			 move counter variable, and sets the
	//			 visited status to true.
	//************************************************
	public void markBoardSquare(int in_iCurRow, int in_iCurCol, int in_iMoveCounter)
	{				
		setSquareVisited(in_iCurRow, in_iCurCol);
		setSquareMoveNumber(in_iCurRow, in_iCurCol, in_iMoveCounter);
	}
	
	//************************************************
	//	Method: showGameBoard()
	//
	//	Purpose: Prints the game board after all
	//			 possible moves have been exhausted.
	//************************************************
	public void showGameBoard()
	{
		for(int iRow = 0; iRow < m_BOARD_SIZE; iRow++)
		{
			for(int iCol = 0; iCol < m_BOARD_SIZE; iCol++)
			{
				System.out.printf("%3d", m_playingBoard[iRow][iCol].getMoveNumber());
				if(iCol == m_BOARD_SIZE - 1)
				{
					System.out.println();
				}
			}
		}
	}
	
	//************************************************
	//	Method: showHeuristics()
	//
	//	Purpose: Prints the accessibility heuristics
	//************************************************
	public void showHeuristics()
	{
		for(int iRow = 0; iRow < m_BOARD_SIZE; iRow++)
		{
			for(int iCol = 0; iCol < m_BOARD_SIZE; iCol++)
			{
				System.out.printf("%3d", getSquareAccessibility(iRow, iCol));
				if(iCol == m_BOARD_SIZE - 1)
				{
					System.out.println();
				}
			}
		}
	}
	
	//************************************************
	//	Method: createHeuristics(Knight)
	//
	//	Purpose: Uses a dummy Knight object to play
	//			through all possible moves for every
	//			square on the game board.
	//			The number of possible moves from each
	//			square is recorded in the
	//			m_aiAccessibility array.
	//************************************************
	private void createHeuristics()
	{
		int iLinkCount = 0,			// Stores the number of linking squares for a particular square
			iTestRow,				// Knight's current row plus a move number value
			iTestCol;				// Knight's current column plus a move number value
		
		
		// Adds a move number value to the knight's current row and tests whether it is a move candidate.
		// If the location is on the game board, it increments the link count
		for(int iRow = 0; iRow < m_BOARD_SIZE; iRow++)
		{
			for(int iCol = 0; iCol < m_BOARD_SIZE; iCol++)
			{
				for(int iMoveNum = 0; iMoveNum < Knight.NUM_ALLOWED_MOVES; iMoveNum++)
				{
					iTestRow = iRow + m_currentKnight.getVerticalMoveValue(iMoveNum);
					iTestCol = iCol + m_currentKnight.getHorizontalMoveValue(iMoveNum);
					
					 if(checkSquareExistsAndIsUnVisted(iTestRow, iTestCol) == true)
					 {
						 iLinkCount++;
					 }
				}
				
				// Set accessibility and reset link count for next column
				setSquareAccessibility(iRow, iCol, iLinkCount);
				iLinkCount = 0;
			}
		}
	}
	
	//************************************************
	//	Method: checkSquareExistsAndIsUnVisted(int, int)
	//
	//	Purpose: Uses a row, column and current instance
	//			 of the chess board to determine if the
	//			 row and column are within the bounds
	//			 of the chess board and has not been
	//			 visited.  Returns true, if the space is
	//		 	 valid, otherwise returns false.
	//************************************************
	public boolean checkSquareExistsAndIsUnVisted(int in_iTestRow, int in_iTestCol)
	{
		if((in_iTestRow >= 0 && in_iTestRow < m_BOARD_SIZE) && (in_iTestCol >= 0 && in_iTestCol < m_BOARD_SIZE))
		{
			if(isSquareVisited(in_iTestRow, in_iTestCol) == false)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	//************************************************
	//	Method: lowerAccessibility(Knight)
	//
	//	Purpose: Checks all the squares that are
	//			 accessible from the current square
	//			 and reduces their accessibility by 1.
	//************************************************
	public void lowerAccessibility()
	{
		int[] 	aiPossibleMoves;
		int		iNumOfPossibleMoves,
				iCurRow			= m_currentKnight.getCurrentRow(),
				iCurCol			= m_currentKnight.getCurrentCol(),
				iChangeRow,
				iChangeCol;
		
		// Get the number of possible moves
		iNumOfPossibleMoves = m_currentKnight.findNumOfPossibleMoves(this);
		
		// Create an array with the size of the number of possible moves
		aiPossibleMoves = new int[iNumOfPossibleMoves];
		
		// Get the move numbers of the possible moves
		aiPossibleMoves = m_currentKnight.findPossibleMoves(this, iNumOfPossibleMoves);
		
		for(int iMoveNum = 0; iMoveNum < aiPossibleMoves.length; iMoveNum++)
		{
			iChangeRow = iCurRow + m_currentKnight.getVerticalMoveValue(aiPossibleMoves[iMoveNum]);
			iChangeCol = iCurCol + m_currentKnight.getHorizontalMoveValue(aiPossibleMoves[iMoveNum]);
			
			if(isSquareVisited(iChangeRow, iChangeCol) == false)
			{
				decrSquareAccessibility(iChangeRow, iChangeCol);
			}
		}
	}
}
