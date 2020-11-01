package com.chess.gui;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.board.MoveTransition;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;

/*
 * Created by Adham Ibrahim on Oct 11, 2020
 */

public class Table {
	
	private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
	private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
	private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
	
	private final Color LIGHT_TILE_COLOR = Color.decode("#FFFACD");
	private final Color DARK_TILE_COLOR = Color.decode("#593E1A");
	
	private final JFrame gameFrame;
	private final GameHistoryPanel gameHistoryPanel;
	private final TakenPiecesPanel takenPiecesPanel;
	private final BoardPanel boardPanel;
	private final MoveLog moveLog;
	private Board chessBoard;
	
	private Tile sourceTile;
	private Tile destinationTile;
	private Piece humanMovedPiece;
	
	private BoardDirection boardDirection;
	private boolean highlightLegalMoves;
	
	public Table() {
		this.chessBoard = Board.createStandardBoard();
		
		this.gameFrame = new JFrame("JChess");
		this.gameFrame.setLayout(new BorderLayout());
		
		this.gameHistoryPanel = new GameHistoryPanel();
		this.takenPiecesPanel = new TakenPiecesPanel();
		this.moveLog = new MoveLog();
		
		final JMenuBar tableMenuBar = createMenuBar();
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		
		this.boardPanel = new BoardPanel();
		
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
		this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
		this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
		
		this.boardDirection = BoardDirection.NORMAL;
		this.highlightLegalMoves = true;
		
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameFrame.setVisible(true);
	}

