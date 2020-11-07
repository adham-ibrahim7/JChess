package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.PawnAttackMove;
import com.chess.engine.board.Move.PawnJump;

import java.util.ArrayList;
import java.util.Collection;

import static com.chess.engine.board.Move.PawnMove;

public class Pawn extends Piece {

	private final static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};
	
	public Pawn(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
		super(Piece.PieceType.PAWN, piecePosition, pieceAlliance, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		
		final Collection<Move> legalMoves = new ArrayList<>();

		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
			int candidateDestinationCoordinate = this.piecePosition + this.getPieceAlliance().getDirection() * currentCandidateOffset;
			
			if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}
			
			//TODO deal with promotions!!
			if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
			} else if (currentCandidateOffset == 16 && this.isFirstMove() &&
					//TODO clean up exceptions
					(BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceAlliance.isBlack() ||
					  BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceAlliance.isWhite())) {
				final int behindCandidateDestinationCoordinate = this.piecePosition + this.pieceAlliance.getDirection() * 8;
				if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
						!board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
				}
			} else if (currentCandidateOffset == 7 &&
					!(BoardUtils.FIRST_ROW[this.piecePosition] && this.pieceAlliance.isWhite()) &&
					!(BoardUtils.EIGHTH_ROW[this.piecePosition] && this.pieceAlliance.isBlack())) {
				if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
					}
				} else if (board.getEnPassantPawn() != null) {
					final Piece pieceOnCandidate = board.getEnPassantPawn();
					if (pieceOnCandidate.getPiecePosition() == (this.piecePosition + this.pieceAlliance.getOppositeDirection()) &&
							this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
					}
				}
			} else if (currentCandidateOffset == 9 &&
					!(BoardUtils.FIRST_ROW[this.piecePosition] && this.pieceAlliance.isBlack()) &&
					!(BoardUtils.EIGHTH_ROW[this.piecePosition] && this.pieceAlliance.isWhite())) {
				if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
					}
				} else if (board.getEnPassantPawn() != null) {
					final Piece pieceOnCandidate = board.getEnPassantPawn();
					if (pieceOnCandidate.getPiecePosition() == (this.piecePosition - this.pieceAlliance.getOppositeDirection()) &&
						this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
					}
				}
			}
		}
		
		return legalMoves;
	}
	
	public String toString() { return Piece.PieceType.PAWN.toString(); }

	public Pawn movePiece(Move move) {
		return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance(), false);
	}
	
}
