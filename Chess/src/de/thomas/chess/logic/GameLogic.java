package de.thomas.chess.logic;

import java.util.LinkedList;

/**
 * Stores the current game and checks moves
 * @author Thomas Opitz
 *
 */
public class GameLogic {
	private Board currentBoard;
	private LinkedList<GameChangedListener> gameChangedListener;

	public GameLogic() {
		gameChangedListener = new LinkedList<GameChangedListener>();
		currentBoard = new Board();
	}

	public Board getCurrentBoard() {
		return currentBoard;//currentBoard.clone();
	}
	
	public void addGameChangedListener(GameChangedListener g) {
		gameChangedListener.add(g);
	}

	/**
	 * Returns a list of moves feasible for a piece on the current board
	 * @param piece observed piece
	 * @return list of possible positions to move to
	 */
	public LinkedList<Position> getMoves(Piece piece) {
		return getMoves(piece, currentBoard);
	}

	/**
	 * Returns a list of moves feasible for a piece on a specific board
	 * @param piece observed piece
	 * @param board observed board
	 * @return list of possible positions to move to
	 */
	public LinkedList<Position> getMoves(Piece piece, Board board) {
		return getMoves(piece, board, false, true);
	}
	
	/**
	 * Returns a list of moves feasible for a piece on a specific board
	 * @param piece observed piece
	 * @param board observed board
	 * @param noCastling should castling be considered
	 * @param examineCheck should a possible check be considered
	 * @return list of possible positions to move to
	 */
	public LinkedList<Position> getMoves(Piece piece, Board board, boolean noCastling, boolean examineCheck) {
		LinkedList<Position> moves = new LinkedList<Position>();
		
		switch (piece.getType()) {
		case PAWN:	 moves = getPawnMoves(piece, board); break;
		case KNIGHT: moves = getKnightMoves(piece, board); break;
		case BISHOP: moves = getBishopMoves(piece, board); break;
		case ROOK:	 moves = getRookMoves(piece, board); break;
		case QUEEN:	 moves = getQueenMoves(piece, board); break;
		case KING:	 moves = getKingMoves(piece, board, noCastling); break;
		}
		
		if (examineCheck) {
			for (int k = 0; k < moves.size(); k++) {
				Board checkBoard = board.clone();
				Piece checkPiece = checkBoard.getPieceByName(piece.getName());
				
				if (checkBoard.getPieceFromPosition(moves.get(k)) != null) {
					checkBoard.removePiece(checkBoard.getPieceFromPosition(moves.get(k)));
				}
				
				checkBoard.movePiece(checkPiece, moves.get(k));
				
				//TODO side effects of k--?
				if (isCheck(piece.getPlayer(), checkBoard)) {
					moves.remove(k--);
				}
			}
		}

		return moves;
	}

