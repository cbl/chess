package com.github.cbl.chess.notations;

import com.github.cbl.chess.chess.Board;
import com.github.cbl.chess.chess.Position;

public class AlgebraicNotation implements Notation {
    public static final String fileToChar = "abcdefgh";

    public static int charToFile(char ch) {
        return fileToChar.indexOf(ch);
    }

    public static int parseSquare(String square)
    {
        return 8 * (square.charAt(1) - '0' - 1) + charToFile(square.charAt(0));
    }

    public static long parseBBSquare(String square)
    {
        return Board.getBBSquare(parseSquare(square));
    }

     /**
     * Generate a board from the given notation.
     */
    public Position parse(String notation)
    {
        return new Position();
    }

    /**
     * Generate the notation for a given position.
     */
    public String compose(Position position)
    {
        return "";
    }
}
