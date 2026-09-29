package ivs.games.accessories.gridboard.standard.checkers;

import ivs.games.accessories.gridboard.standard.PieceColor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Pieces in a standard checkers set.
 *
 * <p>Each constant describes a type and color. The same constant may occupy
 * multiple cells; individual pieces are identified by their positions.</p>
 */
@RequiredArgsConstructor
@Getter
public enum StandardPiece implements CheckersPiece {

    /** An ordinary white piece. */
    WHITE_MAN(CheckerType.MAN, PieceColor.WHITE),

    /** A white king. */
    WHITE_KING(CheckerType.KING, PieceColor.WHITE),

    /** An ordinary black piece. */
    BLACK_MAN(CheckerType.MAN, PieceColor.BLACK),

    /** A black king. */
    BLACK_KING(CheckerType.KING, PieceColor.BLACK);

    private final CheckerType type;
    private final PieceColor color;
}
