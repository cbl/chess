package notations;

import chess.Board;

public class AlgebraicNotation implements NotationParser {
     /**
     * Generate a board from the given notation.
     */
    public Board parse(String notation)
    {
        return new Board();
    }

    /**
     * Generate the notation for a given board.
     */
    public String compose(Board board)
    {
        return "";
    }

    public int parseMove(String notation)
    {
        // e4
        return Board.E4;
    }

    /**
     * Generate the notation for a given board.
     */
    public String composeMove(int move)
    {
        return "";
    }
}