	private LinkedList<Position> getPawnMoves(Piece piece, Board board) {
		LinkedList<Position> pawnMoves = new LinkedList<Position>();

		if (piece.getPlayer() == 1) {
			if (piece.getPosY() - 1 >= 0 && board.getPieceFromPosition(piece.getPosX(), piece.getPosY() -1) == null) {
				pawnMoves.add(new Position(piece.getPosX(), piece.getPosY() - 1));

				if (piece.getMoves() < 1 && piece.getPosY() - 2 >= 0 && board.getPieceFromPosition(piece.getPosX(), piece.getPosY() - 2) == null)
					pawnMoves.add(new Position(piece.getPosX(), piece.getPosY() - 2));
			}
			Piece pieceUpLeft = board.getPieceFromPosition(piece.getPosX() - 1, piece.getPosY() - 1);
			Piece pieceUpRight = board.getPieceFromPosition(piece.getPosX() + 1, piece.getPosY() - 1);

			if (piece.getPosX() - 1 >= 0 && piece.getPosY() - 1 >= 0 &&  pieceUpLeft != null && pieceUpLeft.getPlayer() == 2)
				pawnMoves.add(new Position(piece.getPosX() - 1, piece.getPosY() - 1));
			if (piece.getPosX() + 1 < 8 && piece.getPosY() - 1 >= 0 && pieceUpRight != null && pieceUpRight.getPlayer() == 2)
				pawnMoves.add(new Position(piece.getPosX() + 1, piece.getPosY() - 1));
		}
		else {
			if (piece.getPosY() + 1 < 8 && board.getPieceFromPosition(piece.getPosX(), piece.getPosY() +1) == null) {
				pawnMoves.add(new Position(piece.getPosX(), piece.getPosY() + 1));

				if (piece.getMoves() < 1 && piece.getPosY() + 2 < 8 && board.getPieceFromPosition(piece.getPosX(), piece.getPosY() + 2) == null)
					pawnMoves.add(new Position(piece.getPosX(), piece.getPosY() + 2));
			}
			Piece pieceDownLeft = board.getPieceFromPosition(piece.getPosX() - 1, piece.getPosY() + 1);
			Piece pieceDownRight = board.getPieceFromPosition(piece.getPosX() + 1, piece.getPosY() + 1);

			if (piece.getPosX() - 1 >= 0 && piece.getPosY() + 1 < 8 &&  pieceDownLeft != null && pieceDownLeft.getPlayer() == 1)
				pawnMoves.add(new Position(piece.getPosX() - 1, piece.getPosY() + 1));
			if (piece.getPosX() + 1 < 8 && piece.getPosY() + 1 < 8 && pieceDownRight != null && pieceDownRight.getPlayer() == 1)
				pawnMoves.add(new Position(piece.getPosX() + 1, piece.getPosY() + 1));
		}
		
		//en passant
		Piece pawnLeft = board.getPieceFromPosition(piece.getPosX() - 1, piece.getPosY());
		if (pawnLeft != null
		&& pawnLeft.getPlayer() != piece.getPlayer() 
		&& pawnLeft.getNotationName().equals("") 
		&& pawnLeft.getMoves() == 1
		&& pawnLeft.isEnPassantTakeable()) {
			
			if (piece.getPlayer() == 1)
				pawnMoves.add(new Position(piece.getPosX() - 1, piece.getPosY() - 1));
			else
				pawnMoves.add(new Position(piece.getPosX() - 1, piece.getPosY() + 1));
			
			pawnLeft.setEnPassantTakeable(false);
		}
		
		Piece pawnRight = board.getPieceFromPosition(piece.getPosX() + 1, piece.getPosY());
		if (pawnRight != null 
		&& pawnRight.getPlayer() != piece.getPlayer() 
		&& pawnRight.getNotationName().equals("")
		&& pawnRight.getMoves() == 1
		&& pawnRight.isEnPassantTakeable()) {
			
			if (piece.getPlayer() == 1)
				pawnMoves.add(new Position(piece.getPosX() + 1, piece.getPosY() - 1));
			else
				pawnMoves.add(new Position(piece.getPosX() + 1, piece.getPosY() + 1));
			
			pawnRight.setEnPassantTakeable(false);
		}	
				

		return pawnMoves;
	}

