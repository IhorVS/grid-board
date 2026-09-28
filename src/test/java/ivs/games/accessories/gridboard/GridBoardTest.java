package ivs.games.accessories.gridboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GridBoardTest {

    private GridBoard<?> board;

    @BeforeEach
    void setUp() {
        board = mock(GridBoard.class, CALLS_REAL_METHODS);
        when(board.getRows()).thenReturn(8);
        when(board.getColumns()).thenReturn(10);
    }

    @Test
    void acceptsPositionsOnBoardBoundaries() {
        assertDoesNotThrow(() -> board.validatePosition(new Position(0, 0)));
        assertDoesNotThrow(() -> board.validatePosition(new Position(7, 9)));
    }

    @Test
    void rejectsNegativeRow() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> board.validatePosition(new Position(-1, 0)));

        assertEquals("Row -1 is outside the board. Valid rows: 0 to 7", exception.getMessage());
    }

    @Test
    void rejectsRowBeyondBoard() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> board.validatePosition(new Position(8, 0)));

        assertEquals("Row 8 is outside the board. Valid rows: 0 to 7", exception.getMessage());
    }

    @Test
    void rejectsNegativeColumn() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> board.validatePosition(new Position(0, -1)));

        assertEquals("Column -1 is outside the board. Valid columns: 0 to 9", exception.getMessage());
    }

    @Test
    void rejectsColumnBeyondBoard() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> board.validatePosition(new Position(0, 10)));

        assertEquals("Column 10 is outside the board. Valid columns: 0 to 9", exception.getMessage());
    }

    @Test
    void rejectsNullPosition() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> board.validatePosition(null));

        assertEquals("Position must not be null", exception.getMessage());
    }
}
