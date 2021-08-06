package com.github.cbl.chess.notations;

import com.github.cbl.chess.chess.Bitboard;
import com.github.cbl.chess.chess.Board;
import com.github.cbl.chess.chess.Move;
import com.github.cbl.chess.chess.Piece;
import com.github.cbl.chess.chess.Position;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.lang.NumberFormatException;

public class FenNotation implements Notation {

    /**
     * The FEN for the standard chess starting position.
     */
    public static final String startingFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0";
    
    public static final String pieceToChar = " PNBRQK  pnbrqk";

    /**
     * Get the piece from a givne char.
     */
    public static int charToPiece(char ch) {
        return pieceToChar.indexOf(ch);
    }
    
    /**
     * Generate a board from the given notation.
     */
    public Position parse(String fen) throws InvalidFormatException
    {
        try {
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
                    p.setPieceAt(square, Piece.getType(piece), Piece.getColor(piece));
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

                if(ch == ' ') break;
                if(ch == '-') continue; 

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

            substr = fen.substring(++i);
            if(substr == "") return p;

            // Number of halfmoves and moves
            p.halfmoveCount = Integer.parseInt(substr.substring(0, substr.indexOf(' ')));
            p.moveNumber = Integer.parseInt(fen.substring((++i)+substr.indexOf(' ')));

            return p;
        } catch(NumberFormatException e) {
            throw new Notation.InvalidFormatException();
        }
    }

    /**
     * Compose the FEN representaiton of the given position.
     */
    public String compose(Position pos)
    {
        StringJoiner sj = new StringJoiner(" ");

        sj.add(composeEPD(pos));
        sj.add(Integer.toString(pos.moveNumber));
        sj.add(Integer.toString(pos.halfmoveCount));

        return sj.toString();
    }

    /**
     * Compose the EPD representation of the given position.
     */
    protected String composeEPD(Position pos) {
        StringJoiner sj = new StringJoiner(" ");

        sj.add(composeBoardFen(pos));
        sj.add(pos.sideToMove == Piece.Color.WHITE ? "w" : "b");
        sj.add(composeCastlingRights(pos));
        sj.add(pos.epSquare == Bitboard.EMPTY ? "-" : Board.squareToString(Board.fromBB(pos.epSquare)));

        return sj.toString();
    }

    /**
     * Compose the castling rights of the given position.
     */
    protected String composeCastlingRights(Position pos) {
        String r = "";

        long whiteKingSide = Bitboard.WHITE_SIDE & Bitboard.KING_SIDE;
        long whiteQueenSide = Bitboard.WHITE_SIDE & Bitboard.QUEEN_SIDE;
        long blackKingSide = Bitboard.BLACK_SIDE & Bitboard.KING_SIDE;
        long blackQueenSide = Bitboard.BLACK_SIDE & Bitboard.QUEEN_SIDE;

        if(whiteKingSide != Bitboard.EMPTY) r += "K";
        if(whiteQueenSide != Bitboard.EMPTY) r += "Q";
        if(blackKingSide != Bitboard.EMPTY) r += "k";
        if(blackQueenSide != Bitboard.EMPTY) r += "q";

        if(r == "") return "-";

        return r;
    }

    /**
     * Compose the board FEN.
     */
    protected String composeBoardFen(Position pos) {
        StringJoiner sj = new StringJoiner("");
        int empty = 0;

        for(int sq = Board.A1;sq<=Board.H8;sq++) {
            int mirror = sq ^ 0x38; // Mirrors the square vertically.
            int piece = pos.pieceAt(mirror);

            if(piece == Piece.NONE) {
                empty++;
            } else {
                if(empty > 0) {
                    sj.add(Integer.toString(empty));
                    empty = 0;
                }
                sj.add(Character.toString(pieceToChar.charAt(piece)));
            }

            if((Board.BB_SQUARES[mirror] & Bitboard.FILE_H) != Bitboard.EMPTY) {
                if(empty > 0) {
                    sj.add(Integer.toString(empty));
                    empty = 0;
                }

                if(mirror != Board.H1) {
                    sj.add("/");
                }
            }
        }
    
        return sj.toString();
    }
}
