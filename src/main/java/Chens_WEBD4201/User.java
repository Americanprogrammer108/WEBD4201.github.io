package Chens_WEBD4201;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;


public class User {
	/**
	 * The User class takes on the CollegeInterface class, where it will set the attributes for the user and maybe print it out using the dump() function.
	 *
	 */
	public static Vector<User> student = new Vector<User>();
	private static User aUser;
	
	//establish connection
	private static Connection aConnection; //is null; will cause a NullPointerException
	private static Statement aStatement;
	
	//public class constants
	public final static long DEFAULT_ID = 100123456;
	public final static String DEFAULT_PASSWORD = "password";
	public final static byte MINIMUM_PASSWORD_LENGTH = 8;
	public final static byte MAXIMUM_PASSWORD_LENGTH = 40;
	public final static String DEFAULT_FIRST_NAME = "John";
	public final static String DEFAULT_LAST_NAME = "Doe";
	public final static String DEFAULT_EMAIL_ADDRESS = "john.doe@dcmail.com";
	public final static boolean DEFAULT_ENABLED_STATUS = true;
	public final static char DEFAULT_TYPE = 's';
	public final static byte ID_NUMBER_LENGTH = 9;
	
	private static final String insertUser = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	private static final String retrieveUser = "SELECT * FROM Users WHERE id = ?";
	private static final String retrieveallUsers = "SELECT * FROM users ORDER BY FirstName;";
	
	private static final String updateUser = "UPDATE users SET id = ?, firstname = ?, lastname = ?, emailaddress = ?, enabled = ?, type = ? WHERE id = ?;";
	private static final String deleteUser = "DELETE FROM users WHERE id = ?;";
	
	
	public static final DateFormat DF = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CANADA);
	
	//private class attributes
	private static long staticid;
	private static String staticpassword;
	private static String staticfirstName;
	private static String staticlastName;
	private static String staticemailAddress;
	private static Date staticlastAccess;
	private static Date staticenrolldate;
	private static boolean staticenabled;
	private static char statictype;
	
	private long id;
	private String password;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private Date lastAccess;
	private Date enrolldate;
	private boolean enabled;
	private char type;
	
	
	
	//START OF SETTERS
	public void setID(long ID) throws InvalidIdException
	{
		try
		{
			String myID = Long.toString(ID);
			if (myID.length() == 9)
			{
				id = ID;
			}
			else
			{
				throw new InvalidIdException("Invalid ID");
			}
			
		}
		catch(InvalidIdException id)
		{
			String convertedID = Long.toString(ID);
			
			if (convertedID.length() < ID_NUMBER_LENGTH)
			{
				System.out.print("ID is not long enough! ID must have a length of " + ID_NUMBER_LENGTH + ".\n");
			}
			else if (convertedID.length() > ID_NUMBER_LENGTH)
			{
				System.out.print("ID is too long! ID must have a length of " + ID_NUMBER_LENGTH + ".\n");
			}
			
		}
		
	}
	
