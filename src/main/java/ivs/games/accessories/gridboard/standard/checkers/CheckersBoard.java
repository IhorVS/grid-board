package ivs.games.accessories.gridboard.standard.checkers;

import ivs.games.accessories.gridboard.StandardBoard;
import lombok.NonNull;

/**
 * A rectangular board for checkers pieces.
 *
 * <p>The board starts empty. Dimensions and piece placement are chosen by
 * the game.</p>
 *
 * @param <P> the type of checkers pieces stored on this board
 */
public class CheckersBoard<P extends CheckersPiece> extends StandardBoard<P> {

    /**
     * Creates an empty board with the given dimensions.
     *
     * @param rows    the number of rows
     * @param columns the number of columns
     * @throws IllegalArgumentException if either dimension is less than one
     */
    public CheckersBoard(int rows, int columns) {
        super(rows, columns);
    }

    /**
     * Creates an independent copy of another checkers board.
     *
     * @param source the board to copy
     * @throws NullPointerException if {@code source} is {@code null}
     */
    public CheckersBoard(@NonNull CheckersBoard<P> source) {
        super(source);
    }
}
