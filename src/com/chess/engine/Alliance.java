package com.chess.engine;

public enum Alliance {
	WHITE {
		public int getDirection() {
			return -1;
		}
		
		public boolean isBlack() {return false;}
		
		public boolean isWhite() {return true;}
	},
	
	BLACK {
		public int getDirection() {
			return 1;
		}
		
		public boolean isBlack() {return true;}
		
		public boolean isWhite() {return false;}
	};
	
	public abstract int getDirection();

	public abstract boolean isBlack();

	public abstract boolean isWhite();
}
