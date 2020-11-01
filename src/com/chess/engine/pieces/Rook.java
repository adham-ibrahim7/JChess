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

public class Rook extends Piece {

	private static final int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8, -1, 1, 8};

<<<<<<< HEAD
	public Rook(int piecePosition, Alliance pieceAlliance) {
		super(Piece.PieceType.ROOK, piecePosition, pieceAlliance);
=======
	public Rook(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
		super(Piece.PieceType.ROOK, piecePosition, pieceAlliance, isFirstMove);
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(candidateDestinationCoordinate, currentCandidateOffset) ||
                		isEigthColumnExclusion(candidateDestinationCoordinate, currentCandidateOffset)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidateOffset;
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
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
	}
	
	private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGTH_COLUMN[currentPosition] && (candidateOffset == 1);
	}
<<<<<<< HEAD

	@Override
	public String toString() {
		return Piece.PieceType.ROOK.toString();
=======
	
	@Override
	public Rook movePiece(Move move) {
		return new Rook(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance(), false);
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
	}
	
}