//	private boolean verifyId(long iD2) {
//		String convertedID = Long.toString(iD2);
//		if (convertedID.length() == ID_NUMBER_LENGTH)
//		{
//			return true;
//		}
//		
//		return false;
//		
//	}

	
	public void setFirstName(String firstname) throws InvalidNameException
	{
		System.out.println(firstname);
		try
		{
			if(firstname != null && firstname.isEmpty() == false)
			{
				int counter = 0;
				while (counter < firstname.length())
				{
					if (Character.isAlphabetic(firstname.charAt(counter)))
					{
						counter++;
					}
					else
					{
						throw new InvalidNumberNameException(firstname);
						//if anything goes wrong with the name such as a number containing it, throw the invalidNumberNameexception class.
					}
				}
				firstName = firstname;
			}
			else
			{
				throw new InvalidNameException(firstname);
			}
		}
		catch(InvalidNameException name)
		{
			System.out.println("First name cannot be empty. Try again.");
			System.out.print(name);
		}
		catch(InvalidNumberNameException name)
		{
			System.out.println("The name you entered contains numbers. Try again.");
		}
			
	}
	
	private boolean verifyFirstName(String firstname2) {
		if (firstname2 == null)
		{
			System.out.println("First name cannot be empty!"); //only applies to empty strings
			return false;
		}
		else
		{
			return true;
		}
	}
	

	public void setLastName(String lastname)
	{
		try
		{
			if(verifyLastName(lastname) == true)
			{
				//set last name if valid
				lastName = lastname;
			}
			else
			{
				//if not, throw exception
				throw new InvalidNameException(lastname);
			}
		}
		catch (InvalidNameException invalidlastname)
		{
			int lastnameerror = Integer.parseInt(lastname);
			System.out.println("Last name cannot be a number!");
		}
		
	}
	private boolean verifyLastName(String lastname2) throws InvalidNameException {
		if (lastname2 == null)
		{
			//display if last name is empty
			System.out.println("Last name cannot be empty!");
			return false;
		}
		else
		{
			//if not, return true
			return true;
		}
		
	}

	
	public void setPassword(String Password) throws InvalidPasswordException
	{
		try
		{
			if (verifyPassword(Password))
			{
				//valid password? Set it
				password = Password;
			}
			else
			{
				//if not, throw exception
				throw new InvalidPasswordException("Bad password");
			}
		}
		catch(InvalidPasswordException invalidpassword)
		{
			// if the password is not long enough, throw the exception
			System.out.print("Password not long enough. Password length must be between " + MINIMUM_PASSWORD_LENGTH + " and " + MAXIMUM_PASSWORD_LENGTH);
		}
	}
	
	private boolean verifyPassword(String password2) {
		if (password2.length() >= MINIMUM_PASSWORD_LENGTH && password2.length() <= MAXIMUM_PASSWORD_LENGTH)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}

	
	public void setEmailAddress(String email)
	{
		emailAddress = email;
	}
	
	public void setLastAccess(Date lastaccess)
	{
		lastAccess = lastaccess;
	}
	
	public void setenrolldate(Date enrollDate)
	{
		enrolldate = enrollDate;
	}
	
	public void setenabled(boolean isenabled)
	{
		enabled = isenabled;
	}
	
	public void settype(char Type)
	{
		type = Type;
	}
	//END OF SETTERS
	
	
	//START OF GETTERS
	public long getid()
	{
		return id;
	}
	
	public String getpassword()
	{
		return password;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getEmailAddress()
	{
		return emailAddress;
	}
	
	public Date getLastAccess()
	{
		return lastAccess;
	}
	
	public Date getEnrollDate()
	{
		return enrolldate;
	}
	
	public boolean getEnabled()
	{
		return enabled;
	}
	
	public char getType()
	{
		return type;
	}
	
	//END OF GETTERS
	
	

	
	//parameterized constructor
	public User(long id, String password, String firstName, String lastName, String emailAddress, Date lastAccess, Date enrollDate, boolean enabled, char type) throws InvalidIdException, InvalidNameException, InvalidPasswordException, InvalidUserDataException
	{
		//set all the attributes from the data being passed
		setID(id);
		setFirstName(firstName);
		setLastName(lastName);
		setEmailAddress(emailAddress);
		setLastAccess(lastAccess);
		setenrolldate(enrollDate);
		setenabled(enabled);
		settype(type);
	}
	
	//default constructor
	public User() throws InvalidUserDataException, InvalidNameException, InvalidIdException, InvalidPasswordException
	{
		//let's call the parameterized constructor
		this(DEFAULT_ID, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL_ADDRESS, new Date(), new Date(), DEFAULT_ENABLED_STATUS, DEFAULT_TYPE);
	}
	

	
	public void dump()
	{
		//print information out
		System.out.print(toString());
	}

//
	public static User authenticate(long id, String password) throws NotFoundException, InvalidUserDataException, InvalidNameException, InvalidIdException, InvalidPasswordException
	{
		return User.retrieve(id);
	}
	
	
	
	//DATABASE SECTION
	public static void initialize(Connection conn) throws SQLException
	{
		try
		{
			aConnection = conn;
			aStatement = aConnection.createStatement();
		}
		catch (SQLException sqle)
		{
			System.out.println(sqle);
		}
	}
	
	
	//CRUD methods
	
	//CREATE
	public static boolean create(Student aStudent) throws DuplicateException, InvalidIdException, InvalidNameException, InvalidPasswordException, InvalidUserDataException
	{
		boolean inserteduser = false, insertedstudent = false;
	
		
//		String insertuser = "INSERT INTO Users (Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type) VALUES (100222222, 'password', 'Robert', 'McReady', 'bob.mcready@dcmail.ca', '2016-03-07', '2015-09-03', true, 's');";
		
//		String insertstudent = "INSERT INTO Students (Id, ProgramCode, ProgramDescription, Year) VALUES (100222222, 'CPA', 'Computer Programmer Analyst', 3);";
		try
		{
			retrieve(aStudent.getid());
			throw new DuplicateException("Record with that id already exists!");
		}
		catch(NotFoundException nfe)
		{
			
			try
			{
				System.out.print("Inserting, please wait...\n");
				
//				User aUser = new User(id, password, firstName, lastName, emailAddress, lastAccess, enrolldate, enabled, type);
				PreparedStatement psInsertUser = aConnection.prepareStatement(insertUser);
				psInsertUser.setLong(1, aStudent.getid());
				psInsertUser.setString(2, aStudent.getFirstName());
				psInsertUser.setString(3, aStudent.getProgramDescription());
				psInsertUser.setInt(4, aStudent.getYear());
				psInsertUser.setLong(5, aStudent.getid());
				psInsertUser.setString(6, aStudent.getProgramCode());
				psInsertUser.setString(7, aStudent.getProgramDescription());
				psInsertUser.setInt(8, aStudent.getYear());
				psInsertUser.setLong(9, aStudent.getid());

				System.out.print("Insert successful.\n");
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle);
			}
		}
		return inserteduser && insertedstudent;
	}
	
	//RETRIEVE
	public static User retrieve(long key) throws NotFoundException, InvalidUserDataException, InvalidNameException, InvalidIdException, InvalidPasswordException
	{
		aUser = new User();
		try
		{
			PreparedStatement ps = aConnection.prepareStatement(retrieveUser);
			ps.setLong(1, key);
			ResultSet rs = ps.executeQuery();
			boolean firstRecord = rs.next();
			
			if (firstRecord) {
				aUser.id = rs.getInt("id");
				aUser.firstName = rs.getString("firstName");
				aUser.lastName = rs.getString("lastName");
				aUser.password = rs.getString("password");
				aUser.emailAddress = rs.getString("emailaddress");
				aUser.lastAccess = rs.getDate("lastaccess");
				aUser.enrolldate = rs.getDate("enrolldate");
				aUser.enabled = rs.getBoolean("enabled");
				aUser.type = rs.getString("type").charAt(0);
			} else {
				throw (new NotFoundException("The indicated record is not found."));
			}
			rs.close();
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle);
		}
		return aUser;
	}
	
	public static Vector<User> retrieveAll() throws InvalidUserDataException, InvalidNameException, InvalidIdException, InvalidPasswordException
	{
		Vector<User> users = new Vector<User>();
		try
		{
			PreparedStatement ps = aConnection.prepareStatement(retrieveallUsers);
			ResultSet rs = ps.executeQuery();
			boolean moreData = rs.next();
			while(moreData)
			{
				User getNewUser = new User();
				getNewUser.id = rs.getLong("ID");
				getNewUser.password = rs.getString("password");
				getNewUser.firstName = rs.getString("FirstName");
				getNewUser.lastName = rs.getString("LastName");
				getNewUser.emailAddress = rs.getString("EmailAddress");
				getNewUser.lastAccess = rs.getDate("LastAccess");
				getNewUser.enrolldate = rs.getDate("EnrollDate");
				getNewUser.enabled = rs.getBoolean("Enabled");
				getNewUser.type = rs.getString("Type").charAt(0);
				users.addElement(getNewUser);
				moreData = rs.next();
			}
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle);
		}
		return users;
	}
	
	//UPDATE
	public static boolean UPDATE(User updatedUser) throws InvalidUserDataException, InvalidNameException, InvalidIdException, InvalidPasswordException 
	{
		
		System.out.println("Updating " + updatedUser.getid());
		int records = 0;
		try
		{
			retrieve(updatedUser.getid());
			PreparedStatement ps = aConnection.prepareStatement(updateUser);
			ps.setLong(1, updatedUser.id);
			ps.setString(2, updatedUser.firstName);
			ps.setString(3, updatedUser.lastName);
			ps.setString(4, updatedUser.emailAddress);
			ps.setBoolean(5, updatedUser.enabled);
			ps.setString(6, String.valueOf(updatedUser.type));
			ps.setLong(7, updatedUser.id);
			records = ps.executeUpdate();
			System.out.println("Update successful.");
		}
		catch(NotFoundException nfe)
		{
			System.out.print(nfe);
		}
		catch(SQLException nfe)
		{
			
		}
		
		return records == 1;
	}
	
	
	//DELETE
	public static boolean delete(long getid) throws NotFoundException, InvalidUserDataException, InvalidNameException, InvalidIdException, InvalidPasswordException
	{
		System.out.println("Deleting...");
		int records = 0, records2 = 0;
		
		try
		{
			retrieve(getid);
			PreparedStatement ps = aConnection.prepareStatement(deleteUser);
			ps.setLong(1, getid);
			records = ps.executeUpdate();
	
			System.out.println("Delete successful.");
		}
		catch (NotFoundException nfe) { System.out.println(nfe);}
		catch(SQLException sqle)
		{
			System.out.println(sqle);
		}
		
		return records == 1;

	}
	
	public static void terminate() throws SQLException
	{
		try
		{
			aConnection.close();
		}
		catch (SQLException sqle)
		{
			System.out.println(sqle);
		}
	}
	//
	
	

}
