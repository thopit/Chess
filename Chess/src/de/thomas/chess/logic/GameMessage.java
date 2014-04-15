package de.thomas.chess.logic;

/**
 * Message containing information of change in game
 * @author Thomas Opitz
 *
 */
public class GameMessage {
	public static final int PIECE_MOVED = 1;
	
	private int type;
	private Object content;
	
	public GameMessage(int type, Object content) {
		this.type = type;
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public Object getContent() {
		return content;
	}

}
