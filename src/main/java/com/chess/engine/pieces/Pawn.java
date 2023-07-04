package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.PieceBehaviour.Alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATE = {8, 9, 16};

    Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffSet : CANDIDATE_MOVE_COORDINATE) {

            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() *
                    currentCandidateOffSet);
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                break;
            }
            if (currentCandidateOffSet == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
            } else if (currentCandidateOffSet == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())) {
                final int behindCandidateDestinationCoordinate = this.piecePosition +
                        (this.pieceAlliance.getDirection() * 8);
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()
                        && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                        legalMoves.add(new Move.MajorMove(board,this,candidateDestinationCoordinate));
                }
            }
            else if (currentCandidateOffSet==7){

            }
            else if(candidateDestinationCoordinate==9){

            }
        }
        return legalMoves;
    }
}
