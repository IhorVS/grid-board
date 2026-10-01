/**
 * Provides shared color and typed-piece contracts for standard chess and checkers sets.
 *
 * <p>{@link ivs.games.accessories.gridboard.standard.ColoredPiece} combines an immutable piece
 * with its type and color. {@link ivs.games.accessories.gridboard.standard.PieceColor}
 * provides {@code WHITE} and {@code BLACK}, with case-sensitive codes {@code W} and {@code B}
 * and parsing through {@code fromCode}. The {@code getOpposite} method returns the other color.</p>
 *
 * <p>These types do not define movement directions, initial arrangements or game rules.</p>
 */
package ivs.games.accessories.gridboard.standard;
