package com.chess.engine.board;

/*
 * Created by Adham Ibrahim on Oct 7, 2020
 */

public class MoveTransition {
	
	private final Board transitionBoard;
	private final Move move;
	private final MoveStatus moveStatus;
	
	public MoveTransition(final Board transitionBoard, final Move move, final MoveStatus moveStatus) {
		this.transitionBoard = transitionBoard;
		this.move = move;
		this.moveStatus = moveStatus;
	}

	public MoveStatus getMoveStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
