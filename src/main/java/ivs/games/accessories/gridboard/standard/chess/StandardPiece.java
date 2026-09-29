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

    /** A white pawn: ♙ (U+2659). */
    WHITE_PAWN(ChessType.PAWN, PieceColor.WHITE),

    /** A white knight: ♘ (U+2658). */
    WHITE_KNIGHT(ChessType.KNIGHT, PieceColor.WHITE),

    /** A white bishop: ♗ (U+2657). */
    WHITE_BISHOP(ChessType.BISHOP, PieceColor.WHITE),

    /** A white rook: ♖ (U+2656). */
    WHITE_ROOK(ChessType.ROOK, PieceColor.WHITE),

    /** A white queen: ♕ (U+2655). */
    WHITE_QUEEN(ChessType.QUEEN, PieceColor.WHITE),

    /** A white king: ♔ (U+2654). */
    WHITE_KING(ChessType.KING, PieceColor.WHITE),

    /** A black pawn: ♟ (U+265F). */
    BLACK_PAWN(ChessType.PAWN, PieceColor.BLACK),

    /** A black knight: ♞ (U+265E). */
    BLACK_KNIGHT(ChessType.KNIGHT, PieceColor.BLACK),

    /** A black bishop: ♝ (U+265D). */
    BLACK_BISHOP(ChessType.BISHOP, PieceColor.BLACK),

    /** A black rook: ♜ (U+265C). */
    BLACK_ROOK(ChessType.ROOK, PieceColor.BLACK),

    /** A black queen: ♛ (U+265B). */
    BLACK_QUEEN(ChessType.QUEEN, PieceColor.BLACK),

    /** A black king: ♚ (U+265A). */
    BLACK_KING(ChessType.KING, PieceColor.BLACK);

    private final ChessType type;
    private final PieceColor color;
    private final String code;
    private final char symbol;

    StandardPiece(ChessType type, PieceColor color) {
        this.type = type;
        this.color = color;
        this.code = color.getCode() + type.getCode();

        this.symbol = getSymbol(type, color);
    }

    private static char getSymbol(ChessType type, PieceColor color) {
        int typeOffset = switch (type) {
            case KING -> 0;
            case QUEEN -> 1;
            case ROOK -> 2;
            case BISHOP -> 3;
            case KNIGHT -> 4;
            case PAWN -> 5;
        };
        int colorOffset = color == PieceColor.BLACK ? 6 : 0;
        return (char) (0x2654 + colorOffset + typeOffset);
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

    /**
     * Returns the standard chess piece represented by a Unicode symbol.
     *
     * @param symbol the chess piece symbol
     * @return the corresponding standard piece
     * @throws IllegalArgumentException if the symbol is unknown
     */
    public static StandardPiece fromSymbol(char symbol) {
        return Arrays.stream(values())
                .filter(piece -> piece.symbol == symbol)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown chess piece symbol: " + symbol));
    }
}
