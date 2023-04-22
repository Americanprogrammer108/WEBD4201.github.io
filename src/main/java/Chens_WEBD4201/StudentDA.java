package Chens_WEBD4201;

import java.util.Vector;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.String;

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
	
	static String delete1 = "DELETE FROM Students WHERE id = ?;";
	static String delete2 = "DELETE FROM Users WHERE id = ?;";
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
	
	private static final String insertStudent = "INSERT INTO students VALUES (?, ?, ?, ?);";
	private static final String insertUser = "INSERT INTO users VALUES (?, encode(digest(?, 'sha1'), 'hex'), ?, ?, ?, ?, ?, ?, ?);";
	
	private static final String retrieveStudent = "SELECT Users.Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type, ProgramCode, ProgramDescription, Year FROM Users, Students WHERE Users.id = Students.id AND Users.Id = ?";
	private static final String retrieveallStudents = "SELECT * FROM students;";
	
	private static final String updateStudent = "UPDATE students SET  WHERE id = ?;";
	private static final String deleteStudent = "DELETE FROM students WHERE id = ?;";
	
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
		try
		{
			retrieve(aStudent.getid());
			throw new DuplicateException("Record with that id already exists!");
		}
		catch(NotFoundException nfe)
		{
			
			try
			{
				User aUser = new User(id, password, fName, lName, Email, lastAccess, EnrollDate, enabled, type);
				PreparedStatement psInsertUser = aConnection.prepareStatement(insertUser);
				psInsertUser.setLong(1, aStudent.getid());
				psInsertUser.setString(2, aStudent.getpassword());
				psInsertUser.setString(3, aStudent.getFirstName());
				psInsertUser.setString(4, aStudent.getLastName());
				psInsertUser.setString(5, aStudent.getEmailAddress());
				psInsertUser.setDate(6, (Date) aStudent.getLastAccess());
				psInsertUser.setDate(7, (Date) aStudent.getEnrollDate());
				psInsertUser.setBoolean(8, aStudent.getEnabled());
				psInsertUser.setString(9, String.valueOf(type));
				
				System.out.print("Inserting, please wait...\n");
				PreparedStatement psInsertStudent = aConnection.prepareStatement(insertStudent);
				psInsertStudent.setLong(1, aStudent.getid());
				psInsertStudent.setString(2, aStudent.getProgramCode());
				psInsertStudent.setString(3, aStudent.getProgramDescription());
				psInsertStudent.setInt(4, aStudent.getYear());
				
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
		try
		{
			PreparedStatement ps = aConnection.prepareStatement(retrieveStudent);
			ps.setLong(1, key);
			ResultSet rs = ps.executeQuery();
			boolean firstrecord = rs.next();
			if (firstrecord)
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
	
	/**
	 * Delete command will delete the student on its id whether it exists or not.
	 * @param aStudent
	 * @return
	 * @throws NotFoundException
	 */
	public static boolean delete(long deleteid) throws NotFoundException
	{
		System.out.println("Deleting, please wait...");
		int records = 0, records2 = 0;
		

		try
		{
			retrieve(deleteid);
			PreparedStatement ps = aConnection.prepareStatement(delete1);
			ps.setLong(1, deleteid);
			
			PreparedStatement ps2 = aConnection.prepareStatement(delete2);
			ps2.setLong(1, deleteid);

			records = ps.executeUpdate();
			records = ps2.executeUpdate();	

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
		
		String updateuser = "UPDATE Users SET FirstName= ?, LastName=?, EmailAddress=?, LastAccess=?, EnrollDate= ?, Type= ?, Enabled=?, WHERE Id = ?";
		
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
	
	public static void authenticate(long id, String password) throws NotFoundException
	{
		retrieve(id);
	}
}
