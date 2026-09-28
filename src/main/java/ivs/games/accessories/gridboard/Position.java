package ivs.games.accessories.gridboard;

 /**
 * The position of a cell on a grid board.
 *
 * <pre>
 *                 column
 *              0   1   2
 *            +---+---+---+
 * row 0      |   |   |   |
 *            +---+---+---+
 * row 1      |   |   |   |
 *            +---+---+---+
 * row 2      |   |   |   |
 *            +---+---+---+
 * </pre>
 *
 * <p>Rows increase from top to bottom; columns increase from left to right.
 * Both indexes start at zero.</p>
 *
 * @param row the row index
 * @param column the column index
 */
public record Position(int row, int column) {
}
