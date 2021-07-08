package com.github.cbl.chess;

import com.github.cbl.chess.chess.BitBoard;
import com.github.cbl.chess.chess.Board;
import com.github.cbl.chess.chess.AttackIndex;
import com.github.cbl.chess.chess.Position;
import com.github.cbl.chess.console.CLI;
import com.github.cbl.chess.chess.Piece;
import com.github.cbl.chess.notations.FenNotation;
import com.github.cbl.chess.notations.Notation;
import com.github.cbl.chess.ui.DesktopUI;
import com.github.cbl.chess.ui.UI;
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
        CLI cli = new CLI();
        cli.run();
        // System.out.println(p.toAscii(p.pseudoLegalMoves(Board.D1)));
        // // System.out.println(  
        // //     // BitBoard.toAscii(p.)
        // //     BitBoard.toAscii(AttackIndex.pseudo[Piece.BISHOP][Board.G2])
        // // );
        // System.out.println(
        //     // BitBoard.toAscii(p.)
        //     BitBoard.toAscii(p.castlingRights)
        // );

        // UI.run();
        // Runnable r = new Runnable() {
        //     @Override
        //     public void run() {
        //         UI ui = new DesktopUI();
        //         ui.mount();
        //     }
        // };
        // SwingUtilities.invokeLater(r);
    }
}

// 0 0
// 0 0

// 0001 => 1
// 0010 => 2