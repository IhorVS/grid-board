/**
 * Provides the standard grid board implementation and shared contracts for colored pieces.
 *
 * <p>{@link ivs.games.accessories.gridboard.standard.StandardBoard} implements
 * {@link ivs.games.accessories.gridboard.GridBoard} using a two-dimensional array.
 * It accepts dimensions or copies a {@link ivs.games.accessories.gridboard.GridBoardView},
 * sharing immutable pieces while keeping cell contents independent.
 * The chess and checkers subpackages provide specialized boards and standard piece sets.</p>
 *
 * <p>{@link ivs.games.accessories.gridboard.standard.ColoredPiece} combines an immutable piece
 * with its type and color. {@link ivs.games.accessories.gridboard.standard.PieceColor}
 * provides {@code WHITE} and {@code BLACK}, with case-sensitive codes {@code W} and {@code B}
 * and parsing through {@code fromCode}. The {@code getOpposite} method returns the other color.</p>
 *
 * <p>These types do not define movement directions, initial arrangements or game rules.</p>
 */
package ivs.games.accessories.gridboard.standard;
