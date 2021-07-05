package com.github.cbl.chess.chess;

public class Position {
    public int[] pieces = new int[Board.SQUARE_COUNT];
    public int[] moves = new int[Move.MAX_MOVES_COUNT];
    public int sideToMove = Piece.Color.WHITE;
    public int moveIndex = -1;
    public long castlingRights = 0;
    public long enPassantSquare = 0;
    public long halfmoveCount = 0;
    public long moveNumber = 0;

    public void addPiece(int square, int type, int color) {
        this.pieces[square] = color | type;
    }

    public void addPiece(int square, int piece) {
        this.pieces[square] = piece;
    }

    public long getLegalMoves(int square) {
        return MoveGenerator.getLegal(
            this.pieces, this.moves, this.moveIndex, square
        );
    }

    public void setSideToMove(int color) {
        this.sideToMove = color;
    }

    public void move(int from, int to) {
        int move = Move.make(Move.Type.NORMAL, this.pieces[from], from, to);
        this.moves[++this.moveIndex] = move;
        this.sideToMove = Piece.Color.opposite(this.sideToMove);
        this.pieces[to] = this.pieces[from];
        this.pieces[from] = 0;
    }

    public void setCastlingRight(int color, int rookSquare) {
        if(rookSquare == Board.SQUARE_NONE) return;

        long ranks = color == Piece.Color.WHITE 
            ? BitBoard.WHITE_SIDE 
            : BitBoard.BLACK_SIDE;
        long side = Board.FILE_E < Board.getFile(rookSquare) 
            ? BitBoard.KING_SIDE
            : BitBoard.QUEEN_SIDE;

        castlingRights |= ranks & side;
    }

    public void setEnPassantSquare(int square) {
        this.enPassantSquare = Board.getBBSquare(square);
    }

    public void setEnPassantSquare(long bbSquare) {
        this.enPassantSquare = bbSquare;
    }

    /**
     * Get the ASCII representation of a board.
     */
    public String toAscii(long colored) {
        String ascii = "    A B C D E F G H\n";
        ascii += "    ----------------\n";

        for (int r = 7; r >= 0; r--) {
            ascii += (r + 1) + " | ";
            for (int f = 0; f <= 7; f++) {
                int square = Board.makeSquare(r, f);
                boolean isColored = (colored & Board.getBBSquare(square)) != 0;
                int piece = this.pieces[square];

                ascii += isColored 
                    ? "\u001b[41m"
                    : Board.isWhite(square) ? "\u001B[47m\u001B[30m" : "\u001B[40m\u001B[37m";
                ascii += piece == 0 ? "  " : Piece.toAscii(piece)+" ";
                ascii += "\u001B[0m";
            }
            ascii += " | " + (r + 1) + "\n";
        }
        ascii += "    ----------------\n";
        ascii += "    A B C D E F G H";

        return ascii.toString();
    }
}