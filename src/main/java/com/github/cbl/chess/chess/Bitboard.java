package com.github.cbl.chess.chess;

import java.util.ArrayList;
import java.util.List;

public class Bitboard {
    public static final long ALL = -1;
    public static final long EMPTY = 0;
    public static final long LIGHT_SQUARES = 0x55AA55AA55AA55AAL;
    public static final long DARK_SQUARES = 0xAA55AA55AA55AA55L;
    public static final long ALL_SQUARES = LIGHT_SQUARES | DARK_SQUARES;

    public static final long FILE_A = 0x0101010101010101L;
    public static final long FILE_B = FILE_A << 1;
    public static final long FILE_C = FILE_A << 2;
    public static final long FILE_D = FILE_A << 3;
    public static final long FILE_E = FILE_A << 4;
    public static final long FILE_F = FILE_A << 5;
    public static final long FILE_G = FILE_A << 6;
    public static final long FILE_H = FILE_A << 7;

    public static final long RANK_1 = 0xFF;
    public static final long RANK_2 = RANK_1 << (8 * 1);
    public static final long RANK_3 = RANK_1 << (8 * 2);
    public static final long RANK_4 = RANK_1 << (8 * 3);
    public static final long RANK_5 = RANK_1 << (8 * 4);
    public static final long RANK_6 = RANK_1 << (8 * 5);
    public static final long RANK_7 = RANK_1 << (8 * 6);
    public static final long RANK_8 = RANK_1 << (8 * 7);

    public static final long WHITE_SIDE = RANK_1 | RANK_2| RANK_3 | RANK_4;
    public static final long BLACK_SIDE = RANK_5 | RANK_6 | RANK_7 | RANK_8;
    public static final long QUEEN_SIDE = FILE_A | FILE_B | FILE_C | FILE_D;
    public static final long KING_SIDE = FILE_E | FILE_F | FILE_G | FILE_H;

    public static final long A1 = FILE_A & RANK_1;
    public static final long B1 = FILE_B & RANK_1;
    public static final long C1 = FILE_C & RANK_1;
    public static final long D1 = FILE_D & RANK_1;
    public static final long E1 = FILE_E & RANK_1;
    public static final long F1 = FILE_F & RANK_1;
    public static final long G1 = FILE_G & RANK_1;
    public static final long H1 = FILE_H & RANK_1;

    public static final long A2 = FILE_A & RANK_2;
    public static final long B2 = FILE_B & RANK_2;
    public static final long C2 = FILE_C & RANK_2;
    public static final long D2 = FILE_D & RANK_2;
    public static final long E2 = FILE_E & RANK_2;
    public static final long F2 = FILE_F & RANK_2;
    public static final long G2 = FILE_G & RANK_2;
    public static final long H2 = FILE_H & RANK_2;

    public static final long A3 = FILE_A & RANK_3;
    public static final long A1_A2 = A1 | A2;
    public static final long S_A1_A2 = A1 + A2;
    public static final long T_A1_A2 = (RANK_1 | RANK_2) & FILE_A;

    public static final long B3 = FILE_B & RANK_3;
    public static final long C3 = FILE_C & RANK_3;
    public static final long D3 = FILE_D & RANK_3;
    public static final long E3 = FILE_E & RANK_3;
    public static final long F3 = FILE_F & RANK_3;
    public static final long G3 = FILE_G & RANK_3;
    public static final long H3 = FILE_H & RANK_3;

    public static final long A4 = FILE_A & RANK_4;
    public static final long B4 = FILE_B & RANK_4;
    public static final long C4 = FILE_C & RANK_4;
    public static final long D4 = FILE_D & RANK_4;
    public static final long E4 = FILE_E & RANK_4;
    public static final long F4 = FILE_F & RANK_4;
    public static final long G4 = FILE_G & RANK_4;
    public static final long H4 = FILE_H & RANK_4;

    public static final long A5 = FILE_A & RANK_5;
    public static final long B5 = FILE_B & RANK_5;
    public static final long C5 = FILE_C & RANK_5;
    public static final long D5 = FILE_D & RANK_5;
    public static final long E5 = FILE_E & RANK_5;
    public static final long F5 = FILE_F & RANK_5;
    public static final long G5 = FILE_G & RANK_5;
    public static final long H5 = FILE_H & RANK_5;

