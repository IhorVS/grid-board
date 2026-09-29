package ivs.games.accessories.gridboard.standard.checkers;

/**
 * A type of checkers piece.
 *
 * <p>Games may define additional piece types by implementing this interface.</p>
 */
public interface CheckersPieceType {
    /**
     * Returns the short code of this checkers piece type.
     *
     * @return the type code
     */
    String getCode();
}
