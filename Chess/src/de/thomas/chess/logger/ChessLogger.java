package de.thomas.chess.logger;

import de.thomas.chess.logic.GameChangedListener;
import de.thomas.chess.logic.GameLogic;
import de.thomas.chess.logic.GameMessage;
import de.thomas.chess.logic.Move;

public class ChessLogger implements GameChangedListener {
	private boolean active;
	
	public ChessLogger(GameLogic logic) {
		logic.addGameChangedListener(this);
		this.active = true;
	}

	@Override
	public void handleMessage(GameMessage message) {
		if (! active) return;
		
		switch (message.getType()) {
		case GameMessage.PIECE_MOVED:
			Move move = (Move) message.getContent();
			
			System.out.println(move);
		}
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

}
