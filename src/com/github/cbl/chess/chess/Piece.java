package com.github.cbl.chess.chess;

public class Piece {
    public static final int NONE = 0;
    public static final int PAWN = 1;
    public static final int KNIGHT = 2;
    public static final int BISHOP = 3;
    public static final int ROOK = 4;
    public static final int QUEEN = 5;
    public static final int KING = 6;
    public static final int ALL = PAWN | KNIGHT | BISHOP | ROOK | QUEEN | KING;

    public static int[] VALUES = { 0, 1, 3, 3, 5, 7, 0 };

    public static class Block {
        public static final int TYPE = 7;
        public static final int COLOR = 0b11 << 3;

        public static final int TYPE_SIZE = 3;
        public static final int COLOR_SIZE = 2;
    }

    public static class Color {
        public static final int WHITE = 8;
        public static final int BLACK = 16;
        public static final int ALL = WHITE | BLACK;

        public static int opposite(int color) {
            return ~color & Block.COLOR;
        }
    }

    public static int make(int type, int color) {
        return color | type;
    }

    public static int getValue(int piece) {
        return VALUES[getType(piece)];
    }

    public static int getColor(int piece) {
        return piece & Block.COLOR;
    }

    public static boolean isColor(int piece, int color) {
        return (piece & Block.COLOR) == color;
    }

    public static int getType(int piece) {
        return piece & ALL;
    }

    public static boolean isType(int piece, int type) {
        return (piece & ALL) == type;
    }

    /**
     * Get the ASCII representation of a board.
     */
    public static String toAscii(int piece) {
        String[] white = { "♙", "♘", "♗", "♖", "♕", "♔" };
        String[] black = { "♟︎", "♞", "♝", "♜", "♛", "♚" };
        // String letter = letters[getType(piece)-1];
        return isColor(piece, Color.WHITE) ? white[getType(piece)-1] : black[getType(piece)-1];
    }
}
