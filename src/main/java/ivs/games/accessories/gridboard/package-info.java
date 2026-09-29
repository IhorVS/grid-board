/**
 * Provides mutable rectangular boards, immutable piece contracts, positions and cell address conversion.
 *
 * <p>{@link ivs.games.accessories.gridboard.GridBoard} holds at most one piece per cell.
 * {@link ivs.games.accessories.gridboard.StandardBoard} uses a two-dimensional array.
 * Boards support occupied-cell traversal, state snapshots, state replacement and clearing.</p>
 *
 * <p>Coordinates are zero-based: rows increase downward and columns to the right.
 * Cell addresses use lowercase letters for columns and one-based numbers for rows;
 * {@code a1} is {@code Position(0, 0)}. Cell notation supports columns {@code a} through {@code z}
 * without checking board dimensions. Position arithmetic also does not validate board boundaries.</p>
 *
 * <p>Snapshots and board copies share immutable pieces, but have independent cell contents.
 * Boards store pieces without applying movement, capture, promotion or player-orientation rules.</p>
 */
package ivs.games.accessories.gridboard;
