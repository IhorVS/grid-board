package ivs.games.accessories.gridboard.standard.checkers;

import ivs.games.accessories.gridboard.Position;
import ivs.games.accessories.gridboard.standard.PieceColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckersBoardTest {

    private enum AdditionalType implements CheckersPieceType {
        CUSTOM
    }

    private enum AdditionalPiece implements CheckersPiece {
        WHITE_CUSTOM;

        @Override
        public CheckersPieceType getType() {
            return AdditionalType.CUSTOM;
        }

        @Override
        public PieceColor getColor() {
            return PieceColor.WHITE;
        }
    }

    @Test
    void createsEmptyBoardWithGivenDimensions() {
        CheckersBoard<StandardPiece> board = new CheckersBoard<>(10, 10);

        assertEquals(10, board.getRows());
        assertEquals(10, board.getColumns());
        assertTrue(board.isEmpty());
    }

    @Test
    void acceptsStandardCheckersPieces() {
        CheckersBoard<StandardPiece> board = new CheckersBoard<>(8, 8);
        Position position = new Position(2, 1);

        board.place(position, StandardPiece.WHITE_MAN);

        assertSame(StandardPiece.WHITE_MAN, board.get(position));
    }

    @Test
    void acceptsCustomCheckersPieces() {
        CheckersBoard<AdditionalPiece> board = new CheckersBoard<>(10, 10);
        Position position = new Position(3, 4);

        board.place(position, AdditionalPiece.WHITE_CUSTOM);

        assertSame(AdditionalPiece.WHITE_CUSTOM, board.get(position));
    }

    @Test
    void copyConstructorCreatesIndependentCheckersBoard() {
        CheckersBoard<StandardPiece> source = new CheckersBoard<>(8, 8);
        Position position = new Position(2, 1);
        source.place(position, StandardPiece.WHITE_MAN);

        CheckersBoard<StandardPiece> copy = new CheckersBoard<>(source);
        copy.remove(position);

        assertEquals(8, copy.getRows());
        assertEquals(8, copy.getColumns());
        assertNull(copy.get(position));
        assertSame(StandardPiece.WHITE_MAN, source.get(position));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void rejectsNullSource() {
        assertThrows(NullPointerException.class, () -> new CheckersBoard<>(null));
    }
}
