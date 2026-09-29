package ivs.games.accessories.gridboard.standard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PieceColorTest {
    @Test
    void findsColorByCode() {
        assertSame(PieceColor.WHITE, PieceColor.fromCode("W"));
        assertSame(PieceColor.BLACK, PieceColor.fromCode("B"));
    }

    @Test
    void rejectsUnknownCode() {
        assertThrows(IllegalArgumentException.class, () -> PieceColor.fromCode(""));
        assertThrows(IllegalArgumentException.class, () -> PieceColor.fromCode("w"));
        assertThrows(IllegalArgumentException.class, () -> PieceColor.fromCode("X"));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void rejectsNullCode() {
        assertThrows(NullPointerException.class, () -> PieceColor.fromCode(null));
    }

    @Test
    void returnsOppositeColor() {
        assertSame(PieceColor.BLACK, PieceColor.getOpposite(PieceColor.WHITE));
        assertSame(PieceColor.WHITE, PieceColor.getOpposite(PieceColor.BLACK));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void rejectsNullColor() {
        assertThrows(NullPointerException.class, () -> PieceColor.getOpposite(null));
    }
}
