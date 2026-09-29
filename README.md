# Grid Board

A Java library for rectangular boards that hold immutable pieces. It provides
a general board API and ready-to-use chess and checkers piece sets. The library
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

The utility accepts columns `a` through `z` and does not check board
boundaries. Board operations validate positions against the board's dimensions.

## Using a board

```java
GridBoard<StandardPiece> board = new ChessBoard<>(8, 8);

Position kingPosition = CellNotation.parse("e1");
board.place(kingPosition, StandardPiece.WHITE_KING);

StandardPiece king = board.get(kingPosition);
board.remove(kingPosition);
```

This example uses
`ivs.games.accessories.gridboard.standard.chess.StandardPiece`.

Each cell holds at most one piece. `place` requires an empty cell, and `remove`
requires an occupied cell. The board does not interpret either operation as a
game move.

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
identify pieces; they do not define a notation for moves.

## Board state

`getState()` returns an unmodifiable snapshot mapping occupied positions to
pieces. `setState(state)` replaces the board's contents after validating the
entire map. `clear()` removes all pieces. A copy constructor creates another
board with the same dimensions and piece positions.

Snapshots and copies share references to piece objects. Implementations of
`Piece` must therefore be immutable.

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
