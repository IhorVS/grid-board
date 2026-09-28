package ivs.games.accessories.gridboard;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

/**
 * A grid board backed by a two-dimensional array.
 *
 * @param <P> the type of pieces stored on this board
 */
public class StandardBoard<P extends Piece> implements GridBoard<P> {

    @Getter
    private final int rows;
    @Getter
    private final int columns;

    private final Object[][] cells;

    /**
     * Creates an empty board with the given dimensions.
     *
     * @param rows the number of rows
     * @param columns the number of columns
     * @throws IllegalArgumentException if either dimension is less than one
     */
    public StandardBoard(int rows, int columns) {
        Validate.isTrue(rows > 0, "Number of rows must be positive: %d", rows);
        Validate.isTrue(columns > 0, "Number of columns must be positive: %d", columns);

        this.rows = rows;
        this.columns = columns;
        this.cells = new Object[rows][columns];
    }

    @Override
    public boolean isEmptyCell(Position position) {
        validatePosition(position);
        return cells[position.row()][position.column()] == null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public P get(Position position) {
        validatePosition(position);
        return (P) cells[position.row()][position.column()];
    }

    @Override
    public void place(Position position, P piece) {
        validatePosition(position);
        Validate.notNull(piece, "Piece must not be null");

        Validate.validState(cells[position.row()][position.column()] == null,
                "Cell is occupied: %s", position);

        cells[position.row()][position.column()] = piece;
    }

    @Override
    public P remove(Position position) {
        P piece = get(position);

        Validate.validState(piece != null, "Cell is empty: %s", position);

        cells[position.row()][position.column()] = null;
        return piece;
    }
}
