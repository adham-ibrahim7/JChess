package com.chess.engine.board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.chess.engine.pieces.Piece;

/*
 * The Tile class represents the tiles of the board.
 * A chess board has 8 * 8 = 64 tiles, of which are always
 * either empty or occupied
 */

public abstract class Tile {

	protected final int tileCoordinate;
	
	private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();
	
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
		
		return Collections.unmodifiableMap(emptyTileMap);
	}
	
	public static Tile createTile(final int tileCoordinate, final Piece piece) {
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES.get(tileCoordinate);
	}
	
	private Tile (int tileCoordinate) {
		this.tileCoordinate = tileCoordinate;
	}
	
	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	public int getTileCoordinate() {
		return this.tileCoordinate;
	}
	
	public static final class EmptyTile extends Tile {
		
		private EmptyTile(final int coordinate) {
			super(coordinate);
		}
		
		@Override
		public String toString() {
			return "-";
		}
		
		@Override
		public boolean isTileOccupied() {
			return false;
		}
		
		@Override
		public Piece getPiece() {
			return null;
		}
		
	}
	
	public static final class OccupiedTile extends Tile {
		
		private final Piece pieceOnTile;
		
		private OccupiedTile(final int tileCoordinate, final Piece pieceOnTile) {
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}
		
		@Override
		public String toString() {
			return pieceOnTile.getPieceAlliance().isBlack() ? pieceOnTile.toString().toLowerCase() : pieceOnTile.toString();
		}
		
		@Override
		public boolean isTileOccupied() {
			return true;
		}
		
		@Override
		public Piece getPiece() {
			return this.pieceOnTile;
		}
	}

}
