package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {
    protected final int tileCoordinate;
    /*
        *protected since we want to use it in child class


    * private static final Map<Integer, EmptyTile> EMPTY_TILE_MAP = new HashMap<>();

    * private static final Map<Integer, EmptyTile> foo() {

        *for (int i = 0; i < 64; i++) {
        *    EMPTY_TILE_MAP.put(i, new EmptyTile(i));
        *}
        *return EMPTY_TILE_MAP;
    }
    !No mutability
    */
    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));

        }
        return Collections.unmodifiableMap(emptyTileMap);
        /*
          * return ImmutableMap.copyOf(emptyTileMap);
          * from guava library
         */
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        if (piece == null) {
            return new OccupiedTile(tileCoordinate, piece);
        }
        return EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile {
        private EmptyTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        public String toString(){
            return "-";
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;

        private OccupiedTile( final int tileCoordinate, final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public String toString(){
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase():
                    getPiece().toString();
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
