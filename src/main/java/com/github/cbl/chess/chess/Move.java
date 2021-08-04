package com.github.cbl.chess.chess;

public class Move {
    public static final int UP = 8;
    public static final int DOWN = -UP;
    public static final int RIGHT = 1;
    public static final int LEFT = -RIGHT;
    public static final int UP_RIGHT = UP + RIGHT;
    public static final int UP_LEFT = UP + LEFT;
    public static final int DOWN_RIGHT = DOWN + RIGHT;
    public static final int DOWN_LEFT = DOWN + LEFT;

    protected static final int[] KNIGHT_MOVES = {-17, -15, -10, -6, 6, 10, 15, 17};
    protected static final int[] BISHOP_MOVES = {
        9, 9*2, 9*3, 9*4, 9*5, 9*6, 9*7,
        7, 7*2, 7*3, 7*4, 7*5, 7*6, 7*7,
        -9, -9*2, -9*3, -9*4, -9*5, -9*6, -9*7,
        -7, -7*2, -7*3, -7*4, -7*5, -7*6, -7*7,
    };
    protected static final int[] ROOK_MOVES = {
        1, 2, 3, 4, 5, 6, 7,
        -1, -2, -3, -4, -5, -6, -7,
        8, 8*2, 8*3, 8*4, 8*5, 8*6, 8*7,
        -8, -8*2, -8*3, -8*4, -8*5, -8*6, -8*7,
    };
    protected static final int[] KING_MOVES = {-9, -8, -7, -1, 1, 7, 8, 9};

    public static final int MAX_MOVES_COUNT = 256;

    public static int pawn(int color) {
        return color == Piece.Color.WHITE ? UP : DOWN;
    }

    public int from;
    public int to;
    public int promotion;

    /**
     * Create new Move instance.
     */
    public Move(int from, int to)
    {
        this.from = from;
        this.to = to;
        this.promotion = 0;
    }

    /**
     * Create new Move instance.
     */
    public Move(int from, int to, int promotion)
    {
        this.from = from;
        this.to = to;
        this.promotion = promotion;
    }

    /**
     * Determines if the given move is a capture or a pawn move.
     * 
     * This is used to find out if a draw can be claimed under the 50 move rule:
     * > The fifty-move rule in chess states that a player can claim a draw if 
     * > no capture has been made and no pawn has been moved in the last fifty 
     * > moves.
     * 
     * @see https://en.wikipedia.org/wiki/Fifty-move_rule
     */
    public boolean isZeroing(Position pos) {
        long touched = Board.BB_SQUARES[from] ^ Board.BB_SQUARES[to];

        return (touched & pos.pawns(pos.sideToMove)) != 0 
            || (touched & pos.piecesByColor(Piece.Color.opposite(pos.sideToMove))) != 0;
    }

    /**
     * Indicates if the move is a promotion.
     */
    public boolean isPromotion()
    {
        return this.promotion != 0;
    }

    /**
     * Get the moves distance.
     */
    public int distance()
    {
        return Board.distance(from, to);
    }
}
