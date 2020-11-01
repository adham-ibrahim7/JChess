package com.chess.engine.player;

<<<<<<< HEAD
import java.util.Collection;
=======
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
<<<<<<< HEAD
=======
import com.chess.engine.board.MoveStatus;
import com.chess.engine.board.MoveTransition;
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

public abstract class Player {

	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
<<<<<<< HEAD
	
	Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
		this.board = board;
		this.legalMoves = legalMoves;
		this.playerKing = establishKing();
	}

=======
	private final boolean isInCheck;
	
	Player(final Board board, final Collection<Move> initLegalMoves, final Collection<Move> opponentMoves) {
		this.board = board;
		this.playerKing = establishKing();
		this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
		
		Collection<Move> allLegalMoves = calculateKingCastles(initLegalMoves, opponentMoves);
		allLegalMoves.addAll(initLegalMoves);
		this.legalMoves = Collections.unmodifiableCollection(allLegalMoves);
	}
	
	protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> opponentMoves) {
		final List<Move> attackMoves = new ArrayList<>();
		for (final Move move : opponentMoves) {
			if (piecePosition == move.getDestinationCoordinate()) {
				attackMoves.add(move);
			}
		}
		return Collections.unmodifiableList(attackMoves);
	}
	
	public King getPlayerKing() {
		return this.playerKing;
	}
	
	public Collection<Move> getLegalMoves() {
		return this.legalMoves;
	}
	
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
	private King establishKing() {
		for (final Piece piece : getActivePieces()) {
			if (piece.getPieceType().isKing()) {
				return (King) piece;
			}
		}
		
		throw new RuntimeException("This player has no king!!");
	}
	
<<<<<<< HEAD
	public abstract Collection<Piece> getActivePieces();
	
	public abstract Alliance getAlliance();
	
	public abstract Player getOpponent();
	
=======
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
	public boolean isMoveLegal(Move move) {
		return this.legalMoves.contains(move);
	}
	
	public boolean isInCheck() {
<<<<<<< HEAD
=======
		return isInCheck;
	}
	
	protected boolean hasEscapeMoves() {
		for (final Move move : legalMoves) {
			final MoveTransition transition = makeMove(move);
			if (transition.getMoveStatus().isDone()) {
				return true;
			}
		}
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
		return false;
	}
	
	public boolean isInCheckmate() {
<<<<<<< HEAD
		return false;
	}
	
	public boolean isInStalemate() {
		return false;
	}
	
=======
		return this.isInCheck && !hasEscapeMoves();
	}
	
	public boolean isInStalemate() {
		return !this.isInCheck && !hasEscapeMoves();
	}
	
	//TODO get these done
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
	public boolean isCastled() {
		return false;
	}
	
<<<<<<< HEAD
=======
	public MoveTransition makeMove(final Move move) {
		if (!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		
		final Board transitionBoard = move.execute();
		
		final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
																		transitionBoard.currentPlayer().getLegalMoves());
		
		if (!kingAttacks.isEmpty()) {
			return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
		}
		
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
	}
	
	public abstract Collection<Piece> getActivePieces();
	public abstract Alliance getAlliance();
	public abstract Player getOpponent();
	
	protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals);
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
}
