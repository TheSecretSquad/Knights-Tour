//****************************************************
//File: GUISquare
//
//Purpose: JPanel that represents a square on a chess board.
//		   Panel contains a label whose text can be
//		   programmatically changed.
//
//Written By: Peter DiSalvo 
//****************************************************

package thesecretsquad.Chess.KnightsTour;

import java.awt.*;
import javax.swing.*;

public class GUISquare extends JPanel
{
	private JLabel m_label;		// Label in panel
	
	//****************************************************
	// Method: GUISquare
	//
	// Purpose: Constructor.  Initializes square.
	//****************************************************
	public GUISquare()
	{
		// Create JLabel object
		m_label = new JLabel();
		
		// Set foreground color to white (text color will be white)
		m_label.setForeground(Color.white);
		
		// Add label to square panel
		add(m_label);
	}
	
	//****************************************************
	// Method: setText
	//
	// Purpose: Sets the square's label text with the
	//			String parameter.
	//****************************************************
	public void setText(String in_szText)
	{
		m_label.setText(in_szText);
	}
	
	//****************************************************
	// Method: resetText
	//
	// Purpose: Sets the square's label text to null, which
	//			clears the label.
	//****************************************************
	public void resetText()
	{
		m_label.setText(null);
	}
}
