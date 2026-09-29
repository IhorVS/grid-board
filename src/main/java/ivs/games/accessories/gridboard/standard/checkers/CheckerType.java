package ivs.games.accessories.gridboard.standard.checkers;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Types of pieces in a standard checkers set.
 *
 * <p>Codes are short identifiers for displaying piece types. They do not
 * define a notation for checkers moves.</p>
 */
@Getter
@RequiredArgsConstructor
public enum CheckerType implements CheckersPieceType {

    /**
     * An ordinary checkers piece.
     */
    MAN("M"),

    /**
     * A promoted checkers piece.
     */
    KING("K");

    private final String code;

    /**
     * Returns the piece type identified by the given code.
     *
     * @param code the case-sensitive piece type code
     * @return the corresponding piece type
     * @throws NullPointerException     if {@code code} is {@code null}
     * @throws IllegalArgumentException if the code is unknown
     */
    public static CheckerType fromCode(@NonNull String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown checkers piece type code: " + code));
    }
}
