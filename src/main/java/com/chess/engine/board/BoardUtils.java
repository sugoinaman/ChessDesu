package com.chess.engine.board;


public class BoardUtils {

    public static final boolean[] FIRST_COLUMN=initColumn(0);
    public static final boolean []SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);
    public static final boolean[] SECOND_ROW=null;
    public static final boolean[] SEVENTH_ROW=null;

    public static final int NUM_TILES=64;
    public static final int NUM_TILES_PER_ROW=8;

    private static boolean[] initColumn(int columnNumber) {
        final boolean [] column =new boolean[64];
        do {
            column[columnNumber] = true;
            columnNumber += 8;

        }while(columnNumber<64);
        return column;
    }




    private BoardUtils(){
        throw new RuntimeException("Cannot be instantiated");
    }

    public static boolean isValidTileCoordinate(final int coordinate){
        return (coordinate>=0 && coordinate<64);
    }
}
