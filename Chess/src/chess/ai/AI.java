package chess.ai;

import chess.logic.GameChangedListener;
import chess.logic.GameLogic;
import chess.logic.GameMessage;

/**
 * Abstract class describing an AI
 * @author Thomas
 *
 */
public abstract class AI implements GameChangedListener {
	protected GameLogic logic;
	protected int player;

	public AI(GameLogic logic, int player) {
		this.logic = logic;
		logic.addGameChangedListener(this);
		this.player = player;
	}
	
	
	@Override
	public void handleMessage(GameMessage message) {
		switch (message.getType()) {
		case GameMessage.PIECE_MOVED: handlePieceMovedMessage(message); break;
		}
	}

	abstract void handlePieceMovedMessage(GameMessage message);

}
