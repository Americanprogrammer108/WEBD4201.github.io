package webd4201.chene;
import java.lang.Exception;

public class InvalidPasswordException extends Exception{
	
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("serial")
	
	/**
	 * This class throws an exception when the password is too long or short.
	 */
	public InvalidPasswordException() {
		super();
	}
	
	public InvalidPasswordException (String message)
	{
		super(message);
	}
	
	public boolean verifyPassword(String password)
	{
		if (password.length() == 9)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
