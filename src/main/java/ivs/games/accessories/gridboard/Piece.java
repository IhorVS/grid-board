package ivs.games.accessories.gridboard;

/**
 * A piece that can be placed on a grid board.
 *
 * <p>Implementations must be immutable. All properties of a piece must remain
 * unchanged after its creation, including properties of objects referenced by
 * the piece.</p>
 *
 * <p>Movement and interaction rules are defined by the game, not by the piece.</p>
 */
public interface Piece {
    /**
     * Returns a stable text code for this piece.
     *
     * <p>The code is chosen by the game. It need not be unique across different
     * games or represent an individual piece on the board.</p>
     *
     * @return the piece code
     */
    String getCode();
}
