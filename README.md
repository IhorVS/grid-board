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

## Reading and modifying a board

The board API separates access to reading and modification:

- `GridBoardView<P>` provides dimensions, cell reading, position validation,
  occupied-cell traversal and state snapshots.
- `GridBoard<P>` extends `GridBoardView<P>` and adds `place`, `remove`,
  `setState` and `clear`.
- `standard.StandardBoard<P>` implements `GridBoard<P>` using a two-dimensional array.

The root package `ivs.games.accessories.gridboard` contains the board contracts,
`Piece`, `Position` and `CellNotation`. The concrete `StandardBoard` implementation
is in `ivs.games.accessories.gridboard.standard`, alongside `ColoredPiece` and
`PieceColor`. Chess and checkers specializations are in its respective subpackages.

A `GridBoardView` reference exposes no modification methods. It is not an
immutable snapshot or an unmodifiable wrapper: the underlying board may change
through another reference. Giving a mutable implementation a view type does
not prevent a caller from casting it back to its mutable type.

```java
GridBoard<StandardPiece> board = new ChessBoard<>(8, 8);
board.place(CellNotation.parse("a5"), StandardPiece.WHITE_KING);

GridBoardView<StandardPiece> view = board;
StandardPiece king = view.get(CellNotation.parse("a5"));

board.clear();
boolean empty = view.isEmpty(); // true: the view observes the same board
```

Both `ChessBoard` and `CheckersBoard` inherit these contracts from
`StandardBoard`; dedicated chess or checkers view interfaces are not required.
Games can extend the view and mutable interfaces to expose additional properties
while reusing `StandardBoard` through inheritance.

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

The chess enum has twelve values; the checkers enum has four. The same value can
occupy multiple cells. Neither set tracks piece quantities or individual identities.

Both `StandardPiece` enums expose `getSymbol()` and `fromSymbol(char)` for their
Unicode symbols. These methods belong to the concrete enums, not to the shared
piece contracts. The console demonstrations use text codes.

## Board state

`getState()` returns an unmodifiable snapshot of occupied positions and pieces.
`setState(state)` replaces the board contents after validating the entire map;
`clear()` removes all pieces. Invalid state entries are rejected before the board
is changed. Subsequent changes to the supplied map do not affect the board.

`StandardBoard` accepts a `GridBoardView<P>` in its copy constructor. `ChessBoard`
and `CheckersBoard` accept a source of their respective board class. Copies preserve
dimensions and piece positions while keeping their cell contents independent.

```java
import ivs.games.accessories.gridboard.standard.StandardBoard;

GridBoardView<StandardPiece> view = board;
StandardBoard<StandardPiece> copy = new StandardBoard<>(view);
```

The view refers to the live source; `getState()` returns a fixed snapshot;
the copy is an independent mutable board. Subclasses with additional properties
must copy those properties in their own constructors. The map returned by
`getState()` contains occupied cells only, not dimensions or subclass properties.

Snapshots and copies share references to piece objects. Implementations of
`Piece` must therefore be immutable.

## Console rendering

`ConsoleBoardRenderer<P>` accepts a function converting a piece to text and
returns a string. Its `render` method accepts `GridBoardView<P>`, so it can
render a board exposed solely through its reading interface. It does not print
or modify the board.

```java
ConsoleBoardRenderer<StandardPiece> automatic = new ConsoleBoardRenderer<>(StandardPiece::getCode);
System.out.println(automatic.render(view));

ConsoleBoardRenderer<StandardPiece> fixed = new ConsoleBoardRenderer<>(StandardPiece::getCode, 7);
System.out.println(fixed.render(view));
```

Automatic width is the longest piece representation or numeric label plus
two spaces. Fixed width is the full cell width excluding borders; it must be
positive and large enough for all text and numeric labels. Text is centered,
with an extra space on the right when padding is odd. Empty cells contain spaces.
The conversion function runs once per occupied cell and must return non-null
text without control characters. An empty string is allowed.

The renderer displays lettered columns vertically, in descending order, and
numeric rows horizontally, in ascending order. Thus, the bottom line is
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

Run their `main` methods from the IDE with the test classpath. Both demos 
display piece codes with automatic cell sizing and a fixed cell width of seven. 
They are not included in the main library JAR.

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
