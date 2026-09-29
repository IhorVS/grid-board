package ivs.games.accessories.gridboard.standard.chess;

import ivs.games.accessories.gridboard.standard.PieceColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StandardPieceTest {

    @ParameterizedTest
    @MethodSource("pieces")
    void hasExpectedTypeColorAndCode(
            StandardPiece piece, ChessType type, PieceColor color, String code) {
        assertSame(type, piece.getType());
        assertSame(color, piece.getColor());
        assertEquals(code, piece.getCode());
        assertSame(piece, StandardPiece.fromCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "wp", "W", "P", "WX", "PW", " WP"})
    void rejectsUnknownCode(String code) {
        assertThrows(IllegalArgumentException.class, () -> StandardPiece.fromCode(code));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void rejectsNullCode() {
        assertThrows(NullPointerException.class, () -> StandardPiece.fromCode(null));
    }

    private static Stream<Arguments> pieces() {
        return Stream.of(
                Arguments.of(StandardPiece.WHITE_PAWN, ChessType.PAWN, PieceColor.WHITE, "WP"),
                Arguments.of(StandardPiece.WHITE_KNIGHT, ChessType.KNIGHT, PieceColor.WHITE, "WN"),
                Arguments.of(StandardPiece.WHITE_BISHOP, ChessType.BISHOP, PieceColor.WHITE, "WB"),
                Arguments.of(StandardPiece.WHITE_ROOK, ChessType.ROOK, PieceColor.WHITE, "WR"),
                Arguments.of(StandardPiece.WHITE_QUEEN, ChessType.QUEEN, PieceColor.WHITE, "WQ"),
                Arguments.of(StandardPiece.WHITE_KING, ChessType.KING, PieceColor.WHITE, "WK"),
                Arguments.of(StandardPiece.BLACK_PAWN, ChessType.PAWN, PieceColor.BLACK, "BP"),
                Arguments.of(StandardPiece.BLACK_KNIGHT, ChessType.KNIGHT, PieceColor.BLACK, "BN"),
                Arguments.of(StandardPiece.BLACK_BISHOP, ChessType.BISHOP, PieceColor.BLACK, "BB"),
                Arguments.of(StandardPiece.BLACK_ROOK, ChessType.ROOK, PieceColor.BLACK, "BR"),
                Arguments.of(StandardPiece.BLACK_QUEEN, ChessType.QUEEN, PieceColor.BLACK, "BQ"),
                Arguments.of(StandardPiece.BLACK_KING, ChessType.KING, PieceColor.BLACK, "BK")
        );
    }
}