	private LinkedList<Position> getKnightMoves(Piece piece, Board board) {
		LinkedList<Position> knightMoves = new LinkedList<Position>();
		
		//TODO array
		Piece piece1 = board.getPieceFromPosition(piece.getPosX() - 1, piece.getPosY() - 2);
		Piece piece2 = board.getPieceFromPosition(piece.getPosX() + 1, piece.getPosY() - 2);
		Piece piece3 = board.getPieceFromPosition(piece.getPosX() - 1, piece.getPosY() + 2);
		Piece piece4 = board.getPieceFromPosition(piece.getPosX() + 1, piece.getPosY() + 2);
		Piece piece5 = board.getPieceFromPosition(piece.getPosX() - 2, piece.getPosY() - 1);
		Piece piece6 = board.getPieceFromPosition(piece.getPosX() - 2, piece.getPosY() + 1);
		Piece piece7 = board.getPieceFromPosition(piece.getPosX() + 2, piece.getPosY() - 1);
		Piece piece8 = board.getPieceFromPosition(piece.getPosX() + 2, piece.getPosY() + 1); 
		
		if (piece.getPosX() > 0 && piece.getPosY() > 1 && piece1 == null || piece1 != null && piece1.getPlayer() != piece.getPlayer())
			knightMoves.add(new Position(piece.getPosX() - 1, piece.getPosY() - 2));
		if (piece.getPosX() < 7 && piece.getPosY() > 1 && piece2 == null || piece2 != null &&  piece2.getPlayer() != piece.getPlayer())
			knightMoves.add(new Position(piece.getPosX() + 1, piece.getPosY() - 2));
		if (piece.getPosX() > 0 && piece.getPosY() < 6 && piece3 == null || piece3 != null &&  piece3.getPlayer() != piece.getPlayer())
			knightMoves.add(new Position(piece.getPosX() - 1, piece.getPosY() + 2));
		if (piece.getPosX() < 7 && piece.getPosY() < 6 && piece4 == null || piece4 != null &&  piece4.getPlayer() != piece.getPlayer())
			knightMoves.add(new Position(piece.getPosX() + 1, piece.getPosY() + 2));
		if (piece.getPosX() > 1 && piece.getPosY() > 0 && piece5 == null || piece5 != null &&  piece5.getPlayer() != piece.getPlayer())
			knightMoves.add(new Position(piece.getPosX() - 2, piece.getPosY() - 1));
		if (piece.getPosX() > 1 && piece.getPosY() < 7 && piece6 == null || piece6 != null &&  piece6.getPlayer() != piece.getPlayer())
			knightMoves.add(new Position(piece.getPosX() - 2, piece.getPosY() + 1));
		if (piece.getPosX() < 6 && piece.getPosY() > 0 && piece7 == null || piece7 != null &&  piece7.getPlayer() != piece.getPlayer())
			knightMoves.add(new Position(piece.getPosX() + 2, piece.getPosY() - 1));
		if (piece.getPosX() < 6 && piece.getPosY() < 7 && piece8 == null || piece8 != null &&  piece8.getPlayer() != piece.getPlayer())
			knightMoves.add(new Position(piece.getPosX() + 2, piece.getPosY() + 1));
		
		return knightMoves;
	}

	private LinkedList<Position> getBishopMoves(Piece piece, Board board) {
		LinkedList<Position> bishopMoves = new LinkedList<Position>();
		bishopMoves.addAll(getDiagonal(piece.getPosX(), piece.getPosY(), 8, board, piece.getPlayer()));
		return bishopMoves;
	}

	private LinkedList<Position> getRookMoves(Piece piece, Board board) {
		LinkedList<Position> rookMoves = new LinkedList<Position>();
		rookMoves.addAll(getVertical(piece.getPosX(), piece.getPosY(), 8, board, piece.getPlayer()));
		rookMoves.addAll(getHorizontal(piece.getPosX(), piece.getPosY(), 8, board, piece.getPlayer()));
		return rookMoves;
	}

	private LinkedList<Position> getQueenMoves(Piece piece, Board board) {
		LinkedList<Position> queenMoves = new LinkedList<Position>();
		queenMoves.addAll(getVertical(piece.getPosX(), piece.getPosY(), 8, board, piece.getPlayer()));
		queenMoves.addAll(getHorizontal(piece.getPosX(), piece.getPosY(), 8, board, piece.getPlayer()));
		queenMoves.addAll(getDiagonal(piece.getPosX(), piece.getPosY(), 8, board, piece.getPlayer()));
		return queenMoves;
	}

