package ivs.games.accessories.gridboard.standard;

import ivs.games.accessories.gridboard.Piece;

/**
 * A piece with a type and a color.
 *
 * <p>Implementations must be immutable, as required by {@link Piece}.</p>
 *
 * @param <T> the type used to classify pieces in a particular set
 */
public interface ColoredPiece<T> extends Piece {

    /**
     * Returns the type of this piece.
     *
     * @return the piece type
     */
    T getType();

    /**
     * Returns the color of this piece.
     *
     * @return the piece color
     */
    PieceColor getColor();
}
