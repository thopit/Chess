package de.thomas.chess.logic;

/**
 * Class for storing the position of a piece
 * @author Thomas Opitz
 *
 */
public class Position {
	private int posX;
	private int posY;
	
	public Position(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof Position)) return false;
		Position p = (Position) o;
		
		return p.getPosX() == posX && p.getPosY() == posY;
	}
	
	@Override
	public String toString() {
		String str = "";
		
		switch (posX) {
		case 0: str += "a"; break;
		case 1: str += "b"; break;
		case 2: str += "c"; break;
		case 3: str += "d"; break;
		case 4: str += "e"; break;
		case 5: str += "f"; break;
		case 6: str += "g"; break;
		case 7: str += "h"; break;
		}
		
		switch (posY) {
		case 0: str += "8"; break;
		case 1: str += "7"; break;
		case 2: str += "6"; break;
		case 3: str += "5"; break;
		case 4: str += "4"; break;
		case 5: str += "3"; break;
		case 6: str += "2"; break;
		case 7: str += "1"; break;
		}
		
		return str;
	}
	
	@Override
	public Position clone() {
		return new Position(posX, posY);
	}

}
