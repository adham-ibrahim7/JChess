package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveStatus;
import com.chess.engine.board.MoveTransition;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

public abstract class Player {

	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
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
	
	private King establishKing() {
		for (final Piece piece : getActivePieces()) {
			if (piece.getPieceType().isKing()) {
				return (King) piece;
			}
		}
		
		throw new RuntimeException("This player has no king!!");
	}
	
	public boolean isMoveLegal(Move move) {
		return this.legalMoves.contains(move);
	}
	
	public boolean isInCheck() {
		return isInCheck;
	}
	
	protected boolean hasEscapeMoves() {
		for (final Move move : legalMoves) {
			final MoveTransition transition = makeMove(move);
			if (transition.getMoveStatus().isDone()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInCheckmate() {
		return this.isInCheck && !hasEscapeMoves();
	}
	
	public boolean isInStalemate() {
		return !this.isInCheck && !hasEscapeMoves();
	}
	//TODO get these done
	public boolean isCastled() {
		return false;
	}
	
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
}
