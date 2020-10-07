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
	
	Piece(final PieceType pieceType, final int piecePosition, final Alliance pieceAlliance) {
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
		//TODO more work here
		this.isFirstMove = false;
		this.pieceType = pieceType;
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

	public enum PieceType {
		
		PAWN("P") {
			public boolean isKing() {return false;}
		},
		BISHOP("B") {
			public boolean isKing() {return false;}
		},
		ROOK("R") {
			public boolean isKing() {return false;}
		},
		KNIGHT("K") {
			public boolean isKing() {return false;}
		},
		QUEEN("Q") {
			public boolean isKing() {return false;}
		},
		KING("K") {
			public boolean isKing() {return true;}
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
	}
	
}
