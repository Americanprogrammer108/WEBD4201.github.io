package Chens_WEBD4201;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;



import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.String;
import Chens_WEBD4201.User;

public class UserDA {
	
	public static Vector<User> student = new Vector<User>();
	private static User aUser;
	
	//establish connection
	private static Connection aConnection; //is null; will cause a NullPointerException
	private static Statement aStatement;
	private static final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");

	private static long id;
	private static String password;
	private static String fName;
	private static String lName;
	private static String Email;
	private static java.util.Date lastAccess;
	private static java.util.Date EnrollDate;
	private static boolean enabled;
	private static char type;
	private static String programcode;
	private static String programdescription;
	private static int year;
	/**
	 * This will initialize the database to connect to the java file.
	 * @param conn
	 * @throws SQLException
	 */
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
	
	private static final String insertUser = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	private static final String retrieveUser = "SELECT * FROM Users WHERE id = ?";
	private static final String retrieveallUsers = "SELECT * FROM users;";
	
	private static final String updateUser = "UPDATE users SET WHERE id = ?;";
	private static final String deleteUser = "DELETE FROM users WHERE id = ?;";
	
	/**
	 * This will create a student by inserting the record based on the data attributes
	 * @param aStudent
	 * @return
	 * @throws DuplicateException
	 * @throws InvalidUserDataException 
	 * @throws InvalidPasswordException 
	 * @throws InvalidNameException 
	 * @throws InvalidIdException 
	 */
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
	
	
	/**
	 * This will retrieve the student based on its id, whereas retrieveAll will get every student in the table.
	 * @param key
	 * @return
	 * @throws NotFoundException
	 */
	public static User retrieve(long key) throws NotFoundException, InvalidUserDataException, InvalidNameException, InvalidIdException, InvalidPasswordException
	{
		aUser = null;
		try
		{
			PreparedStatement ps = aConnection.prepareStatement(retrieveUser);
			ps.setLong(1, key);
			ResultSet rs = ps.executeQuery();
			
			boolean firstRecord = rs.next();
			if (firstRecord) {
				id = rs.getInt("id");
				fName = rs.getString("firstName");
				lName = rs.getString("lastName");
				password = rs.getString("password");
				Email = rs.getString("emailaddress");
				lastAccess = rs.getDate("lastaccess");
				EnrollDate = rs.getDate("enrolldate");
				enabled = rs.getBoolean("enabled");
				type = rs.getString("type").charAt(0);
			} else {
				throw (new NotFoundException("This record is not found."));
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
				id = rs.getLong("ID");
				password = rs.getString("password");
				fName = rs.getString("FirstName");
				lName = rs.getString("LastName");
				Email= rs.getString("EmailAddress");
				lastAccess = rs.getDate("LastAccess");
				EnrollDate = rs.getDate("EnrollDate");
				enabled = rs.getBoolean("Enabled");
				type = rs.getString("Type").charAt(0);
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
	
	/**
	 * Delete command will delete the student on its id whether it exists or not.
	 * @param aStudent
	 * @return
	 * @throws NotFoundException
	 */
	public static boolean delete(long getid) throws NotFoundException, InvalidUserDataException, InvalidNameException, InvalidIdException, InvalidPasswordException
	{
		System.out.println("Deleting, please wait...");
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
			
		}
		
		return records == 1;
	}
		
	/**
	 * Update the student based on its id.
	 * @param aStudent
	 * @return
	 * @throws NotFoundException
	 */
	public static boolean Update(User myUser) throws NotFoundException, InvalidUserDataException, InvalidNameException, InvalidIdException, InvalidPasswordException
	{
		System.out.println("Updating User...");
		int records = 0;
		System.out.println(myUser.getid());
		try
		{
			retrieve(id);
			PreparedStatement ps = aConnection.prepareStatement(updateUser);
			ps.setLong(1, id);
			ps.setString(2, password);
			ps.setString(3, fName);
			ps.setString(4, lName);
			ps.setString(5, Email);
			ps.setDate(6, (java.sql.Date) lastAccess);
			ps.setDate(7, (java.sql.Date) EnrollDate);
			ps.setBoolean(8, enabled);
			ps.setString(9, String.valueOf(type));
		}
		catch(NotFoundException nfe)
		{
			throw new NotFoundException("Record is not found, update failed.");
		}
		catch(SQLException nfe)
		{
			System.out.println(nfe);
		}
		return records == 1;
	}
	

	
	
	/**
	 * After running everything, terminate the operation
	 * @throws SQLException
	 */
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
	
	
	
	
	public static void authenticate(long id, String password) throws NotFoundException, InvalidUserDataException, InvalidNameException, InvalidIdException, InvalidPasswordException
	{
		retrieve(id);
	}
}
