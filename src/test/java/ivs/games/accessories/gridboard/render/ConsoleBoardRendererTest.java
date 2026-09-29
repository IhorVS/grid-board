package ivs.games.accessories.gridboard.render;

import ivs.games.accessories.gridboard.Piece;
import ivs.games.accessories.gridboard.Position;
import ivs.games.accessories.gridboard.StandardBoard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleBoardRendererTest {

    private enum TestPiece implements Piece {
        SHORT("X"),
        LONG("LONG"),
        ODD("ABC");

        private final String code;

        TestPiece(String code) {
            this.code = code;
        }

        @Override
        public String getCode() {
            return code;
        }
    }

    @Test
    void rendersEmptyBoardWithSpacesAndLabelsOnAllSides() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 2);
        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(TestPiece::getCode);

        assertEquals(lines(
                "    1   2  ",
                "  +---+---+",
                "b |   |   | b",
                "  +---+---+",
                "a |   |   | a",
                "  +---+---+",
                "    1   2  "
        ), renderer.render(board));
    }

    @Test
    void rendersRectangularBoardWithLetteredColumnsFromTopToBottom() {
        StandardBoard<TestPiece> board = new StandardBoard<>(3, 2);
        board.place(new Position(0, 0), TestPiece.SHORT);
        board.place(new Position(2, 1), TestPiece.ODD);

        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(TestPiece::getCode);

        assertEquals(lines(
                "     1     2     3   ",
                "  +-----+-----+-----+",
                "b |     |     | ABC | b",
                "  +-----+-----+-----+",
                "a |  X  |     |     | a",
                "  +-----+-----+-----+",
                "     1     2     3   "
        ), renderer.render(board));
    }

    @Test
    void determinesAutomaticWidthFromLongestPieceRepresentation() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 1);
        board.place(new Position(0, 0), TestPiece.SHORT);
        board.place(new Position(1, 0), TestPiece.LONG);

        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(TestPiece::getCode);

        assertEquals(lines(
                "     1      2    ",
                "  +------+------+",
                "a |  X   | LONG | a",
                "  +------+------+",
                "     1      2    "
        ), renderer.render(board));
    }

    @Test
    void determinesAutomaticWidthFromNumericLabels() {
        StandardBoard<TestPiece> board = new StandardBoard<>(10, 1);
        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(TestPiece::getCode);

        String[] output = renderer.render(board).split(System.lineSeparator());
        String expectedLabels = "    1    2    3    4    5    6    7    8    9    10  ";

        assertEquals(expectedLabels, output[0]);
        assertEquals("  +" + "----+".repeat(10), output[1]);
        assertEquals("a |" + "    |".repeat(10) + " a", output[2]);
        assertEquals(expectedLabels, output[4]);
    }

    @Test
    void usesFixedCellWidthAndPlacesExtraPaddingOnTheRight() {
        StandardBoard<TestPiece> board = new StandardBoard<>(1, 1);
        board.place(new Position(0, 0), TestPiece.ODD);

        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(TestPiece::getCode, 6);

        assertEquals(lines(
                "     1    ",
                "  +------+",
                "a | ABC  | a",
                "  +------+",
                "     1    "
        ), renderer.render(board));
    }

    @Test
    void acceptsFixedWidthEqualToRepresentationWidth() {
        StandardBoard<TestPiece> board = new StandardBoard<>(1, 1);
        board.place(new Position(0, 0), TestPiece.ODD);

        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(TestPiece::getCode, 3);

        assertEquals(lines(
                "    1  ",
                "  +---+",
                "a |ABC| a",
                "  +---+",
                "    1  "
        ), renderer.render(board));
    }

    @Test
    void rejectsFixedWidthTooSmallForPieceRepresentation() {
        StandardBoard<TestPiece> board = new StandardBoard<>(1, 1);
        board.place(new Position(0, 0), TestPiece.LONG);

        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(TestPiece::getCode, 3);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> renderer.render(board));

        assertEquals("Cell width 3 is too small. Required width: 4", exception.getMessage());
    }

    @Test
    void rejectsFixedWidthTooSmallForNumericLabels() {
        StandardBoard<TestPiece> board = new StandardBoard<>(10, 1);
        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(TestPiece::getCode, 1);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> renderer.render(board));

        assertEquals("Cell width 1 is too small. Required width: 2", exception.getMessage());
    }

    @Test
    void appliesRepresentationFunctionOncePerOccupiedCell() {
        StandardBoard<TestPiece> board = new StandardBoard<>(3, 2);
        board.place(new Position(0, 0), TestPiece.SHORT);
        board.place(new Position(2, 1), TestPiece.SHORT);

        AtomicInteger calls = new AtomicInteger();

        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(piece -> {
            calls.incrementAndGet();
            return piece.getCode();
        });

        renderer.render(board);

        assertEquals(2, calls.get());
    }

    @Test
    void doesNotApplyRepresentationFunctionToEmptyCells() {
        StandardBoard<TestPiece> board = new StandardBoard<>(2, 2);
        AtomicInteger calls = new AtomicInteger();

        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(piece -> {
            calls.incrementAndGet();
            return piece.getCode();
        });

        renderer.render(board);

        assertEquals(0, calls.get());
    }

    @Test
    void usesSuppliedRepresentationFunction() {
        StandardBoard<TestPiece> board = new StandardBoard<>(1, 1);
        board.place(new Position(0, 0), TestPiece.SHORT);

        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(piece -> "[" + piece.getCode() + "]");

        assertEquals(lines(
                "     1   ",
                "  +-----+",
                "a | [X] | a",
                "  +-----+",
                "     1   "
        ), renderer.render(board));
    }

    @Test
    void acceptsEmptyPieceRepresentation() {
        StandardBoard<TestPiece> board = new StandardBoard<>(1, 1);
        board.place(new Position(0, 0), TestPiece.SHORT);

        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(piece -> "");

        assertEquals(lines(
                "    1  ",
                "  +---+",
                "a |   | a",
                "  +---+",
                "    1  "
        ), renderer.render(board));
    }

    @Test
    void reflectsBoardChangesWithoutModifyingItsContents() {
        StandardBoard<TestPiece> board = new StandardBoard<>(1, 1);
        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(TestPiece::getCode);

        String emptyImage = renderer.render(board);

        board.place(new Position(0, 0), TestPiece.LONG);
        var state = board.getState();

        assertTrue(renderer.render(board).contains("| LONG |"));
        assertEquals(state, board.getState());

        board.clear();

        assertEquals(emptyImage, renderer.render(board));
    }

    @Test
    void acceptsTwentySixColumns() {
        StandardBoard<TestPiece> board = new StandardBoard<>(1, 26);
        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(TestPiece::getCode);

        String[] output = renderer.render(board).split(System.lineSeparator());

        assertEquals("z |   | z", output[2]);
        assertEquals("a |   | a", output[output.length - 3]);
    }

    @Test
    void rejectsMoreThanTwentySixColumns() {
        StandardBoard<TestPiece> board = new StandardBoard<>(1, 27);
        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(TestPiece::getCode);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> renderer.render(board));

        assertEquals("Number of columns must not exceed 26: 27", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, Integer.MIN_VALUE})
    void rejectsNonPositiveFixedWidth(int width) {
        assertThrows(IllegalArgumentException.class,
                () -> new ConsoleBoardRenderer<>(TestPiece::getCode, width));
    }

    @Test
    void rejectsNullRepresentationFunctionForAutomaticWidth() {
        assertThrows(NullPointerException.class, () -> new ConsoleBoardRenderer<TestPiece>(null));
    }

    @Test
    void rejectsNullRepresentationFunctionForFixedWidth() {
        assertThrows(NullPointerException.class, () -> new ConsoleBoardRenderer<TestPiece>(null, 3));
    }

    @Test
    void rejectsNullBoard() {
        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(TestPiece::getCode);

        assertThrows(NullPointerException.class, () -> renderer.render(null));
    }

    @Test
    void rejectsNullPieceRepresentation() {
        StandardBoard<TestPiece> board = new StandardBoard<>(1, 1);
        board.place(new Position(0, 0), TestPiece.SHORT);

        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(piece -> null);

        assertThrows(NullPointerException.class, () -> renderer.render(board));
    }

    @ParameterizedTest
    @ValueSource(strings = {"\n", "\r", "\t", "X\nY", "\033[31mX"})
    void rejectsControlCharactersInPieceRepresentation(String text) {
        StandardBoard<TestPiece> board = new StandardBoard<>(1, 1);
        board.place(new Position(0, 0), TestPiece.SHORT);

        ConsoleBoardRenderer<TestPiece> renderer = new ConsoleBoardRenderer<>(piece -> text);

        assertThrows(IllegalArgumentException.class, () -> renderer.render(board));
    }

    private static String lines(String... lines) {
        return String.join(System.lineSeparator(), lines);
    }
}
