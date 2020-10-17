package com.chess.engine.board;

/*
 * Created by Adham Ibrahim on Oct 7, 2020
 */

public enum MoveStatus {
	DONE {
		public boolean isDone() {return true;}
	}, 
	
	ILLEGAL_MOVE {
		public boolean isDone() {return false;}
	}, 
	
	LEAVES_PLAYER_IN_CHECK {
		public boolean isDone() {return false;}
	};
	
	public abstract boolean isDone();
}
