package com.github.cbl.chess.chess;

public class SquareSet
{
    public long squares = 0;

    public SquareSet(long squares)
    {
        this.squares = squares;
    }

    public SquareSet(int[] squares)
    {
        for(int sq : squares) this.squares |= Board.BB_SQUARES[sq];
    }

    public SquareSet(long[] squares)
    {
        for(long sq : squares) this.squares |= sq;
    }

    public SquareSet add(long square)
    {
        this.squares |= square;

        return this;
    }

    public SquareSet add(int square)
    {
        this.squares |= Board.BB_SQUARES[square];

        return this;
    }

    public int length()
    {
        return Long.bitCount(this.squares);
    }
}