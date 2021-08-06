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
    public long checkers = 0;

    protected int moveIndex = 0;
    public Move[] moves = new Move[Move.MAX_MOVES_COUNT];
    protected long[] piecesByType = new long[7];
    protected long[] piecesByColor = new long[3];
    protected int[] pieces = new int[Board.SQUARE_COUNT];
    protected long[] pinned = new long[3];

    protected List<Position> stack = new ArrayList<Position>();

    /**
     * Create a new instance of the Position.
     */
    public Position() {
        //
    }

    /**
     * Clone an instance of the Position.
     */
    protected Position(Position pos) {
        this.restore(pos);
    }

    /**
     * Restore an instance of the Position.
     */
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

    /**
     * Gets bitboard that contains all pieces of the given type.
     */
    public long piecesByType(int type) {
        return this.piecesByType[type];
    }

    /**
     * Gets bitboard that contains pieces by the given color.
     */
    public long piecesByColor(int color) {
        return this.piecesByColor[color];
    }

    /**
     * Gets bitboard that contains all pawns.
     */
    public long pawns() {
        return this.piecesByType[Piece.PAWN];
    }

    /**
     * Gets bitboard that contains all pawns of the given color.
     */
    public long pawns(int color) {
        return this.piecesByType[Piece.PAWN] & this.piecesByColor[color];
    }

    /**
     * Gets bitboard that contains all knights.
     */
    public long knights() {
        return this.piecesByType[Piece.KNIGHT];
    }

    /**
     * Gets bitboard that contains all knights of the given color.
     */
    public long knights(int color) {
        return this.piecesByType[Piece.KNIGHT] & this.piecesByColor[color];
    }

    /**
     * Gets bitboard that contains all bishops.
     */
    public long bishops() {
        return this.piecesByType[Piece.BISHOP];
    }

    /**
     * Gets bitboard that contains all bishops of the given color.
     */
    public long bishops(int color) {
        return this.piecesByType[Piece.BISHOP] & this.piecesByColor[color];
    }

    /**
     * Gets bitboard that contains all rooks.
     */
    public long rooks() {
        return this.piecesByType[Piece.ROOK];
    }

    /**
     * Gets bitboard that contains all rooks of the given color.
     */
    public long rooks(int color) {
        return this.piecesByType[Piece.ROOK] & this.piecesByColor[color];
    }

    /**
     * Gets bitboard that contains all queens.
     */
    public long queens() {
        return this.piecesByType[Piece.QUEEN];
    }

    /**
     * Gets bitboard that contains all queens of the given color.
     */
    public long queens(int color) {
        return this.piecesByType[Piece.QUEEN] & this.piecesByColor[color];
    }

    /**
     * Gets bitboard that contains all kings.
     */
    public long kings() {
        return this.piecesByType[Piece.KING];
    }

    /**
     * Gets bitboard that contains the king of the given color.
     */
    public long king(int color) {
        return this.piecesByType[Piece.KING] & this.piecesByColor[color];
    }

    /**
     * Gets bitboard that contains all pieces of the given color.
     */
    public long occupied(int color) {
        return this.piecesByColor[color];
    }

    /**
     * Gets bitboard that contains all pieces.
     */
    public long occupied() {
        return this.piecesByType[Piece.ANY];
    }

    /**
     * Gets the color of the player whose turn it is not.
     */
    public int themColor() {
        return Piece.Color.opposite(this.sideToMove);
    }

    /**
     * Get all pieces of the given color and type.
     */
    public long piecesByColorAndType(int color, int type) {
        return this.piecesByType[type] & this.piecesByColor[color];
    }

    /**
     * Determines out if a move does not put the king at the given square in 
     * check.
     */
    protected boolean isSafe(int kingSquare, Move move) {
        if(move.from == kingSquare) {
            return !this.isAttackedBy(this.themColor(), move.to);
        }

        // TODO: EP Skewered
        
        return (this.pinned[this.sideToMove] & Board.BB_SQUARES[move.from]) == 0 
            || (BBIndex.STRECHED[move.from][move.to] & Board.BB_SQUARES[kingSquare]) != 0;
    }

    /**
     * Determines if a pseudo-legal move is a castling move.
     */
    public boolean isCastling(Move move) {
        if(this.pieceTypeAt(move.from) != Piece.KING) {
            return false;
        }

        return Math.abs(move.from - move.to) == 2;
    }

    /**
     * Determines whether the given square is attacked by the given color.
     */
    public boolean isAttackedBy(int color, int square) {
        return this.attackers(color, square) != 0;
    }

    /**
     * Gets bitboard containing blockers for sliding pieces of the opponent.
     * 
     * A piece is a blocker when:
     * - it is between the king and an opponents sliding piece.
     * - it is the only piece between the king and the opponents sliding piece.
     */
    protected long sliderBlockers(int color) {
        int themColor = Piece.Color.opposite(color);
        int kingSquare = Board.fromBB(this.king(color));
        long rooksAndQueens = this.rooks() | this.queens();
        long bishopsAndQueens = this.bishops() | this.queens();

        // All opponent's sliding pieces.
        long slidingPieces = (
            (BBIndex.RANK_ATTACKS[kingSquare].get(Bitboard.EMPTY) & rooksAndQueens) |
            (BBIndex.FILE_ATTACKS[kingSquare].get(Bitboard.EMPTY) & rooksAndQueens) |
            (BBIndex.BISHOP_ATTACKS[kingSquare].get(Bitboard.EMPTY) & bishopsAndQueens)
        ) & this.occupied(themColor);

        long blockers = 0;

        for(int slidingPiece : Board.toReversedList(slidingPieces)) {
            long attack = BBIndex.BETWEEN[kingSquare][slidingPiece] & this.occupied();

            // Add to blockers when exactly one peace is between the sliding piece 
            // and the king.
            if(attack != 0 && Board.BB_SQUARES[Bitboard.lsb(attack)] == attack)
                blockers |= attack;
        }

        return blockers & this.occupied(color);
    }

    /**
     * Cache check info.
     */
    protected void setCheckInfo() {
        this.pinned[Piece.Color.WHITE] = this.sliderBlockers(Piece.Color.WHITE);
        this.pinned[Piece.Color.BLACK] = this.sliderBlockers(Piece.Color.BLACK);
        this.checkers = this.attackers(this.themColor(), Board.fromBB(this.king(this.themColor())));
    }

    /**
     * Determines whether it is checkmate.
     */
    public boolean isCheckmate() {
        if(!this.isCheck()) return false;

        return this.generateLegalMoves(Bitboard.ALL).isEmpty();
    }

    /**
     * Determines whether it is stalemate.
     */
    public boolean isStalemate() {
        if(this.isCheck()) return false;

        return this.generateLegalMoves(Bitboard.ALL).isEmpty();
    }

    /**
     * Determine whether the game has ended due to insufficient material.
     * 
     * Insufficient material occures when it is not possible for any side to 
     * force a checkmate.
     * 
     * This can happen when:
     * 1. There are only two kings.
     * 2. We only have a knight AND:
     *      2.1. We do not have any other pieces, including more than one knight.
     *      2.2. The opponent does not have pawns, knights, bishops or rooks.
     * 3. We only have a bishop:
     *      3.1. We do not have any other pieces, including bishops of the 
     *           opposite color.
     *      3.2. The opponent does not have bishops of the opposite color, pawns 
     *           or knights.
     * 
     * @see https://support.chess.com/article/128-what-does-insufficient-mating-material-mean
     */
    public boolean isInsufficientMaterial() {
        if(occupied() == kings()) return true;

        int[] colors = {Piece.Color.WHITE, Piece.Color.BLACK};

        for(int color : colors) {
            if((occupied(color) & (pawns() | rooks() | queens())) != Bitboard.EMPTY) {
                return false;
            }
            
            if((occupied(color) & knights()) != Bitboard.EMPTY) {
                if(Long.bitCount(occupied(color)) > 2 
                    || (occupied(Piece.Color.opposite(color)) & ~kings() & ~queens()) != Bitboard.EMPTY) {
                    return false;
                }
            }

            if((occupied(color) & bishops()) != Bitboard.EMPTY) {
                boolean isOfSameColor = 
                    (bishops() & Bitboard.DARK_SQUARES) == Bitboard.EMPTY || 
                    (bishops() & Bitboard.LIGHT_SQUARES) == Bitboard.EMPTY;
                if(isOfSameColor || pawns() != Bitboard.EMPTY || knights() != Bitboard.EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Determine whether the king is in check.
     */
    public boolean isCheck() {
        return this.checkers != 0;
    }

    /**
     * Generate all legal moves.
     */
    public MoveList generateLegalMoves(long fromMask) {
        return this.generateLegalMoves(fromMask, Bitboard.ALL);
    }

    /**
     * Generate all legal moves.
     */
    public MoveList generateLegalMoves(long fromMask, long toMask) {
        MoveList moveList = new MoveList();
        long bbKing = this.king(this.sideToMove);
        int king = Board.fromBB(bbKing);
        long checkers = this.attackers(this.themColor(), king);

        MoveList pseudoLegal = checkers == 0
            ? this.generatePseudoLegalMoves(fromMask, toMask)
            : this.generateEvasions(bbKing, checkers, fromMask, toMask);
        
        for(Move move : pseudoLegal)
            if(this.isSafe(king, move)) moveList.add(move);
    
        return moveList;
    }

    /**
     * Generate all Pseudo-Legal moves.
     * 
     * > A Pseudo-Legal Move is legal in the sense that it is consistent with 
     * > the current board representation it is assigned to, and it must be 
     * > member of all pseudo legal generated moves for that position and side 
     * > to move ...
     * @see https://www.chessprogramming.org/Pseudo-Legal_Move
     */
    public MoveList generatePseudoLegalMoves(long fromMask) {
        return this.generatePseudoLegalMoves(fromMask, Bitboard.ALL);
    }

    /**
     * Generate all Pseudo-Legal moves.
     * 
     * > A Pseudo-Legal Move is legal in the sense that it is consistent with 
     * > the current board representation it is assigned to, and it must be 
     * > member of all pseudo legal generated moves for that position and side 
     * > to move ...
     * @see https://www.chessprogramming.org/Pseudo-Legal_Move
     */
    public MoveList generatePseudoLegalMoves(long fromMask, long toMask) {
        MoveList moveList = new MoveList();
        long ourPieces = this.occupied(this.sideToMove);

        // Generate moves for pieces that are not pawns.
        long nonPawns = ourPieces & fromMask & ~this.pawns() ;
        for(int from : Board.toReversedList(nonPawns)) {
            long moves = this.attacks(from) & ~ourPieces & toMask;
            for(int to : Board.toReversedList(moves)) 
                moveList.add(new Move(from, to));
        }

        // Generate castling moves.
        moveList.addAll(this.generatePseudoLegalCastlingMoves(fromMask, toMask));

        long pawns = this.pawns(this.sideToMove) & fromMask;

        if(pawns == 0) {
            return moveList;
        }

        // Generate pawn captures.
        for(int from : Board.toReversedList(pawns)) {
            long attacks = (
                this.attacks(from) & 
                (this.occupied(this.themColor()) | epSquare) & 
                toMask
            );

            for(int to : Board.toReversedList(attacks))  {
                int rank = Board.getRank(to);
                if(rank == Board.RANK_1 || rank == Board.RANK_8) {
                    moveList.add(new Move(from, to, Piece.QUEEN));
                    moveList.add(new Move(from, to, Piece.ROOK));
                    moveList.add(new Move(from, to, Piece.BISHOP));
                    moveList.add(new Move(from, to, Piece.KNIGHT));
                } else {
                    moveList.add(new Move(from, to));
                }
            }   
        }

        // Generate pawn moves.
        long singleMoves = 0;
        long doubleMoves = 0;
        if(this.sideToMove == Piece.Color.WHITE) {
            singleMoves = pawns << 8 & ~this.occupied();
            doubleMoves = singleMoves << 8 & ~this.occupied() & (Bitboard.RANK_3 | Bitboard.RANK_4);
        } else {
            singleMoves = pawns >> 8 & ~this.occupied();
            doubleMoves = singleMoves >>8 & ~this.occupied() & (Bitboard.RANK_6 | Bitboard.RANK_5);
        }

        singleMoves &= toMask;
        doubleMoves &= toMask;

        // Generate single pawn moves.
        for(int to : Board.toReversedList(singleMoves)) {
            int from = to - Move.pawn(this.sideToMove);
            int rank = Board.getRank(to);
            if(rank == Board.RANK_1 || rank == Board.RANK_8) {
                moveList.add(new Move(from, to, Piece.QUEEN));
                moveList.add(new Move(from, to, Piece.ROOK));
                moveList.add(new Move(from, to, Piece.BISHOP));
                moveList.add(new Move(from, to, Piece.KNIGHT));
            } else {
                moveList.add(new Move(from, to));
            }
        }

        // Generate double pawn moves.
        for(int to : Board.toReversedList(doubleMoves)) {
            int from = to - Move.pawn(this.sideToMove) * 2;
            moveList.add(new Move(from, to));
        }

        moveList.addAll(this.generatePesudoLegalEnPassant(fromMask));

        return moveList;
    }

    /**
     * Generate pseudo legal en passant moves.
     */
    public MoveList generatePesudoLegalEnPassant(long fromMask) {
        MoveList moveList = new MoveList();

        if((this.epSquare & this.occupied()) == 0) return moveList;

        int rank = this.sideToMove == Piece.Color.WHITE ? Board.RANK_3 : Board.RANK_4;
        int epSquare = Board.fromBB(this.epSquare);
        long capturers = (
            this.pawns(this.sideToMove) & fromMask &
            BBIndex.PAWN_ATTACKS[this.themColor()][epSquare] &
            BBIndex.RANKS[rank]
        );

        for(int capturer : Board.toReversedList(capturers))
            moveList.add(new Move(capturer, epSquare));

        return moveList;
    }

    /**
     * Generate castling moves for all kings whose position match with the 
     * given bit mask.
     */
    public MoveList generatePseudoLegalCastlingMoves(long fromMask, long toMask) {
        MoveList moveList = new MoveList();
        long bbKing = this.king(this.sideToMove) & fromMask;

        if(bbKing == 0) return moveList;

        int kingSquare = Board.fromBB(bbKing);
        int kingRank = Board.getRank(kingSquare);
        int leftRookSquare = Board.fromBB(Bitboard.FILE_A & Bitboard.RANKS[kingRank]);
        int rightRookSquare = Board.fromBB(Bitboard.FILE_H & Bitboard.RANKS[kingRank]);
        long ourPieces = this.occupied(this.sideToMove) & ~this.king(this.sideToMove);
        long castlingRights = this.castlingRightsFor(this.sideToMove);
        long castleRight = Bitboard.shiftRight(bbKing, 2) & toMask;
        long castleLeft = Bitboard.shiftLeft(bbKing, 2) & toMask;
        long blockersRight = BBIndex.BETWEEN[kingSquare][rightRookSquare] & ourPieces;
        long blockersLeft = BBIndex.BETWEEN[kingSquare][leftRookSquare] & ourPieces;

        if((castleLeft & castlingRights) != 0 && castleLeft != 0 && blockersRight == 0)
            moveList.add(new Move(kingSquare, Board.fromBB(castleLeft)));
        if((castleRight & castlingRights) != 0 && castleRight != 0  && blockersLeft == 0)
            moveList.add(new Move(kingSquare, Board.fromBB(castleRight)));

        return moveList;
    }

    /**
     * Determine if a move from one square to another is legal.
     */
    public boolean isLegal(int from, int to) {
        int piece = this.pieceAt(from);
        if(piece == 0 || !Piece.isColor(piece, this.sideToMove)) {
            return false;
        }

        return this.generateLegalMoves(Board.BB_SQUARES[from]).exists(from, to);
    }

    /**
     * Get the piece at a given square.
     */
    public int pieceAt(int square) {
        return this.pieces[square];
    }

    /**
     * Get the piece type at a given square.
     */
    public int pieceTypeAt(int square) {
        return Piece.getType(this.pieces[square]);
    }

    /**
     * The the piece color at a given square.
     */
    public int pieceColorAt(int square) {
        return Piece.getColor(this.pieces[square]);
    }

    /**
     * Get the move at a given index.
     */
    public Move moveAt(int index) {
        return this.moves[index];
    }

    /**
     * Set the side to move.
     */
    public void setSideToMove(int color) {
        this.sideToMove = color;
    }

    /**
     * Determine if a move reveals the own king into check.
     */
    public boolean isIntoCheck(Move move) {
        long king = this.king(this.sideToMove);

        long checkers = this.attackers(Piece.Color.opposite(this.sideToMove), Board.fromBB(king));
        // MoveList evasions = this.generateEvasions(king, checkers, Board.BB_SQUARES[move.from], Board.BB_SQUARES[move.to]);
        // if(checked != 0) {
        //     return true;
        // }
        
        return false;
    }

    /**
     * Generate king evasions for when the king is in check.
     */
    protected MoveList generateEvasions(long king, long checkers, long fromMask, long toMask) {
        long sliders = checkers & (this.bishops() | this.rooks() | this.queens());
        MoveList evasions = new MoveList();
        long attacked = 0;
        int kingSquare = Board.fromBB(king);
        
        for(int checker : Board.toList(checkers)) {
            attacked |= BBIndex.STRECHED[kingSquare][checker] & ~Board.BB_SQUARES[checker];
        }

        // Generate king eveasions.
        if((king & fromMask) != 0) {
            for(int to : Board.toReversedList(BBIndex.ATTACKS[Piece.KING][kingSquare] & ~this.occupied(this.sideToMove))) {
                evasions.add(new Move(kingSquare, to));
            }

            // TODO: capture the checking pawn with an en passant move.
        }

        // Moves to block check are generated when only one opponent piece is 
        // giving check.
        int checker = Bitboard.lsb(checkers);
        if(Board.BB_SQUARES[checker] == checkers) {
            long target = BBIndex.BETWEEN[kingSquare][checker] | checkers;

            // Moves that capture the checker:
            evasions.addAll(this.generatePseudoLegalMoves(fromMask, target & toMask));
        }

        return evasions;
    }

    /**
     * Generates a bitboard containing pieces that attack the given square.
     */
    public long attackers(int color, int square) {
        long diagPieces = BBIndex.BISHOP_MASKS[square] & this.occupied();
        long filePieces = BBIndex.FILE_MASKS[square] & this.occupied();
        long rankPieces = BBIndex.RANK_MASKS[square] & this.occupied();
        long queensAndRooks = this.queens() | this.rooks();
        long queensAndBishops = this.queens() | this.bishops();

        long attackers = (
            (BBIndex.ATTACKS[Piece.KING][square] & this.kings()) |
            (BBIndex.ATTACKS[Piece.KNIGHT][square] & this.knights()) |
            (BBIndex.BISHOP_ATTACKS[square].get(diagPieces) & queensAndBishops) |
            (BBIndex.FILE_ATTACKS[square].get(filePieces) & queensAndRooks) |
            (BBIndex.RANK_ATTACKS[square].get(rankPieces) & queensAndRooks) |
            (BBIndex.PAWN_ATTACKS[Piece.Color.opposite(color)][square] & this.pawns()));

        return attackers & this.piecesByColor[color];
    }

    /**
     * Generates a bitboard that contains all pieces that are attacked by the 
     * given square.
     */
    public long attacks(int square) {
        int piece = this.pieceAt(square);

        if(piece == Piece.NONE) return 0;

        int pieceType = Piece.getType(piece);

        if(pieceType == Piece.PAWN) {
            return BBIndex.PAWN_ATTACKS[Piece.getColor(piece)][square];
        }

        if(pieceType == Piece.KNIGHT || pieceType == Piece.KING) {
            return BBIndex.ATTACKS[pieceType][square];
        }

        long attacks = 0;
        
        if(pieceType != Piece.ROOK) {
            long diagPieces = BBIndex.BISHOP_MASKS[square] & this.occupied();
            attacks |= BBIndex.BISHOP_ATTACKS[square].get(diagPieces);
        }
        if(pieceType != Piece.BISHOP) {
            long filePieces = BBIndex.FILE_MASKS[square] & this.occupied();
            long rankPieces = BBIndex.RANK_MASKS[square] & this.occupied();
            attacks |= BBIndex.FILE_ATTACKS[square].get(filePieces);
            attacks |= BBIndex.RANK_ATTACKS[square].get(rankPieces);
        }

        return attacks;
    }

    /**
     * Push a move to the move stack.
     */
    public void push(Move move) {
        long fromBB = Board.BB_SQUARES[move.from];
        int toFile = Board.getFile(move.to);
        int fromFile = Board.getFile(move.from);
        int fromRank = Board.getRank(move.from);

        // Do en passant
        if(epSquare != Bitboard.EMPTY && Board.BB_SQUARES[move.to] == epSquare) {
            removePieceAt(move.from + (Board.getRank(move.to) - Board.getRank(move.from)));
        }
        
        // Reset En Passant square
        this.epSquare = 0;

        // Resetting halfmove count when move is a pawn move or a capture.
        if(move.isZeroing(this)) this.halfmoveCount = 0;
        else this.halfmoveCount++;

        int pieceType = removePieceAt(move.from);

        // Set piece type when move is promotion.
        if(move.isPromotion()) pieceType = move.promotion;

        // Place the piece at the given square.
        this.setPieceAt(move.to, pieceType, this.sideToMove);

        // Do castling.
        if(pieceType == Piece.KING && move.distance() == 2 && toFile == Board.FILE_C) {
            int rookSquare =  Board.square(fromRank, Board.FILE_A);
            this.removePieceAt(rookSquare);
            this.setPieceAt(rookSquare, Piece.ROOK, this.sideToMove);
        } else if(pieceType == Piece.KING && move.distance() == 2 && toFile == Board.FILE_G) {
            int rookSquare =  Board.square(fromRank, Board.FILE_H);
            this.removePieceAt(rookSquare);
            this.setPieceAt(rookSquare, Piece.ROOK, this.sideToMove);
        }

        // Clear castling rights after king move.
        if(pieceType == Piece.KING) {
            this.castlingRights &= this.sideToMove == Piece.Color.WHITE ? Bitboard.BLACK_SIDE : Bitboard.WHITE_SIDE;
        }

        // Clear castling rights after rook moved from original square.
        if(pieceType == Piece.ROOK && (fromBB & Bitboard.ROOK_SQUARES) != 0) {
            this.castlingRights &= ~((this.sideToMove == Piece.Color.WHITE ? Bitboard.WHITE_SIDE : Bitboard.BLACK_SIDE)
                & (fromFile == Board.FILE_A ? Bitboard.QUEEN_SIDE : Bitboard.KING_SIDE));
        }

        // Clear square above pawn when en passant move was made.
        if((Board.BB_SQUARES[move.to] & this.epSquare) != 0) {
            removePieceAt(move.to - Move.pawn(this.sideToMove));
        }

        // Remember possible En Passant for next move.
        if(pieceType == Piece.PAWN && Math.abs(Board.getRank(move.from)-Board.getRank(move.to)) == 2) {
            this.epSquare = Board.BB_SQUARES[move.from + Move.pawn(sideToMove)];
        }

        // Push move to the stack and increase index.
        this.moves[this.moveIndex] = move;
        this.moveIndex++;

        try {
            this.stack.add(this.clone());
        } catch(CloneNotSupportedException e) {}

        // Cache check info.
        this.setCheckInfo();

        // Swap side to move.
        this.sideToMove = Piece.Color.opposite(this.sideToMove);
    }

    /**
     * Restores the previous position and returns the last executed move.
     */
    public Move pop() {
        // Cannot pop when no move has been made so far.
        if(this.moveIndex == 0) return null;

        Move move = this.moves[this.moveIndex-1];
        this.stack.remove(this.stack.size() - 1);
        this.restore(this.stack.remove(this.stack.size() - 1));

        return move;
    }

    /**
     * Gets the last executed move.
     */
    public Move peek() {
        return this.moveIndex > 0 ? this.moves[this.moveIndex-1] : null;
    }

    /**
     * Set a piece at the given square.
     */
    public void setPieceAt(int square, int pieceType, int color) {
        this.removePieceAt(square);

        long mask = Board.BB_SQUARES[square];

        this.piecesByType[pieceType] |= mask;
        this.piecesByType[Piece.ANY] |= mask;
        this.piecesByColor[Piece.Color.ANY] |= mask;
        this.piecesByColor[color] |= mask;
        this.pieces[square] = Piece.make(pieceType, color);
    }

    /**
     * Remove a piece from the given square.
     */
    public int removePieceAt(int square) {
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

    /**
     * Set castling rights for the given color and rook square.
     */
    public void setCastlingRight(int color, int rookSquare) {
        if(rookSquare == Board.SQUARE_NONE) return;

        long ranks = color == Piece.Color.WHITE 
            ? Bitboard.WHITE_SIDE 
            : Bitboard.BLACK_SIDE;
        long side = Board.FILE_E < Board.getFile(rookSquare) 
            ? Bitboard.KING_SIDE
            : Bitboard.QUEEN_SIDE;

        castlingRights |= ranks & side;
    }

    /**
     * Gets bitboard that contains all castling rights for the given color.
     */
    public long castlingRightsFor(int color) {
        return this.castlingRights
            & (color == Piece.Color.WHITE
            ? Bitboard.WHITE_SIDE 
            : Bitboard.BLACK_SIDE);
    }

    /**
     * Set en passant square.
     */
    public void setEnPassantSquare(int square) {
        this.epSquare = Board.BB_SQUARES[square];
    }

    /**
     * Set en passant square by bitboard square.
     */
    public void setEnPassantSquare(long bbSquare) {
        this.epSquare = bbSquare;
    }

    /**
     * Gets the ASCII representation of the position.
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

    /**
     * Clone the position.
     */
    public Position clone() throws CloneNotSupportedException {
        return new Position(this);
    }
}