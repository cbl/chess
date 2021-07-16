package com.github.cbl.chess.chess;

import java.lang.Cloneable;
import java.lang.CloneNotSupportedException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class Position implements Cloneable {

    public int sideToMove = Piece.Color.WHITE;
    public int halfmoveCount = 0;
    public int moveNumber = 0;
    public long castlingRights = 0;
    public long epSquare = 0;
    public long[] checkSquares = new long[7];

    protected int moveIndex = 0;
    protected Move[] moves = new Move[Move.MAX_MOVES_COUNT];
    protected long[] piecesByType = new long[7];
    protected long[] piecesByColor = new long[3];
    protected int[] pieces = new int[Board.SQUARE_COUNT];

    protected List<Position> stack = new ArrayList<Position>();

    public Position() {
        //
    }

    protected Position(Position pos) {
        this.restore(pos);
    }

    protected void restore(Position pos) {
        this.sideToMove = pos.sideToMove;
        this.halfmoveCount = pos.halfmoveCount;
        this.moveNumber = pos.moveNumber;
        this.castlingRights = pos.castlingRights;
        this.epSquare = pos.epSquare;
        this.checkSquares = pos.checkSquares;
        this.moveIndex = pos.moveIndex;
        this.moves = pos.moves;
        this.piecesByType = pos.piecesByType;
        this.piecesByColor = pos.piecesByColor;
        this.pieces = pos.pieces;
        this.stack = pos.stack;
    }
    
    public void addPiece(int square, int type, int color) {
        long bbSquare = Board.BB_SQUARES[square];
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

    public long pawns()
    {
        return this.piecesByType[Piece.PAWN];
    }

    public long pawns(int color)
    {
        return this.piecesByType[Piece.PAWN] & this.piecesByColor[color];
    }

    public long knights()
    {
        return this.piecesByType[Piece.KNIGHT];
    }

    public long knights(int color)
    {
        return this.piecesByType[Piece.KNIGHT] & this.piecesByColor[color];
    }

    public long bishops()
    {
        return this.piecesByType[Piece.BISHOP];
    }

    public long bishops(int color)
    {
        return this.piecesByType[Piece.BISHOP] & this.piecesByColor[color];
    }

    public long rooks()
    {
        return this.piecesByType[Piece.ROOK];
    }

    public long rooks(int color)
    {
        return this.piecesByType[Piece.ROOK] & this.piecesByColor[color];
    }

    public long queens()
    {
        return this.piecesByType[Piece.QUEEN];
    }

    public long queens(int color)
    {
        return this.piecesByType[Piece.QUEEN] & this.piecesByColor[color];
    }

    public long kings()
    {
        return this.piecesByType[Piece.KING];
    }

    public long king(int color)
    {
        return this.piecesByType[Piece.KING] & this.piecesByColor[color];
    }

    public long occupied()
    {
        return this.piecesByColor[Piece.Color.ANY];
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

        long bbTo = Board.BB_SQUARES[to];
        return (this.legalMoves(from) & bbTo) != 0;
    }

    public int pieceAt(int square) {
        return this.pieces[square];
    }

    public int pieceTypeAt(int square) {
        return Piece.getType(this.pieces[square]);
    }

    public Move moveAt(int index) {
        return this.moves[index];
    }

    public void setSideToMove(int color) {
        this.sideToMove = color;
    }

    // public boolean givesCheck(Move move) {
        
    // }

    public boolean isIntoCheck(Move move) {
        long king = this.king(this.sideToMove);

        long checkers = this.attackers(Piece.Color.opposite(this.sideToMove), Board.fromBBSquare(king));
        List<Move> evasions = this.generateEvasions(king, checkers, Board.BB_SQUARES[move.from], Board.BB_SQUARES[move.to]);
        // if(checked != 0) {
        //     return true;
        // }
        
        return false;
    }

    protected List<Move> generateEvasions(long king, long checkers, long from, long to) {
        long sliders = checkers & (this.bishops() | this.rooks() | this.queens());
        List<Move> evasions = new ArrayList<Move>();
        long attacked = 0;
        int kingSq = Board.fromBBSquare(king);
        
        for(long checker : BitBoard.toList(checkers)) {
            attacked |= AttackIndex.streched[kingSq][Board.fromBBSquare(checker)] & ~checker;
        }

        if((king & from) != 0) {
            for(long toSq : BitBoard.toReversedList(AttackIndex.pseudo[Piece.KING][kingSq])) {
                evasions.add(new Move(kingSq, Board.fromBBSquare(toSq)));
            }
        }

        return evasions;
    }

    public long attackers(int color, int square) {
        long occupied = this.occupied();
        long rankPieces = AttackIndex.ranks[square] & occupied;
        long filePieces = AttackIndex.files[square] & occupied;
        long diagonalPieces = AttackIndex.diagonals[square] & occupied;

        long queensAndRooks = this.queens() | this.rooks();
        long queensAndBishops = this.queens() | this.bishops();

        long attackers = (
            (AttackIndex.pseudo[Piece.KING][square] & this.kings()) |
            (AttackIndex.pseudo[Piece.KNIGHT][square] & this.knights()) |
            (AttackIndex.pseudo[Piece.ROOK][square] & queensAndRooks) |
            (AttackIndex.pseudo[Piece.BISHOP][square] & queensAndBishops) |
            (AttackIndex.pawns[Piece.Color.opposite(color)][square] & this.pawns()));

        return attackers & this.piecesByColor[color];
    }

    public void push(Move move) {
        long fromBB = Board.BB_SQUARES[move.from];
        long toBB = Board.BB_SQUARES[move.to];
        int toFile = Board.getFile(move.to);
        int fromFile = Board.getFile(move.from);
        int fromRank = Board.getRank(move.from);
        
        // Reset En Passant square
        this.epSquare = 0;

        // Resetting halfmove count when move is a pawn move or a capture.
        if(move.isZeroing(this)) this.halfmoveCount = 0;
        else this.halfmoveCount++;

        int pieceType = this.removePieceAt(move.from);

        // Set piece type when move is promotion.
        if(move.isPromotion()) pieceType = move.promotion;

        // Place the piece at the given square.
        this.setPieceAt(move.to, pieceType, this.sideToMove);

        // Do castling.
        if(pieceType == Piece.KING && toFile == Board.FILE_C) {
            int rookSquare =  Board.square(fromRank, Board.FILE_A);
            this.removePieceAt(rookSquare);
            this.setPieceAt(rookSquare, Piece.ROOK, this.sideToMove);
        } else if(pieceType == Piece.KING && toFile == Board.FILE_G) {
            int rookSquare =  Board.square(fromRank, Board.FILE_H);
            this.removePieceAt(rookSquare);
            this.setPieceAt(rookSquare, Piece.ROOK, this.sideToMove);
        }

        // Clear castling rights after king move.
        if(pieceType == Piece.KING) {
            this.castlingRights &= this.sideToMove == Piece.Color.WHITE ? BitBoard.BLACK_SIDE : BitBoard.WHITE_SIDE;
        }

        // Clear castling rights after rook moved from original square.
        if(pieceType == Piece.ROOK && (fromBB & BitBoard.ROOK_SQUARES) != 0) {
            this.castlingRights &= ~((this.sideToMove == Piece.Color.WHITE ? BitBoard.WHITE_SIDE : BitBoard.BLACK_SIDE)
                & (fromFile == Board.FILE_A ? BitBoard.QUEEN_SIDE : BitBoard.KING_SIDE));
        }

        // Clear square above pawn when en passant move was made.
        if((Board.BB_SQUARES[move.to] & this.epSquare) != 0) {
            this.removePieceAt(move.to - Move.pawn(this.sideToMove));
        }

        // Remember possible En Passant for next move.
        if(pieceType == Piece.PAWN && Math.abs(Board.getRank(move.from)-Board.getRank(move.to)) == 2) {
            this.epSquare = Board.BB_SQUARES[move.from + Move.pawn(this.sideToMove)];
        }

        // Push move to the stack and increase index.
        this.moves[this.moveIndex] = move;
        this.moveIndex++;

        try {
            this.stack.add(this.clone());
        } catch(CloneNotSupportedException e) {}

        // Swap side to move.
        this.sideToMove = Piece.Color.opposite(this.sideToMove);
    }

    public Move pop()
    {
        // Cannot pop when no move has been made so far.
        if(this.moveIndex == 0) return null;

        Move move = this.moves[this.moveIndex-1];
        this.stack.remove(this.stack.size() - 1);
        this.restore(this.stack.remove(this.stack.size() - 1));

        return move;
    }

    public Move peek()
    {
        return this.moveIndex > 0 ? this.moves[this.moveIndex-1] : null;
    }

    public void setPieceAt(int square, int pieceType, int color)
    {
        this.removePieceAt(square);

        long mask = Board.BB_SQUARES[square];

        this.piecesByType[pieceType] |= mask;
        this.piecesByType[Piece.ANY] |= mask;
        this.piecesByColor[Piece.Color.ANY] |= mask;
        this.piecesByColor[color] |= mask;
        this.pieces[square] = Piece.make(pieceType, color);
    }

    public int removePieceAt(int square)
    {
        int pieceType = this.pieceTypeAt(square);
        long mask = Board.BB_SQUARES[square];

        if(pieceType == 0) return pieceType;

        this.piecesByType[pieceType] &= ~mask;
        this.piecesByType[Piece.ANY] &= ~mask;
        this.piecesByColor[Piece.Color.ANY] &= ~mask;
        this.piecesByColor[Piece.Color.WHITE] &= ~mask;
        this.piecesByColor[Piece.Color.BLACK] &= ~mask;
        this.pieces[square] = 0;

        return pieceType;
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
        this.epSquare = Board.BB_SQUARES[square];
    }

    public void setEnPassantSquare(long bbSquare) {
        this.epSquare = bbSquare;
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
                boolean isColored = (colored & Board.BB_SQUARES[square]) != 0;
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

    public Position clone() throws CloneNotSupportedException {
        return new Position(this);
    }
}