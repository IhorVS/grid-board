package ivs.games.accessories.gridboard.demo;

import ivs.games.accessories.gridboard.Position;
import ivs.games.accessories.gridboard.render.ConsoleBoardRenderer;
import ivs.games.accessories.gridboard.standard.checkers.CheckersBoard;
import ivs.games.accessories.gridboard.standard.checkers.StandardPiece;

/**
 * Demonstrates rendering the initial international draughts arrangement
 * using piece codes.
 *
 * <p>Each side has twenty men on dark cells in its four starting lines.
 * Cell {@code a1} is considered dark.</p>
 */
@SuppressWarnings("JavaPrintToLogpoint")
public class CheckersRendererDemo {

    public static void main(String[] args) {
        CheckersBoard<StandardPiece> board = new CheckersBoard<>(10, 10);

        for (int row = 0; row < board.getRows(); row++) {
            for (int column = 0; column < board.getColumns(); column++) {
                if ((row + column) % 2 != 0) {
                    continue;
                }

                if (column < 4) {
                    board.place(new Position(row, column), StandardPiece.WHITE_MAN);
                } else if (column >= 6) {
                    board.place(new Position(row, column), StandardPiece.BLACK_MAN);
                }
            }
        }

        ConsoleBoardRenderer<StandardPiece> automatic =
                new ConsoleBoardRenderer<>(StandardPiece::getCode);

        System.out.println("Piece codes, automatic cell width:");
        System.out.println(automatic.render(board));
        System.out.println();

        ConsoleBoardRenderer<StandardPiece> fixed =
                new ConsoleBoardRenderer<>(StandardPiece::getCode, 7);

        System.out.println("Piece codes, cell width = 7:");
        System.out.println(fixed.render(board));
    }
}
