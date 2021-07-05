package com.github.cbl.chess.notations;

import com.github.cbl.chess.chess.Position;

public interface Notation {
    /**
     * Create a Position from the given notation.
     */
    public Position parse(String notation);

    /**
     * Compose the notation for a given position.
     */
    public String compose(Position posiion);
}
