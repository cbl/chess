package com.github.cbl.chess.chess;

public class MoveGenerator {
    public static long legalMoves(Position p, int square) {
        long moves = attacks(p, square);

        return moves;
    }

    public static long attacks(Position p, int square) {
        long attacks = 0, 
            pseudo = pseudoLegalMoves(p, square);

        if(pseudo == 0) return 0;

        int piece = p.pieceAt(square),
            usColor = Piece.getColor(piece);
        

        // Night moves cannot be blocked.
        if(Piece.isType(piece, Piece.KNIGHT)) {
            return pseudo & ~p.piecesByColor(usColor);
        }

        // Filter blocked squares on upper half of the board.
        attacks |= filterBlocked(p, pseudo, square, Move.LEFT);
        // Filter blocked squares on lower half of the board.
        attacks |= filterBlocked(p, pseudo, square, Move.RIGHT);

        attacks &= ~p.piecesByColor(usColor);

        return attacks;
    }

    protected static long filterBlocked(Position p, long pseudo, int from, int move) {
        long legal = 0;
        int match = 0, piece;
        boolean[] blocked = new boolean[4];
        for(int sq = from+move;sq<=Board.H8&&sq>=Board.A1;sq+=move) {            
            if(!BitBoard.valueAt(pseudo, sq)) continue;
            match = (sq-from)%8 == 0 ? 0 : (sq-from)%9 == 0 
                ? 1 : sq/8 == from/8 ? 3 : (sq-from)%7 == 0 ? 2  : -1;
            if(blocked[match]) continue;

            legal |= pseudo & Board.getBBSquare(sq);

            // Block direction if a piece is on the square.
            if(match != -1 && (piece = p.pieceAt(sq)) != 0) {
                blocked[match] = true;
                if(Piece.isType(piece, Piece.PAWN) && match == 0) {
                    legal &= ~Board.getBBSquare(sq);
                }
            }
        }
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
            pseudo &= p.piecesByColor(themColor) | p.enPassantSquare;
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
