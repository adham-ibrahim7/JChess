package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorAttackMove;
import com.chess.engine.board.Move.MajorMove;

public class King extends Piece {

	private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};;
	
    public King(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
		super(Piece.PieceType.KING, piecePosition, pieceAlliance, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                isEigthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                continue;
            }
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Piece pieceAtDestination = board.getTile(candidateDestinationCoordinate).getPiece();
                if (pieceAtDestination == null) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Alliance pieceAtDestinationAllegiance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAtDestinationAllegiance) {
                        legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate,
                                pieceAtDestination));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_ROW[currentPosition] && (candidateOffset == -9 || candidateOffset == 7 || candidateOffset == -1);
	}
	
	private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_ROW[currentPosition] && (candidateOffset == 9 || candidateOffset == -7 || candidateOffset == 1);
	}
	
	@Override
	public String toString() {
        return Piece.PieceType.KING.toString();
    }

	public King movePiece(Move move) {
		return new King(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance(), false);
	}
	
}
