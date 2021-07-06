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

    public static class Block {
        public static final int SQUARE_SIZE = 6;
        public static final int SQUARE = ~(~1 << 5);
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
    public static int makeSquare(int rank, int file) {
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
     * Convert board square to BitBoard square.
     */
    public static long getBBSquare(int square) {
        return 0x1L << square;
    }

    /**
     * Get BitBoard square if the given square is on the board, otherwise 
     * return 0.
     */
    public static long getSafeBBSquare(int square) {

        return isOnBoard(square) ? getBBSquare(square) : 0;
    }
}
