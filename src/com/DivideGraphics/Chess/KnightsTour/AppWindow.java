//****************************************************
//File: AppWindow
//
//Purpose: Application window for Knight's Tour
//
//Written By: Peter DiSalvo 
//****************************************************

package com.DivideGraphics.Chess.KnightsTour;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AppWindow extends JFrame
{
	private Tour		m_knightsTour;					// Tour object
	private int			m_boardSize;					// Board dimension
	private GUISquare	m_guiSquares[][];				// Array of JPanels representing squares
	private boolean		m_isTourRunning 		= false;	// Flag: true if tour is running, false if tour is not running
	private boolean		m_isTourFinished 	= false;	// Flag: true if tour completed run, false if tour did not finish
	private boolean		m_useNewGameParams 	= true;		// Flag: true if user wants new game parameters, false if user wants the same as previous game
	private Timer		m_timer;						// Times the movement of the knight
	private final int	m_MAX_BOARD_SIZE 	= 100;		// Maximum board size
	
	//****************************************************
	// Method: AppWindow
	//
	// Purpose: Constructor.  Set default close operation
	//			for the window and launches a new game.
	//****************************************************
	public AppWindow()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		newGame();
		createTimer();
	}
	
	//****************************************************
	// Method: newGame
	//
	// Purpose: Sets parameters for a new knight's tour game.
	//****************************************************
	private void newGame()
	{
		// If the board size is greater than zero, then the board
		// must be cleared before starting again.
		if(m_boardSize > 0)
		{
			// Remove current squares from the board
			removeSquares();
			
			// Repaint the window
			repaint();
		}

		// If user selected to enter new game parameters
		// prompt the user for the new board size and set
		// the board size member
		if(m_useNewGameParams == true)
		{
			// Prompt user for board size and set member
			m_boardSize = promptBoardSize();
		}		
		
		// If user wants new game parameter, resize window
		// otherwise leave window the same size
		if(m_useNewGameParams == true)
		{
			// Set size of window
			setSize(m_boardSize * 35, m_boardSize * 35);
		}
		
		
		// Set grid layout, rows and columns are equal to the board size,
		// with a 1 pixel gap between them (board size divided by board size = 1) 
		setLayout(new GridLayout(m_boardSize, m_boardSize, m_boardSize / m_boardSize, m_boardSize / m_boardSize));
		
		// Create new tour object with board size
		m_knightsTour = new Tour(m_boardSize);
		
		// Draw the squares in the window
		drawSquares();
	}
	
	//****************************************************
	// Method: drawSquares
	//
	// Purpose: Draws the squares on the screen.
	//****************************************************
	private void drawSquares()
	{	
		// Create new array with size of the board
		m_guiSquares = new GUISquare[m_boardSize][m_boardSize];
		
		// Nested loop iterates through all squares
		for(int iRow = 0; iRow < m_guiSquares.length; iRow++)
		{
			for (int iCol = 0; iCol < m_guiSquares.length; iCol++)
			{
				// Create new GUISquare object
				m_guiSquares[iRow][iCol] = new GUISquare();
				
				// Add mouse GUISquare in the array
				m_guiSquares[iRow][iCol].addMouseListener(new SquareClickHandler());

				// Alternate square color
				setCorrectSquareColor(iRow, iCol);
				
				// Add square to Frame
				add(m_guiSquares[iRow][iCol]);
			}
		}
		
		// Cause container to lay out its subcomponents again.
		// It should be invoked when this container's subcomponents are modified
		// (added to or removed from the container, or layout-related information changed)
		// after the container has been displayed.
		validate();
	}
	
	//****************************************************
	// Method: setCorrectSquareColor
	//
	// Purpose: Determines the square color based on its
	//			position on the board.
	//****************************************************
	private void setCorrectSquareColor(int in_iRow, int in_iCol)
	{
		if((in_iRow + in_iCol) % 2 == 0)
		{
			m_guiSquares[in_iRow][in_iCol].setBackground(Color.lightGray);
		}
		else
		{
			m_guiSquares[in_iRow][in_iCol].setBackground(Color.darkGray);
		}
	}
	
	//****************************************************
	// Method: removeSquares
	//
	// Purpose: Removes squares from their container
	//****************************************************
	private void removeSquares()
	{
		// Nested loop iterates through all squares
		for(int iRow = 0; iRow < m_guiSquares.length; iRow++)
		{
			for (int iCol = 0; iCol < m_guiSquares.length; iCol++)
			{
				// Call remove to remove square from Frame
				remove(m_guiSquares[iRow][iCol]);
			}
		}
	}
	
	//****************************************************
	// Method: promptBoardSize
	//
	// Purpose: Prompts user to enter desired board size.
	//			Validates input and returns a valid number.
	//****************************************************
	private int promptBoardSize()
	{
		String 	szBoardSize;			// String value to hold user input
		int 	iBoardSize = 0;			// Integer value to be returned
		boolean bInvalidString = true;	// True indicates that the input is invalid
		
		// Do the following while user input is invalid
		do
		{
			// Prompt user for input and store in String variable
			szBoardSize = JOptionPane.showInputDialog("Enter an integer to set the number of rows and columns on the board:");
			
			// If user input is a null value, user clicked cancel or closed the window,
			// so exit the program.
			if(szBoardSize == null)
			{
				System.exit(0);
			}
			else
			{
				try
				{
					// Test if string is empty and assign boolean value to bInvalidString
					bInvalidString = szBoardSize.isEmpty();

					// parseInt throws NumberFormatException if the String is empty or equal to ""
					iBoardSize = Integer.parseInt(szBoardSize);
					
					// If parseInt did not throw an exception, program will continue here.
					// If board size is zero, string is still invalid, so display error
					if(iBoardSize <= 0 || iBoardSize >= m_MAX_BOARD_SIZE)
					{
						String szMessage;
						
						bInvalidString = true;
						
						szMessage = iBoardSize <= 0 ? "You must enter a valid integer value." : String.format("Board size can not exceed %d", m_MAX_BOARD_SIZE);
						
						JOptionPane.showMessageDialog(this, szMessage, "Invalid Number", JOptionPane.WARNING_MESSAGE);
					}
				}
				// Handle NumberFormatException thrown from parseInt
				catch(NumberFormatException nfeEx)
				{
					// Display error message
					JOptionPane.showMessageDialog(this, "You must enter a valid integer value.", "Invalid Number", JOptionPane.WARNING_MESSAGE);
				}
			}
				
		}
		while(bInvalidString == true);
		
		// At this point the input is valid, so set board size and return
		iBoardSize = Integer.parseInt(szBoardSize);
			
		return iBoardSize;
	}
	
	//****************************************************
	// Method: createTimer
	//
	// Purpose: Create action listener that performs
	//			a knight's move at an interval.
	//****************************************************
	private void createTimer()
	{
		int delay = 50;		// Delay in milliseconds
		
		// Set flag indicating tour is finished to false.
		m_isTourFinished = false;	
		
		// Create anonymous action listener variable and
		// override it's actionPerformed method to call
		// moveKnight
		ActionListener taskPerformer = new ActionListener()
		{
		    public void actionPerformed(ActionEvent evt)
		    {
		    	// Set correct square color
		    	setCorrectSquareColor(m_knightsTour.getKnight().getCurrentRow(),
		    						  m_knightsTour.getKnight().getCurrentCol());
		    	
		    	// Move the knight
		    	moveKnight();
		    	
		    	// Set flash color
		    	flashColor();
		    	
		    }
		};
		
		// Set class timer member to a timer instance
		// and pass delay, and taskPerformer as the action listener
		m_timer = new Timer(delay, taskPerformer);
	}
	
	//****************************************************
	// Method: flashColor
	//
	// Purpose: Changes the color of the current square.
	//****************************************************
	private void flashColor()
	{
		int			iCurrentRow = m_knightsTour.getKnight().getCurrentRow();
		int			iCurrentCol = m_knightsTour.getKnight().getCurrentCol();
		GUISquare	gsqCurrentSquare = m_guiSquares[iCurrentRow][iCurrentCol];
		Color 		flashColor = Color.orange;
		
		gsqCurrentSquare.setBackground(flashColor);
	}
	
	//****************************************************
	// Method: startTimer
	//
	// Purpose: Starts the timer
	//****************************************************
	private void startTimer()
	{
		m_timer.start();
	}
	
	//****************************************************
	// Method: stopTimer
	//
	// Purpose: Stops the currently running timer
	//****************************************************
	private void stopTimer()
	{
		m_timer.stop();
	}
	
	//****************************************************
	// Method: moveKnight
	//
	// Purpose: Moves the knight, and sets the text on the
	//			label of the current square
	//****************************************************
	private void moveKnight()
	{
		GUISquare	gsqCurrentSquare;		// Stores the current square (position of knight)
		int			iCurrentRow;			// Current row position of knight
		int			iCurrentCol;			// Current column position of knight
		
		// Get current row and column of knight
		iCurrentRow = m_knightsTour.getKnight().getCurrentRow();
		iCurrentCol = m_knightsTour.getKnight().getCurrentCol();
		
		// Set current square
		gsqCurrentSquare = m_guiSquares[iCurrentRow][iCurrentCol];
	
		
		// Set the text label on the current square
		gsqCurrentSquare.setText(Integer.toString(m_knightsTour.getKnight().getMoveCounter()));

		// Move the knight
		m_knightsTour.move();
		
		// If the knight has no more moves,
		// stop the timer, set running flag to false
		// and tour finished flag to true.
		if(m_knightsTour.hasMove() == false)
		{
			m_timer.stop();
			m_isTourRunning = false;
			m_isTourFinished = true;
		}
	}
	
	//****************************************************
	// Method: askNewParam
	//
	// Purpose: Asks the user if they want new parameters
	//			for the next game.
	//****************************************************
	public void askNewParam()
	{
		int 	iNewParamAnswer;	// Integer value of dialog response (Yes = 0, No = 1, Cancel = 3)

		// Store response from dialog
		iNewParamAnswer = JOptionPane.showConfirmDialog(this, "Do you want to use the same board?", "Paused", JOptionPane.YES_NO_CANCEL_OPTION);
		
		// If yes, user does not get new game parameters
		if(iNewParamAnswer == 0)
		{
			m_useNewGameParams = false;
		}
		// If no, user gets new game parameters
		else if(iNewParamAnswer == 1)
		{
			m_useNewGameParams = true;
		}
		// If cancel, program exits
		else
		{
			System.exit(0);
		}
	}
	
	//****************************************************
	// Method: askNewGame
	//
	// Purpose: Asks the user if they want a new game
	//			this method is called if the user clicks on
	//			a square while the tour is running.
	//****************************************************
	public void tourPaused()
	{
		int iNewGameAnswer;		// Integer value of dialog response (Yes = 0, No = 1, Cancel = 3)
		
		// Stop the time, which pauses the tour
		stopTimer();
		
		// Set tour running flag to false
		m_isTourRunning = false;
		
		// Store response from dialog
		iNewGameAnswer = JOptionPane.showConfirmDialog(this, "Start a new tour?", "Paused", JOptionPane.YES_NO_CANCEL_OPTION);

		// If yes
		if(iNewGameAnswer == 0)
		{
			// User is asked if they want new game parameters.
			// Preferences are stored in class member variables
			askNewParam();
			
			// Call resetTour on the knight, to clear current state
			m_knightsTour.resetTour();
			
			// Setup a new game
			newGame();
		}
		// If no, set running to true
		// and start the timer again
		else if(iNewGameAnswer == 1)
		{
			// If user does not want a new game
			// set tour running flag back to true 
			// and restart the timer.
			
			m_isTourRunning = true;
			startTimer();
		}
		// If cancel, program exits
		else
		{
			System.exit(0);
		}
	}
	

	//************************************************
	//	Method: showHeuristics()
	//
	//	Purpose: Displays the accessibility heuristics
	//************************************************
	public void showHeuristics()
	{
		for(int iRow = 0; iRow < m_boardSize; iRow++)
		{
			for(int iCol = 0; iCol < m_boardSize; iCol++)
			{
				m_guiSquares[iRow][iCol].setText(Integer.toString(m_knightsTour.getSquare(iRow, iCol).getAccessibility()));
			}
		}
	}
	
	/*--NOTE--
	 * Quadrants have not proven useful
	//************************************************
	//	Method: showQuadrants()
	//
	//	Purpose: Displays the accessibility heuristics
	//************************************************
	public void showQuadrants()
	{
		for(int iRow = 0; iRow < m_boardSize; iRow++)
		{
			for(int iCol = 0; iCol < m_boardSize; iCol++)
			{
				m_guiSquares[iRow][iCol].setText(Integer.toString(m_knightsTour.getSquare(iRow, iCol).getQuadrant()));
			}
		}
	}
	*/
	
	//****************************************************
	// Class: SquareClickHandler
	//
	// Purpose: Mouse listener for squares.  Listens for mouse clicks.
	//****************************************************
	class SquareClickHandler extends MouseAdapter
	{
		//****************************************************
		// Method: mouseClicked
		//
		// Purpose: Overrides MouseAdapter's mouseClicked
		// 			method to handle mouse click events
		//****************************************************
		@Override
		public void mouseClicked(MouseEvent meEvent)
		{			
			// If the tour is NOT running when the mouse is clicked
			if(m_isTourRunning == false)
			{
				// If the tour has finished, i.e., user has not interrupted the tour
				// with subsequent clicks and the tour is allowed to complete
				if(m_isTourFinished == true)
				{
					// Set tour finished back to false
					m_isTourFinished = false;

					// Ask the user if they want new game parameters
					askNewParam();

					// Setup a new game
					newGame();
				}
				
				// Nested loops iterate through all squares
				for(int iRow = 0; iRow < m_boardSize; iRow++)
				{
					for(int iCol = 0; iCol < m_boardSize; iCol++)
					{
						// Compare the square to the event source, to figure
						// out which square was clicked
						if(meEvent.getSource() == m_guiSquares[iRow][iCol])
						{
							// If the current square in the loop and the event source are equal, then
							// the current square in the loop is the event source.
							
							// Set the start position  
							m_knightsTour.setStartPosition(iRow, iCol);
							
							// Set tour running flag to true
							m_isTourRunning = true;
							
							// Start the timer
							startTimer();
							
							// No need to continue the loop at this point
							break;
						}
					}
				}
			}
			// If the tour IS running when the mouse is clicked
			else
			{				
				tourPaused();			
			}
		}
	}
}