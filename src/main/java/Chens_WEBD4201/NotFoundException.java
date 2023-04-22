package Chens_WEBD4201;
@SuppressWarnings("serial")


public class NotFoundException extends Exception{
	/**
	 * This class will throw an exception when the record associated with the id does not exist
	 * @author Chens
	 * version 1.0
	 * @since February 1, 2023
	 */
	
	/**
	 * Default constructor
	 */
	public NotFoundException()
	{
		super();
	}
	
	/**
	 * Parameterized constructor
	 * @param message
	 */
	public NotFoundException(String message)
	{
		super(message);
	}
	
	
}