    public static final long A6 = FILE_A & RANK_6;
    public static final long B6 = FILE_B & RANK_6;
    public static final long C6 = FILE_C & RANK_6;
    public static final long D6 = FILE_D & RANK_6;
    public static final long E6 = FILE_E & RANK_6;
    public static final long F6 = FILE_F & RANK_6;
    public static final long G6 = FILE_G & RANK_6;
    public static final long H6 = FILE_H & RANK_6;

    public static final long A7 = FILE_A & RANK_7;
    public static final long B7 = FILE_B & RANK_7;
    public static final long C7 = FILE_C & RANK_7;
    public static final long D7 = FILE_D & RANK_7;
    public static final long E7 = FILE_E & RANK_7;
    public static final long F7 = FILE_F & RANK_7;
    public static final long G7 = FILE_G & RANK_7;
    public static final long H7 = FILE_H & RANK_7;
    
    public static final long A8 = FILE_A & RANK_8;
    public static final long B8 = FILE_B & RANK_8;
    public static final long C8 = FILE_C & RANK_8;
    public static final long D8 = FILE_D & RANK_8;
    public static final long E8 = FILE_E & RANK_8;
    public static final long F8 = FILE_F & RANK_8;
    public static final long G8 = FILE_G & RANK_8;
    public static final long H8 = FILE_H & RANK_8;

    public static final long DIAGONAL_A1_H8 = A1 | B2 | C3 | D4 | E5 | F6 | G7 | H8;
    public static final long DIAGONAL_A8_H1 = A8 | B7 | C6 | D5 | E4 | F3 | G2 | H1;
    public static final long ROOK_SQUARES = A1 | A8 | H1 | H8;
    public static final long OUTLINE = Bitboard.RANK_1 | Bitboard.RANK_8 | Bitboard.FILE_A | Bitboard.FILE_H;

    public static final long KNIGHT_MASK = B1 | A2;

    public static final long[] FILES = {
        FILE_A, FILE_B, FILE_C, FILE_D, FILE_E, FILE_F, FILE_G, FILE_H
    };

    public static final long[] RANKS = {
        RANK_1, RANK_2, RANK_3, RANK_4, RANK_5, RANK_6, RANK_7, RANK_8
    };

    /**
     * Make a new square from the intersection of the rank and the file.
     */
    public static long makeSquare(long rank, long file) {
        return rank & file;
    }

    public static long safeDestination(int square, int step) {
        int to = square + step;
        return Board.isOnBoard(to) && Board.distance(square, to) <= 2 
            ? Board.BB_SQUARES[to] : 0;
    }

    public static long safe(long board) {
        return board & (QUEEN_SIDE | KING_SIDE);
    }

    public static long file(int square) {
        return FILE_A << Board.getFile(square);
    }

    public static long rank(int square) {
        return RANK_1 << (8 * Board.getRank(square));
    }

    /**
     * Determines whether the given square is on the board.
     */
    public static boolean isSquareOnBoard(long square) {
        return (ALL_SQUARES & square) != 0;
    }

    /**
     * Shift up.
     */
    public static long shiftUp(long board, int times) {
        return (board << (8 * times)) & (~0 << 8 * times);
    }

    /**
     * Shift down.
     */
    public static long shiftDown(long square, int times) {
        return square >> (8 * times) & (~RANK_8 >> (8 * --times));
    }

    /**
     * Shift right.
     */
    public static long shiftRight(long board, int times) {
        long outsideFiles = 0;

        for(int i=0;i<times;i++) {
            outsideFiles |= FILE_A << i;
        }

        return (board << times) & ~outsideFiles;
    }

    /**
     * Shift left.
     */
    public static long shiftLeft(long board, int times) {
        long outsideFiles = 0;

        for(int i=0;i<times;i++) {
            outsideFiles |= FILE_H >> i;
        }
        
        return (board >> times) & ~outsideFiles;
    }

    /**
     * Shift up right.
     */
    public static long shiftUpRight(long board, int times) {
        return ((board & ~FILE_A) << (9 * times)) & (~FILE_A << 8);
    }

    /**
     * Shift up left.
     */
    public static long shiftUpLeft(long board, int times) {
        return (board & ~FILE_A) << (7 * times);
    }

