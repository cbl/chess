package com.github.cbl.chess.chess;

import java.lang.Math;

/**
 * The MoveIndex is an index to lookup all possible pseudo moves for any piece 
 * at any square.
 */
public class AttackIndex {
    public static long[][] pawns = new long[3][];
    public static long[][] pseudo = new long[7][];

    /**
     * Singleton pattern.
     */
    protected static final AttackIndex instance = new AttackIndex();

    /**
     * Generate pseudo attacks.
     */
    AttackIndex() {
        this.initIndexes();

        for(int sq=Board.A1;sq<=Board.H8;sq++) {
            pawns[Piece.Color.WHITE][sq] = pawnAttacks(Piece.Color.WHITE, sq);
            pawns[Piece.Color.BLACK][sq] = pawnAttacks(Piece.Color.BLACK, sq);

            for (int move: Move.KNIGHT_MOVES) {
                if(Math.abs(Board.getFile(sq + move) - Board.getFile(sq)) > 2) continue;
                if(Math.abs(Board.getRank(sq + move) - Board.getRank(sq)) > 2) continue;
                pseudo[Piece.KNIGHT][sq] |= Board.getSafeBBSquare(sq + move);
            }

            for(int move: Move.BISHOP_MOVES) {
                if(move > 0 && (move%7) == 0 && ((sq+move)%8) > (sq%8)) continue;
                if(move > 0 && (move%9) == 0 && ((sq+move)%8) < (sq%8)) continue;
                if(move < 0 && (move%9) == 0 && (Math.abs(sq+move)%8) > (sq%8)) continue;
                if(move < 0 && (move%7) == 0 && (Math.abs(sq+move)%8) < (sq%8)) continue;
                pseudo[Piece.BISHOP][sq] |= Board.getSafeBBSquare(sq + move);
                pseudo[Piece.QUEEN][sq] |= Board.getSafeBBSquare(sq + move);
            }

            for(int move: Move.ROOK_MOVES) {
                if((sq/8) != ((sq+move)/8) && Math.abs(move) < 8) continue;
                pseudo[Piece.ROOK][sq] |= Board.getSafeBBSquare(sq + move);
                pseudo[Piece.QUEEN][sq] |= Board.getSafeBBSquare(sq + move);
            }

            for (int move: Move.KING_MOVES)
                pseudo[Piece.KING][sq] |= Board.getSafeBBSquare(sq + move);
        }
    }

    protected void initIndexes() {
        for(int i=0;i<7;i++) {
            if(i==Piece.Color.WHITE || i==Piece.Color.BLACK)
                pawns[i] = new long[Board.SQUARE_COUNT];
            pseudo[i] = new long[Board.SQUARE_COUNT];
        }
    }

    protected long pawnAttacks(int color, int square) {
        int forward = color == Piece.Color.WHITE ? Move.UP : Move.DOWN;

        return BitBoard.safe(0
            | Board.getBBSquare(square + forward + Move.RIGHT)
            | Board.getBBSquare(square + forward + Move.LEFT)
        );
    }
}