package com.github.cbl.chess.chess;

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

public class Board {
    public static final int A1 = 0;
    public static final int B1 = 1;
    public static final int C1 = 2;
    public static final int D1 = 3;
    public static final int E1 = 4;
    public static final int F1 = 5;
    public static final int G1 = 6;
    public static final int H1 = 7;

    public static final int A2 = 8;
    public static final int B2 = 9;
    public static final int C2 = 10;
    public static final int D2 = 11;
    public static final int E2 = 12;
    public static final int F2 = 13;
    public static final int G2 = 14;
    public static final int H2 = 15;

    public static final int A3 = 16;
    public static final int B3 = 17;
    public static final int C3 = 18;
    public static final int D3 = 19;
    public static final int E3 = 20;
    public static final int F3 = 21;
    public static final int G3 = 22;
    public static final int H3 = 23;

    public static final int A4 = 24;
    public static final int B4 = 25;
    public static final int C4 = 26;
    public static final int D4 = 27;
    public static final int E4 = 28;
    public static final int F4 = 29;
    public static final int G4 = 30;
    public static final int H4 = 31;

    public static final int A5 = 32;
    public static final int B5 = 33;
    public static final int C5 = 34;
    public static final int D5 = 35;
    public static final int E5 = 36;
    public static final int F5 = 37;
    public static final int G5 = 38;
    public static final int H5 = 39;

    public static final int A6 = 40;
    public static final int B6 = 41;
    public static final int C6 = 42;
    public static final int D6 = 43;
    public static final int E6 = 44;
    public static final int F6 = 45;
    public static final int G6 = 46;
    public static final int H6 = 47;

    public static final int A7 = 48;
    public static final int B7 = 49;
    public static final int C7 = 50;
    public static final int D7 = 51;
    public static final int E7 = 52;
    public static final int F7 = 53;
    public static final int G7 = 54;
    public static final int H7 = 55;

    public static final int A8 = 56;
    public static final int B8 = 57;
    public static final int C8 = 58;
    public static final int D8 = 59;
    public static final int E8 = 60;
    public static final int F8 = 61;
    public static final int G8 = 62;
    public static final int H8 = 63;

    public static final int SQUARE_COUNT = 64;
    public static final int SQUARE_NONE = 64;

    public static final int FILE_A = 0;
    public static final int FILE_B = 1;
    public static final int FILE_C = 2;
    public static final int FILE_D = 3;
    public static final int FILE_E = 4;
    public static final int FILE_F = 5; 
    public static final int FILE_G = 6;
    public static final int FILE_H = 7;
    public static final int FILE_NONE = 8;

    public static final int RANK_1 = 0;
    public static final int RANK_2 = 1;
    public static final int RANK_3 = 2;
    public static final int RANK_4 = 3;
    public static final int RANK_5 = 4;
    public static final int RANK_6 = 5;
    public static final int RANK_7 = 6;
    public static final int RANK_8 = 7;
    public static final int RANK_NONE = 8;

    public static final int[] SQUARES = {
        A1,B1,C1,D1,E1,F1,G1,H1,
        A2,B2,C2,D2,E2,F2,G2,H2,
        A3,B3,C3,D3,E3,F3,G3,H3,
        A4,B4,C4,D4,E4,F4,G4,H4,
        A5,B5,C5,D5,E5,F5,G5,H5,
        A6,B6,C6,D6,E6,F6,G6,H6,
        A7,B7,C7,D7,E7,F7,G7,H7,
        A8,B8,C8,D8,E8,F8,G8,H8,
    };

    public static final long[] BB_SQUARES = generateBBSquares();

    public static final String[] FILE_NAMES = {"a", "b", "c", "d", "e", "f", "g", "h"};
    public static final String[] RANK_NAMES = {"1", "2", "3", "4", "5", "6", "7", "8"};

    public static class Block {
        public static final int SQUARE_SIZE = 6;
        public static final int SQUARE = ~(~1 << 5);
    }

    /**
     * Generate bitboard squares.
     */
    private static final long[] generateBBSquares()
    {
        long[] bbSquares = new long[SQUARE_COUNT];

        for(int sq = A1;sq<=H8;sq++) bbSquares[sq] = 0x1L << sq;

        return bbSquares;
    }

    /**
     * Get distance between two squares.
     */
    public static int distance(int a, int b)
    {
        return Math.max(
            Math.abs(getFile(a) - getFile(b)), 
            Math.abs(getRank(a) - getRank(b))
        );
    }

    /**
     * Determine whether the given square is on board.
     */
    public static boolean isOnBoard(int square) {
        return square >= A1 && square <= H8;
    }

    /**
     * Determine whether the given square is white.
     */
    public static boolean isWhite(int square) {
        return getRank(square) % 2 != getFile(square) % 2;
    }

    /**
     * Make a new square from the intersection of the rank and the file.
     */
    public static int square(int rank, int file) {
        return rank * 8 + file;
    }

    /**
     * Make a new square from the intersection of the rank and the file.
     */
    public static int safeSquare(int square) {
        return isOnBoard(square) ? square : SQUARE_NONE;
    }

    /**
     * Determine whether the given square is on the given rank.
     */
    public static boolean isRank(int square, int rank) {
        return getRank(square) == rank;
    }

    /**
     * Determine whether the given square is on the given file.
     */
    public static boolean isFile(int square, int file) {
        return getFile(square) == file;
    }

    /**
     * Get the file of the given square.
     */
    public static int getFile(int square) {
        return square % 8;
    }

    /**
     * Get the rank of the given square.
     */
    public static int getRank(int square) {
        return square / 8;
    }

    /**
     * Convert board Bitboard square to square.
     */
    public static int fromBB(long square) {
        return Long.numberOfTrailingZeros(square);
    }

    /**
     * Get Bitboard square if the given square is on the board, otherwise 
     * return 0.
     */
    public static long getSafeBBSquare(int square) {

        return isOnBoard(square) ? BB_SQUARES[square] : 0;
    }

    /**
     * Get list of squares from Bitboard.
     */
    public static List<Integer> toList(long bb) {
        List<Integer> squares = new ArrayList<Integer>();

        for(int sq=Board.A1;sq<=Board.H8;sq++) {
            if(Bitboard.valueAt(bb, sq)) squares.add(sq);
        }

        return squares;
    }

    /**
     * Get reversed list of squares from Bitboard.
     */
    public static List<Integer> toReversedList(long bb) {
        List<Integer> squares = new ArrayList<Integer>();

        for(int sq=Board.H8;sq>=Board.A1;sq--) {
            if(Bitboard.valueAt(bb, sq)) squares.add(sq);
        }

        return squares;
    }

    /**
     * Get the string reprensentation of a square.
     */
    public static String squareToString(int square) {
        return FILE_NAMES[getFile(square)] + RANK_NAMES[getRank(square)];
    }
}
