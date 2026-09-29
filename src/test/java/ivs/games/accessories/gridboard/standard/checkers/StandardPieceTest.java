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
    void hasExpectedTypeColorCodeAndSymbol(
            StandardPiece piece, CheckerType type, PieceColor color, String code, char symbol) {
        assertSame(type, piece.getType());
        assertSame(color, piece.getColor());
        assertEquals(code, piece.getCode());
        assertEquals(symbol, piece.getSymbol());
        assertSame(piece, StandardPiece.fromCode(code));
        assertSame(piece, StandardPiece.fromSymbol(symbol));
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

    @Test
    void rejectsUnknownSymbol() {
        assertThrows(IllegalArgumentException.class, () -> StandardPiece.fromSymbol('?'));
    }

    @SuppressWarnings("UnnecessaryUnicodeEscape")
    private static Stream<Arguments> pieces() {
        return Stream.of(
                Arguments.of(StandardPiece.WHITE_MAN, CheckerType.MAN, PieceColor.WHITE, "WM", '\u26C0'),
                Arguments.of(StandardPiece.WHITE_KING, CheckerType.KING, PieceColor.WHITE, "WK", '\u26C1'),
                Arguments.of(StandardPiece.BLACK_MAN, CheckerType.MAN, PieceColor.BLACK, "BM", '\u26C2'),
                Arguments.of(StandardPiece.BLACK_KING, CheckerType.KING, PieceColor.BLACK, "BK", '\u26C3')
        );
    }
}
