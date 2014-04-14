package chess.ai;

import java.util.LinkedList;
import java.util.List;

import chess.logic.Board;
import chess.logic.GameLogic;
import chess.logic.GameMessage;
import chess.logic.Move;
import chess.logic.Piece;
import chess.logic.Position;

/**
 * Main AI used
 * @author Thomas
 *
 */
public class T_AI extends AI {
	public T_AI(GameLogic logic, int player) {
		super(logic, player);
	}
	

	@Override
	void handlePieceMovedMessage(GameMessage message) {
		int valueFactor = 1;
		
		if (player == 2) valueFactor = -1;
		
		Board board = logic.getCurrentBoard();
		if (board.getCurrentPlayer() != player)
			return;

		LinkedList<Piece> pieces = logic.getCurrentBoard().getPieces();
		LinkedList<Move> moves = new LinkedList<Move>();

		for (Piece piece : pieces) {
			if (piece.getPlayer() == player) {
				LinkedList<Position> positions = logic.getMoves(piece);

				for (Position pos : positions) {
					moves.add(new Move(pos, piece, false));
				}
			}
		}
		
		
		List<Pair<Move, Integer>> bestMoves = new LinkedList<Pair<Move, Integer>>();
		int bestNumber = -100;
		
		for (Move move : moves) {
			Board testBoard = board.clone();
			
			logic.movePiece(move.getPiece().getName(), testBoard, move.getPosition());
			


			//Move testMove = new Move(move.getPosition(), move.getPiece(), false);
			
			if (testBoard.getValue() * valueFactor >= bestNumber) {
				
				
				
				bestMoves.add(new Pair<Move, Integer>(new Move(move.getPosition(), move.getPiece(), false), testBoard.getValue() * valueFactor));
				bestNumber = testBoard.getValue() * valueFactor;
				
				
				//TODO Improve
				LinkedList<Pair<Move, Integer>> removeList = new LinkedList<Pair<Move,Integer>>();
				
				for (Pair<Move, Integer> p : bestMoves) {
					if (p.getRight() < bestNumber) {
						removeList.add(p);
					}
				}
				
				bestMoves.removeAll(removeList);
				
				
			}
			
		}
		
		int random = (int) (Math.random() * bestMoves.size());

		Move bestMove = bestMoves.get(random).getLeft();
		
		logic.movePiece(bestMove.getPiece().getName(), bestMove.getPosition());
		
		
	}

}