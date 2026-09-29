# Grid Board

A Java library for rectangular boards that hold immutable pieces. It provides
a general board API, standard chess and checkers piece sets, and a console renderer. The library
stores pieces but does not define moves, captures, starting positions, or game
rules.

Requires Java 21.

## Coordinates

A position is `Position(row, column)`. Both indexes start at zero. Rows
increase from top to bottom; columns increase from left to right.

```text
                       column
                  0    1    ...   7
                +----+----+-----+----+
     row 0      | a1 | b1 | ... | h1 |
                +----+----+-----+----+
     row 1      | a2 | b2 | ... | h2 |
                +----+----+-----+----+
       ...      | ...  ...  ...  ... |
                +----+----+-----+----+
     row 7      | a8 | b8 | ... | h8 |
                +----+----+-----+----+
```

For example, `a1` is `Position(0, 0)`, `e2` is `Position(1, 4)`, and
`h8` is `Position(7, 7)`. This is the library's coordinate convention.
The board has no player orientation. A game decides which direction is
forward for each player and how to display the board.

`CellNotation` converts between addresses and positions:

```java
Position position = CellNotation.parse("e2"); // Position(1, 4)
String address = CellNotation.format(position); // "e2"
```

The utility accepts lowercase columns `a` through `z` and row numbers from
`1` through `Integer.MAX_VALUE`. It rejects whitespace, leading zeroes and
uppercase letters. It does not check board boundaries. Board operations
validate positions against the board's dimensions. The board itself is not
limited to 26 columns; this restriction belongs to cell notation and the renderer.

`Position.translate(rowDelta, columnDelta)` creates a shifted position.
`rowDeltaTo(other)` and `columnDeltaTo(other)` return signed differences to
another position. These operations do not validate board boundaries.

## Using a board

```java
GridBoard<StandardPiece> board = new ChessBoard<>(8, 8);

Position kingPosition = CellNotation.parse("a5");
board.place(kingPosition, StandardPiece.WHITE_KING);

StandardPiece king = board.get(kingPosition);
board.remove(kingPosition);
```

This example uses
`ivs.games.accessories.gridboard.standard.chess.StandardPiece`.

Each cell holds at most one piece. `place` requires an empty cell, and `remove`
requires an occupied cell. `get` returns `null` for an empty cell.
`isEmptyCell` checks one cell, and `isEmpty` checks the entire board.
The board does not interpret these operations as game moves.

`forEachPiece((position, piece) -> ...)` visits occupied cells in row-major
order, skipping empty cells. `StandardBoard` stores cells in a two-dimensional
array. Games can supply other implementations of `GridBoard`.

## Chess and checkers sets

The `standard.chess` package provides `ChessBoard`, `ChessPiece`, `ChessPieceType`,
`ChessType`, and `StandardPiece`. The `standard.checkers` package provides the
corresponding checkers types: `CheckersBoard`, `CheckersPiece`,
`CheckersPieceType`, `CheckerType`, and `StandardPiece`.

Both boards accept dimensions in their constructors and start empty. A game
chooses the number and placement of pieces. Standard pieces are enum values,
so the same value can be placed in multiple cells. Games can also implement
`ChessPiece` or `CheckersPiece` to define additional immutable pieces.

Standard types, colors, and pieces have short, case-sensitive codes. A piece
code combines its color and type: `WP` is a white chess pawn, `BK` is a black
chess king, `WM` is an ordinary white checker, and `WK` is a white checkers
king. The standard enums provide `getCode()` and `fromCode()`. These codes
identify type-and-color combinations, not individual instances; they do not
define a notation for moves. Codes are interpreted within a particular piece
set: for example, `WK` exists in both sets.

| Set      | Type codes                                                      |
| -------- | --------------------------------------------------------------- |
| Chess    | `P` pawn, `N` knight, `B` bishop, `R` rook, `Q` queen, `K` king |
| Checkers | `M` man, `K` king                                               |
| Colors   | `W` white, `B` black                                            |

The chess enum has twelve values; the checkers enum has four. Neither set
tracks quantities or assigns identities to individual pieces. Boards do not
restrict placement to cells of a particular color or enforce promotion.

Both `StandardPiece` enums also expose `getSymbol()` and `fromSymbol(char)`
for their Unicode symbols. This functionality belongs to the concrete enums,
not to `Piece` or the piece-type interfaces. There is no dedicated Unicode
console renderer; the demos use text codes to avoid font-dependent alignment.

## Board state

`getState()` returns an unmodifiable snapshot mapping occupied positions to
pieces. `setState(state)` replaces the board's contents after validating the
entire map. `clear()` removes all pieces. A copy constructor creates another
board with the same dimensions and piece positions. `StandardBoard` accepts a
`GridBoard<P>` source; `ChessBoard` and `CheckersBoard` accept a source of their
respective board class. Copying does not change the source, and subsequent
changes to either board do not affect the other board's cells.

Snapshots and copies share references to piece objects. Implementations of
`Piece` must therefore be immutable.

## Console rendering

`ConsoleBoardRenderer<P>` accepts a function converting a piece to text and
returns a string. It does not print or modify the board.

```java
ConsoleBoardRenderer<StandardPiece> automatic = new ConsoleBoardRenderer<>(StandardPiece::getCode);
System.out.println(automatic.render(board));

ConsoleBoardRenderer<StandardPiece> fixed = new ConsoleBoardRenderer<>(StandardPiece::getCode, 7);
System.out.println(fixed.render(board));
```

Automatic width is the longest piece representation or numeric label plus
two spaces. Fixed width is the full cell width excluding borders; it must be
positive and large enough for all text and numeric labels. Text is centered,
with an extra space on the right when padding is odd. Empty cells contain spaces.
The conversion function runs once per occupied cell and must return non-null
text without control characters. An empty string is allowed.

The renderer displays lettered columns vertically, in descending order, and
numeric rows horizontally, in ascending order. Thus the bottom line is
`a1 ... a8` and the top line is `h1 ... h8` on an 8-by-8 board. Labels appear
on all four sides. This presentation does not change `Position` coordinates.

For a 2-by-2 board with a white king at `a1` and a black king at `b2`:

```text
     1    2
  +----+----+
b |    | BK | b
  +----+----+
a | WK |    | a
  +----+----+
     1    2
```

Rendering supports at most 26 lettered columns. Width is counted in Unicode
code points, assuming one terminal position per code point. Use a monospaced
font and ordinary text codes for aligned output. Wide or combining characters
and terminal escape sequences are not supported. Output uses the system line
separator and has no trailing line separator.

## Demonstrations

The following classes are in `src/test/java/ivs/games/accessories/gridboard/demo`:

- `ChessRendererDemo`: an 8-by-8 board with the initial chess arrangement.
- `CheckersRendererDemo`: a 10-by-10 board with twenty men per side for
  international draughts, treating `a1` as a dark cell.

Run their `main` methods from the IDE with the test classpath. Both show
piece codes with automatic sizing and a fixed width of seven. They are not
included in the main library JAR.

The demos put white pieces at the `a` edge and black pieces at the opposite
edge. For example, the white chess king is at `a5` and the queen at `a4` in
this display. These are the library's cell addresses, not conventional chess
move notation. Initial arrangements exist only in demo code; constructors
always create empty boards.

## Build

```bash
mvn test
mvn install
```

The Maven coordinates for a locally installed copy are:

```xml
<dependency>
    <groupId>ivs.games.accessories</groupId>
    <artifactId>grid-board</artifactId>
    <version>1.0.0</version>
</dependency>
```

## License

See [LICENSE](LICENSE) for the project license.
