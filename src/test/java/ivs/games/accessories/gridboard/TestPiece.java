package ivs.games.accessories.gridboard;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
enum TestPiece implements Piece {
    FIRST("F"),
    SECOND("S");

    private final String code;
}
