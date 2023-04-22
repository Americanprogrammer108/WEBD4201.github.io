package Chens_WEBD4201;
import java.util.Vector;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
public class Student extends User{
	/**
	 * Ethan Chen
	 * WEBD 4201
	 * January 19, 2022
	 * Clint McDonald
	 * 
	 * This class will inherit the User class to set the Student. It will set the student's attributes to its values. If there is an exception, the code will throw it.
	 * Also, the class has been modified so as to connect to the database to create a student.
	 */
	//public constant attributes
	public final static String DEFAULT_PROGRAM_CODE = "UNDC";
	public final static String DEFAULT_PROGRAM_DESCRIPTION = "Undeclared";
	public final static int DEFAULT_YEAR = 1;
	
	//private attributes
	private String programCode;
	private String programDescription;
	private int year;
	private Vector<Mark> marks;
	
	
	
	
	//parameterized constructor
	/**
	 * This constructor will call the base constructor which is the User class
	 * @param id
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param emailaddress
	 * @param enrolldate
	 * @param lastAccess
	 * @param type
	 * @param enabled
	 * @param program
	 * @param description
	 * @param myyear
	 * @param marks
	 * @throws InvalidIdException
	 * @throws InvalidNameException
	 * @throws InvalidPasswordException
	 * @throws InvalidUserDataException
	 */
	public Student(long id, String password, String firstName, String lastName, String emailaddress, Date enrolldate, Date lastAccess, char type, boolean enabled, String program, String description, int myyear, Vector<Mark> marks) throws InvalidIdException, InvalidNameException, InvalidPasswordException, InvalidUserDataException
	{
		super(id, password, firstName, lastName, emailaddress, enrolldate, lastAccess, enabled, type);
	
		//parent sets
		/* setID(id);
		setFirstName(firstName);
		setLastName(lastName);
		setEmailAddress(emailaddress);
		setLastAccess(lastAccess);
		setenrolldate(enrolldate);
		setenabled(enabled);
		settype(type); */
		
		//child sets
		setProgramCode(program);
		setProgramDescription(description);
		setYear(myyear);
		setMark(marks);
		}
	
	//overloaded constructor
	public Student(long id, String password, String firstName, String lastName, String emailaddress, Date enrolldate, Date lastAccess, char type, boolean enabled,  
			String programCode, String programDescription, int currentyear) throws InvalidIdException, InvalidNameException, InvalidPasswordException, InvalidUserDataException
	{
		this(id, password, firstName, lastName, emailaddress, lastAccess, enrolldate, type, enabled, programCode, programDescription, currentyear, null);
	}
	
	//default constructor
	public Student() throws InvalidIdException, InvalidNameException, InvalidPasswordException, InvalidUserDataException 
	{
		this(DEFAULT_ID, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL_ADDRESS, new Date(), new Date(), DEFAULT_TYPE, DEFAULT_ENABLED_STATUS, 
			DEFAULT_PROGRAM_CODE, DEFAULT_PROGRAM_DESCRIPTION, DEFAULT_YEAR);

	}

	/**
	 * 
	 * @param programcode
	 */
	//start of setters
	public void setProgramCode(String programcode)
	{
		programCode = programcode;
	}
	
	public void setProgramDescription(String programdesc)
	{
		programDescription = programdesc;
	}
	
	public void setYear(int setyear)
	{
		year = setyear;
	}
	
	public void setMark(Vector<Mark> marks)
	{
		this.marks = marks;
	}

	//end of setters
	
	/**
	 * 
	 * @return
	 */
	//start of getters
	public String getProgramCode()
	{
		return programCode;
	}
	
	public String getProgramDescription()
	{
		return programDescription;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public Vector<Mark> getMarks()
	{
		return marks;
	}
	//end of getters
	
	
	//override methods
	/**
	 * The toString method will print out information regarding the student, user, or faculty.
	 */
	public String toString()
	{
		String currentyear = null;
		if (getYear() == 1)
		{
			currentyear = "1st year";
		}
		else if (getYear() == 2)
		{
			currentyear = "2nd year";
		}
		else if (getYear() == 3)
		{	
			currentyear = "3rd year";
		}
		else
		{
			currentyear = "4th year";
		}
		return "Student info for: " + getFirstName() + " " + getLastName() + "(" + getid() + ")" + 
				"\nCurrently in " + currentyear + " of " + getProgramDescription() + " (" + getProgramCode() + ") at Durham College" +
				"\nEnrolled on: " + getEnrollDate();
	}

	

	
	
	//database methods
	public static void initialize(Connection aConnection) throws SQLException
	{
		StudentDA.initialize(aConnection);
	}
	
	public static void terminate() throws SQLException
	{
		StudentDA.terminate();
	}
	
	public static Student retrieve(long id) throws NotFoundException
	{
		return StudentDA.retrieve(id);
	}

	
	//create, update, and delete methods
	public boolean create() throws DuplicateException
	{
		try {
			StudentDA.create(this);
		} catch (DuplicateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidUserDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public int update() throws NotFoundException
	{
		StudentDA.Update(this);
		return 0;
		
	}
	
	public int delete() throws NotFoundException, SQLException
	{
		StudentDA.delete(getid());
		return 1;
		
	}
	
	public static Student authenticate(long id, String password) throws NotFoundException
	{
		return StudentDA.retrieve(id);
	}
	
}
