package ivs.games.accessories.gridboard.standard.checkers;

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
            StandardPiece piece, CheckerType type, PieceColor color, String code) {
        assertSame(type, piece.getType());
        assertSame(color, piece.getColor());
        assertEquals(code, piece.getCode());
        assertSame(piece, StandardPiece.fromCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "wm", "W", "M", "WX", "MW", " WM"})
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
                Arguments.of(StandardPiece.WHITE_MAN, CheckerType.MAN, PieceColor.WHITE, "WM"),
                Arguments.of(StandardPiece.WHITE_KING, CheckerType.KING, PieceColor.WHITE, "WK"),
                Arguments.of(StandardPiece.BLACK_MAN, CheckerType.MAN, PieceColor.BLACK, "BM"),
                Arguments.of(StandardPiece.BLACK_KING, CheckerType.KING, PieceColor.BLACK, "BK")
        );
    }
}
