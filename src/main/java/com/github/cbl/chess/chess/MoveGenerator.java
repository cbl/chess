package com.github.cbl.chess.chess;

public class MoveGenerator {
    public static long getLegal(int[] pieces, int[] moves, int moveIndex, int square) {
        int piece = pieces[square];

        if(piece == 0) {
            return 0;
        }

        long board = 0;
        
        switch(Piece.getType(piece)) {
            case Piece.PAWN:
                board = pawnMoves(pieces, moves, moveIndex, square);
                break;
            case Piece.ROOK:
                board = rookMoves(pieces, moves, square);
                break;
            default: board = 0;
                break;
        }

        // Eliminate squares on which your own pieces are standing.
        for(int sq = 0;sq<64;sq++) {
            if(pieces[sq] == 0 || sq == square) {
                continue;
            }
            if(Piece.isColor(pieces[sq], Piece.getColor(piece))) {
                board &= ~Board.getBBSquare(sq);
            }
        }

        // Cannot step on current square:
        board &= ~Board.getBBSquare(square);

        return board;
    }

    protected static long pawnMoves(int[] pieces, int[] moves, int moveIndex, int square) {
        int color = Piece.getColor(pieces[square]);
        int forward = color == Piece.Color.WHITE ? Move.UP : Move.DOWN;
        int startingRank = color == Piece.Color.WHITE ? Board.RANK_2 : Board.RANK_7;
        int upRight =  square + forward + Move.RIGHT;
        int upLeft =  square + forward + Move.LEFT;
        int themColor = Piece.Color.opposite(color);
        int previousMove = moveIndex >= 0 ? moves[moveIndex] : 0;


        // Can move two up if pawn is on starting rank.
        boolean canMoveTwoFoward = Board.isRank(square, startingRank);
        // One up and one left if pawn us not on file H and 
        boolean canTakeRight = !Board.isFile(square, Board.FILE_H) 
            && Piece.isColor(pieces[upRight], themColor);
        // One up and one right if pawn is not on file A and 
        boolean canTakeLeft = !Board.isFile(square, Board.FILE_H) 
            && Piece.isColor(pieces[upLeft], themColor);

        
        // Determine wheter the opponent moved his pawn two up in his previous move.
        boolean opponentPreviouslyMovedPawnTwoForward = 
            (Move.getToSquare(previousMove) - Move.getFromSquare(previousMove)) / -forward == 2 
            && Piece.isType(Move.getPiece(previousMove), Piece.PAWN);

        // En Passant move can be made when:
        // - Opponent previously moved his pawn two squares up &
        // - He placed his pawn left or right next to the pawn to move
        boolean canEnPassantRight = opponentPreviouslyMovedPawnTwoForward 
            && Move.getToSquare(previousMove) == square + Move.RIGHT;
        boolean canEnPassantLeft = opponentPreviouslyMovedPawnTwoForward 
            && Move.getToSquare(previousMove) == square + Move.LEFT;

        return
            // Allways possible to move one up.
            Board.getBBSquare(square + forward) 
            | (canMoveTwoFoward ? Board.getBBSquare(square + forward * 2) : 0)
            | (canTakeRight || canEnPassantRight ? Board.getBBSquare(upRight) : 0)
            | (canTakeLeft || canEnPassantLeft ? Board.getBBSquare(upLeft) : 0);
    }

    protected static long rookMoves(int[] pieces, int[] moves, int square) {
        int rookRank = Board.getRank(square);
        int rookFile = Board.getFile(square);

        System.out.println(rookRank);
        System.out.println(rookFile);

        long board = 0
            | (BitBoard.RANK_1 << (8 * rookRank))
            | (BitBoard.FILE_A << rookFile);

        for(int sq = 0;sq<64;sq++) {
            if (pieces[sq] == 0 || sq == square || (Board.getBBSquare(sq) & board) == 0) {
                continue;
            }

            int sqRank = Board.getRank(sq);
            int sqFile = Board.getFile(sq);

            if(rookRank != sqRank && rookFile != sqFile) {
                continue;
            }

            // Check if a piece blocks the rank or the file.
            if(rookRank != sqRank) {
                board &= rookRank > sqRank 
                    ? (~(~(~BitBoard.RANK_1 << (8 * sqRank)) & ~Board.getBBSquare(sq)))
                    : (~(~(~BitBoard.RANK_8 >> (8 * --sqRank)) & ~Board.getBBSquare(sq)));
            } else {
                board &= rookFile > sqFile
                    ? ~((BitBoard.QUEEN_SIDE | BitBoard.QUEEN_SIDE << 7-sqFile) & (BitBoard.RANK_1 << (8 * sqRank)) & ~Board.getBBSquare(sq))
                    : ~((BitBoard.KING_SIDE | ~BitBoard.KING_SIDE << sqFile) & (BitBoard.RANK_1 << (8 * sqRank)) & ~Board.getBBSquare(sq));
            }
        }

        return board;
    }
}
