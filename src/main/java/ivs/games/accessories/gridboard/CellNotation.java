package ivs.games.accessories.gridboard;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.Validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converts between grid positions and letter-and-number cell addresses.
 *
 * <p>Columns are named {@code a} through {@code z} from left to right.
 * Row numbers start at {@code 1} at the top and increase downward.
 * For example, {@code a1} is {@code Position(0, 0)} and {@code h8}
 * is {@code Position(7, 7)}.</p>
 *
 * <p>This class does not check whether a position belongs to a particular
 * board. Use {@link GridBoard#validatePosition(Position)} for that.</p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CellNotation {

    private static final Pattern ADDRESS_PATTERN = Pattern.compile("([a-z])([1-9][0-9]*)");
    private static final int COLUMN_COUNT = 26;

    /**
     * Converts a cell address to a position.
     *
     * @param address the address, such as {@code e2}
     * @return the corresponding position
     * @throws NullPointerException if {@code address} is {@code null}
     * @throws IllegalArgumentException if the address has an invalid format
     *         or its row number exceeds the supported integer range
     */
    public static Position parse(@NonNull String address) {
        Matcher matcher = ADDRESS_PATTERN.matcher(address);
        Validate.isTrue(matcher.matches(), "Invalid cell address: %s", address);

        try {
            int row = Integer.parseInt(matcher.group(2)) - 1;
            int column = matcher.group(1).charAt(0) - 'a';
            return new Position(row, column);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Row number is too large: " + address, exception);
        }
    }

    /**
     * Converts a position to a cell address.
     *
     * @param position the position to convert
     * @return the address, such as {@code e2}
     * @throws NullPointerException if {@code position} is {@code null}
     * @throws IllegalArgumentException if the row is negative or too large,
     *         or the column is outside the range {@code 0} to {@code 25}
     */
    public static String format(@NonNull Position position) {
        Validate.isTrue(position.row() >= 0,
                "Row must not be negative: %d", position.row());
        Validate.isTrue(position.row() != Integer.MAX_VALUE,
                "Row is too large for cell notation: %d", position.row());
        Validate.isTrue(position.column() >= 0 && position.column() < COLUMN_COUNT,
                "Column cannot be represented in cell notation: %d", position.column());

        char column = (char) ('a' + position.column());
        return column + Integer.toString(position.row() + 1);
    }
}