	private LinkedList<Position> getKingMoves(Piece piece, Board board, boolean noCastling) {
		LinkedList<Position> kingMoves = new LinkedList<Position>();
		
		kingMoves.addAll(getHorizontal(piece.getPosX(), piece.getPosY(), 1, board, piece.getPlayer()));
		kingMoves.addAll(getVertical(piece.getPosX(), piece.getPosY(), 1, board, piece.getPlayer()));
		kingMoves.addAll(getDiagonal(piece.getPosX(), piece.getPosY(), 1, board, piece.getPlayer()));
		
		if (! noCastling) {
			int kingPosX = piece.getPosX();
			int kingPosY = piece.getPosY();
			
			if (piece.getMoves() < 1 && ! isCheck(piece.getPlayer(), board)) {
				//long castling
				Piece leftRook = currentBoard.getPieceFromPosition(kingPosX - 4, kingPosY);
				if (leftRook != null && leftRook.getMoves() < 1 
						&& currentBoard.getPieceFromPosition(kingPosX - 3, kingPosY) == null
						&& currentBoard.getPieceFromPosition(kingPosX - 2, kingPosY) == null
						&& currentBoard.getPieceFromPosition(kingPosX - 1, kingPosY) == null) {
					
					Board checkBoard[] = new Board[2];
					Piece king[] = new Piece[2];
					boolean check[] = new boolean[2];
					
					for (int k = 0; k < 2; k++) {
						checkBoard[k] = currentBoard.clone();
						king[k] = checkBoard[k].getPieceByName("K" + piece.getPlayer());
						checkBoard[k].movePiece(king[k], kingPosX - (k+1), kingPosY);
						check[k] = isCheck(piece.getPlayer(), checkBoard[k]);
					}

					if (! check[0] && ! check[1]) {
						kingMoves.add(new Position(kingPosX - 2, kingPosY));
					}

				}

				//short castling
				Piece rightRook = currentBoard.getPieceFromPosition(kingPosX + 3, kingPosY);

				if (rightRook != null && rightRook.getMoves() < 1 
						&& currentBoard.getPieceFromPosition(kingPosX + 2, kingPosY) == null
						&& currentBoard.getPieceFromPosition(kingPosX + 1, kingPosY) == null) {

					Board[] checkBoard = new Board[2];
					Piece[] king = new Piece[2];
					boolean[] check = new boolean[2];

					for (int k = 0; k < 2; k++) {
						checkBoard[k] = currentBoard.clone();
						king[k] = checkBoard[k].getPieceByName("K" + piece.getPlayer());
						checkBoard[k].movePiece(king[k], kingPosX + (k+1), kingPosY);
						check[k] = isCheck(piece.getPlayer(), checkBoard[k]);
					}

					if (! check[0] && ! check[1]) {
						kingMoves.add(new Position(kingPosX + 2, kingPosY));
					}
				}
			}
		}
		

		
		return kingMoves;
		
	}
	
	/**
     * Gets all vertical positions to move to
     * @param posX x coordinate to start
     * @param posY y coordinate to start
     * @param length length of fields to search
     * @param board the board on which is searched
     * @param player player of piece
     * @return LinkedList of Positions to move
     */
    public LinkedList<Position> getVertical(int posX, int posY, int length, Board board, int player) {
    	LinkedList<Position> list = new LinkedList<Position>();
    	
    	for (int y = posY - 1; y >= 0 && y >= posY - length; y--) {
    		if (board.getPieceFromPosition(posX, y) == null)
    			list.add(new Position(posX,y));
    		
    		if (board.getPieceFromPosition(posX, y) != null) {
    			if (board.getPieceFromPosition(posX, y).getPlayer() != player)
    				list.add(new Position(posX,y));
    			
    			break;
    		}
    	}
    	
    	for (int y = posY + 1; y <= 7 && y <= posY + length; y++) {
    		if (board.getPieceFromPosition(posX, y) == null)
    			list.add(new Position(posX,y));
    		
    		if (board.getPieceFromPosition(posX, y) != null) {
    			if (board.getPieceFromPosition(posX, y).getPlayer() != player)
    				list.add(new Position(posX,y));
    			
    			break;
    		}
    	}
    	
    	return list;
    }
    
