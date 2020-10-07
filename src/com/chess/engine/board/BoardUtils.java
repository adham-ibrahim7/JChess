package com.chess.engine.board;

public class BoardUtils {
	
	public static final int NUM_TILES = 64;
	public static final int NUM_TILES_PER_ROW = 8;
	
	public static final boolean[] FIRST_COLUMN = initColumn(0);
	public static final boolean[] SECOND_COLUMN = initColumn(1);
	public static final boolean[] SEVENTH_COLUMN = initColumn(6);
	public static final boolean[] EIGTH_COLUMN = initColumn(7);
	
	//TODO fix this
	public static final boolean[] SECOND_ROW = initRow(8);
	public static final boolean[] SEVENTH_ROW = initRow(48);
	
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
