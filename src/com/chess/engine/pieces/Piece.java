package com.chess.engine.pieces;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece implements Comparable<Piece> {
	
	//TODO get better art??
	private static String PIECE_ART_FOLDER_PATH = "art/holywarriors/";
	
	protected final int piecePosition;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	protected final PieceType pieceType;

	private final int cachedHashCode;

	Piece (final PieceType pieceType, final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
		this.isFirstMove = isFirstMove;
		this.pieceType = pieceType;
	
		this.cachedHashCode = computeHashCode();
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
	
	@Override
	public int compareTo(Piece o) {
		return Integer.compare(this.getPieceType().getValue(), o.getPieceType().getValue());
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

	public String getImageIconFilePath() {
		return PIECE_ART_FOLDER_PATH + 
				//TODO alliance toString() - is it B or Black??
				this.getPieceAlliance().toString().substring(0, 1) + 
				this.toString() + ".gif";
	}

	//TODO disallow multiple moves to the same destination
	public abstract Collection<Move> calculateLegalMoves(final Board board);

	public abstract Piece movePiece(Move move);
	
	public enum PieceType {
		
		PAWN("P", 100),
		
		BISHOP("B", 300),
		
		KNIGHT("N", 300),
		
		ROOK("R", 500),
		
		QUEEN("Q", 900),
		
		KING("K", 10000);
		
		private String pieceName;
		private int value;
		
		PieceType(final String pieceName, final int value) {
			this.pieceName = pieceName;
			this.value = value;
		}
		
		public int getValue() {
			return this.value;
		}

		public boolean isKing() {
			return this == PieceType.KING;
		}
		
		public boolean isRook() {
			return this == PieceType.ROOK;
		}

		@Override
		public String toString() {
			return this.pieceName;
		}
	}

	@Override
	public String toString() {
		return this.pieceType.toString();
	}

}
