package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;

import static com.chess.engine.board.Move.*;

public class Bishop extends Piece {

	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};
	
	public Bishop(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
		super(Piece.PieceType.BISHOP, piecePosition, pieceAlliance, isFirstMove);
	}

	public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate) ||
                    isEigthColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidateOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Piece pieceAtDestination = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (pieceAtDestination == null) {
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    }
                    else {
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }
	
	private static boolean isFirstColumnExclusion(final int candidateOffset, final int currentPosition) {
		return BoardUtils.EIGHTH_ROW[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
	}
	
	private static boolean isEigthColumnExclusion(final int candidateOffset, final int currentPosition) {
		return BoardUtils.FIRST_ROW[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);
	}
	
	@Override
	public String toString() {
        return Piece.PieceType.BISHOP.toString();
    }

	public Bishop movePiece(Move move) {
		return new Bishop(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance(), false);
	}

}
