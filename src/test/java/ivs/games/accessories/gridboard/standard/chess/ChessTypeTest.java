package ivs.games.accessories.gridboard.standard.chess;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChessTypeTest {

    @ParameterizedTest
    @MethodSource("typesAndCodes")
    void hasExpectedCode(ChessType type, String code) {
        assertEquals(code, type.getCode());
    }

    @ParameterizedTest
    @MethodSource("typesAndCodes")
    void findsTypeByCode(ChessType type, String code) {
        assertSame(type, ChessType.fromCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "p", "X", "PP", " P"})
    void rejectsUnknownCode(String code) {
        assertThrows(IllegalArgumentException.class, () -> ChessType.fromCode(code));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void rejectsNullCode() {
        assertThrows(NullPointerException.class, () -> ChessType.fromCode(null));
    }

    private static Stream<Arguments> typesAndCodes() {
        return Stream.of(
                Arguments.of(ChessType.PAWN, "P"),
                Arguments.of(ChessType.KNIGHT, "N"),
                Arguments.of(ChessType.BISHOP, "B"),
                Arguments.of(ChessType.ROOK, "R"),
                Arguments.of(ChessType.QUEEN, "Q"),
                Arguments.of(ChessType.KING, "K")
        );
    }
}
