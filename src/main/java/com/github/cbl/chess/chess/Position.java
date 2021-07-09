package com.github.cbl.chess.chess;

import java.lang.Math;

public class Position {
    public int sideToMove = Piece.Color.WHITE;
    public int halfmoveCount = 0;
    public int moveNumber = 0;
    public long castlingRights = 0;
    public long enPassantSquare = 0;
    public long[] checkSquares = new long[7];

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
        int piece = this.pieceAt(from);
        if(piece == 0 || !Piece.isColor(piece, this.sideToMove)) {
            return false;
        }

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
        int piece = this.pieces[from];
        int type = Piece.getType(piece);
        long bbFrom = Board.getBBSquare(from);
        int toFile = Board.getFile(to);
        int fromFile = Board.getFile(from);
        int fromRank = Board.getRank(from);
        
        this.movePiece(piece, from, to);
        if(type == Piece.KING && toFile == Board.FILE_C) {
            int rookSquare =  Board.square(fromRank, Board.FILE_A);
            this.movePiece(rookSquare, rookSquare, Board.square(fromRank, Board.FILE_D));
        } else if(type == Piece.KING && toFile == Board.FILE_G) {
            int rookSquare =  Board.square(fromRank, Board.FILE_H);
            this.movePiece(rookSquare, rookSquare, Board.square(fromRank, Board.FILE_F));
        }

        // Clear castling rights after king move.
        if(type == Piece.KING) {
            this.castlingRights &= this.sideToMove == Piece.Color.WHITE ? BitBoard.BLACK_SIDE : BitBoard.WHITE_SIDE;
        }

        // Clear castling rights after rook moved from original square.
        if(type == Piece.ROOK && (bbFrom & BitBoard.ROOK_SQUARES) != 0) {
            this.castlingRights &= ~((this.sideToMove == Piece.Color.WHITE ? BitBoard.WHITE_SIDE : BitBoard.BLACK_SIDE)
                & (fromFile == Board.FILE_A ? BitBoard.QUEEN_SIDE : BitBoard.KING_SIDE));
        }

        // Clear square above pawn when en passant move was made.
        if((Board.getBBSquare(to) & this.enPassantSquare) != 0) {
            clearSquare(to - Move.pawn(this.sideToMove));
        }

        // Cache en possible passant moves.
        if(type == Piece.PAWN && Math.abs(Board.getRank(from)-Board.getRank(to)) == 2) {
            this.enPassantSquare = Board.getBBSquare(from + Move.pawn(this.sideToMove));
        } else {
            this.enPassantSquare = 0;
        }

        int move = Move.make(Move.Type.NORMAL, this.pieces[from], from, to);
        this.moves[(++this.halfmoveCount)-1] = move;

        this.sideToMove = Piece.Color.opposite(this.sideToMove);
    }

    /**
     * Relocate a piece from one square to another.
     */
    public void movePiece(int piece, int from, int to) {
        long bbFrom = Board.getBBSquare(from);
        long bbTo = Board.getBBSquare(to);
        int color = Piece.getColor(piece);
        int type = Piece.getColor(piece);
        this.piecesByColor[color] &= ~bbFrom;
        this.piecesByColor[Piece.Color.opposite(color)] &= ~bbTo;
        this.piecesByColor[color] |= bbTo;
        this.piecesByType[type] &= ~bbFrom;
        for(int t=0;t<this.piecesByType.length;t++) this.piecesByType[t] &= ~bbTo;
        this.piecesByType[type] |= bbTo;
        this.pieces[to] = this.pieces[from];
        this.pieces[from] = 0;
    }

    public void clearSquare(int square) {
        long bbSquare = Board.getBBSquare(square);
        for(int t=0;t<this.piecesByType.length;t++) this.piecesByType[t] &= ~bbSquare;
        for(int t=0;t<this.piecesByColor.length;t++) this.piecesByColor[t] &= ~bbSquare;
        this.pieces[square] = 0;
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