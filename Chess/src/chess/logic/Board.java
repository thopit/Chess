package chess.logic;

import java.util.LinkedList;


/**
 * In this class the existence and position of all pieces is stored
 * @author Thomas
 *
 */
public class Board implements Comparable<Board> {
	private LinkedList<Piece> pieces;
	private Piece[][] field;
	private int currentPlayer;
	
	/**
	 * Creates a new board
	 */
	public Board() {
		currentPlayer = 1;
		pieces = new LinkedList<Piece>();
		field = new Piece[8][8];
		
		for (int k = 0; k < 8; k++) {
			Piece pawn = new Piece(Type.PAWN, 1, k, 6, "P1" + (k+1));
        	pieces.add(pawn);
        	field[k][6] = pawn;
        }
		Piece rook1 = new Piece(Type.ROOK,1,0,7, "R11");
		pieces.add(rook1);
		field[0][7] = rook1;
		
		Piece knight1 = new Piece(Type.KNIGHT,1,1,7, "K11");
		pieces.add(knight1);
		field[1][7] = knight1;
		
		Piece bishop1 = new Piece(Type.BISHOP,1,2,7, "B11");
		pieces.add(bishop1);
		field[2][7] = bishop1;
		
		Piece queen1 = new Piece(Type.QUEEN,1,3,7, "Q1");
		pieces.add(queen1);
		field[3][7] = queen1;
		
		Piece king1 = new Piece(Type.KING,1,4,7, "K1");
		pieces.add(king1);
		field[4][7] = king1;
		
		Piece bishop2 = new Piece(Type.BISHOP,1,5,7, "B12");
		pieces.add(bishop2);
		field[5][7] = bishop2;
		
		Piece knight2 = new Piece(Type.KNIGHT,1,6,7, "K12");
		pieces.add(knight2);
		field[6][7] = knight2;
		
		Piece rook2 = new Piece(Type.ROOK,1,7,7, "R12");
		pieces.add(rook2);
		field[7][7] = rook2;
		
        for (int k = 0; k < 8; k++) {
        	Piece pawn = new Piece(Type.PAWN,2, k, 1, "P2" + (k+1));
        	pieces.add(pawn);
        	field[k][1] = pawn;
        }
        
        Piece rookA = new Piece(Type.ROOK,2,0,0, "R21");
        pieces.add(rookA);
        field[0][0] = rookA;
        
        Piece knightA = new Piece(Type.KNIGHT,2,1,0, "K21");
		pieces.add(knightA);
		field[1][0] = knightA;
		
		Piece bishopA = new Piece(Type.BISHOP,2,2,0, "B21");
		pieces.add(bishopA);
		field[2][0] = bishopA;
		
		Piece queenA = new Piece(Type.QUEEN,2,3,0, "Q2");
		pieces.add(queenA);
		field[3][0] = queenA;
		
		Piece kingA = new Piece(Type.KING,2,4,0, "K2");
		pieces.add(kingA);
		field[4][0] = kingA;
		
		Piece bishopB = new Piece(Type.BISHOP,2,5,0, "B22");
		pieces.add(bishopB);
		field[5][0] = bishopB;
		
		Piece knightB = new Piece(Type.KNIGHT,2,6,0, "K22");
		pieces.add(knightB);
		field[6][0] = knightB;
		
		Piece rookB = new Piece(Type.ROOK,2,7,0, "R22");
		pieces.add(rookB);
		field[7][0] = rookB;
	}
	
	public LinkedList<Piece> getPieces() {
		return pieces;
	}
	
	public void setPieces(LinkedList<Piece> pieces) {
		this.pieces = pieces;
	}
	
	public Piece[][] getField() {
		return field;
	}

	public void setField(Piece[][] field) {
		this.field = field;
	}

	/**
	 * Returns a piece which is on the specified position
	 * @param pos Position of the piece
	 * @return the piece of the position if existing, else null
	 */
	public Piece getPieceFromPosition(Position pos) {
		return field[pos.getPosX()][pos.getPosY()];
		
		/*
		for (Piece p: pieces) {
			if (p.getPosition().equals(pos))
				return p;
		}
		return null;
		*/
	}
	
	/**
	 * Returns a piece which is on the specified position
	 * @param posX x position of the piece
	 * @param posY y position of the piece
	 * @return the piece of the position if existing, else null
	 */
	public Piece getPieceFromPosition(int posX, int posY) {
		if (posX < 0 || posX >= 8 || posY < 0 || posY >= 8)
			return null;
		
		return field[posX][posY];
		
		/*
		for (Piece p: pieces) {
			if (p.getPosX() == posX && p.getPosY() == posY)
				return p;
		}
		return null;
		*/
	}
	
	/**
	 * Returns a piece which has a specific name
	 * @param str name of the piece
	 * @return the piece with the name if it exists, else null
	 */
	public Piece getPieceByName(String str) {
		for (Piece p: pieces) {
			if (p.getName().equals(str))
				return p;
		}
		
		return null;
	}
	
	/**
	 * Moves piece to specified position
	 * @param p piece to move
	 * @param posX x position to move to
	 * @param posY y position to move to
	 */
	public void movePiece(Piece p, int posX, int posY) {
		field[p.getPosX()][p.getPosY()] = null;
		field[posX][posY] = p;
		p.setPos(posX, posY);
	}
	
	/**
	 * Moves piece to specified position
	 * @param p piece to move
	 * @param pos position to move to
	 */
	public void movePiece(Piece p, Position pos) {
		movePiece(p, pos.getPosX(), pos.getPosY());
	}
	
	/**
	 * Removes piece from board
	 * @param p piece to remove
	 */
	public boolean removePiece(Piece p) {
		field[p.getPosX()][p.getPosY()] = null;
		return pieces.remove(p);
	}
	
	/**
	 * Removes piece of specified position from board
	 * @param pos position of piece
	 */
	public boolean removePieceFromPosition(Position pos) {
		Piece p = getPieceFromPosition(pos);
		
		if (p != null) {
			field[p.getPosX()][p.getPosY()] = null;
			return removePiece(p);
		}
		
		return false;
	}
	
	/**
	 * Evaluation function of this board
	 * @return a value describing the advantage of a player
	 */
	public int getValue() {
		int value = 0;
		int factor;

		for (Piece p: pieces) {
			if (p.getPlayer() == 1) factor = 1;
			else factor = -1;

			switch (p.getType()) {
			case PAWN:
				value += factor;
				break;
			case KNIGHT:
				value += 3 * factor;
				break;
			case BISHOP:
				value += 3 * factor;
				break;
			case ROOK:
				value += 5 * factor;
				break;
			case QUEEN:
				value += 9 * factor;
				break;
			default:
				break;
			}
		}
		
		return value;
	}
	
	@Override
	public Board clone() {
		Board board = new Board();
		
		LinkedList<Piece> newPieces = new LinkedList<Piece>();
		Piece[][] newField = new Piece[8][8];
		
		for (Piece p: pieces) {
			Piece p2 = (Piece) p.clone();
			newPieces.add(p2);
			newField[p2.getPosX()][p2.getPosY()] = p2;
		}
		
		board.setPieces(newPieces);
		board.setField(newField);
		
		return board;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	@Override
	public int compareTo(Board arg0) {
		int value = getValue();
		int boardValue = arg0.getValue();
		
		if (value == boardValue) return 0;
		else if (value > boardValue) return 1;
		else return -1;
	}
	
	
	
}
