package ivs.games.accessories.gridboard.standard.chess;

import ivs.games.accessories.gridboard.standard.PieceColor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;

/**
 * Pieces in a standard chess set.
 *
 * <p>Each constant describes a type and color. The same constant may occupy
 * multiple cells; individual pieces are identified by their positions.</p>
 */
@Getter
public enum StandardPiece implements ChessPiece {

    /**
     * A white pawn.
     */
    WHITE_PAWN(ChessType.PAWN, PieceColor.WHITE),

    /**
     * A white knight.
     */
    WHITE_KNIGHT(ChessType.KNIGHT, PieceColor.WHITE),

    /**
     * A white bishop.
     */
    WHITE_BISHOP(ChessType.BISHOP, PieceColor.WHITE),

    /**
     * A white rook.
     */
    WHITE_ROOK(ChessType.ROOK, PieceColor.WHITE),

    /**
     * A white queen.
     */
    WHITE_QUEEN(ChessType.QUEEN, PieceColor.WHITE),

    /**
     * A white king.
     */
    WHITE_KING(ChessType.KING, PieceColor.WHITE),

    /**
     * A black pawn.
     */
    BLACK_PAWN(ChessType.PAWN, PieceColor.BLACK),

    /**
     * A black knight.
     */
    BLACK_KNIGHT(ChessType.KNIGHT, PieceColor.BLACK),

    /**
     * A black bishop.
     */
    BLACK_BISHOP(ChessType.BISHOP, PieceColor.BLACK),

    /**
     * A black rook.
     */
    BLACK_ROOK(ChessType.ROOK, PieceColor.BLACK),

    /**
     * A black queen.
     */
    BLACK_QUEEN(ChessType.QUEEN, PieceColor.BLACK),

    /**
     * A black king.
     */
    BLACK_KING(ChessType.KING, PieceColor.BLACK);

    private final ChessType type;
    private final PieceColor color;
    private final String code;

    StandardPiece(ChessType type, PieceColor color) {
        this.type = type;
        this.color = color;
        this.code = color.getCode() + type.getCode();
    }

    /**
     * Returns the standard chess piece identified by its color and type code.
     *
     * @param code the case-sensitive piece code, such as {@code WP} or {@code BK}
     * @return the corresponding standard piece
     * @throws NullPointerException     if {@code code} is {@code null}
     * @throws IllegalArgumentException if the code is unknown
     */
    public static StandardPiece fromCode(@NonNull String code) {
        return Arrays.stream(values())
                .filter(piece -> piece.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown chess piece code: " + code));
    }
}
