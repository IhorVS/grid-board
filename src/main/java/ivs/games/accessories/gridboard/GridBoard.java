package ivs.games.accessories.gridboard;

import java.util.Map;

/**
 * A mutable rectangular grid that holds at most one piece in each cell.
 *
 * <p>See {@link GridBoardView} for the coordinate diagram, coordinate conventions
 * and reading operations.</p>
 *
 * <p>This interface adds operations for changing the board contents
 * without enforcing game rules.</p>
 *
 * @param <P> the type of pieces stored on this board
 */
public interface GridBoard<P extends Piece> extends GridBoardView<P> {

    /**
     * Places a piece in an empty cell.
     *
     * @param position the position at which to place the piece
     * @param piece    the piece to place
     * @throws NullPointerException     if the piece or position is {@code null}
     * @throws IllegalArgumentException if the position is outside the board
     * @throws IllegalStateException    if the cell is occupied
     */
    void place(Position position, P piece);

    /**
     * Removes and returns the piece at the position.
     *
     * @param position the position from which to remove the piece
     * @return the removed piece
     * @throws NullPointerException     if {@code position} is {@code null}
     * @throws IllegalArgumentException if the position is outside the board
     * @throws IllegalStateException    if the cell is empty
     */
    P remove(Position position);

    /**
     * Replaces the entire board contents with the given state.
     *
     * <p>Positions must belong to this board, and pieces must not be {@code null}.
     * The entire state is validated before the board is changed. Later changes
     * to the supplied map do not affect the board.</p>
     *
     * @param state the new contents of the board; an empty map clears it
     * @throws NullPointerException     if {@code state}, a position, or a piece is {@code null}
     * @throws IllegalArgumentException if a position is outside the board
     */
    void setState(Map<Position, P> state);

    /**
     * Removes all pieces from the board without changing its dimensions.
     */
    void clear();
}