	private JMenuBar createMenuBar() {
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		tableMenuBar.add(createPreferencesMenu());
		return tableMenuBar;
	}

	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		
		final JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO open pgn file!!!
				System.out.println("open up that pgn file!");
			}
		});
		fileMenu.add(openPGN);
		
		final JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitMenuItem);
		
		return fileMenu;
	}
	
	private JMenu createPreferencesMenu() {
		final JMenu preferencesMenu = new JMenu("Preferences");
		
		final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
		flipBoardMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boardDirection = boardDirection.opposite();
				boardPanel.drawBoard(chessBoard);
			}
		});
		preferencesMenu.add(flipBoardMenuItem);
		
		preferencesMenu.addSeparator();
		
		final JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("Highlight Legal Moves", false);
		legalMoveHighlighterCheckbox.setSelected(true);
		legalMoveHighlighterCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected();
				boardPanel.drawBoard(chessBoard);
			}
		});
		preferencesMenu.add(legalMoveHighlighterCheckbox);
		
		return preferencesMenu;
	}
	
	@SuppressWarnings("serial")
	private class BoardPanel extends JPanel {
		
		final List<TilePanel> boardTiles;
		
		BoardPanel() {
			super(new GridLayout(8, 8));
			this.boardTiles = new ArrayList<>();
			for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
				final TilePanel tilePanel = new TilePanel(this, i);
				this.boardTiles.add(tilePanel);
				add(tilePanel);
			}
			setPreferredSize(BOARD_PANEL_DIMENSION);
			validate();
		}

		public void drawBoard(final Board board) {
			removeAll();
			for (final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
				tilePanel.drawTile(board);
				add(tilePanel);
			}
			validate();
			repaint();
		}
	
	}
	
	@SuppressWarnings("serial")
	private class TilePanel extends JPanel {
		
		private final int tileId;
		
		TilePanel(final BoardPanel boardPanel, final int tileId) {
			super(new GridBagLayout());
			this.tileId = tileId;
			setPreferredSize(TILE_PANEL_DIMENSION);
			assignTileColor();
			assignTilePieceIcon(chessBoard);
			
			addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(final MouseEvent e) {
					if (isRightMouseButton(e)) {
						sourceTile = null;
						destinationTile = null;
						humanMovedPiece = null;
					} else if (isLeftMouseButton(e)) {
						if (sourceTile == null) {
							//first click
							
							sourceTile = chessBoard.getTile(tileId);
							humanMovedPiece = sourceTile.getPiece();
							if (humanMovedPiece == null) {
								sourceTile = null;
							}
						} else {
							//second click
							
							destinationTile = chessBoard.getTile(tileId);
							final Move move = MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
							final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
							
							if (transition.getMoveStatus().isDone()) {
								chessBoard = transition.getTransitionBoard();
								moveLog.addMove(move);
								//TODO add the move that was made to the move log
							}
							
							sourceTile = null;
							destinationTile = null;
							humanMovedPiece = null;
						}
					}
					
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							gameHistoryPanel.redo(chessBoard, moveLog);
							takenPiecesPanel.redo(moveLog);
							boardPanel.drawBoard(chessBoard);
						}
					});
				}

				@Override
				public void mousePressed(final MouseEvent e) {}

				@Override
				public void mouseReleased(final MouseEvent e) {}

				@Override
				public void mouseEntered(final MouseEvent e) {}

				@Override
				public void mouseExited(final MouseEvent e) {}
				
			});
			
			validate();
		}

		public void drawTile(final Board board) {
			assignTileColor();
			assignTilePieceIcon(board);
			highlightLegals(board);
			validate();
			repaint();
		}
		
		private void highlightLegals(final Board board) {
			if (highlightLegalMoves) {
				final String highlightIconFilePath = "art/misc/green_dot.png";
				
				for (final Move move : pieceLegalMoves(board)) {
					if (move.getDestinationCoordinate() == this.tileId) {
						try {
							add(new JLabel(new ImageIcon(ImageIO.read(new File(highlightIconFilePath)))));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		private Collection<Move> pieceLegalMoves(Board board) {
			if (humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()) {
				return humanMovedPiece.calculateLegalMoves(board);
			}
			
			return Collections.emptyList();
		}

		private void assignTilePieceIcon(final Board board) {
			removeAll();
			
			if (board.getTile(this.tileId).isTileOccupied()) {
				try {
					final BufferedImage image = ImageIO.read(new File(board.getTile(tileId).getPiece().getImageIconFilePath()));
					add(new JLabel(new ImageIcon(image)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		private void assignTileColor() {
			if (BoardUtils.EIGTH_RANK[tileId] ||
				BoardUtils.SIXTH_RANK[tileId] ||
				BoardUtils.FOURTH_RANK[tileId] ||
				BoardUtils.SECOND_RANK[tileId]) {
				setBackground(this.tileId % 2 == 0 ? LIGHT_TILE_COLOR : DARK_TILE_COLOR);
			} else if (BoardUtils.SEVENTH_RANK[tileId] ||
					   BoardUtils.FIFTH_RANK[tileId] ||
					   BoardUtils.THIRD_RANK[tileId] ||
					   BoardUtils.FIRST_RANK[tileId]) {
				setBackground(this.tileId % 2 == 0 ? DARK_TILE_COLOR : LIGHT_TILE_COLOR);
			}
		}
	
	}
	
	public enum BoardDirection {
		
		NORMAL {

			@Override
			public List<TilePanel> traverse(final List<TilePanel> boardTiles) {
				return boardTiles;
			}

			@Override
			public BoardDirection opposite() {
				return FLIPPED;
			}
			
		},
		
		FLIPPED {

			@Override
			public List<TilePanel> traverse(final List<TilePanel> boardTiles) {
				final List<TilePanel> reversed = new ArrayList<>();
				for (TilePanel tilePanel : boardTiles) {
					reversed.add(0, tilePanel);
				}
				return reversed;
			}

			@Override
			public BoardDirection opposite() {
				return NORMAL;
			}
			
		};
		
		public abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
		public abstract BoardDirection opposite();
		
	}
	
	public static class MoveLog {
		private final List<Move> moves;
		
		MoveLog() {
			this.moves = new ArrayList<>();
		}
		
		public List<Move> getMoves() {
			return this.moves;
		}
		
		public void addMove(final Move move) {
			this.moves.add(move);
		}
		
		public int size() {
			return this.moves.size();
		}
		
		public Move removeMove(int index) {
			return this.moves.remove(index);
		}
		
		public boolean removeMove(final Move move) {
			return this.moves.remove(move);
		}
		
	}
	
}
