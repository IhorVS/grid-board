/**
 * Provides board reading and modification contracts, immutable pieces, positions and cell address conversion.
 *
 * <p>{@link ivs.games.accessories.gridboard.GridBoardView} provides reading operations, position validation,
 * occupied-cell traversal and state snapshots. {@link ivs.games.accessories.gridboard.GridBoard}
 * extends that contract with placement, removal, state replacement and clearing.
 * {@link ivs.games.accessories.gridboard.standard.StandardBoard} implements both contracts
 * using a two-dimensional array.</p>
 *
 * <p>A view reference exposes no modification methods but is not an immutable snapshot or an unmodifiable wrapper.
 * The underlying board may change through another reference. Its implementation may also expose modification methods.
 * The coordinate diagram and conventions are documented in {@link ivs.games.accessories.gridboard.GridBoardView}.</p>
 *
 * <p>Coordinates are zero-based: rows increase downward and columns to the right.
 * Cell addresses use lowercase letters for columns and one-based numbers for rows;
 * {@code a1} is {@code Position(0, 0)}. Cell notation supports columns {@code a} through {@code z}
 * without checking board dimensions. Position arithmetic also does not validate board boundaries.</p>
 *
 * <p>State snapshots are unmodifiable maps of occupied positions to pieces.
 * StandardBoard can copy a GridBoardView into an independent mutable board with the same dimensions and contents.
 * Snapshots and board copies share immutable piece objects. Subclasses copy additional properties themselves.</p>
 *
 * <p>Boards store pieces without applying movement, capture, promotion or player-orientation rules.</p>
 */
package ivs.games.accessories.gridboard;
