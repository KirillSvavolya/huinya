package model.ExceptionCollections;

public class IllegalCoordinateException extends Exception {
	private static final long serialVersionUID = 3925322232828489703L;
	private static String DefaultMessage = "The coordinate given does not respect the preconditons";
	// Default message.
	public IllegalCoordinateException() {
		this(DefaultMessage);
	}
	// Custom message.
	public IllegalCoordinateException(String message) {
		super(message);
	}
}
