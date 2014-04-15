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
	
	private int posX;
	private int posY;
	private Piece piece;
	private int type;
	private boolean taken;
	
	/**
	 * Creates a new move
	 * @param pos position to move to
	 * @param piece piece to move
	 */
	public Move(Position pos, Piece piece, boolean taken) {
		this(pos.getPosX(), pos.getPosY(), piece, taken);
	}
	
	/**
	 * Creates a new move
	 * @param posX x position to move to
	 * @param posY y position to move to
	 * @param piece piece to move
	 */
	public Move(int posX, int posY, Piece piece, boolean taken) {
		this.posX = posX;
		this.posY = posY;
		this.piece = piece;
		this.taken = taken;
	}
	
	/**
	 * Creates a new move
	 * @param posX x position to move to
	 * @param posY y position to move to
	 * @param piece piece to move
	 * @param type special type of move (like castling, en passant)
	 */
	public Move(int posX, int posY, Piece piece, int type, boolean taken) {
		this.posX = posX;
		this.posY = posY;
		this.piece = piece;
		this.type = type;
		this.taken = taken;
	}
	
	/**
	 * Creates a new move
	 * @param pos position to move to
	 * @param piece piece to move
	 * @param type special type of move (like castling, en passant)
	 */
	public Move(Position pos, Piece piece, int type, boolean taken) {
		this(pos.getPosX(), pos.getPosY(), piece, type, taken);
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
	
	public void setPosition(Position p) {
		posX = p.getPosX();
		posY = p.getPosY();
	}
	
	public Position getPosition() {
		return new Position(posX, posY);
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
		
		if (taken)
			str += "x";
		
		str += (new Position(posX, posY).toString());
		
		return str;
	}
}
