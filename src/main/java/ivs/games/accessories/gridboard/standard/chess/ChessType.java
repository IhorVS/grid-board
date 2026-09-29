package ivs.games.accessories.gridboard.standard.chess;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Types of pieces in a standard chess set.
 *
 * <p>Codes are short identifiers for displaying piece types. They do not
 * define how moves are written in chess notation.</p>
 */
@Getter
@RequiredArgsConstructor
public enum ChessType implements ChessPieceType {

    /**
     * A pawn.
     */
    PAWN("P"),

    /**
     * A knight.
     */
    KNIGHT("N"),

    /**
     * A bishop.
     */
    BISHOP("B"),

    /**
     * A rook.
     */
    ROOK("R"),

    /**
     * A queen.
     */
    QUEEN("Q"),

    /**
     * A king.
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
    public static ChessType fromCode(@NonNull String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown chess piece type code: " + code));
    }
}
