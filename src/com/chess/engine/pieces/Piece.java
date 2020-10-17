package com.chess.engine.pieces;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece {

	protected final int piecePosition;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	protected final PieceType pieceType;
	private final int cachedHashCode;
	
	Piece(final PieceType pieceType, final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
		//TODO fix first move
		this.isFirstMove = isFirstMove;
		this.pieceType = pieceType;
	
		this.cachedHashCode = computeHashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) 
			return true;
	
		if (!(o instanceof Piece)) {
			return false;
		}
		
		Piece p = (Piece) o;
		
		return piecePosition == p.getPiecePosition() && 
			   pieceType == p.getPieceType() &&
			   pieceAlliance == p.getPieceAlliance() &&
			   isFirstMove == p.isFirstMove();
	}
	
	private int computeHashCode() {
		int result = pieceType.hashCode();
		result = 31 * result + pieceAlliance.hashCode();
		result = 31 * result + piecePosition;
		result = 31 * result + (isFirstMove ? 1 : 0);
		
		return result;
	}
	
	@Override
	public int hashCode() {
		return cachedHashCode;
	}
	
	public int getPiecePosition() {
		return this.piecePosition;
	}
	
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}
	
	public boolean isFirstMove() {
		return this.isFirstMove;
	}
	
	public PieceType getPieceType() {
		return this.pieceType;
	}
	
	public abstract Collection<Move> calculateLegalMoves(final Board board);

	public abstract Piece movePiece(Move move);
	
	public enum PieceType {
		
		PAWN("P") {
			public boolean isKing() {return false;}
			public boolean isRook() {return false;}
		},
		BISHOP("B") {
			public boolean isKing() {return false;}
			public boolean isRook() {return false;}
		},
		ROOK("R") {
			public boolean isKing() {return false;}
			public boolean isRook() {return true;}
		},
		KNIGHT("N") {
			public boolean isKing() {return false;}
			public boolean isRook() {return false;}
		},
		QUEEN("Q") {
			public boolean isKing() {return false;}
			public boolean isRook() {return false;}
		},
		KING("K") {
			public boolean isKing() {return true;}
			public boolean isRook() {return false;}
		};
		
		private String pieceName;
		
		PieceType(final String pieceName) {
			this.pieceName = pieceName;
		}
		
		public abstract boolean isKing();
		
		@Override
		public String toString() {
			return this.pieceName;
		}

		public abstract boolean isRook();
	}

}
