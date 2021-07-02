package board;

import types.Color;
import types.Piece;

public class BoardPiece {
    public Color color;
    public Piece piece;

    BoardPiece(Piece piece, Color color) {
        this.piece = piece;
        this.color = color;
    }

    @Override
    public String toString() {
        String name = "";
        if (this.piece == Piece.Pawn) {
            name = "p";
        }
        if (this.piece == Piece.Knight) {
            name = "n";
        }
        if (this.piece == Piece.Bishop) {
            name = "b";
        }
        if (this.piece == Piece.Rook) {
            name = "r";
        }
        if (this.piece == Piece.Queen) {
            name = "q";
        }
        if (this.piece == Piece.King) {
            name = "k";
        }

        if (this.color == Color.White) {
            name = name.toUpperCase();
        }

        return name;
    }
}
