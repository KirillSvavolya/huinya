package model;

import model.ExceptionCollections.IllegalCoordinateException;

public interface Field {
	public static final int numOfCellsRequired = 63; // for test purposes.
	public static final int numberOfShips = 28;
	public static final int EMPTY_CELL = 0;
	public static final int TAKEN_CELL = 1;
	public static final int DEAD_CELL = 2;
	public static final int MISS_CELL = 3;
	public static int AAscii = 65;
	public static int OAscii = 79;
	
	/**
	 * Translates coordinate from string to indices.
	 * @requires coordinate == "[0-9]" + "[A-O]".
	 * @param coordinate to translate.
	 * @return array with two integers representing indices of the given coordinate in the two dimensional array.
	 * @throws IllegalCoordinateException if the coordinate given does not respect the preconditions.
	 */
	default public int[] breakCoordinates(String coordinate) throws IllegalCoordinateException {
			int[] res = new int[2];
			int vertical = Integer.parseInt("" + coordinate.charAt(0));
			char horizontal = coordinate.charAt(1);
			int ascii = (int) horizontal;
			if (ascii > OAscii && ascii < AAscii && vertical < 0 && vertical > 9) {
				throw new IllegalCoordinateException();
			}
			ascii = ascii - AAscii;
			res[0] = vertical;
			res[1] = ascii;
			return res;
		}
}