    /**
     * Gets all horizontal positions to move to
     * @param posX x coordinate to start
     * @param posY y coordinate to start
     * @param length length of fields to search
     * @param board the board on which is searched
     * @param player player of piece
     * @return LinkedList of Positions to move
     */
    public LinkedList<Position> getHorizontal(int posX, int posY, int length, Board board, int player) {
    	LinkedList<Position> list = new LinkedList<Position>();

    	for (int x = posX - 1; x >= 0 && x >= posX - length; x--) {
    		if (board.getPieceFromPosition(x, posY) == null)
    			list.add(new Position(x,posY));

    		if (board.getPieceFromPosition(x, posY) != null) {
    			if (board.getPieceFromPosition(x, posY).getPlayer() != player)
    				list.add(new Position(x,posY));

    			break;
    		}
    	}

    	for (int x = posX + 1; x <= 7 && x <= posX + length; x++) {
    		if (board.getPieceFromPosition(x, posY) == null)
    			list.add(new Position(x,posY));

    		if (board.getPieceFromPosition(x, posY) != null) {
    			if (board.getPieceFromPosition(x, posY).getPlayer() != player)
    				list.add(new Position(x,posY));

    			break;
    		}
    	}
    	
    	return list;
    }
    
    /**
     * Gets all diagonal positions to move to
     * @param posX x coordinate to start
     * @param posY y coordinate to start
     * @param length length of fields to search
     * @param board the board on which is searched
     * @param player player of piece
     * @return LinkedList of Positions to move
     */
    public LinkedList<Position> getDiagonal(int posX, int posY, int length, Board board, int player) {
    	LinkedList<Position> list = new LinkedList<Position>();
    	
    	int x = posX - 1;
    	int y = posY - 1;
    	
    	while(x >= 0 && y >= 0 && Math.abs(x - posX) <= length && Math.abs(y - posY) <= length) {
    		if (board.getPieceFromPosition(x, y) == null)
    			list.add(new Position(x,y));
    		
    		if (board.getPieceFromPosition(x, y) != null) {
    			if (board.getPieceFromPosition(x, y).getPlayer() != player)
    				list.add(new Position(x,y));

    			break;
    		}
    		x -= 1;
    		y -= 1;
    	}
    	
    	x = posX + 1;
    	y = posY + 1;
    	
    	while(x <= 7 && y <= 7 && Math.abs(x - posX) <= length && Math.abs(y - posY) <= length) {
    		if (board.getPieceFromPosition(x, y) == null)
    			list.add(new Position(x,y));
    		
    		if (board.getPieceFromPosition(x, y) != null) {
    			if (board.getPieceFromPosition(x, y).getPlayer() != player)
    				list.add(new Position(x,y));

    			break;
    		}
    		x += 1;
    		y += 1;
    	}
    	
    	x = posX - 1;
    	y = posY + 1;
    	
    	while(x >= 0 && y <= 7 && Math.abs(x - posX) <= length && Math.abs(y - posY) <= length) {
    		if (board.getPieceFromPosition(x, y) == null)
    			list.add(new Position(x,y));
    		
    		if (board.getPieceFromPosition(x, y) != null) {
    			if (board.getPieceFromPosition(x, y).getPlayer() != player)
    				list.add(new Position(x,y));

    			break;
    		}
    		x -= 1;
    		y += 1;
    	}
    	
    	x = posX + 1;
    	y = posY - 1;
    	
    	while(x <= 7 && y >= 0 && Math.abs(x - posX) <= length && Math.abs(y - posY) <= length) {
    		if (board.getPieceFromPosition(x, y) == null)
    			list.add(new Position(x,y));
    		
    		if (board.getPieceFromPosition(x, y) != null) {
    			if (board.getPieceFromPosition(x, y).getPlayer() != player)
    				list.add(new Position(x,y));

    			break;
    		}
    		x += 1;
    		y -= 1;
    	}
    	
    	return list;
    }
    
