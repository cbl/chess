package chess;

public class Move {
    public static final int UP = 8;
    public static final int DOWN = -UP;
    public static final int RIGHT = 1;
    public static final int LEFT = -RIGHT;
    public static final int UP_RIGHT = UP + RIGHT;
    public static final int UP_LEFT = UP + LEFT;
    public static final int DOWN_RIGHT = DOWN + RIGHT;
    public static final int DOWN_LEFT = DOWN + LEFT;

    public static final int MAX_MOVES_COUNT = 256;

    public static class Block {
        public static final int TO = Board.Block.SQUARE;
        public static final int FROM = Board.Block.SQUARE << Board.Block.SQUARE_SIZE;
        public static final int PIECE_TYPE = Piece.Block.TYPE << Board.Block.SQUARE_SIZE * 2;
        public static final int COLOR = Piece.Block.COLOR << Board.Block.SQUARE_SIZE * 2 + Piece.Block.TYPE_SIZE;
        public static final int PIECE = (Piece.Block.TYPE | Piece.Block.COLOR) << Board.Block.SQUARE_SIZE * 2;
        public static final int TYPE = 0b11 << Board.Block.SQUARE_SIZE * 2 + Piece.Block.TYPE_SIZE + Piece.Block.COLOR_SIZE;
    }

    public static class Type {
        public static final int NORMAL = 0;
        public static final int PROMOTION = 1 << 17;
        public static final int EN_PASSANT = 2 << 17;
        public static final int CASTLING = 3 << 17;
    }

    public static int make(int type, int piece, int from, int to) {
        return (piece 
            << Board.Block.SQUARE_SIZE | from) 
            << Board.Block.SQUARE_SIZE | to
            | type;
    }

    public static int getPiece(int move) {
        return move >> Board.Block.SQUARE_SIZE * 2 & 0b111;
    }

    public static int getToSquare(int move) {
        return move & 63;
    }

    public static int getFromSquare(int move) {
        return move >> 6 & 63;
    }

    public static boolean isPromotion(int move) {
        return 
            Piece.isType(move, Piece.PAWN) &&
            Piece.isColor(getPiece(move), Piece.Color.WHITE) 
                ? Board.isRank(getToSquare(move), Board.RANK_1)
                : Board.isRank(getToSquare(move), Board.RANK_8);
    }
}
