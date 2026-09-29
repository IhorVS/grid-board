package ivs.games.accessories.gridboard.standard.chess;

import ivs.games.accessories.gridboard.standard.PieceColor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Pieces in a standard chess set.
 *
 * <p>Each constant describes a type and color. The same constant may occupy
 * multiple cells; individual pieces are identified by their positions.</p>
 */
@RequiredArgsConstructor
@Getter
public enum StandardPiece implements ChessPiece {

    /** A white pawn. */
    WHITE_PAWN(ChessType.PAWN, PieceColor.WHITE),

    /** A white knight. */
    WHITE_KNIGHT(ChessType.KNIGHT, PieceColor.WHITE),

    /** A white bishop. */
    WHITE_BISHOP(ChessType.BISHOP, PieceColor.WHITE),

    /** A white rook. */
    WHITE_ROOK(ChessType.ROOK, PieceColor.WHITE),

    /** A white queen. */
    WHITE_QUEEN(ChessType.QUEEN, PieceColor.WHITE),

    /** A white king. */
    WHITE_KING(ChessType.KING, PieceColor.WHITE),

    /** A black pawn. */
    BLACK_PAWN(ChessType.PAWN, PieceColor.BLACK),

    /** A black knight. */
    BLACK_KNIGHT(ChessType.KNIGHT, PieceColor.BLACK),

    /** A black bishop. */
    BLACK_BISHOP(ChessType.BISHOP, PieceColor.BLACK),

    /** A black rook. */
    BLACK_ROOK(ChessType.ROOK, PieceColor.BLACK),

    /** A black queen. */
    BLACK_QUEEN(ChessType.QUEEN, PieceColor.BLACK),

    /** A black king. */
    BLACK_KING(ChessType.KING, PieceColor.BLACK);

    private final ChessType type;
    private final PieceColor color;
}
