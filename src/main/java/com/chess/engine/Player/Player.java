package com.chess.engine.Player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.PieceBehaviour.Alliance;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;


    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
        // a player is in check when the opponent moves has king's piece position in the collection

    }

    public King getPlayerKing() {
        return this.playerKing;
    }


    /*
    checking the moves from collection of attack moves
    if it coincides with the current coordinate of king then attack,
    then we return a list of moves that attack that kings position
     */
    private static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> opponentMoves) {
        final List<Move> attackMoves = new ArrayList<>();
        for (final Move move : opponentMoves) {
            if (piecePosition == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    private King establishKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Should not reach here! Not a valid board!");
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return this.board.getCurrentPlayer().getOpponent().getLegalMoves().contains(this.playerKing.getPiecePosition());
        //if a piece has a legal move which targets enemy king = check mate
    }

    private Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    public boolean isInCheckMate() {
        return this.isInCheck() && !hasEscapeMoves();
    }

    private boolean hasEscapeMoves() {
        for (final Move move : this.legalMoves) {
            final MoveTransition transition = makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    public boolean isInStaleMate() {
        return !this.isInCheck() && !hasEscapeMoves();
        //not in check atm but our moves are in check OR we are not in check yet but any move made and our king will be in check
    }

    public boolean isCastled() {
        return false;
    }

    /**
     *
     * @implNote  MoveTransition
     * @ The move transition is a way of tracking the board before the move was made and AFTER the move was made,
     * as well as if the move was successfully made or not.
     */
    public MoveTransition makeMove(final Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE); //returning the same board when the move is illegal
        }
        // We create a new Board everytime a move is legal, this is because our board class is immutable, so we cannot mutate it.
        final Board transitionBoard = move.execute();

        final Collection<Move> kingAttacks = this.calculateAttacksOnTile(transitionBoard.getCurrentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.getCurrentPlayer().getLegalMoves());
        // are there any attacks on the current player's king and if it will leave player in check

        if (!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE); //return new transition mode when MoveStatus is done
    }

    public abstract Collection<Piece> getActivePieces();

    public abstract Alliance getAlliance();

    public abstract Player getOpponent();

}
