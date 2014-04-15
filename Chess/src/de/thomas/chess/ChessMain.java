package de.thomas.chess;


import de.thomas.chess.ai.T_AI;
import de.thomas.chess.gui.ChessFrame;
import de.thomas.chess.logger.ChessLogger;
import de.thomas.chess.logic.GameLogic;

/**
 * Main class of the chess game
 * @author Thomas Opitz
 *
 */
public class ChessMain {
	public ChessMain() {
		GameLogic logic = new GameLogic();
		new ChessFrame(logic);
		new ChessLogger(logic);
		new T_AI(logic, 2);
	}
	
	public static void main(String[] args) {
		new ChessMain();
	}
}
