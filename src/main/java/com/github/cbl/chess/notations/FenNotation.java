package com.github.cbl.chess.notations;

import com.github.cbl.chess.chess.Board;
import com.github.cbl.chess.chess.Move;
import com.github.cbl.chess.chess.Piece;
import com.github.cbl.chess.chess.Position;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class FenNotation implements Notation {
    public static final String pieceToChar = " PNBRQK  pnbrqk";

    public static int charToPiece(char ch) {
        return pieceToChar.indexOf(ch);
    }
    
     /**
     * Generate a board from the given notation.
     */
    public Position parse(String fen)
    {
        Position p = new Position();
        String substr;
        int i, piece, square = Board.A8;
        char ch;

        // Pieces
        for (i=0;i<fen.length();i++) {
            ch = fen.charAt(i);
            if(ch == ' ') break;

            if(Character.isDigit(ch))
                square += (ch - '0') * Move.RIGHT;
            else if(ch == '/')
                square += 2 * Move.DOWN;
            else if((piece = charToPiece(ch)) != -1) {
                p.addPiece(square, piece);
                square++;
            }
        }

        // Side to move
        p.setSideToMove(
            fen.charAt(++i) == 'w' ? Piece.Color.WHITE : Piece.Color.BLACK
        );

        // Castling rights
        for(i+=2;i<fen.length();i++) {
            ch = fen.charAt(i);
            if(ch == '-' || ch == ' ') break;

            piece = charToPiece(ch);
            int color = Piece.getColor(piece);
            int rookSquare = Board.SQUARE_NONE;

            if(Piece.isType(piece, Piece.KING))
                rookSquare = Board.H1;
            else if(Piece.isType(piece, Piece.QUEEN))
                rookSquare = Board.A1;
            if(color == Piece.Color.BLACK)
                rookSquare += 8 * 7;

            p.setCastlingRight(color, rookSquare);
        }
        
        // En Passant
        long enPassantSquare = AlgebraicNotation.parseBBSquare(
            substr = fen.substring(++i, i+=2)
        );
        if(substr.charAt(0) != '-')
            p.setEnPassantSquare(enPassantSquare);
        else
            i--;

        // Number of halfmoves and moves
        substr = fen.substring(++i);
        p.halfmoveCount = Integer.parseInt(substr.substring(0, substr.indexOf(' ')));
        p.moveNumber = Integer.parseInt(fen.substring((++i)+substr.indexOf(' ')));

        return p;
    }

    /**
     * Generate the notation for a given position.
     */
    public String compose(Position position)
    {
        return "lol";
    }
}
