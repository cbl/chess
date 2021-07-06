package com.github.cbl.chess;

import com.github.cbl.chess.chess.BitBoard;
import com.github.cbl.chess.chess.Board;
import com.github.cbl.chess.chess.AttackIndex;
import com.github.cbl.chess.chess.Position;
import com.github.cbl.chess.chess.Piece;

import com.github.cbl.chess.notations.FenNotation;
import com.github.cbl.chess.notations.Notation;

public class Main {
    public static void main(String[] args) throws Exception {
        // long board = 0;

        Notation fen = new FenNotation();
        Position p = fen.parse(
            "4k3/8/2K5/p1P5/1b5B/1P6/P3p3/8 w - - 15 55"
            // "8/8/8/8/8/8/6b1/8 w KQkq - 1 6"
        );
        System.out.println(p.toAscii(p.legalMoves(Board.E8)));
        // System.out.println(
        //     BitBoard.toAscii(p.castlingRights)
        //     // BitBoard.toAscii(MoveIndex.pseudoAttackIndex[Piece.BISHOP][Board.G2])
        // );
        // System.out.println(BitBoard.toAscii(BitBoard.RANK_4 & BitBoard.FILE_D));
        // System.out.println(BitBoard.toAscii(b.getLegalMoves(Board.B4)));
        // System.out.println(BitBoard.toAscii(
        //     BitBoard.shiftRight(BitBoard.DIAGONAL_A1_H8, 3) & ~BitBoard.shiftUp(BitBoard.DIAGONAL_A1_H8, 6)
        // ));
        // System.out.println(BitBoard.toAscii(
        //     // BitBoard.shiftLeft(BitBoard.DIAGONAL_A1_H8, 1)
        //     // BitBoard.DIAGONAL_A1_H8
        // ));
        // System.out.println(BitBoard.toAscii(b.getLegalMoves(Board.H7)));
        // System.out.println(Board.getBBSquare(Board.H2));

        // board = BitBoard.set(board, BitBoard.Square.A1);
        // board = BitBoard.set(board, BitBoard.Square.C5);
        // System.out.println(BitBoard.valueAt(board, BitBoard.Square.A2));

        // System.out.println(BitBoard.toASCII(board));
    }
}

// 0 0
// 0 0

// 0001 => 1
// 0010 => 2