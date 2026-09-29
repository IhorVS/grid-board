package ivs.games.accessories.gridboard.standard.chess;

/**
 * A type of chess piece.
 *
 * <p>Games may define additional piece types by implementing this interface.</p>
 */
public interface ChessPieceType {

    /**
     * Returns the short code of this chess piece type.
     *
     * @return the type code
     */
    String getCode();
}
