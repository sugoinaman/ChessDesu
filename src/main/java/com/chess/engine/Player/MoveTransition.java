package com.chess.engine.Player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class MoveTransition {

    private final Board transitionBoard;
    private final Move move;

    public MoveTransition(Board transitionBoard, Move move,
                          MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    private final MoveStatus moveStatus;

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
