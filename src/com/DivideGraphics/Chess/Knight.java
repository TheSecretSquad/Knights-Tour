//****************************************************
//File: Knight
//
//Purpose: Knight playing piece.
//
//Written By: Peter DiSalvo 
//****************************************************


package com.DivideGraphics.Chess;

public class Knight
{
	private int[] 	m_horizontalMovesAvail;			// Horizontal move values that are available to the knight
	private int[] 	m_verticalMovesAvail;			// Vertical move values that are available to the knight
	private int 	m_currentRow;					// Current row location of the knight
	private int 	m_currentCol;					// Current column location of the knight
	private int		m_previousRow;					// Row the knight was previously on before moving
	private int		m_previousCol;					// Column the knight was previously on before moving
	private int 	m_moveCounter;					// Keeps track of how many moves the knight has made (1 - 64)
	public static final int NUM_ALLOWED_MOVES = 8;	// Number of moves the knight is allowed to make
	
	//************************************************
	//	Method: Knight()
	//
	//	Purpose: Constructor
	//************************************************
	public Knight()
	{		
		m_horizontalMovesAvail	= new int[NUM_ALLOWED_MOVES];
		m_verticalMovesAvail 	= new int[NUM_ALLOWED_MOVES];
		m_moveCounter	= 1;
		setCurrentRow(0);
		setCurrentCol(0);
		setPreviousRow(0);
		setPreviousCol(0);
		fillMoves();
	}
	
	//************************************************
	//	Method: Knight()
	//
	//	Purpose: Constructor
	//************************************************
	public Knight(int in_iStartRow, int in_iStartCol)
	{
		m_horizontalMovesAvail	= new int[NUM_ALLOWED_MOVES];
		m_verticalMovesAvail	= new int [NUM_ALLOWED_MOVES];
		m_moveCounter	= 1;
		setCurrentRow(in_iStartRow);
		setCurrentCol(in_iStartCol);
		setPreviousRow(in_iStartRow);
		setPreviousCol(in_iStartCol);
		fillMoves();
	}
	
	//************************************************
	//	Method: move()
	//
	//	Purpose: Makes a move with the knight piece.
	//			 Sets the previous row and column, and
	//			 increments the move counter.
	//************************************************
	public void move(int iMoveNumber)
	{
		if(iMoveNumber < 0 || iMoveNumber > NUM_ALLOWED_MOVES - 1)
		{
			System.out.println("ERROR: Invalid move: " + iMoveNumber);
			return;
		}
		else
		{			
			setPreviousRow(m_currentRow);
			setPreviousCol(m_currentCol);
			setCurrentRow(m_currentRow + m_verticalMovesAvail[iMoveNumber]);
			setCurrentCol(m_currentCol + m_horizontalMovesAvail[iMoveNumber]);
			incMoveCounter();
		}
	}
	
	//************************************************
	//	Method: findNumOfPossibleMoves(ChessBoard)
	//
	//	Purpose: Finds the number of possible moves on
	//			 the game board from the starting row
	//			 and column supplied by the parameters.
	//************************************************
	public int findNumOfPossibleMoves(ChessBoard in_cbCurrentBoard)
	{
		int		iPossibleMoveCounter	= 0,				// Counts the number of possible moves
				iStartRow				= getCurrentRow(),
				iStartCol				= getCurrentCol(),
				iTestRow,									// Knight's current row plus a move number value
				iTestCol;									// Knight's current column plus a move number value
		
		// Adds a move number value to the knight's current row and tests whether it is a move candidate.
		// if the location is on the game board and unvisited, the possible move counter is incremented.
		for(int iMoveNum = 0; iMoveNum < Knight.NUM_ALLOWED_MOVES; iMoveNum++)
		{
			iTestRow = iStartRow + getVerticalMoveValue(iMoveNum);
			iTestCol = iStartCol + getHorizontalMoveValue(iMoveNum);
			
			if(in_cbCurrentBoard.checkSquareExistsAndIsUnVisted(iTestRow, iTestCol) == true)
			{
				iPossibleMoveCounter++;
			}
		}
		
		return iPossibleMoveCounter;
	}
	
