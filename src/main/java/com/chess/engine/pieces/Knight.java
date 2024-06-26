package com.chess.engine.pieces;

import com.chess.engine.pieces.PieceBehaviour.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {

    // collection of possible move offsets for the piece
    // if a knight is at pos x its legal moves is x + CANDIDATE_MOVE_COORDINATE
    private final static int[] CANDIDATE_MOVE_COORDINATE = {-17, -15, -10, -6, 6, 10, 15, 17};


    public Knight( final Alliance pieceAlliance,final int piecePosition) {
        super(PieceType.KNIGHT,piecePosition, pieceAlliance);
    }


    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {


        final List<Move> legalMoves = new ArrayList<>();


        for (final int currentCandidateOffSet : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffSet;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffSet) ||
                        isSecondColumnExclusion(this.piecePosition, currentCandidateOffSet) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidateOffSet) ||
                        isEighthColumnExclusion(this.piecePosition, currentCandidateOffSet)) {
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    /**
                     * Not occupied so add the piece to the tile
                     * "this" keyword here means that we are passing an instance of the class itself which is what
                     * is required "piece"
                     */
                    legalMoves.add(new Move.MajorMove(board,this, candidateDestinationCoordinate));
                } else {

                     // occupied, check if it's occupied by your piece color or enemy

                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    /**
                     * here we also need to add the legal moves where a player can dethrone the opponent's piece
                     * so we check if their alliances are different and then also add it to legal moves
                     */
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new Move.AttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }


    /**
     * solve an exception where let's say if we were at first column as a knight, and we moved -10 offset
     * we would reach the far right side of the board.
     * Same case if our knight was in second column we need some exclusions for edge cases.
     * @param currentPosition
     * @param candidateOffSet
     * @return boolean
     */
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffSet) {

        //current position should be 0, and then we perform these checks
        return BoardUtils.FIRST_COLUMN[currentPosition] &&
                ((candidateOffSet == -17) ||
                        (candidateOffSet == -10) ||
                        (candidateOffSet == 6) ||
                        (candidateOffSet == 15));
    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] &&
                ((candidateOffset == -10) ||
                        (candidateOffset == 6));
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] &&
                ((candidateOffset == -6) ||
                        (candidateOffset == 10));
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] &&
                ((candidateOffset == -15) ||
                        (candidateOffset == -6) ||
                        (candidateOffset == 10) ||
                        (candidateOffset == 17));
    }

    @Override
    public Knight movePiece(Move move) {
        return new Knight(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }
     @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }
}
