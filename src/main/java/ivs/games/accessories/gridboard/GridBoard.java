package ivs.games.accessories.gridboard;

import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * A rectangular grid that holds at most one piece in each cell.
 *
 * <pre>
 *                       column
 *                  0    1    ...   7
 *                +----+----+-----+----+
 *     row 0      | a1 | b1 | ... | h1 |
 *                +----+----+-----+----+
 *     row 1      | a2 | b2 | ... | h2 |
 *                +----+----+-----+----+
 *       ...      | ...  ...  ...  ... |
 *                +----+----+-----+----+
 *     row 7      | a8 | b8 | ... | h8 |
 *                +----+----+-----+----+
 * </pre>
 *
 * <p>Rows increase from top to bottom; columns increase from left to right.
 * Thus, {@code a1} corresponds to {@code Position(0, 0)}, and {@code h8}
 * corresponds to {@code Position(7, 7)}. The board has no player orientation.
 * The game determines which direction is forward for each player and how
 * the board is displayed.</p>
 *
 * <p>Cell coordinates are represented by {@link Position}. The board stores
 * pieces but does not enforce game rules.</p>
 *
 * @param <P> the type of pieces stored on this board
 */
public interface GridBoard<P extends Piece> {
    /**
     * Returns the number of rows.
     */
    int getRows();

    /**
     * Returns the number of columns.
     */
    int getColumns();

    /**
     * Returns whether the cell is empty.
     *
     * @throws IllegalArgumentException if the position is outside the board
     */
    boolean isEmptyCell(Position position);

    /**
     * Returns the piece at the position, or {@code null} if the cell is empty.
     *
     * @throws IllegalArgumentException if the position is outside the board
     */
    P get(Position position);

    /**
     * Places a piece in an empty cell.
     *
     * @throws NullPointerException     if the piece or position is {@code null}
     * @throws IllegalArgumentException if the position is outside the board
     * @throws IllegalStateException    if the cell is occupied
     */
    void place(Position position, P piece);

    /**
     * Removes and returns the piece at the position.
     *
     * @throws IllegalArgumentException if the position is outside the board
     * @throws IllegalStateException    if the cell is empty
     */
    P remove(Position position);

    /**
     * Validates that the position refers to a cell on this board.
     *
     * @param position the position to validate
     * @throws NullPointerException     if the position is {@code null}
     * @throws IllegalArgumentException if the row or column is outside the board
     */
    default void validatePosition(Position position) {
        Validate.notNull(position, "Position must not be null");

        Validate.isTrue(position.row() >= 0 && position.row() < getRows(),
                "Row %d is outside the board. Valid rows: 0 to %d",
                position.row(), getRows() - 1);

        Validate.isTrue(position.column() >= 0 && position.column() < getColumns(),
                "Column %d is outside the board. Valid columns: 0 to %d",
                position.column(), getColumns() - 1);
    }

    /**
     * Returns whether the board contains no pieces.
     *
     * @return {@code true} if every cell is empty
     */
    boolean isEmpty();

    /**
     * Performs an action for each occupied cell, in row-major order.
     * Empty cells are skipped.
     *
     * @param action the action to perform with the position and piece
     * @throws NullPointerException if {@code action} is {@code null}
     */
    void forEachPiece(BiConsumer<Position, ? super P> action);

    /**
     * Returns a snapshot of the occupied cells and their pieces.
     *
     * <p>The returned map is unmodifiable and does not change when the board
     * changes. Piece objects are shared with the board and are immutable
     * according to the {@link Piece} contract.</p>
     *
     * @return an unmodifiable map from positions to pieces
     */
    Map<Position, P> getState();

    /**
     * Replaces the entire board contents with the given state.
     *
     * <p>Positions must belong to this board, and pieces must not be {@code null}.
     * The entire state is validated before the board is changed. Later changes
     * to the supplied map do not affect the board.</p>
     *
     * @param state the new contents of the board; an empty map clears it
     * @throws NullPointerException if {@code state}, a position, or a piece is {@code null}
     * @throws IllegalArgumentException if a position is outside the board
     */
    void setState(Map<Position, P> state);

    /**
     * Removes all pieces from the board without changing its dimensions.
     */
    void clear();
}
