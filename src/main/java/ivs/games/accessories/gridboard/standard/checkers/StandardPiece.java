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

    /** An ordinary white piece: ⛀ (U+26C0). */
    WHITE_MAN(CheckerType.MAN, PieceColor.WHITE),

    /** A white king: ⛁ (U+26C1). */
    WHITE_KING(CheckerType.KING, PieceColor.WHITE),

    /** An ordinary black piece: ⛂ (U+26C2). */
    BLACK_MAN(CheckerType.MAN, PieceColor.BLACK),

    /** A black king: ⛃ (U+26C3). */
    BLACK_KING(CheckerType.KING, PieceColor.BLACK);

    private final CheckerType type;
    private final PieceColor color;
    private final String code;
    private final char symbol;

    StandardPiece(CheckerType type, PieceColor color) {
        this.type = type;
        this.color = color;
        this.code = color.getCode() + type.getCode();
        this.symbol = getSymbol(type, color);
    }

    private static char getSymbol(CheckerType type, PieceColor color) {
        int typeOffset = type == CheckerType.KING ? 1 : 0;
        int colorOffset = color == PieceColor.BLACK ? 2 : 0;
        return (char) (0x26C0 + colorOffset + typeOffset);
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

    /**
     * Returns the standard checkers piece represented by a Unicode symbol.
     *
     * @param symbol the checkers piece symbol
     * @return the corresponding standard piece
     * @throws IllegalArgumentException if the symbol is unknown
     */
    public static StandardPiece fromSymbol(char symbol) {
        return Arrays.stream(values())
                .filter(piece -> piece.symbol == symbol)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown checkers piece symbol: " + symbol));
    }
}
