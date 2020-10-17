package com.chess.engine;

import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

public enum Alliance {
	WHITE {
		public int getDirection() {
			return -1;
		}
		
		public boolean isBlack() {return false;}
		
		public boolean isWhite() {return true;}

		@Override
		public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
			return whitePlayer;
		}
		
		@Override
		public String toString() {
			return "W";
		}
		
	},
	
	BLACK {
		public int getDirection() {
			return 1;
		}
		
		public boolean isBlack() {return true;}
		
		public boolean isWhite() {return false;}

		@Override
		public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
			return blackPlayer;
		}
		
		@Override
		public String toString() {
			return "B";
		}
	};
	
	public abstract int getDirection();

	public abstract boolean isBlack();

	public abstract boolean isWhite();

	public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);

}
