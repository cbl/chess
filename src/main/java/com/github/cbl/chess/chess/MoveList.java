package com.github.cbl.chess.chess;

import java.util.ArrayList;

public class MoveList extends ArrayList<Move> {
    /**
     * Determine whether a move from one square to another exists in the list.
     */
    public boolean exists(int from, int to)
    {
        for(Move move : this) {
            if(move.from == from && move.to == to) return true;
        }

        return false;
    }

    /**
     * Get the move from a one square to another if it exists.
     */
    public Move get(int from, int to)
    {
        for(Move move : this) {
            if(move.from == from && move.to == to) return move;
        }

        return null;
    }

    /**
     * Gets all squares that can be reached by any move of the list.
     */
    public long getToMask()
    {
        long mask = 0;

        for(Move move : this) mask |= Board.BB_SQUARES[move.to];

        return mask;
    }

    /**
     * Gets all squares from that pieces can be moved by any move from list.
     */
    public long getFromMask()
    {
        long mask = 0;

        for(Move move : this) mask |= Board.BB_SQUARES[move.from];

        return mask;
    }
}
