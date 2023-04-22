package Chens_WEBD4201;
import java.lang.Exception;

public class InvalidNameException extends Exception{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("serial")
	
	/**
	 * This class will throw an exception when the name is a number or is invalid
	 */
	public InvalidNameException() {
		super();
	}
	
	
	
	public InvalidNameException(String message) {
		super(message);
	}


	public boolean validName(String name)
	{
		try
		{
			if (name.isEmpty())
			{
				System.out.println("Name cannot be empty!");
				throw new InvalidNameException();
				
			}
			else
			{
				return true;
			}/**/
			
			/* for (int i = 0; i == name.length(); i++)
			{
				
				if (Character.isAlphabetic(name.charAt(i)) == false)
				{
					throw new Exception(name);
				}
				else
				{
					return true;
				}
			}*/
		}
		catch(Exception e)
		{
			return false;
		}


	}

}
