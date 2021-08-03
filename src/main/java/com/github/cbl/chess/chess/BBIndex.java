package com.github.cbl.chess.chess;

import java.lang.Math;
import java.util.Map;
import java.util.HashMap;

/**
 * The BBIndex class generates various bitboard lookups.
 */
public class BBIndex {
    public static long[][] pawns = new long[3][];
    public static long[][] pseudo = new long[7][];
    public static long[][] blockers = new long[7][];
    public static long[][] streched = new long[Board.SQUARE_COUNT][];
    public static long[][] between = new long[Board.SQUARE_COUNT][];
    public static long[] ranks = new long[Board.SQUARE_COUNT];
    public static long[] files = new long[Board.SQUARE_COUNT];
    public static long[] diagonals = new long[Board.SQUARE_COUNT];

    public static Map<Long, Long>[] BISHOP_ATTACKS = new Map[Board.SQUARE_COUNT];
    public static Map<Long, Long>[] FILE_ATTACKS = new Map[Board.SQUARE_COUNT];
    public static Map<Long, Long>[] RANK_ATTACKS = new Map[Board.SQUARE_COUNT];

    public static long[] BISHOP_MASKS = new long[Board.SQUARE_COUNT];
    public static long[] FILE_MASKS = new long[Board.SQUARE_COUNT];
    public static long[] RANK_MASKS = new long[Board.SQUARE_COUNT];

    protected static int[] BISHOP_ATTACK_DIRECTIONS = {Move.UP_RIGHT, Move.UP_LEFT, Move.DOWN_RIGHT, Move.DOWN_LEFT};
    protected static int[] FILE_ATTACK_DIRECTIONS = {Move.UP, Move.DOWN};
    protected static int[] RANK_ATTACK_DIRECTIONS = {Move.LEFT, Move.RIGHT};

    /**
     * Singleton pattern.
     */
    protected static final BBIndex instance = new BBIndex();

    /**
     * Generate pseudo attacks.
     */
    BBIndex() {
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

            files[sq] = Bitboard.FILES[Board.getFile(sq)];
            ranks[sq] = Bitboard.RANKS[Board.getRank(sq)];
            diagonals[sq] = pseudo[Piece.BISHOP][sq];
            blockers[Piece.BISHOP][sq] = pseudo[Piece.BISHOP][sq] & ~Bitboard.OUTLINE;
            blockers[Piece.ROOK][sq] = pseudo[Piece.ROOK][sq] & ~Bitboard.OUTLINE;
            blockers[Piece.QUEEN][sq] = pseudo[Piece.QUEEN][sq] & ~Bitboard.OUTLINE;
        }

        this.strechIndex();
        setAttackTable(BISHOP_ATTACKS, BISHOP_MASKS, BISHOP_ATTACK_DIRECTIONS);
        setAttackTable(RANK_ATTACKS, RANK_MASKS, RANK_ATTACK_DIRECTIONS);
        setAttackTable(FILE_ATTACKS, FILE_MASKS, FILE_ATTACK_DIRECTIONS);
    }

    protected void setAttackTable(Map<Long, Long>[] table, long[] masks, int[] directions)
    {
        for(int sq=Board.A1;sq<=Board.H8;sq++) {
            Map<Long, Long> attacks = new HashMap<>();
            masks[sq] = Bitboard.slidingAttacks(sq, directions) & ~Bitboard.edges(sq);
            for(long subset : Bitboard.subsets(masks[sq])) {
                attacks.put(subset, Bitboard.slidingAttacks(sq, directions, subset));
            }
            table[sq] = attacks;
        }
    }

    protected void strechIndex()
    {
        for(int a : Board.SQUARES) {
            long bbA = Board.BB_SQUARES[a];
            streched[a] = new long[Board.SQUARE_COUNT];
            between[a] = new long[Board.SQUARE_COUNT];
            
            for(int b : Board.SQUARES) {
                long bbB = Board.BB_SQUARES[b];

                if((diagonals[a] & bbB) != 0) {
                    streched[a][b] =  (diagonals[a] & diagonals[b]) | bbA | bbB;
                } else if((ranks[a] & bbB) != 0) {
                    streched[a][b] = ranks[a] | bbA;
                } else if((files[a] & bbB) != 0) {
                    streched[a][b] = files[a] | bbA;
                }
                long bb = streched[a][b] & ((Bitboard.ALL << a) ^ (Bitboard.ALL << b));
                between[a][b] = bb & (bb - 1);
            }
        }
    }

    protected void initIndexes() {
        for(int i=0;i<7;i++) {
            if(i==Piece.Color.WHITE || i==Piece.Color.BLACK)
                pawns[i] = new long[Board.SQUARE_COUNT];
            pseudo[i] = new long[Board.SQUARE_COUNT];
            blockers[i] = new long[Board.SQUARE_COUNT];
        }
    }

    protected long pawnAttacks(int color, int square) {
        int forward = color == Piece.Color.WHITE ? Move.UP : Move.DOWN;
        int upperRank = color == Piece.Color.WHITE ? Board.RANK_8 : Board.RANK_1;
        long attacks = 0;

        if(Board.getRank(square) == upperRank)
            return attacks;

        if(Board.getFile(square) != Board.FILE_H)
            attacks |= Board.BB_SQUARES[square + forward + Move.RIGHT];
        if(Board.getFile(square) != Board.FILE_A)
            attacks |= Board.BB_SQUARES[square + forward + Move.LEFT];

        return attacks;
    }
}