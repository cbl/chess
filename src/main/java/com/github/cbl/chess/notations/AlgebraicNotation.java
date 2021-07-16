package com.github.cbl.chess.notations;

import com.github.cbl.chess.chess.BitBoard;
import com.github.cbl.chess.chess.Board;
import com.github.cbl.chess.chess.Piece;
import com.github.cbl.chess.chess.Position;

public class AlgebraicNotation implements Notation {
    public static final String fileToChar = "abcdefgh";
    public static final String pieceToChar = "  NBRQK";

    public static int charToFile(char ch) {
        return fileToChar.indexOf(ch);
    }

    public static int charToPiece(char ch) {
        return pieceToChar.indexOf(ch);
    }

    public static int parseSquare(String square)
    {
        return 8 * (square.charAt(1) - '0' - 1) + charToFile(square.charAt(0));
    }

    public static long parseBBSquare(String square)
    {
        int parsed = parseSquare(square);
        return Board.isOnBoard(parsed) ? Board.BB_SQUARES[parsed] : 0;
    }

    /**
     * Parse a move from the given Standard Algebraic Notation (SAN).
     * 
     * <SAN move descriptor piece moves>   ::= <Piece symbol>[<from file>|<from rank>|<from square>]['x']<to square>
     * <SAN move descriptor pawn captures> ::= <from file>[<from rank>] 'x' <to square>[<promoted to>]
     * <SAN move descriptor pawn push>     ::= <to square>[<promoted to>]
     * 
     * @see https://www.chessprogramming.org/Algebraic_Chess_Notation
     */
    public static int parseSANMove(Position pos, String move)
    {
        int from = Board.SQUARE_NONE, 
            to = Board.SQUARE_NONE, 
            piece = Piece.PAWN;
        long possibleOccurrence = BitBoard.QUEEN_SIDE | BitBoard.KING_SIDE;
        boolean takes = false;
        boolean check = false;

        for(int i=0;i<move.length();i++) {
            char ch = move.charAt(i);
            if(charToPiece(ch) != -1) {
                piece = charToPiece(ch);
                if(i > (move.length()-4)) continue;
                if(charToFile(move.charAt(i+1)) != -1)
                    possibleOccurrence &= BitBoard.file(charToFile(move.charAt(i+1)));
                if((move.charAt(i+2) + '0') != -1)
                    possibleOccurrence &= BitBoard.rank((move.charAt(i+2) + '0'));
            }
            if(ch == 'x') takes = true;
        }

        if(move.charAt(move.length()-1) == '+') {
            to = parseSquare(move.substring(move.length()-3, move.length()-1));
        } else {
            to = parseSquare(move.substring(move.length()-2, move.length()));
        }

        if(piece == Piece.PAWN) {
            if(takes) {
                possibleOccurrence = BitBoard.file(charToFile(move.charAt(move.indexOf('x')-1)));
            } else {
                possibleOccurrence &= BitBoard.file(to);
            }
        } 
        
        if(piece == Piece.KING) {
            from = Board.fromBBSquare(pos.piecesByColorAndType(pos.sideToMove, Piece.KING));
        } else {
            long pieces = pos.piecesByColorAndType(pos.sideToMove, piece);
            
            if(Long.bitCount(pieces & possibleOccurrence) > 1) {
                long cache = pieces;
                pieces = 0;
                while(cache != 0) {
                    long square = Long.lowestOneBit(cache);
                    cache &= ~square;
                    if((pos.legalMoves(square) & Board.BB_SQUARES[to]) != 0) {
                        pieces |= square;
                        from = Board.fromBBSquare(square);
                    }
                }
                if(Long.bitCount(pieces & possibleOccurrence) > 1) {
                    return 0;
                }
            } else {
                from = Board.fromBBSquare(pieces & possibleOccurrence);
            };
        }

        if(from == Board.SQUARE_NONE) return 0;
        if(to == Board.SQUARE_NONE) return 0;
        if(!pos.isLegal(from, to)) return 0;

        return (from << 6) | to;
    }

    /**
     * Generate a board from the given notation.
     */
    public Position parse(String notation)
    {
        return new Position();
    }

    /**
     * Generate the notation for a given position.
     */
    public String compose(Position position)
    {
        return "";
    }
}
