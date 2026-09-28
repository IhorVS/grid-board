package ivs.games.accessories.gridboard;

import lombok.NonNull;

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
     /**
      * Returns a new position shifted by the given number of rows and columns.
      * A negative row delta moves up; a negative column delta moves left.
      *
      * <p>The resulting position is not checked against board boundaries.</p>
      *
      * @param rowDelta the change in row
      * @param columnDelta the change in column
      * @return the shifted position
      */
     public Position translate(int rowDelta, int columnDelta) {
         return new Position(row + rowDelta, column + columnDelta);
     }

     /**
      * Returns the signed row difference from this position to another position.
      *
      * @param other the destination position
      * @return {@code other.row() - row()}
      * @throws NullPointerException if {@code other} is {@code null}
      */
     public int rowDeltaTo(@NonNull Position other) {
         return other.row() - row;
     }

     /**
      * Returns the signed column difference from this position to another position.
      *
      * @param other the destination position
      * @return {@code other.column() - column()}
      * @throws NullPointerException if {@code other} is {@code null}
      */
     public int columnDeltaTo(@NonNull Position other) {
         return other.column() - column;
     }
}
