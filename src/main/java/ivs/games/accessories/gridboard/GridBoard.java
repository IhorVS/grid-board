package ivs.games.accessories.gridboard;

import org.apache.commons.lang3.Validate;

import java.util.function.BiConsumer;

/**
 * A rectangular grid that holds at most one piece in each cell.
 *
 * <p>Cell coordinates are described by {@link Position}. The board stores
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
}
