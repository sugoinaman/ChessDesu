package com.chess.engine.Player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.PieceBehaviour.Alliance;

import java.util.Collection;

public class BlackPlayer extends Player {

    public BlackPlayer(final Board board, final Collection<Move> blackStandardLegalMoves,final Collection<Move> whiteStandardLegalMoves) {
        super(board,blackStandardLegalMoves,whiteStandardLegalMoves);

    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.getWhitePlayer();
    }

    private Player getWhitePlayer() {
        return this.board.whiteplayer();
    }
}