    /**
     * Shift down right.
     */
    public static long shiftDownRight(long board, int times) {
        return (board & ~FILE_A) >> (7 * times);
    }

    /**
     * Shift down left.
     */
    public static long shiftDownLeft(long board, int times) {
        return (board & ~FILE_A) >> (9 * times);
    }

    /**
     * Set a square on the board.
     */
    public static long set(long board, long square) {
        return board | square;
    }

    /**
     * Unset a square on the board.
     */
    public static long unset(long board, long square) {
        return board & ~square;
    }

    /**
     * Get the bit at the given square.
     */
    public static boolean valueAt(long board, long square) {
        return (board & square) != 0;
    }

    /**
     * Get the bit at the given square.
     */
    public static boolean valueAt(long board, int square) {
        return ((board >> square) & 1) == 1;
    }
 
    /**
     * Get the edges of the given square.
     */
    public static long edges(int square) {
        return (((RANK_1 | RANK_8) & ~RANKS[Board.getRank(square)]) 
             | ((FILE_A | FILE_H) & ~FILES[Board.getFile(square)]));
    }

    /**
     * Traverse subsets of a set.
     * 
     * @see https://www.chessprogramming.org/Traversing_Subsets_of_a_Set
     */
    public static List<Long> subsets(long set) {
        long subset = EMPTY;
        List<Long> subsets = new ArrayList<Long>();
        while(true) {
            subsets.add(subset);
            subset = (subset - set) & set;
            if(subset == 0) return subsets;
        }
    }

    /**
     * Get sliding attacks from the given square to the given directions.
     */
    public static long slidingAttacks(int sq, int[] directions) {
        return slidingAttacks(sq, directions, EMPTY);
    }

    /**
     * Get sliding attacks from the given square to the given directions. Filter 
     * blocked squares from the given occupation board.
     */
    public static long slidingAttacks(int square, int[] directions, long occupied) {
        long attacks = EMPTY;

        for(int direction : directions) {
            int sq = square;

            while(true) {
                sq += direction;

                if(!(0 <= sq && sq < 64) || Board.distance(sq, sq-direction) > 2) {
                    break;
                }

                attacks |= Board.BB_SQUARES[sq];

                if((occupied & Board.BB_SQUARES[sq]) != 0) break;
            }
        }

        return attacks;
    }

    /**
     * Get the "most significant bit" position form the given bitboard.
     * 
     * @see https://en.wikipedia.org/wiki/Bit_numbering#Most_significant_bit
     */
    public static int msb(long bb) {
        return Long.SIZE - Long.numberOfLeadingZeros(bb);
    }

    
    /**
     * Get the "least significant bit" position form the given bitboard.
     * 
     * @see https://en.wikipedia.org/wiki/Bit_numbering#Least_significant_bit
     */
    public static int lsb(long bb) {
        return Long.numberOfTrailingZeros(bb);
    }

    /**
     * Get list of squares from Bitboard.
     */
    public static List<Long> toList(long bb) {
        List<Long> squares = new ArrayList<Long>();

        for(int sq=Board.A1;sq<=Board.H8;sq++) {
            if(valueAt(bb, sq)) squares.add(Board.BB_SQUARES[sq]);
        }

        return squares;
    }

    /**
     * Get reversed list of squares from Bitboard.
     */
    public static List<Long> toReversedList(long bb) {
        List<Long> squares = new ArrayList<Long>();

        for(int sq=Board.H8;sq>=Board.A1;sq--) {
            if(valueAt(bb, sq)) squares.add(Board.BB_SQUARES[sq]);
        }

        return squares;
    }

    /**
     * Get the ASCII representation of a board.
     */
    public static String toAscii(long board) {
        String ascii = "    A B C D E F G H\n";
        ascii += "    ---------------\n";

        for (long r = 7; r >= 0; r--) {
            ascii += (r + 1) + " | ";
            for (long f = 0; f <= 7; f++) {
                long square = makeSquare(RANK_1 << (8 * r), FILE_A << f);
                ascii += valueAt(board, square) ? "1 " : ". ";
            }
            ascii += "| " + (r + 1) + "\n";
        }
        ascii += "    ---------------\n";
        ascii += "    A B C D E F G H";

        return ascii.toString();
    }
}
