package webd4201.chene;

import java.util.Vector;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.String;
import java.security.*;

public class StudentDA {
	/**
	 * This class is the database class that will do the CRUD operations
	 */
	public static Vector<Student> student = new Vector<Student>();
	private static Student aStudent;
	
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
	
	
	//CRUD Methods
	String retrieveStudent = "SELECT * FROM Users, Students WHERE Users.id = Students.id AND Users.Id = ?";
	
	//INSERT
	String insertuser = "INSERT INTO Users (Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 's');";
	String insertstudent = "INSERT INTO Students (Id, ProgramCode, ProgramDescription, Year) VALUES (?, ?, ?, ?);";
	
	//UPDATE
	String updateuser = "UPDATE Users SET FirstName= ?, LastName= ?, EmailAddress= ?, LastAccess= ?, EnrollDate= ?, Type='s', Enabled=true WHERE Id = ?";
	String updateprogramcode = "UPDATE Students SET ProgramCode='?', ProgramDescription='?', Year=? WHERE Id = ?;";
	
	
	//DELETE
	String delete1 = "DELETE FROM Students WHERE id = ?;";
	String delete2 = "DELETE FROM Users WHERE id = ?;";
	
	
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
	 * @param aStudent
	 * @return
	 * @throws DuplicateException
	 */
	public static boolean create(Student aStudent) throws DuplicateException
	{
		boolean inserteduser = false, insertedstudent = false;
	
		
		String insertuser = "INSERT INTO Users (Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 's');";
		
		String insertstudent = "INSERT INTO Students (Id, ProgramCode, ProgramDescription, Year) VALUES (?, ?, ?, ?);";
		try
		{
			retrieve(aStudent.getid());
			//PreparedStatement = 
			throw new DuplicateException("Record with that id already exists!");
		}
		catch(NotFoundException nfe)
		{
			
			try
			{
				System.out.print("Inserting, please wait...\n");
				PreparedStatement look = aConnection.prepareStatement("SELECT * FROM users, students;");
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
	public static Student retrieve(long key) throws NotFoundException
	{
		aStudent = null;
		String retrieveuser = "SELECT Users.Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type, ProgramCode, ProgramDescription, Year FROM Users, Students WHERE Users.id = Students.id AND Users.id = ? ;";
		
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
				
				//student attributes
				String programcode = rs.getString("ProgramCode");
				String programdesc = rs.getString("ProgramDescription");
				int year = rs.getInt("Year");
				
				
				try {
					aStudent = new Student(id, password, fName, lName, Email, enrolldate, lastAccess, type, enabled, programcode, programdesc, year);
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
		return aStudent;
	}
	
	public static Vector<Student> retrieveAll(long key)
	{
		aStudent = null;
		
		String retrieveuser = "SELECT Users.Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type, ProgramCode, ProgramDescription, Year FROM Users, Students WHERE Users.id = Students.id AND Users.Id = ?";
		try
		{
			ResultSet rs = aStatement.executeQuery(retrieveuser);
			boolean moreData = rs.next();
			while(moreData)
			{
				id = rs.getLong("ID");
				password = rs.getString("password");
				fName = rs.getString("FirstName");
				lName = rs.getString("LastName");
				Email = rs.getString("EmailAddress");
				lastAccess = rs.getDate("LastAccess");
				EnrollDate = rs.getDate("EnrollDate");
				enabled = rs.getBoolean("Enabled");
				type = rs.getString("Type").charAt(0);
				programcode = rs.getString("ProgramCode");
				programdescription = rs.getString("ProgramDescription");
				year = rs.getInt("Year");
				
				
				try {
					aStudent = new Student(id, password, fName, lName, Email, EnrollDate, lastAccess, type, enabled, programcode, programdescription, year);
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
				student.addElement(aStudent);
				moreData = rs.next();
			}
			rs.close();
		}
		catch(SQLException sqle)
		{
			System.out.println(sqle);
		}
		return student;
	}
	
	/**
	 * Delete command will delete the student on its id whether it exists or not.
	 * @param aStudent
	 * @return
	 * @throws NotFoundException
	 */
	public static boolean delete(Student aStudent) throws NotFoundException
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
	 * @param aStudent
	 * @return
	 * @throws NotFoundException
	 */
	public static boolean Update(Student aStudent) throws NotFoundException
	{
		System.out.println("Updating, please wait...");
		int records = 0;
		int records2 = 0;
		
		id = aStudent.getid();
		password = aStudent.getpassword();
		fName = aStudent.getFirstName();
		lName = aStudent.getLastName();
		Email = aStudent.getEmailAddress();
		lastAccess = aStudent.getLastAccess();
		EnrollDate = aStudent.getEnrollDate();
		enabled = aStudent.getEnabled();
		type = aStudent.getType();
		programcode = aStudent.getProgramCode();
		programdescription = aStudent.getProgramDescription();
		year = aStudent.getYear();
		
		String updateuser = "UPDATE Users SET FirstName= 'Robert', LastName='McReady', EmailAddress='bob.mcready@dcmail.ca', LastAccess='2016-03-10', EnrollDate='2015-09-03', Type='s', Enabled=true WHERE Id = 100222222";
		
		String updateprogramcode = "UPDATE Students SET ProgramCode='RPN', ProgramDescription='Registered Practical Nurse', Year=3 WHERE Id = 100222222;";
		
		
		
		try
		{
			retrieve(aStudent.getid());
			
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
	
	public static Student authenticate(long id, String password) throws NoSuchAlgorithmException, NotFoundException
	{
		
		try
		{
		    retrieve(id);
		}
		catch (NotFoundException nfe)
		{
			throw new NotFoundException("ID does not exist");
		}
		return aStudent;
	}
	
	//methods not used in the 3rd assignment
	public static void HashPassword(String password) throws NoSuchAlgorithmException
	{
		String generatedPassword = null;
		
	      // Create MessageDigest instance for MD5
	      MessageDigest md = MessageDigest.getInstance("SHA1");

	      // Add password bytes to digest
	      md.update(password.getBytes());

	      // Get the hash's bytes
	      byte[] bytes = md.digest();

	      // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
	      StringBuilder sb = new StringBuilder();
	      for (int i = 0; i < bytes.length; i++) {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	      }

	      // Get complete hashed password in hex format
	      generatedPassword = sb.toString();
	      
	}
}
