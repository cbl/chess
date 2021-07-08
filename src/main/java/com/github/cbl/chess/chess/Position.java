package com.github.cbl.chess.chess;

public class Position {
    public int sideToMove = Piece.Color.WHITE;
    public int halfmoveCount = 0;
    public int moveNumber = 0;
    public long castlingRights = 0;
    public long enPassantSquare = 0;

    protected int[] moves = new int[Move.MAX_MOVES_COUNT];
    protected long[] piecesByType = new long[7];
    protected long[] piecesByColor = new long[3];
    protected int[] pieces = new int[Board.SQUARE_COUNT];
    
    public void addPiece(int square, int type, int color) {
        long bbSquare = Board.getBBSquare(square);
        this.piecesByColor[color] |= bbSquare;
        this.piecesByColor[Piece.Color.ANY] |= bbSquare;
        this.piecesByType[type] |= bbSquare;
        this.piecesByType[Piece.ANY] |= bbSquare;
        this.pieces[square] = Piece.make(type, color);
    }

    public long piecesByType(int type) {
        return this.piecesByType[type];
    }

    public long piecesByColor(int color) {
        return this.piecesByColor[color];
    }

    public long piecesByColorAndType(int color, int type) {
        return this.piecesByType[type] & this.piecesByColor[color];
    }

    public void addPiece(int square, int piece) {
        this.addPiece(square, Piece.getType(piece), Piece.getColor(piece));
    }

    public long legalMoves(int square) {
        return MoveGenerator.legalMoves(this, square);
    }

    public long legalMoves(long square) {
        return this.legalMoves(Board.fromBBSquare(square));
    }

    public long pseudoLegalMoves(int square) {
        return MoveGenerator.pseudoLegalMoves(this, square);
    }

    public boolean isLegal(int from, int to) {
        long bbTo = Board.getBBSquare(to);

        return (this.legalMoves(from) & bbTo) != 0;
    }

    public int pieceAt(int square) {
        return this.pieces[square];
    }

    public int moveAt(int index) {
        return this.moves[index];
    }

    public void setSideToMove(int color) {
        this.sideToMove = color;
    }

    public void move(int from, int to) {
        int move = Move.make(Move.Type.NORMAL, this.pieces[from], from, to);
        int type = Piece.getType(this.pieces[from]);
        long bbFrom = Board.getBBSquare(from);
        long bbTo = Board.getBBSquare(to);
        this.moves[(++this.halfmoveCount)-1] = move;
        this.piecesByColor[this.sideToMove] &= ~bbFrom;
        this.piecesByColor[this.sideToMove] |= bbTo;
        this.piecesByType[type] &= ~bbFrom;
        this.piecesByType[type] |= bbTo;
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

    public long castlingRightsFor(int color) {
        return this.castlingRights
            & (color == Piece.Color.WHITE
            ? BitBoard.WHITE_SIDE 
            : BitBoard.BLACK_SIDE);
    }

    public void setEnPassantSquare(int square) {
        this.enPassantSquare = Board.getBBSquare(square);
    }

    public void setEnPassantSquare(long bbSquare) {
        this.enPassantSquare = bbSquare;
    }

    /**
     * Get the ASCII representation of the position.
     */
    public String toAscii(long colored) {
        String ascii = "    A B C D E F G H\n";
        ascii += "    ----------------\n";

        for (int r = 7; r >= 0; r--) {
            ascii += (r + 1) + " | ";
            for (int f = 0; f <= 7; f++) {
                int square = Board.square(r, f);
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