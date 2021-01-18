package model.ExceptionCollections;

public class IllegalCoordinateException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3925322232828489703L;

	public IllegalCoordinateException() {
		super("The coordinate given does not respect the preconditons");
	}
}
