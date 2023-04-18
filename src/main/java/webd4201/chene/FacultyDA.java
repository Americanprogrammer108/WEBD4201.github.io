package webd4201.chene;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.security.MessageDigest;


public class FacultyDA {
/**
 * @author Ethan Chen
 * date: 02/15/2023
 * description:
 * This class is the database class for the faculty part.
 */
	public static Vector<Faculty> faculty = new Vector<Faculty>();
	private static Faculty aFaculty;
	
	//establish connection
	private static Connection aConnection; //is null; will cause a NullPointerException
	private static Statement aStatement;
	private static final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");

	private static long id;
	
	
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
	
	/**
	 * This will create a student by inserting the record based on the data attributes
	 * @param aFaculty
	 * @return
	 * @throws DuplicateException
	 */
	public static boolean create(Faculty aFaculty) throws DuplicateException
	{
		boolean insertedfaculty = false;
		boolean inserteduser = false;
	
		String insertfaculty = "INSERT INTO faculty VALUES ("
				+ aFaculty.getid() + ", '" + aFaculty.getschoolcode() + "', '" + aFaculty.getschooldesc() + "', '" + aFaculty.getoffice() + "', " + aFaculty.getextension() + ");";
		
		String insertuser = "INSERT INTO users VALUES ("
				+ aFaculty.getid() + ", encode(digest(" + "'" + aFaculty.getpassword() + "', 'sha1'), 'hex'), '" + aFaculty.getFirstName() + "', '" + aFaculty.getLastName() + "', '" + aFaculty.getEmailAddress() + "', '"
				+ aFaculty.getLastAccess() + "', '" + aFaculty.getEnrollDate() + "', " + aFaculty.getEnabled() + ", '" + aFaculty.getType() + "');";
		
		try
		{
			retrieve(aFaculty.getid());
			throw new DuplicateException("Record with that id already exists!");
		}
		catch(NotFoundException nfe)
		{
			
			try
			{
				System.out.print("\nInserting, please wait...\n");
				insertedfaculty = aStatement.execute(insertuser);
				inserteduser = aStatement.execute(insertfaculty);
				System.out.print("Insert successful.\n");
			}
			catch(SQLException sqle)
			{
				System.out.println(sqle);
			}
		}
		return insertedfaculty && inserteduser;
	}
	
	
	/**
	 * This will retrieve the student based on its id, whereas retrieveAll will get every student in the table.
	 * @param key
	 * @return
	 * @throws NotFoundException
	 */
	public static Faculty retrieve(long key) throws NotFoundException
	{
		aFaculty = null;
		String retrieveuser ="SELECT Users.Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrollDate, Enabled, Type, Faculty.id, schoolcode, schooldescription, office, extension FROM Users, Faculty WHERE Users.id = Faculty.id AND Users.id = " + key + ";";
		
		try
		{
			
			ResultSet rs = aStatement.executeQuery(retrieveuser);
			if (rs.next())
			{
				long id = rs.getLong("id");
				String password = rs.getString("password");
				String fName = rs.getString("FirstName");
				String lName = rs.getString("LastName");
				String Email = rs.getString("EmailAddress");
				Date lastAccess = rs.getDate("LastAccess");
				Date enrolldate = rs.getDate("EnrollDate");
				boolean enabled = rs.getBoolean("Enabled");
				char type = rs.getString("Type").charAt(0);
				
				//faculty attributes
				String schoolcode = rs.getString("Schoolcode");
				String schooldescription = rs.getString("Schooldescription");
				String office = rs.getString("Office");
				int extension = rs.getInt("Extension");
				
				try {
					aFaculty = new Faculty(id, password, fName, lName, Email, enrolldate, lastAccess, enabled, type, schoolcode, schooldescription, office, extension);
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
		return aFaculty;
	}
	
	
	/**
	 * Delete command will delete the student on its id whether it exists or not.
	 * @param aFaculty
	 * @return
	 * @throws NotFoundException
	 */
	public static boolean delete(Faculty aFaculty) throws NotFoundException
	{
		System.out.println("Deleting, please wait...");
		int records = 0, records2 = 0;
		
		String delete1 = "DELETE FROM faculty WHERE id = " + aFaculty.getid() + ";";
		String delete2 = "DELETE FROM users WHERE id = " + aFaculty.getid() + ";";
		
		try
		{
			retrieve(aFaculty.getid());
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
	 * @param aFaculty
	 * @return
	 * @throws NotFoundException
	 */
	public static boolean Update(Faculty aFaculty) throws NotFoundException
	{
		System.out.println("Updating, please wait...");
		int records = 0, records2 = 0;
		
		String updatefaculty = "UPDATE faculty SET office ='" + aFaculty.getoffice() + "', extension=" + aFaculty.getextension() + " WHERE id = " + aFaculty.getid() + ";";
		String updatefaculty2 = "UPDATE faculty SET office ='" + aFaculty.getoffice() + "', extension=" + aFaculty.getextension() + " WHERE id = " + aFaculty.getid() + ";";
		
		try
		{
			retrieve(aFaculty.getid());
			
			records = aStatement.executeUpdate(updatefaculty);
			records2 = aStatement.executeUpdate(updatefaculty2);
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
		return (records + records2) == 0;
	}
	

	
	
	/**
	 * After running everything, terminate the operation
	 * @throws SQLException
	 */


}
