package com.chess.engine.board;


public class BoardUtils {
    /**
     * we initialise the particular column number or row number with true
     * (could have also just used indices)
     * we declare it static because if somehow the values of these changes(which will not because final)
     * the changes should be synced across all instances
     * CHATGPT: When a variable is final but not static, each instance of the class gets its own copy of the variable.
     * Even though the value of the variable can't be changed, it still takes up memory for each instance.
     * This can be inefficient if the variable's value is the same for all instances.
     */

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);
    public static final boolean[] SECOND_ROW = initRow(8); //starting index of 2nd row
    public static final boolean[] SEVENTH_ROW = initRow(48);  // starting index of 7th row

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8; //or column

    private BoardUtils() {
        throw new RuntimeException("Cannot be instantiated");
    } //hence private

    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[NUM_TILES];
        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;

        } while (columnNumber < NUM_TILES);
        return column;
    }

    private static boolean[] initRow(int rowNumber) {
        final boolean[] row = new boolean[NUM_TILES];
        do {
            row[rowNumber] = true;
            rowNumber++;

        } while (rowNumber % NUM_TILES_PER_ROW != 0);
        return row;
    }


    public static boolean isValidTileCoordinate(final int coordinate) {
        //simply return if the coordinate is between [0,64}
        return (coordinate >= 0 && coordinate < 64);
    }
}
