package com.chess.engine.board;

<<<<<<< HEAD
import com.chess.engine.pieces.Piece;

public abstract class Move {

	final Board board;
	final Piece movedPiece;
	final int destinationCoordinate;
=======
import com.chess.engine.board.Board.BoardBuilder;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public abstract class Move {

	public static final Move NULL_MOVE = new NullMove();
	
	protected final Board board;
	protected final Piece movedPiece;
	protected final int destinationCoordinate;
	protected final boolean isFirstMove;
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
	
	private Move(final Board board, final Piece movedPiece, final int destinationCoordinate) {
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
<<<<<<< HEAD
	}
	
=======
		this.isFirstMove = movedPiece.isFirstMove();
	}
	
	private Move(final Board board, final int destinationCoordinate) {
		this.board = board;
		this.movedPiece = null;
		this.destinationCoordinate = destinationCoordinate;
		this.isFirstMove = false;
	}
	
	//TODO cache hash code for move
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + this.destinationCoordinate;
		result = prime * result + this.movedPiece.hashCode();
		result = prime * result + this.movedPiece.getPiecePosition();
		
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		
		if (!(o instanceof Move))
			return false;
		
		Move m = (Move) o;
		
		return this.destinationCoordinate == m.getDestinationCoordinate() &&
			   this.getMovedPiece().equals(m.getMovedPiece()) &&
			   this.getCurrentCoordinate() == m.getCurrentCoordinate();
	}
	
	public Piece getMovedPiece() {
		return movedPiece;
	}
	
	public int getCurrentCoordinate() {
		return movedPiece.getPiecePosition();
	}
	
	public int getDestinationCoordinate() {
		return destinationCoordinate;
	}
	
	public boolean isAttack() {
		return false;
	}
	
	public boolean isCastlingMove() {
		return false;
	}
	
	public Piece getAttackedPiece() {
		return null;
	}
	
	public Board execute() {
		final BoardBuilder builder = new BoardBuilder();
		
		for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
			if (!this.movedPiece.equals(piece)) {
				builder.setPiece(piece);
			}
		}
		
		for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
			builder.setPiece(piece);
		}
		
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setNextMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
		
		return builder.build();
	}

>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
	public static final class MajorMove extends Move {
		
		public MajorMove(final Board board, final Piece movedPiece, final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		
<<<<<<< HEAD
	}
	
	
	public static final class AttackMove extends Move {
		
		final Piece attackedPiece;
		
		public AttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece) {
=======
		@Override
		public boolean equals(Object o) {
			return this == o || o instanceof MajorMove && super.equals(o);
		}
		
		@Override
		public String toString() {
			return movedPiece.toString() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
		}
		
	}
	
	
	public static class MajorAttackMove extends Move {
		
		final Piece attackedPiece;
		
		public MajorAttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece) {
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
			super(board, movedPiece, destinationCoordinate);
			this.attackedPiece = attackedPiece;
		}
		
<<<<<<< HEAD
=======
		@Override
		public int hashCode() {
			return this.attackedPiece.hashCode() + super.hashCode();
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			
			if (!(o instanceof MajorAttackMove))
				return false;
			
			MajorAttackMove a = (MajorAttackMove) o;
			
			return super.equals(o) && attackedPiece.equals(a.getAttackedPiece());
		}
		
		@Override
		public Board execute() {
			//TODO EXECUTE ATTACK MOVE????
			
			return null;
		}
		
		@Override
		public boolean isAttack() {
			return true;
		}
		
		@Override
		public Piece getAttackedPiece() {
			return this.attackedPiece;
		}
		
	}
	
	public static final class PawnMove extends Move {
		
		public PawnMove(final Board board, final Piece movedPiece, final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		
	}
	
	public static class PawnAttackMove extends MajorAttackMove {
		
		final Piece attackedPiece;
		
		public PawnAttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate, attackedPiece);
			this.attackedPiece = attackedPiece;
		}
		
	}
	
	public static final class PawnEnPassantAttackMove extends PawnAttackMove {
		
		final Piece attackedPiece;
		
		public PawnEnPassantAttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate, final Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate, attackedPiece);
			this.attackedPiece = attackedPiece;
		}
		
	}
	
	public static final class PawnJump extends Move {
		
		public PawnJump(final Board board, final Piece movedPiece, final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		
		@Override
		public Board execute() {
			final BoardBuilder builder = new BoardBuilder();
			for (final Piece piece: this.board.currentPlayer().getActivePieces()) {
				if (!this.movedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			
			for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
				builder.setPiece(piece);
			}
			
			final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
			builder.setPiece(movedPawn);
			builder.setEnPassantPawn(movedPawn);
			builder.setNextMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
			
			return builder.build();
		}
		
		@Override
		public String toString() {
			return BoardUtils.getPositionAtCoordinate(destinationCoordinate);
		}
		
	}
	
	private static abstract class CastleMove extends Move {
		
		protected final Rook castleRook;
		protected final int castleRookStart;
		protected final int castleRookDestination;
		
		public CastleMove(final Board board, final Piece movedPiece, final int destinationCoordinate, 
				final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate);
			this.castleRook = castleRook;
			this.castleRookStart = castleRookStart;
			this.castleRookDestination = castleRookDestination;
		}
		
		public Rook getCastleRook() {
			return this.castleRook;
		}
		
		@Override
		public boolean isCastlingMove() {
			return true;
		}
		
		@Override
		public Board execute() {
			final BoardBuilder builder = new BoardBuilder();
			
			for (final Piece piece: this.board.currentPlayer().getActivePieces()) {
				if (!this.movedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			
			for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
				builder.setPiece(piece);
			}
			
			builder.setPiece(this.movedPiece.movePiece(this));
			//TODO look into first move?
			builder.setPiece(new Rook(this.castleRookDestination, this.castleRook.getPieceAlliance(), false));
			builder.setNextMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
			
			return builder.build();
		}
		
	}
	
	public static final class KingSideCastleMove extends CastleMove{
		
		public KingSideCastleMove(final Board board, final Piece movedPiece, final int destinationCoordinate,
				final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
		}
		
		@Override
		public String toString() {
			return "O-O";
		}
		
	}

	public static final class QueenSideCastleMove extends CastleMove {
		
		public QueenSideCastleMove(final Board board, final Piece movedPiece, final int destinationCoordinate,
				final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
		}
	
		@Override
		public String toString() {
			return "O-O-O";
		}
		
	}
	
	public static final class NullMove extends Move {
		
		public NullMove() {
			super(null, -1);
		}
		
		@Override
		public Board execute() {
			throw new RuntimeException("Cannot execute null move");
		}
		
	}
	
	public static class MoveFactory {
		
		private MoveFactory() {
			throw new RuntimeException("Cannot instantiate");
		}
		
		public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate) {
			for (final Move move : board.getAllLegalMoves()) {
				if (move.getCurrentCoordinate() == currentCoordinate &&
					move.getDestinationCoordinate() == destinationCoordinate) {
					return move;
				}
			}
			
			return NULL_MOVE;
		}
		
>>>>>>> 0e8745abf5099f0afcd72c6b106d5dc93c416393
	}
	
}
