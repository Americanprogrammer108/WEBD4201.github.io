package webd4201.chene;

import java.util.Date;
import java.lang.Exception;
import java.sql.Connection;
import java.sql.SQLException;

public class Faculty extends User{

	/**
	 * This class sets the user's attributes to its values; if an error occurs, it will throw an exception
	 */
	public final String DEFAULT_SCHOOL_CODE = "SET";
	public final String DEFAULT_SCHOOL_DESCRIPTION = "School of Engineering & Technology";
	public final String DEFAULT_OFFICE = "H-140";
	public final int DEFAULT_PHONE_EXTENSION = 1234;
	
	private String schoolCode;
	private String schoolDescription;
	private String office;
	private int extension;
	

	
	//parameterized constructor
	//to make things easier, I put the inherited attributes on the left, and the new attributes on the right.
	public Faculty(long id,	String password, String firstName, String lastName, String emailAddress, Date lastAccess, Date enrolldate, boolean enabled, char type, String schoolcode, String schooldesc, String office, int extension) throws InvalidUserDataException, InvalidPasswordException, InvalidNameException, InvalidIdException
	{
		//super();
		super(id, password, firstName, lastName, emailAddress, lastAccess, enrolldate, enabled, type);
		//current attributes
		setschoolcode(schoolcode);
		setschooldescription(schooldesc);
		setoffice(office);
		setextension(extension);
	}

	//default constructor
	public Faculty() throws InvalidIdException, InvalidNameException, InvalidPasswordException, InvalidUserDataException
	{
		super(DEFAULT_ID, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL_ADDRESS, new Date(), new Date(), DEFAULT_ENABLED_STATUS, DEFAULT_TYPE);
	}
	
	//START OF GETTERS
	public String getschoolcode()
	{
		return schoolCode;
	}
	
	public String getschooldesc()
	{
		return schoolDescription;
	}
	
	public String getoffice()
	{
		return office;
	}
	
	public int getextension()
	{
		return extension;
	}
	//END OF GETTERS
	
	
	//START OF SETTERS
	public void setschoolcode(String SchoolCode)
	{
		schoolCode = SchoolCode;
	}
	
	public void setschooldescription(String SchoolDesc)
	{
		schoolDescription = SchoolDesc;
	}
	
	public void setoffice(String Schooloffice)
	{
		office = Schooloffice;
	}
	
	public void setextension(int extensionCode)
	{
		extension = extensionCode;
	}
	
	//END OF SETTERS
	
	public String toString()
	{
		return "Faculty Info for: " + getid() + "\nName: " + getFirstName() + " " + getLastName() + " (" + getEmailAddress() + ")" + "\nCreated on: " + getLastAccess() + "\nLast access: " + getLastAccess() + "\n" + getschooldesc() + "\nOffice: " + getoffice()+ "\n" + getextension();
	}
	
	public void getTypeForDisplay()
	{
		System.out.println(toString());
		//output = output.replaceAll("User", "Faculty");
	}
	
	
	//all class methods
	public static void initialize(Connection aConnection) throws SQLException
	{
		FacultyDA.initialize(aConnection);
	}
	
	public static void terminate() throws SQLException
	{
		FacultyDA.terminate();
	}
	
	public static Faculty retrieve(long id) throws NotFoundException
	{
		return FacultyDA.retrieve(id);
	}
	
	
	//all instance methods
	
	public boolean create() throws DuplicateException
	{
		FacultyDA.create(this);
		
		return FacultyDA.create(this);
	}
	
	public int update() throws NotFoundException
	{
		FacultyDA.Update(this);
		return 0;
		
	}
	
	public int delete() throws NotFoundException, SQLException
	{
		FacultyDA.delete(this);
		return 0;
		
	}
	

}
