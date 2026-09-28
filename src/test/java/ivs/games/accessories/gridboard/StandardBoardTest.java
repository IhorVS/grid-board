package ivs.games.accessories.gridboard;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StandardBoardTest {

    @Test
    void createsEmptyBoardWithGivenDimensions() {
        StandardBoard<TestPiece> board = new StandardBoard<>(8, 10);

        assertEquals(8, board.getRows());
        assertEquals(10, board.getColumns());
        assertTrue(board.isEmptyCell(new Position(0, 0)));
        assertTrue(board.isEmptyCell(new Position(7, 9)));
        assertNull(board.get(new Position(7, 9)));
    }

    @Test
    void rejectsNonPositiveDimensions() {
        assertEquals("Number of rows must be positive: 0",
                assertThrows(IllegalArgumentException.class,
                        () -> new StandardBoard<TestPiece>(0, 8)).getMessage());

        assertEquals("Number of columns must be positive: -1",
                assertThrows(IllegalArgumentException.class,
                        () -> new StandardBoard<TestPiece>(8, -1)).getMessage());
    }

    @Test
    void placesPieceOnlyInSelectedCell() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 2);
        Position position = new Position(1, 0);

        board.place(position, TestPiece.FIRST);

        assertFalse(board.isEmptyCell(position));
        assertSame(TestPiece.FIRST, board.get(position));
        assertTrue(board.isEmptyCell(new Position(0, 0)));
        assertTrue(board.isEmptyCell(new Position(1, 1)));
    }

    @Test
    void rejectsPlacementInOccupiedCellWithoutReplacingPiece() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 2);
        Position position = new Position(1, 0);
        board.place(position, TestPiece.FIRST);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> board.place(position, TestPiece.SECOND));

        assertEquals("Cell is occupied: " + position, exception.getMessage());
        assertSame(TestPiece.FIRST, board.get(position));
    }

    @Test
    void rejectsNullPieceWithoutChangingCell() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 2);
        Position position = new Position(1, 0);

        assertThrows(NullPointerException.class, () -> board.place(position, null));

        assertTrue(board.isEmptyCell(position));
    }

    @Test
    void removesAndReturnsPiece() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 2);
        Position position = new Position(1, 0);
        board.place(position, TestPiece.FIRST);

        assertSame(TestPiece.FIRST, board.remove(position));
        assertTrue(board.isEmptyCell(position));
        assertNull(board.get(position));
    }

    @Test
    void rejectsRemovalFromEmptyCell() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 2);
        Position position = new Position(1, 0);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> board.remove(position));

        assertEquals("Cell is empty: " + position, exception.getMessage());
    }

    @Test
    void validatesPositionBeforeAccessingCell() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 2);

        assertThrows(NullPointerException.class, () -> board.get(null));
        assertThrows(IllegalArgumentException.class,
                () -> board.place(new Position(2, 0), TestPiece.FIRST));
    }

    @Test
    void isEmptyReflectsPlacementAndRemoval() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 2);
        Position position = new Position(0, 1);

        assertTrue(board.isEmpty());

        board.place(position, TestPiece.FIRST);
        assertFalse(board.isEmpty());

        board.remove(position);
        assertTrue(board.isEmpty());
    }

    @Test
    void getStateReturnsOnlyOccupiedCells() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 3);
        Position first = new Position(0, 1);
        Position second = new Position(1, 2);
        board.place(first, TestPiece.FIRST);
        board.place(second, TestPiece.SECOND);

        assertEquals(Map.of(
                first, TestPiece.FIRST,
                second, TestPiece.SECOND), board.getState());
    }

    @Test
    void getStateReturnsUnmodifiableSnapshot() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 2);
        Position position = new Position(0, 1);
        board.place(position, TestPiece.FIRST);

        Map<Position, TestPiece> snapshot = board.getState();

        assertThrows(UnsupportedOperationException.class,
                () -> snapshot.put(new Position(1, 1), TestPiece.SECOND));

        board.remove(position);

        assertEquals(Map.of(position, TestPiece.FIRST), snapshot);
        assertTrue(board.getState().isEmpty());
    }

    @Test
    void setStateReplacesBoardContents() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 2);
        Position oldPosition = new Position(0, 0);
        Position newPosition = new Position(1, 1);
        board.place(oldPosition, TestPiece.FIRST);

        board.setState(Map.of(newPosition, TestPiece.SECOND));

        assertNull(board.get(oldPosition));
        assertSame(TestPiece.SECOND, board.get(newPosition));
        assertEquals(Map.of(newPosition, TestPiece.SECOND), board.getState());
    }

    @Test
    void setStateDoesNotRetainInputMap() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 2);
        Position position = new Position(0, 1);
        Map<Position, TestPiece> state = new HashMap<>();
        state.put(position, TestPiece.FIRST);

        board.setState(state);
        state.clear();

        assertSame(TestPiece.FIRST, board.get(position));
    }

    @Test
    void setStateRejectsInvalidStateWithoutChangingBoard() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 2);
        Position existingPosition = new Position(0, 0);
        board.place(existingPosition, TestPiece.FIRST);
        Map<Position, TestPiece> originalState = board.getState();

        Map<Position, TestPiece> outsideBoard = new HashMap<>();
        outsideBoard.put(new Position(2, 0), TestPiece.SECOND);
        assertThrows(IllegalArgumentException.class, () -> board.setState(outsideBoard));
        assertEquals(originalState, board.getState());

        Map<Position, TestPiece> nullPosition = new HashMap<>();
        nullPosition.put(null, TestPiece.SECOND);
        assertThrows(NullPointerException.class, () -> board.setState(nullPosition));
        assertEquals(originalState, board.getState());

        Map<Position, TestPiece> nullPiece = new HashMap<>();
        nullPiece.put(new Position(1, 1), null);
        assertThrows(NullPointerException.class, () -> board.setState(nullPiece));
        assertEquals(originalState, board.getState());

        assertThrows(NullPointerException.class, () -> board.setState(null));
        assertEquals(originalState, board.getState());
    }

    @Test
    void clearRemovesPiecesWithoutChangingDimensions() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 3);
        board.place(new Position(0, 0), TestPiece.FIRST);
        board.place(new Position(1, 2), TestPiece.SECOND);

        board.clear();

        assertTrue(board.isEmpty());
        assertTrue(board.getState().isEmpty());
        assertEquals(2, board.getRows());
        assertEquals(3, board.getColumns());
    }
}
