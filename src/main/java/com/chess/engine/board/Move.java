package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    private Move(final Board board,
          final Piece movedPiece,
          final int destinationCoordinate){
        this.board=board;
        this.movedPiece=movedPiece;
        this.destinationCoordinate=destinationCoordinate;
    }

    public int getDestinationCoordinate(){
        return this.destinationCoordinate;
    }

    public abstract Board execute();

    public static final class  MajorMove extends Move{

        public MajorMove(final Board board,
                         final Piece movedPiece,
                         final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public Board execute() {
            final Board.Builder builder=new Board.Builder();
            for(final Piece piece : this.board.getCurrentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece: this.board.getCurrentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            builder.setPiece(null);
            builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

    }
    public static final class AttackMove extends Move{

        final Piece attackPiece;
        public AttackMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Piece attackPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackPiece = attackPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }
}
