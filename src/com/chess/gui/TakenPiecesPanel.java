package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.gui.Table.MoveLog;

/*
 * Created by Adham Ibrahim on Oct 18, 2020
 */

@SuppressWarnings("serial")
public class TakenPiecesPanel extends JPanel {
	
	private final JPanel northPanel;
	private final JPanel southPanel;
	
	private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
	private static final Color PANEL_COLOR = Color.decode("#FDF5E6");
	private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40, 80);

	public TakenPiecesPanel() {
		super(new BorderLayout());
		
		this.setBackground(PANEL_COLOR);
		this.setBorder(PANEL_BORDER);
		
		this.northPanel = new JPanel(new GridLayout(8, 2));
		this.northPanel.setBackground(PANEL_COLOR);
		
		this.southPanel = new JPanel(new GridLayout(8, 2));
		this.southPanel.setBackground(PANEL_COLOR);
		
		this.add(this.northPanel, BorderLayout.NORTH);
		this.add(this.southPanel, BorderLayout.SOUTH);
		
		this.setPreferredSize(TAKEN_PIECES_DIMENSION);
	}
	
	public void redo(final MoveLog moveLog) {
		this.southPanel.removeAll();
		this.northPanel.removeAll();
		
		final List<Piece> whiteTakenPieces = new ArrayList<>();
		final List<Piece> blackTakenPieces = new ArrayList<>();
		
		for (final Move move : moveLog.getMoves()) {
			if (move.isAttack()) {
				final Piece takenPiece = move.getAttackedPiece();
				if (takenPiece.getPieceAlliance().isWhite()) {
					whiteTakenPieces.add(takenPiece);
				} else if (takenPiece.getPieceAlliance().isBlack()) {
					blackTakenPieces.add(takenPiece);
				} else {
					//TODO possibly remove?
					throw new RuntimeException("A piece is neither black nor white?");
				}
			}
		}
		
		addPieces(whiteTakenPieces, this.southPanel);
		addPieces(blackTakenPieces, this.northPanel);
		
		validate();
	}
	
	private void addPieces(final List<Piece> takenPieces, final JPanel panel) {
		Collections.sort(takenPieces);
		
		for (final Piece takenPiece : takenPieces) {
			try {
				//TODO clean up?
				panel.add(new JLabel(new ImageIcon(ImageIO.read(new File(takenPiece.getImageIconFilePath())))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
