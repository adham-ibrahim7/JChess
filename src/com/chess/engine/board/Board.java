package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.*;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
	
	private final List<Tile> gameBoard;
	private final Collection<Piece> whitePieces, blackPieces;
	
	private final WhitePlayer whitePlayer;
	private final BlackPlayer blackPlayer;
	
	private final Player currentPlayer;
	
	public Board(BoardBuilder builder) {
		this.gameBoard = createGameBoard(builder);
		
		this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
		this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
	
		final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
		final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
		
		this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
		this.blackPlayer = new BlackPlayer(this, blackStandardLegalMoves, whiteStandardLegalMoves);
	
		this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
	}

	public Tile getTile(final int tileCoordinate) {
		return gameBoard.get(tileCoordinate);
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			final String tileText = this.gameBoard.get(i).toString();
			builder.append(String.format("%3s", tileText));
			if ((i + 1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
				builder.append("\n");
			}
		}
		
		return builder.toString();
	}
	
	public Player whitePlayer() {
		return this.whitePlayer;
	}
	
	public Player blackPlayer() {
		return this.blackPlayer;
	}
	
	public Player currentPlayer() {
		return this.currentPlayer;
	}

	private List<Tile> createGameBoard(final BoardBuilder builder) {
		final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
		}
		return Collections.unmodifiableList(Arrays.asList(tiles));
	}

	private static Collection<Piece> calculateActivePieces(List<Tile> gameBoard, Alliance alliance) {
		
		final List<Piece> activePieces = new ArrayList<>();
		
		for (final Tile tile : gameBoard) {
			if (tile.isTileOccupied()) {
				final Piece piece = tile.getPiece();
				if (piece.getPieceAlliance() == alliance) {
					activePieces.add(piece);
				}
			}
		}
		
		return Collections.unmodifiableList(activePieces);
	}
	
	private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
		final List<Move> legalMoves = new ArrayList<>();
		
		for (final Piece piece: pieces) {
			legalMoves.addAll(piece.calculateLegalMoves(this));
		}
		
		return Collections.unmodifiableList(legalMoves);
	}
	
	public static Board createStandardBoard() {
		final BoardBuilder builder = new BoardBuilder();
		
		//Black pieces layout
		builder.setPiece(new Rook(0, Alliance.BLACK, true));
		builder.setPiece(new Knight(1, Alliance.BLACK, true));
		builder.setPiece(new Bishop(2, Alliance.BLACK, true));
		builder.setPiece(new Queen(3, Alliance.BLACK, true));
		builder.setPiece(new King(4, Alliance.BLACK, true));
		builder.setPiece(new Bishop(5, Alliance.BLACK, true));
		builder.setPiece(new Knight(6, Alliance.BLACK, true));
		builder.setPiece(new Rook(7, Alliance.BLACK, true));
		builder.setPiece(new Pawn(8, Alliance.BLACK, true));
		builder.setPiece(new Pawn(9, Alliance.BLACK, true));
		builder.setPiece(new Pawn(10, Alliance.BLACK, true));
		builder.setPiece(new Pawn(11, Alliance.BLACK, true));
		builder.setPiece(new Pawn(12, Alliance.BLACK, true));
		builder.setPiece(new Pawn(13, Alliance.BLACK, true));
		builder.setPiece(new Pawn(14, Alliance.BLACK, true));
		builder.setPiece(new Pawn(15, Alliance.BLACK, true));
		
		//White pieces layout
		builder.setPiece(new Pawn(48, Alliance.WHITE, true));
		builder.setPiece(new Pawn(49, Alliance.WHITE, true));
		builder.setPiece(new Pawn(50, Alliance.WHITE, true));
		builder.setPiece(new Pawn(51, Alliance.WHITE, true));
		builder.setPiece(new Pawn(52, Alliance.WHITE, true));
		builder.setPiece(new Pawn(53, Alliance.WHITE, true));
		builder.setPiece(new Pawn(54, Alliance.WHITE, true));
		builder.setPiece(new Pawn(55, Alliance.WHITE, true));
		builder.setPiece(new Rook(56, Alliance.WHITE, true));
		builder.setPiece(new Knight(57, Alliance.WHITE, true));
		builder.setPiece(new Bishop(58, Alliance.WHITE, true));
		builder.setPiece(new Queen(59, Alliance.WHITE, true));
		builder.setPiece(new King(60, Alliance.WHITE, true));
		builder.setPiece(new Bishop(61, Alliance.WHITE, true));
		builder.setPiece(new Knight(62, Alliance.WHITE, true));
		builder.setPiece(new Rook(63, Alliance.WHITE, true));
		
		builder.setNextMoveMaker(Alliance.WHITE);
		
		return builder.build();
	}
	
	public static class BoardBuilder {
		
		Map<Integer, Piece> boardConfig;
		Alliance nextMoveMaker;
		Pawn enPassantPawn;
		
		
		public BoardBuilder() {
			boardConfig = new HashMap<>();
		}
		
		public BoardBuilder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}
		
		public BoardBuilder setNextMoveMaker(final Alliance nextMoveMaker) {
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		
		public Board build() {
			return new Board(this);
		}

		public void setEnPassantPawn(Pawn enPassantPawn) {
			this.enPassantPawn = enPassantPawn;
		}
		
	}

	public Collection<Piece> getBlackPieces() {
		return this.blackPieces;
	}
	
	public Collection<Piece> getWhitePieces() {
		return this.whitePieces;
	}

	public Iterable<Move> getAllLegalMoves() {
		Collection<Move> allLegalMoves = new ArrayList<>();
		
		allLegalMoves.addAll(this.whitePlayer.getLegalMoves());
		allLegalMoves.addAll(this.blackPlayer.getLegalMoves());
		
		return allLegalMoves;
	}
	
}
