package chess.ai;

import java.util.LinkedList;

import chess.logic.Board;
import chess.logic.GameLogic;
import chess.logic.GameMessage;
import chess.logic.Move;
import chess.logic.Piece;
import chess.logic.Position;

/**
 * AI that chooses it's move randomly
 * @author Thomas
 *
 */
public class RandomAI extends AI {
	
	public RandomAI(GameLogic logic, int player) {
		super(logic, player);
	}

	@Override
	void handlePieceMovedMessage(GameMessage message) {
		Board board = logic.getCurrentBoard();
		if (board.getCurrentPlayer() != player)
			return;
		
		System.out.println("AI turn");

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

		int random = (int) (Math.random() * moves.size());

		Move move = moves.get(random);

		logic.movePiece(move.getPiece().getName(), move.getPosition());
	}



}