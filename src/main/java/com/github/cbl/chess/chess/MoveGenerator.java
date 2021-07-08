package com.github.cbl.chess.chess;

public class MoveGenerator {
    public static long legalMoves(Position p, int square) {
        long legal = 0, 
            pseudo = pseudoLegalMoves(p, square);

        if(pseudo == 0) return 0;

        int sq,
            direction,
            piece = p.pieceAt(square),
            usColor = Piece.getColor(piece);
        boolean[] blocked = new boolean[4];

        // Night moves cannot be blocked.
        if(Piece.isType(piece, Piece.KNIGHT)) {
            return pseudo & ~p.piecesByColor(usColor);
        }

        // Check upper squares.
        for(sq = square+1;sq<=Board.H8;sq++) {
            if(!BitBoard.valueAt(pseudo, sq)) continue;
            direction = (sq-square)%8 == 0 ? 0 : (sq-square)%9 == 0 
                ? 1 : (sq-square)%7 == 0 ? 2 : sq/8 == square/8 ? 3 : 0;
            if(blocked[direction]) continue;

            legal |= pseudo & Board.getBBSquare(sq);

            // Block direction if a piece is on the square.
            if(p.pieceAt(sq) != 0) blocked[direction] = true;
        }

        // Check lower squares.
        blocked = new boolean[4];
        for(sq = square-1;sq>=Board.A1;sq--) {
            if(!BitBoard.valueAt(pseudo, sq)) continue;
            direction = (sq-square)%8 == 0 ? 0 : (sq-square)%9 == 0 
                ? 1 : (sq-square)%7 == 0 ? 2 : sq/8 == square/8 ? 3 : 0;
            if(blocked[direction]) continue;

            legal |= pseudo & Board.getBBSquare(sq);

            // Block direction if a piece is on the square.
            if(p.pieceAt(sq) != 0) blocked[direction] = true;
        }

        legal &= ~p.piecesByColor(usColor);

        return legal;
    }

    public static long pseudoLegalMoves(Position p, int square) {
        int piece = p.pieceAt(square);
        long pseudo = 0;

        if(piece == 0) return pseudo;
        int type = Piece.getType(piece);
        int usColor = Piece.getColor(piece);
        int themColor = Piece.Color.opposite(usColor);
        
        pseudo |= AttackIndex.pseudo[type][square];

        if(Piece.isType(piece, Piece.PAWN)) {
            pseudo |= AttackIndex.pawns[usColor][square];
            pseudo &= p.piecesByColor(themColor);
            pseudo |= p.enPassantSquare;
            pseudo |= pawnMoves(p, square);
        } else if(Piece.isType(piece, Piece.KING)) {
            pseudo |= castlingMoves(p, square);
        }

        return pseudo;
    }

    protected static long pawnMoves(Position p, int square) {
        int color = Piece.getColor(p.pieceAt(square));
        int forward = color == Piece.Color.WHITE ? Move.UP : Move.DOWN;
        int startingRank = color == Piece.Color.WHITE ? Board.RANK_2 : Board.RANK_7;

        // Can move two up if pawn is on starting rank.
        boolean canMoveTwoFoward = Board.isRank(square, startingRank);
        
        return Board.getBBSquare(square + forward) 
            | (canMoveTwoFoward ? Board.getBBSquare(square + forward * 2) : 0);
    }

    protected static long castlingMoves(Position p, int square) {
        int usColor = Piece.getColor(p.pieceAt(square));
        long bbSquare = Board.getBBSquare(square);
        long castleRight = BitBoard.shiftRight(bbSquare, 2);
        long castleLeft = BitBoard.shiftLeft(bbSquare, 2);

        return (castleRight | castleLeft) & p.castlingRightsFor(usColor);
    }
}
