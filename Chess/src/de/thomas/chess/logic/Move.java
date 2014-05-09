package de.thomas.chess.logic;

/**
 * Class representing the move of a piece
 * @author Thomas Opitz
 *
 */
public class Move {
	//TODO add stuff for all different kinds of moves (promotion) -> add new constructors
	
	public static final int NORMAL = 0;
	public static final int SHORT_CASTLING = 1;
	public static final int LONG_CASTLING = 2;
	public static final int PROMOTION = 3;
	

	private Position oldPos;
	private Position pos;
	private Piece piece;
	private int type;
	private boolean taken;
	
	/**
	 * Creates a new move
	 * @param pos position to move to
	 * @param piece piece to move
	 */
	public Move(Position oldPos, Position pos, Piece piece, boolean taken) {
		this.oldPos = oldPos;
		this.pos = pos;
		this.piece = piece;
		this.taken = taken;
	}
	
	/**
	 * Creates a new move
	 * @param pos position to move to
	 * @param piece piece to move
	 * @param type special type of move (like castling, en passant)
	 */
	public Move(Position oldPos, Position pos, Piece piece, int type, boolean taken) {
		this(oldPos, pos, piece, taken);
		this.type = type;
	}
	
	public void setPosition(Position pos) {
		this.pos = pos;
	}
	
	public Position getPosition() {
		return pos;
	}

	public Piece getPiece() {
		return piece;
	}

	public int getType() {
		return type;
	}
	
	@Override
	public String toString() {
		switch (type) {
		case SHORT_CASTLING:
			return "0-0";
		case LONG_CASTLING:
			return "0-0-0";
		}
		
		String str = "";
		str += piece.getNotationName();
		
		str += oldPos;
		
		if (taken)
			str += "x";
		else
			str += "-";
		
		str += pos;
		
		return str;
	}
}
