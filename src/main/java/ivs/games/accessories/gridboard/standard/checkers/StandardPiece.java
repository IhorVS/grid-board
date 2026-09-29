package ivs.games.accessories.gridboard.standard.checkers;

import ivs.games.accessories.gridboard.standard.PieceColor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;

/**
 * Pieces in a standard checkers set.
 *
 * <p>Each constant describes a type and color. The same constant may occupy
 * multiple cells; individual pieces are identified by their positions.</p>
 */
@Getter
public enum StandardPiece implements CheckersPiece {

    /**
     * An ordinary white piece.
     */
    WHITE_MAN(CheckerType.MAN, PieceColor.WHITE),

    /**
     * A white king.
     */
    WHITE_KING(CheckerType.KING, PieceColor.WHITE),

    /**
     * An ordinary black piece.
     */
    BLACK_MAN(CheckerType.MAN, PieceColor.BLACK),

    /**
     * A black king.
     */
    BLACK_KING(CheckerType.KING, PieceColor.BLACK);

    private final CheckerType type;
    private final PieceColor color;
    private final String code;

    StandardPiece(CheckerType type, PieceColor color) {
        this.type = type;
        this.color = color;
        this.code = color.getCode() + type.getCode();
    }

    /**
     * Returns the standard checkers piece identified by its color and type code.
     *
     * @param code the case-sensitive piece code, such as {@code WM} or {@code BK}
     * @return the corresponding standard piece
     * @throws NullPointerException     if {@code code} is {@code null}
     * @throws IllegalArgumentException if the code is unknown
     */
    public static StandardPiece fromCode(@NonNull String code) {
        return Arrays.stream(values())
                .filter(piece -> piece.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown checkers piece code: " + code));
    }
}
