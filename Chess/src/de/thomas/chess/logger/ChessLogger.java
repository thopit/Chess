package de.thomas.chess.logger;

import java.io.PrintStream;

import de.thomas.chess.logic.GameChangedListener;
import de.thomas.chess.logic.GameLogic;
import de.thomas.chess.logic.GameMessage;
import de.thomas.chess.logic.Move;

/**
 * Logs moves and prints them
 * @author Thomas Opitz
 *
 */
public class ChessLogger implements GameChangedListener {
	private boolean active;
	private PrintStream stream;
	
	public ChessLogger(GameLogic logic, PrintStream stream) {
		logic.addGameChangedListener(this);
		this.stream = stream;
		this.active = true;
	}

	@Override
	public void handleMessage(GameMessage message) {
		if (! active) return;
		
		switch (message.getType()) {
		case GameMessage.PIECE_MOVED:
			Move move = (Move) message.getContent();
			
			stream.println(move);
		}
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

}
