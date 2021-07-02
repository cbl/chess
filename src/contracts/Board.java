package contracts;

import board.BoardPiece;
import types.Color;
import types.Piece;

public interface Board {

    /**
     * Make a move. e.g.: e4
     */
    public void move(String move);

    /**
     * Determine whether a move is legal. e.g.: e4
     */
    public boolean isLegal(String move);

    /**
     * Get Piece at board position.
     */
    public BoardPiece getPieceAt(int position);

    /**
     * Resign the game.
     */
    public void resign();

    public BoardPiece[] getBoard();

    public void addPiece(Piece piece, Color color, int position);
}
