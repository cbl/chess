package board;

import types.Piece;
import moves.Factory;
import types.Color;

public class Board implements contracts.Board {

    private BoardPiece[] board = new BoardPiece[64];
    private Factory moves = new Factory();

    public Board() {

    }

    /**
     * Make a move. e.g.: e4
     */
    public void move(String move) {
        int pos = this.getPositionFromMove(move);

        return;
    }

    public int getPositionFromMove(String move) {
        return 1;
    }

    /**
     * Determine whether a move is legal. e.g.: e4
     */
    public boolean isLegal(String move) {
        return true;
    }

    /**
     * Get Piece at board position.
     */
    public BoardPiece getPieceAt(int position) {
        return this.board[position];
    }

    /**
     * Resign the game.
     */
    public void resign() {
        // TODO:
    }

    /**
     * Resign the game.
     */
    public BoardPiece[] getBoard() {
        return this.board;
    }

    public void addPiece(Piece piece, Color color, int position) {
        this.board[position] = new BoardPiece(piece, color);
    }
}
