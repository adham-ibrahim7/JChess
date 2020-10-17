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
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
	
	private static String defaultPieceImagesPath = "art/fancy/";
	
	private final Color LIGHT_TILE_COLOR = Color.decode("#FFFACD");
	private final Color DARK_TILE_COLOR = Color.decode("#593E1A");
	
	private final JFrame gameFrame;
	private final BoardPanel boardPanel;
	private Board chessBoard;
	
	private Tile sourceTile;
	private Tile destinationTile;
	private Piece humanMovedPiece;
	
	public Table() {
		this.chessBoard = Board.createStandardBoard();
		
		this.gameFrame = new JFrame("JChess");
		this.gameFrame.setLayout(new BorderLayout());
		
		final JMenuBar tableMenuBar = createMenuBar();
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		
		this.boardPanel = new BoardPanel();
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
		
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameFrame.setVisible(true);
	}

	private JMenuBar createMenuBar() {
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		return tableMenuBar;
	}

	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		
		final JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
			for (final TilePanel tilePanel : boardTiles) {
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
								//TODO add the move that was made to the move log
							}
							
							sourceTile = null;
							destinationTile = null;
							humanMovedPiece = null;
						}
						
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								boardPanel.drawBoard(chessBoard);
							}
							
						});
					}
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
			validate();
			repaint();
		}

		private void assignTilePieceIcon(final Board board) {
			removeAll();
			
			if (board.getTile(this.tileId).isTileOccupied()) {
				try {
					final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath + 
							board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0, 1) + 
							board.getTile(this.tileId).getPiece().toString() + ".gif"));
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
	
}
