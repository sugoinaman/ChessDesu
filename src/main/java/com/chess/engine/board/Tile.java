package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a tile on a chess board.
 * Each tile has a unique coordinate and may contain a piece.
 */
public abstract class Tile {

    // The coordinate of this tile on the board.
    protected final int tileCoordinate;

    // A cache of all possible empty tiles, indexed by their coordinates.
    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    /**
     * Returns true if this tile is occupied by a piece.
     */
    public abstract boolean isTileOccupied();

    /**
     * Returns the piece on this tile, or null if the tile is not occupied.
     */
    public abstract Piece getPiece();

    // Private constructor to prevent direct instantiation.
    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    // Creates and returns a map of all possible empty tiles.
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));

        }
        return ImmutableMap.copyOf(emptyTileMap);
        /**
         * return ImmutableMap.copyOf(emptyTileMap);
         * from guava library
         */
    }

    // method to create a new tile.
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        if (piece != null) {
            //if piece is not null then return occupied tile with piece
            return new OccupiedTile(tileCoordinate, piece);
        }
        return EMPTY_TILES_CACHE.get(tileCoordinate);
        /**
         * if piece is null then return empty tile instance, i is tileCoordinate
         * only way to create a tile is through this method
         */
    }


    // Represents an empty tile.
    public static final class EmptyTile extends Tile {
        private EmptyTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        public String toString() {
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

    // Represents a tile occupied by a piece.
    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;

        private OccupiedTile(final int tileCoordinate, final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public String toString() {
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
                    getPiece().toString();
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
