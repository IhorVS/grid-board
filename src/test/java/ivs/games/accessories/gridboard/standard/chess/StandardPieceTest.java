package ivs.games.accessories.gridboard.standard.chess;

import ivs.games.accessories.gridboard.standard.PieceColor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertSame;

class StandardPieceTest {

    @ParameterizedTest
    @MethodSource("pieces")
    void hasExpectedTypeAndColor(StandardPiece piece, ChessType type, PieceColor color) {
        assertSame(type, piece.getType());
        assertSame(color, piece.getColor());
    }

    private static Stream<Arguments> pieces() {
        return Stream.of(
                Arguments.of(StandardPiece.WHITE_PAWN, ChessType.PAWN, PieceColor.WHITE),
                Arguments.of(StandardPiece.WHITE_KNIGHT, ChessType.KNIGHT, PieceColor.WHITE),
                Arguments.of(StandardPiece.WHITE_BISHOP, ChessType.BISHOP, PieceColor.WHITE),
                Arguments.of(StandardPiece.WHITE_ROOK, ChessType.ROOK, PieceColor.WHITE),
                Arguments.of(StandardPiece.WHITE_QUEEN, ChessType.QUEEN, PieceColor.WHITE),
                Arguments.of(StandardPiece.WHITE_KING, ChessType.KING, PieceColor.WHITE),
                Arguments.of(StandardPiece.BLACK_PAWN, ChessType.PAWN, PieceColor.BLACK),
                Arguments.of(StandardPiece.BLACK_KNIGHT, ChessType.KNIGHT, PieceColor.BLACK),
                Arguments.of(StandardPiece.BLACK_BISHOP, ChessType.BISHOP, PieceColor.BLACK),
                Arguments.of(StandardPiece.BLACK_ROOK, ChessType.ROOK, PieceColor.BLACK),
                Arguments.of(StandardPiece.BLACK_QUEEN, ChessType.QUEEN, PieceColor.BLACK),
                Arguments.of(StandardPiece.BLACK_KING, ChessType.KING, PieceColor.BLACK)
        );
    }
}