    /**
     * Checks if Position on current board is endangered
     * @param position position which is checked
     * @param player player who could be endangered
     * @return true if Position is endangered, else false
     */
     public boolean isEndangered(Position position, int player) {
     	return isEndangered(position, currentBoard, player);
     }
    
    /**
     * Checks if Position on specific board is endangered
     * @param position position which is checked
     * @param board board on which is searched
     * @param player player who could be endangered
     * @return true if Position is endangered, else false
     */
     public boolean isEndangered(Position position, Board board, int player) {
     	return isEndangered(position, board, player, false);
     }
     
     /**
      * Checks if Position on specific board is endangered
      * @param position position which is checked
      * @param board board on which is searched
      * @param player player who could be endangered
      * @param noCastling should castling be considered
      * @return true if Position is endangered, else false
      */
      public boolean isEndangered(Position position, Board board, int player, boolean noCastling) {
      	LinkedList<Position> list = new LinkedList<Position>();
      	
      	LinkedList<Piece> pieces = board.getPieces();
      	
      	for(Piece p: pieces) {
      		if (p.getPlayer() != player)
      			list.addAll(getMoves(p, board, noCastling, false));
      	}
      	
      	if (list.contains(position))
      		return true;
      	
      	return false;
      }
      
      /**
       * Checks if piece on current board is endangered
       * @param piece piece to check
       * @return true if piece is endangered, else false
       */
      public boolean isEndangered(Piece piece) {
    	  return isEndangered(piece, currentBoard);
      }
      
      /**
       * Checks if piece on current board is endangered
       * @param piece piece to check
       * @param noCastling should castling be considered
       * @return true if piece is endangered, else false
       */
      public boolean isEndangered(Piece piece, boolean noCastling) {
    	  return isEndangered(piece, currentBoard, noCastling);
      }
      
      /**
       * Checks if piece on specific board is endangered
       * @param piece piece to check
       * @param board board to check at
       * @return true if piece is endangered, else false
       */
      public boolean isEndangered(Piece piece, Board board) {
     	 return isEndangered(piece.getPosition(), board, piece.getPlayer());
      }
      
      /**
       * Checks if piece on specific board is endangered
       * @param piece piece piece to check
       * @param board board to check at
       * @param noCastling should castling be considered
       * @return true if piece is endangered, else false
       */
      public boolean isEndangered(Piece piece, Board board, boolean noCastling) {
    	  return isEndangered(piece.getPosition(), board, piece.getPlayer(), noCastling);
      }
      
      /**
       * Looks if player is in check on current board
       * @param player player to consider
       * @return true if there is check
       */
      public boolean isCheck(int player) {
    	  return isCheck(player, currentBoard);
      }
      
      /**
       * Looks if player is in check on specific board
       * @param player player to consider
       * @param board board to consider
       * @return true if there is check
       */
      public boolean isCheck(int player, Board board) {
    	  LinkedList<Piece> pieces = board.getPieces();
    	  Piece king = null;
    	  
    	  for (Piece p: pieces) {
    		  if (p.getName().equals("K" + player)) {
    			  king = p;
    			  break;
    		  } 
    	  }
    	  
    	  return isEndangered(king, board, true);
      }
      

	/**
	 * Tries to move a piece on the current board
	 * @param piece piece to move 
	 * @return true if move successful, else false
	 */
	public boolean movePiece(String name, Position position) {
		return movePiece(name, currentBoard, position);
	}

