package de.thomas.chess.gui;

import javax.swing.JFrame;

import de.thomas.chess.logic.GameLogic;


/**
 * GUI frame of the chess game
 * @author Thomas
 *
 */
@SuppressWarnings("serial")
public class ChessFrame extends JFrame {
	GameLogic logic;

	public ChessFrame(GameLogic logic) {
		
		ChessPanel gamePanel = new ChessPanel(logic);
		add(gamePanel);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(515, 540);
        setLocationRelativeTo(null);
        setTitle("Chess");

        setResizable(false);
        setVisible(true);
        
    }
}
