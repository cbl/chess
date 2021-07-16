package com.github.cbl.chess.chess;

import java.lang.Math;

/**
 * The AttackIndex class is an index to lookup all possible pseudo moves for any 
 * piece at any square.
 */
public class AttackIndex {
    public static long[][] pawns = new long[3][];
    public static long[][] pseudo = new long[7][];
    public static long[][] streched = new long[Board.SQUARE_COUNT][];
    public static long[] ranks = new long[Board.SQUARE_COUNT];
    public static long[] files = new long[Board.SQUARE_COUNT];
    public static long[] diagonals = new long[Board.SQUARE_COUNT];

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

            AttackIndex.files[sq] = BitBoard.FILES[Board.getFile(sq)];
            AttackIndex.ranks[sq] = BitBoard.RANKS[Board.getRank(sq)];
            AttackIndex.diagonals[sq] = pseudo[Piece.BISHOP][sq];
        }

        this.strechIndex();
    }

    protected void strechIndex()
    {
        for(int a : Board.SQUARES) {
            streched[a] = new long[Board.SQUARE_COUNT];
            
            for(int b : Board.SQUARES) {
                long bbB = Board.BB_SQUARES[b];

                if((diagonals[a] & bbB) != 0) {
                    streched[a][b] = diagonals[a];
                } else if((ranks[a] & bbB) != 0) {
                    streched[a][b] = ranks[a];
                } else if((files[a] & bbB) != 0) {
                    streched[a][b] = files[a];
                }
            }
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
        long attacks = 0;

        if(Board.isOnBoard(square + forward + Move.RIGHT))
            attacks |= Board.BB_SQUARES[square + forward + Move.RIGHT];
        if(Board.isOnBoard(square + forward + Move.LEFT))
            attacks |= Board.BB_SQUARES[square + forward + Move.LEFT];

        return attacks;
    }
}