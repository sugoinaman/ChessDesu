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


    Player(final Board board,
           final Collection<Move>legalMoves,
           final Collection<Move>opponentMoves){
        this.board=board;
        this.playerKing=establishKing();
        this.legalMoves=legalMoves;
        this.isInCheck=!Player.calculateAttacksOnTile
                (this.playerKing.getPiecePosition(),opponentMoves).isEmpty();
    }


    /*
    checking the moves from collection of attack moves
    if it coincides with the current coordinate of king then attack,
    then we return a list of moves that attack that kings position
     */
    private static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> opponentMoves) {
        final List<Move>attackMoves=new ArrayList<>();
        for(final Move move:opponentMoves){
            if(piecePosition==move.getDestinationCoordinate()){
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    private King establishKing() {
        for(final Piece piece:getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("Should not reach here! Not a valid board!");
    }

    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }
    public boolean isInCheck(){
        return this.board.getCurrentPlayer().getOpponent().getLegalMoves().contains(this.playerKing.getPiecePosition());
    }
    public boolean isInCheckMate(){
        return this.isInCheck() && !hasEscapeMoves();
    }

    private boolean hasEscapeMoves() {
        for(final Move move:this.legalMoves){
            final MoveTransition transition=makeMove(move);
            if(transition.getMoveStatus().isDone()){
                return true;
            }
        }
        return false;
    }

    public boolean isInStaleMate(){
        return !this.isInCheck() && !hasEscapeMoves();
        //not in check atm but our moves are in check
    }
    public boolean isCastled(){
        return false;
    }
    public MoveTransition makeMove(final Move move){
        if(!isMoveLegal(move)){
            return new MoveTransition(this.board,move,MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitionBoard=move.execute();
        final Collection<Move> kingAttacks=this.calculateAttacksOnTile(transitionBoard.getCurrentPlayer().getOpponent().getPlayerKing().getPiecePosition());
        if(!kingAttacks.isEmpty()){
            return new MoveTransition(this.board,move,MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transitionBoard,move,MoveStatus.DONE);
    }

    public abstract Collection<Piece> getActivePieces() ;
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();

}
