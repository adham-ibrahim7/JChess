package com.chess.engine;

<<<<<<< HEAD
=======
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
public enum Alliance {
	WHITE {
		public int getDirection() {
			return -1;
		}
		
		public boolean isBlack() {return false;}
		
		public boolean isWhite() {return true;}
<<<<<<< HEAD
=======

		@Override
		public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
			return whitePlayer;
		}
		
		@Override
		public String toString() {
			return "W";
		}
		
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
	},
	
	BLACK {
		public int getDirection() {
			return 1;
		}
		
		public boolean isBlack() {return true;}
		
		public boolean isWhite() {return false;}
<<<<<<< HEAD
=======

		@Override
		public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
			return blackPlayer;
		}
		
		@Override
		public String toString() {
			return "B";
		}
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
	};
	
	public abstract int getDirection();

<<<<<<< HEAD
	public abstract boolean isBlack();

	public abstract boolean isWhite();
=======
	//TODO refactor this into piece.isWhite() as opposed to piece.getPieceAlliance().isWhite()
	public abstract boolean isBlack();

	public abstract boolean isWhite();

	public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);

>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
}
