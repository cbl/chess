package com.github.cbl.chess;

import com.github.cbl.chess.chess.Bitboard;
import com.github.cbl.chess.chess.Board;
import com.github.cbl.chess.chess.BBIndex;
import com.github.cbl.chess.chess.Position;
import com.github.cbl.chess.chess.Piece;
import com.github.cbl.chess.notations.FenNotation;
import com.github.cbl.chess.notations.Notation;
import com.github.cbl.chess.ui.BoardUi;
import com.github.cbl.chess.chess.GameOfChess;

import java.lang.Runnable;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) throws Exception {
        // long board = 0;

        Notation fen = new FenNotation();
        Position p = fen.parse(
            "rnbqkbnr/pp4pp/4p3/2ppPp2/5P2/1P3N2/P1PP2PP/RNBQKB1R b KQkq - 1 5"
            // "8/8/8/8/8/8/6b1/8 w KQkq - 1 6"
        );
        GameOfChess game = new GameOfChess(p);
        // CLI cli = new CLI();
        // cli.run();

        // UI.run();
        BoardUi ui = new BoardUi();
    }
}

// 0 0
// 0 0

// 0001 => 1
// 0010 => 2