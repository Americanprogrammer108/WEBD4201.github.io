package webd4201.chene;
import java.lang.Exception;

public class InvalidUserDataException extends Exception{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("serial")
	
	/**
	 * The class will throw an exception if any of the values in the user class is invalid
	 */
	public InvalidUserDataException() {
		super();
	}
	
	public InvalidUserDataException(String message) {
		super(message);
	}

}
