/**
 * Provides a typed checkers board, extensible piece contracts and four standard type-and-color values.
 *
 * <p>{@link ivs.games.accessories.gridboard.standard.checkers.CheckersBoard} accepts dimensions and starts empty.
 * Its copy constructor copies another checkers board while sharing immutable piece values.
 * Games choose piece quantities, initial placement and rules, including promotion and playable cell colors.</p>
 *
 * <p>{@link ivs.games.accessories.gridboard.standard.checkers.CheckerType} provides
 * {@code MAN} with code {@code M} and {@code KING} with code {@code K}.
 * {@link ivs.games.accessories.gridboard.standard.checkers.StandardPiece} combines the color code
 * with the type code, such as {@code WM}, and provides {@code fromCode}.
 * It also exposes Unicode symbols through {@code getSymbol} and {@code fromSymbol}.
 * These symbols are properties of the enum, not requirements of the piece contracts.</p>
 *
 * <p>The same enum value can occupy multiple cells; codes do not identify individual pieces.
 * Custom types and pieces may implement
 * {@link ivs.games.accessories.gridboard.standard.checkers.CheckersPieceType} and
 * {@link ivs.games.accessories.gridboard.standard.checkers.CheckersPiece}, respectively.
 * Piece codes do not define checkers move notation.</p>
 */
package ivs.games.accessories.gridboard.standard.checkers;
