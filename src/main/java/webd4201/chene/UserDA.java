package webd4201.chene;

import java.util.Vector;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.String;
import java.security.*;

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
	
	/**
	 * This will create a student by inserting the record based on the data attributes
	 * @param aUser
	 * @return
	 * @throws DuplicateException
	 */
	public static boolean create(Student aUser) throws DuplicateException
	{
		boolean inserteduser = false, insertedstudent = false;
	
		
		String insertuser = "INSERT INTO Users (Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type) VALUES (" + id + ", '"+ password +"', '"+ fName + "', '"+ lName +"', '"+ Email +"', '" + lastAccess + "', '"+ EnrollDate +"', "+ enabled + ", 's');";
		
		String insertstudent = "INSERT INTO Students (Id, ProgramCode, ProgramDescription, Year) VALUES (100222222, 'CPA', 'Computer Programmer Analyst', 3);";
		try
		{
			retrieve(aUser.getid());
			throw new DuplicateException("Record with that id already exists!");
		}
		catch(NotFoundException nfe)
		{
			
			try
			{
				System.out.print("Inserting, please wait...\n");
				PreparedStatement insertonestudent = aConnection.prepareStatement(insertstudent);
				PreparedStatement insertoneuser = aConnection.prepareStatement(insertuser);
				inserteduser = aStatement.execute(insertuser);
				insertedstudent = aStatement.execute(insertstudent);
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
	public static User retrieve(long key) throws NotFoundException
	{
		aUser = null;
		String retrieveuser = "SELECT Users.Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type, ProgramCode, ProgramDescription, Year FROM Users, Students WHERE Users.id = Students.id AND Users.id = " + key + ";";
		try
		{
			ResultSet rs = aStatement.executeQuery(retrieveuser);
			if (rs.next())
			{
				id = rs.getLong("ID");
				String password = rs.getString("password");
				String fName = rs.getString("FirstName");
				String lName = rs.getString("LastName");
				String Email = rs.getString("EmailAddress");
				Date lastAccess = rs.getDate("LastAccess");
				Date enrolldate = rs.getDate("EnrollDate");
				boolean enabled = rs.getBoolean("Enabled");
				char type = rs.getString("Type").charAt(0);
				String programcode = rs.getString("ProgramCode");
				String programdesc = rs.getString("ProgramDescription");
				int year = rs.getInt("Year");
				
				
				try {
					aUser = new Student(id, password, fName, lName, Email, enrolldate, lastAccess, type, enabled, programcode, programdesc, year);
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
			}
			else
			{
				throw new NotFoundException("The indicated record is not found.");
			}
			rs.close();
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle);
		}
		return aUser;
	}
	
	
	
	/**
	 * Delete command will delete the student on its id whether it exists or not.
	 * @param aUser
	 * @return
	 * @throws NotFoundException
	 */
	public static boolean delete(User aUser) throws NotFoundException
	{
		System.out.println("Deleting, please wait...");
		int records = 0, records2 = 0;
		
		String delete1 = "DELETE FROM Students WHERE id = 100222222;";
		String delete2 = "DELETE FROM Users WHERE id = 100222222;";
		try
		{
			retrieve(id);
			records = aStatement.executeUpdate(delete1);
			records2 = aStatement.executeUpdate(delete2);
			System.out.println("Delete successful");
		}
		catch(NotFoundException e)
		{
			throw new NotFoundException("Record not found, delete failed.");
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
		return (records + records2) == 2;
	}
		
	/**
	 * Update the student based on its id.
	 * @param aUser
	 * @return
	 * @throws NotFoundException
	 */
	public static boolean Update(Student aUser) throws NotFoundException
	{
		System.out.println("Updating, please wait...");
		int records = 0;
		int records2 = 0;
		
		id = aUser.getid();
		password = aUser.getpassword();
		fName = aUser.getFirstName();
		lName = aUser.getLastName();
		Email = aUser.getEmailAddress();
		lastAccess = aUser.getLastAccess();
		EnrollDate = aUser.getEnrollDate();
		enabled = aUser.getEnabled();
		type = aUser.getType();
		
		String updateuser = "UPDATE Users SET FirstName= 'Robert', LastName='McReady', EmailAddress='bob.mcready@dcmail.ca', LastAccess='2016-03-10', EnrollDate='2015-09-03', Type='s', Enabled=true WHERE Id = 100222222";
		
		String updateprogramcode = "UPDATE Students SET ProgramCode='RPN', ProgramDescription='Registered Practical Nurse', Year=3 WHERE Id = 100222222;";
		
		
		
		try
		{
			retrieve(aUser.getid());
			
			records = aStatement.executeUpdate(updateuser);
			records2 = aStatement.executeUpdate(updateprogramcode);
			System.out.print("Update successful");
		}
		catch(NotFoundException e)
		{
			throw new NotFoundException("Record is not found, update failed.");
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
		return records + records2 == 0;
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
	
	public static User authenticate(long id, String password) throws NoSuchAlgorithmException, NotFoundException
	{
		
		try
		{
		    retrieve(id);
		}
		catch (NotFoundException nfe)
		{
			throw new NotFoundException("ID does not exist");
		}
		return aUser;
	}
	
	//methods
	
	public void HashPassword(String password)
	{
		String passwordToHash = password;
	    String generatedPassword = null;

	    try 
	    {
	      // Create MessageDigest instance for MD5
	      MessageDigest md = MessageDigest.getInstance("MD5");

	      // Add password bytes to digest
	      md.update(passwordToHash.getBytes());

	      // Get the hash's bytes
	      byte[] bytes = md.digest();

	      // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
	      StringBuilder sb = new StringBuilder();
	      for (int i = 0; i < bytes.length; i++) {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	      }

	      // Get complete hashed password in hex format
	      generatedPassword = sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }
	    System.out.println(generatedPassword);
	}
}