	//************************************************
	//	Method: findPossibleMoves(ChessBoard, int)
	//
	//	Purpose: Finds the moves on the game board from
	//			 the knight's current position, and returns
	//			 an array of the possible moves.
	//************************************************
	public int[] findPossibleMoves(ChessBoard in_cbCurrentBoard, int iNumPossibleMoves)
	{
		int[] 	aiPossibleMoves			= new int[iNumPossibleMoves];			// Holds up to 8 possible move values
		int 	iStoreCurRow 			= getCurrentRow(),						// Stores the knight's current row
				iStoreCurCol 			= getCurrentCol(),						// Stores the knight's current column
				iTestRow,														// Knight's current row plus a move number value
				iTestCol,														// Knight's current column plus a move number value
				iGoodMove = 0;													// Counts the number of actual moves
		
		// Adds a move number value to the knight's current row and tests whether it is a move candidate.
		// if the location is on the game board and unvisited, it is added to the array of possible moves.
		for(int iMoveNum = 0; iMoveNum < Knight.NUM_ALLOWED_MOVES; iMoveNum++)
		{
			iTestRow = iStoreCurRow + getVerticalMoveValue(iMoveNum);
			iTestCol = iStoreCurCol + getHorizontalMoveValue(iMoveNum);
			
			 if(in_cbCurrentBoard.checkSquareExistsAndIsUnVisted(iTestRow, iTestCol) == true)
			 {
					 aiPossibleMoves[iGoodMove++] = iMoveNum;
			 }
		}
		
		return aiPossibleMoves;
	}

	//************************************************
	//	Method: findBestMove(ChessBoard, int[])
	//
	//	Purpose: Finds the moves on the game board from
	//			 the knight's current position, and returns
	//			 an array of the possible moves.
	//************************************************
	public int findBestMove(ChessBoard in_cbCurrentBoard, int[] aiPossibleMoves)
	{
		int 	iStoreCurRow 	= getCurrentRow(),		// Stores the knight's current row
				iStoreCurCol 	= getCurrentCol(),		// Stores the knight's current column
				iTestRow,								// Knight's current row plus a move number value
				iTestCol,								// Knight's current column plus a move number value
				iLowestAccessibility,
				iTestAccessibility,
				iMoveNumWithLowest;
		
		// If the array of possible moves is greater than one, then there are at least
		// two moves to compare accessibility with.
		if(aiPossibleMoves.length > 1)
		{
			// Give iLowestAccessibility a starting value to compare the rest to
			iTestRow = iStoreCurRow + getVerticalMoveValue(aiPossibleMoves[0]);
			iTestCol = iStoreCurCol + getHorizontalMoveValue(aiPossibleMoves[0]);
			iLowestAccessibility = in_cbCurrentBoard.getSquareAccessibility(iTestRow, iTestCol);
			iMoveNumWithLowest = aiPossibleMoves[0];
			
			// Test each move in the array against the lowest value
			/* Change 11/12/2011: iMoveNum in loop header initial value changed from 0 to 1
			 * Reason: The initial lowest value is set to move 0, if iMoveNum was left at 0,
			 * 		   then it is unnecessarily comparing it in the loop*/
			for(int iMoveNum = 1; iMoveNum < aiPossibleMoves.length; iMoveNum++)
			{
				iTestRow = iStoreCurRow + getVerticalMoveValue(aiPossibleMoves[iMoveNum]);
				iTestCol = iStoreCurCol + getHorizontalMoveValue(aiPossibleMoves[iMoveNum]);

				iTestAccessibility = in_cbCurrentBoard.getSquareAccessibility(iTestRow, iTestCol);
				
				// If the tested value is lower than the current lowest value
				// store the accessibility value and store the move number with the
				// lowest value found in that array position

				if(iTestAccessibility < iLowestAccessibility || iLowestAccessibility < 1)
				{
					iLowestAccessibility = iTestAccessibility;
					iMoveNumWithLowest = aiPossibleMoves[iMoveNum];					
				}
			}
			
			return iMoveNumWithLowest;
		}
		// Return the first value in the array if the array length is 1, because there are
		// no other moves to compare with
		else
			return aiPossibleMoves[0];
	}
	
