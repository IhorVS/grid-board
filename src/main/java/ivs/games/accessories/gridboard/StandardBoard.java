package ivs.games.accessories.gridboard;

import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

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
     * @param rows    the number of rows
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

    /**
     * Creates a board with the same dimensions and piece positions as the source.
     *
     * @param source the board to copy
     * @throws NullPointerException if {@code source} is {@code null}
     */
    public StandardBoard(@NonNull GridBoard<P> source) {
        this(source.getRows(), source.getColumns());
        setState(source.getState());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmptyCell(Position position) {
        validatePosition(position);
        return cells[position.row()][position.column()] == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public P get(Position position) {
        validatePosition(position);
        return (P) cells[position.row()][position.column()];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void place(Position position, P piece) {
        validatePosition(position);
        Validate.notNull(piece, "Piece must not be null");

        Validate.validState(cells[position.row()][position.column()] == null,
                "Cell is occupied: %s", position);

        cells[position.row()][position.column()] = piece;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public P remove(Position position) {
        P piece = get(position);

        Validate.validState(piece != null, "Cell is empty: %s", position);

        cells[position.row()][position.column()] = null;
        return piece;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        for (Object[] row : cells) {
            for (Object piece : row) {
                if (piece != null) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void forEachPiece(BiConsumer<Position, ? super P> action) {
        Validate.notNull(action, "Action must not be null");

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Object piece = cells[row][column];

                if (piece != null) {
                    action.accept(new Position(row, column), (P) piece);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Position, P> getState() {
        Map<Position, P> state = new HashMap<>();
        forEachPiece(state::put);
        return Map.copyOf(state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setState(Map<Position, P> state) {
        Validate.notNull(state, "State must not be null");

        for (Map.Entry<Position, P> entry : state.entrySet()) {
            validatePosition(entry.getKey());
            Validate.notNull(entry.getValue(), "State must not contain null pieces");
        }

        clear();

        for (Map.Entry<Position, P> entry : state.entrySet()) {
            Position position = entry.getKey();
            cells[position.row()][position.column()] = entry.getValue();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        Arrays.stream(cells).forEach(row -> Arrays.fill(row, null));
    }
}
