/**
 * Provides text rendering of rectangular boards using application-supplied piece representations.
 *
 * <p>{@link ivs.games.accessories.gridboard.render.ConsoleBoardRenderer} returns a string with
 * centered text, borders and coordinate labels on all four sides. Empty cells contain spaces.
 * Automatic width includes two padding spaces; fixed width excludes borders and must fit the text and labels.</p>
 *
 * <p>Lettered columns run from the last column at the top to {@code a} at the bottom.
 * Numeric rows increase from left to right. Rendering does not change board coordinates or contents.
 * At most 26 lettered columns are supported.</p>
 *
 * <p>Each occupied cell is converted once to non-null text without control characters.
 * Width counts Unicode code points, assuming one terminal position per code point.
 * Aligned output requires a monospaced font and suitable text; the standard demos use piece codes.
 * There is no dedicated Unicode piece renderer. Output uses the system line separator without a trailing separator.</p>
 */
package ivs.games.accessories.gridboard.render;
