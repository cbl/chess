
import board.Board;
import board.BoardPiece;
import types.Color;
import types.Piece;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        List<String> pieces = new ArrayList<String>();

        board.addPiece(Piece.Pawn, Color.White, 8);

        System.out.println("+---+---+---+---+---+---+---+---+");
        for (int i = 63; i >= 0; i--) {
            BoardPiece piece = board.getPieceAt(i);
            String name = piece == null ? " " : piece.toString();
            pieces.add(name);
            if ((i % 8) == 0) {
                Collections.reverse(pieces);
                System.out.print("| " + String.join(" | ", pieces));
                pieces = new ArrayList<String>();
                System.out.println(" |\n+---+---+---+---+---+---+---+---+");
            }
        }
    }
}
