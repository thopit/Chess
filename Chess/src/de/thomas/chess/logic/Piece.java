package de.thomas.chess.logic;

/**
 * Class representing a piece
 * @author Thomas
 *
 */
public class Piece {
	private Type type;
	private Position pos;
	public String name;
	private String notationName;
	private int player;
	private int moves;
	private boolean enPassantTakeable = true;
	
	/**
	 * Creates a new piece
	 * @param type Type of the piece
	 * @param player Player of the piece
	 * @param posX Starting posX
	 * @param posY Starting posY
	 */
	public Piece(Type type, int player, int posX, int posY, String name) {
		this.type = type;
		
		switch (type) {
		case PAWN:
			notationName = "";
			break;
		case KNIGHT:
			notationName = "N";
			break;
		case BISHOP:
			notationName = "B";
			break;
		case ROOK:
			notationName = "R";
			break;
		case QUEEN:
			notationName = "Q";
			break;
		case KING:
			notationName = "K";
			break;
		}
		
		this.player = player;
		this.pos = new Position(posX, posY);
		this.name = name;
		moves = 0;
	}
	
	public Type getType() {
		return type;
	}

	//TODO probably name should also be changed?
	public void setType(Type type) {
		this.type = type;
		
		switch (type) {
		case PAWN:
			notationName = "";
			break;
		case KNIGHT:
			notationName = "N";
			break;
		case BISHOP:
			notationName = "B";
			break;
		case ROOK:
			notationName = "R";
			break;
		case QUEEN:
			notationName = "Q";
			break;
		case KING:
			notationName = "K";
			break;
		}
	}

	public int getPosX() {
		return pos.getPosX();
	}

	public int getPosY() {
		return pos.getPosY();
	}
	
	public Position getPosition() {
		return pos;
	}

	public void setPos(int posX, int posY) {
		pos = new Position(posX, posY);
		
		if (moves < 2)
			moves++;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public String getNotationName() {
		return notationName;
	}
	
	public String getName() {
		return name;
	}

	public int getMoves() {
		return moves;
	}
	
	public boolean isEnPassantTakeable() {
		return enPassantTakeable;
	}

	public void setEnPassantTakeable(boolean enPassantTakeable) {
		this.enPassantTakeable = enPassantTakeable;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Piece) 
			return name.equals(((Piece) o).getName());
		
		return false;
	}
	
	@Override
	public Object clone() {
		Piece p = new Piece(type, player, pos.getPosX(), pos.getPosY(), name);
		return p;
	}

}

