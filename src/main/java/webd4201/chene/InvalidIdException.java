package webd4201.chene;
import java.lang.Exception;

public class InvalidIdException extends Exception{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("serial")
	
	/**
	 * 
	 * @param iD2
	 * @return
	 * This class will throw an exception when the ID is invalid
	 */
	
	public boolean verifyId(long iD2) {
		String convertedID = Long.toString(iD2);
		// TODO Auto-generated method stub
		if (convertedID.length() == 9)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public InvalidIdException() {
		super();
	}

	public InvalidIdException(String message) {
		super(message);
		//message = "INVALID ID";
		//System.out.println(message);
		
	}
}
