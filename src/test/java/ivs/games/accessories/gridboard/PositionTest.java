package ivs.games.accessories.gridboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PositionTest {

    @Test
    void translatesPositionByRowAndColumnDeltas() {
        Position position = new Position(3, 4);

        assertEquals(new Position(2, 6), position.translate(-1, 2));
        assertEquals(new Position(5, 1), position.translate(2, -3));
        assertEquals(position, position.translate(0, 0));
    }

    @Test
    void translationDoesNotCheckBoardBoundaries() {
        Position position = new Position(0, 0);

        assertEquals(new Position(-1, -2), position.translate(-1, -2));
    }

    @Test
    void calculatesSignedRowDelta() {
        Position position = new Position(3, 4);

        assertEquals(-2, position.rowDeltaTo(new Position(1, 7)));
        assertEquals(2, position.rowDeltaTo(new Position(5, 1)));
        assertEquals(0, position.rowDeltaTo(new Position(3, 0)));
    }

    @Test
    void calculatesSignedColumnDelta() {
        Position position = new Position(3, 4);

        assertEquals(-3, position.columnDeltaTo(new Position(1, 1)));
        assertEquals(3, position.columnDeltaTo(new Position(5, 7)));
        assertEquals(0, position.columnDeltaTo(new Position(0, 4)));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void rejectsNullDestination() {
        Position position = new Position(3, 4);

        assertThrows(NullPointerException.class, () -> position.rowDeltaTo(null));
        assertThrows(NullPointerException.class, () -> position.columnDeltaTo(null));
    }
}
