package com.github.cbl.chess;

import com.github.cbl.chess.chess.BitBoard;
import com.github.cbl.chess.chess.Board;
import com.github.cbl.chess.chess.Piece;

public class Main {
    public static void main(String[] args) throws Exception {
        // long board = 0;




        System.out.println(Board.isWhite(Board.B2));
        Board b = new Board();
        b.addPiece(Board.A7, Piece.PAWN, Piece.Color.BLACK);
        b.addPiece(Board.B7, Piece.PAWN, Piece.Color.BLACK);
        b.addPiece(Board.C7, Piece.PAWN, Piece.Color.BLACK);
        b.addPiece(Board.D7, Piece.PAWN, Piece.Color.BLACK);
        b.addPiece(Board.E7, Piece.PAWN, Piece.Color.BLACK);
        b.addPiece(Board.F7, Piece.PAWN, Piece.Color.BLACK);
        b.addPiece(Board.G7, Piece.PAWN, Piece.Color.BLACK);
        b.addPiece(Board.H7, Piece.PAWN, Piece.Color.BLACK);
        b.addPiece(Board.F7, Piece.PAWN, Piece.Color.BLACK);
        b.addPiece(Board.E5, Piece.PAWN, Piece.Color.BLACK);
        
        // b.move(Board.F7, Board.F6);
        // b.move(Board.E5, Board.A1);
        System.out.println(b.toAscii(b.getLegalMoves(Board.E5)));
        // System.out.println(BitBoard.toAscii(b.getLegalMoves(Board.G4)));
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