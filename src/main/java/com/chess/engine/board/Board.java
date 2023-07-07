package com.chess.engine.board;

import com.chess.engine.pieces.*;
import com.chess.engine.pieces.PieceBehaviour.Alliance;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Map;

public class Board {

    private final List<Tile> gameBoard;

    private Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
    }

    private static List<Tile> createGameBoard(final Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        //black layout
        builder.setPiece(new Rook(0, Alliance.BLACK));
        builder.setPiece(new Knight(1, Alliance.BLACK));
        builder.setPiece(new Bishop(2, Alliance.BLACK));
        builder.setPiece(new Queen(3, Alliance.BLACK));
        builder.setPiece(new King(4, Alliance.BLACK));
        builder.setPiece(new Bishop(5, Alliance.BLACK));
        builder.setPiece(new Knight(6, Alliance.BLACK));
        builder.setPiece(new Rook(7, Alliance.BLACK));
        builder.setPiece(new Pawn(8, Alliance.BLACK));
        builder.setPiece(new Pawn(9, Alliance.BLACK));
        builder.setPiece(new Pawn(10, Alliance.BLACK));
        builder.setPiece(new Pawn(11, Alliance.BLACK));
        builder.setPiece(new Pawn(12, Alliance.BLACK));
        builder.setPiece(new Pawn(13, Alliance.BLACK));
        builder.setPiece(new Pawn(14, Alliance.BLACK));
        builder.setPiece(new Pawn(15, Alliance.BLACK));

        // White Layout

        builder.setPiece(new Rook(48, Alliance.WHITE));
        builder.setPiece(new Knight(49, Alliance.WHITE));
        builder.setPiece(new Bishop(50, Alliance.WHITE));
        builder.setPiece(new Queen(51, Alliance.WHITE));
        builder.setPiece(new King(52, Alliance.WHITE));
        builder.setPiece(new Bishop(53, Alliance.WHITE));
        builder.setPiece(new Knight(54, Alliance.WHITE));
        builder.setPiece(new Rook(55, Alliance.WHITE));
        builder.setPiece(new Pawn(56, Alliance.WHITE));
        builder.setPiece(new Pawn(57, Alliance.WHITE));
        builder.setPiece(new Pawn(58, Alliance.WHITE));
        builder.setPiece(new Pawn(59, Alliance.WHITE));
        builder.setPiece(new Pawn(60, Alliance.WHITE));
        builder.setPiece(new Pawn(61, Alliance.WHITE));
        builder.setPiece(new Pawn(62, Alliance.WHITE));
        builder.setPiece(new Pawn(63, Alliance.WHITE));
        return null;
    }

    public Tile getTile(final int tileCoordinate) {
        return null;
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig; // Map the board's individual tile index with the Piece itself
        Alliance nextMoveMaker;

        public Builder() {

        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build() {
            return new Board(this);
        }


    }
}