	/**
	 * Tries to move a piece on the specified board
	 * @param piece piece to move
	 * @param board board to move on
	 * @return true if move successful, else false
	 */
	public boolean movePiece(String name, Board board, Position position) {
		Piece piece = board.getPieceByName(name);
		
		LinkedList<Position> moveList = getMoves(piece);
		boolean taken = false;
		
		//TODO add choice of promotion (not only queen)
		//TODO add more information when move isn't possible or throw MoveNotPossibleException
		
		if (moveList.contains(position)) {
			//Disable en passant
			for (Piece p : board.getPieces()) {
				if (p.getPlayer() == piece.getPlayer() && p.getType() == Type.PAWN) {
					p.setEnPassantTakeable(false);
				}
			}
			
			int posX = piece.getPosX();
			int posY = piece.getPosY();
			
			if (board.getPieceFromPosition(position) != null) {
				taken = true;
				board.removePieceFromPosition(position);
			}
			
			
			board.movePiece(piece, position);
			
			//Castling
			if (piece.getNotationName() == "K") {
				
				if (position.getPosX() == posX - 2) {
					Piece leftRook = board.getPieceFromPosition(posX - 4, posY);
					board.movePiece(leftRook, leftRook.getPosX() + 3, leftRook.getPosY());
				}
				else if (position.getPosX() == posX + 2) {
					Piece rightRook = board.getPieceFromPosition(posX + 3, posY);
					board.movePiece(rightRook, rightRook.getPosX() - 2, rightRook.getPosY());
				}
				
			}
			else if (piece.getNotationName() == "") {
				if (piece.getPlayer() == 1) {
					//En passant
					Piece pawnLeft = board.getPieceFromPosition(posX - 1, posY);
					
					if (position.getPosX() == posX - 1 && position.getPosY() == posY - 1 
							&& pawnLeft != null 
							&& pawnLeft.getPlayer() != piece.getPlayer()
							&& pawnLeft.getNotationName().equals("")) {
						
						board.removePieceFromPosition(new Position(posX - 1, posY));
					}
					
					Piece pawnRight = board.getPieceFromPosition(posX + 1, posY);
					
					if (position.getPosX() == posX + 1 && position.getPosY() == posY - 1 
							&& pawnRight != null 
							&& pawnRight.getPlayer() != piece.getPlayer()
							&& pawnRight.getNotationName().equals("")) {

						board.removePieceFromPosition(new Position(posX + 1, posY));
					}
					
					//Promotion
					if (piece.getPosY() == 0)
						piece.setType(Type.QUEEN);
					
					//En passant possible
					if (posY == piece.getPosY() + 2)
						piece.setEnPassantTakeable(true);
					
					
				}
				else if (piece.getPlayer() == 2) {
					Piece pawnLeft = board.getPieceFromPosition(posX - 1, posY);

					if (position.getPosX() == posX - 1 && position.getPosY() == posY + 1 
							&& pawnLeft != null 
							&& pawnLeft.getPlayer() != piece.getPlayer()
							&& pawnLeft.getNotationName().equals("")) {

						board.removePieceFromPosition(new Position(posX - 1, posY));
					}

					Piece pawnRight = board.getPieceFromPosition(posX + 1, posY);

					if (position.getPosX() == posX + 1 && position.getPosY() == posY + 1 
							&& pawnRight != null 
							&& pawnRight.getPlayer() != piece.getPlayer()
							&& pawnRight.getNotationName().equals("")) {

						board.removePieceFromPosition(new Position(posX + 1, posY));
					}
					
					
					//Promotion
					if (piece.getPosY() == 7)
						piece.setType(Type.QUEEN);
					
					//En Passant possible
					if (posY == piece.getPosY() - 2) {
						piece.setEnPassantTakeable(true);
					}
				}
			}
			
			if (board.getCurrentPlayer() == 1)
				board.setCurrentPlayer(2);
			else
				board.setCurrentPlayer(1);
			
			if (board == currentBoard) {
				for (GameChangedListener g : gameChangedListener) {


					GameMessage msg = new GameMessage(GameMessage.PIECE_MOVED, new Move(position, piece, taken));
					g.handleMessage(msg);
				}
			}
			
			
			return true;
		}

		return false;
	}

}
