package ivs.games.accessories.gridboard.standard;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * The color of a piece in a standard two-player set.
 */
@Getter
@RequiredArgsConstructor
public enum PieceColor {

    /**
     * The white piece color.
     */
    WHITE("W"),

    /**
     * The black piece color.
     */
    BLACK("B");

    private final String code;

    /**
     * Returns the color identified by the given code.
     *
     * @param code the case-sensitive color code
     * @return the corresponding color
     * @throws NullPointerException     if {@code code} is {@code null}
     * @throws IllegalArgumentException if the code is unknown
     */
    public static PieceColor fromCode(@NonNull String code) {
        return switch (code) {
            case "W" -> WHITE;
            case "B" -> BLACK;
            default -> throw new IllegalArgumentException("Unknown piece color code: " + code);
        };
    }

    /**
     * Returns the opposite piece color.
     *
     * @param color the color whose opposite is requested
     * @return {@code BLACK} for {@code WHITE}, or {@code WHITE} for {@code BLACK}
     * @throws NullPointerException if {@code color} is {@code null}
     */
    public static PieceColor getOpposite(@NonNull PieceColor color) {
        return switch (color) {
            case WHITE -> BLACK;
            case BLACK -> WHITE;
        };
    }
}
