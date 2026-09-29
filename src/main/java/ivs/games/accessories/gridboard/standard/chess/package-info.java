/**
 * Provides a typed chess board, extensible piece contracts and twelve standard type-and-color values.
 *
 * <p>{@link ivs.games.accessories.gridboard.standard.chess.ChessBoard} accepts dimensions and starts empty.
 * Its copy constructor copies another chess board while sharing immutable piece values.
 * Games choose piece quantities, initial placement and rules.</p>
 *
 * <p>{@link ivs.games.accessories.gridboard.standard.chess.ChessType} provides codes
 * {@code P}, {@code N}, {@code B}, {@code R}, {@code Q} and {@code K}.
 * {@link ivs.games.accessories.gridboard.standard.chess.StandardPiece} combines the color code
 * with the type code, such as {@code WP}, and provides {@code fromCode}.
 * It also exposes Unicode symbols through {@code getSymbol} and {@code fromSymbol}.
 * These symbols are properties of the enum, not requirements of the piece contracts.</p>
 *
 * <p>The same enum value can occupy multiple cells; codes do not identify individual pieces.
 * Custom types and pieces may implement
 * {@link ivs.games.accessories.gridboard.standard.chess.ChessPieceType} and
 * {@link ivs.games.accessories.gridboard.standard.chess.ChessPiece}, respectively.
 * Cell addresses and piece codes do not implement chess move notation.</p>
 */
package ivs.games.accessories.gridboard.standard.chess;
