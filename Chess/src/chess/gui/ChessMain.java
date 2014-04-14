package chess.gui;


import chess.ai.*;
import chess.logger.ChessLogger;
import chess.logic.GameLogic;

/**
 * Main class of the chess game
 * @author Thomas
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
