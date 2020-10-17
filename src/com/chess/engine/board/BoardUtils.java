package com.chess.engine.board;

public class BoardUtils {
	
	public static final int NUM_TILES = 64;
	public static final int NUM_TILES_PER_ROW = 8;
	
	public static final boolean[] FIRST_COLUMN = initColumn(0);
	public static final boolean[] SECOND_COLUMN = initColumn(1);
	public static final boolean[] SEVENTH_COLUMN = initColumn(6);
	public static final boolean[] EIGTH_COLUMN = initColumn(7);
	
	public static final boolean[] EIGTH_RANK = initRow(0);
	public static final boolean[] SEVENTH_RANK = initRow(8);
	public static final boolean[] SIXTH_RANK = initRow(16);
	public static final boolean[] FIFTH_RANK = initRow(24);
	public static final boolean[] FOURTH_RANK = initRow(32);
	public static final boolean[] THIRD_RANK = initRow(40);
	public static final boolean[] SECOND_RANK = initRow(48);
	public static final boolean[] FIRST_RANK = initRow(56);
	
	private static boolean[] initColumn(int columnNumber) {
		final boolean[] column = new boolean[NUM_TILES];
		
		do {
			column[columnNumber] = true;
			columnNumber += NUM_TILES_PER_ROW;
		} while (columnNumber < NUM_TILES);
		
		return column;
	}
	
	private static boolean[] initRow(int rowStart) {
		final boolean[] row = new boolean[NUM_TILES];
		
		int current = rowStart;
		
		do {
			row[current] = true;
			current++;
		} while (current < rowStart + 8);
		
		return row;
	}
	
	private BoardUtils() {
		throw new RuntimeException("Cannot instantiate BoardUtils class");
	}
	
	public static boolean isValidTileCoordinate(int coordinate) {
		return coordinate >= 0 && coordinate < NUM_TILES;
	}

}
