package ivs.games.accessories.gridboard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CellNotationTest {

    @Test
    void parsesCellAddresses() {
        assertEquals(new Position(0, 0), CellNotation.parse("a1"));
        assertEquals(new Position(1, 4), CellNotation.parse("e2"));
        assertEquals(new Position(7, 7), CellNotation.parse("h8"));
        assertEquals(new Position(98, 25), CellNotation.parse("z99"));
    }

    @Test
    void formatsPositions() {
        assertEquals("a1", CellNotation.format(new Position(0, 0)));
        assertEquals("e2", CellNotation.format(new Position(1, 4)));
        assertEquals("h8", CellNotation.format(new Position(7, 7)));
        assertEquals("z99", CellNotation.format(new Position(98, 25)));
    }

    @Test
    void supportsLargestRepresentableRow() {
        Position position = new Position(Integer.MAX_VALUE - 1, 25);

        assertEquals("z2147483647", CellNotation.format(position));
        assertEquals(position, CellNotation.parse("z2147483647"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "1", "A1", "a0", "a01", "aa1", "a-1", "a1 ", "1a"})
    void rejectsInvalidAddresses(String address) {
        assertThrows(IllegalArgumentException.class, () -> CellNotation.parse(address));
    }

    @Test
    void rejectsRowNumberOutsideIntegerRange() {
        assertThrows(IllegalArgumentException.class,
                () -> CellNotation.parse("a2147483648"));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void rejectsNullAddress() {
        assertThrows(NullPointerException.class, () -> CellNotation.parse(null));
    }

    @Test
    void rejectsInvalidPositions() {
        assertThrows(IllegalArgumentException.class,
                () -> CellNotation.format(new Position(-1, 0)));
        assertThrows(IllegalArgumentException.class,
                () -> CellNotation.format(new Position(Integer.MAX_VALUE, 0)));
        assertThrows(IllegalArgumentException.class,
                () -> CellNotation.format(new Position(0, -1)));
        assertThrows(IllegalArgumentException.class,
                () -> CellNotation.format(new Position(0, 26)));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void rejectsNullPosition() {
        assertThrows(NullPointerException.class, () -> CellNotation.format(null));
    }
}
