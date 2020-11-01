package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
<<<<<<< HEAD
import com.chess.engine.board.Move.AttackMove;
=======
import com.chess.engine.board.Move.MajorAttackMove;
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
import com.chess.engine.board.Move.MajorMove;

public class King extends Piece {

	private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};;
	
<<<<<<< HEAD
	public King(int piecePosition, Alliance pieceAlliance) {
		super(Piece.PieceType.KING, piecePosition, pieceAlliance);
=======
	public King(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
		super(Piece.PieceType.KING, piecePosition, pieceAlliance, isFirstMove);
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
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
<<<<<<< HEAD
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate,
=======
                        legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate,
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
                                pieceAtDestination));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }
	
<<<<<<< HEAD

	
=======
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7 || candidateOffset == -1);
	}
	
	private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGTH_COLUMN[currentPosition] && (candidateOffset == 9 || candidateOffset == -7 || candidateOffset == 1);
	}
	
	@Override
<<<<<<< HEAD
	public String toString() {
		return Piece.PieceType.KING.toString();
=======
	public King movePiece(Move move) {
		return new King(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance(), false);
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
	}
	
}
