package ivs.games.accessories.gridboard.standard.chess;

import ivs.games.accessories.gridboard.StandardBoard;

/**
 * A rectangular board for chess pieces.
 *
 * <p>The board starts empty. Dimensions and piece placement are chosen by
 * the game.</p>
 *
 * @param <P> the type of chess pieces stored on this board
 */
public class ChessBoard<P extends ChessPiece> extends StandardBoard<P> {

    /**
     * Creates an empty board with the given dimensions.
     *
     * @param rows the number of rows
     * @param columns the number of columns
     * @throws IllegalArgumentException if either dimension is less than one
     */
    public ChessBoard(int rows, int columns) {
        super(rows, columns);
    }

    /**
     * Creates an independent copy of another chess board.
     *
     * @param source the board to copy
     * @throws NullPointerException if {@code source} is {@code null}
     */
    public ChessBoard(ChessBoard<P> source) {
        super(source);
    }
}

