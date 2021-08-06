package com.github.cbl.chess.notations;

import com.github.cbl.chess.chess.Position;
import java.lang.Exception;

public interface Notation {

    class InvalidFormatException extends Exception {};

    /**
     * Create a Position from the given notation.
     */
    public Position parse(String notation) throws InvalidFormatException;

    /**
     * Compose the notation for a given position.
     */
    public String compose(Position posiion);
}
