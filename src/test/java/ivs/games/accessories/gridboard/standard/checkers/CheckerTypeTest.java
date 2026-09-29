package ivs.games.accessories.gridboard.standard.checkers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckerTypeTest {

    @ParameterizedTest
    @CsvSource({"MAN, M", "KING, K"})
    void hasExpectedCodeAndParsesIt(CheckerType type, String code) {
        assertEquals(code, type.getCode());
        assertSame(type, CheckerType.fromCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "m", "X", "MM", " M"})
    void rejectsUnknownCode(String code) {
        assertThrows(IllegalArgumentException.class, () -> CheckerType.fromCode(code));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void rejectsNullCode() {
        assertThrows(NullPointerException.class, () -> CheckerType.fromCode(null));
    }
}