	//************************************************
	//	Method: setCurrentRow()
	//
	//	Purpose: Receives an int value and sets the
	//			 current row.
	//************************************************
	public void setCurrentRow(int in_iRow)
	{
		m_currentRow = in_iRow;
	}
	
	//************************************************
	//	Method: getCurrentRow()
	//
	//	Purpose: Returns the value of the current row.
	//************************************************
	public int getCurrentRow()
	{
		return m_currentRow;
	}
	
	//************************************************
	//	Method: setCurrentCol(int)
	//
	//	Purpose: Receives and int value and sets the
	//			 current column.
	//************************************************
	public void setCurrentCol(int in_iCol)
	{
		m_currentCol = in_iCol;
	}
	
	//************************************************
	//	Method: getCurrentCol()
	//
	//	Purpose: Returns the value of the current column.
	//************************************************
	public int getCurrentCol()
	{
		return m_currentCol;
	}
	
	//************************************************
	//	Method: setPreviousRow(int)
	//
	//	Purpose: Receives an int value and sets the
	//			 previous row.
	//************************************************
	public void setPreviousRow(int in_iRow)
	{
		m_previousRow = in_iRow;
	}
	
	//************************************************
	//	Method: getPreviousRow()
	//
	//	Purpose: Returns the value of the previous row.
	//************************************************
	public int getPreviousRow()
	{
		return m_previousRow;
	}
	
	//************************************************
	//	Method: setPreviousCol(int)
	//
	//	Purpose: Receives an int value and sets the
	//			 previous column.
	//************************************************
	public void setPreviousCol(int in_iCol)
	{
		m_previousCol = in_iCol;
	}
	
	//************************************************
	//	Method: getPreviousCol()
	//
	//	Purpose: Returns the value of the previous column.
	//************************************************
	public int getPreviousCol()
	{
		return m_previousCol;
	}
	
	//************************************************
	//	Method: incMoveCounter()
	//
	//	Purpose: Increments the move counter member
	//			 variable by 1.
	//************************************************
	public void incMoveCounter()
	{
		m_moveCounter++;
	}
	
	//************************************************
	//	Method: getMoveCounter()
	//
	//	Purpose: Returns the value of the move counter.
	//************************************************
	public int getMoveCounter()
	{
		return m_moveCounter;
	}
	
	//************************************************
	//	Method: getHorizontalMoveValue()
	//
	//	Purpose: Receives a move number and returns the
	//			 value of the horizontal movement.
	//************************************************
	public int getHorizontalMoveValue(int in_iMoveNumber)
	{
		return m_horizontalMovesAvail[in_iMoveNumber];
	}
	
	//************************************************
	//	Method: getVerticalMoveNumber()
	//
	//	Purpose: Receives a move number and returns the
	//			 value of the vertical movement.
	//************************************************
	public int getVerticalMoveValue(int in_iMoveNumber)
	{
		return m_verticalMovesAvail[in_iMoveNumber];
	}
	
	//************************************************
	//	Method: fillMoves()
	//
	//	Purpose: Maps the possible horizontal and
	//			 vertical moves of the knight.
	//************************************************
	private void fillMoves()
	{
		m_horizontalMovesAvail[0] = 2;	// R
		m_horizontalMovesAvail[1] = 1; 	// R
		m_horizontalMovesAvail[2] = -1;	// L
		m_horizontalMovesAvail[3] = -2;	// L
		m_horizontalMovesAvail[4] = -2;	// L
		m_horizontalMovesAvail[5] = -1;	// L
		m_horizontalMovesAvail[6] = 1;	// R
		m_horizontalMovesAvail[7] = 2;	// R
		
		m_verticalMovesAvail[0]	= -1; 	// U
		m_verticalMovesAvail[1] = -2; 	// U
		m_verticalMovesAvail[2]	= -2; 	// U
		m_verticalMovesAvail[3] = -1; 	// U
		m_verticalMovesAvail[4] = 1; 	// D
		m_verticalMovesAvail[5] = 2; 	// D
		m_verticalMovesAvail[6] = 2; 	// D
		m_verticalMovesAvail[7] = 1; 	// D
	}
}
