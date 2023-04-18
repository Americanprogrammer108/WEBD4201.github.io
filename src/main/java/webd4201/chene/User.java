package webd4201.chene;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class User {
	/**
	 * The User class takes on the CollegeInterface class, where it will set the attributes for the user and maybe print it out using the dump() function.
	 *
	 */
	
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
	
	public static final DateFormat DF = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CANADA);
	
	//private class attributes
	private long id;
	private String password;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private Date lastAccess;
	private Date enrolldate;
	private boolean enabled;
	private char type;
	
	
	// DEFAULT VALUES
	public static final Boolean DEF_ISUSER = false;
	public static final Boolean DEF_ISADMIN = false;
	public static final Boolean DEF_ISDEV = false;
	
	// DATABASE VALUES
	private static Connection aConnection;
	private static Statement aStatement;
	
	private static final String sqlGetOne = "SELECT * FROM Users WHERE username = ?;";
	private static final String sqlInsertUser = "INSERT INTO Users VALUES (?,?,?,?);";
	private static final String sqlDeleteUser = "DELETE FROM Users WHERE username = ?;";
	private static final String sqlUpdateUser = "UPDATE Users SET isUser = ?, isAdmin= ?, isDev = ?, WHERE username = ?;";
	
	
	
	
	//START OF SETTERS
	public void setID(long ID) throws InvalidIdException
	{
		try
		{
			if (verifyId(ID))
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
	
	private boolean verifyId(long iD2) {
		String convertedID = Long.toString(iD2);
		if (convertedID.length() == ID_NUMBER_LENGTH)
		{
			return true;
		}
		
		return false;
		
	}

	
	public void setFirstName(String firstname) throws InvalidNameException
	{
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
	
	//CRUD METHODS
	
	
	//CREATE
	public static boolean Create(User newUser) throws DuplicateException
	{
		Boolean retVal = false;
		try {
			retrieve(newUser.getid());
			throw (new DuplicateException("User already exists!")); 
		}
		catch (NotFoundException nfe) {
			try {
				System.out.println("This user does not exist - good to create one!");
				PreparedStatement psInsertUser = aConnection.prepareStatement(sqlInsertClientRole);
				psInsertUser.setLong(1, newUser.getid());
				psInsertUser.setString(2, getpassword());
				psInsertUser.setString(3, newUser.get);
				
				retVal = (psInsertUser.executeUpdate() == 1);
				System.out.println("Client Role result: " + retVal);
			}
			catch (SQLException sqle) {
				System.out.println("Error: " + sqle);
			}
		}
		return enabled;
	}
	
	
	//RETRIEVE
	public static User retrieve(long userid)
	{
		User getone = null;
		
		try
		{
			PreparedStatement userGetOne = aConnection.prepareStatement(sqlGetOne);
			userGetOne.setLong(1, userid);
			ResultSet rs = userGetOne.executeQuery();
			
			boolean gotit = rs.next();
			
			if (gotit) {
				User aUser = new User(rs.getLong("id"), rs.getString("password"), rs.getString("password"), rs.getString("password"), rs.getString("password"), rs.getDate("enrolldate"), rs.getDate("lastaccess"), rs.getBoolean("enabled"), rs.getString("type").charAt(0));
				getone = aUser;
			} else {
				throw (new NotFoundException("ClientRole was not found!"));
			}
			rs.close();
		}
		catch(Exception e)
		{
			
		}
		return getone;
	}
	
	//UPDATE
	
	
	//DELETE
	public static int Delete(long id) {
		int records = 0;
		try {
			PreparedStatement psDeleteUser = aConnection.prepareStatement(sqlDeleteClientRole);
			psDeleteUser.setLong(1, id);
			records = psDeleteUser.executeUpdate();
		}
		catch (SQLException sqle) {
			System.out.println("Error: " + sqle);
		}
		
		return records;
	}
	

	
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
	
	@SuppressWarnings("null")
	public String toString()
	{
		//this will print everything out after the dump() function is called
		String output = null;
		output.replaceAll("User", "Faculty");
		return "User Info for: " + getid() + "\nName: "
				+ getFirstName() + " " + getLastName()
				+ "\nCreated on: " + getEnrollDate() + "\nLast access: ";
	}
	
	public void dump()
	{
		//print information out
		System.out.print(toString());
	}

	public static User authenticate(long user, String password) throws NotFoundException
	{
		return UserDA.retrieve(user);
		
	}
	

}
