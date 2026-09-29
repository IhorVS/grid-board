package ivs.games.accessories.gridboard.standard.chess;

import ivs.games.accessories.gridboard.Position;
import ivs.games.accessories.gridboard.standard.PieceColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChessBoardTest {

    private enum AdditionalType implements ChessPieceType {
        ARCHBISHOP;

        @Override
        public String getCode() {
            return "A";
        }
    }

    private enum AdditionalPiece implements ChessPiece {
        WHITE_ARCHBISHOP;

        @Override
        public String getCode() {
            return "WA";
        }

        @Override
        public ChessPieceType getType() {
            return AdditionalType.ARCHBISHOP;
        }

        @Override
        public PieceColor getColor() {
            return PieceColor.WHITE;
        }
    }

    @Test
    void createsEmptyBoardWithGivenDimensions() {
        ChessBoard<StandardPiece> board = new ChessBoard<>(8, 10);

        assertEquals(8, board.getRows());
        assertEquals(10, board.getColumns());
        assertTrue(board.isEmpty());
    }

    @Test
    void acceptsStandardChessPieces() {
        ChessBoard<StandardPiece> board = new ChessBoard<>(8, 8);
        Position position = new Position(0, 4);

        board.place(position, StandardPiece.BLACK_KING);

        assertSame(StandardPiece.BLACK_KING, board.get(position));
    }

    @Test
    void acceptsCustomChessPieces() {
        ChessBoard<AdditionalPiece> board = new ChessBoard<>(8, 10);
        Position position = new Position(0, 2);

        board.place(position, AdditionalPiece.WHITE_ARCHBISHOP);

        assertSame(AdditionalPiece.WHITE_ARCHBISHOP, board.get(position));
    }

    @Test
    void copyConstructorCreatesIndependentChessBoard() {
        ChessBoard<StandardPiece> source = new ChessBoard<>(8, 8);
        Position position = new Position(0, 4);
        source.place(position, StandardPiece.BLACK_KING);

        ChessBoard<StandardPiece> copy = new ChessBoard<>(source);
        copy.remove(position);

        assertEquals(8, copy.getRows());
        assertEquals(8, copy.getColumns());
        assertNull(copy.get(position));
        assertSame(StandardPiece.BLACK_KING, source.get(position));
    }

    @Test
    void rejectsNullSource() {
        assertThrows(NullPointerException.class, () -> new ChessBoard<>((ChessBoard<StandardPiece>) null));
    }
}
