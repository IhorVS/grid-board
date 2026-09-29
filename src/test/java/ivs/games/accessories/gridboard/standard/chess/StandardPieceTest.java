package ivs.games.accessories.gridboard.standard.chess;

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
            StandardPiece piece, ChessType type, PieceColor color, String code, char symbol) {
        assertSame(type, piece.getType());
        assertSame(color, piece.getColor());
        assertEquals(code, piece.getCode());
        assertEquals(symbol, piece.getSymbol());
        assertSame(piece, StandardPiece.fromCode(code));
        assertSame(piece, StandardPiece.fromSymbol(symbol));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "wp", "W", "P", "WX", "PW", " WP"})
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
                Arguments.of(StandardPiece.WHITE_PAWN, ChessType.PAWN, PieceColor.WHITE, "WP", '\u2659'),
                Arguments.of(StandardPiece.WHITE_KNIGHT, ChessType.KNIGHT, PieceColor.WHITE, "WN", '\u2658'),
                Arguments.of(StandardPiece.WHITE_BISHOP, ChessType.BISHOP, PieceColor.WHITE, "WB", '\u2657'),
                Arguments.of(StandardPiece.WHITE_ROOK, ChessType.ROOK, PieceColor.WHITE, "WR", '\u2656'),
                Arguments.of(StandardPiece.WHITE_QUEEN, ChessType.QUEEN, PieceColor.WHITE, "WQ", '\u2655'),
                Arguments.of(StandardPiece.WHITE_KING, ChessType.KING, PieceColor.WHITE, "WK", '\u2654'),
                Arguments.of(StandardPiece.BLACK_PAWN, ChessType.PAWN, PieceColor.BLACK, "BP", '\u265F'),
                Arguments.of(StandardPiece.BLACK_KNIGHT, ChessType.KNIGHT, PieceColor.BLACK, "BN", '\u265E'),
                Arguments.of(StandardPiece.BLACK_BISHOP, ChessType.BISHOP, PieceColor.BLACK, "BB", '\u265D'),
                Arguments.of(StandardPiece.BLACK_ROOK, ChessType.ROOK, PieceColor.BLACK, "BR", '\u265C'),
                Arguments.of(StandardPiece.BLACK_QUEEN, ChessType.QUEEN, PieceColor.BLACK, "BQ", '\u265B'),
                Arguments.of(StandardPiece.BLACK_KING, ChessType.KING, PieceColor.BLACK, "BK", '\u265A')
        );
    }
}
