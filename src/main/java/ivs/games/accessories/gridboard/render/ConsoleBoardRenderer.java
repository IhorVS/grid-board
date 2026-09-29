package ivs.games.accessories.gridboard.render;

import ivs.games.accessories.gridboard.GridBoard;
import ivs.games.accessories.gridboard.Piece;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.function.Function;

/**
 * Renders a board as text with centered piece representations.
 *
 * <p>The bottom line contains cells {@code a1, a2, ...}. Lettered columns
 * are displayed from the last column at the top to column {@code a}
 * at the bottom. Numeric labels increase from left to right.
 * Labels are displayed on all four sides.</p>
 *
 * <p>Empty cells contain spaces. Correct alignment requires a monospaced
 * font and text whose code points each occupy one terminal position.</p>
 *
 * @param <P> the type of pieces on the board
 */
public class ConsoleBoardRenderer<P extends Piece> {

    private static final String COLUMN_LABELS = "abcdefghijklmnopqrstuvwxyz";
    private static final int AUTOMATIC_WIDTH = 0;
    private static final int HORIZONTAL_PADDING = 2;

    private final Function<? super P, String> textOf;
    private final int cellWidth;

    /**
     * Creates a renderer that determines the cell width automatically.
     *
     * <p>The width accommodates the longest piece representation or numeric
     * label, with one additional space on each side.</p>
     *
     * @param textOf the function providing piece representations
     * @throws NullPointerException if {@code textOf} is {@code null}
     */
    public ConsoleBoardRenderer(Function<? super P, String> textOf) {
        this.textOf = Validate.notNull(textOf, "Text function must not be null");
        this.cellWidth = AUTOMATIC_WIDTH;
    }

    /**
     * Creates a renderer with a fixed cell width.
     *
     * @param textOf the function providing piece representations
     * @param cellWidth the full width of each cell, excluding its borders
     * @throws NullPointerException if {@code textOf} is {@code null}
     * @throws IllegalArgumentException if {@code cellWidth} is not positive
     */
    public ConsoleBoardRenderer(Function<? super P, String> textOf, int cellWidth) {
        this.textOf = Validate.notNull(textOf, "Text function must not be null");
        Validate.isTrue(cellWidth > 0, "Cell width must be positive: %d", cellWidth);
        this.cellWidth = cellWidth;
    }

    /**
     * Returns a text representation of the current board contents.
     *
     * <p>The representation function is called once for each occupied cell.
     * Its result must be a non-null string containing no control characters.</p>
     *
     * @param board the board to render
     * @return the rendered board, without a trailing line separator
     * @throws NullPointerException if {@code board} or a piece representation is {@code null}
     * @throws IllegalArgumentException if there are more than 26 columns,
     *                                  a representation contains control characters,
     *                                  or the fixed width cannot accommodate the text or labels
     */
    public String render(GridBoard<P> board) {
        Validate.notNull(board, "Board must not be null");
        Validate.isTrue(board.getColumns() <= COLUMN_LABELS.length(),
                "Number of columns must not exceed %d: %d",
                COLUMN_LABELS.length(), board.getColumns());

        String[][] cells = new String[board.getColumns()][board.getRows()];

        for (String[] line : cells) {
            Arrays.fill(line, "");
        }

        board.forEachPiece((position, piece) -> {
            String text = Validate.notNull(textOf.apply(piece),
                    "Piece representation must not be null");

            Validate.isTrue(text.codePoints().noneMatch(Character::isISOControl),
                    "Piece representation must not contain control characters");

            cells[position.column()][position.row()] = text;
        });

        int width = determineCellWidth(cells, board.getRows());
        StringBuilder result = new StringBuilder();
        String separator = System.lineSeparator();

        appendLabels(result, board.getRows(), width);
        result.append(separator);
        appendBorder(result, board.getRows(), width);
        result.append(separator);

        for (int column = board.getColumns() - 1; column >= 0; column--) {
            char label = COLUMN_LABELS.charAt(column);
            result.append(label).append(" |");

            for (String text : cells[column]) {
                result.append(center(text, width)).append('|');
            }

            result.append(' ').append(label).append(separator);
            appendBorder(result, board.getRows(), width);
            result.append(separator);
        }

        appendLabels(result, board.getRows(), width);
        return result.toString();
    }

    private int determineCellWidth(String[][] cells, int rows) {
        int requiredWidth = textWidth(Integer.toString(rows));

        for (String[] line : cells) {
            for (String text : line) {
                requiredWidth = Math.max(requiredWidth, textWidth(text));
            }
        }

        if (cellWidth == AUTOMATIC_WIDTH) {
            return requiredWidth + HORIZONTAL_PADDING;
        }

        Validate.isTrue(cellWidth >= requiredWidth,
                "Cell width %d is too small. Required width: %d",
                cellWidth, requiredWidth);

        return cellWidth;
    }

    private void appendLabels(StringBuilder result, int rows, int width) {
        result.append("   ");

        for (int row = 0; row < rows; row++) {
            result.append(center(Integer.toString(row + 1), width));
            result.append(' ');
        }
    }

    private void appendBorder(StringBuilder result, int rows, int width) {
        result.append("  +");

        String segment = "-".repeat(width) + "+";

        result.repeat(segment, Math.max(0, rows));
    }

    private String center(String text, int width) {
        int padding = width - textWidth(text);
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;

        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }

    private int textWidth(String text) {
        return text.codePointCount(0, text.length());
    }
}
