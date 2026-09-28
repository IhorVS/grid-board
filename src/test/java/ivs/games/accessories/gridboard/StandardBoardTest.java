package ivs.games.accessories.gridboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StandardBoardTest {

    private enum TestPiece implements Piece {
        FIRST,
        SECOND
    }

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
}
