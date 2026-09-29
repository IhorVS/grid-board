package ivs.games.accessories.gridboard.standard.checkers;

import ivs.games.accessories.gridboard.standard.PieceColor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertSame;

class StandardPieceTest {

    @ParameterizedTest
    @MethodSource("pieces")
    void hasExpectedTypeAndColor(StandardPiece piece, CheckerType type, PieceColor color) {
        assertSame(type, piece.getType());
        assertSame(color, piece.getColor());
    }

    private static Stream<Arguments> pieces() {
        return Stream.of(
                Arguments.of(StandardPiece.WHITE_MAN, CheckerType.MAN, PieceColor.WHITE),
                Arguments.of(StandardPiece.WHITE_KING, CheckerType.KING, PieceColor.WHITE),
                Arguments.of(StandardPiece.BLACK_MAN, CheckerType.MAN, PieceColor.BLACK),
                Arguments.of(StandardPiece.BLACK_KING, CheckerType.KING, PieceColor.BLACK)
        );
    }
}
