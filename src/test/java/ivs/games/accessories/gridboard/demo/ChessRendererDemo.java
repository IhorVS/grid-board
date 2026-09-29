package ivs.games.accessories.gridboard.demo;

import ivs.games.accessories.gridboard.Position;
import ivs.games.accessories.gridboard.render.ConsoleBoardRenderer;
import ivs.games.accessories.gridboard.standard.chess.ChessBoard;
import ivs.games.accessories.gridboard.standard.chess.StandardPiece;

/**
 * Demonstrates rendering the initial chess arrangement using piece codes.
 */
@SuppressWarnings("JavaPrintToLogpoint")
public class ChessRendererDemo {

    public static void main(String[] args) {
        ChessBoard<StandardPiece> board = new ChessBoard<>(8, 8);

        StandardPiece[] whiteBackRank = {
                StandardPiece.WHITE_ROOK,
                StandardPiece.WHITE_KNIGHT,
                StandardPiece.WHITE_BISHOP,
                StandardPiece.WHITE_QUEEN,
                StandardPiece.WHITE_KING,
                StandardPiece.WHITE_BISHOP,
                StandardPiece.WHITE_KNIGHT,
                StandardPiece.WHITE_ROOK
        };

        StandardPiece[] blackBackRank = {
                StandardPiece.BLACK_ROOK,
                StandardPiece.BLACK_KNIGHT,
                StandardPiece.BLACK_BISHOP,
                StandardPiece.BLACK_QUEEN,
                StandardPiece.BLACK_KING,
                StandardPiece.BLACK_BISHOP,
                StandardPiece.BLACK_KNIGHT,
                StandardPiece.BLACK_ROOK
        };

        for (int row = 0; row < board.getRows(); row++) {
            board.place(new Position(row, 0), whiteBackRank[row]);
            board.place(new Position(row, 1), StandardPiece.WHITE_PAWN);
            board.place(new Position(row, 6), StandardPiece.BLACK_PAWN);
            board.place(new Position(row, 7), blackBackRank[row]);
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
