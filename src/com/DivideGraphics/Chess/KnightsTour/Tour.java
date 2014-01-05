//****************************************************
//File: Tour
//
//Purpose: Uses a chess board and a knight to play
//		   the knight's tour game.
//
//Written By: Peter DiSalvo 
//****************************************************

package com.DivideGraphics.Chess.KnightsTour;

import com.DivideGraphics.Chess.ChessBoard;
import com.DivideGraphics.Chess.Knight;
import com.DivideGraphics.Chess.Square;

public class Tour
{
	private ChessBoard 	m_chessBoard;			// Chess board on which the knight moves
	private Knight		m_knightPiece;			// Knight that is moved
	private	boolean		m_foundMove = false;	// Flag: true if Knight has a move available, otherwise false
	
	//****************************************************
	// Method: Tour
	//
	// Purpose: Constructor. Receives chess board and
	//			knight objects to set the members.
	//****************************************************
	public Tour(ChessBoard in_cbChessboard, Knight in_ktKnight)
	{
		m_knightPiece = in_ktKnight;
		m_chessBoard = in_cbChessboard;
		m_chessBoard.addKnight(m_knightPiece);
	}
	
	//****************************************************
	// Method: Tour
	//
	// Purpose: Constructor.  Empty parameters.
	//			Creates knight, and passes knight to
	//			the chess board.
	//****************************************************
	public Tour()
	{
		m_knightPiece = new Knight();
		m_chessBoard = new ChessBoard(m_knightPiece);
	}
	
	//****************************************************
	// Method: Tour
	//
	// Purpose: Constructor.  Creates a knight and passes
	//			it to the chess board with the int parameter
	//			that sets the dimension of the chess board.
	//****************************************************
	public Tour(int in_iDimension)
	{
		m_knightPiece = new Knight();
		m_chessBoard = new ChessBoard(m_knightPiece, in_iDimension);
	}
	
	//****************************************************
	// Method: Tour
	//
	// Purpose: Constructor.  Creates a default chess board
	//			with dimension 8 x 8 and starts the knight
	//			at the square using the two int parameters.
	//****************************************************
	public Tour(int in_iStartRow, int in_iStartCol)
	{
		m_knightPiece = new Knight(in_iStartRow, in_iStartCol);
		m_chessBoard = new ChessBoard(m_knightPiece);
	}
	
	//****************************************************
	// Method: Tour
	//
	// Purpose: Constructor.  Creates knight and chessboard
	//			and sets knight start position, and the size
	//			of the chess board.
	//****************************************************
	public Tour(int in_iStartRow, int in_iStartCol, int in_iDimension)
	{
		m_knightPiece = new Knight(in_iStartRow, in_iStartCol);
		m_chessBoard = new ChessBoard(m_knightPiece, in_iDimension);
	}
	
	//****************************************************
	// Method: resetTour
	//
	// Purpose: Clears the knight and chess board members
	//			by pointing them to null.
	//****************************************************
	public void resetTour()
	{
		m_knightPiece 		= null;
		m_chessBoard 	= null;		
	}
	
	//****************************************************
	// Method: setStartPosition
	//
	// Purpose: Sets the start position of
	//			the knight on the chess board.
	//****************************************************
	public void setStartPosition(int in_iStartRow, int in_iStartCol)
	{
		m_knightPiece.setCurrentRow(in_iStartRow);
		m_knightPiece.setCurrentCol(in_iStartCol);
		m_knightPiece.setPreviousRow(in_iStartRow);
		m_knightPiece.setPreviousCol(in_iStartCol);
	}
	
	//****************************************************
	// Method: getChessBoard
	//
	// Purpose: Returns a reference to the chess board.
	//****************************************************
	public ChessBoard getChessBoard()
	{
		return m_chessBoard;
	}
	
	//****************************************************
	// Method: getKnight
	//
	// Purpose: Returns a reference to the knight player.
	//****************************************************
	public Knight getKnight()
	{
		return m_knightPiece;
	}
	
	//****************************************************
	// Method: getKnight
	//
	// Purpose: Returns a reference to the knight player.
	//****************************************************
	public Square getSquare(int in_iRow, int in_iCol)
	{
		return m_chessBoard.getSquareAt(in_iRow, in_iCol);
	}
	
	//****************************************************
	// Method: hasMove
	//
	// Purpose: Returns boolean class member flag, which
	//			is true if the knight has an available move,
	//			and false if the knight does not have a move.
	//			If the knight does not have a move, then
	//			the tour is obviously finished.
	//****************************************************
	public boolean hasMove()
	{
		return m_foundMove;
	}
	
	//****************************************************
	// Method: playGame
	//
	// Purpose: Plays the knight's tour game, and outputs
	//			the board to the console.
	//****************************************************
	public void playGame()
	{	
		do
		{
			move();
				
		} while(m_foundMove == true);
			
			System.out.println("Game Board");
			m_chessBoard.showGameBoard();
			
			System.out.printf("Number of moves: %d\n", m_knightPiece.getMoveCounter());
			
			System.out.println();
	}
	
	//****************************************************
	// Method: move
	//
	// Purpose: Moves the knight based on the underlying
	//			chess board heuristics.
	//****************************************************
	public void move()
	{
		int 		iNumPossibleMoves,
					iBestMove;
		int[]		aiPossibleMoves;
		
		m_foundMove = false;
		// Mark visited status and move number on square at current position
		m_chessBoard.markBoardSquare(m_knightPiece.getCurrentRow(),
									   m_knightPiece.getCurrentCol(),
									   m_knightPiece.getMoveCounter());
		
		// Lower accessibility values of surrounding squares
		m_chessBoard.lowerAccessibility();
		
		// Find the number of possible moves from the current position
		iNumPossibleMoves = m_knightPiece.findNumOfPossibleMoves(m_chessBoard);
		
		// If the number of possible moves is greater than zero, there there is at least one possible move
		if(iNumPossibleMoves > 0)
		{
			m_foundMove = true;
			
			// Find the possible moves from the current position and return into an array
			aiPossibleMoves = m_knightPiece.findPossibleMoves(m_chessBoard, iNumPossibleMoves);
			
			// Using the array of possible moves, find the best move based on the accessibility heuristic
			iBestMove = m_knightPiece.findBestMove(m_chessBoard, aiPossibleMoves);
			
			// Move the knight to the best move
			m_knightPiece.move(iBestMove);
		}
	}
}
