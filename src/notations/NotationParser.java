package notations;

import chess.Board;

public interface NotationParser {
    /**
     * Generate a board from the given notation.
     */
    public Board parse(String notation);

    /**
     * Generate the notation for a given board.
     */
    public String compose(Board board);

    public int parseMove(String notation);

    /**
     * Generate the notation for a given board.
     */
    public String composeMove(int move);
}
